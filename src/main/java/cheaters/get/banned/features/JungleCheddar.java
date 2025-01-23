package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.TabUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;

public class JungleCheddar {
    // rats love cheese
    // this feature should automatically "stonk" blocks under the jungle crystal

    final EntityPlayerSP myself = Shady.mc.thePlayer; // this is you, playing
    BlockPos nw = null, se = null;          // corners of the hole to make
    int nwX = 0, nwZ = 0, seX = 0, seZ = 0; // chunk coords of the chunks the corners are in

    boolean cheeseFound = false, stonked = false;
    int tickCooldown = 0;

    private final Set<String> loadedChunks = new HashSet<>();

    final boolean debug = false;

    /**
     * basically we check what every player has under their feet: if we find someone with stonebricks below their feet
     * he's probably a temple guard: we then check at ~6 ~ ~ relative to them looking for another guy like him
     * if there is: we found the temple guards and we can then calculate where the crystal is and "stonk" below it
     */
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (Config.jungleCheddar && Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS)) {

            // running every 5 seconds, no need to run at 20hz
            if (tickCooldown < 100) {
                tickCooldown++;
                return;
            } else tickCooldown = 0;
            if (!cheeseFound) {
                List<EntityPlayer> npcs = getNPCs();
                // girl math to find crystal
                if (!npcs.isEmpty()) findCheeseCorners(npcs);
                if (cheeseFound) getChunksFromCorners();
            }

            if (!stonked && cheeseFound) {
                if (areAllChunksLoaded()) {
                    if (debug) Shady.out("Stonking from " + nw.toString() + " to " + se.toString());
                    GhostBlocks.fillWithGhostBlocks(nw, se);
                    stonked = true;
                } else {
                    if (debug) Shady.out("Waiting for all chunks to load...");
                }
            }

        }
    }

    private String getChunkKey(int chunkX, int chunkZ) {
        return chunkX + "," + chunkZ;
    }

    private boolean areAllChunksLoaded() {
        // if these indexes end nullpointerexceptioning somehow, simon hypixel: i am coming for you
        // btw this (+-1) is because we need some more chunks than what's strictly needed
        for (int x = nwX - 1; x <= seX + 1; x++) {
            for (int z = nwZ - 1; z <= seZ + 1; z++) {
                if (!loadedChunks.contains(getChunkKey(x, z))) {
                    return false;
                }
            }
        }
        return true;
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        String chunkKey = getChunkKey(event.getChunk().xPosition, event.getChunk().zPosition);
        loadedChunks.add(chunkKey);

        if (cheeseFound && !stonked && areAllChunksLoaded()) {
            if (debug) Shady.out("All chunks loaded, re-stonking...");
            GhostBlocks.fillWithGhostBlocks(nw, se);
            stonked = true;
        }
    }

    @SubscribeEvent
    public void onChunkUnload(ChunkEvent.Unload event) {
        String chunkKey = getChunkKey(event.getChunk().xPosition, event.getChunk().zPosition);
        loadedChunks.remove(chunkKey);

        if (cheeseFound && isChunkWithinCheese(event.getChunk().xPosition, event.getChunk().zPosition)) {
            if (debug) Shady.out("Cuboid chunk unloaded, resetting stonked state.");
            stonked = false;
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (Utils.inSkyBlock) {
            if (debug) Shady.out("Cleared everything");
            clear();
        }
    }

    private boolean isChunkWithinCheese(int chunkX, int chunkZ) {
        return chunkX >= nwX && chunkX <= seX
                && chunkZ >= nwZ && chunkZ <= seZ;
    }

    private List<EntityPlayer> getNPCs() {
        List<EntityPlayer> players = Shady.mc.theWorld.playerEntities;
        List<EntityPlayer> NPCs = new ArrayList<>(Collections.emptyList());
        List<String> tab = TabUtils.getTabList();

        for (EntityPlayer entityPlayer : players) {
            // filtering out real players from NPCs
            if (entityPlayer.equals(myself)) continue;
            if (entityPlayer.getName().length() != 10 || entityPlayer.getName().chars().anyMatch(c -> Character.isUpperCase(c) || c == ' '))
                continue;
            if (tab.stream().anyMatch(str -> str.contains(entityPlayer.getName())))
                continue;
            NPCs.add(entityPlayer); // if checks pass, this is 99% an npc
        }

        return NPCs;
    }

    private void findCheeseCorners(List<EntityPlayer> NPCs) {
        BlockPos westTempleGuard = null;

        for (EntityPlayer npc : NPCs) {
            if (npc == null) continue;
            // Get the block under the npc's feet
            BlockPos blockUnderWestGuard = new BlockPos(npc.posX, npc.posY - 1, npc.posZ);

            // if he's above stonebricks, we check for another dude like him
            if (Shady.mc.theWorld.getBlockState(blockUnderWestGuard).getBlock() == Blocks.stonebrick) {
                for (EntityPlayer npc2 : NPCs) {
                    if (npc2.equals(npc)) continue;
                    if (npc.equals(myself)) continue;

                    // Check if the other player is at ~6 ~ ~ relative to the first npc
                    BlockPos belowNpc2 = new BlockPos(npc.posX + 6, npc.posY - 1, npc.posZ);

                    if (Shady.mc.theWorld.getBlockState(belowNpc2).getBlock() == Blocks.stonebrick) {
                        westTempleGuard = blockUnderWestGuard;
                        if (debug) Shady.out("WTG: " + westTempleGuard);
                        else Shady.out("Kalhuiki Door Guardians found! Cheddar placed, may the alloy be with you.");
                        break;
                    }
                }
            }
            if (westTempleGuard != null) break;
        }

        if (westTempleGuard != null) {
            nw = westTempleGuard.add(55, -44, 13);
            se = westTempleGuard.add(65, -45, 23);
            cheeseFound = true;
        }
    }

    private void getChunksFromCorners() {
        nwX = nw.getX() >> 4;
        nwZ = nw.getZ() >> 4;
        seX = se.getX() >> 4;
        seZ = se.getZ() >> 4;
    }

    private void clear() {
        stonked = false;
        loadedChunks.clear();
        nw = null;
        se = null;
        nwX = 0;
        nwZ = 0;
        seX = 0;
        seZ = 0;
        cheeseFound = false;
        tickCooldown = 0;
    }

}

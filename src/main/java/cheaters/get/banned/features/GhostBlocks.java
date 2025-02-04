package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.mixins.MixinRenderGlobalAccessor;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.DestroyBlockProgress;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.*;


public class GhostBlocks {

    private final Queue<Map.Entry<BlockPos, Integer>> ghostedBlocks = new LinkedList<>(); // FIFO queue storing (BlockPos, TickAdded)
    private int tickCounter = 0; // Keeps track of game ticks
    private BlockPos lastTargetedBlock = null;


    public GhostBlocks() {
        KeybindUtils.register("Create Ghost Block", Keyboard.KEY_G);
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(PolyfrostConfig.GHOST_BLOCKS_KEYBIND && KeybindUtils.get("Create Ghost Block").isKeyDown()) {
            MovingObjectPosition object = Shady.mc.thePlayer.rayTrace(Shady.mc.playerController.getBlockReachDistance(), 1);
            if(object != null) {
                if(object.getBlockPos() != null) {
                    Block lookingAtblock = Shady.mc.theWorld.getBlockState(object.getBlockPos()).getBlock();
                    if(!Utils.isInteractable(lookingAtblock) && lookingAtblock != Blocks.air) {
                        Shady.mc.theWorld.setBlockToAir(object.getBlockPos());
                        MiscStats.add(MiscStats.Metric.BLOCKS_GHOSTED);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRightClick(ClickEvent.Right event) {
        if (PolyfrostConfig.GHOST_BLOCKS_WITH_R)
            if (Utils.inSkyBlock && pickaxeChecks()) {
                Shady.mc.theWorld.setBlockToAir(Shady.mc.objectMouseOver.getBlockPos());
                event.setCanceled(true);
            }
    }

    // basically, this code for left click sucks because unlike with onRightClick, onLeftClick isn't triggering
    // every tick while holding the button, but only once: if we .setBlockToAir() with it, we only _stonk_ the first block
    // since a lot of times you want to stonk many blocks at once, onClientTick does it best at _stonking_ the blocks
    // after the first one. This would all be fucking useless if a BlockBreakEvent would exist on the client

    @SubscribeEvent
    public void onLeftClick(ClickEvent.Left event) {
        if (PolyfrostConfig.GHOST_BLOCKS_WITH_L)
            if (Utils.inSkyBlock && pickaxeChecks())
                ghostedBlocks.add(new AbstractMap.SimpleEntry<>(Shady.mc.objectMouseOver.getBlockPos(), tickCounter));
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (Shady.mc == null || Shady.mc.thePlayer == null || Shady.mc.theWorld == null || !PolyfrostConfig.GHOST_BLOCKS_WITH_L)
            return;

        tickCounter++; // Increment tick counter

        // Remove expired ghost blocks (ones that have been in queue for 10+ ticks)
        while (!ghostedBlocks.isEmpty() && tickCounter - ghostedBlocks.peek().getValue() >= 10) {
            BlockPos expiredPos = ghostedBlocks.poll().getKey(); // Get and remove the oldest block
            Shady.mc.theWorld.setBlockToAir(expiredPos);
        }

        if (Utils.inSkyBlock && pickaxeChecks()) {
            BlockPos currentlyLookingAt = null;
            try {
                currentlyLookingAt = Shady.mc.objectMouseOver.getBlockPos();
            } catch (Exception ignored) {}

            if (Mouse.isButtonDown(0) && currentlyLookingAt != null) {
                if (!currentlyLookingAt.equals(lastTargetedBlock)) {
                    ghostedBlocks.add(new AbstractMap.SimpleEntry<>(currentlyLookingAt, tickCounter)); // Add new block with current tick
                }
            }

            lastTargetedBlock = currentlyLookingAt;
        }
    }


    private boolean pickaxeChecks() {
        return Shady.mc.objectMouseOver != null &&
                Shady.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                !Utils.isInteractable(Shady.mc.theWorld.getBlockState(Shady.mc.objectMouseOver.getBlockPos()).getBlock()) &&
                (Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem()).contains("PICKAXE") || Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem()).contains("STONK"));
    }

    private int getBlockBreakProgress(BlockPos pos) {
        MixinRenderGlobalAccessor accessor = (MixinRenderGlobalAccessor) Shady.mc.renderGlobal;

        Map<Integer, DestroyBlockProgress> damagedBlocks = accessor.getDamagedBlocks();
        if (damagedBlocks == null || damagedBlocks.isEmpty()) {
            return 0;
        }

        for (DestroyBlockProgress progress : damagedBlocks.values()) {
            if (progress != null && pos.equals(progress.getPosition())) {
                return progress.getPartialBlockDamage();
            }
        }
        return 0;
    }

    public static void fillWithGhostBlocks(BlockPos a, BlockPos b) {
        int x1 = Math.min(a.getX(), b.getX());
        int x2 = Math.max(a.getX(), b.getX());
        int y1 = Math.min(a.getY(), b.getY());
        int y2 = Math.max(a.getY(), b.getY());
        int z1 = Math.min(a.getZ(), b.getZ());
        int z2 = Math.max(a.getZ(), b.getZ());

        // Iterate through all cubes in the cuboid.
        for (int x = x1; x <= x2; x++)
            for (int y = y1; y <= y2; y++)
                for (int z = z1; z <= z2; z++) {
                    //Utils.out("Processing block at (" + x + ", " + y + ", " + z + ")");
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = Shady.mc.theWorld.getBlockState(pos).getBlock();
                    if (block != Blocks.air)
                        Shady.mc.theWorld.setBlockToAir(pos);
                }

    }
}

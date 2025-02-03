package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.ClickEvent;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.KeybindUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;

public class GhostBlocks {

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
        if(Utils.inSkyBlock && PolyfrostConfig.GHOST_BLOCKS_W_PICK && Shady.mc.objectMouseOver != null && Shady.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && !Utils.isInteractable(Shady.mc.theWorld.getBlockState(Shady.mc.objectMouseOver.getBlockPos()).getBlock())) {
            String itemId = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
            if(itemId.equals("STONK_PICKAXE") || itemId.equals("GOLD_PICKAXE")) {
                Shady.mc.theWorld.setBlockToAir(Shady.mc.objectMouseOver.getBlockPos());
                event.setCanceled(true);
            }
        }
    }
/*
    @SubscribeEvent
    public void onLeftClick(ClickEvent.Left event) {
        if(Utils.inSkyBlock && Config.stonkGhostBlock && Shady.mc.objectMouseOver != null &&
                Shady.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK &&
                !Utils.isInteractable(Shady.mc.theWorld.getBlockState(Shady.mc.objectMouseOver.getBlockPos()).getBlock())) {

            String itemId = Utils.getSkyBlockID(Shady.mc.thePlayer.getHeldItem());
            if(itemId.equals("STONK_PICKAXE") || itemId.equals("GOLD_PICKAXE") || itemId.equals("DIAMOND_PICKAXE")) {
                Shady.mc.theWorld.setBlockToAir(Shady.mc.objectMouseOver.getBlockPos());
                event.setCanceled(true);
            }
        }
    }*/

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
                    //System.out.println("Processing block at (" + x + ", " + y + ", " + z + ")");
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = Shady.mc.theWorld.getBlockState(pos).getBlock();
                    if (block != Blocks.air)
                        Shady.mc.theWorld.setBlockToAir(pos);
                }

    }
}

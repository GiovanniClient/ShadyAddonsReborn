package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import static cheaters.get.banned.gui.config.Config.barriersEsp;


public class BarrierESP {
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (barriersEsp) {
            if (event.phase != TickEvent.Phase.END) return;

            if (Shady.mc == null || Shady.mc.thePlayer == null || Shady.mc.theWorld == null)
                return;

            int chunkRadius = Shady.mc.gameSettings.renderDistanceChunks - 1;
            BlockPos playerPos = Shady.mc.thePlayer.getPosition();
            int playerChunkX = playerPos.getX() >> 4; // Convert block X to chunk X
            int playerChunkZ = playerPos.getZ() >> 4; // Convert block Z to chunk Z

            for (int chunkX = playerChunkX - chunkRadius; chunkX <= playerChunkX + chunkRadius; chunkX++) {
                for (int chunkZ = playerChunkZ - chunkRadius; chunkZ <= playerChunkZ + chunkRadius; chunkZ++) {
                    // Get the chunk, but check if it exists and is loaded
                    Chunk chunk = Shady.mc.theWorld.getChunkFromChunkCoords(chunkX, chunkZ);
                    if (chunk == null || !chunk.isLoaded()) {
                        System.out.printf("Skipping unloaded chunk at (%d, %d)%n", chunkX, chunkZ);
                        continue;
                    }

                    for (ExtendedBlockStorage blockStorage : chunk.getBlockStorageArray()) {
                        if (blockStorage == null || blockStorage.isEmpty()) continue;

                        for (int x = 0; x < 16; x++) {
                            for (int y = 0; y < 16; y++) {
                                for (int z = 0; z < 16; z++) {
                                    IBlockState blockState = blockStorage.get(x, y, z);
                                    if (blockState.getBlock() == Blocks.barrier) {
                                        int worldX = (chunk.xPosition << 4) + x;
                                        int worldY = blockStorage.getYLocation() + y;
                                        int worldZ = (chunk.zPosition << 4) + z;

                                        try {
                                            Shady.mc.theWorld.setBlockState(new BlockPos(worldX, worldY, worldZ), Blocks.glass.getDefaultState(), 3);
                                            System.out.printf("Replaced barrier at (%d, %d, %d)%n", worldX, worldY, worldZ);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.BlockChangeEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cheaters.get.banned.gui.polyconfig.PolyfrostConfig.*;

public class GemstoneESP {

    private ConcurrentHashMap<BlockPos, Gemstone> gemstones = new ConcurrentHashMap<>();
    private HashSet<BlockPos> checked = new HashSet<>();
    private BlockPos lastChecked = null;
    private boolean isScanning = false;

    enum Gemstone {
        RUBY(new Color(0xff, 0, 0), EnumDyeColor.RED),
        AMETHYST(new Color(137, 0, 201), EnumDyeColor.PURPLE),
        JADE(new Color(157, 249, 32), EnumDyeColor.LIME),
        SAPPHIRE(new Color(60, 121, 224), EnumDyeColor.LIGHT_BLUE),
        AMBER(new Color(237, 139, 35), EnumDyeColor.ORANGE),
        TOPAZ(new Color(249, 215, 36), EnumDyeColor.YELLOW),
        JASPER(new Color(214, 15, 150), EnumDyeColor.MAGENTA),

        ONYX(new Color(0, 0, 0), EnumDyeColor.BLACK),
        PERIDOT(new Color(0, 0x7e, 0), EnumDyeColor.GREEN),
        AQUAMARINE(new Color(0, 3, 0xad), EnumDyeColor.BLUE),
        CITRINE(new Color(0x90, 0x34, 0x05), EnumDyeColor.BROWN), // don't mine amber and citrine together ig

        RUBY_SHARD(RUBY),
        AMETHYST_SHARD(AMETHYST),
        JADE_SHARD(JADE),
        SAPPHIRE_SHARD(SAPPHIRE),
        AMBER_SHARD(AMBER),
        TOPAZ_SHARD(TOPAZ),
        JASPER_SHARD(JASPER),

        ONYX_SHARD(ONYX),
        PERIDOT_SHARD(PERIDOT),
        AQUAMARINE_SHARD(AQUAMARINE),
        CITRINE_SHARD(CITRINE);

        public Color color;
        public EnumDyeColor dyeColor;

        Gemstone(Color color, EnumDyeColor dyeColor) {
            this.color = color;
            this.dyeColor = dyeColor;
        }

        Gemstone(Gemstone gemstone) {
            this.color = gemstone.color;
            this.dyeColor = gemstone.dyeColor;
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(isEnabled() && !isScanning && (lastChecked == null || !lastChecked.equals(Shady.mc.thePlayer.playerLocation))) {
            isScanning = true;
            new Thread(()->{

                BlockPos playerPosition = Shady.mc.thePlayer.getPosition();
                lastChecked = playerPosition;

                for(int x = playerPosition.getX()- PolyfrostConfig.GEM_ESP_SCAN_RADIUS; x < playerPosition.getX()+PolyfrostConfig.GEM_ESP_SCAN_RADIUS; x++) {
                    for(int y = playerPosition.getY()-PolyfrostConfig.GEM_ESP_SCAN_RADIUS; y < playerPosition.getY()+PolyfrostConfig.GEM_ESP_SCAN_RADIUS; y++) {
                        for(int z = playerPosition.getZ()-PolyfrostConfig.GEM_ESP_SCAN_RADIUS; z < playerPosition.getZ()+PolyfrostConfig.GEM_ESP_SCAN_RADIUS; z++) {

                            BlockPos position = new BlockPos(x, y, z);

                            if(!checked.contains(position) && !Shady.mc.theWorld.isAirBlock(position)) {
                                Gemstone gemstone = getGemstone(Shady.mc.theWorld.getBlockState(position));
                                if(gemstone != null) gemstones.put(position, gemstone);
                            }
                            checked.add(position);

                        }
                    }
                }

                isScanning = false;

            }, "ShadyAddons-GemstoneScanner").start();
        }
    }

    @SubscribeEvent
    public void onBlockChange(BlockChangeEvent event) {
        if(event.newBlock.getBlock() == Blocks.air) {
            gemstones.remove(event.position);
        }
    }

    @SubscribeEvent
    public void onRenderWorld(RenderWorldLastEvent event) {
        if(isEnabled()) {
            for(Map.Entry<BlockPos, Gemstone> gemstone : gemstones.entrySet()) {
                if(!isGemstoneEnabled(gemstone.getValue())) continue;
                double distanceSq = gemstone.getKey().distanceSq(Shady.mc.thePlayer.posX, Shady.mc.thePlayer.posY, Shady.mc.thePlayer.posZ);
                if(distanceSq > Math.pow(PolyfrostConfig.GEM_ESP_SCAN_RADIUS + 2, 2)) continue;

                if(PolyfrostConfig.GEM_ESP_HIGHLIGHT_MODE == 0) { // Outlined
                    RenderUtils.outlineBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks);
                } else { // Filled
                    RenderUtils.highlightBlock(gemstone.getKey(), gemstone.getValue().color, event.partialTicks);
                }
            }
        }
    }

    private static boolean isEnabled() {
        return Shady.mc.thePlayer != null &&
                Shady.mc.theWorld != null &&
                isAnyGemstoneEspEnabled() &&
                Utils.inSkyBlock &&
                (LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS) || LocationUtils.onIsland(LocationUtils.Island.DWARVEN_MINES));
    }

    private static Gemstone getGemstone(IBlockState block) {
        if(block.getBlock() != Blocks.stained_glass && block.getBlock() != Blocks.stained_glass_pane) return null;

        EnumDyeColor color = Utils.firstNotNull(block.getValue(BlockStainedGlass.COLOR), block.getValue(BlockStainedGlassPane.COLOR));

        if(color == Gemstone.RUBY.dyeColor) return Gemstone.RUBY;
        if(color == Gemstone.AMETHYST.dyeColor) return Gemstone.AMETHYST;
        if(color == Gemstone.JADE.dyeColor) return Gemstone.JADE;
        if(color == Gemstone.SAPPHIRE.dyeColor) return Gemstone.SAPPHIRE;
        if(color == Gemstone.AMBER.dyeColor) return Gemstone.AMBER;
        if(color == Gemstone.TOPAZ.dyeColor) return Gemstone.TOPAZ;
        if(color == Gemstone.JASPER.dyeColor) return Gemstone.JASPER;

        if(color == Gemstone.ONYX.dyeColor) return Gemstone.ONYX;
        if(color == Gemstone.PERIDOT.dyeColor) return Gemstone.PERIDOT;
        if(color == Gemstone.AQUAMARINE.dyeColor) return Gemstone.AQUAMARINE;
        if(color == Gemstone.CITRINE.dyeColor) return Gemstone.CITRINE;

        return null;
    }

    private static boolean isGemstoneEnabled(Gemstone gemstone) {
        if(PolyfrostConfig.GEM_ESP_GLASS_PANES) {
            switch(gemstone) {
                case RUBY_SHARD:
                    return PolyfrostConfig.GEM_ESP_RUBY;
                case AMETHYST_SHARD:
                    return PolyfrostConfig.GEM_ESP_AMETHYST;
                case JADE_SHARD:
                    return PolyfrostConfig.GEM_ESP_JADE;
                case SAPPHIRE_SHARD:
                    return PolyfrostConfig.GEM_ESP_SAPPHIRE;
                case AMBER_SHARD:
                    return PolyfrostConfig.GEM_ESP_AMBER;
                case TOPAZ_SHARD:
                    return PolyfrostConfig.GEM_ESP_TOPAZ;
                case JASPER_SHARD:
                    return PolyfrostConfig.GEM_ESP_JASPER;

                case ONYX_SHARD:
                    return PolyfrostConfig.GEM_ESP_ONYX;
                case PERIDOT_SHARD:
                    return PolyfrostConfig.GEM_ESP_PERIDOT;
                case AQUAMARINE_SHARD:
                    return PolyfrostConfig.GEM_ESP_AQUAMARINE;
                case CITRINE_SHARD:
                    return GEM_ESP_CITRINE;
            }
        }

        switch(gemstone) {
            case RUBY:
                return PolyfrostConfig.GEM_ESP_RUBY;
            case AMETHYST:
                return PolyfrostConfig.GEM_ESP_AMETHYST;
            case JADE:
                return PolyfrostConfig.GEM_ESP_JADE;
            case SAPPHIRE:
                return PolyfrostConfig.GEM_ESP_SAPPHIRE;
            case AMBER:
                return PolyfrostConfig.GEM_ESP_AMBER;
            case TOPAZ:
                return PolyfrostConfig.GEM_ESP_TOPAZ;
            case JASPER:
                return PolyfrostConfig.GEM_ESP_JASPER;
            case ONYX:
                return PolyfrostConfig.GEM_ESP_ONYX;
            case PERIDOT:
                return PolyfrostConfig.GEM_ESP_PERIDOT;
            case AQUAMARINE:
                return PolyfrostConfig.GEM_ESP_AQUAMARINE;
            case CITRINE:
                return GEM_ESP_CITRINE;
            default:
                return false;
        }
    }

    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
        gemstones.clear();
        checked.clear();
        lastChecked = null;
    }

    public static boolean isAnyGemstoneEspEnabled() {
        return GEM_ESP_RUBY || GEM_ESP_AMBER || GEM_ESP_SAPPHIRE || GEM_ESP_JADE || GEM_ESP_AMETHYST || GEM_ESP_TOPAZ || GEM_ESP_JASPER || GEM_ESP_ONYX || GEM_ESP_PERIDOT || GEM_ESP_AQUAMARINE || GEM_ESP_CITRINE;
    }


}

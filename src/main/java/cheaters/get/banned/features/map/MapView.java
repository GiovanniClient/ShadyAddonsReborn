package cheaters.get.banned.features.map;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.map.elements.MapTile;
import cheaters.get.banned.features.map.elements.doors.DoorTile;
import cheaters.get.banned.features.map.elements.rooms.RoomTile;
import cheaters.get.banned.features.map.elements.rooms.RoomType;
import cheaters.get.banned.features.map.elements.rooms.Separator;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.FontUtils;
import cheaters.get.banned.utils.RenderUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.ArrayList;

// Hey future self, I've over-documented this because rendering code can become confusing
public class MapView {

    public static int tileSize = 8;
    public static final int maxMapPx = tileSize * 23; // Max width/height in pixels
    public static final int maxMapBlocks = 197; // Max width/heigh in blocks
    private static final int borderSize = 3;
    private static ArrayList<String> roomNamesDrawn = new ArrayList<>();

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent event) {
        if(event.type != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        if(MapController.scannedMap == null || !Utils.inDungeon || !PolyfrostConfig.MAP_DUNG_TOGGLE || DungeonUtils.inBoss) return;

        // Scaling
        float scale = PolyfrostConfig.MAP_DUNG_SCALE / 100f;
        GlStateManager.pushMatrix();
        GlStateManager.scale(scale, scale, scale);

        // Positioning
        GlStateManager.translate(
                PolyfrostConfig.MAP_DUNG_X_OFFSET + (PolyfrostConfig.MAP_DUNG_BORDER_COLOR > 0 ? borderSize : 0),
                PolyfrostConfig.MAP_DUNG_Y_OFFSET + (PolyfrostConfig.MAP_DUNG_BORDER_COLOR > 0 ? borderSize : 0),
                0
        );

        // Draw Map Background
        Gui.drawRect(
                0,
                0,
                maxMapPx + tileSize * 2,
                maxMapPx + tileSize * 2 + (PolyfrostConfig.MAP_DUNG_SHOW_INFO ? 30 : 0),
                Utils.addAlphaPct(Color.BLACK, PolyfrostConfig.MAP_DUNG_BG_OPACITY / 100f).getRGB()
        );

        // Draw Map Border
        if(PolyfrostConfig.MAP_DUNG_BORDER_COLOR > 0) {
            int borderColor = new int[]{
                    Color.TRANSLUCENT,
                    getChroma(),
                    Color.BLACK.getRGB(),
                    Color.WHITE.getRGB()
            }[PolyfrostConfig.MAP_DUNG_BORDER_COLOR];

            int mapWidth = maxMapPx + tileSize * 2;
            int mapHeight = mapWidth + (PolyfrostConfig.MAP_DUNG_SHOW_INFO ? 30 : 0);

            // Top Border
            Gui.drawRect(
                    -borderSize,
                    -borderSize,
                    mapWidth + borderSize,
                    0,
                    borderColor
            );

            // Left Border
            Gui.drawRect(
                    -borderSize,
                    -borderSize,
                    0,
                    mapHeight + borderSize,
                    borderColor
            );

            // Bottom Border
            Gui.drawRect(
                    -borderSize,
                    mapHeight,
                    mapWidth + borderSize,
                    mapHeight + borderSize,
                    borderColor
            );

            // Right Border
            Gui.drawRect(
                    mapWidth,
                    -borderSize,
                    mapWidth + borderSize,
                    mapHeight + borderSize,
                    borderColor
            );
        }

        // Draw Run Info
        if(PolyfrostConfig.MAP_DUNG_SHOW_INFO) {
            FontUtils.drawCenteredString(
                    String.format(
                            "Secrets: §a%d§7/%d §f   Crypts: §a%d\n§fDeaths: §%c%d §f   Score: §%c%d",
                            DungeonUtils.secretsFound,
                            MapController.scannedMap.totalSecrets,
                            DungeonUtils.cryptsFound,
                            DungeonUtils.deaths > 4 ? 'c' : 'a',
                            DungeonUtils.deaths,
                            getColorCode(DungeonUtils.score, 269, 300),
                            DungeonUtils.score
                    ),
                    maxMapPx / 2 + 10,
                    maxMapPx + 10 + 15
            );
        }

        // Fix Transparency
        GlStateManager.enableAlpha();

        // Padding
        GlStateManager.translate(tileSize, tileSize, 0);

        // Draw Rooms & Doors
        MapTile[][] elements = MapController.scannedMap.elements;
        for(int rowNum = 0; rowNum < elements.length; rowNum++) {
            MapTile[] row = elements[rowNum];

            for(int colNum = 0; colNum < row.length; colNum++) {
                MapTile tile = row[colNum];
                if(tile == null) continue;

                // (number of rooms * room size) + (number of doors/seperators * door size)
                int x = (int) ((Math.ceil(colNum / 2f) * tileSize * 3) + (Math.floor(colNum / 2f) * tileSize));
                int y = (int) ((Math.ceil(rowNum / 2f) * tileSize * 3) + (Math.floor(rowNum / 2f) * tileSize));

                boolean colEven = colNum % 2 == 0;
                boolean rowEven = rowNum % 2 == 0;

                // Draw Rooms
                if(colEven && rowEven) { // Rooms are only in even columns and rows, draw them
                    Gui.drawRect(
                            x,
                            y,
                            x + tileSize*3,
                            y + tileSize*3,
                            tile.color
                    );
                    if(!drawCheckmark((RoomTile) tile, x, y)) {
                        drawRoomName((RoomTile) tile, x, y);
                    }
                // Draw Doors
                } else if(tile instanceof DoorTile) {
                    /*
                     * Even column and odd row mean that it must be horizontal because rooms
                     * are in even columns and vertical doors and separators are in odd rows.
                     * Horizontal separators and doors are in even columns and odd rows. Does
                     * that make sense? Try drawing a diagram.
                     */
                    if(colEven) { // Horizontal doors
                        Gui.drawRect(
                                x + tileSize, // Center door (rooms are 3x3, doors are 1x1 on the side)
                                y,
                                x + tileSize * 2, // Again, centering
                                y + tileSize,
                                tile.color
                        );
                    } else { // Vertical doors
                        Gui.drawRect(
                                x,
                                y + tileSize, // More centering, the same thing but vertically
                                x + tileSize,
                                y + tileSize * 2,
                                tile.color
                        );
                    }
                // Draw Separators
                } else if(tile instanceof Separator) { // Render separators
                    if(colEven) { // Horizontal separators (see note above)
                        Gui.drawRect(
                                x,
                                y,
                                x + tileSize * 3, // 3x1 rectangle
                                y + tileSize,
                                tile.color
                        );
                    } else if(rowEven) { // Vertical separators
                        Gui.drawRect(
                                x,
                                y,
                                x + tileSize,
                                y  + tileSize * 3,  // 1x3 rectangle
                                tile.color
                        );
                    } else { // Small square seperator (fills hole in 2x2 rooms)
                        Gui.drawRect(
                                x,
                                y,
                                x + tileSize,
                                y + tileSize,
                                tile.color
                        );
                    }
                }
            }
        }

        // Draw Player Heads
        if(PolyfrostConfig.MAP_DUNG_SHOW_HEADS > 0) {
            int headSize = 14;
            for(EntityPlayer teammate : DungeonUtils.teammates) {
                if(PolyfrostConfig.MAP_DUNG_SHOW_HEADS == 2 && teammate != Shady.mc.thePlayer || teammate.isDead) continue; // Only render own head

                int playerX = (int) ((teammate.getPosition().getX() - MapScanner.xCorner) / (float) maxMapBlocks * maxMapPx);
                int playerZ = (int) ((teammate.getPosition().getZ() - MapScanner.zCorner) / (float) maxMapBlocks * maxMapPx);
                int playerRotation = (int) (teammate.getRotationYawHead() - 180);

                drawPlayerIcon(teammate, headSize, playerX-headSize/2, playerZ-headSize/2, playerRotation);
            }
        }

        GlStateManager.translate(-tileSize, -tileSize, 0); // Reset Padding
        GlStateManager.translate(
                -PolyfrostConfig.MAP_DUNG_X_OFFSET - (PolyfrostConfig.MAP_DUNG_BORDER_COLOR > 0 ? borderSize : 0),
                -PolyfrostConfig.MAP_DUNG_Y_OFFSET - (PolyfrostConfig.MAP_DUNG_BORDER_COLOR > 0 ? borderSize : 0),
                0
        ); // Reset Positioning
        GlStateManager.popMatrix(); // Reset Scaling

        roomNamesDrawn.clear();
    }

    private static void drawPlayerIcon(EntityPlayer player, int size, int x, int y, int angle) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(x+size/2f, y+size/2f, 0);
        GlStateManager.rotate(angle, 0, 0, 1);
        GlStateManager.translate(-x-size/2f, -y-size/2f, 0);

        Gui.drawRect(x, y, x+size, y+size, Color.BLACK.getRGB());
        GlStateManager.color(255, 255, 255);

        GlStateManager.translate(0, 0, 200);
        RenderUtils.drawPlayerIcon(player, size-2, x+1, y+1);
        GlStateManager.translate(0, 0, -200);

        GlStateManager.popMatrix();
    }

    private static final ResourceLocation icon_cross = new ResourceLocation("shadyaddons:dungeonscanner/cross.png");
    private static final ResourceLocation icon_whiteCheck = new ResourceLocation("shadyaddons:dungeonscanner/white_check.png");
    private static final ResourceLocation icon_check = new ResourceLocation("shadyaddons:dungeonscanner/check.png");

    private static boolean drawCheckmark(RoomTile room, int x, int y) {
        ResourceLocation resourceLocation;

        switch(room.status) {
            case FAILED:
                resourceLocation = icon_cross;
                break;

            case CLEARED:
                resourceLocation = icon_whiteCheck;
                break;

            case GREEN:
                resourceLocation = icon_check;
                break;

            default:
                return false;
        }

        RenderUtils.drawTexture(
                resourceLocation,
                x + (int) (tileSize * 0.75),
                y + (int) (tileSize * 0.75),
                (int) (tileSize * 1.5),
                (int) (tileSize * 1.5)
        );

        return true;
    }

    private static void drawRoomName(RoomTile roomTile, int x, int y) {
        if(PolyfrostConfig.MAP_DUNG_ROOM_NAMES == 0) return;

        String name = null;

        if(PolyfrostConfig.MAP_DUNG_ROOM_NAMES == 1) { // Important
            if(roomTile.room.type == RoomType.YELLOW || roomTile.room.type == RoomType.PUZZLE || roomTile.room.type == RoomType.TRAP) {
                name = RoomLists.shortNames.get(roomTile.room.name);
                if(name == null) name = roomTile.room.name.replace(" ", "\n");
            }
        } else if(PolyfrostConfig.MAP_DUNG_ROOM_NAMES == 2) { // All
            if(roomNamesDrawn.contains(roomTile.room.name)) return;
            name = roomTile.room.name.replace(" ", "\n");
            roomNamesDrawn.add(roomTile.room.name);
        }

        if(name == null) return;

        GlStateManager.translate(0, 0, 100);

        FontUtils.drawCenteredString(
                name,
                (int) (x + tileSize*1.5),
                (int) (y + tileSize*1.5)
        );

        GlStateManager.translate(0, 0, -100);
    }

    /**
     * Gets a color code based on a number and two thresholds
     *
     * @param value The value to test
     * @param orangeAfter The threshold for orange
     * @param greenAfter The threshold for green
     * @return The matching color code
     */
    private static char getColorCode(int value, int orangeAfter, int greenAfter) {
        if(value < orangeAfter) return 'c'; // Return red
        if(value < greenAfter) return '6'; // Return orange
        return 'a'; // Return green
    }

    /**
     * Gets a rainbow color value based on the current time
     */
    private static int getChroma() {
        float hue = (System.currentTimeMillis() % 3000) / 3000f;
        return Color.getHSBColor(hue, 0.75f, 1f).getRGB();
    }

}
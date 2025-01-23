package cheaters.get.banned.features;

import cheaters.get.banned.events.RenderEntityModelEvent;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.gui.config.settings.FolderSetting;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.OutlineUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class MobESP {

    private static HashMap<Entity, Color> highlightedEntities = new HashMap<>();
    private static HashSet<Entity> checkedStarNameTags = new HashSet<>();

    private static void highlightEntity(Entity entity, Color color) {
        highlightedEntities.put(entity, color);
    }

    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(Utils.inDungeon) {
            if(Config.minibossEsp && event.entity instanceof EntityPlayer) {
                String name = event.entity.getName();
                switch(name) {
                    case "Shadow Assassin":
                        event.entity.setInvisible(false);
                        highlightEntity(event.entity, Color.MAGENTA);
                        break;

                    case "Lost Adventurer":
                        highlightEntity(event.entity, Color.BLUE);
                        break;

                    case "Diamond Guy":
                        highlightEntity(event.entity, Color.CYAN);
                        break;
                }
            }

            if(Config.secretBatEsp && event.entity instanceof EntityBat) {
                highlightEntity(event.entity, Color.RED);
            }
        }

        if(Utils.inSkyBlock && LocationUtils.onIsland(LocationUtils.Island.CRYSTAL_HOLLOWS)) {
            if(Config.sludgeEsp) {
                if(event.entity instanceof EntitySlime && !(event.entity instanceof EntityMagmaCube)) {
                    highlightEntity(event.entity, Color.GREEN);
                }
            }

            if(Config.yogEsp) {
                if(event.entity instanceof EntityMagmaCube) {
                    highlightEntity(event.entity, Color.RED);
                }
            }

            if(Config.corleoneEsp) {
                if(event.entity instanceof EntityOtherPlayerMP && event.entity.getName().equals("Team Treasurite")) {
                    float health = ((EntityOtherPlayerMP) event.entity).getMaxHealth();
                    if(health == 1_000_000 || health == 2_000_000) {
                        highlightEntity(event.entity, Color.PINK);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderEntityModel(RenderEntityModelEvent event) {
        if(Config.glaciteCorpses && LocationUtils.onIsland(LocationUtils.Island.GLACITE_MINESHAFTS)) {
            if(event.entity instanceof EntityArmorStand) {
                EntityArmorStand armorStand = (EntityArmorStand) event.entity;
                if (armorStand.getShowArms()) {
                    highlightEntity(event.entity, Color.MAGENTA);
                }
                // i wanted to change the color based on the corpse type
                // (by checking their outfit) tho i think i'm too skill issued to pull it off
            }
        }
        if(Config.glaciteCorpses && !LocationUtils.onIsland(LocationUtils.Island.GLACITE_MINESHAFTS)) {
            highlightedEntities.clear(); // for some reason, onWorldLoad isn't always clearing it, so...
        }


        if(Utils.inDungeon && !checkedStarNameTags.contains(event.entity) && Config.starredMobEsp) {
            if(event.entity instanceof EntityArmorStand) {
                if(event.entity.hasCustomName() && event.entity.getCustomNameTag().contains("✯")) {
                    List<Entity> possibleEntities = event.entity.getEntityWorld().getEntitiesInAABBexcluding(event.entity, event.entity.getEntityBoundingBox().expand(0, 3, 0), entity -> !(entity instanceof EntityArmorStand));
                    if(!possibleEntities.isEmpty()) {
                        highlightEntity(possibleEntities.get(0), Color.ORANGE);
                    }
                    checkedStarNameTags.add(event.entity);
                }
            }
        }

        if(FolderSetting.isEnabled("Mob ESP") && !highlightedEntities.isEmpty() && highlightedEntities.containsKey(event.entity)) {
            OutlineUtils.outlineEntity(event, highlightedEntities.get(event.entity));
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        highlightedEntities.clear();
        checkedStarNameTags.clear();
    }

}

package cheaters.get.banned.features;

import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.utils.LocationUtils;
import cheaters.get.banned.utils.ScoreboardUtils;
import cheaters.get.banned.utils.Utils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ShowHiddenEntities {

    @SubscribeEvent
    public void onBeforeRenderEntity(RenderLivingEvent.Pre<EntityLivingBase> event) {
        if(event.entity.isInvisible()) {
            if(PolyfrostConfig.SHOW_HIDDEN_FELS && event.entity instanceof EntityEnderman) {
                event.entity.setInvisible(false);
            }

            if(Utils.inDungeon && event.entity instanceof EntityPlayer) {
                if(PolyfrostConfig.SHOW_HIDDEN_SHADOW_ASS && event.entity.getName().contains("Shadow Assassin")) {
                    event.entity.setInvisible(false);
                }

                if(PolyfrostConfig.SHOW_STEALTHY_BLOOD_MOBS) {
                    for(String name : new String[]{"Revoker", "Psycho", "Reaper", "Cannibal", "Mute", "Ooze", "Putrid", "Freak", "Leech", "Tear", "Parasite", "Flamer", "Skull", "Mr. Dead", "Vader", "Frost", "Walker", "Wandering Soul", "Bonzo", "Scarf", "Livid"}) {
                        if(event.entity.getName().contains(name)) {
                            event.entity.setInvisible(false);
                            break;
                        }
                    }
                }
            }

            if(PolyfrostConfig.SHOW_HIDDEN_GHOSTS && event.entity instanceof EntityCreeper && ScoreboardUtils.scoreboardContains("The Mist")) {
                event.entity.setInvisible(false);
            }

            if(PolyfrostConfig.SHOW_SNEAKY_CREEPERS && event.entity instanceof EntityCreeper && LocationUtils.onIsland(LocationUtils.Island.DEEP_CAVERNS)) {
                event.entity.setInvisible(false);
            }
        }
    }

}

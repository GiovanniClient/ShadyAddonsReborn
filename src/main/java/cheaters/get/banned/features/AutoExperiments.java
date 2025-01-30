package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class AutoExperiments {

    private enum ExperimentType {
        CHRONOMATRON,
        ULTRASEQUENCER,
        SUPERPAIRS,
        NONE
    }

    private ExperimentType currentExperiment = ExperimentType.NONE;
    private boolean sequenceAdded = false;
    private int clicks = 0;
    private long lastClickTime = 0;
    private final ArrayList<Integer> chronomatronOrder = new ArrayList<>(28);
    private int lastAdded = 0;
    private final HashMap<Integer, Integer> ultrasequencerOrder = new HashMap<>();
    private int startCooldown = 0; //10-30 ticks cooldown
    private int endCooldown = 0;


    public AutoExperiments() {
        System.out.println("AutoExperiments CREATO");
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        currentExperiment = ExperimentType.NONE;
        sequenceAdded = false;
        chronomatronOrder.clear();
        lastAdded = 0;
        ultrasequencerOrder.clear();

        //if (!Utils.inSkyBlock || event.gui instanceof GuiChest) return;

        String chestName = Utils.getGuiName(event.gui);

        if (chestName.startsWith("Chronomatron (")) {
            currentExperiment = ExperimentType.CHRONOMATRON;
        } else if (chestName.startsWith("Ultrasequencer (")) {
            currentExperiment = ExperimentType.ULTRASEQUENCER;
        } else if (chestName.startsWith("Superpairs(")) {
            currentExperiment = ExperimentType.SUPERPAIRS;
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (currentExperiment == ExperimentType.NONE || !PolyfrostConfig.autoExperiments || Shady.mc.thePlayer == null) return;

        if (startCooldown == 0)
            endCooldown = new Random().nextInt(10) + 5;
        if (startCooldown < endCooldown) {
            startCooldown++;
            return;
        }

        ContainerChest container = (ContainerChest) Shady.mc.thePlayer.openContainer;

        switch (currentExperiment) {
            case CHRONOMATRON:
                if (container.getSlot(49).getStack().getItem().getUnlocalizedName().toUpperCase().contains("LIGHTGEM") &&
                        !container.getSlot(lastAdded).getStack().isItemEnchanted()) {
                    sequenceAdded = false;
                    if (chronomatronOrder.size() > 8) {
                        startCooldown = 0;
                        Shady.mc.thePlayer.closeScreen();
                    }
                }

                if (!sequenceAdded && container.getSlot(49).getStack().getItem() == Items.clock) {
                    for (int i = 10; i <= 43; i++) {
                        if (container.getSlot(i).getStack().isItemEnchanted()) {
                            chronomatronOrder.add(i);
                            lastAdded = i;
                            sequenceAdded = true;
                            clicks = 0;
                            lastClickTime = System.currentTimeMillis();
                            break;
                        }
                    }
                }
                if (sequenceAdded && container.getSlot(49).getStack().getItem() == Items.clock &&
                        chronomatronOrder.size() > clicks &&
                        System.currentTimeMillis() - lastClickTime > new Random().nextInt(550) + 360) {
                    clickSlot(chronomatronOrder.get(clicks));
                    lastClickTime = System.currentTimeMillis();
                    clicks++;
                }
                break;

            case ULTRASEQUENCER:
                if (container.getSlot(49).getStack().getItem() == Items.clock) {
                    sequenceAdded = false;
                }

                if (!sequenceAdded && container.getSlot(49).getStack().getItem().getUnlocalizedName().toUpperCase().contains("LIGHTGEM")) {
                    if (!container.getSlot(44).getHasStack()) return;

                    ultrasequencerOrder.clear();

                    for (int slot = 9; slot <= 44; slot++) {
                        if (container.getSlot(slot).getStack().getItem() == Items.dye) {
                            ultrasequencerOrder.put(/*ordine*/container.getSlot(slot).getStack().stackSize - 1, /*slot*/slot);
                        }
                    }
                    sequenceAdded = true;
                    clicks = 0;
                    lastClickTime = System.currentTimeMillis();

                    if (ultrasequencerOrder.size() > 6) {
                        startCooldown = 0;
                        Shady.mc.thePlayer.closeScreen();
                    }
                }

                if (container.getSlot(49).getStack().getItem() == Items.clock &&
                        ultrasequencerOrder.containsKey(clicks) &&
                        System.currentTimeMillis() - lastClickTime > new Random().nextInt(550) + 360) {
                    Integer slotNumber = ultrasequencerOrder.get(clicks);
                    if (slotNumber != null) {
                        clickSlot(slotNumber);
                        lastClickTime = System.currentTimeMillis();
                        clicks++;
                    }
                }
                break;

            default:
                return;
        }
    }

    private void clickSlot(int slot) {
        Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, slot, 0, 3, Shady.mc.thePlayer);
    }
}

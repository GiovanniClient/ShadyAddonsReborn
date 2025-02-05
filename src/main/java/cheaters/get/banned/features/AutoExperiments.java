package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.utils.Utils;
import net.minecraft.init.Items;
import net.minecraft.inventory.ContainerChest;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static cheaters.get.banned.gui.polyconfig.PolyfrostConfig.*;

public class AutoExperiments {

    private enum ExperimentType {
        CHRONOMATRON,
        ULTRASEQUENCER,
        SUPERPAIRS,
        END,
        NONE
    }

    // You may ask me, what are these numbers based on? You see, that's a great question, basically
    private final int START_DELAY_MIN = 234; // ms
    private final int START_DELAY_MAX = 678;

    private final int END_DELAY_MIN = 777;
    private final int END_DELAY_MAX = 3333;

    private ExperimentType currentExperiment = ExperimentType.NONE;
    private final ArrayList<Integer> chronomatronOrder = new ArrayList<>(28);
    private final HashMap<Integer, Integer> ultrasequencerOrder = new HashMap<>();

    private int lastAdded = 0, clicks = 0;
    private long startDelay = -1, endDelay = -1, clickDelay = -1;
    private boolean sequenceAdded = false;

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if (AE_CLICK_DELAY_MIN > AE_CLICK_DELAY_MAX) {
            Utils.out("Auto-Experiments: You've set MIN delay > MAX delay, ignoring your values.");
            AE_CLICK_DELAY_MIN = 300;
            AE_CLICK_DELAY_MAX = 800;
            return;
        }

        //if (!Utils.inSkyBlock || event.gui instanceof GuiChest) return;

        String chestName = Utils.getGuiName(event.gui);

        if (chestName.startsWith("Chronomatron (")) {
            Utils.debug("Chronomatron detected");
            currentExperiment = ExperimentType.CHRONOMATRON;
        } else if (chestName.startsWith("Ultrasequencer (")) {
            Utils.debug("Ultrasequencer detected");
            currentExperiment = ExperimentType.ULTRASEQUENCER;
        } else if (chestName.startsWith("Superpairs(")) {
            Utils.debug("Superpairs detected");
            currentExperiment = ExperimentType.SUPERPAIRS;
        } else if (chestName.contains("Over")) {
            Utils.debug("Experiment over.");
            currentExperiment = ExperimentType.END;
        } else clear();

    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if (currentExperiment == ExperimentType.NONE || !PolyfrostConfig.AUTO_EXPERIMENTS || Shady.mc.thePlayer == null) return;

        ContainerChest container = (ContainerChest) Shady.mc.thePlayer.openContainer;

        long rightNow = System.currentTimeMillis();

        // Small random startup delay (200-600ms)
        if (startDelay == -1) {
            startDelay = rightNow + new Random().nextInt(START_DELAY_MAX - START_DELAY_MIN) + START_DELAY_MIN;
            Utils.debug("Start delay: " + (startDelay - rightNow));
        }

        if (rightNow < startDelay) return;

        switch (currentExperiment) {
            // exits the experiment on a chain of 9
            case CHRONOMATRON:
                // LIGHTGEM is supposed to be glowstone btw. Wtf notch?
                if (container.getSlot(49).getStack().getItem().getUnlocalizedName().toUpperCase().contains("LIGHTGEM") &&
                        !container.getSlot(lastAdded).getStack().isItemEnchanted()) {
                    sequenceAdded = false;
                    if (chronomatronOrder.size() > (11 - AE_METAPHYSICAL_SERUM)) {
                        Shady.mc.thePlayer.closeScreen();
                    }
                }

                // saves the sequence
                if (!sequenceAdded && container.getSlot(49).getStack().getItem() == Items.clock) {
                    Utils.debug("salviamo");
                    for (int i = 10; i <= 43; i++) {
                        if (container.getSlot(i).getStack() != null && container.getSlot(i).getStack().isItemEnchanted()) {
                            chronomatronOrder.add(i);
                            Utils.debug("aggiungo " + i);
                            lastAdded = i;
                            sequenceAdded = true;
                            clicks = 0;
                            break;
                        }
                    }
                }

                // clicks through the saved sequence, also has random delays
                if (sequenceAdded && container.getSlot(49).getStack().getItem() == Items.clock &&
                        chronomatronOrder.size() > clicks) {

                    if (clickDelay == -1) {
                        clickDelay =  rightNow + new Random().nextInt(AE_CLICK_DELAY_MAX - AE_CLICK_DELAY_MIN) + AE_CLICK_DELAY_MIN;
                        Utils.debug("Note n" + (clicks+1) + ", Click delay: " + (clickDelay-rightNow) + "ms");
                    }

                    if (rightNow > clickDelay) {
                        clickSlot(chronomatronOrder.get(clicks));
                        clicks++;
                        clickDelay = -1;
                    }
                }
                break;

            case ULTRASEQUENCER:
                // check to see if we're supposed to click or save the sequence
                if (container.getSlot(49).getStack().getItem() == Items.clock)
                    sequenceAdded = false;

                // saves the sequence and exists if we're done
                if (!sequenceAdded && container.getSlot(49).getStack().getItem().getUnlocalizedName().toUpperCase().contains("LIGHTGEM")) {
                    if (!container.getSlot(44).getHasStack()) return;

                    ultrasequencerOrder.clear();

                    for (int slot = 9; slot <= 44; slot++)
                        if (container.getSlot(slot).getStack().getItem() == Items.dye)
                            ultrasequencerOrder.put(container.getSlot(slot).getStack().stackSize - 1, slot);

                    sequenceAdded = true;
                    clicks = 0;
                }

                if (container.getSlot(49).getStack().getItem() == Items.clock &&
                        ultrasequencerOrder.containsKey(clicks)) {

                    if (clickDelay == -1) {
                        clickDelay =  rightNow + new Random().nextInt(AE_CLICK_DELAY_MAX - AE_CLICK_DELAY_MIN) + AE_CLICK_DELAY_MIN;
                        Utils.debug("Note n" + (clicks+1) + ", Click delay: " + (clickDelay-rightNow) + "ms");
                    }

                    if (rightNow > clickDelay) {
                        // exits once we're done
                        if (ultrasequencerOrder.size() > (9 - AE_METAPHYSICAL_SERUM))
                            Shady.mc.thePlayer.closeScreen();

                        Integer slotNumber = ultrasequencerOrder.get(clicks);
                        if (slotNumber != null) {
                            clickSlot(slotNumber);
                            clicks++;
                            clickDelay = -1;
                        }
                    }
                }
                break;

            case END:
                if (endDelay == -1) {
                    endDelay =  rightNow + new Random().nextInt(END_DELAY_MAX - END_DELAY_MIN) + END_DELAY_MIN;
                    Utils.debug("End delay: " + (endDelay-rightNow) + "ms");
                }

                if (rightNow > endDelay && AE_AUTO_QUIT) {
                    // if (container.getSlot(11).getStack().getItem() == Items.skull) {
                    Shady.mc.thePlayer.closeScreen();
                    endDelay = -1;
                    currentExperiment = ExperimentType.NONE;
                }
                break;

            default:
        }
    }


    private void clickSlot(int slot) {
        Utils.debug("clicking slot " + slot);
        Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId, slot, AE_CLICK_BUTTON, AE_CLICK_MODE, Shady.mc.thePlayer);
    }

    private void clear() {
        currentExperiment = ExperimentType.NONE;
        chronomatronOrder.clear();
        ultrasequencerOrder.clear();
        sequenceAdded = false;
        lastAdded = 0;
        clickDelay = -1;
        endDelay = -1;
        startDelay = -1;
    }
}

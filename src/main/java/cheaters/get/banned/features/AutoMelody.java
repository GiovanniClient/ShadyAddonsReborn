package cheaters.get.banned.features;

import cheaters.get.banned.Shady;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.utils.Utils;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;

public class AutoMelody {

    private boolean inHarp = false;
    private ArrayList<Item> lastInventory = new ArrayList<>();

    private int counter = 0;
    private int last_clicked_slot = -1;


    public AutoMelody() {
        System.out.println("AutoMelody CREATO");
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui instanceof GuiChest && Utils.inSkyBlock ) { // && Config.autoMelody
            if(Utils.getGuiName(event.gui).startsWith("Harp -")) {
                lastInventory.clear();
                inHarp = true;
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        counter++;
        if (counter % 2 == 0) return;
        if(!inHarp || !PolyfrostConfig.AUTO_MELODY || Shady.mc.thePlayer == null) return;
        String inventoryName = Utils.getInventoryName();
        if(inventoryName == null || !inventoryName.startsWith("Harp -")) inHarp = false;

        ArrayList<Item> thisInventory = new ArrayList<>();
        for(Slot slot : Shady.mc.thePlayer.openContainer.inventorySlots) {
            if(slot.getStack() != null) thisInventory.add(slot.getStack().getItem());
        }

        counter++;

        if(!lastInventory.toString().equals(thisInventory.toString())) {
            for(Slot slot : Shady.mc.thePlayer.openContainer.inventorySlots) {
                if(slot.getStack() != null && slot.getStack().getItem() instanceof ItemBlock
                        && ((ItemBlock) slot.getStack().getItem()).getBlock() == Blocks.quartz_block) {

                    Shady.mc.playerController.windowClick(Shady.mc.thePlayer.openContainer.windowId,
                            slot.slotNumber, 0, 3, Shady.mc.thePlayer);
                    last_clicked_slot = slot.slotNumber;
                    break;
                }
            }
        }

        lastInventory.clear();
        lastInventory.addAll(thisInventory);
    }

}

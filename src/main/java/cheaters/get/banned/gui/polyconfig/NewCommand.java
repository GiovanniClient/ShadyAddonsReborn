package cheaters.get.banned.gui.polyconfig;

import cc.polyfrost.oneconfig.utils.commands.annotations.*;
import cheaters.get.banned.Shady;
import cheaters.get.banned.features.AutoWardrobe;
import cheaters.get.banned.features.commandpalette.CommandPalette;
import cheaters.get.banned.features.map.MapController;
import cheaters.get.banned.features.map.MapScanner;
import cheaters.get.banned.features.routines.Routine;
import cheaters.get.banned.features.routines.Routines;
import cheaters.get.banned.stats.RoutinesAPI;
import cheaters.get.banned.utils.DungeonUtils;
import cheaters.get.banned.utils.EstonianUtils;
import cheaters.get.banned.utils.Utils;
import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.io.IOException;

/**
 * An example command implementing the Command api of OneConfig.
 * Registered in ExampleMod.java with `CommandManager.INSTANCE.registerCommand(new ExampleCommand());`
 *
 * @see Command
 * @see Main
 */
@Command(value = Shady.MOD_ID, description = "Access the " + Shady.MOD_NAME + " GUI.",
        aliases = {"sh", "shr", "shady", "shadyreborn", "shadyaddonsreborn", "gio", "giova", "gcl", "giovanni", "giovanniclient"})
public class NewCommand {
    private static final String UNKNOWN_COMMAND = "Unrecognized command!";
    private static final String INVALID_ARGUMENTS = "Invalid arguments!";

    @Main
    private void handle() {
        Shady.polyfrostConfig.openGui();
    }

    @SubCommand
    private void install(String id) {
        if (id != null && !id.isEmpty())
            RoutinesAPI.download(id);
        else Utils.out("No routine ID specified");
    }

    @SubCommand
    private void routines() {
        try {
            Desktop.getDesktop().open(Routines.routinesDir);
        } catch(IOException e) {
            Utils.out("Unable to open directory");
            Utils.out("Find it manually at .../minecraft/config/shady/routines");
        }
    }

    // spent 45mins on this, and I'm pretty sure it's now useless
    @SubCommand(aliases = {"wd"})
    private void wardrobe(@Greedy String input) {
        if(!Utils.inSkyBlock) {
            Utils.out("You must be in SkyBlock to use this command!");
            return;
        }

        if (input == null || input.isEmpty()) {
            AutoWardrobe.open(1, 0);
            return;
        }

        int slot, page;

        if (!input.contains(" ")) { // like /shady wd 1
            try {
                slot = Integer.parseInt(input);
            } catch (Exception e) {
                Utils.out(INVALID_ARGUMENTS);
                return;
            }
            if(slot > 0 && slot <= 9)
                AutoWardrobe.open(1, slot);
            else if(slot < 18)
                AutoWardrobe.open(2, slot % 9);
            else if(slot == 18)
                AutoWardrobe.open(2, 9);
            else Utils.out(INVALID_ARGUMENTS);
            return;
        } else /* contains " " */ {
            try {
                String[] parts = input.split(" "); // Split by space
                slot = Integer.parseInt(parts[0]);
                page = Integer.parseInt(parts[1]);

                if(page > 2) page = 2;
                AutoWardrobe.open(page, slot % 9);
            } catch (Exception e) {
                Utils.out(INVALID_ARGUMENTS);
            }
        }
    }

    @SubCommand
    private void force_dungeon() {
        Utils.forceDungeon = !Utils.forceDungeon;
        Utils.out("Toggled forcing dungeon to "+Utils.forceDungeon);
    }

    @SubCommand
    private void force_skyblock() {
        Utils.forceSkyBlock = !Utils.forceSkyBlock;
        Utils.out("Toggled forcing SkyBlock to "+Utils.forceSkyBlock);
    }

    @SubCommand
    private void disable() {
        Shady.disable();
    }

    @SubCommandGroup(value = "debug")
    private static class debug {
        @SubCommand
        private void dungeon() {
            if(Utils.inDungeon) DungeonUtils.debug();
        }

        @SubCommand
        private void routines() {
            for(Routine routine : Routines.routines.values()) {
                Utils.out(StringUtils.rightPad(routine.name + ' ', 40, '-'));
                Utils.out("Concurrent: " + (routine.allowConcurrent ? "true" : "false"));
                Utils.out("Trigger: " + routine.trigger.getClass().getSimpleName());
                Utils.out("Actions: " + routine.actions.size());
            }
        }

        @SubCommand
        private void palette() {
            Shady.guiToOpen = new CommandPalette();
        }

        @SubCommand
        private void estonia() {
            EstonianUtils.playFolkSong();
        }

        @SubCommand
        private void crash() {
            Shady.shouldCrash = true; // wtf
        }

        @SubCommand
        private void copy_look() {
            if(Shady.mc.objectMouseOver != null)
                Utils.copyToClipboard(Shady.mc.objectMouseOver.getBlockPos().getX() + ", " + Shady.mc.objectMouseOver.getBlockPos().getY() + ", " + Shady.mc.objectMouseOver.getBlockPos().getZ());
        }

        @SubCommand
        private void scan() {
            MapController.scannedMap = MapScanner.getScan();
            Utils.out("Forced scan, check logs for any errors");

            if(MapController.scannedMap == null)
                Utils.out("Map is null");
        }
    }
}
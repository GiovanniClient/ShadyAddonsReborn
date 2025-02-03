package cheaters.get.banned.gui.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.utils.Utils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class LegacyCommand extends CommandBase {

    @Override
    public String getCommandName() {
        return "lsh";
    }

    @Override
    public List<String> getCommandAliases() {
        return new ArrayList<String>(){{
            add("lshr");
            add("legacysh");
            add("legacyshr");
            add("legacyshady");
            add("legacyshadyaddons");
            add("legacyshadyaddonsreborn");
        }};
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + getCommandName();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if(!Shady.isShadyEnabled) {
            Utils.sendMessageAsPlayer("/" + RandomStringUtils.random(10, true, false));
            return;
        }

        Shady.guiToOpen = new ConfigGui(new ResourceLocation("shadyaddons:"+Utils.getLogo()+".png"));
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

}

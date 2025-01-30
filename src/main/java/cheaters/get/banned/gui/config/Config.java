package cheaters.get.banned.gui.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.AutoWardrobe;
import cheaters.get.banned.features.commandpalette.CommandPalette;
import cheaters.get.banned.utils.Utils;

public class Config {

    /**
     * I AM MOVING EVERYTHING TO POLYFROST CONFIG
     * see PolyfrostConfig.java
     *
     * many things left here are stuff i don't feel like moving yet
     * OR stuff i will eventually just remove from the mod
     *
     * without these variables the mod just won't compile so here they are
     */


    public static boolean autoGg = false;
    public static boolean autoSimonSays = false;
    public static boolean royalPigeonMacro = false;

    public static int autoClickerMode = 0;
    public static int autoClickerCps = 0;
    public static boolean stopAutoClickerInGui = true;
    public static boolean autoClickerIndicator = true;

    public static boolean stonklessStonk = false;
    public static boolean alwaysOn = false;
    public static boolean disableInBoss = false;
    public static boolean onlyEnableWhenHoldingPickaxe = false;
    public static boolean clickThroughSummons = false;
    public static boolean hideSummons = false;


    public static boolean autoSalvage = false;
    public static boolean automaticallyStartSalvaging = false;
    public static boolean autoSell = false;
    public static boolean autoSellMinionDrops = false;
    public static boolean autoSellSalvageable = false;
    public static boolean autoSellSuperboom = false;
    public static boolean autoSellDungeonsJunk = false;
    public static boolean autoSellPotions = false;

    public static boolean crystalReach = false;
    public static boolean crystalEtherwarp = false;
    public static int crystalSide = 0;
    public static boolean renewCrystalHollowsPass = false;
    public static boolean normalAbilityKeybind = false;

    public static boolean iceSprayHotkey = false;

    public static boolean rogueSwordHotkey = false;
    public static boolean powerOrbHotkey = false;
    public static boolean weirdTubaHotkey = false;
    public static boolean gyrokineticWandHotkey = false;
    public static boolean pigmanSwordHotkey = false;
    public static boolean healingWandHotkey = false;
    public static boolean fishingRodHotkey = false;
    public static boolean teleportWithAnything = false;
    public static boolean disableOutsideDungeons = false;
    public static boolean soulWhipWithAnything = false;
    public static boolean termWithAnything = false;
    public static boolean aotsWithAnything = false;
    public static boolean autoReadyUp = false;
    public static boolean blockCellsAlignment = false;
    public static boolean blockGiantsSlam = false;
    public static boolean blockValkyrie = false;

    public static boolean socialQuickMathsSolver = false;
    public static int quickMathsAnswerDelay = 100;
    public static boolean enableMathsOutsideSkyBlock = false;
    public static boolean connectFourAI = false;

    public static boolean antiKb = false;
    public static boolean noRotate = false;
    public static boolean autoTerminals = false;
    public static boolean terminalHalfTrip = false;
    public static int terminalClickDelay = 100;

    @Property(
            type = Property.Type.BUTTON,
            button = "Open Palette",
            name = "Command Palette",
            note = "Command/Control + K"
    )
    public static Runnable openCommandPalette = () -> {
        Shady.guiToOpen = new CommandPalette();
        Utils.out("You can customize the shortcut in Minecraft controls (which you can open with the Command Palette!)");
    };

    @Property(
            type = Property.Type.BUTTON,
            button = "Open Wardrobe",
            name = "Instant Wardrobe",
            note = "by RoseGold"
    )
    public static Runnable openWardrobe = () -> {
        if(Utils.inSkyBlock) {
            AutoWardrobe.open(1, 0);
            Utils.out("Use /sh wardrobe [slot] to equip a specific set!");
        } else {
            Utils.out("You must be in SkyBlock to use this!");
        }
    };


}

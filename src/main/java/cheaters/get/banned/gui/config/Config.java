package cheaters.get.banned.gui.config;

import cheaters.get.banned.Shady;
import cheaters.get.banned.features.AutoWardrobe;
import cheaters.get.banned.features.commandpalette.CommandPalette;
import cheaters.get.banned.features.routines.Routines;
import cheaters.get.banned.stats.RoutinesAPI;
import cheaters.get.banned.utils.Utils;

public class Config {

    // i removed a lot of features
    // the reason all these variables are still here is that
    // there is a lot of refactoring to debloat the mod and
    // without these variables it just wont compile
    //TODO

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
            type = Property.Type.BOOLEAN,
            name = "Fake ironman in chat"
    )
    public static boolean enableFakeIronman = false;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Melody"
    )
    public static boolean autoMelody = false;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Experiments"
    )
    public static boolean autoExperiments = false;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Disable Sword Block Animation"
    )
    public static boolean disableBlockAnimation = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Ghost Blocks",
            parent = "Keybinds"
    )
    public static boolean ghostBlockKeybind = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Right-Click w/ Stonk",
                parent = "Ghost Blocks"
        )
        public static boolean stonkGhostBlock = false;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Dungeon Map"
    )
    public static boolean dungeonMap = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Show Room Names",
                parent = "Dungeon Map",
                options = {"None", "Important", "All"}
        )
        public static int showRoomNames = 0;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Show Run Information",
                parent = "Dungeon Map"
        )
        public static boolean showDungeonInfo = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Show Player Heads",
                parent = "Dungeon Map",
                options = {"None", "All", "Own"}
        )
        public static int showMapPlayerHeads = 1;

        @Property(
            type = Property.Type.SELECT,
            name = "Map Border",
            parent = "Dungeon Map",
            options = {"None", "Chroma", "Black", "White"}
        )
        public static int mapBorder = 0;

        @Property(
                type = Property.Type.NUMBER,
                name = "Horizontal Offset",
                parent = "Dungeon Map",
                suffix = "px",
                step = 10
        )
        public static int mapXOffset = 10;

        @Property(
                type = Property.Type.NUMBER,
                name = "Vertical Offset",
                parent = "Dungeon Map",
                suffix = "px",
                step = 10
        )
        public static int mapYOffset = 10;

        @Property(
                type = Property.Type.NUMBER,
                name = "Scale",
                parent = "Dungeon Map",
                suffix = "%",
                step = 10,
                min = 50,
                max = 150
        )
        public static int mapScale = 80;

        @Property(
                type = Property.Type.NUMBER,
                name = "Background Opacity",
                parent = "Dungeon Map",
                max = 100,
                step = 10,
                suffix = "%"
        )
        public static int mapBackgroundOpacity = 30;

    @Property(
            type = Property.Type.BOOLEAN,
            name = "Auto-Close Secret Chests"
    )
    public static boolean closeSecretChests = false;

    @Property(
            type = Property.Type.FOLDER,
            name = "Show Hidden Mobs"
    )
    public static boolean showHiddenMobs = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Shadow Assassins",
                parent = "Show Hidden Mobs"
        )
        public static boolean showHiddenShadowAssassins = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Fels",
                parent = "Show Hidden Mobs"
        )
        public static boolean showHiddenFels = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Ghosts",
                parent = "Show Hidden Mobs"
        )
        public static boolean showGhosts = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Stealthy Blood Mobs",
                parent = "Show Hidden Mobs"
        )
        public static boolean showStealthyBloodMobs = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Sneaky Creepers",
                parent = "Show Hidden Mobs"
        )
        public static boolean showSneakyCreepers = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Mob ESP",
            note = "Disable Entity Culling in Patcher"
    )
    public static boolean mobEsp = false;

    @Property(
            type = Property.Type.NUMBER,
            name = "Outline Thickness",
            parent = "Mob ESP",
            min = 3,
            max = 10,
            suffix = "px"
    )
    public static int espThickness = 5;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Glacite Corpses",
            parent = "Mob ESP"
    )
    public static boolean glaciteCorpses = false;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Sludges",
            parent = "Mob ESP"
    )
    public static boolean sludgeEsp = false;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Yogs",
            parent = "Mob ESP"
    )
    public static boolean yogEsp = false;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Corleone",
            parent = "Mob ESP"
    )
    public static boolean corleoneEsp = false;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Starred Mobs",
            parent = "Mob ESP"
    )
    public static boolean starredMobEsp = false;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Secret Bats",
            parent = "Mob ESP"
    )
    public static boolean secretBatEsp = false;

    @Property(
            type = Property.Type.CHECKBOX,
            name = "Minibosses",
            parent = "Mob ESP"
    )
    public static boolean minibossEsp = false;


    @Property(
            type = Property.Type.FOLDER,
            name = "Gemstone ESP"
    )
    public static boolean gemstoneEsp = false;

        @Property(
                type = Property.Type.BOOLEAN,
                name = "Include Glass Panes",
                parent = "Gemstone ESP"
        )
        public static boolean includeGlassPanes = false;

        @Property(
                type = Property.Type.SELECT,
                name = "Highlight Mode",
                parent = "Gemstone ESP",
                options = {"Outlined", "Filled"}
        )
        public static int highlightMode = 0;

        @Property(
                type = Property.Type.NUMBER,
            name = "Scan Radius",
                parent = "Gemstone ESP",
                suffix = " blocks",
                min = 5,
                max = 30
        )
        public static int gemstoneRadius = 15;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Ruby",
                parent = "Gemstone ESP"
        )
        public static boolean rubyEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Amber",
                parent = "Gemstone ESP"
        )
        public static boolean amberEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Sapphire",
                parent = "Gemstone ESP"
        )
        public static boolean sapphireEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Jade",
                parent = "Gemstone ESP"
        )
        public static boolean jadeEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Amethyst",
                parent = "Gemstone ESP"
        )
        public static boolean amethystEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Topaz",
                parent = "Gemstone ESP"
        )
        public static boolean topazEsp = false;

        @Property(
                type = Property.Type.CHECKBOX,
                name = "Jasper",
                parent = "Gemstone ESP"
        )
        public static boolean jasperEsp = false;


    @Property(
            type = Property.Type.BOOLEAN,
            name = "Use Clear Buttons",
            note = "Not a cheat, just cosmetic"
    )
    public static boolean useCleanButtons = false;

    @Property(type = Property.Type.SPACER)
    public static Object spacer2;

    @Property(
            type = Property.Type.BUTTON,
            button = "Open Palette",
            name = "Command Palette",
            note = "Command/Control + K"
    )
    public static Runnable openCommandPalette = () -> {
        Shady.guiToOpen = new CommandPalette();
        Utils.sendModMessage("You can customize the shortcut in Minecraft controls (which you can open with the Command Palette!)");
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
            Utils.sendModMessage("Use /sh wardrobe [slot] to equip a specific set!");
        } else {
            Utils.sendModMessage("You must be in SkyBlock to use this!");
        }
    };


}

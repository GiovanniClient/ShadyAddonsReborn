package cheaters.get.banned.gui.polyconfig;

import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.*;
import cc.polyfrost.oneconfig.config.data.InfoType;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;
import cheaters.get.banned.Shady;

/**
 * The main Config entrypoint that extends the Config type and inits the config options.
 * See <a href="https://docs.polyfrost.cc/oneconfig/config/adding-options">this link</a> for more config Options
 */
public class PolyfrostConfig extends Config {
    //TODO: make an HUD

    @Switch(name = "Fake Ironman (chat symbol)")
    public static boolean fakeIronman = false;

    @Switch(name = "Auto-Melody")
    public static boolean autoMelody = false;

    @Switch(name = "JungleCheddar",
            description = "Auto Jungle Cheese")
    public static boolean jungleCheddar = false;

    @Switch(name = "Disable Sword Block Animation")
    public static boolean disableBlockAnimation = false;

    @Switch(name = "Ghost Blocks")
    public static boolean ghostBlockKeybind = false;

    @Switch(name = "Right-Click w/ Stonk")
    public static boolean stonkGhostBlock = false;


    @Switch(name = "Auto-Close Secret Chests")
    public static boolean closeSecretChests = false;

    @Switch(name = "Use Clear Buttons", description = "Not a cheat, just cosmetic")
    public static boolean useCleanButtons = false;

    // experiments
    @Switch(name = "Main Toggle", subcategory = "Auto-Experiments")
    public static boolean autoExperiments = false;
    @Info(text = "Be careful when messing with speeds.",
            type = InfoType.WARNING, subcategory = "Auto-Experiments")
    public static boolean ignoreme1 = false;
    @Slider(name="MIN Click Speed (ms)", min=100, max=1300, step=25, subcategory = "Auto-Experiments")
    public static int CLICK_DELAY_MIN = 300;
    @Slider(name="MAX Click Speed (ms)", min=100, max=1300, step=25, subcategory = "Auto-Experiments")
    public static int CLICK_DELAY_MAX = 800;
    @Switch(name="Auto Quit When Done", subcategory = "Auto-Experiments")
    public static boolean autoQuit = false;
    @Dropdown(name="Metaphysical Serum", options = {"0", "1", "2", "3"}, subcategory = "Auto-Experiments")
    public static int metaphysicalSerum = 0;
    @Dropdown(name="Click Button (beta)", options = {"0", "1", "2", "3"}, subcategory = "Auto-Experiments")
    public static int clickButton = 0;
    @Dropdown(name="Click Mode (beta)", options = {"0", "1", "2", "3"}, subcategory = "Auto-Experiments")
    public static int clickMode = 3;
    // end experiments

    // hidden stuff
    @Switch(name="Show Hidden Fels", subcategory="Invisible Stuff")
    public static boolean showHiddenFels = false;
    @Switch(name="Show Hidden Ghosts", subcategory="Invisible Stuff")
    public static boolean showGhosts = false;
    @Switch(name="Show Stealthy Blood Mobs", subcategory="Invisible Stuff")
    public static boolean showStealthyBloodMobs = false;
    @Switch(name="Show Hidden Shadow Assassins", subcategory="Invisible Stuff")
    public static boolean showHiddenShadowAssassins = false;
    @Switch(name="Show Sneaky Creepers", subcategory="Invisible Stuff")
    public static boolean showSneakyCreepers = false;
    @Switch(name="Show Barrier Blocks", description="makes them glass", subcategory="Invisible Stuff")
    public static boolean barriersEsp = false;
    // end Show Hidden Stuff

    // ESPs - many are bugged from before the config migration
    //        they are kinda useless anyways, I'll think about fixing or removing them
    @Info(text="type /patcher -> disable entity culling", type = InfoType.WARNING, subcategory="ESP - Mobs")
    @Slider(name="ESP Outline Thinkness", subcategory="ESP - Mobs", min=3f, max=10f, step=1)
    public static int espThickness = 5;
    @Switch(name = "Mineshaft Corpses", subcategory="ESP - Mobs")
    public static boolean glaciteCorpses = false;
    @Switch(name = "Sludges", subcategory="ESP - Mobs")
    public static boolean sludgeEsp = false;
    @Switch(name = "Yogs", subcategory="ESP - Mobs")
    public static boolean yogEsp = false;
    @Switch(name = "Corleone", subcategory="ESP - Mobs")
    public static boolean corleoneEsp = false;
    @Switch(name = "Starred Mobs", subcategory="ESP - Mobs")
    public static boolean starredMobEsp = false;
    @Switch(name = "Secret Bats", subcategory="ESP - Mobs")
    public static boolean secretBatEsp = false;
    @Switch(name = "Minibosses", subcategory="ESP - Mobs")
    public static boolean minibossEsp = false;
    // end ESP - Mobs

    // gemstones ESP
    @Switch(name = "Include Glass Panes", subcategory="ESP - Gemstones")
    public static boolean includeGlassPanes = false;
    @Dropdown(name = "Highlight Mode", subcategory="ESP - Gemstones", options = {"Outlined", "Filled"})
    public static int highlightMode = 0;
    @Slider(name="Scan Radius", subcategory="ESP - Gemstones", min=5f, max=30f, step=1)
    public static int gemstoneRadius = 15;
    @Switch(name = "Ruby", subcategory="ESP - Gemstones")
    public static boolean rubyEsp = false;
    @Switch(name = "Amber", subcategory="ESP - Gemstones")
    public static boolean amberEsp = false;
    @Switch(name = "Sapphire", subcategory="ESP - Gemstones")
    public static boolean sapphireEsp = false;
    @Switch(name = "Jade", subcategory="ESP - Gemstones")
    public static boolean jadeEsp = false;
    @Switch(name = "Amethyst", subcategory="ESP - Gemstones")
    public static boolean amethystEsp = false;
    @Switch(name = "Topaz", subcategory="ESP - Gemstones")
    public static boolean topazEsp = false;
    @Switch(name = "Jasper", subcategory="ESP - Gemstones")
    public static boolean jasperEsp = false;
    @Switch(name = "Onyx", subcategory="ESP - Gemstones")
    public static boolean onyxEsp = false;
    @Switch(name = "Peridot", subcategory="ESP - Gemstones")
    public static boolean peridotEsp = false;
    @Switch(name = "Aquamarine", subcategory="ESP - Gemstones")
    public static boolean aquamarineEsp = false;
    @Switch(name = "Citrine", subcategory="ESP - Gemstones")
    public static boolean citrineEsp = false;
    // end gemstones ESP

    // last time i tried it, this map was buggy: i believe there are much better maps than this one so i might just
    // completely remove it from shady since i don't feel like maintaining it
    @Switch(name = "Dungeon Map", subcategory="Dungeon Map")
    public static boolean dungeonMap = false;
    @Dropdown(name="Show Room Names", options={"None", "Important", "All"}, subcategory="Dungeon Map")
    public static int showRoomNames = 0;
    @Switch(name = "Show Run Information", subcategory="Dungeon Map")
    public static boolean showDungeonInfo = false;
    @Dropdown(name="Show Player Heads", options={"None", "All", "Own"}, subcategory="Dungeon Map")
    public static int showMapPlayerHeads = 1;
    @Dropdown(name="Map Border", options={"None", "Chroma", "Black", "White"}, subcategory="Dungeon Map")
    public static int mapBorder = 0;
    @Slider(name="Horizontal Offset", min=0f, max=100f, step=10, subcategory="Dungeon Map")
    public static int mapXOffset = 10;
    @Slider(name="Vertical Offset", min=0f, max=100f, step=10, subcategory="Dungeon Map")
    public static int mapYOffset = 10;
    @Slider(name = "Scale", min=0f, max=150f, step=10, subcategory="Dungeon Map")
    public static int mapScale = 80;
    @Slider(name="Background Opacity", min=10f, max=100f, step=10, subcategory="Dungeon Map")
    public static int mapBackgroundOpacity = 30;
    // end map

    public PolyfrostConfig() {
        super(new Mod(Shady.MOD_NAME, ModType.UTIL_QOL), Shady.MOD_ID + ".json");
        initialize();
    }
}


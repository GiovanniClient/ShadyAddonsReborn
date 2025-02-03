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
    public static boolean FAKE_IRONMAN = false;

    @Switch(name = "Auto-Melody")
    public static boolean AUTO_MELODY = false;

    @Switch(name = "JungleCheddar",
            description = "Auto Jungle Cheese")
    public static boolean JUNGLE_CHEDDAR = false;

    @Switch(name = "Disable Sword Block Animation")
    public static boolean DISABLE_SWORD_BLOCK_ANIM = false;

    @Switch(name = "Ghost Blocks")
    public static boolean GHOST_BLOCKS_KEYBIND = false;

    @Switch(name = "Right-Click w/ Stonk")
    public static boolean GHOST_BLOCKS_W_PICK = false;


    @Switch(name = "Auto-Close Secret Chests")
    public static boolean AUTO_CLOSE_SECRET_CHESTS = false;

    @Switch(name = "Use Clear Buttons", description = "Not a cheat, just cosmetic")
    public static boolean USE_CLEAN_BUTTONS = false;

    // experiments
    @Switch(name = "Main Toggle", subcategory = "Auto-Experiments")
    public static boolean AUTO_EXPERIMENTS = false;
    @Info(text = "Be careful when messing with speeds.",
            type = InfoType.WARNING, subcategory = "Auto-Experiments")
    public static boolean ignoreme1 = false;
    @Slider(name="MIN Click Speed (ms)", min=100, max=1300, step=25, subcategory = "Auto-Experiments")
    public static int AE_CLICK_DELAY_MIN = 300;
    @Slider(name="MAX Click Speed (ms)", min=100, max=1300, step=25, subcategory = "Auto-Experiments")
    public static int AE_CLICK_DELAY_MAX = 800;
    @Switch(name="Auto Quit When Done", subcategory = "Auto-Experiments")
    public static boolean AE_AUTO_QUIT = false;
    @Dropdown(name="Metaphysical Serum", options = {"0", "1", "2", "3"}, subcategory = "Auto-Experiments")
    public static int AE_METAPHYSICAL_SERUM = 0;
    @Dropdown(name="Click Button (beta)", options = {"0", "1", "2", "3"}, subcategory = "Auto-Experiments")
    public static int AE_CLICK_BUTTON = 0;
    @Dropdown(name="Click Mode (beta)", options = {"0", "1", "2", "3"}, subcategory = "Auto-Experiments")
    public static int AE_CLICK_MODE = 3;
    // end experiments

    // hidden stuff
    @Switch(name="Show Hidden Fels", subcategory="Invisible Stuff")
    public static boolean SHOW_HIDDEN_FELS = false;
    @Switch(name="Show Hidden Ghosts", subcategory="Invisible Stuff")
    public static boolean SHOW_HIDDEN_GHOSTS = false;
    @Switch(name="Show Stealthy Blood Mobs", subcategory="Invisible Stuff")
    public static boolean SHOW_STEALTHY_BLOOD_MOBS = false;
    @Switch(name="Show Hidden Shadow Assassins", subcategory="Invisible Stuff")
    public static boolean SHOW_HIDDEN_SHADOW_ASS = false;
    @Switch(name="Show Sneaky Creepers", subcategory="Invisible Stuff")
    public static boolean SHOW_SNEAKY_CREEPERS = false;
    @Switch(name="Show Barrier Blocks", description="makes them glass", subcategory="Invisible Stuff")
    public static boolean BARRIER_ESP = false;
    // end Show Hidden Stuff

    // ESPs - many are bugged from before the config migration
    //        they are kinda useless anyways, I'll think about fixing or removing them
    @Info(text="type /patcher -> disable entity culling", type = InfoType.WARNING, subcategory="ESP - Mobs")
    @Slider(name="ESP Outline Thinkness", subcategory="ESP - Mobs", min=3f, max=10f, step=1)
    public static int ESP_THINKNESS = 5;
    @Switch(name = "Mineshaft Corpses", subcategory="ESP - Mobs")
    public static boolean ESP_GLACITE_CORPSES = false;
    @Switch(name = "Sludges", subcategory="ESP - Mobs")
    public static boolean ESP_SLUDGE = false;
    @Switch(name = "Yogs", subcategory="ESP - Mobs")
    public static boolean ESP_YOG = false;
    @Switch(name = "Corleone", subcategory="ESP - Mobs")
    public static boolean ESP_CORLEONE = false;
    @Switch(name = "Starred Mobs", subcategory="ESP - Mobs")
    public static boolean ESP_STARRED_MOBS = false;
    @Switch(name = "Secret Bats", subcategory="ESP - Mobs")
    public static boolean ESP_SECRET_BAT = false;
    @Switch(name = "Minibosses", subcategory="ESP - Mobs")
    public static boolean ESP_MINIBOSSES = false;
    // end ESP - Mobs

    // gemstones ESP
    @Switch(name = "Include Glass Panes", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_GLASS_PANES = false;
    @Dropdown(name = "Highlight Mode", subcategory="ESP - Gemstones", options = {"Outlined", "Filled"})
    public static int GEM_ESP_HIGHLIGHT_MODE = 0;
    @Slider(name="Scan Radius", subcategory="ESP - Gemstones", min=5f, max=30f, step=1)
    public static int GEM_ESP_SCAN_RADIUS = 15;
    @Switch(name = "Ruby", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_RUBY = false;
    @Switch(name = "Amber", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_AMBER = false;
    @Switch(name = "Sapphire", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_SAPPHIRE = false;
    @Switch(name = "Jade", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_JADE = false;
    @Switch(name = "Amethyst", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_AMETHYST = false;
    @Switch(name = "Topaz", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_TOPAZ = false;
    @Switch(name = "Jasper", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_JASPER = false;
    @Switch(name = "Onyx", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_ONYX = false;
    @Switch(name = "Peridot", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_PERIDOT = false;
    @Switch(name = "Aquamarine", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_AQUAMARINE = false;
    @Switch(name = "Citrine", subcategory="ESP - Gemstones")
    public static boolean GEM_ESP_CITRINE = false;
    // end gemstones ESP

    // last time i tried it, this map was buggy: i believe there are much better maps than this one so i might just
    // completely remove it from shady since i don't feel like maintaining it
    @Info(text = "last time i tried it, this map was buggy: i believe there are much better maps than this one so i might just\n" +
    "completely remove it from shady since i don't feel like maintaining it", type = InfoType.WARNING ,subcategory="Dungeon Map")
    @Switch(name = "Main Toggle", subcategory="Dungeon Map")
    public static boolean MAP_DUNG_TOGGLE = false;
    @Dropdown(name="Show Room Names", options={"None", "Important", "All"}, subcategory="Dungeon Map")
    public static int MAP_DUNG_ROOM_NAMES = 0;
    @Switch(name = "Show Run Information", subcategory="Dungeon Map")
    public static boolean MAP_DUNG_SHOW_INFO = false;
    @Dropdown(name="Show Player Heads", options={"None", "All", "Own"}, subcategory="Dungeon Map")
    public static int MAP_DUNG_SHOW_HEADS = 1;
    @Dropdown(name="Map Border", options={"None", "Chroma", "Black", "White"}, subcategory="Dungeon Map")
    public static int MAP_DUNG_BORDER_COLOR = 0;
    @Slider(name="Horizontal Offset", min=0f, max=100f, step=10, subcategory="Dungeon Map")
    public static int MAP_DUNG_X_OFFSET = 10;
    @Slider(name="Vertical Offset", min=0f, max=100f, step=10, subcategory="Dungeon Map")
    public static int MAP_DUNG_Y_OFFSET = 10;
    @Slider(name = "Scale", min=0f, max=150f, step=10, subcategory="Dungeon Map")
    public static int MAP_DUNG_SCALE = 80;
    @Slider(name="Background Opacity", min=10f, max=100f, step=10, subcategory="Dungeon Map")
    public static int MAP_DUNG_BG_OPACITY = 30;
    // end map

    public PolyfrostConfig() {
        super(new Mod(Shady.MOD_NAME, ModType.UTIL_QOL), Shady.MOD_ID + ".json");
        initialize();
    }
}


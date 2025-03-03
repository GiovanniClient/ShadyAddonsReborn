package cheaters.get.banned;

import cc.polyfrost.oneconfig.utils.commands.CommandManager;
import cheaters.get.banned.events.MinuteEvent;
import cheaters.get.banned.events.TickEndEvent;
import cheaters.get.banned.features.*;
import cheaters.get.banned.features.commandpalette.CommandPalette;
import cheaters.get.banned.features.map.MapController;
import cheaters.get.banned.features.map.MapView;
import cheaters.get.banned.features.routines.RoutineHooks;
import cheaters.get.banned.features.routines.Routines;
import cheaters.get.banned.gui.config.Config;
import cheaters.get.banned.gui.config.ConfigLogic;
import cheaters.get.banned.gui.config.LegacyCommand;
import cheaters.get.banned.gui.config.settings.BooleanSetting;
import cheaters.get.banned.gui.config.settings.SelectSetting;
import cheaters.get.banned.gui.config.settings.Setting;
import cheaters.get.banned.gui.polyconfig.NewCommand;
import cheaters.get.banned.gui.polyconfig.PolyfrostConfig;
import cheaters.get.banned.remote.DisableFeatures;
import cheaters.get.banned.remote.UpdateGui;
import cheaters.get.banned.remote.Updater;
import cheaters.get.banned.stats.MiscStats;
import cheaters.get.banned.utils.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.apache.commons.lang3.SystemUtils;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Mod(modid = Shady.MOD_ID, name = Shady.MOD_NAME, version = "@VER@", clientSideOnly = true)
public class Shady {

    // Sets the variables from `gradle.properties`. See the `blossom` config in `build.gradle.kts`.
    public static final String MOD_ID = "@ID@";
    public static final String MOD_NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    public static final boolean BETA = VERSION.contains("-pre") || VERSION.equals("@VER"+"SION@");
    public static final boolean DEBUG = false;

    // polyfrost stuff
    public static PolyfrostConfig polyfrostConfig;

    public static final Minecraft mc = Minecraft.getMinecraft();
    public static boolean shouldCrash = false;
    public static final File dir = new File(new File(mc.mcDataDir, "config"), "shady");

    public static boolean USING_SBA = false, USING_PATCHER = false, USING_SKYTILS = false, USING_SBE = false;

    public static GuiScreen guiToOpen = null;
    public static boolean isShadyEnabled = true;

    public static ArrayList<Setting> settings = new ArrayList<>();

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(!dir.exists()) dir.mkdirs();
        ClientCommandHandler.instance.registerCommand(new LegacyCommand());

        EstonianUtils.loadEstonian();
        settings = ConfigLogic.collect(Config.class, DisableFeatures.load());

        // Read JSON Files
        ConfigLogic.load();
        Routines.load();
        MapController.loadRooms();

        // Do Remote Things
        // Updater.check();
        // Analytics.collect("version", VERSION);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        polyfrostConfig = new PolyfrostConfig();
        CommandManager.INSTANCE.registerCommand(new NewCommand());

        MinecraftForge.EVENT_BUS.register(new TickEndEvent());
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Utils());
        MinecraftForge.EVENT_BUS.register(new LocationUtils());
        MinecraftForge.EVENT_BUS.register(new DungeonUtils());
        MinecraftForge.EVENT_BUS.register(new RotationUtils());
        MinecraftForge.EVENT_BUS.register(new MiscStats());
        MinecraftForge.EVENT_BUS.register(new RoutineHooks());

        MinecraftForge.EVENT_BUS.register(new JungleCheddar());
        MinecraftForge.EVENT_BUS.register(new BarrierESP());

        MinecraftForge.EVENT_BUS.register(new NoAbilities());
        MinecraftForge.EVENT_BUS.register(new StonklessStonk()); // ghosthand
        MinecraftForge.EVENT_BUS.register(new GhostBlocks());
        MinecraftForge.EVENT_BUS.register(new AutoCloseChest());
        //MinecraftForge.EVENT_BUS.register(new RoyalPigeonMacro());
        //MinecraftForge.EVENT_BUS.register(new AutoGG());
        //MinecraftForge.EVENT_BUS.register(new AutoSimonSays());
        //MinecraftForge.EVENT_BUS.register(new AbilityKeybind());
        //MinecraftForge.EVENT_BUS.register(new AutoClicker());
        //MinecraftForge.EVENT_BUS.register(new AutoRenewCrystalHollows());BlockAbilities
        MinecraftForge.EVENT_BUS.register(new DisableSwordAnimation());
        MinecraftForge.EVENT_BUS.register(new ShowHiddenEntities());
        //MinecraftForge.EVENT_BUS.register(new HideSummons());
        //MinecraftForge.EVENT_BUS.register(new TeleportWithAnything());
        //MinecraftForge.EVENT_BUS.register(new ItemMacro());
        MinecraftForge.EVENT_BUS.register(new MobESP());
        MinecraftForge.EVENT_BUS.register(new GemstoneESP());
        //MinecraftForge.EVENT_BUS.register(new NewAutoTerminals());
        //MinecraftForge.EVENT_BUS.register(new AutoTerminals());
        MinecraftForge.EVENT_BUS.register(new AutoMelody());
        MinecraftForge.EVENT_BUS.register(new AutoExperiments());
        //MinecraftForge.EVENT_BUS.register(new AutoReadyUp());
        //MinecraftForge.EVENT_BUS.register(new CrystalReach());
        //MinecraftForge.EVENT_BUS.register(new AutoSalvage());
        //MinecraftForge.EVENT_BUS.register(new AutoSell());
        //MinecraftForge.EVENT_BUS.register(new SocialCommandSolver());
        //MinecraftForge.EVENT_BUS.register(new ConnectFourSolver());
        MinecraftForge.EVENT_BUS.register(new AutoWardrobe());
        //MinecraftForge.EVENT_BUS.register(new CrystalEtherwarp());
        //MinecraftForge.EVENT_BUS.register(new NoRotate());
        //MinecraftForge.EVENT_BUS.register(new AntiKB());

        MinecraftForge.EVENT_BUS.register(new MapController());
        MinecraftForge.EVENT_BUS.register(new MapView());

        KeybindUtils.register("Command Palette", Keyboard.KEY_K);

        for(KeyBinding keyBinding : KeybindUtils.keyBindings.values()) {
            ClientRegistry.registerKeyBinding(keyBinding);
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        USING_SBA = Loader.isModLoaded("skyblockaddons");
        USING_PATCHER = Loader.isModLoaded("patcher");
        USING_SKYTILS = Loader.isModLoaded("skytils");
        USING_SBE = Loader.isModLoaded("skyblockextras");

        cheaters.get.banned.remote.Capes.load(); // TODO: Figure out how to make this async

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime firstRun = now.withSecond(0).plusMinutes(1);
        Duration initialDelay = Duration.between(now, firstRun);
        long initalDelaySeconds = initialDelay.getSeconds();

        // Triggers the MinuteEvent event
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            MinecraftForge.EVENT_BUS.post(new MinuteEvent());
        }, initalDelaySeconds, 60, TimeUnit.SECONDS);
    }

    // Send heartbeat/stats every 5 minutes
    @SubscribeEvent
    public void onMinute(MinuteEvent event) {
        if(MiscStats.minutesSinceLastSend == 5) {
            MiscStats.minutesSinceLastSend = 0;
            //MiscStats.send();
        }

        if(EstonianUtils.isEstoniaDay() && Shady.mc.theWorld != null && Math.random() > 0.9) {
            EstonianUtils.playFolkSong();
        }

        MiscStats.minutesSinceLastSend++;
    }

    // this is used to display the /shady menu
    @SubscribeEvent
    public void onTick(TickEndEvent event) {
        if(guiToOpen != null) {
            mc.displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }

        if(shouldCrash) {
            throw new NullPointerException("You did this to yourself! Isn't it wonderful?");
        }
    }

    @SubscribeEvent
    public void onInput(InputEvent.KeyInputEvent event) {
        if(KeybindUtils.isPressed("Command Palette")) {
            if(SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_MAC_OSX) {
                if(!Keyboard.isKeyDown(Keyboard.KEY_LMETA) && !Keyboard.isKeyDown(Keyboard.KEY_RMETA)) {
                    return;
                }
            } else if(!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) && !Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
                return;
            }

            Shady.guiToOpen = new CommandPalette();
            MiscStats.add(MiscStats.Metric.COMMAND_PALETTE_OPENS);
        }
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(Updater.shouldUpdate && event.gui instanceof GuiMainMenu) {
            guiToOpen = new UpdateGui();
            Updater.shouldUpdate = false;
        }
    }

    public static void disable() {
        isShadyEnabled = false;
        for(Setting setting : settings) {
            if(setting instanceof BooleanSetting) setting.set(false);
            if(setting instanceof SelectSetting) setting.set(0);
        }
    }

}

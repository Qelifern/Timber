package timber;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import timber.config.Config;
import timber.proxy.ClientProxy;
import timber.proxy.IProxy;
import timber.proxy.ServerProxy;

@Mod(Main.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Main {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "timber";

    public static IProxy proxy = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());

    public Main() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("timber-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("timber.toml"));
    }

    @SubscribeEvent
    public static void config(ModConfig.Reloading event) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("timber-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("timber.toml"));
    }

    @SubscribeEvent
    public static void config(ModConfig.ModConfigEvent event) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("timber-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("timber.toml"));
    }

    private void setup(final FMLCommonSetupEvent event) {
        proxy.setup(event);
        Main.LOGGER.info("HELLO WORLD!!!");
    }
}

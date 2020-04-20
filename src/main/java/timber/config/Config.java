package timber.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.tuple.Pair;
import timber.Main;

import java.nio.file.Path;

import static net.minecraftforge.fml.Logging.CORE;

@Mod.EventBusSubscriber
public class Config {
    public static final ForgeConfigSpec clientSpec;
    public static final Config.Client CLIENT;
    public static final ForgeConfigSpec serverSpec;
    public static final Config.Server SERVER;

    static {
        final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Config.Server::new);
        serverSpec = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();

        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Config.Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    public static void loadConfig(ForgeConfigSpec spec, Path path) {
        Main.LOGGER.debug("Loading config file {}", path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        Main.LOGGER.debug("Built TOML config for {}", path.toString());
        configData.load();
        Main.LOGGER.debug("Loaded TOML config file {}", path.toString());
        spec.setConfig(configData);
    }

    public static class Server {
        public final ForgeConfigSpec.BooleanValue dropsInCreativeMode;
        public final ForgeConfigSpec.BooleanValue damageAxe;

        Server(ForgeConfigSpec.Builder builder) {
            builder.comment("Server configuration settings")
                    .push("server");

            dropsInCreativeMode = builder
                    .comment(" Timber Mod will drop items in creative mode aswell, true to enable, false to disable. Default: false.").define("drops_in_creative_mode", false);

            damageAxe = builder
                    .comment(" Will damage the axe with the amount of logs chopped, true to enable, false to disable. Default: true.").define("damage_axe", true);

            builder.pop();
        }
    }

    public static class Client {
        public final ForgeConfigSpec.BooleanValue activateTimberMod;
        public final ForgeConfigSpec.BooleanValue visualIndicator;
        public final ForgeConfigSpec.BooleanValue reverseControl;

        Client(ForgeConfigSpec.Builder builder) {
            builder.comment("Client only settings")
                    .push("client");
            activateTimberMod = builder
                    .comment(" Timber Mod will disable itself if this value is set to false. Default: true.").define("activate_timber_mod", true);
            visualIndicator = builder
                    .comment(" Shows a text at your crosshair to inform you that you have Timber activated. Default: true.").define("visual_indicator", true);
            reverseControl = builder
                    .comment(" Reverses the control of sneaking, if false then sneaking and chopping will disable the Timber Mod, if true then it's in reverse. Default: false.").define("reverse_control", false);


            builder.pop();
        }
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("timber-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("timber.toml"));
        Main.LOGGER.debug("Loaded {} config file {}", Main.MOD_ID, configEvent.getConfig().getFileName());
    }

    @SubscribeEvent
    public static void onFileChange(final ModConfig.Reloading configEvent) {
        Config.loadConfig(Config.clientSpec, FMLPaths.CONFIGDIR.get().resolve("timber-client.toml"));
        Config.loadConfig(Config.serverSpec, FMLPaths.CONFIGDIR.get().resolve("timber.toml"));
        Main.LOGGER.fatal(CORE, "{} config just got changed on the file system!", Main.MOD_ID);
    }

}

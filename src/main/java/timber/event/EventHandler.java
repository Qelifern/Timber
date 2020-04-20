package timber.event;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import timber.config.Config;
import timber.proxy.ClientProxy;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {



    @SubscribeEvent
    public static void onBlockBreakEvent(final BlockEvent.BreakEvent event) {
        Timber.onBlockBreak(event);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent(priority= EventPriority.NORMAL, receiveCanceled = true)
    public static void onEvent(InputEvent.KeyInputEvent e) {
        if (ClientProxy.toggleTimber.isPressed()) {
            if (Config.CLIENT.activateTimberMod.get()) {
                Config.CLIENT.activateTimberMod.set(false);
                Minecraft.getInstance().player.sendMessage(new StringTextComponent("Disabled Timber Mod"));
            } else {
                Config.CLIENT.activateTimberMod.set(true);
                Minecraft.getInstance().player.sendMessage(new StringTextComponent("Activated Timber Mod"));
            }
        }
    }

}

package timber.rendering;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.AxeItem;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import timber.config.Config;

@Mod.EventBusSubscriber
public class OverlayRenderer {

    public static OverlayRenderer instance = new OverlayRenderer();

    @SubscribeEvent
    public void renderGameOverlayEvent(RenderGameOverlayEvent event) {
        if (event.isCancelable() || event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            return;
        }
        if (!(Minecraft.getInstance().player.getHeldItem(Hand.MAIN_HAND).getItem() instanceof AxeItem)) {
            return;
        }
        if (!Config.CLIENT.activateTimberMod.get()) {
            return;
        }
        if (!Config.CLIENT.visualIndicator.get()) {
            return;
        }
        RenderSystem.pushMatrix();

        RenderSystem.disableLighting();

        FontRenderer fontRenderer = Minecraft.getInstance().fontRenderer;


        int x = 302;
        int y = 150;
        fontRenderer.drawString("Timber!", x, y, 0xffffffff);

        RenderSystem.popMatrix();

    }

}

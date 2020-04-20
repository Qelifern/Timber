package timber.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.lwjgl.glfw.GLFW;
import timber.Main;
import timber.rendering.OverlayRenderer;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientProxy implements IProxy {

	public static final KeyBinding toggleTimber = new KeyBinding(Main.MOD_ID + ".key.toggleTimber", GLFW.GLFW_KEY_V, "key.categories." + Main.MOD_ID);


	static {
	    ClientRegistry.registerKeyBinding(toggleTimber);
    }

	@Override
	public void setup(FMLCommonSetupEvent event) {
		MinecraftForge.EVENT_BUS.register(OverlayRenderer.instance);
	}

	@Override
	public PlayerEntity getClientPlayer() {
		return Minecraft.getInstance().player;
	}

	@Override
	public World getClientWorld() {
		return Minecraft.getInstance().world;
	}

	@Override
	public World getServerWorld(int dim) {
        throw new IllegalStateException("Can't call this client-side!");
	}



}

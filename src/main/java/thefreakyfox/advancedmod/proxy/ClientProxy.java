package thefreakyfox.advancedmod.proxy;

import net.minecraft.entity.player.EntityPlayer;
import thefreakyfox.advancedmod.client.handler.KeyInputEventHandler;
import thefreakyfox.advancedmod.client.settings.KeyBindings;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;


public class ClientProxy extends CommonProxy {

	@Override
	public void preInit() {
		registerKeyBindings();
	}

	private void registerKeyBindings() {
		FMLCommonHandler.instance().bus().register( new KeyInputEventHandler() );
		for ( final KeyBindings key : KeyBindings.values() ) {
			ClientRegistry.registerKeyBinding( key.getKeybind() );
		}
	}

	@Override
	public void init() {}

	@Override
	public void postInit() {}

	@Override
	public Side getSide() {
		return Side.CLIENT;
	}

	@Override
	public EntityPlayer getClientPlayer() {
		// return Minecraft.getMinecraft().thePlayer;
		return FMLClientHandler.instance().getClientPlayerEntity();
	}

}

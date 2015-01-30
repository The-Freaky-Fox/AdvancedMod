package thefreakyfox.advancedmod;

import thefreakyfox.advancedmod.init.ModBlocks;
import thefreakyfox.advancedmod.proxy.CommonProxy;
import thefreakyfox.advancedmod.reference.Reference;
import thefreakyfox.advancedmod.util.LogHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod( modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION )
public class AdvancedMod {

	@Instance( Reference.MOD_ID )
	public static AdvancedMod instance;

	@SidedProxy( clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS )
	public static CommonProxy proxy;

	@EventHandler
	public void preInit( FMLPreInitializationEvent event ) {
		ModBlocks.init();
		proxy.preInit();
		LogHelper.info( "Pre-initialisation complete!" );
	}

	@EventHandler
	public void init( FMLInitializationEvent event ) {
		proxy.init();
		LogHelper.info( "Initialisation complete!" );
	}

	@EventHandler
	public void postInit( FMLPostInitializationEvent event ) {
		proxy.postInit();
		LogHelper.info( "Post-initialisation complete!" );
	}

}

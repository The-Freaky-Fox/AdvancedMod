package thefreakyfox.advancedmod;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import thefreakyfox.advancedmod.event.AdvancedModEventHandler;
import thefreakyfox.advancedmod.init.ModBlocks;
import thefreakyfox.advancedmod.init.ModTileEntities;
import thefreakyfox.advancedmod.network.DescriptionHandler;
import thefreakyfox.advancedmod.network.NetworkHandler;
import thefreakyfox.advancedmod.proxy.CommonProxy;
import thefreakyfox.advancedmod.reference.Reference;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;
import thefreakyfox.advancedmod.util.Log;
import thefreakyfox.advancedmod.world.gen.WorldGeneratorFlag;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLInterModComms.IMCEvent;
import cpw.mods.fml.common.event.FMLInterModComms.IMCMessage;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod( modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION )
public class AdvancedMod {

	@Instance( Reference.MOD_ID )
	public static AdvancedMod instance;

	@SidedProxy( clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS )
	public static CommonProxy proxy;

	@EventHandler
	public void preInit( FMLPreInitializationEvent event ) {
		ModBlocks.init();
		ModTileEntities.init();
		proxy.preInit();
		GameRegistry.registerWorldGenerator( new WorldGeneratorFlag(), 0 );
		NetworkHandler.init();
		DescriptionHandler.init();
		NetworkRegistry.INSTANCE.registerGuiHandler( instance, new GuiHandler() );
		MinecraftForge.EVENT_BUS.register( new AdvancedModEventHandler() );
		FMLCommonHandler.instance().bus().register( new AdvancedModEventHandler() );
		FMLInterModComms.sendMessage( Reference.MOD_ID, "camoMineBlacklist", new ItemStack( Blocks.stone ) );
		Log.info( "Pre-initialisation complete!" );
	}

	@EventHandler
	public void init( FMLInitializationEvent event ) {
		proxy.init();
		Log.info( "Initialisation complete!" );
	}

	@EventHandler
	public void postInit( FMLPostInitializationEvent event ) {
		proxy.postInit();
		Log.info( "Post-initialisation complete!" );
	}

	@EventHandler
	public void onIMCMessages( IMCEvent event ) {
		Log.info( "Receiving IMC" );
		for ( final IMCMessage message : event.getMessages() ) {
			if ( message.key.equalsIgnoreCase( "camoMineBlacklist" ) ) {
				if ( message.isItemStackMessage() ) {
					final ItemStack blacklistedStack = message.getItemStackValue();
					if ( blacklistedStack.getItem() != null ) {
						TileEntityCamoMine.camouflageBlacklist.add( blacklistedStack );
						Log.info( String.format( "Mod %s added %s to be blacklist as camouflage for the Camo Mine", message.getSender(),
								blacklistedStack.toString() ) );
					} else {
						throw new IllegalStateException( String.format( "ItemStack tried to be used in registry by the mod %s has a null item", message.getSender() ) );
					}
				} else {
					Log.warn( String.format( "Mod %s sent a non-ItemStack message, where an ItemStack message was expected.", message.getSender() ) );
				}

			} else {
				Log.warn( String.format( "Mod %s used an invalid IMC key: %s", message.getSender(), message.key ) );
			}
		}
	}
}

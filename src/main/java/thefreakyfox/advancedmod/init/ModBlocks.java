package thefreakyfox.advancedmod.init;

import thefreakyfox.advancedmod.block.BlockAdvancedMod;
import thefreakyfox.advancedmod.block.BlockDutchFlag;
import thefreakyfox.advancedmod.reference.Names;
import thefreakyfox.advancedmod.reference.Reference;
import thefreakyfox.advancedmod.util.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder( Reference.MOD_ID )
public class ModBlocks {

	public static final BlockAdvancedMod dutchFlag = new BlockDutchFlag();

	public static void init() {
		LogHelper.info( "ModBlocks called" );

		GameRegistry.registerBlock( dutchFlag, Names.Blocks.DUTCH_FLAG );
	}


}

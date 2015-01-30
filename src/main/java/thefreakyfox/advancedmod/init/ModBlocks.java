package thefreakyfox.advancedmod.init;

import thefreakyfox.advancedmod.reference.Reference;
import thefreakyfox.advancedmod.util.LogHelper;
import cpw.mods.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder( Reference.MOD_ID )
public class ModBlocks {

	public static void init() {
		LogHelper.info( "ModBlocks called" );
	}

}

package thefreakyfox.advancedmod.init;

import thefreakyfox.advancedmod.reference.Names;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;
import cpw.mods.fml.common.registry.GameRegistry;


public class ModTileEntities {

	public static void init() {
		GameRegistry.registerTileEntity( TileEntityCamoMine.class, Names.TileEntities.CAMO_MINE );
	}

}

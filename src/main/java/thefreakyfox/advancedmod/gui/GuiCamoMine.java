package thefreakyfox.advancedmod.gui;

import net.minecraft.entity.player.InventoryPlayer;
import thefreakyfox.advancedmod.inventory.ContainerCamoMine;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;


public class GuiCamoMine extends GuiAdvancedMod {

	public GuiCamoMine( InventoryPlayer playerInventory, TileEntityCamoMine te ) {
		super( new ContainerCamoMine( playerInventory, te ), "camoMine", te );
	}


}

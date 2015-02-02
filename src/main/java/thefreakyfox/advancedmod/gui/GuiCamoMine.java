package thefreakyfox.advancedmod.gui;

import net.minecraft.entity.player.InventoryPlayer;
import thefreakyfox.advancedmod.inventory.ContainerCamoMine;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;


public class GuiCamoMine extends GuiAdvancedMod {

	public GuiCamoMine( InventoryPlayer playerInventory, TileEntityCamoMine te ) {
		super( new ContainerCamoMine( playerInventory, te ) );
	}

	@Override
	protected void drawGuiContainerBackgroundLayer( float p_146976_1_, int p_146976_2_, int p_146976_3_ ) {
		// TODO Auto-generated method stub

	}

}

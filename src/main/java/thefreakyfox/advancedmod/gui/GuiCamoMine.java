package thefreakyfox.advancedmod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import thefreakyfox.advancedmod.inventory.ContainerCamoMine;
import thefreakyfox.advancedmod.network.MessageHandleGuiButtonPress;
import thefreakyfox.advancedmod.network.NetworkHandler;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;


public class GuiCamoMine extends GuiAdvancedMod {

	private final TileEntityCamoMine te;

	public GuiCamoMine( InventoryPlayer playerInventory, TileEntityCamoMine te ) {
		super( new ContainerCamoMine( playerInventory, te ), "camoMine", te );
		this.te = te;
	}

	// Note: is also called when screen size changes
	@Override
	public void initGui() {
		super.initGui();
		final GuiButton button = new GuiButton( 0, guiLeft + 10, guiTop + 37, 40, 20,
				I18n.format( "gui.advancedmod.camoMine.button.reset" ) );
		buttonList.add( button );
	}

	@Override
	protected void actionPerformed( GuiButton button ) {
		if ( button.id == 0 ) {
			NetworkHandler.sendToServer( new MessageHandleGuiButtonPress( te, 0 ) );
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int mouseX, int mouseY ) {
		super.drawGuiContainerForegroundLayer( mouseX, mouseY );
		// Colour: 0xAARRGGBB
		fontRendererObj.drawString( I18n.format( "gui.advancedmod.camoMine.timer", te.getTimer() ), 8, 25, 0xFF404040 );
	}


}

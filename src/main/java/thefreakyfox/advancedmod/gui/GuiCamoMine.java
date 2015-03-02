package thefreakyfox.advancedmod.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

import org.lwjgl.opengl.GL11;

import thefreakyfox.advancedmod.inventory.ContainerCamoMine;
import thefreakyfox.advancedmod.network.MessageHandleGuiButtonPress;
import thefreakyfox.advancedmod.network.MessageHandleTextUpdate;
import thefreakyfox.advancedmod.network.NetworkHandler;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;


public class GuiCamoMine extends GuiAdvancedMod {

	private final TileEntityCamoMine te;
	private GuiButton resetButton;
	private GuiTextField textField;

	public GuiCamoMine( InventoryPlayer playerInventory, TileEntityCamoMine te ) {
		super( new ContainerCamoMine( playerInventory, te ), "camoMine", te );
		this.te = te;
	}

	// Note: is also called when screen size changes
	@Override
	public void initGui() {
		super.initGui();
		resetButton = new GuiButton( 0, guiLeft + 10, guiTop + 37, 40, 20, "" );
		buttonList.add( resetButton );

		textField = new GuiTextField( fontRendererObj, guiLeft + 100, guiTop + 65, 70, 12 );
		textField.setMaxStringLength( 40 );
		textField.setText( te.getTarget() );
	}

	@Override
	public void onTextfieldUpdate( int id ) {
		if ( id == 0 )
			textField.setText( te.getTarget() );
	}

	@Override
	protected void keyTyped( char chr, int keyCode ) {
		if ( textField.textboxKeyTyped( chr, keyCode ) ) {
			NetworkHandler.sendToServer( new MessageHandleTextUpdate( te, 0, textField.getText() ) );
		} else {
			super.keyTyped( chr, keyCode );
		}
	}

	/**
	 * Called when the mouse is clicked.
	 */
	@Override
	protected void mouseClicked( int mouseX, int mouseY, int partialTick ) {
		super.mouseClicked( mouseX, mouseY, partialTick );
		textField.mouseClicked( mouseX, mouseY, partialTick );
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen( int mouseX, int mouseY, float partialTick ) {
		super.drawScreen( mouseX, mouseY, partialTick );
		GL11.glDisable( GL11.GL_LIGHTING );
		GL11.glDisable( GL11.GL_BLEND );
		textField.drawTextBox();
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
		fontRendererObj.drawString( I18n.format( "gui.advancedmod.camoMine.target", te.getTimer() ), 120, 55, 0xFF404040 );
		if ( te.getTimer() >= 0 )
			fontRendererObj.drawString( I18n.format( "gui.advancedmod.camoMine.timer", te.getTimer() ), 8, 25, 0xFF404040 );
	}

	@Override
	public void updateScreen() {
		super.updateScreen();
		resetButton.displayString = I18n.format( "gui.advancedmod.camoMine.button."
				+ ( te.getTimer() == -1 ? "restart" : "reset" ) );
	}

}

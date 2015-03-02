package thefreakyfox.advancedmod.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import thefreakyfox.advancedmod.gui.GuiAdvancedMod;
import thefreakyfox.advancedmod.tileentity.TileEntityAdvancedMod;
import cpw.mods.fml.common.network.ByteBufUtils;


public class MessageHandleTextUpdate extends MessageXYZ <MessageHandleTextUpdate> {

	private int id;
	private String text;

	public MessageHandleTextUpdate() {}

	public MessageHandleTextUpdate( TileEntityAdvancedMod te, int id, String text ) {
		super( te );
		this.id = id;
		this.text = text;
	}

	@Override
	public void fromBytes( ByteBuf buf ) {
		super.fromBytes( buf );
		id = buf.readInt();
		text = ByteBufUtils.readUTF8String( buf );
	}

	@Override
	public void toBytes( ByteBuf buf ) {
		super.toBytes( buf );
		buf.writeInt( id );
		ByteBufUtils.writeUTF8String( buf, text );
	}

	@Override
	public void handleClientSide( MessageHandleTextUpdate message, EntityPlayer player ) {
		handleServerSide( message, player );
		final GuiScreen gui = Minecraft.getMinecraft().currentScreen;
		if ( gui instanceof GuiAdvancedMod ) {
			( ( GuiAdvancedMod ) gui ).onTextfieldUpdate( message.id );
		}
	}

	@Override
	public void handleServerSide( MessageHandleTextUpdate message, EntityPlayer player ) {
		final TileEntity te = message.getTileEntity( player.worldObj );
		if ( te instanceof TileEntityAdvancedMod ) {
			( ( TileEntityAdvancedMod ) te ).onGuiTextfieldUpdate( message.id, message.text );
		}
	}

}

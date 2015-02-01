package thefreakyfox.advancedmod.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.minecraft.tileentity.TileEntity;
import thefreakyfox.advancedmod.AdvancedMod;
import thefreakyfox.advancedmod.reference.Reference;
import thefreakyfox.advancedmod.tileentity.TileEntityAdvancedMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;

@Sharable
public class DescriptionHandler extends SimpleChannelInboundHandler <FMLProxyPacket> {

	public static final String CHANNEL = Reference.MOD_ID + ":Description";

	static {
		NetworkRegistry.INSTANCE.newChannel( CHANNEL, new DescriptionHandler() );
	}


	public static void init() {
		// not actually doing anything here, apart from loading the class. If the channel registry
		// goes in here, Netty will throw a duplicate channel error
	}

	@Override
	protected void channelRead0( ChannelHandlerContext ctx, FMLProxyPacket msg ) throws Exception {
		final ByteBuf buf = msg.payload();
		final int x = buf.readInt();
		final int y = buf.readInt();
		final int z = buf.readInt();

		final TileEntity te = AdvancedMod.proxy.getClientPlayer().worldObj.getTileEntity( x, y, z );
		if ( te instanceof TileEntityAdvancedMod && te != null ) {
			( ( TileEntityAdvancedMod ) te ).readFromPacket( buf );
		}
	}
}

package thefreakyfox.advancedmod.thirdparty.waila;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import thefreakyfox.advancedmod.init.ModBlocks;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;


public class WailaCamoHandler implements IWailaDataProvider {

	@Override
	public ItemStack getWailaStack( IWailaDataAccessor accessor, IWailaConfigHandler config ) {
		final TileEntity te = accessor.getTileEntity();
		if ( te instanceof TileEntityCamoMine ) {
			final TileEntityCamoMine teMine = ( TileEntityCamoMine ) te;

			final int side = accessor.getSide().ordinal();
			final ItemStack stack = teMine.getCamoStack( side );
			if ( stack != null && stack.getItem() instanceof ItemBlock )
				return stack;
		}

		return new ItemStack( ModBlocks.camoMine );
	}

	@Override
	public List <String> getWailaHead( ItemStack itemStack, List <String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config ) {
		return currenttip;
	}

	@Override
	public List <String> getWailaBody( ItemStack itemStack, List <String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config ) {
		final NBTTagCompound tag = accessor.getNBTData();
		final String target = tag.getString( "target" );
		if ( !target.equals( "" ) ) {
			currenttip.add( I18n.format( "advancedmod.waila.camoMine.target" ) + ' ' + EnumChatFormatting.GREEN + target );
		}
		final int timer = tag.getInteger( "timer" );
		if ( timer == 0 ) {
			currenttip.add( I18n.format( "advancedMod.waila.camoMine.primed" ) );
		} else if ( timer == -1 ) {
			currenttip.add( I18n.format( "advancedMod.waila.camoMine.notPrimed" ) );
		} else {
			currenttip.add( I18n.format( "advancedMod.waila.camoMine.primingIn" ) + ' ' + timer );
		}

		return currenttip;
	}

	@Override
	public List <String> getWailaTail( ItemStack itemStack, List <String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config ) {
		return currenttip;
	}

	@Override
	public NBTTagCompound getNBTData( EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z ) {
		final TileEntityCamoMine teMine = ( TileEntityCamoMine ) te;
		tag.setString( "target", teMine.getTarget() );
		tag.setInteger( "timer", teMine.getTimer() );
		return tag;
	}

}

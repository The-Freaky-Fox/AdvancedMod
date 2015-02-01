package thefreakyfox.advancedmod.tileentity;

import io.netty.buffer.ByteBuf;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.common.network.ByteBufUtils;


public class TileEntityCamoMine extends TileEntityAdvancedMod {

	private int timer = 60;
	private ItemStack camoStack;

	@Override
	public void updateEntity() {
		if ( timer > 0 )
			timer-- ;
		if ( timer == 0 && !worldObj.isRemote ) {
			final List <Entity> entities = worldObj.getEntitiesWithinAABB( EntityPlayer.class,
					AxisAlignedBB.getBoundingBox( xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2 ) );
			if ( entities.size() > 0 ) {
				worldObj.createExplosion( null, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 3.0F, true );
			}
		}
	}

	public ItemStack getCamoStack() {
		return camoStack;
	}

	public void setCamoStack( ItemStack stack ) {
		camoStack = stack;
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	}

	@Override
	public void writeToPacket( ByteBuf buf ) {
		ByteBufUtils.writeItemStack( buf, camoStack );
	}

	@Override
	public void readFromPacket( ByteBuf buf ) {
		camoStack = ByteBufUtils.readItemStack( buf );
		worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
	}

	@Override
	public void writeToNBT( NBTTagCompound tag ) {
		super.writeToNBT( tag );
		tag.setInteger( "timer", timer );

		if ( camoStack != null ) {
			final NBTTagCompound t = new NBTTagCompound();
			camoStack.writeToNBT( t );
			tag.setTag( "camoStack", t );
		}
	}

	@Override
	public void readFromNBT( NBTTagCompound tag ) {
		super.readFromNBT( tag );
		timer = tag.getInteger( "timer" );

		if ( tag.hasKey( "camoStack" ) ) {
			camoStack = ItemStack.loadItemStackFromNBT( tag.getCompoundTag( "camoStack" ) );
		} else {
			camoStack = null;
		}
	}

}

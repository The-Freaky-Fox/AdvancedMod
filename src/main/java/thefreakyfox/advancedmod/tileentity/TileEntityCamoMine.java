package thefreakyfox.advancedmod.tileentity;

import io.netty.buffer.ByteBuf;

import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import thefreakyfox.advancedmod.init.ModBlocks;
import cpw.mods.fml.common.network.ByteBufUtils;


public class TileEntityCamoMine extends TileEntityAdvancedMod implements ISidedInventory {

	private int timer = 60;
	private String target = "";
	private final ItemStack[] camoStacks = new ItemStack[6];

	@Override
	public void updateEntity() {
		if ( timer > 0 )
			timer-- ;
		if ( timer == 0 && !worldObj.isRemote ) {
			final List <Entity> entities = worldObj.getEntitiesWithinAABB( EntityPlayer.class,
					AxisAlignedBB.getBoundingBox( xCoord - 1, yCoord - 1, zCoord - 1, xCoord + 2, yCoord + 2, zCoord + 2 ) );
			for ( final Entity entity : entities ) {
				if ( target.equals( "" ) || entity.getCommandSenderName().equalsIgnoreCase( target ) ) {
					worldObj.createExplosion( null, xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D, 3.0F, true );
					break;
				}
			}
		}
	}


	public String getTarget() {
		return target;
	}

	public void setTarget( String target ) {
		this.target = target;
		markDirty();
	}

	public void setTimer( int value ) {
		timer = value;
		markDirty();
	}

	public int getTimer() {
		return timer;
	}

	@Override
	public void onGuiButtonPress( int id ) {
		if ( id == 0 ) {
			if ( timer == -1 ) { // if not armed, start arming.
				setTimer( 60 );
			} else {
				// if armed, stop being armed.
				setTimer( -1 );
			}
		}
	}

	@Override
	public void onGuiTextfieldUpdate( int id, String text ) {
		if ( id == 0 ) {
			setTarget( text );
		}
	}

	public ItemStack getCamoStack( int side ) {
		return getStackInSlot( side );
	}

	public void setCamoStack( ItemStack stack, int side ) {
		setInventorySlotContents( side, stack );
	}

	@Override
	public void writeToPacket( ByteBuf buf ) {
		for ( final ItemStack stack : camoStacks )
			ByteBufUtils.writeItemStack( buf, stack );
	}

	@Override
	public void readFromPacket( ByteBuf buf ) {
		for ( int i = 0; i < camoStacks.length; i++ )
			camoStacks[i] = ByteBufUtils.readItemStack( buf );
		worldObj.markBlockRangeForRenderUpdate( xCoord, yCoord, zCoord, xCoord, yCoord, zCoord );
	}

	@Override
	public void writeToNBT( NBTTagCompound tag ) {
		super.writeToNBT( tag );
		tag.setInteger( "timer", timer );
		tag.setString( "target", target );

		final NBTTagList camoStackTag = new NBTTagList();

		for ( int i = 0; i < camoStacks.length; i++ ) {
			final ItemStack stack = camoStacks[i];
			if ( stack != null ) {
				final NBTTagCompound t = new NBTTagCompound();
				stack.writeToNBT( t );
				t.setByte( "index", ( byte ) i );
				camoStackTag.appendTag( t );
			}
		}
		tag.setTag( "camoStacks", camoStackTag );
	}

	@Override
	public void readFromNBT( NBTTagCompound tag ) {
		super.readFromNBT( tag );
		timer = tag.getInteger( "timer" );
		target = tag.getString( "target" );

		Arrays.fill( camoStacks, null );
		final NBTTagList camoStackTag = tag.getTagList( "camoStacks", 10 );

		for ( int i = 0; i < camoStackTag.tagCount(); i++ ) {
			final NBTTagCompound t = camoStackTag.getCompoundTagAt( i );
			final int index = t.getByte( "index" );
			if ( index >= 0 && index < camoStacks.length )
				camoStacks[index] = ItemStack.loadItemStackFromNBT( t );
		}
	}


	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory() {
		return camoStacks.length;
	}

	/**
	 * Returns the stack in slot i
	 */
	@Override
	public ItemStack getStackInSlot( int slot ) {
		return camoStacks[slot];
	}

	/**
	 * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and
	 * returns them in a
	 * new stack.
	 */
	@Override
	public ItemStack decrStackSize( int slot, int amount ) {
		if ( camoStacks[slot] != null ) {
			ItemStack itemstack;

			if ( camoStacks[slot].stackSize <= amount ) {
				itemstack = camoStacks[slot];
				setInventorySlotContents( slot, null );
				markDirty();
				return itemstack;
			} else {
				itemstack = camoStacks[slot].splitStack( amount );

				if ( camoStacks[slot].stackSize == 0 ) {
					setInventorySlotContents( slot, null );
				}

				markDirty();
				return itemstack;
			}
		} else {
			return null;
		}
	}

	/**
	 * When some containers are closed they call this on each slot, then drop whatever it returns as
	 * an EntityItem -
	 * like when you close a workbench GUI.
	 */
	@Override
	public ItemStack getStackInSlotOnClosing( int slot ) {
		if ( camoStacks[slot] != null ) {
			final ItemStack itemstack = camoStacks[slot];
			camoStacks[slot] = null;
			return itemstack;
		} else {
			return null;
		}
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor
	 * sections).
	 */
	@Override
	public void setInventorySlotContents( int slot, ItemStack stack ) {
		camoStacks[slot] = stack;

		if ( stack != null && stack.stackSize > getInventoryStackLimit() ) {
			stack.stackSize = getInventoryStackLimit();
		}

		markDirty();
		worldObj.markBlockForUpdate( xCoord, yCoord, zCoord );
	}

	/**
	 * Returns the name of the inventory
	 */
	@Override
	public String getInventoryName() {
		return ModBlocks.camoMine.getUnlocalizedName() + ".name";
	}

	/**
	 * Returns if the inventory is named
	 */
	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	/**
	 * Returns the maximum stack size for a inventory slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	/**
	 * Do not make give this method the name canInteractWith because it clashes with Container
	 */
	@Override
	public boolean isUseableByPlayer( EntityPlayer player ) {
		return worldObj.getTileEntity( xCoord, yCoord, zCoord ) != this ? false : player.getDistanceSq( xCoord + 0.5D,
				yCoord + 0.5D, zCoord + 0.5D ) <= 64.0D;
	}

	@Override
	public void openInventory() {}

	@Override
	public void closeInventory() {}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into
	 * the given slot.
	 */
	@Override
	public boolean isItemValidForSlot( int slot, ItemStack stack ) {
		return stack != null && stack.getItem() instanceof ItemBlock;
	}

	@Override
	public int[] getAccessibleSlotsFromSide( int side ) {
		return new int[] { side };
	}

	@Override
	public boolean canInsertItem( int slot, ItemStack stack, int side ) {
		return isItemValidForSlot( slot, stack );
	}

	@Override
	public boolean canExtractItem( int slot, ItemStack stack, int side ) {
		return true;
	}


}

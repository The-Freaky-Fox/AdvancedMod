package thefreakyfox.advancedmod.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;


public class ContainerCamoMine extends ContainerAdvancedMod {

	private final TileEntityCamoMine te;

	public ContainerCamoMine( InventoryPlayer playerInventory, TileEntityCamoMine te ) {

		addSlotToContainer( new Slot( te, 0, 80, 58 ) );
		addSlotToContainer( new Slot( te, 1, 80, 22 ) );
		addSlotToContainer( new Slot( te, 2, 80, 40 ) );
		addSlotToContainer( new Slot( te, 3, 62, 40 ) );
		addSlotToContainer( new Slot( te, 4, 98, 40 ) );
		addSlotToContainer( new Slot( te, 5, 102, 18 ) );

		addPlayerSlots( playerInventory, 8, 84 );
		this.te = te;
	}

	@Override
	public boolean canInteractWith( EntityPlayer player ) {
		return te.isUseableByPlayer( player );
	}

	@Override
	public ItemStack transferStackInSlot( EntityPlayer player, int slot ) {
		return null;
	}

}

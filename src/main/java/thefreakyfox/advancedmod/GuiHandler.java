package thefreakyfox.advancedmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import thefreakyfox.advancedmod.gui.GuiCamoMine;
import thefreakyfox.advancedmod.inventory.ContainerCamoMine;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;
import cpw.mods.fml.common.network.IGuiHandler;


public class GuiHandler implements IGuiHandler {

	public enum GuiIDs {
		CAMO_MINE;
	}

	@Override
	public Object getServerGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z ) {
		switch ( GuiIDs.values()[ID] ) {
			case CAMO_MINE:
				return new ContainerCamoMine( player.inventory, ( TileEntityCamoMine ) world.getTileEntity( x, y, z ) );
		}
		throw new IllegalArgumentException( "No gui with id: " + ID );
	}

	@Override
	public Object getClientGuiElement( int ID, EntityPlayer player, World world, int x, int y, int z ) {
		switch ( GuiIDs.values()[ID] ) {
			case CAMO_MINE:
				return new GuiCamoMine( player.inventory, ( TileEntityCamoMine ) world.getTileEntity( x, y, z ) );
		}
		throw new IllegalArgumentException( "No gui with id: " + ID );
	}

}

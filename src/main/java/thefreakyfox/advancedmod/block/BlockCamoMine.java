package thefreakyfox.advancedmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import thefreakyfox.advancedmod.AdvancedMod;
import thefreakyfox.advancedmod.GuiHandler;
import thefreakyfox.advancedmod.reference.Names;
import thefreakyfox.advancedmod.reference.Reference;
import thefreakyfox.advancedmod.tileentity.TileEntityCamoMine;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockCamoMine extends BlockAdvancedModTileEntity {

	public BlockCamoMine() {
		setBlockName( Names.Blocks.CAMO_MINE );
		setBlockTextureName( Reference.MOD_ID_LOWER + ':' + Names.Blocks.DUTCH_FLAG );
	}

	@Override
	public TileEntity createNewTileEntity( World world, int meta ) {
		return new TileEntityCamoMine();
	}


	@Override
	public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int side, float hitX,
			float hitY, float hitZ ) {
		if ( !world.isRemote ) {
			if ( player.isSneaking() ) {
				player.openGui( AdvancedMod.instance, GuiHandler.GuiIDs.CAMO_MINE.ordinal(), world, x, y, z );
			} else {
				final TileEntity te = world.getTileEntity( x, y, z );
				if ( te instanceof TileEntityCamoMine ) {
					final TileEntityCamoMine teMine = ( TileEntityCamoMine ) te;

					if ( teMine.getCamoStack( side ) != null ) {
						final ItemStack camoStack = teMine.getCamoStack( side );
						teMine.setCamoStack( null, side );
						final EntityItem itemEntity = new EntityItem( world, x, y, z, camoStack );
						world.spawnEntityInWorld( itemEntity );
					} else {
						final ItemStack playerItem = player.getCurrentEquippedItem();
						if ( playerItem != null ) {
							final ItemStack camoStack = playerItem.splitStack( 1 );
							teMine.setCamoStack( camoStack, side );
						}
					}
				}
			}
		}
		return true;
	}

	@SideOnly( Side.CLIENT )
	@Override
	public IIcon getIcon( IBlockAccess world, int x, int y, int z, int side ) {
		final TileEntity te = world.getTileEntity( x, y, z );
		if ( te instanceof TileEntityCamoMine && te != null ) {
			final TileEntityCamoMine teMine = ( TileEntityCamoMine ) te;
			final ItemStack stack = teMine.getCamoStack( side );
			if ( stack != null && stack.getItem() instanceof ItemBlock ) {
				final Block block = ( ( ItemBlock ) stack.getItem() ).field_150939_a;
				return block.getIcon( side, stack.getItemDamage() );
			} else {
				return super.getIcon( world, x, y, z, side );
			}
		}

		return null;
	}

}

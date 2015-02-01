package thefreakyfox.advancedmod.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
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
			final TileEntity te = world.getTileEntity( x, y, z );
			if ( te instanceof TileEntityCamoMine && te != null ) {
				final TileEntityCamoMine teMine = ( TileEntityCamoMine ) te;
				teMine.setCamoStack( player.getCurrentEquippedItem() );
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
			final ItemStack stack = teMine.getCamoStack();
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

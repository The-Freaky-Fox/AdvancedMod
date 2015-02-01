package thefreakyfox.advancedmod.block;

import java.util.Random;

import net.minecraft.world.World;
import thefreakyfox.advancedmod.reference.Names;
import thefreakyfox.advancedmod.reference.Reference;


public class BlockDutchFlag extends BlockAdvancedMod {

	public BlockDutchFlag() {
		setBlockName( Names.Blocks.DUTCH_FLAG );
		setBlockTextureName( Reference.MOD_ID_LOWER + ':' + Names.Blocks.DUTCH_FLAG );
		setTickRandomly( true );
	}

	@Override
	public void updateTick( World world, int x, int y, int z, Random random ) {
		if ( random.nextInt( 10 ) == 0 )
			world.playSoundEffect( x, y, z, "advancedmod:fagFlap", 1.0F, random.nextFloat() + 0.5F );
	}

	// @Override
	// public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int
	// side, float hitX,
	// float hitY, float hitZ ) {
	// world.playSoundEffect( x, y, z, "advancedmod:flagFlap", 1.0F, player.getRNG().nextFloat() +
	// 0.5F );
	// return true;
	// }

}

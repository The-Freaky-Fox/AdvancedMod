package thefreakyfox.advancedmod.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import thefreakyfox.advancedmod.init.ModBlocks;
import cpw.mods.fml.common.IWorldGenerator;


public class WorldGeneratorFlag implements IWorldGenerator {

	private WorldGenMinable flagGen = new WorldGenMinable( ModBlocks.dutchFlag, 32 );

	@Override
	public void generate( Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider ) {
		final int x = chunkX * 16;
		final int z = chunkZ * 16;

		switch ( world.provider.dimensionId ) {
			case 0:
				generateSurface( world, x, z, random );
				break;
			case -1:
				generateNether( world, x, z, random );
				break;
			case 1:
				generateEnd( world, x, z, random );
				break;
			default:
				generateSurface( world, x, z, random );
		}
	}

	public void generateNether( World world, int x, int z, Random random ) {}

	public void generateEnd( World world, int x, int z, Random random ) {}

	public void generateSurface( World world, int x, int z, Random random ) {
		if ( random.nextInt( 10 ) == 0 ) {
			final int randX = x + random.nextInt( 16 );
			final int randZ = z + random.nextInt( 16 );
			final int randY = world.getHeightValue( randX, randZ );
			if ( !BiomeDictionary.isBiomeOfType( world.getBiomeGenForCoords( randX, randZ ), Type.WATER ) ) {
				Block block = world.getBlock( randX, randY - 1, randZ );
				if ( block != Blocks.water && block != Blocks.lava ) {
					generateFlag( world, randX, randY, randZ );
				}
			}
		}

		for ( int i = 0; i < 5; i++ ) {
			int randX = x + random.nextInt( 16 );
			int randY = 20 + random.nextInt( 40 );
			int randZ = z + random.nextInt( 16 );
			flagGen.generate( world, random, randX, randY, randZ );
		}

	}

	private void generateFlag( World world, int x, int y, int z ) {
		for ( int i = 0; i < 10; i++ ) {
			world.setBlock( x, y + i, z, Blocks.fence, 0, 2 );
		}

		for ( int dx = 0; dx < 3; dx++ ) {
			for ( int dy = 0; dy < 3; dy++ ) {
				world.setBlock( x + 1 + dx, y + 7 + dy, z, ModBlocks.dutchFlag );
			}
		}
	}
}

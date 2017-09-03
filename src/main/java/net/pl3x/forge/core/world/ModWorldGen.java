package net.pl3x.forge.core.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.pl3x.forge.core.block.ModBlocks;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        // Overworld (0)
        if (world.provider.getDimension() == 0) {
            runGenerator(world, random, chunkX, chunkZ, ModBlocks.oreRuby.getDefaultState(), 1, 6, 0, 11);
        }
    }

    /**
     * @param world       World
     * @param random      Random
     * @param chunk_X     X chunk coord
     * @param chunk_Z     Z chunk coord
     * @param ore         Ore block to generate
     * @param maxChances  Max number of veins per chunk
     * @param maxVeinSize Max number of ores per vein
     * @param minY        Min height
     * @param maxY        Max height
     */
    private void runGenerator(World world, Random random, int chunk_X, int chunk_Z, IBlockState ore, int maxChances, int maxVeinSize, int minY, int maxY) {
        if (minY < 0 || maxY > 255 || minY > maxY) {
            throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
        }

        int heightDiff = maxY - minY + 1;
        for (int i = 0; i < maxChances; i++) {
            new WorldGenMinable(ore, random.nextInt(maxVeinSize))
                    .generate(world, random, new BlockPos(
                            (chunk_X << 4) + random.nextInt(16),
                            minY + random.nextInt(heightDiff),
                            (chunk_Z << 4) + random.nextInt(16)));
        }
    }
}

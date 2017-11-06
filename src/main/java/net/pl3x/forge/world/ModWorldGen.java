package net.pl3x.forge.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public class ModWorldGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        // Overworld (0)
        if (world.provider.getDimension() == 0) {
            runGenerator(world, random, chunkX, chunkZ, ModBlocks.RUBY.getDefaultState(), 1, 6, 0, 11);
        }
    }

    /**
     * @param world       World
     * @param random      Random
     * @param chunkX      X chunk coord
     * @param chunkZ      Z chunk coord
     * @param ore         Ore block to generate
     * @param maxChances  Max number of veins per chunk
     * @param maxVeinSize Max number of ores per vein
     * @param minY        Min height
     * @param maxY        Max height
     */
    private void runGenerator(World world, Random random, int chunkX, int chunkZ, IBlockState ore, int maxChances, int maxVeinSize, int minY, int maxY) {
        int heightDiff = maxY - minY + 1;
        for (int i = 0; i < maxChances; i++) {
            new WorldGenMinable(ore, random.nextInt(maxVeinSize))
                    .generate(world, random, new BlockPos(
                            (chunkX << 4) + random.nextInt(16),
                            minY + random.nextInt(heightDiff),
                            (chunkZ << 4) + random.nextInt(16)));
        }
    }
}

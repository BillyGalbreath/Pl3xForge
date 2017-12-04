package net.pl3x.forge.color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;

@SideOnly(Side.CLIENT)
public class ModColorManager {
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    public static void registerColorHandlers() {
        final BlockColors blockColors = minecraft.getBlockColors();
        final ItemColors itemColors = minecraft.getItemColors();

        registerBlockColorHandlers(blockColors);
        registerItemColorHandlers(blockColors, itemColors);
    }

    private static void registerBlockColorHandlers(final BlockColors blockColors) {
        // Use the grass color of the biome or the default grass color
        final IBlockColor grassColorHandler = (state, blockAccess, pos, tintIndex) -> {
            if (blockAccess != null && pos != null) {
                return BiomeColorHelper.getGrassColorAtPos(blockAccess, pos);
            }
            return ColorizerGrass.getGrassColor(0.5D, 1.0D);
        };

        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_BLACK);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_BLUE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_BROWN);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_CYAN);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_GRAY);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_GREEN);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_LIGHT_BLUE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_LIME);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_MAGENTA);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_ORANGE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_PINK);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_PURPLE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_RED);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SILVER);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_WHITE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_YELLOW);

        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_BLACK);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_BLUE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_BROWN);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_CYAN);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_GRAY);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_GREEN);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_LIGHT_BLUE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_LIME);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_MAGENTA);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_ORANGE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_PINK);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_PURPLE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_RED);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_SILVER);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_WHITE);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.CURB_SLAB_YELLOW);

        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.GRASS_SLAB);
        blockColors.registerBlockColorHandler(grassColorHandler, ModBlocks.GRASS_SLAB_DOUBLE);
    }

    private static void registerItemColorHandlers(final BlockColors blockColors, final ItemColors itemColors) {
        // Use the Block's color handler for an ItemBlock
        final IItemColor itemBlockColorHandler = (stack, tintIndex) ->
                blockColors.colorMultiplier(((ItemBlock) stack.getItem()).getBlock()
                        .getStateFromMeta(stack.getMetadata()), null, null, tintIndex);

        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_BLACK);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_BLUE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_BROWN);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_CYAN);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_GRAY);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_GREEN);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_LIGHT_BLUE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_LIME);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_MAGENTA);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_ORANGE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_PINK);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_PURPLE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_RED);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SILVER);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_WHITE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_YELLOW);

        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_BLACK);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_BLUE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_BROWN);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_CYAN);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_GRAY);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_GREEN);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_LIGHT_BLUE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_LIME);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_MAGENTA);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_ORANGE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_PINK);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_PURPLE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_RED);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_SILVER);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_WHITE);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.CURB_SLAB_YELLOW);

        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.GRASS_SLAB);
        itemColors.registerItemColorHandler(itemBlockColorHandler, ModBlocks.GRASS_SLAB_DOUBLE);
    }
}

package net.pl3x.forge.block.custom.slab;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;

import java.util.Random;

public abstract class BlockConcreteSlab extends BlockSlab {
    private static final PropertyEnum<BlockConcreteSlab.Variant> VARIANT = PropertyEnum.create("variant", BlockConcreteSlab.Variant.class);

    private final String name;
    private final EnumDyeColor color;

    public BlockConcreteSlab(String name, EnumDyeColor color) {
        super(Material.ROCK);

        this.name = name;
        this.color = color;

        setUnlocalizedName(name);
        setRegistryName(name);
        setSoundType(SoundType.STONE);
        setHardness(2.0F);
        useNeighborBrightness = true;

        IBlockState iblockstate = blockState.getBaseState();
        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, net.minecraft.block.BlockSlab.EnumBlockHalf.BOTTOM);
        }
        setDefaultState(iblockstate);

        ModBlocks.__BLOCKS__.add(this);
    }

    public void registerItemModel(Item item) {
        Pl3x.proxy.registerItemRenderer(item, 0, name);
    }

    public Item createItemBlock() {
        BlockConcreteSlab half = getHalfBlock(color);
        return new ItemSlab(half, half, getDoubleBlock(color)).setRegistryName(getRegistryName());
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(getHalfBlock(color));
    }

    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
        return new ItemStack(getHalfBlock(color));
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.getBlockColor(color);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState().withProperty(VARIANT, BlockConcreteSlab.Variant.DEFAULT);
        if (!isDouble()) {
            iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }
        return iblockstate;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int i = 0;
        if (!isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    public String getUnlocalizedName(int meta) {
        return this.getUnlocalizedName();
    }

    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    public Comparable<?> getTypeForItem(ItemStack stack) {
        return BlockConcreteSlab.Variant.DEFAULT;
    }

    public enum Variant implements IStringSerializable {
        DEFAULT;

        public String getName() {
            return "default";
        }
    }

    private static BlockConcreteSlab getHalfBlock(EnumDyeColor color) {
        switch (color) {
            case BLACK:
                return ModBlocks.CONCRETE_SLAB_BLACK;
            case BLUE:
                return ModBlocks.CONCRETE_SLAB_BLUE;
            case BROWN:
                return ModBlocks.CONCRETE_SLAB_BROWN;
            case CYAN:
                return ModBlocks.CONCRETE_SLAB_CYAN;
            case GRAY:
                return ModBlocks.CONCRETE_SLAB_GRAY;
            case GREEN:
                return ModBlocks.CONCRETE_SLAB_GREEN;
            case LIGHT_BLUE:
                return ModBlocks.CONCRETE_SLAB_LIGHT_BLUE;
            case LIME:
                return ModBlocks.CONCRETE_SLAB_LIME;
            case MAGENTA:
                return ModBlocks.CONCRETE_SLAB_MAGENTA;
            case ORANGE:
                return ModBlocks.CONCRETE_SLAB_ORANGE;
            case PINK:
                return ModBlocks.CONCRETE_SLAB_PINK;
            case PURPLE:
                return ModBlocks.CONCRETE_SLAB_PURPLE;
            case RED:
                return ModBlocks.CONCRETE_SLAB_RED;
            case SILVER:
                return ModBlocks.CONCRETE_SLAB_SILVER;
            case YELLOW:
                return ModBlocks.CONCRETE_SLAB_YELLOW;
            case WHITE:
            default:
                return ModBlocks.CONCRETE_SLAB_WHITE;
        }
    }

    private static BlockConcreteSlab getDoubleBlock(EnumDyeColor color) {
        switch (color) {
            case BLACK:
                return ModBlocks.CONCRETE_SLAB_BLACK_DOUBLE;
            case BLUE:
                return ModBlocks.CONCRETE_SLAB_BLUE_DOUBLE;
            case BROWN:
                return ModBlocks.CONCRETE_SLAB_BROWN_DOUBLE;
            case CYAN:
                return ModBlocks.CONCRETE_SLAB_CYAN_DOUBLE;
            case GRAY:
                return ModBlocks.CONCRETE_SLAB_GRAY_DOUBLE;
            case GREEN:
                return ModBlocks.CONCRETE_SLAB_GREEN_DOUBLE;
            case LIGHT_BLUE:
                return ModBlocks.CONCRETE_SLAB_LIGHT_BLUE_DOUBLE;
            case LIME:
                return ModBlocks.CONCRETE_SLAB_LIME_DOUBLE;
            case MAGENTA:
                return ModBlocks.CONCRETE_SLAB_MAGENTA_DOUBLE;
            case ORANGE:
                return ModBlocks.CONCRETE_SLAB_ORANGE_DOUBLE;
            case PINK:
                return ModBlocks.CONCRETE_SLAB_PINK_DOUBLE;
            case PURPLE:
                return ModBlocks.CONCRETE_SLAB_PURPLE_DOUBLE;
            case RED:
                return ModBlocks.CONCRETE_SLAB_RED_DOUBLE;
            case SILVER:
                return ModBlocks.CONCRETE_SLAB_SILVER_DOUBLE;
            case YELLOW:
                return ModBlocks.CONCRETE_SLAB_YELLOW_DOUBLE;
            case WHITE:
            default:
                return ModBlocks.CONCRETE_SLAB_WHITE_DOUBLE;
        }
    }
}

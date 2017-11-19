package net.pl3x.forge.item.custom.seed;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.item.ModItems;

import java.util.List;

public class ItemEnderPearlSeeds extends ItemSeeds {
    public ItemEnderPearlSeeds() {
        super(ModBlocks.CROP_ENDER_PEARL, ModBlocks.TILLED_END_STONE);
        setUnlocalizedName("enderpearl_seeds");
        setRegistryName("enderpearl_seeds");

        setCreativeTab(CreativeTabs.MISC);

        ModItems.items.add(this);
    }

    public void registerItemModel() {
        Pl3x.proxy.registerItemRenderer(this, 0, "enderpearl_seeds");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (stack.getItem() instanceof ItemSeeds) {
            tooltip.add(I18n.format("enderpearl_crop.tip.seed"));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack itemstack = player.getHeldItem(hand);
        IBlockState state = world.getBlockState(pos);
        if (facing == EnumFacing.UP && player.canPlayerEdit(pos.offset(facing), facing, itemstack) &&
                state.getBlock() == ModBlocks.TILLED_END_STONE && world.isAirBlock(pos.up())) {
            world.setBlockState(pos.up(), getPlant(world, pos));
            if (player instanceof EntityPlayerMP) {
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos.up(), itemstack);
            }
            itemstack.shrink(1);
            return EnumActionResult.SUCCESS;
        } else {
            return EnumActionResult.FAIL;
        }
    }

    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }
}

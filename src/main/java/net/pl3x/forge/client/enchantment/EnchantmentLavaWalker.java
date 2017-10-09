package net.pl3x.forge.client.enchantment;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.client.block.ModBlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EnchantmentLavaWalker extends Enchantment {
    private static Map<UUID, BlockPos> playerPositionTracker = new HashMap<>();

    public EnchantmentLavaWalker() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_FEET, new EntityEquipmentSlot[]{EntityEquipmentSlot.FEET});
        setName("lavaWalker");
        setRegistryName(getName());

        MinecraftForge.EVENT_BUS.register(this);
    }

    private static void freezeLava(EntityLivingBase entity, World world, BlockPos pos, int level) {
        if (!entity.onGround) {
            return;
        }
        float f = (float) Math.min(16, 2 + level);
        float f2 = f * f;
        BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(0, 0, 0);
        for (BlockPos.MutableBlockPos pos2 : BlockPos.getAllInBoxMutable(pos.add(-f, -1.0D, -f), pos.add(f, -1.0D, f))) {
            if (pos2.distanceSqToCenter(entity.posX, entity.posY, entity.posZ) <= f2) {
                pos1.setPos(pos2.getX(), pos2.getY() + 1, pos2.getZ());
                IBlockState state = world.getBlockState(pos1);
                if (state.getMaterial() == Material.AIR) {
                    IBlockState state1 = world.getBlockState(pos2);
                    if (state1.getMaterial() == Material.LAVA && state1.getValue(BlockLiquid.LEVEL) == 0 &&
                            world.mayPlace(ModBlocks.frostedObsidian, pos2, false, EnumFacing.DOWN, null)) {
                        world.setBlockState(pos2, ModBlocks.frostedObsidian.getDefaultState(), 2);
                        world.scheduleUpdate(pos2.toImmutable(), ModBlocks.frostedObsidian,
                                MathHelper.getInt(entity.getRNG(), 60, 120));
                    }
                }
            }
        }
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return enchantmentLevel * 10;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return this.getMinEnchantability(enchantmentLevel) + 15;
    }

    @Override
    public boolean isTreasureEnchantment() {
        return false;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        return super.canApplyTogether(enchantment) && enchantment != Enchantments.FROST_WALKER;
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!(event.getEntityLiving() instanceof EntityPlayerMP)) {
            return;
        }

        EntityLivingBase entity = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(ModEnchantments.LAVA_WALKER, entity);
        if (level < 1) {
            return; // not wearing lava walker boots
        }

        BlockPos curPos = entity.getPosition();
        BlockPos prevPos = playerPositionTracker.get(entity.getUniqueID());
        if (prevPos != null &&
                curPos.getX() == prevPos.getX() &&
                curPos.getY() == prevPos.getY() &&
                curPos.getZ() == prevPos.getZ()) {
            return; // did not move a full block
        }
        playerPositionTracker.put(entity.getUniqueID(), curPos);

        EnchantmentLavaWalker.freezeLava(entity, entity.getEntityWorld(), entity.getPosition(), level);
    }
}

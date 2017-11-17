package net.pl3x.forge.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.block.ModBlocks;
import net.pl3x.forge.block.custom.furniture.BlockSeat;

import javax.annotation.Nullable;

public class EntityChairSeat extends Entity {
    public EntityChairSeat(World world) {
        super(world);
        setEntityInvulnerable(true);
        setNoGravity(true);
    }

    private BlockPos getPos() {
        return new BlockPos(posX, posY, posZ);
    }

    private IBlockState getBlockState(BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() instanceof BlockSeat ? state.getActualState(world, pos) : null;
    }

    private int getYaw(IBlockState state) {
        if (state != null) {
            EnumFacing facing = state.getValue(BlockSeat.FACING);
            if (facing == EnumFacing.EAST) {
                return 90;
            } else if (facing == EnumFacing.SOUTH) {
                return 180;
            } else if (facing == EnumFacing.WEST) {
                return 270;
            }
        }
        return 0;
    }

    @Override
    protected void entityInit() {
        //
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        //
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        //
    }

    @Override
    public double getMountedYOffset() {
        return -0.21D;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return null;
    }

    @Override
    public void setDropItemsWhenDead(boolean dropWhenDead) {
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return true;
    }

    @Override
    public EnumFacing getAdjustedHorizontalFacing() {
        return getHorizontalFacing().rotateY();
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        return false;
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return riddenByEntities.isEmpty() ? null : riddenByEntities.get(0);
    }

    @Override
    public boolean canFitPassenger(Entity passenger) {
        return riddenByEntities.isEmpty();
    }

    protected void applyYawToEntity(Entity passenger) {
        if (!isPassenger(passenger)) {
            return;
        }
        rotationYaw = getYaw(getBlockState(getPos()));
        passenger.setRenderYawOffset(rotationYaw);
        float yaw = MathHelper.wrapDegrees(passenger.rotationYaw - rotationYaw);
        float yawClamped = MathHelper.clamp(yaw, -105.0F, 105.0F);
        passenger.prevRotationYaw += yawClamped - yaw;
        passenger.rotationYaw += yawClamped - yaw;
        passenger.setRotationYawHead(passenger.rotationYaw);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity passenger) {
        if (!isPassenger(passenger)) {
            return;
        }
        applyYawToEntity(passenger);
    }

    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (isPassenger(passenger)) {
            applyYawToEntity(passenger);
        }
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
        applyYawToEntity(passenger);
        passenger.rotationYaw = getYaw(getBlockState(getPos()));
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        BlockPos pos = getPos();
        IBlockState state = getBlockState(pos);
        if (state != null) {
            EnumFacing facing = state.getValue(BlockSeat.FACING);
            pos = pos.offset(facing.rotateAround(EnumFacing.Axis.Y));
            passenger.setPosition(pos.getX() + 0.5, pos.getY() + 0.25, pos.getZ() + 0.5);
        }
    }

    @Override
    public boolean shouldRiderSit() {
        return true;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (riddenByEntities.isEmpty()) {
            setDead();
            return;
        }
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        return !isEntityInvulnerable(source);
    }

    @Override
    public boolean isEntityInvulnerable(DamageSource source) {
        return super.isEntityInvulnerable(source) || source.isCreativePlayer();
    }

    @SideOnly(Side.CLIENT)
    public boolean getAlwaysRenderNameTagForRender() {
        return false;
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        //
    }

    @Override
    public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
        return false;
    }
}

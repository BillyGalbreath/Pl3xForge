package net.pl3x.forge.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public abstract class EntityVehicle extends Entity {
    private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(EntityVehicle.class, DataSerializers.FLOAT);

    private boolean inputForwardDown, inputBackDown, inputLeftDown, inputRightDown, inputActionDown;

    private float deltaRotation;
    private float momentum;
    private float angularMomentum;
    private int spinningTicks = 0; // Out of control

    public EntityVehicle(World worldIn) {
        super(worldIn);
        this.stepHeight = 0.6f;
    }

    @SideOnly(Side.CLIENT)
    public abstract ModelBase getModel();

    @SideOnly(Side.CLIENT)
    public abstract ResourceLocation getTexture();

    @SideOnly(Side.CLIENT)
    public abstract void renderCar(double x, double y, double z, float entityYaw, float partialTicks);

    @Override
    protected void entityInit() {
        dataManager.register(DAMAGE_TAKEN, 0.0F);
    }

    @Override
    public float getEyeHeight() {
        return height;
    }

    @Override
    public double getMountedYOffset() {
        return 0D;
    }

    @Override
    protected boolean canTriggerWalking() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !isDead;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return getEntityBoundingBox();
    }

    @Override
    public void setDropItemsWhenDead(boolean dropWhenDead) {
        // TODO
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
        if (player.isSneaking()) {
            return false;
        }
        if (!world.isRemote) {
            setRider(player);
        }
        return true;
    }

    public void setRider(EntityPlayer player) {
        if (getControllingPassenger() == null) {
            player.rotationYaw = rotationYaw;
            player.rotationPitch = rotationPitch;
            if (!world.isRemote) {
                player.startRiding(this);
            }
        }
    }

    @Nullable
    @Override
    public Entity getControllingPassenger() {
        return riddenByEntities.isEmpty() ? null : riddenByEntities.get(0);
    }

    @Override
    public boolean canFitPassenger(Entity passenger) {
        return riddenByEntities.size() < 1;
    }

    protected void applyYawToEntity(Entity passenger) {
        passenger.setRenderYawOffset(this.rotationYaw);
        float yaw = MathHelper.wrapDegrees(passenger.rotationYaw - this.rotationYaw);
        float yawClamped = MathHelper.clamp(yaw, -105.0F, 105.0F);
        passenger.prevRotationYaw += yawClamped - yaw;
        passenger.rotationYaw += yawClamped - yaw;
        passenger.setRotationYawHead(passenger.rotationYaw);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity passenger) {
        applyYawToEntity(passenger);
    }

    public void updatePassenger(Entity passenger) {
        if (!isPassenger(passenger)) {
            return;
        }
        float f = 0.0F;
        float f1 = (float) ((isDead ? 0.009999999776482582D : getMountedYOffset()) + passenger.getYOffset());
        if (riddenByEntities.size() > 1) {
            int i = riddenByEntities.indexOf(passenger);
            if (i == 0) {
                f = 0.2F;
            } else {
                f = -0.6F;
            }
        }
        Vec3d vec3d = (new Vec3d((double) f, 0.0D, 0.0D)).rotateYaw(-rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
        passenger.setPosition(posX + vec3d.x, posY + (double) f1, posZ + vec3d.z);
        double speedSqAbs = this.motionZ * this.motionZ + this.motionX * this.motionX;
        if (speedSqAbs > 0.001 && (inputLeftDown || inputRightDown)) {
            passenger.rotationYaw += deltaRotation;
            passenger.setRotationYawHead(passenger.getRotationYawHead() + deltaRotation);
        }
        applyYawToEntity(passenger);
        System.out.println("Car Y: " + posY);
        System.out.println("Plr Y: " + passenger.posY);
    }

    @Override
    protected void addPassenger(Entity passenger) {
        super.addPassenger(passenger);
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (world.isRemote && passenger instanceof EntityPlayer) {
            Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        onUpdate(canPassengerSteer());
    }

    protected void onUpdate(boolean canPassengerSteer) {
        if (canPassengerSteer) {
            updateMotion();
            if (this.world.isRemote) {
                this.controlVehicle();
            }
            move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        } else {
            motionX = 0.0D;
            motionY = 0.0D;
            motionZ = 0.0D;
        }
    }

    private void updateMotion() {
        if (spinningTicks > 0) {
            if (--spinningTicks > 15) {
                momentum = 0.8F;
                angularMomentum = 0.9F;
                world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, posX, posY + 2, posZ, 0.1, 0.1, 0.1);
            }
        } else {
            momentum = angularMomentum = 0.9F;
        }
        motionX *= (double) momentum;
        motionZ *= (double) momentum;
        motionY += hasNoGravity() ? 0.0D : -0.03999999910593033D;
        deltaRotation *= angularMomentum;
    }

    @SideOnly(Side.CLIENT)
    public void updateInputs(boolean forward, boolean back, boolean left, boolean right, boolean action) {
        this.inputForwardDown = forward;
        this.inputBackDown = back;
        this.inputLeftDown = left;
        this.inputRightDown = right;
        this.inputActionDown = action;
    }

    @SideOnly(Side.CLIENT)
    public void controlVehicle() {
        if (!isBeingRidden()) {
            return;
        }
        float fwd = 0.0F; //Forward movement?

        if (spinningTicks <= 10) {
            double deltaR = 0; //rotation
            if (this.inputLeftDown)
                deltaR -= 1;
            if (this.inputRightDown)
                deltaR += 1;

            if (this.inputForwardDown)
                fwd += 0.04;
            if (this.inputBackDown)
                fwd -= 0.005;

            double speedSqAbs = this.motionZ * this.motionZ + this.motionX * this.motionX;

            double momYaw = MathHelper.wrapDegrees(MathHelper.atan2(this.motionZ, this.motionX) * 180 / Math.PI) - 90;
            double rotYaw = MathHelper.wrapDegrees(this.rotationYaw);
            double angle = MathHelper.wrapDegrees(rotYaw - momYaw);
            double speedSq = (angle > 170 || angle < -170) ? -speedSqAbs : speedSqAbs;


            //times 2.5 and reverse if driving backwards
            deltaR *= 2.5 * speedSq > 0 ? 1 : -1;
            if (speedSq > 0) {
                //TODO: Yeah - find some actual good numbers
                deltaR *= speedSq * 25 * Math.pow(Math.max(5, Math.min(30, Math.abs(angle)) - 4), -1.001);
            }

            if (speedSqAbs * Math.abs(angle) > 3.5) {
                deltaR = 0;
                this.spinningTicks = this.spinningTicks * 2 + 2;
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY + 2, this.posZ, 0.1, 0.1, 0.1);
            }
            if (speedSqAbs > 0.001 && (inputLeftDown || inputRightDown)) {
                //Apply the rotation if the car is moving.
                this.deltaRotation += deltaR;
                this.rotationYaw += this.deltaRotation;
            }
        } else {
            this.rotationYaw += this.deltaRotation; //Spin me around
        }

        //Apply movement
        this.motionX += (double) (MathHelper.sin(-this.rotationYaw * 0.017453292F) * fwd);
        this.motionZ += (double) (MathHelper.cos(this.rotationYaw * 0.017453292F) * fwd);
    }

    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (isEntityInvulnerable(source)) {
            return false;
        }
        if (!world.isRemote && !isDead) {
            if (source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && isPassenger(source.getTrueSource())) {
                return false;
            }
            boolean isCreativeUser = source.getTrueSource() instanceof EntityPlayer && ((EntityPlayer) source.getTrueSource()).capabilities.isCreativeMode;
            if (isCreativeUser || getDamageTaken() > 40.0F) {
                if (!isCreativeUser && world.getGameRules().getBoolean("doEntityDrops")) {
                    // TODO dropItemWithOffset(this.getItemBoat(), 1, 0.0F);
                }
                setDead();
            }
        }
        return true;
    }

    public void setDamageTaken(float damageTaken) {
        dataManager.set(DAMAGE_TAKEN, damageTaken);
    }

    public float getDamageTaken() {
        return dataManager.get(DAMAGE_TAKEN);
    }

    public boolean isInputForwardDown() {
        return inputForwardDown;
    }

    public boolean isInputBackDown() {
        return inputBackDown;
    }

    public boolean isInputLeftDown() {
        return inputLeftDown;
    }

    public boolean isInputRightDown() {
        return inputRightDown;
    }

    public boolean isInputActionDown() {
        return inputActionDown;
    }
}

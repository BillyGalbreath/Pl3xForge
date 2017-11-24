package net.pl3x.forge.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.pl3x.forge.entity.ai.EntityAIFollowConeHead;
import net.pl3x.forge.item.ModItems;

public class EntityTrafficCone extends EntityLiving {
    public EntityTrafficCone(World world) {
        super(world);
        setHealth(getMaxHealth());
        setCanPickUpLoot(false);
        enablePersistence();
        setSize(0.625F, 0.8125F);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MAX_HEALTH);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0.1D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
    }

    protected void initEntityAI() {
        targetTasks.addTask(2, new EntityAIFollowConeHead(this, 1.0F, 3.0F, 7.0F));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (source == DamageSource.OUT_OF_WORLD ||
                source.getImmediateSource() instanceof EntityPlayer ||
                source.getTrueSource() instanceof EntityPlayer) {
            damageEntity(source, amount);
            return true;
        }
        return false;
    }

    @Override
    protected void damageEntity(DamageSource source, float amount) {
        EntityPlayer player = null;
        if (source == DamageSource.OUT_OF_WORLD) {
            setDead();
        } else if (source.getImmediateSource() instanceof EntityPlayer) {
            player = (EntityPlayer) source.getImmediateSource();
        } else if (source.getTrueSource() instanceof EntityPlayer) {
            player = (EntityPlayer) source.getTrueSource();
        }

        if (player != null) {
            setHealth(0F);
            if (!world.isRemote && !player.isCreative()) {
                world.spawnEntity(new EntityItem(world, posX, posY, posZ, ModItems.TRAFFIC_CONE.getDefaultInstance()));
            }
        }
    }

    @Override
    protected void onDeathUpdate() {
        ++deathTime;
        if (deathTime == 20) {
            setDead();
            for (int k = 0; k < 20; ++k) {
                world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                        posX + (double) (rand.nextFloat() * width * 2.0F) - (double) width,
                        posY + (double) (rand.nextFloat() * height),
                        posZ + (double) (rand.nextFloat() * width * 2.0F) - (double) width,
                        rand.nextGaussian() * 0.02D,
                        rand.nextGaussian() * 0.02D,
                        rand.nextGaussian() * 0.02D);
            }
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (!dead) {
            dead = true;
            getCombatTracker().reset();
            world.setEntityState(this, (byte) 3);
        }
    }

    @Override
    public boolean canBePushed() {
        return this.isEntityAlive();
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public void applyEntityCollision(Entity entityIn) {
        if (!this.isRidingSameEntity(entityIn)) {
            if (!entityIn.noClip && !this.noClip) {
                double d0 = entityIn.posX - this.posX;
                double d1 = entityIn.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D) {
                    d2 = (double) MathHelper.sqrt(d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D) {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
                    d0 = d0 * (double) (1.0F - this.entityCollisionReduction);
                    d1 = d1 * (double) (1.0F - this.entityCollisionReduction);

                    if (!this.isBeingRidden()) {
                        this.addVelocity(-d0 * 10, 0.0D, -d1 * 10);
                    }

                    if (!entityIn.isBeingRidden()) {
                        entityIn.addVelocity(d0, 0.0D, d1);
                    }
                }
            }
        }
    }
}

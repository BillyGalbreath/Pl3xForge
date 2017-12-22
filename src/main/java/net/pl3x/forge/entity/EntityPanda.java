package net.pl3x.forge.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class EntityPanda extends EntityAnimal {
    private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(EntityPanda.class, DataSerializers.BOOLEAN);
    private float clientSideStandAnimation0;
    private float clientSideStandAnimation;
    private int warningSoundTicks;

    public EntityPanda(World worldIn) {
        super(worldIn);
        setSize(1.3F, 1.4F);
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
        return new EntityPanda(world);
    }

    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    protected void initEntityAI() {
        super.initEntityAI();
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityPanda.AIMeleeAttack());
        tasks.addTask(1, new EntityPanda.AIPanic());
        tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
        tasks.addTask(5, new EntityAIWander(this, 1.0D));
        tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        tasks.addTask(7, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityPanda.AIHurtByTarget());
        targetTasks.addTask(2, new EntityPanda.AIAttackPlayer());
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    }

    protected SoundEvent getAmbientSound() {
        return isChild() ? SoundEvents.ENTITY_POLAR_BEAR_BABY_AMBIENT : SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn) {
        playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
    }

    protected void playWarningSound() {
        if (warningSoundTicks <= 0) {
            playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.0F);
            warningSoundTicks = 40;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LootTableList.ENTITIES_POLAR_BEAR;
    }

    protected void entityInit() {
        super.entityInit();
        dataManager.register(IS_STANDING, false);
    }

    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            clientSideStandAnimation0 = clientSideStandAnimation;

            if (isStanding()) {
                clientSideStandAnimation = MathHelper.clamp(clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
            } else {
                clientSideStandAnimation = MathHelper.clamp(clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
            }
        }

        if (warningSoundTicks > 0) {
            --warningSoundTicks;
        }
    }

    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag) {
            applyEnchantments(this, entityIn);
        }

        return flag;
    }

    public boolean isStanding() {
        return dataManager.get(IS_STANDING);
    }

    public void setStanding(boolean standing) {
        dataManager.set(IS_STANDING, standing);
    }

    @SideOnly(Side.CLIENT)
    public float getStandingAnimationScale(float scale) {
        return (clientSideStandAnimation0 + (clientSideStandAnimation - clientSideStandAnimation0) * scale) / 6.0F;
    }

    protected float getWaterSlowDown() {
        return 0.98F;
    }

    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
        if (livingdata instanceof EntityPanda.GroupData) {
            if (((EntityPanda.GroupData) livingdata).madeParent) {
                setGrowingAge(-24000);
            }
        } else {
            EntityPanda.GroupData groupData = new EntityPanda.GroupData();
            groupData.madeParent = true;
            livingdata = groupData;
        }

        return livingdata;
    }

    class AIAttackPlayer extends EntityAINearestAttackableTarget<EntityPlayer> {
        public AIAttackPlayer() {
            super(EntityPanda.this, EntityPlayer.class, 20, true, true, null);
        }

        public boolean shouldExecute() {
            if (isChild()) {
                return false;
            } else {
                if (super.shouldExecute()) {
                    for (EntityPanda panda : world.getEntitiesWithinAABB(EntityPanda.class, getEntityBoundingBox().grow(8.0D, 4.0D, 8.0D))) {
                        if (panda.isChild()) {
                            return true;
                        }
                    }
                }

                setAttackTarget(null);
                return false;
            }
        }

        protected double getTargetDistance() {
            return super.getTargetDistance() * 0.5D;
        }
    }

    class AIHurtByTarget extends EntityAIHurtByTarget {
        public AIHurtByTarget() {
            super(EntityPanda.this, false);
        }

        public void startExecuting() {
            super.startExecuting();

            if (isChild()) {
                alertOthers();
                resetTask();
            }
        }

        protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
            if (creatureIn instanceof EntityPanda && !creatureIn.isChild()) {
                super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
            }
        }
    }

    class AIMeleeAttack extends EntityAIAttackMelee {
        public AIMeleeAttack() {
            super(EntityPanda.this, 1.25D, true);
        }

        protected void checkAndPerformAttack(EntityLivingBase entity, double reach) {
            double d0 = getAttackReachSqr(entity);

            if (reach <= d0 && attackTick <= 0) {
                attackTick = 20;
                attacker.attackEntityAsMob(entity);
                setStanding(false);
            } else if (reach <= d0 * 2.0D) {
                if (attackTick <= 0) {
                    setStanding(false);
                    attackTick = 20;
                }

                if (attackTick <= 10) {
                    setStanding(true);
                    playWarningSound();
                }
            } else {
                attackTick = 20;
                setStanding(false);
            }
        }

        public void resetTask() {
            setStanding(false);
            super.resetTask();
        }

        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return (double) (4.0F + attackTarget.width);
        }
    }

    class AIPanic extends EntityAIPanic {
        public AIPanic() {
            super(EntityPanda.this, 2.0D);
        }

        public boolean shouldExecute() {
            return (isChild() || isBurning()) && super.shouldExecute();
        }
    }

    static class GroupData implements IEntityLivingData {
        public boolean madeParent;

        private GroupData() {
        }
    }
}

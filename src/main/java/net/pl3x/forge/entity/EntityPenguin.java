package net.pl3x.forge.entity;

import com.google.common.collect.Sets;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFishFood.FishType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.sound.ModSounds;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class EntityPenguin extends EntityAnimal {
    public static final ResourceLocation LOOT_ENTITIES_PENGUIN_FISH = LootTableList.register(new ResourceLocation(Pl3x.modId, "entities/penguin"));
    public static final Set<Item> TEMPTATION_ITEMS = Sets.newHashSet(
            new ItemStack(Items.FISH, 1, FishType.COD.getMetadata()).getItem(),
            new ItemStack(Items.FISH, 1, FishType.SALMON.getMetadata()).getItem());

    public short rotationFlipper;
    private boolean moveFlipper = false;

    public EntityPenguin(World world) {
        super(world);
        this.setSize(0.4F, 0.95F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIExtinguishFire());
        this.tasks.addTask(2, new EntityAIPanic(this, 1.5D));
        this.tasks.addTask(3, new EntityAIMate(this, 0.8D));
        this.tasks.addTask(4, new EntityAIAvoidEntity<>(this, EntityPolarBear.class, 6.0F, 1.0D, 1.2D));
        this.tasks.addTask(5, new EntityAITempt(this, 1.0D, false, TEMPTATION_ITEMS));
        this.tasks.addTask(6, new EntityAIFollowParent(this, 1.1D));
        this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityPenguin.class, 6.0F));
        this.tasks.addTask(10, new EntityAILookIdle(this));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.16D);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isChild() ? ModSounds.PENGUIN_BABY_AMBIENT : ModSounds.PENGUIN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.PENGUIN_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PENGUIN_DEATH;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (world.isRemote) {
            if (this.posZ != this.prevPosZ) {
                if (moveFlipper) {
                    rotationFlipper++;
                }
            }
        }
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isBreedingItem(@Nonnull ItemStack stack) {
        return !stack.isEmpty() && TEMPTATION_ITEMS.contains(stack.getItem());
    }

    @Nullable
    @Override
    public ResourceLocation getLootTable() {
        return LOOT_ENTITIES_PENGUIN_FISH;
    }

    @Override
    @Nonnull
    public EntityPenguin createChild(@Nonnull EntityAgeable ageable) {
        return new EntityPenguin(this.world);
    }

    @Override
    public float getEyeHeight() {
        return this.isChild() ? 0.5F : 0.9F;
    }

    private class EntityAIExtinguishFire extends EntityAIPanic {
        EntityAIExtinguishFire() {
            super(EntityPenguin.this, 2.0D);
        }

        @Override
        public boolean shouldExecute() {
            return (EntityPenguin.this.isChild() || EntityPenguin.this.isBurning()) && super.shouldExecute();
        }
    }
}

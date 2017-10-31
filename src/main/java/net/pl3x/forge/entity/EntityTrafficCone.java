package net.pl3x.forge.entity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityTrafficCone extends EntityLiving {
    public EntityTrafficCone(World world) {
        super(world);
        setCanPickUpLoot(false);
        enablePersistence();
        setSize(0.625F, 0.8125F);
    }

    @Override
    public float getEyeHeight() {
        return super.getEyeHeight();
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
    public void damageEntity(DamageSource source, float amount) {
        if (source == DamageSource.OUT_OF_WORLD) {
            setDead();
        } else if (source.getImmediateSource() instanceof EntityPlayer ||
                source.getTrueSource() instanceof EntityPlayer) {
            // TODO spawn traffic_cone item
            setDead();
        }
    }
}

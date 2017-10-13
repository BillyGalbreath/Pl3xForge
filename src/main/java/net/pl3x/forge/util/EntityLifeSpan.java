package net.pl3x.forge.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityTameable;
import net.pl3x.forge.scheduler.Pl3xRunnable;

public class EntityLifeSpan extends Pl3xRunnable {
    private final EntityLivingBase entity;

    public EntityLifeSpan(EntityLivingBase entity) {
        this.entity = entity;
    }

    @Override
    public void run() {
        if (entity.isDead) {
            cancel();
            return;
        }

        if (entity.hasCustomName()) {
            cancel();
            return;
        }

        if (entity instanceof EntityTameable && ((EntityTameable) entity).isTamed()) {
            cancel();
            return;
        }

        entity.isDead = true;
    }
}

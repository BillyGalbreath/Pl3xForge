package net.pl3x.forge.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.pl3x.forge.Pl3x;

public class ModEntities {
    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3x.modId + ":banker"),
                EntityBanker.class, "Banker", 0, Pl3x.instance, 48, 1, true);
    }
}

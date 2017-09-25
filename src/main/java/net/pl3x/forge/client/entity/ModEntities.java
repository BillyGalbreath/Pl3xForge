package net.pl3x.forge.client.entity;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.pl3x.forge.client.Pl3xForgeClient;

public class ModEntities {
    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3xForgeClient.modId + ":banker"),
                EntityBanker.class, "Banker", 0, Pl3xForgeClient.instance, 48, 1, true);
    }
}

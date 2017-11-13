package net.pl3x.forge.entity;

import com.google.common.collect.Lists;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.entity.render.RenderBanker;
import net.pl3x.forge.entity.render.RenderChair;
import net.pl3x.forge.entity.render.RenderPenguin;
import net.pl3x.forge.entity.render.RenderTrafficCone;
import net.pl3x.forge.entity.render.RenderVehicle;

import java.util.List;
import java.util.Set;

import static net.minecraftforge.common.BiomeDictionary.Type.FOREST;
import static net.minecraftforge.common.BiomeDictionary.Type.NETHER;
import static net.minecraftforge.common.BiomeDictionary.Type.SNOWY;

public class ModEntities {
    public static void init() {
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3x.modId, "banker"), EntityBanker.class, "banker", 0, Pl3x.instance, 48, 1, true, 0xFFFFFF, 0x000000);
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3x.modId, "chair"), EntityChairSeat.class, "chair", 1, Pl3x.instance, 48, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3x.modId, "penguin"), EntityPenguin.class, "penguin", 2, Pl3x.instance, 48, 1, true, 0x000000, 0xFFFFFF);
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3x.modId, "traffic_cone"), EntityTrafficCone.class, "traffic_cone", 3, Pl3x.instance, 48, 1, true);
        EntityRegistry.registerModEntity(new ResourceLocation(Pl3x.modId, "go_cart"), EntityGoCart.class, "go_cart", 4, Pl3x.instance, 48, 1, true);

        addEntitySpawns();
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders() {
        RenderingRegistry.registerEntityRenderingHandler(EntityBanker.class, RenderBanker::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityChairSeat.class, RenderChair::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPenguin.class, RenderPenguin::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTrafficCone.class, RenderTrafficCone::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGoCart.class, RenderVehicle::new);
    }

    private static void addEntitySpawns() {
        List<Biome> penguinBiomes = Lists.newArrayList();
        for (Biome biome : Biome.REGISTRY) {
            Set<BiomeDictionary.Type> types = BiomeDictionary.getTypes(biome);
            if (types.contains(SNOWY) && !types.contains(FOREST) && !types.contains(NETHER) && !biome.getSpawnableList(EnumCreatureType.CREATURE).isEmpty()) {
                penguinBiomes.add(biome);
            }
        }
        EntityRegistry.addSpawn(EntityPenguin.class, 2, 1, 4, EnumCreatureType.CREATURE, penguinBiomes.toArray(new Biome[0]));
    }
}

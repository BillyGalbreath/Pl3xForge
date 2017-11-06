package net.pl3x.forge.util;

import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BiomeHelper {
    private static final ResourceLocation ADVENTURING_TIME = new ResourceLocation("minecraft", "adventure/adventuring_time");
    private static final Map<String, String> EXPLORE_BIOMES = new HashMap<>();


    static {
        EXPLORE_BIOMES.put("beaches", "Beach");
        EXPLORE_BIOMES.put("birch_forest", "Birch Forest");
        EXPLORE_BIOMES.put("birch_forest_hills", "Birch Forest Hills");
        EXPLORE_BIOMES.put("cold_beach", "Cold Beach");
        EXPLORE_BIOMES.put("taiga_cold", "Cold Taiga");
        EXPLORE_BIOMES.put("taiga_cold_hills", "Cold Taiga Hills");
        EXPLORE_BIOMES.put("deep_ocean", "Deep Ocean");
        EXPLORE_BIOMES.put("desert", "Desert");
        EXPLORE_BIOMES.put("desert_hills", "DesertHills");
        EXPLORE_BIOMES.put("extreme_hills", "Extreme Hills");
        EXPLORE_BIOMES.put("extreme_hills_with_trees", "Extreme Hills+");
        EXPLORE_BIOMES.put("forest", "Forest");
        EXPLORE_BIOMES.put("forest_hills", "ForestHills");
        EXPLORE_BIOMES.put("frozen_river", "FrozenRiver");
        EXPLORE_BIOMES.put("ice_mountains", "Ice Mountains");
        EXPLORE_BIOMES.put("ice_flats", "Ice Plains");
        EXPLORE_BIOMES.put("jungle", "Jungle");
        EXPLORE_BIOMES.put("jungle_edge", "JungleEdge");
        EXPLORE_BIOMES.put("jungle_hills", "JungleHills");
        EXPLORE_BIOMES.put("redwood_taiga", "Mega Taiga");
        EXPLORE_BIOMES.put("redwood_taiga_hills", "Mega Taiga Hills");
        EXPLORE_BIOMES.put("mesa", "Mesa");
        EXPLORE_BIOMES.put("mesa_clear_rock", "Mesa Plateau");
        EXPLORE_BIOMES.put("mesa_rock", "Mesa Plateau F");
        EXPLORE_BIOMES.put("mushroom_island", "MushroomIsland");
        EXPLORE_BIOMES.put("mushroom_island_shore", "MushroomIslandShore");
        EXPLORE_BIOMES.put("ocean", "Ocean");
        EXPLORE_BIOMES.put("plains", "Plains");
        EXPLORE_BIOMES.put("river", "River");
        EXPLORE_BIOMES.put("roofed_forest", "Roofed Forest");
        EXPLORE_BIOMES.put("savanna", "Savanna");
        EXPLORE_BIOMES.put("savanna_rock", "Savanna Plateau");
        EXPLORE_BIOMES.put("stone_beach", "Stone Beach");
        EXPLORE_BIOMES.put("swampland", "Swampland");
        EXPLORE_BIOMES.put("taiga", "Taiga");
        EXPLORE_BIOMES.put("taiga_hills", "TaigaHills");
    }

    public static List<String> getVisitedBiomes(EntityPlayerMP player) {
        AdvancementProgress progress = player.getAdvancements().getProgress(
                player.getServerWorld().getAdvancementManager().getAdvancement(ADVENTURING_TIME));
        List<String> list = new ArrayList<>();
        for (String complete : progress.getCompletedCriteria()) {
            list.add(EXPLORE_BIOMES.get(complete) + "&a");
        }
        for (String incomplete : progress.getRemaningCriteria()) {
            list.add(EXPLORE_BIOMES.get(incomplete) + "&c");
        }
        Collections.sort(list);
        return list.stream().map(name -> {
            String color = name.substring(name.length() - 2);
            return color + name.substring(0, name.length() - 2);
        }).collect(Collectors.toList());
    }
}

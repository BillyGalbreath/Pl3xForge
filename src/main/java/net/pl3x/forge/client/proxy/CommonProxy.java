package net.pl3x.forge.client.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.data.PlayerDataImpl;
import net.pl3x.forge.client.entity.ModEntities;
import net.pl3x.forge.client.listener.CapabilityHandler;
import net.pl3x.forge.client.recipe.ModRecipes;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        //
    }

    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        ModEntities.init();

        CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerDataImpl.PlayerDataStorage(), PlayerDataImpl.class);
        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        //
    }

    public void serverStarting(FMLServerStartingEvent event) {
        //
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        //
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        //
    }
}

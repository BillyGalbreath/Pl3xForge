package net.pl3x.forge.client.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.pl3x.forge.client.data.PlayerData;
import net.pl3x.forge.client.data.PlayerDataImpl;
import net.pl3x.forge.client.data.PlayerDataStorage;
import net.pl3x.forge.client.listener.CapabilityHandler;
import net.pl3x.forge.client.listener.ServerEventHandler;
import net.pl3x.forge.client.recipe.ModRecipes;
import net.pl3x.forge.client.world.ModWorldGen;

public class ServerProxy {
    public void preInit(FMLPreInitializationEvent event) {
        //
    }

    public void init(FMLInitializationEvent event) {
        CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerDataStorage(), PlayerDataImpl.class);

        ModRecipes.init();

        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);

        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
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

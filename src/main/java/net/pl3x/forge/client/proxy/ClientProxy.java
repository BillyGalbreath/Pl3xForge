package net.pl3x.forge.client.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.pl3x.forge.client.Pl3xForgeClient;
import net.pl3x.forge.client.block.enchantmentsplitter.TileEntityEnchantmentSplitter;
import net.pl3x.forge.client.block.enchantmentsplitter.TileEntityEnchantmentSplitterRenderer;
import net.pl3x.forge.client.entity.EntityBanker;
import net.pl3x.forge.client.entity.ModEntities;
import net.pl3x.forge.client.entity.RenderBanker;
import net.pl3x.forge.client.gui.TitleScreen;
import net.pl3x.forge.client.listener.ClientEventHandler;
import net.pl3x.forge.client.recipe.ModRecipes;

public class ClientProxy extends ServerProxy {
    public void preInit(FMLPreInitializationEvent event) {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentSplitter.class, new TileEntityEnchantmentSplitterRenderer());
        RenderingRegistry.registerEntityRenderingHandler(EntityBanker.class, new RenderBanker.RenderBankerFactory());
    }

    public void init(FMLInitializationEvent event) {
        ModRecipes.init();
        ModEntities.init();

        MinecraftForge.EVENT_BUS.register(new TitleScreen());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
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
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Pl3xForgeClient.modId + ":" + id, "inventory"));
    }
}

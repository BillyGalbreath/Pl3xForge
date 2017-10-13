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
import net.pl3x.forge.client.entity.EntityBanker;
import net.pl3x.forge.client.entity.render.RenderBanker;
import net.pl3x.forge.client.gui.TitleScreen;
import net.pl3x.forge.client.listener.ClientEventHandler;
import net.pl3x.forge.client.listener.KeyBindings;
import net.pl3x.forge.client.listener.KeyInputHandler;
import net.pl3x.forge.client.listener.ServerEventHandler;
import net.pl3x.forge.client.tileentity.TileEntityEnchantmentSplitter;
import net.pl3x.forge.client.tileentity.TileEntityShop;
import net.pl3x.forge.client.tileentity.renderer.TileEntityEnchantmentSplitterRenderer;
import net.pl3x.forge.client.tileentity.renderer.TileEntityShopRenderer;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentSplitter.class, new TileEntityEnchantmentSplitterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShop.class, new TileEntityShopRenderer());

        RenderingRegistry.registerEntityRenderingHandler(EntityBanker.class, new RenderBanker.RenderBankerFactory());
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);

        MinecraftForge.EVENT_BUS.register(new TitleScreen());
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());

        KeyBindings.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        super.serverStopping(event);
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        super.registerItemRenderer(item, meta, id);

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Pl3xForgeClient.modId + ":" + id, "inventory"));
    }
}

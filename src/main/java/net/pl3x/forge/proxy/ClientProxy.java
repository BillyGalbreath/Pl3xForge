package net.pl3x.forge.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerCape;
import net.minecraft.client.renderer.entity.layers.LayerElytra;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.Pl3xSettings;
import net.pl3x.forge.cape.LayerPl3xCape;
import net.pl3x.forge.cape.LayerPl3xElytra;
import net.pl3x.forge.color.ModColorManager;
import net.pl3x.forge.configuration.ClientConfig;
import net.pl3x.forge.discord.Discord;
import net.pl3x.forge.discord.PresenceManager;
import net.pl3x.forge.entity.ModEntities;
import net.pl3x.forge.exception.FuckOptifine;
import net.pl3x.forge.gui.TitleScreen;
import net.pl3x.forge.listener.BigHeadListener;
import net.pl3x.forge.listener.ClientEventHandler;
import net.pl3x.forge.listener.KeyBindings;
import net.pl3x.forge.listener.KeyInputHandler;
import net.pl3x.forge.network.OpenInventoryPacket;
import net.pl3x.forge.tileentity.TileEntityCuttingBoard;
import net.pl3x.forge.tileentity.TileEntityEnchantmentSplitter;
import net.pl3x.forge.tileentity.TileEntityMirror;
import net.pl3x.forge.tileentity.TileEntityPlate;
import net.pl3x.forge.tileentity.TileEntityShop;
import net.pl3x.forge.tileentity.TileEntityTV;
import net.pl3x.forge.tileentity.TileEntityTrafficLight;
import net.pl3x.forge.tileentity.renderer.TileEntityCuttingBoardRenderer;
import net.pl3x.forge.tileentity.renderer.TileEntityEnchantmentSplitterRenderer;
import net.pl3x.forge.tileentity.renderer.TileEntityMirrorRenderer;
import net.pl3x.forge.tileentity.renderer.TileEntityPlateRenderer;
import net.pl3x.forge.tileentity.renderer.TileEntityShopRenderer;
import net.pl3x.forge.tileentity.renderer.TileEntityTVRenderer;
import net.pl3x.forge.tileentity.renderer.TileEntityTrafficLightRenderer;

import java.util.Iterator;

@SideOnly(Side.CLIENT)
public class ClientProxy extends ServerProxy {
    public static boolean rendering = false;
    public static Entity renderEntity = null;

    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        if (FMLClientHandler.instance().hasOptifine()) {
            throw new FuckOptifine();
        }

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCuttingBoard.class, new TileEntityCuttingBoardRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEnchantmentSplitter.class, new TileEntityEnchantmentSplitterRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPlate.class, new TileEntityPlateRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityShop.class, new TileEntityShopRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrafficLight.class, new TileEntityTrafficLightRenderer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTV.class, new TileEntityTVRenderer());

        ModEntities.registerRenders();
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);

        // dont bind this in pre-init, needs Minecraft instance for RenderGlobal
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMirror.class, new TileEntityMirrorRenderer());

        MinecraftForge.EVENT_BUS.register(new BigHeadListener());
        MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new TileEntityMirrorRenderer());
        MinecraftForge.EVENT_BUS.register(new TitleScreen());

        ModColorManager.registerColorHandlers();

        KeyBindings.init();

        overrideVanillaLayers();
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);

        Pl3xSettings.INSTANCE.chatBGRed = ClientConfig.chatOptions.background.red;
        Pl3xSettings.INSTANCE.chatBGGreen = ClientConfig.chatOptions.background.green;
        Pl3xSettings.INSTANCE.chatBGBlue = ClientConfig.chatOptions.background.blue;
        Pl3xSettings.INSTANCE.chatBGAlpha = ClientConfig.chatOptions.background.alpha;

        Discord.loadLibs();
        PresenceManager.INSTANCE.init();
        PresenceManager.INSTANCE.update(false);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        super.serverStarting(event);
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        super.serverStopping(event);
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        super.registerItemRenderer(item, meta, id);

        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Pl3x.modId + ":" + id, "inventory"));
    }

    public void handleOpenPlayerInventory(OpenInventoryPacket packet) {
        Minecraft.getMinecraft().addScheduledTask(() ->
                Minecraft.getMinecraft().displayGuiScreen(
                        new GuiInventory(Minecraft.getMinecraft().player)));
    }

    public void overrideVanillaLayers() {
        for (RenderPlayer renderer : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            for (Iterator<LayerRenderer<AbstractClientPlayer>> iter = renderer.layerRenderers.listIterator(); iter.hasNext(); ) {
                Class<? extends LayerRenderer> c = iter.next().getClass();
                if (c.equals(LayerCape.class) || c.equals(LayerElytra.class)) {
                    iter.remove();
                }
            }
            renderer.addLayer(new LayerPl3xCape(renderer));
            renderer.addLayer(new LayerPl3xElytra(renderer));
        }
    }
}

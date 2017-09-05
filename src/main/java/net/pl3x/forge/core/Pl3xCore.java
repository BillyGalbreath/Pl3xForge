package net.pl3x.forge.core;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.pl3x.forge.core.block.ModBlocks;
import net.pl3x.forge.core.item.ModItems;
import net.pl3x.forge.core.proxy.ServerProxy;
import net.pl3x.forge.core.scheduler.Pl3xScheduler;
import net.pl3x.forge.core.util.TPSTracker;

@Mod(modid = Pl3xCore.modId, name = Pl3xCore.name, version = Pl3xCore.version)
public class Pl3xCore {
    public static final String modId = "pl3x";
    public static final String name = "Pl3xCore";
    public static final String version = "@VERSION@";

    @Mod.Instance(modId)
    public static Pl3xCore instance;

    @SidedProxy(serverSide = "net.pl3x.forge.core.proxy.ServerProxy", clientSide = "net.pl3x.forge.core.proxy.ClientProxy")
    public static ServerProxy proxy;

    private static final Pl3xScheduler pl3xScheduler = new Pl3xScheduler();
    private static TPSTracker tpsTracker = new TPSTracker();

    public static Pl3xScheduler getScheduler() {
        return pl3xScheduler;
    }

    public static TPSTracker getTpsTracker() {
        return tpsTracker;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        proxy.serverStarting(event);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppingEvent event) {
        proxy.serverStopping(event);
    }

    @Mod.EventBusSubscriber
    public static class EventHandler {
        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModItems.register(event.getRegistry());
            ModBlocks.registerItemBlocks(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            ModBlocks.register(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModItems.registerModels();
            ModBlocks.registerModels();
        }

        @SubscribeEvent
        public static void serverTick(TickEvent.ServerTickEvent event) {
            if (event.side == Side.SERVER && event.phase == TickEvent.Phase.START) {
                getScheduler().tick();
            }
        }
    }
}

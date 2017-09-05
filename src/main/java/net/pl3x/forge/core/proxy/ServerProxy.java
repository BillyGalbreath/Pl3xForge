package net.pl3x.forge.core.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.pl3x.forge.core.CapabilityHandler;
import net.pl3x.forge.core.EventHandler;
import net.pl3x.forge.core.Logger;
import net.pl3x.forge.core.Pl3xCore;
import net.pl3x.forge.core.command.CmdBack;
import net.pl3x.forge.core.command.CmdCountdown;
import net.pl3x.forge.core.command.CmdDelHome;
import net.pl3x.forge.core.command.CmdFly;
import net.pl3x.forge.core.command.CmdFlySpeed;
import net.pl3x.forge.core.command.CmdHome;
import net.pl3x.forge.core.command.CmdHomes;
import net.pl3x.forge.core.command.CmdRTP;
import net.pl3x.forge.core.command.CmdSetHome;
import net.pl3x.forge.core.command.CmdSpawn;
import net.pl3x.forge.core.command.CmdTPA;
import net.pl3x.forge.core.command.CmdTPAHere;
import net.pl3x.forge.core.command.CmdTPAccept;
import net.pl3x.forge.core.command.CmdTPDeny;
import net.pl3x.forge.core.command.CmdTPS;
import net.pl3x.forge.core.command.CmdTPToggle;
import net.pl3x.forge.core.command.CmdTop;
import net.pl3x.forge.core.configuration.ConfigWatcher;
import net.pl3x.forge.core.configuration.PermissionsConfig;
import net.pl3x.forge.core.data.PlayerData;
import net.pl3x.forge.core.data.PlayerDataImpl;
import net.pl3x.forge.core.data.PlayerDataStorage;
import net.pl3x.forge.core.recipe.ModRecipes;
import net.pl3x.forge.core.world.ModWorldGen;

import java.io.File;

public class ServerProxy {
    private Thread configWatcher;

    public void preInit(FMLPreInitializationEvent event) {
        Logger.setLogger(event.getModLog());

        File configDir = new File(event.getModConfigurationDirectory(), Pl3xCore.name);

        configWatcher = new Thread(new ConfigWatcher(configDir.toPath()), "ConfigWatcher");
        configWatcher.start();

        PermissionsConfig.reload(configDir);
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);
        ModRecipes.init();
        registerCapabilities();
    }

    public void postInit(FMLPostInitializationEvent event) {
        Pl3xCore.getTpsTracker().runTaskTimer(1, 1);
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdBack());
        event.registerServerCommand(new CmdCountdown());
        event.registerServerCommand(new CmdDelHome());
        event.registerServerCommand(new CmdFly());
        event.registerServerCommand(new CmdFlySpeed());
        event.registerServerCommand(new CmdHome());
        event.registerServerCommand(new CmdHomes());
        event.registerServerCommand(new CmdRTP());
        event.registerServerCommand(new CmdSetHome());
        event.registerServerCommand(new CmdSpawn());
        event.registerServerCommand(new CmdTop());
        event.registerServerCommand(new CmdTPA());
        event.registerServerCommand(new CmdTPAccept());
        event.registerServerCommand(new CmdTPAHere());
        event.registerServerCommand(new CmdTPDeny());
        event.registerServerCommand(new CmdTPS());
        event.registerServerCommand(new CmdTPToggle());
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        configWatcher.interrupt();
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        // client side only
    }

    public void registerCapabilities() {
        CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerDataStorage(), PlayerDataImpl.class);

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }
}

package net.pl3x.forge.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.pl3x.forge.ChatColor;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.command.CmdBack;
import net.pl3x.forge.command.CmdCountdown;
import net.pl3x.forge.command.CmdDelHome;
import net.pl3x.forge.command.CmdFlip;
import net.pl3x.forge.command.CmdFlipText;
import net.pl3x.forge.command.CmdFly;
import net.pl3x.forge.command.CmdFlySpeed;
import net.pl3x.forge.command.CmdGMAdventure;
import net.pl3x.forge.command.CmdGMCreative;
import net.pl3x.forge.command.CmdGMSpectate;
import net.pl3x.forge.command.CmdGMSurvival;
import net.pl3x.forge.command.CmdHome;
import net.pl3x.forge.command.CmdHomes;
import net.pl3x.forge.command.CmdJump;
import net.pl3x.forge.command.CmdMail;
import net.pl3x.forge.command.CmdPing;
import net.pl3x.forge.command.CmdRTP;
import net.pl3x.forge.command.CmdRussia;
import net.pl3x.forge.command.CmdSetHome;
import net.pl3x.forge.command.CmdShrug;
import net.pl3x.forge.command.CmdSpawn;
import net.pl3x.forge.command.CmdTPA;
import net.pl3x.forge.command.CmdTPAHere;
import net.pl3x.forge.command.CmdTPAccept;
import net.pl3x.forge.command.CmdTPDeny;
import net.pl3x.forge.command.CmdTPS;
import net.pl3x.forge.command.CmdTPToggle;
import net.pl3x.forge.command.CmdTop;
import net.pl3x.forge.command.CmdUnflip;
import net.pl3x.forge.configuration.ConfigWatcher;
import net.pl3x.forge.configuration.IconConfig;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.configuration.MOTDConfig;
import net.pl3x.forge.configuration.MailConfig;
import net.pl3x.forge.configuration.PermsConfig;
import net.pl3x.forge.data.PlayerData;
import net.pl3x.forge.data.PlayerDataImpl;
import net.pl3x.forge.entity.ModEntities;
import net.pl3x.forge.gui.ModGuiHandler;
import net.pl3x.forge.listener.CapabilityHandler;
import net.pl3x.forge.listener.ChatEventHandler;
import net.pl3x.forge.listener.ServerEventHandler;
import net.pl3x.forge.motd.MOTDTask;
import net.pl3x.forge.network.PacketHandler;
import net.pl3x.forge.recipe.ModRecipes;
import net.pl3x.forge.util.TPSTracker;
import net.pl3x.forge.world.ModWorldGen;

import java.io.File;

public class ServerProxy {
    private static TPSTracker tpsTracker;
    private static MOTDTask motdTask = new MOTDTask();
    private Thread configWatcher;

    public static TPSTracker getTpsTracker() {
        return tpsTracker;
    }

    public void preInit(FMLPreInitializationEvent event) {
        File configDir = new File(event.getModConfigurationDirectory(), Pl3x.name);

        Lang.reload(configDir);
        PermsConfig.reload(configDir);
        MailConfig.reload(configDir);
        IconConfig.reload(configDir);
        MOTDConfig.reload(configDir);

        configWatcher = new Thread(new ConfigWatcher(configDir.toPath()), ChatColor.colorize("&1Config&r"));
        configWatcher.start();
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new ModWorldGen(), 0);

        CapabilityManager.INSTANCE.register(PlayerData.class, new PlayerDataImpl.PlayerDataStorage(), PlayerDataImpl.class);
        NetworkRegistry.INSTANCE.registerGuiHandler(Pl3x.instance, new ModGuiHandler());

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        MinecraftForge.EVENT_BUS.register(new ChatEventHandler());

        ModRecipes.init();
        ModEntities.init();
        PacketHandler.init();
    }

    public void postInit(FMLPostInitializationEvent event) {
        tpsTracker = new TPSTracker();
        tpsTracker.runTaskTimer(20, 20); // 1 second
        motdTask.runTaskTimer(1, 100); // 5 seconds
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdBack());
        event.registerServerCommand(new CmdCountdown());
        event.registerServerCommand(new CmdDelHome());
        event.registerServerCommand(new CmdFlip());
        event.registerServerCommand(new CmdFlipText());
        event.registerServerCommand(new CmdFly());
        event.registerServerCommand(new CmdFlySpeed());
        event.registerServerCommand(new CmdGMAdventure());
        event.registerServerCommand(new CmdGMCreative());
        event.registerServerCommand(new CmdGMSpectate());
        event.registerServerCommand(new CmdGMSurvival());
        event.registerServerCommand(new CmdHome());
        event.registerServerCommand(new CmdHomes());
        event.registerServerCommand(new CmdJump());
        event.registerServerCommand(new CmdMail());
        event.registerServerCommand(new CmdPing());
        event.registerServerCommand(new CmdRTP());
        event.registerServerCommand(new CmdRussia());
        event.registerServerCommand(new CmdSetHome());
        event.registerServerCommand(new CmdShrug());
        event.registerServerCommand(new CmdSpawn());
        event.registerServerCommand(new CmdTop());
        event.registerServerCommand(new CmdTPA());
        event.registerServerCommand(new CmdTPAccept());
        event.registerServerCommand(new CmdTPAHere());
        event.registerServerCommand(new CmdTPDeny());
        event.registerServerCommand(new CmdTPS());
        event.registerServerCommand(new CmdTPToggle());
        event.registerServerCommand(new CmdUnflip());
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        configWatcher.interrupt();
    }

    public void registerItemRenderer(Item item, int meta, String id) {
        //
    }
}

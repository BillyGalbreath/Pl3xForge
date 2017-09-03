package net.pl3x.forge.core.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
import net.pl3x.forge.core.CapabilityHandler;
import net.pl3x.forge.core.EventHandler;
import net.pl3x.forge.core.command.CmdBack;
import net.pl3x.forge.core.command.CmdCountdown;
import net.pl3x.forge.core.command.CmdDelHome;
import net.pl3x.forge.core.command.CmdHome;
import net.pl3x.forge.core.command.CmdHomes;
import net.pl3x.forge.core.command.CmdSetHome;
import net.pl3x.forge.core.command.CmdTop;
import net.pl3x.forge.core.data.IPlayerData;
import net.pl3x.forge.core.data.PlayerData;
import net.pl3x.forge.core.data.PlayerDataStorage;

public class CommonProxy {
    public void registerItemRenderer(Item item, int meta, String id) {
    }

    public void registerCapabilities() {
        CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(), PlayerData.class);

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CmdBack());
        event.registerServerCommand(new CmdCountdown());
        event.registerServerCommand(new CmdDelHome());
        event.registerServerCommand(new CmdSetHome());
        event.registerServerCommand(new CmdHome());
        event.registerServerCommand(new CmdHomes());
        event.registerServerCommand(new CmdTop());
    }

    public void serverStopping(FMLServerStoppingEvent event) {
        //
    }
}

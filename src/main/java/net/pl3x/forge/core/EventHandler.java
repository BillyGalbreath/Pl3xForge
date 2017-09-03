package net.pl3x.forge.core;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.core.data.PlayerDataProvider;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Teleport;

import java.lang.reflect.Field;

public class EventHandler {
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerMP)) {
            return; // not a player
        }
        EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
        // fly mode
        // god mode
        // etc etc

        try {
            SPacketPlayerListHeaderFooter packet = new SPacketPlayerListHeaderFooter();
            Field header = packet.getClass().getDeclaredField("field_179703_a");
            Field footer = packet.getClass().getDeclaredField("field_179702_b");
            header.setAccessible(true);
            footer.setAccessible(true);
            header.set(packet, new TextComponentString("\n&6&o&lPl3xCraft\n    &cWebsite&e: &7&opl3x.net\n&9&m-----------------"
                    .replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1")));
            footer.set(packet, new TextComponentString("&9&m-----------------\n&cMap&e: &7&opl3x.net/dynmap\n  &cVoice&e: &7&opl3x.net/discord  \n"
                    .replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1")));
            player.connection.sendPacket(packet);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof EntityPlayerMP)) {
            return; // not a player
        }
        EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
        Teleport.BACK_DB.put(player.getUniqueID(), new Location(player));
        Lang.send(player, Lang.BACK_ON_DEATH);
    }

    @SubscribeEvent

    public void onPlayerClone(PlayerEvent.Clone event) {
        event.getEntityPlayer().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null)
                .setMap(event.getOriginal().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null)
                        .getMap());
    }
}

package net.pl3x.forge.core;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.core.configuration.PermissionsConfig;
import net.pl3x.forge.core.data.PlayerDataProvider;
import net.pl3x.forge.core.permissions.Permissions;
import net.pl3x.forge.core.util.Lang;
import net.pl3x.forge.core.util.Teleport;
import net.pl3x.forge.core.util.TeleportRequest;
import net.pl3x.forge.core.util.Utils;
import org.apache.commons.lang3.text.WordUtils;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

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
            header.set(packet, new TextComponentString(Lang.colorize("\n&6&o&lPl3xCraft\n    &cWebsite&e: &7&opl3x.net\n&9&m-----------------")));
            footer.set(packet, new TextComponentString(Lang.colorize("&9&m-----------------\n&cMap&e: &7&opl3x.net/dynmap\n  &cVoice&e: &7&opl3x.net/discord  \n")));
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
                .setHomes(event.getOriginal().getCapability(PlayerDataProvider.PLAYER_DATA_CAPABILITY, null)
                        .getHomes());
    }

    @SubscribeEvent
    public void onPlayerQuit(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        Iterator<TeleportRequest> iter = Teleport.TELEPORT_REQUESTS.values().iterator();
        while (iter.hasNext()) {
            TeleportRequest request = iter.next();
            if (request.getRequester().equals(event.player) ||
                    request.getTarget().equals(event.player)) {
                request.cancel();
                iter.remove();
            }
        }
    }

    @SubscribeEvent
    public void onServerChatEvent(CommandEvent event) {
        new Thread(Logger.colorizeConsole("&3Command&r")) {
            @Override
            public void run() {
                Logger.info("&6" + event.getSender().getName() + " issued server command: &3/" +
                        event.getCommand().getName() + " " + String.join(" ", event.getParameters()));
            }
        }.start();
    }

    @SubscribeEvent
    public void onServerChatEvent(ServerChatEvent event) {
        EntityPlayerMP sender = event.getPlayer();
        String message = event.getMessage();
        Collection<EntityPlayerMP> recipients = Utils.getPlayers();

        // TODO check for staff chat
        boolean isStaffChat = false;
        if (message.startsWith("s:")) {
            if (!Permissions.hasPermission(sender, "command.staff")) {
                Lang.send(sender, Lang.COMMAND_NO_PERMISSION);
                event.setCanceled(true);
                return;
            }

            isStaffChat = true;
            message = message.substring(2);

            recipients.removeIf(recipient ->
                    !Permissions.hasPermission(recipient, "command.staff"));
        }

        // TODO check for [item] in message
        //
        //

        // check for color codes
        message = Permissions.hasPermission(sender, "chat.color") ?
                message.replaceAll("(?i)&([a-f0-9r])", "\u00a7$1") :
                message.replaceAll("(?i)&([a-f0-9r])", "")
                        .replaceAll("(?i)\u00a7([a-f0-9r])", "");

        // check for format codes
        message = Permissions.hasPermission(sender, "chat.style") ?
                message.replaceAll("(?i)&([k-or])", "\u00a7$1") :
                message.replaceAll("(?i)&([k-or])", "")
                        .replaceAll("(?i)\u00a7([k-or])", "");

        // fix up the format
        String format = (isStaffChat ? Lang.CHAT_STAFF_FORMAT : Lang.CHAT_FORMAT)
                .replace("{prefix}", Permissions.getPrefix(sender))
                .replace("{suffix}", Permissions.getSuffix(sender))
                .replace("{message}", message);

        // build the components
        String[] parts = format.split("\\{sender}");

        TextComponentString component1 = new TextComponentString(Lang.colorize(parts[0]));

        TextComponentString component2 = new TextComponentString(Lang.colorize(parts[1]));

        // TODO build the [item] component from component2
        //
        //

        TextComponentString componentSender = new TextComponentString(sender.getName());
        componentSender.setStyle(new Style()
                .setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                        "/msg " + sender.getName() + " "))
                .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                        new TextComponentString(Lang.colorize(WordUtils.capitalizeFully(
                                PermissionsConfig.getHolder().getPlayer(sender.getUniqueID()).getGroup())
                                + " " + sender.getDisplayNameString())))));

        ITextComponent finalComponent = new TextComponentString("")
                .appendSibling(component1)
                .appendSibling(componentSender)
                .appendSibling(component2);

        event.setComponent(finalComponent);

        // TODO cancel event and manually send for staff only chat
        //
        //
    }
}

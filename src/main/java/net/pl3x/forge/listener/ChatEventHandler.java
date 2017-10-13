package net.pl3x.forge.listener;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.pl3x.forge.ChatColor;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.configuration.PermsConfig;
import net.pl3x.forge.icons.IconManager;
import net.pl3x.forge.permissions.Permissions;
import net.pl3x.forge.util.Utils;
import org.apache.commons.lang3.text.WordUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ChatEventHandler {
    @SubscribeEvent
    public void onChatEvent(ServerChatEvent event) {
        if (event.isCanceled() || event.getPlayer() == null || event.getMessage() == null) {
            return;
        }

        EntityPlayerMP sender = event.getPlayer();
        Collection<EntityPlayerMP> recipients = Utils.getPlayers();

        String messageText = event.getMessage();

        // clean up the message for staff chat
        boolean isStaffChat = false;
        if (messageText.startsWith("s:")) {
            if (!Permissions.hasPermission(sender, "command.staff")) {
                Lang.send(sender, Lang.getData().COMMAND_NO_PERMISSION);
                event.setCanceled(true);
                return;
            }
            isStaffChat = true;
            messageText = messageText.substring(2);
            recipients.removeIf(recipient ->
                    !Permissions.hasPermission(recipient, "command.staff"));
        }

        // clean up the player prefix/suffix
        String prefix = Permissions.getPrefix(sender);
        String suffix = Permissions.getSuffix(sender);

        String group = sender instanceof FakePlayer ? "discord" : "default";
        try {
            group = PermsConfig.getHolder().getPlayer(sender.getUniqueID()).getGroup();
        } catch (Exception ignore) {
        }

        // build the player name component
        ITextComponent componentSender = new TextComponentString(sender.getName())
                .setStyle(new Style()
                        .setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                                "/msg " + sender.getName() + " "))
                        .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                new TextComponentString(ChatColor.colorize(WordUtils.capitalizeFully(group)
                                        + " " + sender.getDisplayNameString())))));

        // compile the chat message
        ITextComponent chatComponent = new TextComponentString("");

        // staff prefix
        if (isStaffChat) {
            chatComponent.appendSibling(new TextComponentString(ChatColor.colorize("&8[&4S&8]")));
        }

        // player prefix
        if (prefix != null && !prefix.isEmpty()) {
            chatComponent.appendSibling(new TextComponentString(ChatColor.colorize(prefix)));
        }

        // player name
        chatComponent.appendSibling(componentSender);

        // player suffix
        if (suffix != null && !suffix.isEmpty()) {
            chatComponent.appendSibling(new TextComponentString(ChatColor.colorize(suffix)));
        }

        // format/message separator
        chatComponent.appendSibling(new TextComponentString(ChatColor.colorize("&3:")));

        messageText = " " + messageText;
        if (sender instanceof FakePlayer) {
            messageText = ChatColor.colorize("&7&o") + messageText;
        }

        // translate icon {tags} to unicode
        messageText = IconManager.INSTANCE.translateIconsTags(messageText);

        // message
        ITextComponent message = Utils.newChatWithLinks(messageText);
        boolean permColor = Permissions.hasPermission(sender, "chat.color");
        boolean permStyle = Permissions.hasPermission(sender, "chat.style");
        if (permColor || permStyle) {
            List<ITextComponent> coloredSiblings = new ArrayList<>();
            String lastColor = "";
            for (ITextComponent sibling : message.getSiblings()) {
                if (sibling.getStyle().getClickEvent() == null) {
                    String text = sibling.getUnformattedComponentText();
                    text = permColor ? text.replaceAll("(?i)&([a-f0-9r])", "\u00a7$1") :
                            text.replaceAll("(?i)&([a-f0-9r])", "").replaceAll("(?i)\u00a7([a-f0-9r])", "");
                    text = permStyle ? text.replaceAll("(?i)&([k-or])", "\u00a7$1") :
                            text.replaceAll("(?i)&([k-or])", "").replaceAll("(?i)\u00a7([k-or])", "");
                    sibling = new TextComponentString(lastColor + text);
                    lastColor = ChatColor.getLastColors(text);
                }
                coloredSiblings.add(sibling);
            }
            message = new TextComponentString("");
            coloredSiblings.forEach(message::appendSibling);
        }

        // handle [item]
        if (messageText.contains("[item]")) {
            List<ITextComponent> itemSiblings = new ArrayList<>();
            for (ITextComponent sibling : message.getSiblings()) {
                String text = sibling.getUnformattedComponentText();
                if (text.contains("[item]")) {
                    ItemStack handItemStack = sender.getHeldItemMainhand().copy();
                    Item handItem = handItemStack.getItem();
                    NBTTagCompound nbt = handItemStack.serializeNBT();
                    if (handItem == Items.WRITABLE_BOOK || handItem == Items.WRITTEN_BOOK) {
                        // remove the pages from writable books (prevents client crash)
                        nbt.setTag("pages", new NBTTagList());
                    }
                    ITextComponent itemComponent = new TextComponentString(
                            ChatColor.colorize("&3[&e" + handItemStack.getDisplayName() +
                                    (handItemStack.getCount() > 1 ? " &7\u00D7&6" + handItemStack.getCount() : "") + "&3]"))
                            .setStyle(new Style()
                                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                                            new TextComponentString(nbt.toString()))));
                    String lastColor = ChatColor.getLastColors(text);
                    String[] parts = text.split("\\[item]", -1);
                    for (int i = 0; i < parts.length; i++) {
                        itemSiblings.add(new TextComponentString(lastColor + parts[i]));
                        if (i + 1 < parts.length) {
                            itemSiblings.add(itemComponent);
                        }
                    }
                } else {
                    itemSiblings.add(sibling);
                }
            }
            message = new TextComponentString("");
            itemSiblings.forEach(message::appendSibling);
        }

        // inject chat icon tooltips to icon unicode
        message = IconManager.INSTANCE.tooltipComponents(message);

        message.getSiblings().forEach(chatComponent::appendSibling);

        // control who sees the chat message
        if (isStaffChat) {
            // only staff members see message
            recipients.forEach(recipient ->
                    recipient.sendMessage(chatComponent));
            event.setCanceled(true);
            return;
        }

        // all online players see message
        event.setComponent(chatComponent);
    }
}

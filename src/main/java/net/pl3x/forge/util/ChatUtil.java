package net.pl3x.forge.util;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.pl3x.forge.color.ChatColor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    // borrowed from ForgeHooks.java
    private static final Pattern URL_PATTERN = Pattern.compile("((?:[a-z0-9]{2,}://)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_]+\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"\u00A7 \n]|$))", Pattern.CASE_INSENSITIVE);

    // borrowed from ForgeHooks.java
    public static ITextComponent newChatWithLinks(String string) {
        // Includes ipv4 and domain pattern
        // Matches an ip (xx.xxx.xx.xxx) or a domain (something.com) with or
        // without a protocol or path.
        ITextComponent component = new TextComponentString("");
        Matcher matcher = URL_PATTERN.matcher(string);
        int lastEnd = 0;
        String lastColor = "";

        // Find all urls
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();

            // Append the previous left overs
            String part = lastColor + string.substring(lastEnd, start);
            if (part.length() > 0) {
                component.appendSibling(new TextComponentString(part));
                lastColor = ChatColor.getLastColors(ChatColor.colorize(part));
            }
            lastEnd = end;

            // build the link component
            String url = string.substring(start, end);
            ITextComponent link = new TextComponentString(lastColor + ChatColor.UNDERLINE + ChatColor.ITALIC + url);

            try {
                // Add schema so client doesn't crash.
                if ((new URI(url)).getScheme() == null) {
                    url = "http://" + url;
                }
            } catch (URISyntaxException e) {
                // Bad syntax bail out!
                component.appendSibling(new TextComponentString(lastColor + url));
                continue;
            }

            // Set the click event and append the link.
            link.getStyle()
                    .setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url))
                    .setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                            new TextComponentString(ChatColor.colorize("&eClick to open link&3:&r\n") + url)));
            component.appendSibling(link);
        }

        // Append the rest of the message.
        String end = string.substring(lastEnd);
        if (end.length() > 0) {
            component.appendSibling(new TextComponentString(lastColor + end));
        }
        return component;
    }
}

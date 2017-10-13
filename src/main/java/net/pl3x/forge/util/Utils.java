package net.pl3x.forge.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.UsernameCache;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.pl3x.forge.ChatColor;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static MinecraftServer serverInstance = FMLCommonHandler.instance().getMinecraftServerInstance();
    private static PlayerList playerList = serverInstance.getPlayerList();

    public static Collection<String> getOnlinePlayerNames() {
        return Arrays.asList(playerList.getOnlinePlayerNames());
    }

    public static Collection<EntityPlayerMP> getPlayers() {
        return new ArrayList<>(playerList.getPlayers());
    }

    public static EntityPlayerMP getPlayer(UUID uuid) {
        return playerList.getPlayerByUUID(uuid);
    }

    public static EntityPlayerMP getPlayer(String name) {
        return getPlayer(name, false);
    }

    public static EntityPlayerMP getPlayer(String name, boolean ignoreCase) {
        for (String tempName : getOnlinePlayerNames()) {
            if (ignoreCase) {
                if (tempName.equalsIgnoreCase(name)) {
                    return playerList.getPlayerByUsername(tempName);
                }
            } else if (tempName.equals(name)) {
                return playerList.getPlayerByUsername(tempName);
            }
        }
        return null;
    }

    public static void chat(EntityPlayerMP player, String message) {
        ServerChatEvent event = new ServerChatEvent(player, message,
                new TextComponentTranslation("chat.type.text",
                        player.getDisplayName(), ForgeHooks.newChatWithLinks(message)));
        if (MinecraftForge.EVENT_BUS.post(event)) {
            return; // event cancelled
        }
        playerList.sendMessage(event.getComponent(), false);
    }

    public static UUID getUUIDFromName(String name) {
        for (Map.Entry<UUID, String> entry : UsernameCache.getMap().entrySet()) {
            if (entry.getValue().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static boolean isOp(EntityPlayerMP player) {
        return playerList.getOppedPlayers().getEntry(player.getGameProfile()) != null;
    }

    public static void setFlySpeed(EntityPlayerMP player, float speed) {
        PlayerCapabilities capabilities = player.capabilities;
        try {
            Field flySpeed = capabilities.getClass().getDeclaredField("field_75096_f");
            flySpeed.setAccessible(true);
            flySpeed.set(capabilities, speed / 2F);
        } catch (IllegalAccessException | NoSuchFieldException ignore) {
        }
    }

    // borrowed from ForgeEssentials
    public static RayTraceResult getLineOfSight(EntityPlayerMP player, double maxDistance) {
        Vec3d lookAt = player.getLook(1);
        Vec3d playerPos = new Vec3d(player.posX, player.posY + (player.getEyeHeight() - player.getDefaultEyeHeight()), player.posZ);
        Vec3d pos1 = playerPos.addVector(0, player.getEyeHeight(), 0);
        Vec3d pos2 = pos1.addVector(lookAt.x * maxDistance, lookAt.y * maxDistance, lookAt.z * maxDistance);
        return player.world.rayTraceBlocks(pos1, pos2);
    }

    // borrowed from ForgeHooks.java
    private static final Pattern URL_PATTERN = Pattern.compile(
            //         schema                          ipv4            OR        namespace                 port     path         ends
            //   |-----------------|        |-------------------------|  |-------------------------|    |---------| |--|   |---------------|
            "((?:[a-z0-9]{2,}:\\/\\/)?(?:(?:[0-9]{1,3}\\.){3}[0-9]{1,3}|(?:[-\\w_]{1,}\\.[a-z]{2,}?))(?::[0-9]{1,5})?.*?(?=[!\"\u00A7 \n]|$))",
            Pattern.CASE_INSENSITIVE);

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

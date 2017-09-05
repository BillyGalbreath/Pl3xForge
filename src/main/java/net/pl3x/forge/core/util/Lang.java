package net.pl3x.forge.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class Lang {
    public static String COMMAND_NO_PERMISSION = "&cYou do not have permission to use this command";
    public static String MUST_SPECIFY_PLAYER = "&cMust specify a player";
    public static String PLAYER_NOT_ONLINE = "&cPlayer is not online";
    public static String COMMAND_ON_COOLDOWN = "&cThat command is on cooldown";

    public static String BACK_ON_DEATH = "&aUse /back to teleport to where you died";
    public static String BACK_NO_LOCATION = "&cNo back location set";
    public static String BACK_TO_PREVIOUS = "&aTeleported to previous location";

    public static String CHAT_FORMAT = "{prefix}{sender}{suffix}&3:&r {message}";
    public static String CHAT_STAFF_FORMAT = "&7[&4S&7]&r{prefix}{sender}{suffix}&4:&r {message}";

    public static String FLY_ONLY_SURVIVAL = "&cFly only works in survival mode";
    public static String FLY_TOGGLED = "&aFly mode toggled {state}";

    public static String FLY_SPEED_INVALID = "&cSpeed must be between 0 and 10";
    public static String FLY_SPEED_SET = "&aFly speed set to {speed}";

    public static String HOME_NOT_FOUND = "&cHome not found";
    public static String HOME_LIMIT_REACHED = "&cYou have reached you homes limit";
    public static String HOME_ALREADY_SET = "&cHome already set. Use /delhome to remove it first";
    public static String HOME_NONE_SET = "&cYou have no homes set";
    public static String HOME_CREATED = "&aHome set";
    public static String HOME_DELETED = "&aHome removed";
    public static String HOME_TELEPORTED = "&aTeleported home";
    public static String HOME_LIST = "&aList of homes&e:\n&d{homes}";

    public static String RTP_NO_SAFE_LOCATION = "&cCould not locate a safe location";
    public static String RTP_SUCCESS = "&aTeleported to a random location";

    public static String SPAWN_TELEPORT = "&aTeleported to server spawn";

    public static String TELEPORT_REQUEST_TIMED_OUT = "&cTeleport request timed out";
    public static String TELEPORT_REQUEST_ACCEPT_TARGET = "&aAccepted teleport request from {requester}";
    public static String TELEPORT_REQUEST_ACCEPT_REQUESTER = "&a{target} accepted teleport request";
    public static String TELEPORT_REQUEST_DENIED_TARGET = "&aDenied teleport request from {requester}";
    public static String TELEPORT_REQUEST_DENIED_REQUESTER = "&a{target} denied teleport request";
    public static String TELEPORT_REQUEST_TARGET_HAS_PENDING = "&c{target} already has pending teleport request";
    public static String TELEPORT_REQUEST_TARGET_TOGGLED_OFF = "&c{target} has disabled teleport requests";
    public static String TELEPORT_REQUEST_TPA_TARGET = "&a{requester} requested to teleport to you\n&aType &7/tpaccept &aor &7/tpdeny";
    public static String TELEPORT_REQUEST_TPAHERE_TARGET = "&a{requester} requested you to teleport to them\n&aType &7/tpaccept &aor &7/tpdeny";
    public static String TELEPORT_REQUEST_REQUESTER = "&aTeleport request sent to {target}";
    public static String TELEPORT_REQUEST_NONE_PENDING = "&cNo pending teleport requests";
    public static String TELEPORT_REQUEST_TP_TOGGLE = "&aTeleport requests toggled {state}";

    public static String TELEPORT_TOP = "&aTeleported to surface";

    public static String TPS_TRACKER = "&aServer TPS is {tps}";

    public static void send(EntityPlayerMP player, String message) {
        for (String part : message.split("\n")) {
            player.sendMessage(new TextComponentString(colorize(part)));
        }
    }

    public static String colorize(String string) {
        return string.replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1");
    }
}

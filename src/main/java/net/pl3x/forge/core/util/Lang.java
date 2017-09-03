package net.pl3x.forge.core.util;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;

public class Lang {
    public static String BACK_ON_DEATH = "&aUse /back to teleport to where you died";
    public static String BACK_NO_LOCATION = "&cNo back location set";
    public static String BACK_TO_PREVIOUS = "&aTeleported to previous location";
    public static String TELEPORT_TOP = "&aTeleported to surface";
    public static String HOME_NOT_FOUND = "&cHome not found";
    public static String HOME_LIMIT_REACHED = "&cYou have reached you homes limit";
    public static String HOME_ALREADY_SET = "&cHome already set. Use /delhome to remove it first";
    public static String HOME_NONE_SET = "&cYou have no homes set";
    public static String HOME_CREATED = "&aHome set";
    public static String HOME_DELETED = "&aHome removed";
    public static String HOME_TELEPORTED = "&aTeleported home";
    public static String HOME_LIST = "&aList of homes&e:\n&d{homes}";

    public static void send(EntityPlayerMP player, String message) {
        for (String part : message.split("\n")) {
            player.sendMessage(new TextComponentString(part.replaceAll("(?i)&([a-f0-9k-or])", "\u00a7$1")));
        }
    }
}

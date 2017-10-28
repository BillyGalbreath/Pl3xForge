package net.pl3x.forge.configuration;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.text.TextComponentString;
import net.pl3x.forge.ChatColor;
import net.pl3x.forge.Logger;
import net.pl3x.forge.Pl3x;

import java.io.File;
import java.io.IOException;

public class Lang extends ConfigLoader implements ConfigBase {
    public static final Lang INSTANCE = new Lang();
    public static final String FILE_NAME = "messages.json";

    public Data data;

    public void init() {
        reload();
    }

    public String file() {
        return FILE_NAME;
    }

    public void reload() {
        Logger.info("Loading " + FILE_NAME + " from disk...");
        try {
            data = loadConfig(new Data(), Data.class, new File(Pl3x.configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public static void send(ICommandSender sender, String message) {
        for (String part : message.split("\n")) {
            sender.sendMessage(new TextComponentString(ChatColor.colorize(part)));
        }
    }

    public class Data {
        public String COMMAND_NO_PERMISSION = "&cYou do not have permission to use this command";
        public String TARGET_NO_PERMISSION = "&cTarget does not have that permission";
        public String MUST_SPECIFY_PLAYER = "&cMust specify a player";
        public String MUST_SPECIFY_MESSAGE = "&cMust specify message";
        public String PLAYER_NOT_ONLINE = "&cPlayer is not online";
        public String PLAYER_NOT_FOUND = "&cPlayer is not found";
        public String COMMAND_ON_COOLDOWN = "&cThat command is on cooldown";
        public String UNKNOWN_COMMAND = "&cUnknown subcommand";

        public String BACK_ON_DEATH = "&aUse /back to teleport to where you died";
        public String BACK_NO_LOCATION = "&cNo back location set";
        public String BACK_TO_PREVIOUS = "&aTeleported to previous location";

        public String FLY_ONLY_SURVIVAL = "&cFly only works in survival mode";
        public String FLY_TOGGLED = "&aFly mode toggled {state}";

        public String FLY_SPEED_INVALID = "&cSpeed must be between 0 and 10";
        public String FLY_SPEED_SET = "&aFly speed set to {speed}";

        public String GAMEMODE_SENDER = "&a{target}'s gamemode set to {gamemode}";
        public String GAMEMODE_TARGET = "&aYour gamemode set to {gamemode}";

        public String HOME_NOT_FOUND = "&cHome not found";
        public String HOME_BED_MISSING = "&cBed is missing from the world";
        public String HOME_LIMIT_REACHED = "&cYou have reached you homes limit";
        public String HOME_CANNOT_MANUALLY_SET_BED = "&cYou cannot manually set your bed home";
        public String HOME_ALREADY_SET = "&cHome already set. Use /delhome to remove it first";
        public String HOME_NONE_SET = "&cYou have no homes set";
        public String HOME_BED_SET = "&aBed home set";
        public String HOME_CREATED = "&aHome set";
        public String HOME_DELETED = "&aHome removed";
        public String HOME_TELEPORTED = "&aTeleported home";
        public String HOME_LIST = "&aList of homes&e:\n&d{homes}";

        public String JUMP_TOO_FAR = "&cToo far away";
        public String JUMP_TELEPORT = "&aJumped to block";

        public String MAIL_SENT = "&aMail sent";
        public String MAIL_CLEARED = "&aYour mailbox has been cleared";
        public String MAIL_ENTRY = "&d[&e{from}&d] &e&o{message}";
        public String MAIL_NONE = "&cYou have no mail!";
        public String MAIL_NOTIFICATION = "&aYou have &7{amount} &aunread messages in your mailbox";

        public String PING = "&a{target}'s ping is {ping}&ams";

        public String RTP_NO_SAFE_LOCATION = "&cCould not locate a safe location";
        public String RTP_SUCCESS = "&aTeleported to a random location";

        public String SPAWN_TELEPORT = "&aTeleported to server spawn";

        public String TELEPORT_REQUEST_TIMED_OUT = "&cTeleport request timed out";
        public String TELEPORT_REQUEST_ACCEPT_TARGET = "&aAccepted teleport request from {requester}";
        public String TELEPORT_REQUEST_ACCEPT_REQUESTER = "&a{target} accepted teleport request";
        public String TELEPORT_REQUEST_DENIED_TARGET = "&aDenied teleport request from {requester}";
        public String TELEPORT_REQUEST_DENIED_REQUESTER = "&a{target} denied teleport request";
        public String TELEPORT_REQUEST_TARGET_HAS_PENDING = "&c{target} already has pending teleport request";
        public String TELEPORT_REQUEST_TARGET_TOGGLED_OFF = "&c{target} has disabled teleport requests";
        public String TELEPORT_REQUEST_TPA_TARGET = "&a{requester} requested to teleport to you\n&aType &7/tpaccept &aor &7/tpdeny";
        public String TELEPORT_REQUEST_TPAHERE_TARGET = "&a{requester} requested you to teleport to them\n&aType &7/tpaccept &aor &7/tpdeny";
        public String TELEPORT_REQUEST_REQUESTER = "&aTeleport request sent to {target}";
        public String TELEPORT_REQUEST_NONE_PENDING = "&cNo pending teleport requests";
        public String TELEPORT_REQUEST_TP_TOGGLE = "&aTeleport requests toggled {state}";

        public String TOP_TELEPORT = "&aTeleported to surface";

        public String TPS_TRACKER_NOW = "&aServer TPS is currently {tps}";
        public String TPS_TRACKER_AVERAGES = "&aTPS from last 1m, 5m, 15m&e: &7[{tps1}&7, {tps5}&7, {tps15}&7]";
    }
}

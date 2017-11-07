package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.UsernameCache;
import net.pl3x.forge.configuration.Lang;
import net.pl3x.forge.configuration.MailConfig;
import net.pl3x.forge.permissions.Permissions;
import net.pl3x.forge.util.PlayerUtil;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class CmdMail extends CommandBase {
    public CmdMail() {
        super("mail", "Manage your mailbox");
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        if (args.length == 1) {
            return Arrays.stream(new String[]{"clear", "read", "send"})
                    .filter(cmd -> cmd.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (args.length >= 2) {
            return UsernameCache.getMap().values().stream()
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        if (args.length == 0) {
            Lang.send(sender, Lang.INSTANCE.data.MAIL_NOTIFICATION
                    .replace("{amount}", Integer.toString(MailConfig.INSTANCE.getMailBag(player).size())));
            return;
        }

        // clear
        if (args[0].equalsIgnoreCase("clear")) {
            MailConfig.INSTANCE.getMailBag(player).clear();
            MailConfig.INSTANCE.save();
            Lang.send(sender, Lang.INSTANCE.data.MAIL_CLEARED);
            return;
        }

        // read
        else if (args[0].equalsIgnoreCase("read")) {
            List<String> mails = MailConfig.INSTANCE.getMailBag(player).getEntries();
            if (mails == null || mails.isEmpty()) {
                Lang.send(sender, Lang.INSTANCE.data.MAIL_NONE);
                return;
            }
            mails.forEach(mail -> Lang.send(sender, mail));
            return;
        }

        // send
        else if (args[0].equalsIgnoreCase("send")) {
            if (!Permissions.hasPermission(player, getPermissionNode() + ".send")) {
                Lang.send(sender, Lang.INSTANCE.data.COMMAND_NO_PERMISSION);
                return;
            }

            if (args.length < 2) {
                Lang.send(sender, Lang.INSTANCE.data.MUST_SPECIFY_PLAYER);
                return;
            }

            if (args.length < 3) {
                Lang.send(sender, Lang.INSTANCE.data.MUST_SPECIFY_MESSAGE);
                return;
            }

            EntityPlayerMP target = PlayerUtil.getPlayer(args[1]);
            MailConfig.MailBag mailBag = MailConfig.INSTANCE.getMailBag(target);
            if (target == null) {
                UUID uuid = PlayerUtil.getUUIDFromName(args[1]);
                if (uuid == null) {
                    Lang.send(sender, Lang.INSTANCE.data.PLAYER_NOT_FOUND);
                    return;
                }
                mailBag = MailConfig.INSTANCE.getMailBag(uuid);
            }

            mailBag.add(sender.getName(), String.join(" ",
                    Arrays.copyOfRange(args, 2, args.length)));
            MailConfig.INSTANCE.save();

            if (target != null) {
                Lang.send(target, Lang.INSTANCE.data.MAIL_NOTIFICATION
                        .replace("{amount}", Integer.toString(mailBag.size())));
            }
            Lang.send(sender, Lang.INSTANCE.data.MAIL_SENT);
            return;
        }

        Lang.send(sender, Lang.INSTANCE.data.UNKNOWN_COMMAND);
        Lang.send(sender, Lang.INSTANCE.data.MAIL_NOTIFICATION
                .replace("{amount}", Integer.toString(MailConfig.INSTANCE.getMailBag(player).size())));
    }
}

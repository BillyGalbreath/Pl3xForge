package net.pl3x.forge.command;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.pl3x.forge.util.PlayerUtil;

public class CmdFlipText extends CommandBase {
    public CmdFlipText() {
        super("fliptext", "Flip some text");
        addAlias("textflip");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (sender.getEntityWorld().isRemote) {
            return; // do not process client side
        }

        EntityPlayerMP player = getCommandSenderAsPlayer(sender);

        String message = "";
        if (args.length > 0) {
            message = String.join(" ", args);
        }

        PlayerUtil.chat(player, flip(message));
    }

    private String flip(String str) {
        String normal = "abcdefghijklmnopqrstuvwxyz_,;.?!/\\'ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String split = "ɐqɔpǝɟbɥıظʞןɯuodbɹsʇnʌʍxʎz‾'؛˙¿¡/\\,∀qϽᗡƎℲƃHIſʞ˥WNOԀὉᴚS⊥∩ΛMXʎZ";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char letter = str.charAt(i);
            int a = normal.indexOf(letter);
            sb.append((a != -1) ? split.charAt(a) : letter);
        }
        return new StringBuilder(sb.toString()).reverse().toString();
    }
}

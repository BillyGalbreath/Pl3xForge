package net.pl3x.forge.motd;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.pl3x.forge.ChatColor;
import net.pl3x.forge.configuration.MOTDConfig;
import org.apache.commons.lang3.Validate;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MOTDCache {
    public final static MOTDCache INSTANCE = new MOTDCache();

    private ITextComponent description;
    private String iconBlob;

    public ITextComponent getDescription() {
        return description;
    }

    public String getIconBlob() {
        return iconBlob;
    }

    public boolean isEmpty() {
        return description == null;
    }

    public void update() {
        updateDescription();
        updateIcon();
    }

    private void updateDescription() {
        List<String> motds = MOTDConfig.getData().motds;
        if (motds == null || motds.isEmpty()) {
            return;
        }
        description = new TextComponentString(ChatColor.colorize(
                motds.get(ThreadLocalRandom.current().nextInt(motds.size()))));
    }

    private void updateIcon() {
        File[] iconFiles = FMLCommonHandler.instance().getMinecraftServerInstance()
                .getFile("icons").listFiles((dir, name) -> name.endsWith(".png"));
        if (iconFiles == null || iconFiles.length < 1) {
            iconBlob = null;
            return;
        }
        int choice = 0;
        if (iconFiles.length > 1) {
            choice = ThreadLocalRandom.current().nextInt(iconFiles.length);
        }
        ByteBuf bytebuf = Unpooled.buffer();
        try {
            BufferedImage bufferedimage = ImageIO.read(iconFiles[choice]);
            Validate.validState(bufferedimage.getWidth() == 64, "Must be 64 pixels wide");
            Validate.validState(bufferedimage.getHeight() == 64, "Must be 64 pixels high");
            ImageIO.write(bufferedimage, "PNG", new ByteBufOutputStream(bytebuf));
            ByteBuf base64 = Base64.encode(bytebuf);
            iconBlob = "data:image/png;base64," + base64.toString(StandardCharsets.UTF_8);
        } catch (Exception ignore) {
            iconBlob = null;
        } finally {
            bytebuf.release();
        }
    }
}

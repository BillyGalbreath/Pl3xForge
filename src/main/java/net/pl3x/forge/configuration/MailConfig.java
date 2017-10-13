package net.pl3x.forge.configuration;

import net.minecraft.entity.player.EntityPlayerMP;
import net.pl3x.forge.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MailConfig extends ConfigLoader {
    static final String FILE_NAME = "mail.json";
    private static File configDir;
    private static MailData data;

    public static MailBag getMailBag(EntityPlayerMP player) {
        return data.getMailBag(player.getUniqueID());
    }

    public static MailBag getMailBag(UUID uuid) {
        return data.getMailBag(uuid);
    }

    public static void reload(File dir) {
        configDir = dir;
        reload();
    }

    public static void reload() {
        Logger.info("Loading " + FILE_NAME + " from disk...");
        try {
            data = loadConfig(new MailData(), MailData.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public static void save() {
        try {
            saveConfig(data, MailData.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class MailData {
        private HashMap<UUID, MailBag> mailBags = new HashMap<>();

        public MailBag getMailBag(UUID uuid) {
            return mailBags.computeIfAbsent(uuid, k -> new MailBag());
        }
    }

    public static class MailBag {
        private List<String> mail = new ArrayList<>();

        public int size() {
            return mail.size();
        }

        public void clear() {
            mail.clear();
        }

        public void add(String sender, String message) {
            mail.add(sender + ":" + message);
        }

        public List<String> getEntries() {
            List<String> entries = new ArrayList<>();
            for (String entry : mail) {
                String[] split = entry.split(":", 2);
                if (split.length < 2) {
                    continue; // improper format; skip
                }
                entries.add(Lang.getData().MAIL_ENTRY
                        .replace("{from}", split[0])
                        .replace("{message}", split[1]));
            }
            return entries;
        }
    }
}

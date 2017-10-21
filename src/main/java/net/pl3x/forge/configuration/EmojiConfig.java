package net.pl3x.forge.configuration;

import net.pl3x.forge.Logger;
import net.pl3x.forge.Pl3x;
import net.pl3x.forge.icons.IconManager;
import net.pl3x.forge.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmojiConfig extends ConfigLoader implements ConfigBase {
    public static final EmojiConfig INSTANCE = new EmojiConfig();
    public static final String FILE_NAME = "emojis.json";

    public Data data;

    public void init() {
        File file = new File(Pl3x.configDir, FILE_NAME);
        if (!file.exists()) {
            FileUtils.saveResource(FILE_NAME, file);
        }
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

        IconManager.INSTANCE.reloadIcons();
    }

    public class Data {
        private List<Emoji> emojis;

        public List<Emoji> getEmojis() {
            return emojis;
        }

        public void setEmojis(List<Emoji> emojis) {
            this.emojis = emojis;
        }
    }

    public class Emoji {
        private String emoji;
        private String description;
        private String hex;
        private String code;
        private boolean supports_fitzpatrick;
        private List<String> aliases;
        private List<String> tags;

        public String getEmoji() {
            return emoji;
        }

        public void setEmoji(String emoji) {
            this.emoji = emoji;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getHex() {
            return hex;
        }

        public void setHex(String hex) {
            this.hex = hex;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public boolean isSupports_fitzpatrick() {
            return supports_fitzpatrick;
        }

        public void setSupports_fitzpatrick(boolean supports_fitzpatrick) {
            this.supports_fitzpatrick = supports_fitzpatrick;
        }

        public List<String> getAliases() {
            return aliases;
        }

        public void setAliases(List<String> aliases) {
            this.aliases = aliases;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }
    }
}

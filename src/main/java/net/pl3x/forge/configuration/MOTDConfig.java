package net.pl3x.forge.configuration;

import net.pl3x.forge.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MOTDConfig extends ConfigLoader {
    static final String FILE_NAME = "motd.json";
    private static File configDir;
    private static Data data;

    public static Data getData() {
        return data;
    }

    public static void init(File dir) {
        configDir = dir;
        reload();
    }

    public static void reload() {
        Logger.info("Loading " + FILE_NAME + " from disk...");
        try {
            data = loadConfig(new Data(), Data.class, new File(configDir, FILE_NAME));
        } catch (IOException e) {
            data = null;
            e.printStackTrace();
        }
    }

    public static class Data {
        public final List<String> motds = new ArrayList<String>() {{
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6The best revenge is massive success");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Join us! We have cookies! \\o/");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6When nothing is going right, go left");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Never steal. The gvmt hates competition");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Smile.. It confuses people");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Adults are just kids with money");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Why do they put pizza in a square box");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Your intelligence is my common sense");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Vegetarians are killing the rainforest");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Quick! What’s the number for 911!?");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Please speak up I'm wearing a towel");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6I intend to live forever, or die trying");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Would a fly without wings be a walk?");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6As I said before, I never repeat myself");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6I scored high on my drug test");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6I would agree if you were right");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6The future just aint what it used to be");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6True skill comes without effort");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6I’m not evil, I’m god with a twist");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6I didn’t hit you. I high-fived your face");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6I am in shape.. Round is a shape");
            add("&7&ki&b&kii&3&kii&7&ki&b&kii&3&kiii&f            -=[&cPl3x&bForge &2Server&f]=-             &3&kiii&b&kii&7&ki&3&kii&b&kii&7&ki\n&ev&d1.12.1 &7- &6Drive it like you stole it!");
        }};
    }
}

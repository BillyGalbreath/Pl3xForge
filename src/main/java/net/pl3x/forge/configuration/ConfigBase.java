package net.pl3x.forge.configuration;

public interface ConfigBase {
    void init();

    void reload();

    String file();
}

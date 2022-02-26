package com.github.meshotron2.room_creator.plugins;

import java.util.List;

public class Config {
    private final List<ConfigEntry> entries;

    public Config(List<ConfigEntry> entries) {
        this.entries = entries;
    }

    public List<ConfigEntry> getEntries() {
        return entries;
    }

    @Override
    public String toString() {
        return "Config{" +
                "entries=" + entries +
                '}';
    }

    public ConfigEntry getPlugin(String plugin) {
        for (ConfigEntry ce : entries)
            if (ce.getPluginFile().equals(plugin))
                return ce;

        return null;
    }
}


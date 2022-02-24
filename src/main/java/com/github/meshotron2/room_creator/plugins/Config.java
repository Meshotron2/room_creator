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

// TODO: 2/23/22 Add a checksum
class ConfigEntry {
    /**
     * The file containing the plugin's executable
     */
    private final String pluginFile;
    /**
     * The command to run to list all materials in the file
     * Should receive the file to read from.
     * Should return a JSON in the format:
     * {
     * "\<material\>": "\<default value\>"
     * }
     */
    private final String listMaterials;
    /**
     * The command to create the dwm file.
     * Should receive the file to write to and a JSON in the format:
     * {
     * "\<material\>": "\<value\>"
     * }
     * Should then create the dwm file
     */
    private final String mapToDwm;

    ConfigEntry(String pluginFile, String listMaterials, String mapToDwm) {
        this.pluginFile = pluginFile;
        this.listMaterials = listMaterials;
        this.mapToDwm = mapToDwm;
    }

    public String getPluginFile() {
        return pluginFile;
    }

    public String getListMaterials() {
        return listMaterials;
    }

    public String getMapToDwm() {
        return mapToDwm;
    }

    @Override
    public String toString() {
        return "ConfigEntry{" +
                "pluginFile='" + pluginFile + '\'' +
                ", listMaterials='" + listMaterials + '\'' +
                ", mapToDwm='" + mapToDwm + '\'' +
                '}';
    }
}

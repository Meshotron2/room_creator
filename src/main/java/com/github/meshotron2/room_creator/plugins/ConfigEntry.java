package com.github.meshotron2.room_creator.plugins;

import java.util.List;

// TODO: 2/23/22 Add a checksum
public class ConfigEntry {
    /**
     * The file containing the plugin's executable
     */
    private final String pluginFile;
    /**
     * The file type(s) the plugin should be able to process
     */
    private final List<String> fileTypes;
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

    ConfigEntry(String pluginFile, List<String> fileTypes, String listMaterials, String mapToDwm) {
        this.pluginFile = pluginFile;
        this.fileTypes = fileTypes;
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
                ", fileTypes='" + fileTypes + '\'' +
                ", listMaterials='" + listMaterials + '\'' +
                ", mapToDwm='" + mapToDwm + '\'' +
                '}';
    }

    public List<String> getFileTypes() {
        return fileTypes;
    }
}

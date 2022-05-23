package com.github.meshotron2.room_creator.communication;

/**
 * Represents the data the GUI sends when sending the room and plugin with which to process it
 */
public class PluginAndRoom {
    /**
     * The plugin to use to process the room
     */
    private final String plugin;
    /**
     * The room to be processed
     */
    private final JSONRoom room;

    PluginAndRoom(String plugin, JSONRoom room) {
        this.plugin = plugin;
        this.room = room;
    }

    public JSONRoom getRoom() {
        return room;
    }

    public String getPlugin() {
        return plugin;
    }

    @Override
    public String toString() {
        return "PluginAndRoom{" +
                "plugin='" + plugin + '\'' +
                ", room=" + room +
                '}';
    }
}

package com.github.meshotron2.room_creator.communication;

public class PluginAndRoom {
    private final String plugin;
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

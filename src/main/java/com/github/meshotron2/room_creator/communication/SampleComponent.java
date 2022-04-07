package com.github.meshotron2.room_creator.communication;

import com.google.gson.JsonObject;

import java.util.Map;

public class SampleComponent implements RoomComponent {
    private String type;
    private JsonObject properties;
    public SampleComponent(String type, JsonObject properties) {
        this.type = type;
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "SampleComponent{" +
                "type='" + type + '\'' +
                ", properties=" + properties +
                '}';
    }
}

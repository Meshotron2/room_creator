package com.github.meshotron2.room_creator.communication;

import com.google.gson.JsonObject;

/**
 * Fallback for room components, storing the type of the component and it's properties as a {@link JsonObject}
 */
public class SampleComponent implements RoomComponent {
    /**
     * The component's type
     */
    private String type;
    /**
     * The properties of the component
     */
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

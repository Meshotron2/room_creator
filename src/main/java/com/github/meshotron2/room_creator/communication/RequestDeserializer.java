package com.github.meshotron2.room_creator.communication;

import com.google.gson.*;

import java.lang.reflect.Type;

public class RequestDeserializer implements JsonDeserializer<Request<?>> {
    @Override
    public Request<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject o = jsonElement.getAsJsonObject();

        final String reqType = o.get("type").getAsString();
        switch (reqType) {
            case "plugin":
                return new Request<>(reqType, o.get("data").getAsString());
            case "room_final":
                return new Request<>(reqType, jsonDeserializationContext.deserialize(o.get("data"), RoomComponent.class));
        }

        return null;
    }
}

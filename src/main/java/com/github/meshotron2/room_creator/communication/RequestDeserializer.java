package com.github.meshotron2.room_creator.communication;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.List;

public class RequestDeserializer implements JsonDeserializer<Request<?>> {
    @Override
    public Request<?> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject o = jsonElement.getAsJsonObject();
        System.out.println("DESERIALIZING " + o.toString());

        final String reqType = o.get("type").getAsString();
        switch (reqType) {
            case "plugin":
                return new Request<>(reqType, o.get("data").getAsString());
            case "room_final":
                return new Request<>(reqType, jsonDeserializationContext.deserialize(o.get("data"), JSONRoom.class));
            case "room_plugin":
//                return new Request<>(reqType, jsonDeserializationContext.deserialize(o.get("data"), PluginAndRoom.class));
                System.out.println("room_plugin");
                Request<PluginAndRoom> pluginAndRoomRequest = new Request<>(reqType, new PluginAndRoom(
                        o.get("data").getAsJsonObject().get("plugin").getAsString(),
                        jsonDeserializationContext.deserialize(
                                o.get("data").getAsJsonObject().get("room"),
                                JSONRoom.class
                        )
                ));
                System.out.println(pluginAndRoomRequest);
                return pluginAndRoomRequest;
        }

        return null;
    }
}


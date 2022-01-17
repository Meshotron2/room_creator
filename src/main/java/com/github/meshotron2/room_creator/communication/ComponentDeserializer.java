package com.github.meshotron2.room_creator.communication;

import com.google.gson.*;

import java.lang.reflect.Type;

public class ComponentDeserializer implements JsonDeserializer<RoomComponent> {
    @Override
    public RoomComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();

        System.out.println("Process " + jsonObject.get("type").getAsString());

        switch (jsonObject.get("type").getAsString()) {
            case "circle":
                CircleComponent circleComponent = new CircleComponent(
                        jsonObject.get("centre_x").getAsString(),
                        jsonObject.get("centre_y").getAsString(),
                        jsonObject.get("centre_z").getAsString(),
                        jsonObject.get("radius").getAsString());

                System.out.println(circleComponent);

                return circleComponent;
            case "cuboid":
                CuboidComponent cuboidComponent = new CuboidComponent(
                        jsonObject.get("x1").getAsString(),
                        jsonObject.get("y1").getAsString(),
                        jsonObject.get("z1").getAsString(),
                        jsonObject.get("x2").getAsString(),
                        jsonObject.get("y2").getAsString(),
                        jsonObject.get("z2").getAsString());

                System.out.println(cuboidComponent);

                return cuboidComponent;
            default:
                return new SampleComponent(
                        jsonObject.get("type").getAsString(),
                        jsonObject
                );
        }
    }
}
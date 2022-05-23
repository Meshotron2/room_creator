package com.github.meshotron2.room_creator.communication;

import com.google.gson.*;

import java.lang.annotation.Documented;
import java.lang.reflect.Type;

/**
 * {@link JsonDeserializer} for every {@link RoomComponent} class.
 *
 * When adding a new component to the system, {@link ComponentDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)}
 * has to be updated accordingly.
 */
public class ComponentDeserializer implements JsonDeserializer<RoomComponent> {

    /**
     * Deserializes every {@link RoomComponent}.
     *
     * See {@link JsonDeserializer#deserialize(JsonElement, Type, JsonDeserializationContext)}
     * for details on the parameters and exceptions.
     *
     * @return The deserialized {@link RoomComponent}
     */
    @Override
    public RoomComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonElement tp = jsonObject.get("type");
        System.out.println("Process " + tp.getAsString());

//        if (jsonObject.has("type"))
//            switch (tp.getAsString()) {
//                case "data":
//                    return jsonObject.get("data");
//            }
//        else
            switch (tp.getAsString()) {
                case "circle":
                    SphereComponent circleComponent = new SphereComponent(
                            jsonObject.get("centre_x").getAsString(),
                            jsonObject.get("centre_y").getAsString(),
                            jsonObject.get("centre_z").getAsString(),
                            jsonObject.get("radius").getAsString(),
                            jsonObject.get("coefficient").getAsString());

                    System.out.println(circleComponent);

                    return circleComponent;
                case "cuboid":
                    CuboidComponent cuboidComponent = new CuboidComponent(
                            jsonObject.get("x1").getAsString(),
                            jsonObject.get("y1").getAsString(),
                            jsonObject.get("z1").getAsString(),
                            jsonObject.get("x2").getAsString(),
                            jsonObject.get("y2").getAsString(),
                            jsonObject.get("z2").getAsString(),
                            jsonObject.get("coefficient").getAsString());

                    System.out.println(cuboidComponent);

                    return cuboidComponent;
                default:
                    return new SampleComponent(
                            tp.getAsString(),
                            jsonObject
                    );
            }
    }
}

package com.github.meshotron2.room_creator.communication;

import java.util.Arrays;
import java.util.Map;

public class JSONRoom {
    private String xg;
    private String yg;
    private String zg;
    private String f;
    private String file;

    private Map<String, RoomComponent> shapes;

    @Override
    public String toString() {
        return "JSONRoom{" +
                "xg='" + xg + '\'' +
                ", yg='" + yg + '\'' +
                ", zg='" + zg + '\'' +
                ", f='" + f + '\'' +
                ", file='" + file + '\'' +
                ", shapes=" + shapes +
                '}';
    }

    private String mapToString(Map<String, RoomComponent> map) {
        final StringBuilder sb = new StringBuilder();

        map.forEach((s, rc) -> sb.append(s).append(": ").append(rc.toString()));

        return sb.toString();
    }
}

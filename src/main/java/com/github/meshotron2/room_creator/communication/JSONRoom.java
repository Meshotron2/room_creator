package com.github.meshotron2.room_creator.communication;

import com.github.meshotron2.room_creator.Room;

import java.io.IOException;
import java.util.Map;

public class JSONRoom {
    private String xg;
    private String yg;
    private String zg;
    private String f;
    private String file;

    private Map<String, RoomComponent> shapes;

    public void write() throws IOException {
        final Room r = new Room(file, Integer.parseInt(xg), Integer.parseInt(yg), Integer.parseInt(zg), Integer.parseInt(f));

        r.startWrite();

        shapes.forEach((s, rc) -> {
            try {
                if (rc instanceof CircleComponent) {
                    final CircleComponent cc = (CircleComponent) rc;
                    r.doSphere(cc.getCentre_x(), cc.getCentre_y(), cc.getCentre_z(), cc.getRadius(), cc.getC());
                }
                if (rc instanceof CuboidComponent) {
                    final CuboidComponent cc = (CuboidComponent) rc;
                    r.doCuboid(cc.getX1(), cc.getX2(), cc.getY1(), cc.getY2(), cc.getZ1(), cc.getZ2(), cc.getC());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        r.endWrite();
    }

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

package com.github.meshotron2.room_creator.communication;

import com.github.meshotron2.room_creator.Room;

import java.io.IOException;
import java.util.Map;

/**
 * Represents a room as data that can be converted to and from JSON.
 * <p>
 * Used to communicate between the GUI and partitioner.
 */
public class JSONRoom {
    /**
     * The number of nodes in the x axis
     */
    private String xg;
    /**
     * The number of nodes in the y axis
     */
    private String yg;
    /**
     * The number of nodes in the z axis
     */
    private String zg;
    /**
     * The sampling frequency.
     */
    private String f;
    /**
     * The file in which this room is stored
     */
    private String file;
    /**
     * Maps the component ids to their data
     */
    private Map<String, RoomComponent> shapes;

    /**
     * Converts this JSON data into a proper {@link Room} and writes its data to a new dwm file.
     *
     * @throws IOException on errors in the file handling
     */
    public void write() throws IOException {
        final Room r = new Room(file, Integer.parseInt(xg), Integer.parseInt(yg), Integer.parseInt(zg), Integer.parseInt(f));

        r.startWrite();

        shapes.forEach((s, rc) -> {
            try {
                if (rc instanceof SphereComponent) {
                    final SphereComponent cc = (SphereComponent) rc;
                    r.doSphere(cc.getCenter_x(), cc.getCenter_y(), cc.getCenter_z(), cc.getRadius(), cc.getC());
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

    /**
     * Converts a Map to a string formatted in JSON.
     *
     * @param map The map to convert
     * @return A JSON string with the map's contents.
     */
    private String mapToString(Map<String, RoomComponent> map) {
        final StringBuilder sb = new StringBuilder();

        map.forEach((s, rc) -> sb.append(s).append(": ").append(rc.toString()));

        return sb.toString();
    }

    public String getFile() {
        return file;
    }
}

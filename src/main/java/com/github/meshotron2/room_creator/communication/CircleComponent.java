package com.github.meshotron2.room_creator.communication;

public class CircleComponent implements RoomComponent{
    private String centre_x;
    private String centre_y;
    private String centre_z;
    private String radius;
    private char c;

    public CircleComponent(String centre_x, String centre_y, String centre_z, String radius) {
        this.centre_x = centre_x;
        this.centre_y = centre_y;
        this.centre_z = centre_z;
        this.radius = radius;
    }

    @Override
    public String toString() {
        return "CircleComponent{" +
                "centre_x='" + centre_x + '\'' +
                ", centre_y='" + centre_y + '\'' +
                ", centre_z='" + centre_z + '\'' +
                ", radius='" + radius + '\'' +
                '}';
    }

    public int getCentre_x() {
        return Integer.parseInt(centre_x);
    }

    public int getCentre_y() {
        return Integer.parseInt(centre_y);
    }

    public int getCentre_z() {
        return Integer.parseInt(centre_z);
    }

    public int getRadius() {
        return Integer.parseInt(radius);
    }

    public char getC() {
        return c;
    }
}

package com.github.meshotron2.room_creator.communication;

public class CircleComponent implements RoomComponent{
    private String centre_x;
    private String centre_y;
    private String centre_z;
    private String radius;

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
}

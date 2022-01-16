package com.github.meshotron2.room_creator.communication;

public class CuboidComponent implements RoomComponent {
    private String x1;
    private String y1;
    private String z1;
    private String x2;
    private String y2;
    private String z2;

    public CuboidComponent(String x1, String y1, String z1, String x2, String y2, String z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    @Override
    public String toString() {
        return "CuboidComponent{" +
                "x1='" + x1 + '\'' +
                ", y1='" + y1 + '\'' +
                ", z1='" + z1 + '\'' +
                ", x2='" + x2 + '\'' +
                ", y2='" + y2 + '\'' +
                ", z2='" + z2 + '\'' +
                '}';
    }
}

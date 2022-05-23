package com.github.meshotron2.room_creator.communication;

/**
 * Describes a Sphere
 */
public class SphereComponent implements RoomComponent {
    /**
     * X coordinate of the center point
     */
    private String center_x;
    /**
     * Y coordinate of the center point
     */
    private String center_y;
    /**
     * Z coordinate of the center point
     */
    private String center_z;
    /**
     * Sphere's radius
     */
    private String radius;
    /**
     * The refraction coefficient.
     * <p>
     * See the official dmw specification for possible values
     */
    private char c;

    /**
     * Creates a Sphere component.
     *
     * @param center_x X coordinate of the center point
     * @param center_y Y coordinate of the center point
     * @param center_z Z coordinate of the center point
     * @param radius   Sphere's radius
     * @param c        The refraction coefficient
     */
    public SphereComponent(String center_x, String center_y, String center_z, String radius, String c) {
        this.center_x = center_x;
        this.center_y = center_y;
        this.center_z = center_z;
        this.radius = radius;
        this.c = c.charAt(0);
    }

    public int getCenter_x() {
        return Integer.parseInt(center_x);
    }

    public int getCenter_y() {
        return Integer.parseInt(center_y);
    }

    public int getCenter_z() {
        return Integer.parseInt(center_z);
    }

    public int getRadius() {
        return Integer.parseInt(radius);
    }

    public char getC() {
        return c;
    }

    @Override
    public String toString() {
        return "CircleComponent{" +
                "centre_x='" + center_x + '\'' +
                ", centre_y='" + center_y + '\'' +
                ", centre_z='" + center_z + '\'' +
                ", radius='" + radius + '\'' +
                ", c='" + c + '\'' +
                '}';
    }
}

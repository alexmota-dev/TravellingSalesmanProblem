package com.compilers.entitys;

public class Point {
    private String id;
    private int x;
    private int y;

    public Point(String id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public String getId() {
        return id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(String name) {
        this.id = name;
    }

    @Override
    public String toString() {
        return "\nPoint{" +
                id + " " +
                "x=" + x +
                ", y=" + y +
                "}";
    }

}

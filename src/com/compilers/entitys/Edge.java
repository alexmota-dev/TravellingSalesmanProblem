package com.compilers.entitys;

public class Edge {
    private Point pointA;
    private Point pointB;
    private float length;

    public Edge(Point pointA, Point pointB, float length) {
        this.pointA = pointA;
        this.pointB = pointB;
        this.length = length;
    }

    public Point getPointA() {
        return pointA;
    }

    public void setPointA(Point pointA) {
        this.pointA = pointA;
    }

    public Point getPointB() {
        return pointB;
    }

    public void setPointB(Point pointB) {
        this.pointB = pointB;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }
    @Override
    public String toString() {
        return "\nEdge{" +
                pointA.getId()
                + " - " +
                length
                +  " - " +
                pointB.getId() + '}';
    }

}

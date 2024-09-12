package com.compilers.entitys;

import java.util.ArrayList;
import java.util.Arrays;

public class CartesianPlane {
    private ArrayList<Point> points;
    private ArrayList<Edge> edges;

    public CartesianPlane(ArrayList<Point> points, ArrayList<Edge> edges) {
        this.points = points;
        this.edges = edges;
    }

    public CartesianPlane() {
        this.points = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Point> points) {
        this.points = points;
    }

    public void addPoint(Point point) {
        this.points.add(point);
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public String toString() {
        return "CartesianPlane{" +
                "points=" + Arrays.toString(new ArrayList[]{points}) +
                "}\n" +
                "edges=" + Arrays.toString(new ArrayList[]{edges}) +
                        '}';
    }
}

package com.compilers;

import com.compilers.entitys.CartesianPlane;
import com.compilers.entitys.Point;
import com.compilers.entitys.Edge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

// Complexidade O(n!)
public class Main {
    //Global
    static ArrayList<Point> pointsList = new ArrayList<>(10);
    static ArrayList<String> cities = new ArrayList<>(10);
    static int MINIMUM = 0;
    static int MAXIMUM = 100;

    public static void main(String[] args) {
        int vertexNumber = definedVertexNumber();
        CartesianPlane cartesianPlane = generateCartesianPlane(vertexNumber);

        printCartesianPlane(cartesianPlane, vertexNumber);
        calculateBestPath(cartesianPlane);
    }

    private static int definedVertexNumber(){
        System.out.println("Escolha o número de vértices: ");
        Scanner input = new Scanner(System.in);
        int vertexNumber = input.nextInt();
        while (vertexNumber <= 1){
            System.out.println("Escolha um valor maior que 1: ");
            vertexNumber = input.nextInt();
        }

        return vertexNumber;
    }

    private static CartesianPlane generateCartesianPlane(int vertexNumber) {
        ArrayList<Point> points = generatePoints(vertexNumber);
        ArrayList<Edge> edges =  generateEdges(points);

        CartesianPlane cartesianPlane = new CartesianPlane(points, edges);
        return cartesianPlane;
    }

    private static CartesianPlane generateCartesianPlaneMock(int vertexNumber) {
        ArrayList<Point> points = generatePointsMock(vertexNumber);
        ArrayList<Edge> edges =  generateEdgesMock(points);

        CartesianPlane cartesianPlane = new CartesianPlane(points, edges);
        return cartesianPlane;
    }

    private static ArrayList<Point> generatePoints(int vertexNumber){
        ArrayList<Point> points = new ArrayList<>(vertexNumber);

        for (int i = 0; i <= vertexNumber; i++){
            Point newPoint = generatePointNotRepeat();
            points.add(newPoint);
        }

        return points;
    }

    private static ArrayList<Point> generatePointsMock(int vertexNumber){
        ArrayList<Point> points = new ArrayList<>(vertexNumber);

        for (int i = 0; i <= vertexNumber; i++){
            Point newPoint = new Point(String.valueOf(i), i, i);
            points.add(newPoint);
        }

        return points;
    }

    private static ArrayList<Edge> generateEdges(ArrayList<Point> points){
        ArrayList<Edge> edges = new ArrayList<>(points.size());

        for (int i = 0; i < points.size(); i++) {
            boolean existLeastOneEdge = false;
            for (int j = i + 1; j < points.size(); j++) {
                boolean existEdgeTemp = generateBoolean();

                if(existEdgeTemp){
                    existLeastOneEdge = existEdgeTemp;
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);
                    float distance = calculateDistanec(p1, p2);

                    Edge edge = new Edge(p1, p2, distance);
                    edges.add(edge);
                }

                if(j == points.size() - 1 && !existLeastOneEdge){
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);
                    float distance = calculateDistanec(p1, p2);

                    Edge edge = new Edge(p1, p2, distance);
                    edges.add(edge);
                }
            }
        }

        return edges;
    }

    private static ArrayList<Edge> generateEdgesMock(ArrayList<Point> points){
        ArrayList<Edge> edges = new ArrayList<>(points.size());

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if(i != j){
                    Point p1 = points.get(i);
                    Point p2 = points.get(j);
                    float distance = calculateDistanec(p1, p2);

                    Edge edge = new Edge(p1, p2, distance);
                    edges.add(edge);
                }
            }
        }

        return edges;
    }

    private static Point generatePointNotRepeat(){
        int x = generateRandomNumberBetween(MINIMUM, MAXIMUM);
        int y = generateRandomNumberBetween(MINIMUM, MAXIMUM);
        String cityName = generateNewCityName();

        while(existisPointSameCordinate(x, y)){
            x = generateRandomNumberBetween(MINIMUM, MAXIMUM);
            y = generateRandomNumberBetween(MINIMUM, MAXIMUM);
        }

        Point newPoint = new Point(cityName, x, y);

        pointsList.add(newPoint);
        return newPoint;
    }

    private static int generateRandomNumberBetween(int min, int max){
        Random random = new Random();
        int randomNumber = random.nextInt((max - min) + 1) + min;
        return randomNumber;
    }

    private static void printCartesianPlane(CartesianPlane cartesianPlane, int vertexNumber){
        System.out.println("============= Mapeamento dos "+ vertexNumber +" vértices =============");
        System.out.println(cartesianPlane.toString());
        printDistances(cartesianPlane);
    }

    private static String generateRandomString() {
        Random random = new Random();
        StringBuilder combinacao = new StringBuilder(3);

        for (int i = 0; i < 3; i++) {
            char letraAleatoria = (char) (random.nextInt(26) + 'a'); // Gera um número entre 0 e 25 e adiciona 'a'
            combinacao.append(letraAleatoria);
        }

        return combinacao.toString();
    }

    private static String generateNewCityName(){
        String city = generateRandomString();
        while(cities.contains(city)){
            city = generateRandomString();
        }

        cities.add(city);
        return city;
    }

    private static boolean existisPointSameCordinate(int x, int y){
        for(Point point : pointsList){
            if(point.getX() == x && point.getY() == y){
                return true;
            }
        }
        return false;
    }

    private static void printDistances(CartesianPlane cartesianPlane) {
        ArrayList<Point> points = cartesianPlane.getPoints();
        ArrayList<Edge> edges = cartesianPlane.getEdges();

        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                Point p1 = points.get(i);
                Point p2 = points.get(j);
                float distance = calculateDistanec(p1, p2);

                if(edges.contains(new Edge(p1, p2, distance))){
                    System.out.println("Distância entre (" + p1.getX() + ", " + p1.getY() + ") e (" + p2.getX() + ", " + p2.getY() + ") = " + distance);
                }
            }
        }
    }

    private static float calculateDistanec(Point p1, Point p2) {
        double exactDistance = Math.sqrt(Math.pow((p2.getX() - p1.getX()), 2) + Math.pow((p2.getY() - p1.getY()), 2));
        return (float) (Math.round(exactDistance * 100.0) / 100.0);
    }

    private static boolean generateBoolean(){
        Random random = new Random();
        return random.nextBoolean();
    }

    public static void calculateBestPath(CartesianPlane cartesianPlane) {
        ArrayList<Point> points = cartesianPlane.getPoints();
        ArrayList<Edge> edges = cartesianPlane.getEdges();

        float litlePath = Float.MAX_VALUE;
        ArrayList<Point> bestPath = null;

        ArrayList<ArrayList<Point>> permutacoes = generatePermutations(points);

        for (ArrayList<Point> permutacao : permutacoes) {
            float path = calculatePathCost(permutacao, edges);

            if (path <= litlePath) {
                litlePath = path;
                bestPath = new ArrayList<>(permutacao);
            }
        }

        System.out.println("\n------------------------------------");

        for(int i =0; i < bestPath.size()-1; i++){
            System.out.print(bestPath.get(i).getId());
            if(i < bestPath.size()-2){
                System.out.print(" -> ");
            }
        }

        System.out.println("\n------------------------------------");

//        System.out.println("Melhor caminho: " + bestPath);
        System.out.println("Distância total: " + litlePath);
    }

    private static ArrayList<ArrayList<Point>> generatePermutations(ArrayList<Point> points) {
        ArrayList<ArrayList<Point>> permutations = new ArrayList<>();
        permute(points, 0, permutations);
        return permutations;
    }

    private static void permute(ArrayList<Point> points, int l, ArrayList<ArrayList<Point>> permutations) {
        if (l == points.size() - 1) {
            permutations.add(new ArrayList<>(points));
        } else {
            for (int i = l; i < points.size(); i++) {
                Collections.swap(points, l, i);
                permute(points, l + 1, permutations);
                Collections.swap(points, l, i); // Reverte a troca
            }
        }
    }

    private static float calculatePathCost(ArrayList<Point> path, ArrayList<Edge> edges) {
        float totalDistance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            Point p1 = path.get(i);
            Point p2 = path.get(i + 1);
            Edge edge = findEdge(p1, p2, edges);

            if (edge != null) {
                totalDistance += edge.getLength();
            } else {
                return Float.MAX_VALUE;
            }
        }

        Edge returnEdge = findEdge(path.get(path.size() - 1), path.get(0), edges);
        if (returnEdge != null) {
            totalDistance += returnEdge.getLength();
        } else {
            return Float.MAX_VALUE;
        }

        return totalDistance;
    }

    private static Edge findEdge(Point p1, Point p2, ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            if ((edge.getPointA().equals(p1) && edge.getPointB().equals(p2)) ||
                    (edge.getPointA().equals(p2) && edge.getPointB().equals(p1))) {
                return edge;
            }
        }
        return null;
    }
}
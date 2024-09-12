package com.compilers;

import com.compilers.entitys.CartesianPlane;
import com.compilers.entitys.Point;
import com.compilers.entitys.Edge;

import java.util.*;

// Complexidade O(n!)
public class Main {
    //Global
    static ArrayList<Point> pointsList = new ArrayList<>(10);
    static ArrayList<String> cities = new ArrayList<>(10);
    static int MINIMUM = 0;
    static int MAXIMUM = 100;

    public static void main(String[] args) {

        CartesianPlane cartesianPlane = null;
        int vertexNumber = definedVertexNumber();

        if(isMock()){
            cartesianPlane = generateCartesianPlaneMock(vertexNumber);
        }
        else{
            cartesianPlane = generateCartesianPlane(vertexNumber);
        }

        printCartesianPlane(cartesianPlane, vertexNumber);

        calculateWithnNarestNeighbor(cartesianPlane);
    }

    private static boolean isMock(){
        System.out.println("Você deseja que aplicação funcione como mock ?");
        System.out.println("[0] - MOCK");
        System.out.println("[1] - REAL");

        Scanner input = new Scanner(System.in);
        int option = input.nextInt();
        while (option < 0 || option > 1){
            System.out.println("Escolha um valor maior que 1: ");
            option = input.nextInt();
        }

        return option == 0;
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

        for (int i = 0; i < vertexNumber; i++){
            Point newPoint = generatePointNotRepeat();
            points.add(newPoint);
        }

        return points;
    }

    private static ArrayList<Point> generatePointsMock(int vertexNumber){
        ArrayList<Point> points = new ArrayList<>(vertexNumber);

        for (int i = 0; i < vertexNumber; i++){
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

    public static void calculateWithBruteForce(CartesianPlane cartesianPlane) {
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

        if(bestPath != null){
            System.out.println("\n------------------------------------");

            for(int i =0; i < bestPath.size()-1; i++){
                System.out.print(bestPath.get(i).getId());
                if(i < bestPath.size()-2){
                    System.out.print(" -> ");
                }
            }

            System.out.println("\n------------------------------------");

            System.out.println("Distância total: " + litlePath);
        }
        else{
            System.out.println("Não existe caminho que satisfaça o problema.");
        }
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
                Collections.swap(points, l, i);
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

    //Pelo vizinho mais proximo
    public static Edge findNearestEdge(Point from, Set<Point> visited, List<Edge> edges) {
        float minDistance = Float.MAX_VALUE;
        Edge nearestEdge = null;

        for (Edge edge : edges) {
            Point otherPoint = (edge.getPointA().equals(from)) ? edge.getPointB() : edge.getPointA();
            if (!visited.contains(otherPoint) && edge.getLength() < minDistance) {
                minDistance = edge.getLength();
                nearestEdge = edge;
            }
        }

        return nearestEdge;
    }

    public static List<Point> narestNeighbor(Point start, List<Point> points, List<Edge> edges) {
        Set<Point> visited = new HashSet<>();
        List<Point> path = new ArrayList<>();
        Point current = start;

        path.add(current);
        visited.add(current);

        while (visited.size() < points.size()) {
            Edge nearestEdge = findNearestEdge(current, visited, edges );
            if (nearestEdge == null) break;

            Point nextPoint = nearestEdge.getPointA().equals(current) ? nearestEdge.getPointB() : nearestEdge.getPointA();
            path.add(nextPoint);
            visited.add(nextPoint);

            current = nextPoint;
        }

        path.add(start);
        return path;
    }

    public static String formatPath(List<Point> path) {
        StringBuilder formattedPath = new StringBuilder();
        for (int i = 0; i < path.size(); i++) {
            formattedPath.append(path.get(i).getId());
            if (i < path.size() - 1) {
                formattedPath.append(" -> ");
            }
        }
        return formattedPath.toString();
    }

    public static Edge findEdgeBetween(Point a, Point b, List<Edge> edges) {
        for (Edge edge : edges) {
            if ((edge.getPointA().equals(a) && edge.getPointB().equals(b)) ||
                    (edge.getPointA().equals(b) && edge.getPointB().equals(a))) {
                return edge;
            }
        }
        return null;
    }

    public static float calculateTotalDistance(List<Point> path, List<Edge> edges) {
        float totalDistance = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            Edge edge = findEdgeBetween(path.get(i), path.get(i + 1), edges);
            if (edge != null) {
                totalDistance += edge.getLength();
            }
        }
        return totalDistance;
    }

    private static void calculateWithnNarestNeighbor(CartesianPlane cartesianPlane){
        List<Point> path = narestNeighbor(cartesianPlane.getPoints().get(0), cartesianPlane.getPoints(), cartesianPlane.getEdges());
        float totalDistance = calculateTotalDistance(path, cartesianPlane.getEdges());

        System.out.println("\n------------------------------------");
        System.out.println("Caminho: " + formatPath(path));
        System.out.println("Distância total: " + totalDistance);
        System.out.println("\n------------------------------------");
    }
}
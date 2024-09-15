package com.compilers.util;

import com.compilers.entitys.CartesianPlane;
import com.compilers.entitys.Edge;
import com.compilers.entitys.Point;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class GenerateFile {

    public static void generatePythonFile(CartesianPlane plane, List<Point> path, Point start) {
        StringBuilder pythonCode = new StringBuilder();

        pythonCode.append("import matplotlib.pyplot as plt\n");
        pythonCode.append("import matplotlib.patches as mpatches\n");
        pythonCode.append("import networkx as nx\n\n");

        pythonCode.append("points = {\n");
        for (Point point : plane.getPoints()) {
            pythonCode.append("    '").append(point.getId()).append("': (")
                    .append(point.getX()).append(", ").append(point.getY()).append("),\n");
        }
        pythonCode.append("}\n\n");

        // Adicionar as arestas
        pythonCode.append("edges = [\n");
        for (Edge edge : plane.getEdges()) {
            pythonCode.append("    ('").append(edge.getPointA().getId()).append("', '")
                    .append(edge.getPointB().getId()).append("', ").append(edge.getLength()).append("),\n");
        }
        pythonCode.append("]\n\n");

        // Caminho destacado
        pythonCode.append("highlight_path = [");
        for (int i = 0; i < path.size(); i++) {
            pythonCode.append("'").append(path.get(i).getId()).append("'");
            if (i < path.size() - 1) pythonCode.append(", ");
        }
        pythonCode.append("]\n\n");

        pythonCode.append("G = nx.Graph()\n\n");

        pythonCode.append("for point, coord in points.items():\n");
        pythonCode.append("    G.add_node(point, pos=coord)\n\n");

        pythonCode.append("for edge in edges:\n");
        pythonCode.append("    G.add_edge(edge[0], edge[1], weight=edge[2])\n\n");

        pythonCode.append("pos = nx.get_node_attributes(G, 'pos')\n\n");

        pythonCode.append("normal_edges = [edge for edge in G.edges() if edge not in zip(highlight_path, highlight_path[1:]) and edge not in zip(highlight_path[1:], highlight_path)]\n");
        pythonCode.append("nx.draw(G, pos, with_labels=True, node_size=500, node_color='lightblue', font_size=10, font_weight='bold', edgelist=normal_edges, edge_color='gray')\n\n");

        pythonCode.append("highlight_edges = list(zip(highlight_path, highlight_path[1:])) + [(highlight_path[-1], highlight_path[0])]  # Fechar o ciclo\n");
        pythonCode.append("nx.draw(G, pos, with_labels=True, node_size=500, node_color='lightblue', font_size=10, font_weight='bold', edgelist=highlight_edges, edge_color='red', width=2.5)\n\n");

        pythonCode.append("start_point = '").append(start.getId()).append("'\n");
        pythonCode.append("nx.draw_networkx_nodes(G, pos, nodelist=[start_point], node_color='green', node_size=600)\n\n");

        pythonCode.append("edge_labels = {(u, v): f'{d[\"weight\"]:.2f}' for u, v, d in G.edges(data=True)}\n");
        pythonCode.append("nx.draw_networkx_edge_labels(G, pos, edge_labels=edge_labels)\n\n");

        pythonCode.append("plt.title(\"Grafo com Caminho Destacado\")\n");

        pythonCode.append("red_patch = mpatches.Patch(color='red', label='Caminho')\n");
        pythonCode.append("gray_patch = mpatches.Patch(color='gray', label='Arestas do Grafo')\n");
        pythonCode.append("green_patch = mpatches.Patch(color='green', label='Ponto Inicial')\n");
        pythonCode.append("plt.legend(handles=[red_patch, gray_patch, green_patch])\n");

        pythonCode.append("plt.show()\n");

        try (FileWriter fileWriter = new FileWriter("generated_graph.py")) {
            fileWriter.write(pythonCode.toString());
            System.out.println("Arquivo Python gerado com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo Python: " + e.getMessage());
        }
    }

}

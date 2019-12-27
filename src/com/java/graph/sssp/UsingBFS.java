package com.java.graph.sssp;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.java.graph.Vertex;

public class UsingBFS {

	// This Graph is Directed and Positive...
	// BFS does not work with weighted Graph, because it always goes by adjacent
	// nodes, so if another path with short distance exists it will be ignored...

	public static void main(String[] args) {

		Graph graph = new UsingBFS.Graph();
		graph.add("chennai");
		graph.add("coimbatore");
		graph.add("salem");
		graph.add("vellore");
		graph.add("dindugal");
		graph.add("tiruchy");
		graph.add("madurai");
		graph.add("kodaikanal");
		graph.add("tirunelveli");
		graph.add("ooty");
		graph.add("nagercoil");

		graph.connect("chennai", "vellore", 150);
		graph.connect("chennai", "salem", 320);
		graph.connect("vellore", "salem", 275);
		graph.connect("salem", "coimbatore", 120);
		graph.connect("chennai", "tiruchy", 350);
		graph.connect("tiruchy", "dindugal", 120);
		graph.connect("dindugal", "madurai", 90);
		graph.connect("dindugal", "kodaikanal", 100);
		graph.connect("madurai", "tirunelveli", 130);
		graph.connect("tirunelveli", "nagercoil", 170);
		graph.connect("coimbatore", "ooty", 115);

		graph.bfs("chennai");
	}

	public static class Graph {

		List<Vertex> vertices = new LinkedList<>();

		public void add(String city) {
			vertices.add(new Vertex(city));
		}

		public void bfs(String city) {
			Vertex start = getCity(city);
			start.distance = 0;
			Queue<Vertex> queue = new ArrayDeque<>();
			queue.add(start);
			while (!queue.isEmpty()) {
				Vertex firstVertex = queue.remove();
				print(firstVertex);
				System.out.println();
				firstVertex.isVisited = true;
				for (Vertex neighbor : firstVertex.neighbors) {
					if (!neighbor.isVisited) {
						neighbor.isVisited = true;
						neighbor.parent = firstVertex;
						queue.add(neighbor);
					}
				}
			}
		}

		private void print(Vertex vertex) {
			if (vertex.parent != null) {
				print(vertex.parent);
			}
			System.out.print(vertex.name + " -> ");
		}

		public void connect(String src, String dest, int distance) {
			Vertex source = getCity(src);
			Vertex destination = getCity(dest);

			source.neighbors.add(destination);
			source.weights.put(destination, distance);
			destination.neighbors.add(source);
		}

		private Vertex getCity(String city) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(city)).findFirst().orElse(null);
		}
	}

}

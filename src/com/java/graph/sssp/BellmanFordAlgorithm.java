package com.java.graph.sssp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BellmanFordAlgorithm {

	public static void main(String[] args) {

		Graph graph = new BellmanFordAlgorithm.Graph();
//		graph.add("chennai");
//		graph.add("coimbatore");
//		graph.add("salem");
//		graph.add("vellore");
//		graph.add("dindugal");
//		graph.add("tiruchy");
//		graph.add("madurai");
//		graph.add("kodaikanal");
//		graph.add("tirunelveli");
//		graph.add("ooty");
//		graph.add("nagercoil");
//		graph.add("arur");
//
//		graph.connect("chennai", "vellore", 150);
//		graph.connect("chennai", "salem", 320);
//		graph.connect("vellore", "salem", 275);
//		graph.connect("vellore", "coimbatore", 225);
//		graph.connect("chennai", "arur", 165);
//		graph.connect("arur", "salem", 120);
//		graph.connect("salem", "coimbatore", 120);
//		graph.connect("chennai", "tiruchy", 350);
//		graph.connect("tiruchy", "dindugal", 120);
//		graph.connect("dindugal", "madurai", 90);
//		graph.connect("dindugal", "kodaikanal", 100);
//		graph.connect("madurai", "tirunelveli", 130);
//		graph.connect("tirunelveli", "nagercoil", 170);
//		graph.connect("coimbatore", "ooty", 115);

		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");
		graph.add("E");

		graph.connect("A", "C", 6); // Add A-> C , weight 6
		graph.connect("B", "A", 3); // Add B-> A , weight 3
		//graph.connect("A", "D", 6); // Add A-> D , weight 6
		graph.connect("A","D",-6); //Add A-> D , weight -6 TEST NEGATIVE WEIGHT
		// HERE
		graph.connect("D", "C", 1); // Add D-> C , weight 1
		graph.connect("C", "D", 2); // Add C-> D , weight 2
		graph.connect("D", "B", 1); // Add D-> B , weight 1
		graph.connect("E", "D", 2); // Add E-> D , weight 2
		graph.connect("E", "B", 4); // Add E-> B , weight 4

		graph.bellmanford("E");
	}

	public static class Graph {

		public class Vertex {

			public String name;
			public int index;
			public int distance;
			public List<Vertex> neighbors = new ArrayList<>();
			public Map<Vertex, Integer> weights = new HashMap<>();
			public boolean isVisited;
			public Vertex parent;

			public Vertex(String name) {
				this.name = name;
				this.distance = Integer.MAX_VALUE;
			}

			public Vertex(String name, int index) {
				this.name = name;
				this.distance = Integer.MAX_VALUE;
				this.index = index;
			}
		}

		List<Vertex> vertices = new LinkedList<>();

		public void add(String city) {
			vertices.add(new Vertex(city));
		}

		public void bellmanford(String city) {
			Vertex start = getCity(city);
			start.distance = 0;

			for (int i = 0; i < vertices.size(); i++) {
				for (Vertex vertex : vertices) {
					for (Vertex neighbor : vertex.neighbors) {
						long distance = (long) vertex.distance + (long) vertex.weights.get(neighbor);
						if (neighbor.distance > distance) {
							neighbor.distance = (int) distance;
							neighbor.parent = vertex;
						}
					}
				}
			}

			System.out.println("Detecting Negative Cycle...");
			for (Vertex vertex : vertices) {
				for (Vertex neighbor : vertex.neighbors) {
					long distance = (long) vertex.distance + (long) vertex.weights.get(neighbor);
					if (neighbor.distance > distance) {
						System.out.println("Negative Cycle Exists");
						System.out.println("Negative Cycle exists between " + vertex.name + " and " + neighbor.name);
						return;
					}
				}
			}
			System.out.println("No Negative Cycle Found...");

			for (Vertex vertex : vertices) {
				if (vertex.name == start.name) {
					continue;
				} else if (vertex.distance >= Integer.MAX_VALUE || vertex.distance <= Integer.MIN_VALUE) {
					System.out.println("No Path Exists to " + vertex.name + " from " + start.name);
				} else {
					System.out.print("Shortest Path to reach " + vertex.name + " from " + start.name + "(Distance "
							+ vertex.distance + ") : ");
					print(vertex, 0);
				}
				System.out.println();
				System.out.println();
			}
		}

		public void edges() {
			int count = 0;
			for (Vertex vertex : vertices) {
				count += vertex.neighbors.size();
			}
			System.out.println("No of Edges in this Weighted Graph : " + count);
		}

		private void print(Vertex vertex, int distance) {
			if (vertex.parent != null) {
				print(vertex.parent, distance);
			}
			System.out.print(vertex.name + " -> ");
		}

		public void connect(String src, String dest, int distance) {
			Vertex source = getCity(src);
			Vertex destination = getCity(dest);

			source.neighbors.add(destination);
			source.weights.put(destination, distance);
			// destination.neighbors.add(source);
			// destination.weights.put(source, distance);
		}

		private Vertex getCity(String city) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(city)).findFirst().orElse(null);
		}
	}

}

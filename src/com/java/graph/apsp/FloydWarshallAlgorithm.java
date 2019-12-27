package com.java.graph.apsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FloydWarshallAlgorithm {

	public static void main(String[] args) {
		Graph graph = new FloydWarshallAlgorithm.Graph();

		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");

		graph.connect("A", "B", 8);
		graph.connect("B", "C", 1);
		graph.connect("C", "A", 4);
		graph.connect("D", "C", 9);
		graph.connect("A", "D", 1);
		graph.connect("D", "B", 2);

		graph.floydWarshall();
	}

	public static class Graph {

		List<Vertex> vertices = new LinkedList<>();
		int[][] matrices;

		public void add(String name) {
			vertices.add(new Vertex(name));
		}

		public Vertex getVertex(String name) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(name)).findFirst().orElse(null);
		}

		public void connect(String src, String dest, int distance) {
			Vertex source = getVertex(src);
			Vertex destination = getVertex(dest);

			source.neighbors.add(destination);
			source.weights.put(destination, distance);
		}

		public void floydWarshall() {
			int size = vertices.size();
			matrices = new int[size][size];

			for (int i = 0; i < size; i++) {
				Vertex first = vertices.get(i);
				for (int j = 0; j < size; j++) {
					Vertex second = vertices.get(j);
					if (i == j) {
						matrices[i][j] = 0;
					} else if (first.neighbors.contains(second)) {
						matrices[i][j] = first.weights.get(second);
					} else {
						matrices[i][j] = Integer.MAX_VALUE;
					}
				}
			}

			System.out.println("Initial Matrix :: Distance of Vertices");
			print();

			for (int k = 0; k < size; k++) {
				System.out.println("K Iteration " + k);
				for (int i = 0; i < size; i++) {
					System.out.println("I Iteration " + i);
					for (int j = 0; j < size; j++) {
						System.out.println("J Iteration " + j);
						System.out.println("matrices[i][j] - " + matrices[i][j]);
						System.out.print("matrices[i][k] - " + matrices[i][k] + ", matrices[k][j] - " + matrices[k][j]);
						System.out.println("\nSum of [i,k] + [k, j] - " + (matrices[i][k] + matrices[k][j]));
						matrices[i][j] = (int) Math.min((long) matrices[i][j], ((long) matrices[i][k] + (long) matrices[k][j]));
						System.out.println();
					}
					System.out.println();
				}
				System.out.println();
			}

			System.out.println("After Adjustment Matrix :: All Pair Shortest Path of Vertices");
			print();

		}

		private void print() {
			Stream.of(matrices).map(Arrays::toString).forEach(System.out::println);
		}

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
	}
}
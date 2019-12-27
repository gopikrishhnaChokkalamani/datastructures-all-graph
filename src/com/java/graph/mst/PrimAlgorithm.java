package com.java.graph.mst;

import java.util.*;

import com.java.graph.mst.KruskalAlgorithm.DisjointSet;

public class PrimAlgorithm {

	public static void main(String[] args) {
		Graph graph = new PrimAlgorithm.Graph();
		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");
		graph.add("E");
		graph.connect("A", "B", 15);
		graph.connect("A", "C", 20);
		graph.connect("B", "C", 13);
		graph.connect("B", "D", 5);
		graph.connect("C", "D", 10);
		graph.connect("C", "E", 6);
		graph.connect("D", "E", 18);

		graph.prim("A");
	}

	public static class Graph {

		List<Vertex> vertices = new ArrayList<>();

		public void add(String name) {
			vertices.add(new Vertex(name));
		}

		public void prim(String name) {
			Vertex source = getVertex(name);
			source.distance = 0;
			Queue<Vertex> queue = new PriorityQueue<>();
			queue.addAll(vertices);

			while (!queue.isEmpty()) {
				Vertex first = queue.remove();
				for (Vertex neighbor : first.neighbors) {
					if (queue.contains(neighbor)) {
						long dist = (long) first.weights.get(neighbor);
						if (neighbor.distance > dist) {
							neighbor.distance = (int) dist;
							neighbor.parent = first;
							queue.remove(neighbor);
							queue.add(neighbor);
						}
					}
				}
			}

			int cost = 0;
			for (Vertex vertex : vertices) {
				if (source.name == vertex.name) {
					continue;
				}
				cost += vertex.distance;
				System.out.println("Source Vertex " + vertex.parent.name + ", destination " + vertex.name
						+ ", shortest distance " + vertex.distance);
				System.out.println();
			}
			System.out.println("Minimum Spanning Tree Cost - " + cost);

		}

		public void connect(String src, String dest, int distance) {
			Vertex source = getVertex(src);
			Vertex destination = getVertex(dest);
			source.neighbors.add(destination);
			destination.neighbors.add(source);
			source.weights.put(destination, distance);
			destination.weights.put(source, distance);
		}

		private Vertex getVertex(String name) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(name)).findFirst().orElse(null);
		}

		public class Vertex implements Comparable<Vertex> {

			public String name;
			public int index;
			public int distance;
			public List<Vertex> neighbors = new ArrayList<>();
			public Map<Vertex, Integer> weights = new HashMap<>();
			public boolean isVisited;
			public Vertex parent;
			public DisjointSet set;

			public Vertex(String name) {
				this.name = name;
				this.distance = Integer.MAX_VALUE;
			}

			public Vertex(String name, int index) {
				this.name = name;
				this.distance = Integer.MAX_VALUE;
				this.index = index;
			}

			@Override
			public int compareTo(Vertex o) {
				// TODO Auto-generated method stub
				return this.distance - o.distance;
			}
		}
	}

}

package com.java.graph.mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.graph.mst.KruskalAlgorithm.DisjointSet.Edge;
import com.java.graph.mst.KruskalAlgorithm.DisjointSet.Vertex;

public class KruskalAlgorithm {

	public static void main(String[] args) {
		DisjointSet set = new KruskalAlgorithm.DisjointSet();
		set.add("A");
		set.add("B");
		set.add("C");
		set.add("D");
		set.add("E");
		set.connect("A", "B", 15);
		set.connect("A", "C", 20);
		set.connect("B", "C", 13);
		set.connect("B", "D", 5);
		set.connect("C", "D", 10);
		set.connect("C", "E", 6);
		set.connect("D", "E", 18);

		MinimumSpanningTree mst = new KruskalAlgorithm.MinimumSpanningTree();
		mst.kruskal(set);
	}

	public static class MinimumSpanningTree {

		public void kruskal(DisjointSet set) {
			set.makeSet(set);
			set.edges.sort((c1, c2) -> c1.distance - c2.distance);
			int cost = 0;
			for (Edge edge : set.edges) {
				Vertex first = edge.source;
				Vertex second = edge.destination;
				if (!first.set.equals(second.set)) {
					DisjointSet disjointset = set.union(first, second);
					cost += edge.distance;
					System.out.println("Union of " + first.name + " and " + second.name);
					print(disjointset);
					System.out.println();
					System.out.println("Cost of " + first.name + " and " + second.name + " is : " + cost);
				}
			}
			System.out.println("Total Cost of the Minimum Spanning Tree is - " + cost);
		}

		private void print(DisjointSet disjointset) {
			for (Vertex vertex : disjointset.vertices) {
				System.out.print(vertex.name + " ");
			}
		}
	}

	public static class DisjointSet {

		List<Vertex> vertices = new ArrayList<>();
		List<Edge> edges = new ArrayList<>();

		public void add(String name) {
			vertices.add(new Vertex(name));
		}

		public void connect(String src, String dest, int distance) {
			Vertex source = getVertex(src);
			Vertex destination = getVertex(dest);
			source.neighbors.add(destination);
			destination.neighbors.add(source);
			source.weights.put(destination, distance);
			destination.weights.put(source, distance);
			edges.add(new Edge(source, destination, distance));
		}

		public void makeSet(DisjointSet set) {
			for (Vertex vertex : set.vertices) {
				DisjointSet disjointset = new DisjointSet();
				disjointset.vertices.add(vertex);
				vertex.set = disjointset;
			}
		}

		public DisjointSet union(Vertex first, Vertex second) {
			if (first.set.equals(second.set)) {
				return null;
			} else {
				DisjointSet firstSet = first.set;
				DisjointSet secondSet = second.set;
				if (firstSet.vertices.size() > secondSet.vertices.size()) {
					for (Vertex vertex : secondSet.vertices) {
						firstSet.vertices.add(vertex);
						vertex.set = firstSet;
					}
					return firstSet;
				} else {
					for (Vertex vertex : firstSet.vertices) {
						secondSet.vertices.add(vertex);
						vertex.set = secondSet;
					}
					return secondSet;
				}
			}
		}

		private Vertex getVertex(String name) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(name)).findFirst().orElse(null);
		}

		public class Vertex {

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
		}

		public class Edge {
			Vertex source;
			Vertex destination;
			int distance;

			public Edge(Vertex source, Vertex destination, int distance) {
				super();
				this.source = source;
				this.destination = destination;
				this.distance = distance;
			}
		}
	}

}

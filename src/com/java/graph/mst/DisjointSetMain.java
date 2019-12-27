package com.java.graph.mst;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.java.graph.mst.DisjointSetMain.DisjointSet.Vertex;

public class DisjointSetMain {

	public static void main(String[] args) {
		DisjointSet set = new DisjointSetMain.DisjointSet();
		set.add("A");
		set.add("B");
		set.add("C");
		set.add("D");
		set.add("E");
//		set.connect("A", "B");
//		set.connect("A", "C");
//		set.connect("B", "C");
//		set.connect("B", "D");
//		set.connect("C", "D");
//		set.connect("C", "E");
//		set.connect("D", "E");
		MinimumSpanningTree mst = new DisjointSetMain.MinimumSpanningTree();
		mst.findMinimumSpanningTree(set);
	}

	public static class MinimumSpanningTree {

		public void findMinimumSpanningTree(DisjointSet set) {
			makeSet(set);
			for (int i = 0; i < set.vertices.size() - 1; i++) {
				Vertex first = set.vertices.get(i);
				Vertex second = set.vertices.get(i + 1);
				if (!first.set.equals(second.set)) {
					DisjointSet disjointset = union(first, second);
					System.out.println("Union between " + first.name + " and " + second.name);
					print(disjointset);
				}
				System.out.println();
			}
		}

		private void print(DisjointSet disjointset) {
			for (Vertex vertex : disjointset.vertices) {
				System.out.print(vertex.name + " ");
			}
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
						vertex.set = firstSet;
						firstSet.vertices.add(vertex);
					}
					return firstSet;
				} else {
					for (Vertex vertex : firstSet.vertices) {
						vertex.set = secondSet;
						secondSet.vertices.add(vertex);
					}
					return secondSet;
				}
			}
		}
	}

	public static class DisjointSet {

		List<Vertex> vertices = new ArrayList<>();

		public void add(String name) {
			vertices.add(new Vertex(name));
		}

//		public void connect(String src, String dest) {
//			Vertex source = getVertex(src);
//			Vertex destination = getVertex(dest);
//			source.neighbors.add(destination);
//			destination.neighbors.add(source);
//			// source.weights.put(destination, distance);
//			// destination.weights.put(source, distance);
//		}

//		private Vertex getVertex(String name) {
//			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(name)).findFirst().orElse(null);
//		}

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
	}

}

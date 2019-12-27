package com.java.graph.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.java.graph.sort.TopologicalSort.Graph.Vertex;

public class TopologicalSort {

	public static void main(String[] args) {
		Graph graph = new TopologicalSort.Graph();
		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");
		graph.add("E");
		graph.add("F");
		graph.add("G");
		graph.add("H");

		graph.connect("A", "C");
		graph.connect("B", "C");
		graph.connect("B", "D");
		graph.connect("C", "E");
		graph.connect("E", "H");
		graph.connect("E", "F");
		graph.connect("F", "G");
		graph.connect("D", "F");

		topologicalSort(graph);

	}

	public static void topologicalSort(Graph graph) {
		List<Vertex> vertices = graph.vertices;
		Stack<Vertex> stack = new Stack<>();
		for (Vertex vertex : vertices) {
			if (!vertex.isVisited) {
				topologicalSort(vertex, stack);
			}
		}
		while (!stack.isEmpty()) {
			Vertex last = stack.pop();
			System.out.print(last.name + " ");
		}
	}

	private static void topologicalSort(Vertex vertex, Stack<Vertex> stack) {
		for (Vertex neighbor : vertex.neighbors) {
			if (!neighbor.isVisited) {
				topologicalSort(neighbor, stack);
			}
		}
		vertex.isVisited = true;
		stack.push(vertex);
	}

	public static class Graph {

		List<Vertex> vertices = new ArrayList<>();

		public void add(String name) {
			vertices.add(new Vertex(name));
		}

		public void connect(String src, String dest) {
			Vertex source = getVertex(src);
			Vertex destination = getVertex(dest);

			source.neighbors.add(destination);
		}

		public Vertex getVertex(String name) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(name)).findFirst().orElse(null);
		}

		public static class Vertex {
			String name;
			int index;
			List<Vertex> neighbors = new ArrayList<>();
			boolean isVisited;

			public Vertex(String name) {
				this.name = name;
			}
		}
	}
}
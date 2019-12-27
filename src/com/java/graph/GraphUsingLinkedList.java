package com.java.graph;

import java.util.*;

public class GraphUsingLinkedList {

	public static void main(String[] args) {
		Graph graph = new GraphUsingLinkedList.Graph();
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

		graph.connect("chennai", "vellore");
		graph.connect("chennai", "salem");
		graph.connect("vellore", "salem");
		graph.connect("salem", "coimbatore");
		graph.connect("chennai", "tiruchy");
		graph.connect("tiruchy", "dindugal");
		graph.connect("dindugal", "madurai");
		graph.connect("dindugal", "kodaikanal");
		graph.connect("madurai", "tirunelveli");
		graph.connect("tirunelveli", "nagercoil");
		graph.connect("coimbatore", "ooty");

		// graph.bfs();
		// chennai vellore salem tiruchy coimbatore dindugal ooty madurai kodaikanal
		// tirunelveli nagercoil

		graph.dfs();
		// chennai tiruchy dindugal kodaikanal madurai tirunelveli nagercoil salem
		// coimbatore ooty vellore

	}

	public static class Graph {

		List<Vertex> vertices = new LinkedList<>();

		public void add(String city) {
			vertices.add(new Vertex(city));
		}

		public void dfs() {
			for (Vertex vertex : vertices) {
				if (!vertex.isVisited) {
					dfs(vertex);
				}
			}
			System.out.println();
		}

		private void dfs(Vertex vertex) {
			Stack<Vertex> stack = new Stack<>();
			stack.push(vertex);
			while (!stack.isEmpty()) {
				Vertex lastVertex = stack.pop();
				System.out.print(lastVertex.name + " ");
				lastVertex.isVisited = true;
				for (Vertex neighbor : lastVertex.neighbors) {
					if (!neighbor.isVisited) {
						neighbor.isVisited = true;
						stack.push(neighbor);
					}
				}
			}
		}

		public void bfs() {
			for (Vertex vertex : vertices) {
				if (!vertex.isVisited) {
					bfs(vertex);
				}
			}
			System.out.println();
		}

		private void bfs(Vertex vertex) {
			Queue<Vertex> queue = new ArrayDeque<>();
			queue.add(vertex);
			while (!queue.isEmpty()) {
				Vertex firstVertex = queue.remove();
				System.out.print(firstVertex.name + " ");
				firstVertex.isVisited = true;
				for (Vertex neighbor : firstVertex.neighbors) {
					if (!neighbor.isVisited) {
						neighbor.isVisited = true;
						queue.add(neighbor);
					}
				}
			}
		}

		public void connect(String src, String dest) {
			Vertex source = getCity(src);
			Vertex destination = getCity(dest);

			source.neighbors.add(destination);
			destination.neighbors.add(source);
		}

		private Vertex getCity(String city) {
			return vertices.stream().filter(c -> c.name.equalsIgnoreCase(city)).findFirst().orElse(null);
		}
	}

}

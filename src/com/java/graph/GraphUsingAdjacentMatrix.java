package com.java.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.stream.Stream;

public class GraphUsingAdjacentMatrix {

	public static void main(String[] args) {

		Graph graph = new GraphUsingAdjacentMatrix.Graph(5);
		graph.add("A");
		graph.add("B");
		graph.add("C");
		graph.add("D");
		graph.add("E");
		graph.print();
		graph.connect(0, 1);
		graph.connect(0, 4);
		graph.connect(1, 2);
		graph.connect(1, 4);
		graph.connect(2, 3);
		graph.connect(4, 3);
		// graph.connect(3, 0);
		System.out.println();
		graph.print();
		System.out.println("Breadth First Search");
		// graph.bfs();
		System.out.println("Depth First Search");
		graph.dfs();
	}

	public static class Graph {
		int[][] adjacencyMatrix;
		List<Vertex> vertices;
		int index = -1;

		public Graph(int size) {
			this.adjacencyMatrix = new int[size][size];
			this.vertices = new ArrayList<>();
			this.index = 0;
		}

		public void add(String name) {
			vertices.add(new Vertex(name, 0));
		}

		public void connect(int a, int b) {
			adjacencyMatrix[a][b] = 1;
			adjacencyMatrix[b][a] = 1;
		}

		public void print() {
			Stream.of(adjacencyMatrix).flatMap(Stream::of).map(Arrays::toString).forEach(System.out::println);
			// System.out.println(Arrays.deepToString(adjacencyMatrix));
			// Stream.of(adjacencyMatrix).map(Arrays::toString).forEach(System.out::println);
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
				for (Vertex neighbor : getNeighbors(firstVertex)) {
					if (!neighbor.isVisited) {
						neighbor.isVisited = true;
						queue.add(neighbor);
					}
				}
			}
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
				for (Vertex neighbor : getNeighbors(lastVertex)) {
					if (!neighbor.isVisited) {
						neighbor.isVisited = true;
						stack.push(neighbor);
					}
				}
			}
		}

		private List<Vertex> getNeighbors(Vertex vertex) {
			List<Vertex> list = new ArrayList<Vertex>();
			int matrixIndex = vertex.index;
			for (int i = 0; i < adjacencyMatrix.length; i++) {
				if (adjacencyMatrix[matrixIndex][i] == 1)
					list.add(vertices.get(i));
			}
			return list;
		}
	}
}
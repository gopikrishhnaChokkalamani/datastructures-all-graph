package com.java.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
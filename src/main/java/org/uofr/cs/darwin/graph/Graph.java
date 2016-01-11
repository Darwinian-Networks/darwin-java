package org.uofr.cs.darwin.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private List<Node> nodes;
	private List<Edge> edges;
	
	public Graph() {
		this.nodes = new ArrayList<>();
		this.edges = new ArrayList<>();
	}
	
	// Nodes Methods
	
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	public void addNodes(Node ...nodes) {
		for (Node node : nodes) {
			this.addNode(node);
		}
	}
	
	public List<Node> getNodes() {
		return nodes;
	}
	
	public Node getNodeWithName(String name) {
		Node nodeWithName = null;
		for (Node node : this.nodes) {
			if (node.getName().equals(name)) {
				nodeWithName = node;
				break;
			}
		}
		return nodeWithName;
	}
	
	public List<Node> getAdjacent(Node node) {
		List<Node> adjacentNodes = new ArrayList<>();
		
		for (Edge edge : this.edges) {
			if (edge.getFirstNode() == node) {
				adjacentNodes.add(edge.getSecondNode());
			} else if (edge.getSecondNode() == node) {
				adjacentNodes.add(edge.getFirstNode());
			}
		}
		
		return adjacentNodes;
	}
	
	// Edges Methods
	
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}
	
	public void addEdges(Edge ...edges) {
		for (Edge edge : edges) {
			this.addEdge(edge);
		}
	}

	public List<Edge> getEdges() {
		return edges;
	}
	
}

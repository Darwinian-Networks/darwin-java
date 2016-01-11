package org.uofr.cs.darwin.graph;

public class Edge {

	private Node firstNode;
	private Node secondNode;
	
	public Edge(Node first, Node second) {
		this.firstNode = first;
		this.secondNode = second;
	}

	public Node getFirstNode() {
		return firstNode;
	}

	public void setFirstNode(Node firstNode) {
		this.firstNode = firstNode;
	}

	public Node getSecondNode() {
		return secondNode;
	}

	public void setSecondNode(Node secondNode) {
		this.secondNode = secondNode;
	}
	
	public boolean contains(Node node) {
		return ((this.firstNode == node) || (this.secondNode == node));
	}
	
	@Override
	public String toString() {
		return "(" + this.firstNode + "," + this.secondNode + ")";
	}
}

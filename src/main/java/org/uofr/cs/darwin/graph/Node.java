package org.uofr.cs.darwin.graph;

public class Node {

	private String name;
	// Optional field for saving unique ids
	private int id;
	
	public Node(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}

package org.uofr.cs.darwin.graph;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DirectedAcyclicGraphTest {
	
	public DirectedAcyclicGraph getDag() {
		DirectedAcyclicGraph dag = new DirectedAcyclicGraph();
		
		Node nodeA = new Node("A");
		Node nodeB = new Node("B");
		Node nodeC = new Node("C");
		Node nodeD = new Node("D");
		Node nodeE = new Node("E");
		Node nodeF = new Node("F");
		
		dag.addNodes(nodeA,nodeB,nodeC,nodeD,nodeE,nodeF);
		
		Edge edgeAB = new Edge(nodeA, nodeB);
		Edge edgeAD = new Edge(nodeA, nodeD);
		Edge edgeBC = new Edge(nodeB, nodeC);
		Edge edgeCD = new Edge(nodeC, nodeD);
		Edge edgeDE = new Edge(nodeD, nodeE);
		Edge edgeCE = new Edge(nodeC, nodeE);
		
		dag.addEdges(edgeAB,edgeAD,edgeBC,edgeCD,edgeDE,edgeCE);
		
		return dag;
	}

	@Test
	public void getInDegree() {
		
		DirectedAcyclicGraph dag = this.getDag();
		
		Map<Node,Integer> correctInDegree = new HashMap<>();
		correctInDegree.put(dag.getNodeWithName("A"), 0);
		correctInDegree.put(dag.getNodeWithName("B"), 1);
		correctInDegree.put(dag.getNodeWithName("C"), 1);
		correctInDegree.put(dag.getNodeWithName("D"), 2);
		correctInDegree.put(dag.getNodeWithName("E"), 2);
		correctInDegree.put(dag.getNodeWithName("F"), 0);
		
		assertEquals(correctInDegree, dag.getInDegree());
	}
	
	@Test
	public void getOrdering() {
		DirectedAcyclicGraph dag = this.getDag();
		System.out.println(dag.getOrdering());
	}
}

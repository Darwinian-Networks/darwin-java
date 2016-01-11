package org.uofr.cs.darwin.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class DirectedAcyclicGraph extends Graph {
	
	public Map<Node,Integer> getInDegree() {
		Map<Node,Integer> inDegree = new HashMap<>();
		
		// Add all nodes and set their in-degree to zero.
		for (Node node : this.getNodes()) {
			inDegree.put(node, 0);
		}
		
		// Compute the in-degree of nodes based on edges
		for (Edge edge : this.getEdges()) {
			// increment in-degree of second node (where the direction is pointing to)
			inDegree.put(edge.getSecondNode(), inDegree.get(edge.getSecondNode()) + 1);
		}
		
		return inDegree;
	}
	
	public List<Node> getOrdering() {
		List<Node> ordering = new ArrayList<>();
		
		Map<Node,Integer> inDegree = this.getInDegree();
		
		while (inDegree.size() > 0) {

			Map<Node, Integer> inDegreeZero = 
					inDegree.entrySet()
					.stream()
					.filter(p -> p.getValue().equals(0))
					.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
				
				for (Entry<Node, Integer> entry : inDegreeZero.entrySet()) {
					Node nodeWithZeroInDegree = entry.getKey();
					ordering.add(nodeWithZeroInDegree);
					// Remove from the in-degree list to get the next one
					inDegree.remove(nodeWithZeroInDegree);
					
					List<Node> adjacentNodes = this.getAdjacent(nodeWithZeroInDegree);
					
					for (Node adjacentNode : adjacentNodes) {
						if (inDegree.containsKey(adjacentNode)){
							inDegree.put(adjacentNode, inDegree.get(adjacentNode) - 1);
						}
					}
				}
		}
		
		return ordering;
	}

}

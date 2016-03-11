package org.uofr.cs.darwin.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uofr.cs.darwin.graph.Edge;
import org.uofr.cs.darwin.graph.Node;

public class AcToGml {

	public static void parse(String address) throws IOException {
		
		// Variables for building the graph
		int numberOfNodes;
		int numberOfEdges;
		
		Map<Integer, Node> nodes = new HashMap<>();
		List<Edge> edges = new ArrayList<>();
		
		// Link the file to read
		BufferedReader bufferedReader = new BufferedReader(new FileReader(address));
		
		
		// *** Reading the graph structure from the AC file ***
		
		// Line by line
		int lineIndexCounter = 0;
		String line;
		while ((line = bufferedReader.readLine()) != null) {
			line = line.trim(); // trim the string
			
			String[] tokens = line.split(" ");
			
			// Decide which type of line this one is
			if (tokens[0].equals("nnf")) {
				numberOfNodes = Integer.parseInt(tokens[1]);
				numberOfEdges = Integer.parseInt(tokens[2]);
			} else if (tokens[0].equals("l")) {
				Node leafNode = new Node(tokens[1]);
				leafNode.setId(lineIndexCounter);
				nodes.put(lineIndexCounter, leafNode);
				System.out.println("New node: " + lineIndexCounter);  // ### DEBUG
				lineIndexCounter++;
			} else if (tokens[0].equals("*") || tokens[0].equals("+")) {
				Node sumOrProductNode = new Node(tokens[0]);
				sumOrProductNode.setId(lineIndexCounter);
				// add nodes
				nodes.put(lineIndexCounter, sumOrProductNode);
				System.out.println("New node: " + lineIndexCounter); // ### DEBUG
				// add edges
				int numberOfChildren = Integer.parseInt(tokens[1]);
				for (int i=1; i <= numberOfChildren; i++) {
					edges.add(new Edge(sumOrProductNode, nodes.get( Integer.parseInt(tokens[1+i]))));
					System.out.println("New edge: ( "+ lineIndexCounter + " , " + tokens[1+i] + ")"); // ### DEBUG
				}
				// update the index line counter
				lineIndexCounter++;
			}
			
		}
		
		// Close if it was opened
		if(bufferedReader != null) bufferedReader.close();
		
	}
}

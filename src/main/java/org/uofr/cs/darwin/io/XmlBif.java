package org.uofr.cs.darwin.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.uofr.cs.darwin.common.BayesianNetwork;
import org.uofr.cs.darwin.common.ConditionalProbabilityTable;
import org.uofr.cs.darwin.common.Variable;

public class XmlBif {
	
	private Document document;
	
	public XmlBif(InputStream xmlFile) throws ParserConfigurationException, SAXException, IOException {
		
		//Get the DOM Builder Factory
	    DocumentBuilderFactory factory = 
	        DocumentBuilderFactory.newInstance();

	    //Get the DOM Builder
	    DocumentBuilder builder = factory.newDocumentBuilder();

	    //Load and Parse the XML document
	    //document contains the complete XML as a Tree.
	    this.document = builder.parse(xmlFile);
	}
	
	public BayesianNetwork getBn() {
		Map<String, Variable> bnVariables = this.getVariables();
		Map<String, ConditionalProbabilityTable> bnCpts = this.getCpts(bnVariables);
		BayesianNetwork bn = new BayesianNetwork(bnVariables, bnCpts);
		return bn;
	}
	
	private Map<String, Variable> getVariables() {
		
		Map<String, Variable> variables = new HashMap<String, Variable>();

	    // Load Variables
	    NodeList variablesList = document.getElementsByTagName("VARIABLE");
	    
	    for (int i = 0; i < variablesList.getLength(); i++) {
	    	Variable variable = new Variable();
	    	Node varNode = variablesList.item(i);
	    	
	    	// Load each information from a Variable (Name, Type, and Values)
	    	NodeList infoVarList = varNode.getChildNodes();
	    	for (int j = 0; j < infoVarList.getLength(); j++) {
	    		Node infoNode = infoVarList.item(j);
	    		String content = infoNode.getTextContent();
	    		switch (infoNode.getNodeName()) {
				case "NAME":
					variable.setName(content);
					break;
				case "TYPE":
					variable.setType(content);
					break;
				case "VALUE":
					variable.addValue(content);
					break;
				case "OUTCOME":  // Some BIF files writes "OUTCOME" instead of "VALUE"
					variable.addValue(content);
					break;
				default:
					break;
				}
	    	}
	    	// Save new Variable
	    	variables.put(variable.getName(), variable);
	    }
	    
	    return variables;
	}
	
	
	private Map<String, ConditionalProbabilityTable> getCpts(Map<String, Variable> variables) {
		

		Map<String, ConditionalProbabilityTable> cpts = new HashMap<>();
		
		// Save new CPT
		NodeList cptsList = document.getElementsByTagName("PROBABILITY");
		if (cptsList.getLength() < 1) {  // Some XML calls this tag "DEFINITION" instead of "PROBABILITY"
			cptsList = document.getElementsByTagName("DEFINITION");
		}
	    
	    for (int k = 0; k < cptsList.getLength(); k++) {
	    	ConditionalProbabilityTable cpt = new ConditionalProbabilityTable();
	    	Node cptNode = cptsList.item(k);
	    	
	    	// Load each information from a Variable (Name, Type, and Values)
	    	NodeList infoCptList = cptNode.getChildNodes();
	    	for (int l = 0; l < infoCptList.getLength(); l++) {
	    		Node infoNode = infoCptList.item(l);
	    		String content = infoNode.getTextContent();
	    		switch (infoNode.getNodeName()) {
				case "FOR":
					cpt.addToLeft(variables.get(content));
					break;
				case "GIVEN":
					cpt.addToRight(variables.get(content));
					break;
				case "TABLE":
					String[] strValues = content.split(" ");
					for (String strValue : strValues) {
						Double newValue = Double.valueOf(strValue);
						cpt.addValue(newValue);
					}
					break;
				default:
					break;
				}
	    	}
	    	// Save new CPT
	    	cpts.put(cpt.getLeft().get(0).getName(), cpt);
	    }
	    return cpts;
	}
}

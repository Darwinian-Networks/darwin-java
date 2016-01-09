package org.uofr.cs.darwin.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.uofr.cs.darwin.factors.ConditionalProbabilityTable;

public class BayesianNetwork {

	private Map<String, Variable> variables;
	private Map<String, ConditionalProbabilityTable> cpts;

	public BayesianNetwork(Map<String, Variable> variables, Map<String, ConditionalProbabilityTable> cpts) {
		this.variables = variables;
		this.cpts = cpts;
	}
	
	public ConditionalProbabilityTable getCpt(String variable) {
		return this.cpts.get(variable);
	}
	
	public Variable getVariable(String variable) {
		return this.variables.get(variable);
	}
	
	public List<Variable> getAllVariables() {
		return new ArrayList<>(this.variables.values());
	}
	
	public List<ConditionalProbabilityTable> getAllCpts() {
		return new ArrayList<>(this.cpts.values());
	}

}

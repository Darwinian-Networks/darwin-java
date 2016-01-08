package org.uofr.cs.darwin.common;

import java.util.ArrayList;
import java.util.List;

public class ConditionalProbabilityTable {

	private List<Variable> variables;
	private List<Variable> left;
	private List<Variable> right;
	private List<Double> values;
	
	public ConditionalProbabilityTable() {
		this.variables = new ArrayList<>();
		this.left = new ArrayList<>();
		this.right = new ArrayList<>();
		this.values = new ArrayList<>();
	}

	public List<Variable> getLeft() {
		return left;
	}

	public List<Variable> getRight() {
		return right;
	}

	public List<Double> getValues() {
		return values;
	}
	
	public void setValues(List<Double> values) {
		this.values = values;
	}

	public void addToLeft(Variable newVar) {
		this.variables.add(newVar);
		this.left.add(newVar);
	}
	
	public void addAllToLeft(List<Variable> newVars) {
		for (Variable v : newVars) {
			this.addToLeft(v);
		}
	}

	public void addToRight(Variable newVar) {
		this.variables.add(newVar);
		this.right.add(newVar);
	}
	
	public void addAllToRight(List<Variable> newVars) {
		for (Variable v : newVars) {
			this.addToRight(v);
		}
	}

	public void addValue(Double newValue) {
		this.values.add(newValue);
	}
	
	public int getNumberOfVariables() {
		return this.left.size()+this.right.size();
	}
	
	public List<Variable> getVariables() {
		return this.variables;
	}
	
	public int getNumberOfRows() {
		int rows = 1;
		for (Variable v : this.getVariables()) {
			rows *= v.getCardinality();
		}
		return rows;
	}
	
	public Double getValue(int index) {
		return this.values.get(index);
	}
	
	public Variable getVariable (int index) {
		return this.getVariables().get(index);
	}
	
	public int getStride(Variable variable) {
		List<Variable> variables = this.getVariables();
		
		int stride = 0;
		
		if (variables.contains(variable)) {
		
			stride = 1;
			
			int index = 0;
			for (Variable v : this.getVariables()) {
				if (variable.getName().equals(v.getName())) {
					break;
				} else {
					stride *= this.getVariable(index).getCardinality();
				}
				index++;
			}
			
		}
		
		return stride;
	}
	
	@Override
	public String toString() {
		String cptStr = "P(";
		for (Variable v : this.left) {
			cptStr += v.getName() + " ";
		}
		cptStr += "| ";
		for (Variable v : this.right) {
			cptStr += v.getName() + " ";
		}
		cptStr += ")";
		return cptStr;
	}
	
	public boolean contains(Variable variable) {
		return this.variables.contains(variable);
	}
}

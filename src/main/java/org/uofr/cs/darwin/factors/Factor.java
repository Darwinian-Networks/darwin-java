package org.uofr.cs.darwin.factors;

import java.util.ArrayList;
import java.util.List;

import org.uofr.cs.darwin.common.Variable;

public abstract class Factor {

	private List<Double> values;
	private List<Variable> variables;
	
	public Factor() {
		this.values = new ArrayList<>();
		this.variables = new ArrayList<>();
	}
	
	public List<Double> getValues() {
		return values;
	}
	
	public void setValues(List<Double> values) {
		this.values = values;
	}
	
	public void addValue(Double newValue) {
		this.values.add(newValue);
	}
	
	public Double getValue(int index) {
		return this.values.get(index);
	}
	
	public List<Variable> getVariables() {
		return this.variables;
	}
	
	public int getNumberOfVariables() {
		return this.variables.size();
	}
	
	public Variable getVariable (int index) {
		return this.getVariables().get(index);
	}
	
	public boolean contains(Variable variable) {
		return this.variables.contains(variable);
	}
	
	public void addVariable(Variable variable) {
		this.variables.add(variable);
	}
	
	public int getNumberOfRows() {
		int rows = 1;
		for (Variable v : this.getVariables()) {
			rows *= v.getCardinality();
		}
		return rows;
	}
	
	@Override
	public String toString() {
		String cptStr = "P(";
		for (Variable v : this.getVariables()) {
			cptStr += v.getName() + " ";
		}
		cptStr += ")";
		return cptStr;
	}
	
	public abstract Factor multiply(Factor multiply);
	public abstract Factor marginalize(Variable variable);

}

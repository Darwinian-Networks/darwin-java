package org.uofr.cs.darwin.factors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uofr.cs.darwin.common.Variable;

public class ConditionalProbabilityTable extends Factor {

	private List<Variable> left;
	private List<Variable> right;
	
	public ConditionalProbabilityTable() {
		this.left = new ArrayList<>();
		this.right = new ArrayList<>();
	}

	public List<Variable> getLeft() {
		return left;
	}

	public List<Variable> getRight() {
		return right;
	}

	public void addToLeft(Variable newVar) {
		this.addVariable(newVar);
		this.left.add(newVar);
	}
	
	public void addAllToLeft(List<Variable> newVars) {
		for (Variable v : newVars) {
			this.addToLeft(v);
		}
	}

	public void addToRight(Variable newVar) {
		this.addVariable(newVar);
		this.right.add(newVar);
	}
	
	public void addAllToRight(List<Variable> newVars) {
		for (Variable v : newVars) {
			this.addToRight(v);
		}
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

	@Override
	public ConditionalProbabilityTable multiply(Factor otherCpt) {

		ConditionalProbabilityTable cpt2 = (ConditionalProbabilityTable) otherCpt;
		
		ConditionalProbabilityTable cpt3 = new ConditionalProbabilityTable();
		
		// Determine the scope of the new CPT by taking the union of both factors.
		List<Variable> cpt3LeftVariables = new ArrayList<>();
		cpt3LeftVariables.addAll(this.getLeft());
		cpt3LeftVariables.removeAll(cpt2.getLeft());
		cpt3LeftVariables.addAll(cpt2.getLeft());
		cpt3.addAllToLeft(cpt3LeftVariables);
		
		List<Variable> cpt3RightVariables = new ArrayList<>();
		cpt3RightVariables.addAll(this.getRight());
		cpt3RightVariables.removeAll(cpt2.getRight());
		cpt3RightVariables.addAll(cpt2.getRight());
		cpt3RightVariables.removeAll(cpt3LeftVariables); // No variable in the Left can be in the Right
		cpt3.addAllToRight(cpt3RightVariables);
		
		// Multiplication loop
		int j = 0;
		int k = 0;
		int[] assignments = new int[cpt3.getNumberOfVariables()];
		
		for (int i = 0; i < cpt3.getNumberOfRows(); i++) {
			cpt3.addValue(this.getValue(j) * cpt2.getValue(k));
			
			for (int l = 0; l < cpt3.getNumberOfVariables(); l++) {
				assignments[l] = assignments[l] + 1;
				int lVarCard = cpt3.getVariable(l).getCardinality();
				if (assignments[l] == lVarCard) {
					assignments[l] = 0;
					j = j - (lVarCard - 1) * this.getStride(cpt3.getVariable(l));
					k = k - (lVarCard - 1) * cpt2.getStride(cpt3.getVariable(l));
				} else {
					j = j + this.getStride(cpt3.getVariable(l));
					k = k + cpt2.getStride(cpt3.getVariable(l));
					break;
				}
			}
		}
		
		
		return cpt3;
	}

	@Override
	public ConditionalProbabilityTable marginalize(Variable variable) {

		ConditionalProbabilityTable marginalCpt = new ConditionalProbabilityTable();
		
		// Determine the scope of the new CPT.
		List<Variable> marginalCptLeftVariables = new ArrayList<>();
		marginalCptLeftVariables.addAll( this.getLeft());
		marginalCptLeftVariables.remove(variable);
		marginalCpt.addAllToLeft(marginalCptLeftVariables);
		
		List<Variable> marginalCptRightVariables = new ArrayList<>();
		marginalCptRightVariables.addAll(this.getRight());
		marginalCptRightVariables.remove(variable);
		marginalCpt.addAllToRight(marginalCptRightVariables);
		
		// Summation loop
		int j = 0;
		int[] assignments = new int[this.getNumberOfVariables()];
		int numberOfRowsInMarginal = marginalCpt.getNumberOfRows();
		List<Double> marginalValues = new ArrayList<>(Collections.nCopies(numberOfRowsInMarginal, 0.0));
		
		for (int i = 0; i < this.getNumberOfRows(); i++) {
			marginalValues.set(j, marginalValues.get(j) + this.getValue(i));
			
			for (int l = 0; l < this.getNumberOfVariables(); l++) {
				assignments[l] = assignments[l] + 1;
				int lVarCard = this.getVariable(l).getCardinality();
				if (assignments[l] == lVarCard) {
					assignments[l] = 0;
					j = j - (lVarCard - 1) * marginalCpt.getStride(this.getVariable(l));
				} else {
					j = j + marginalCpt.getStride(this.getVariable(l));
					break;
				}
			}
		}
		
		marginalCpt.setValues(marginalValues);
		
		
		return marginalCpt;
	}

}

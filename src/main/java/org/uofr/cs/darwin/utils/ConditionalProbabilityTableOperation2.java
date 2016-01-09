package org.uofr.cs.darwin.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.uofr.cs.darwin.common.Variable;
import org.uofr.cs.darwin.factors.ConditionalProbabilityTable;

public class ConditionalProbabilityTableOperation2 {

	public static ConditionalProbabilityTable multiply(ConditionalProbabilityTable cpt1, ConditionalProbabilityTable cpt2) {
		ConditionalProbabilityTable cpt3 = new ConditionalProbabilityTable();
		
		// Determine the scope of the new CPT by taking the union of both factors.
		List<Variable> cpt3LeftVariables = new ArrayList<>();
		cpt3LeftVariables.addAll(cpt1.getLeft());
		cpt3LeftVariables.removeAll(cpt2.getLeft());
		cpt3LeftVariables.addAll(cpt2.getLeft());
		cpt3.addAllToLeft(cpt3LeftVariables);
		
		List<Variable> cpt3RightVariables = new ArrayList<>();
		cpt3RightVariables.addAll(cpt1.getRight());
		cpt3RightVariables.removeAll(cpt2.getRight());
		cpt3RightVariables.addAll(cpt2.getRight());
		cpt3RightVariables.removeAll(cpt3LeftVariables); // No variable in the Left can be in the Right
		cpt3.addAllToRight(cpt3RightVariables);
		
		// Multiplication loop
		int j = 0;
		int k = 0;
		int[] assignments = new int[cpt3.getNumberOfVariables()];
		
		for (int i = 0; i < cpt3.getNumberOfRows(); i++) {
			cpt3.addValue(cpt1.getValue(j) * cpt2.getValue(k));
			
			for (int l = 0; l < cpt3.getNumberOfVariables(); l++) {
				assignments[l] = assignments[l] + 1;
				int lVarCard = cpt3.getVariable(l).getCardinality();
				if (assignments[l] == lVarCard) {
					assignments[l] = 0;
					j = j - (lVarCard - 1) * cpt1.getStride(cpt3.getVariable(l));
					k = k - (lVarCard - 1) * cpt2.getStride(cpt3.getVariable(l));
				} else {
					j = j + cpt1.getStride(cpt3.getVariable(l));
					k = k + cpt2.getStride(cpt3.getVariable(l));
					break;
				}
			}
		}
		
		
		return cpt3;
	}
	
	
	public static ConditionalProbabilityTable marginalize(ConditionalProbabilityTable cpt, Variable variable) {
		ConditionalProbabilityTable marginalCpt = new ConditionalProbabilityTable();
		
		// Determine the scope of the new CPT.
		List<Variable> marginalCptLeftVariables = new ArrayList<>();
		marginalCptLeftVariables.addAll( cpt.getLeft());
		marginalCptLeftVariables.remove(variable);
		marginalCpt.addAllToLeft(marginalCptLeftVariables);
		
		List<Variable> marginalCptRightVariables = new ArrayList<>();
		marginalCptRightVariables.addAll(cpt.getRight());
		marginalCptRightVariables.remove(variable);
		marginalCpt.addAllToRight(marginalCptRightVariables);
		
		// Summation loop
		int j = 0;
		int[] assignments = new int[cpt.getNumberOfVariables()];
		int numberOfRowsInMarginal = marginalCpt.getNumberOfRows();
		List<Double> marginalValues = new ArrayList<>(Collections.nCopies(numberOfRowsInMarginal, 0.0));
		
		for (int i = 0; i < cpt.getNumberOfRows(); i++) {
			marginalValues.set(j, marginalValues.get(j) + cpt.getValue(i));
			
			for (int l = 0; l < cpt.getNumberOfVariables(); l++) {
				assignments[l] = assignments[l] + 1;
				int lVarCard = cpt.getVariable(l).getCardinality();
				if (assignments[l] == lVarCard) {
					assignments[l] = 0;
					j = j - (lVarCard - 1) * marginalCpt.getStride(cpt.getVariable(l));
				} else {
					j = j + marginalCpt.getStride(cpt.getVariable(l));
					break;
				}
			}
		}
		
		marginalCpt.setValues(marginalValues);
		
		
		return marginalCpt;
	}
}

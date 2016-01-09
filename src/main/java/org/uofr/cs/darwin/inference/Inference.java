package org.uofr.cs.darwin.inference;

import java.util.ArrayList;
import java.util.List;

import org.uofr.cs.darwin.common.Variable;
import org.uofr.cs.darwin.factors.ConditionalProbabilityTable;

public class Inference {

	public static List<ConditionalProbabilityTable> sumOut(List<ConditionalProbabilityTable> factorization, List<Variable> varsToSumOut) {
		List<ConditionalProbabilityTable> newFactorization = new ArrayList<>(factorization);
		
		for (Variable varToSumOut : varsToSumOut) {
			// Multiply all together
			List<ConditionalProbabilityTable> relevantCpts = Inference.getAllCptsContaining(newFactorization, varToSumOut);
			ConditionalProbabilityTable product = Inference.multiplyAll(relevantCpts);
			
			// Marginalize out the variable
			ConditionalProbabilityTable marginal = product.marginalize(varToSumOut);
			newFactorization.removeAll(relevantCpts);
			newFactorization.add(marginal);
		}
		
		return newFactorization;
	}
	
	public static List<ConditionalProbabilityTable> getAllCptsContaining(List<ConditionalProbabilityTable> factorization, Variable variable) {
		List<ConditionalProbabilityTable> relevantCpts = new ArrayList<>();
			for (ConditionalProbabilityTable cpt : factorization) {
				if (cpt.contains(variable)) {
					relevantCpts.add(cpt);
				}
			}
		return relevantCpts;
	}

	public static ConditionalProbabilityTable multiplyAll(List<ConditionalProbabilityTable> cpts) {
		ConditionalProbabilityTable product = null;
		if (cpts.size() > 0) {
			product = cpts.get(0);
			for (int i = 1; i < cpts.size(); i++) {
				product = product.multiply(cpts.get(i));
			}
		}
		return product;
	}
}

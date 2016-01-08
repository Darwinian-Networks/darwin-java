package org.uofr.cs.darwin.inference;

import java.util.ArrayList;
import java.util.List;

import org.uofr.cs.darwin.common.ConditionalProbabilityTable;
import org.uofr.cs.darwin.common.Variable;
import org.uofr.cs.darwin.utils.ConditionalProbabilityTableOperation;

public class Inference {

	public static List<ConditionalProbabilityTable> sumOut(List<ConditionalProbabilityTable> factorization, List<Variable> varsToSumOut) {
		List<ConditionalProbabilityTable> newFactorization = new ArrayList<>(factorization);

		// ### DEBUG
		int i = 0;
		try {
			System.out.println("Primeira parada");
			Thread.sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ### --- DEBUG
		
		for (Variable varToSumOut : varsToSumOut) {
			// Multiply all together
			List<ConditionalProbabilityTable> relevantCpts = Inference.getAllCptsContaining(newFactorization, varToSumOut);
			ConditionalProbabilityTable product = Inference.multiplyAll(relevantCpts);
			
			// Marginalize out the variable
			ConditionalProbabilityTable marginal = ConditionalProbabilityTableOperation.marginalize(product, varToSumOut);
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
	
	//TODO instead of ConditionalProbabilityTable use a parent class Factor (Inference should be generic: for CPTs or ADDs)
	public static ConditionalProbabilityTable multiplyAll(List<ConditionalProbabilityTable> cpts) {
		ConditionalProbabilityTable product = null;
		if (cpts.size() > 0) {
			product = cpts.get(0);
			for (int i = 1; i < cpts.size(); i++) {
				product = ConditionalProbabilityTableOperation.multiply(product, cpts.get(i));
			}
		}
		return product;
	}
}

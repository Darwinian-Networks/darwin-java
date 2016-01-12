package org.uofr.cs.darwin.factors.add;

import org.uofr.cs.darwin.common.Variable;
import org.uofr.cs.darwin.factors.Factor;
import org.uofr.cs.darwin.graph.DirectedAcyclicGraph;

public class AlgebraicDecisionDiagram extends Factor {
	
	private DirectedAcyclicGraph dag;
	
	public AlgebraicDecisionDiagram() {
		this.dag = new DirectedAcyclicGraph();
	}

	@Override
	public Factor multiply(Factor multiply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Factor marginalize(Variable variable) {
		// TODO Auto-generated method stub
		return null;
	}

}

package org.uoft.cs.darwin.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.uofr.cs.darwin.common.BayesianNetwork;
import org.uofr.cs.darwin.common.ConditionalProbabilityTable;
import org.uofr.cs.darwin.common.Variable;
import org.uofr.cs.darwin.inference.Inference;
import org.uofr.cs.darwin.io.XmlBif;
import org.xml.sax.SAXException;

public class InferenceTest {

	@Test
	public void sumOut() {
		XmlBif xmlBif;
		try {
			xmlBif = new XmlBif(new FileInputStream(new File("/Users/jhonatanoliveira/Insync/uregina/research/dataset/asia.xml")));
			BayesianNetwork bn = xmlBif.getBn();
			List<Variable> varsToSumOut = bn.getAllVariables();
			varsToSumOut.remove(bn.getVariable("dysp"));
			List<ConditionalProbabilityTable> newFactorization = Inference.sumOut(bn.getAllCpts(), varsToSumOut);
			ConditionalProbabilityTable finalCpt = Inference.multiplyAll(newFactorization);
			System.out.println(finalCpt);
			System.out.println(finalCpt.getValues());
			
			
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package org.uoft.cs.darwin.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.uofr.cs.darwin.common.BayesianNetwork;
import org.uofr.cs.darwin.common.ConditionalProbabilityTable;
import org.uofr.cs.darwin.io.XmlBif;
import org.uofr.cs.darwin.utils.ConditionalProbabilityTableOperation;
import org.xml.sax.SAXException;

public class ConditionalProbabilityTableOperationTest {

	@Test
	public void multiply() {
		XmlBif xmlBif;
		try {
			xmlBif = new XmlBif(new FileInputStream(new File("/Users/jhonatanoliveira/Downloads/asia.xml")));
			BayesianNetwork bn = xmlBif.getBn();
			
			ConditionalProbabilityTable product = ConditionalProbabilityTableOperation.multiply(bn.getCpt("VisitAsia"), bn.getCpt("Tuberculosis"));
			System.out.println(product.toString());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void marginalize() {
		XmlBif xmlBif;
		try {
			xmlBif = new XmlBif(new FileInputStream(new File("/Users/jhonatanoliveira/Downloads/asia.xml")));
			BayesianNetwork bn = xmlBif.getBn();
			
			ConditionalProbabilityTable product = ConditionalProbabilityTableOperation.multiply(bn.getCpt("VisitAsia"), bn.getCpt("Tuberculosis"));
			ConditionalProbabilityTable marginal = ConditionalProbabilityTableOperation.marginalize(product, bn.getVariable("VisitAsia"));
			System.out.println(marginal.getValues());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

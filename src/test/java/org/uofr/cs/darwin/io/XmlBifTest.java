package org.uofr.cs.darwin.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.uofr.cs.darwin.common.BayesianNetwork;
import org.xml.sax.SAXException;

public class XmlBifTest {

	@Test
	public void readBn() {
		
		try {
			XmlBif xmlBif = new XmlBif(new FileInputStream(new File("/Users/jhonatanoliveira/Downloads/asia.xml")));
			BayesianNetwork bn = xmlBif.getBn();
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

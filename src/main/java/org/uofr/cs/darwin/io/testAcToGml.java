package org.uofr.cs.darwin.io;

import java.io.IOException;

public class testAcToGml {

	public static void main(String[] args) {
		 
		try {
			AcToGml.parse("/Users/jhonatanoliveira/Insync/projects/research/irrelevant_ac/ace_v3.0_osx/simple_2var_bn.xbif.ac");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

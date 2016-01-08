package org.uofr.cs.darwin.common;

import java.util.ArrayList;
import java.util.List;

public class Variable {

	private String name;
	private String type;
	private List<String> values;
	
	public Variable() {
		this.values = new ArrayList<String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	public void addValue(String newValue) {
		this.values.add(newValue);
	}
	
	public int getCardinality() {
		return this.values.size();
	}
	
	@Override
	public String toString() {
		return this.name;
		
	}

}

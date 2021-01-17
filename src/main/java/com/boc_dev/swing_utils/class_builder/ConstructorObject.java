package com.boc_dev.swing_utils.class_builder;

import java.util.ArrayList;

public class ConstructorObject {

	private final ArrayList<String> inputTypes;
	private final ArrayList<String> inputNames;

	public ConstructorObject() {
		this.inputTypes = new ArrayList<>();
		this.inputNames = new ArrayList<>();
	}

	public ConstructorObject(ArrayList<String> inputTypes, ArrayList<String> inputNames) {
		this.inputTypes = inputTypes;
		this.inputNames = inputNames;
	}

	public ArrayList<String> getInputTypes() {
		return inputTypes;
	}

	public ArrayList<String> getInputNames() {
		return inputNames;
	}

	public String hashString() {
		StringBuilder sb = new StringBuilder();
		for (String inputType : inputTypes) {
			sb.append(inputType);
		}
		return sb.toString();
	}
}

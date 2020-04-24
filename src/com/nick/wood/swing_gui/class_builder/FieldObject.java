package com.nick.wood.swing_gui.class_builder;

import java.util.ArrayList;

public class FieldObject {
	private ArrayList<String> modifiers;
	private String valueType;

	public FieldObject(ArrayList<String> modifiers, String valueType) {
		this.modifiers = modifiers;
		this.valueType = valueType;
	}

	public ArrayList<String> getModifiers() {
		return modifiers;
	}

	public void setModifiers(ArrayList<String> modifiers) {
		this.modifiers = modifiers;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}
}

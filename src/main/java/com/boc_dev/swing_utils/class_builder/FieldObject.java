package com.boc_dev.swing_utils.class_builder;

import java.util.ArrayList;

public class FieldObject {
	private ArrayList<String> modifiers;
	private String valueType;
	private Object defaultValue;

	public FieldObject(ArrayList<String> modifiers, String valueType) {
		this.modifiers = modifiers;
		this.valueType = valueType;
	}

	public FieldObject(ArrayList<String> modifiers, String valueType, Object defaultValue) {
		this.modifiers = modifiers;
		this.valueType = valueType;
		this.defaultValue = defaultValue;
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

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}
}

package com.nick.wood.swing_gui.model;

public class TestDataTwo {

	private final String id;
	private String name;
	private int number;
	private double number2;
	private boolean bool;
	private final boolean boolFinal;

	public TestDataTwo() {
		id = "id";
		name = "name";
		number = -1;
		number2 = -1.0;
		bool = false;
		boolFinal = false;
	}

	public TestDataTwo(String id, String name, int number, double number2, boolean bool, boolean boolFinal) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.number2 = number2;
		this.bool = bool;
		this.boolFinal = boolFinal;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getNumber2() {
		return number2;
	}

	public void setNumber2(double number2) {
		this.number2 = number2;
	}

	public boolean isBool() {
		return bool;
	}

	public void setBool(boolean bool) {
		this.bool = bool;
	}

	public boolean isBoolFinal() {
		return boolFinal;
	}
}

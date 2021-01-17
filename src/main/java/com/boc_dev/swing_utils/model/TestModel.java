package com.boc_dev.swing_utils.model;

import java.util.ArrayList;
import java.util.HashMap;

public class TestModel {

	private final String id;
	private String name;
	private int number;
	private double number2;
	private boolean bool;
	private final boolean boolFinal;
	private final ArrayList<String> testDataOnes = new ArrayList<>();
	private final ArrayList<TestDataTwo> testDataTwos = new ArrayList<>();
	private final ArrayList<Integer> testDataThrees = new ArrayList<>();
	private final HashMap<TestDataTwo, Integer> hashMap = new HashMap<>();
	private final HashMap<TestDataTwo, TestDataTwo> hashMap2 = new HashMap<>();

	public TestModel(String id, String name, int number, double number2, boolean bool) {
		this.id = id;
		this.name = name;
		this.number = number;
		this.number2 = number2;
		this.bool = bool;
		this.boolFinal = bool;
		this.testDataOnes.add("test");
		this.testDataOnes.add("test2");
		this.testDataOnes.add("test3");
		TestDataTwo testDataTwo = new TestDataTwo("id", "TestValue1", 1, 2.3, true, false);
		this.testDataTwos.add(testDataTwo);
		this.testDataTwos.add(new TestDataTwo("id", "TestValue2", 1, 2.3, true, false));
		this.testDataTwos.add(new TestDataTwo("id", "TestValue3", 1, 2.3, true, false));
		//this.testDataThrees.add(1);
		//this.testDataThrees.add(2);
		//this.testDataThrees.add(3);
		hashMap.put(new TestDataTwo("id", "TestValue1", 1, 2.3, true, false), 1);
		hashMap.put(new TestDataTwo("id", "TestValue1", 1, 2.3, true, false), 2);
		hashMap2.put(testDataTwo, new TestDataTwo("id", "TestValue1", 1, 2.3, true, false));
	}

	public HashMap<TestDataTwo, Integer> getHashMap() {
		return hashMap;
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

	public ArrayList<String> getTestDataOnes() {
		return testDataOnes;
	}

	public ArrayList<TestDataTwo> getTestDataTwos() {
		return testDataTwos;
	}
}

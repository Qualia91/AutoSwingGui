package com.nick.wood.swing_gui.class_builder;

import javax.naming.spi.ObjectFactoryBuilder;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class ClassBuilder {

	private String className;
	private String packageName;
	ArrayList<String> importStrings;
	private HashMap<String, FieldObject> fieldHashMap;
	private HashMap<String, ConstructorObject> inputStringConstructorObjectHashMap;

	public ClassBuilder(String className, String packageName) {
		this.className = className;
		this.packageName = packageName;
		this.importStrings = new ArrayList<>();
		this.fieldHashMap = new HashMap<>();
		this.inputStringConstructorObjectHashMap = new HashMap<>();
	}

	public Class<?> buildClass() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
		StringBuilder sb = new StringBuilder();

		// package line
		sb.append("package ").append(packageName).append(";\n");

		// add imports
		if (!importStrings.isEmpty()) {
			for (String importString : importStrings) {
				sb.append("import ").append(importString).append(";\n");
			}
		}

		// start class
		sb.append("public class ").append(className).append(" {\n\t");

		// fields
		fieldHashMap.forEach((s, o) -> {
			for (String modifier : o.getModifiers()) {
				sb.append(modifier).append(" ");
			}
			sb.append(o.getValueType()).append(" ").append(s);
			// if default value available, set it here so we don't need to make a constructor
			if (o.getDefaultValue() != null) {
				sb.append(" = ");
				if (o.getValueType().contains("ArrayList")) {
					sb.append("new ArrayList<>()");
				} else if (o.getValueType().contains("HashMap")) {
					sb.append("new HashMap<>()");
				} else if (o.getValueType().equals("String")) {
					sb.append("\"").append(o.getDefaultValue()).append("\"");
				} else {
					sb.append(o.getDefaultValue());
				}
			}
			sb.append(";\n\t");
		});

		// create constructors
		inputStringConstructorObjectHashMap.forEach((s, constructorObject) -> {
			sb.append("public ")
					.append(className)
					.append("(");
			for (int i = 0; i < constructorObject.getInputTypes().size(); i++) {
				String inputType = constructorObject.getInputTypes().get(i);
				String inputName = constructorObject.getInputNames().get(i);
				sb.append(inputType)
						.append(" ")
						.append(inputName);
				if (i < constructorObject.getInputTypes().size() - 1) {
					sb.append(", ");
				}
			}
			sb.append(") {\n\t");
			for (int i = 0; i < constructorObject.getInputTypes().size(); i++) {
				String inputName = constructorObject.getInputNames().get(i);
				sb.append("this.")
						.append(inputName)
						.append(" = ")
						.append(inputName)
						.append(";\n\t");
			}
			sb.append("}");
		});

		// getters and setters
		fieldHashMap.forEach((s, o) -> {
			sb.append("public ")
					.append(o.getValueType())
					.append(" ")
					.append("get")
					.append(s.substring(0, 1).toUpperCase())
					.append(s.substring(1))
					.append("() {\n\t\t")
					.append("return ")
					.append(s)
					.append(";\n\t")
					.append("}\n\t");

			if (!o.getModifiers().contains("final")) {
				sb.append("public void set")
						.append(s.substring(0, 1).toUpperCase())
						.append(s.substring(1))
						.append("(")
						.append(o.getValueType())
						.append(" ")
						.append(s)
						.append(") {\n\t\t")
						.append("this.")
						.append(s)
						.append(" = ")
						.append(s)
						.append(";\n\t")
						.append("}\n\t");
			}
		});

		// end class
		sb.append("\n}");

		String code = sb.toString();

		// Save source in .java file.
		String path = ClassBuilder.class.getResource("/").getPath();
		File root = new File(path);
		File sourceFile = new File(root, getClassFilePath());
		sourceFile.getParentFile().mkdirs();
		Files.write(sourceFile.toPath(), code.getBytes(StandardCharsets.UTF_8));

		// Compile source file.
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, sourceFile.getPath());

		// Load and instantiate compiled class.
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
		return Class.forName(packageName + "." + className, true, classLoader);
	}

	public void addField(String fieldName, FieldObject fieldObject) {

		fieldHashMap.put(fieldName, fieldObject);

	}

	public void addImport(String importString) {

		importStrings.add(importString);

	}

	public void addConstructor(ConstructorObject constructorObject) {
		if (inputStringConstructorObjectHashMap.containsKey(constructorObject.hashString())) {
			throw new IllegalArgumentException("Already contains constructor with same signature");
		}

		inputStringConstructorObjectHashMap.put(constructorObject.hashString(), constructorObject);

	}

	public String getClassName() {
		return className;
	}

	public String getClassFile() {
		return "/" + packageName + "/" + className + ".java";
	}

	public String getClassFilePath() {
		return "/" + packageName.replace(".", "/") + "/" + className + ".java";
	}
}

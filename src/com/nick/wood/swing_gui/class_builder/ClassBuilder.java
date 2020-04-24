package com.nick.wood.swing_gui.class_builder;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class ClassBuilder {

	private String className;
	private String packageName;
	private HashMap<String, FieldObject> fieldHashMap;

	public ClassBuilder(String className, String packageName) {
		this.className = className;
		this.packageName = packageName;
		this.fieldHashMap = new HashMap<>();
	}

	public Object buildClass() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
		StringBuilder sb = new StringBuilder();

		// package line
		sb.append("package ").append(packageName).append(";\n");

		// start class
		sb.append("public class ").append(className).append(" {\n\t");

		// fields
		fieldHashMap.forEach((s, o) -> {
			for (String modifier : o.getModifiers()) {
				sb.append(modifier).append(" ");
			}
			sb.append(o.getValueType()).append(" ").append(s).append(";\n\t");
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
		Class<?> cls = Class.forName(packageName + "." + className, true, classLoader);
		return cls.getConstructor().newInstance();
	}

	public void addField(String fieldName, FieldObject fieldObject) {

		fieldHashMap.put(fieldName, fieldObject);

	}

	public void addConstructor(String s) {

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

package com.nick.wood.swing_gui.utils;

import java.lang.reflect.*;

public class ObjectCreationHelper {

	public static Object createNewObject(Class<?> listClass, Object exampleObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Constructor<?> constructor;
		Object o;
		switch (listClass.getTypeName().toLowerCase()) {
			case "java.lang.string":
				o = "New Object";
				break;
			case "java.lang.integer":
				o = 1;
				break;
			case "java.lang.long":
				o = 1L;
				break;
			case "java.lang.float":
				o = 1.0f;
				break;
			case "java.lang.double":
				o = 1.0;
				break;
			default:
				// try and find example object
				if (exampleObject != null) {
					try {
						Method copy = listClass.getDeclaredMethod("copy");
						return copy.invoke(exampleObject);
					} catch (NoSuchMethodException e) {
						// ignore this one
					}
				}

				// if not just use default constructor. this will all come crashing down if that doesnt exist
				constructor = listClass.getConstructor();
				o = constructor.newInstance();
		}

		return o;

	}

	public static Object createNewObjectFromString(Type listType, String str) throws NumberFormatException {
		Object o = null;
		try {
			switch (listType.getTypeName().toLowerCase()) {
				case "java.lang.string":
					o = str;
					break;
				case "java.lang.integer":
					o = Integer.parseInt(str);
					break;
				case "java.lang.long":
					o = Long.parseLong(str);
					break;
				case "java.lang.float":
					o = Float.parseFloat(str);
					break;
				case "java.lang.double":
					o = Double.parseDouble(str);
					break;
				default:
					try {
						Class<?> aClass = Class.forName(listType.getTypeName());
						Constructor<?> constructor = aClass.getConstructor();
						o = constructor.newInstance();
					} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
						e.printStackTrace();
					}
			}
		} catch (NumberFormatException numberFormatException) {
			throw new NumberFormatException("Input string " + str +" cannot be converted to required type of " + listType.getTypeName());
		}

		return o;

	}
}

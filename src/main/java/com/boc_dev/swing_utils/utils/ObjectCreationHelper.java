package com.boc_dev.swing_utils.utils;

import java.lang.reflect.*;

public class ObjectCreationHelper {

	public static Object createNewObject(Class<?> listClass, Object exampleObject) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		// try and find example object
		// if not just use default constructor. this will all come crashing down if that doesnt exist
//		return switch (listClass.getTypeName().toLowerCase()) {
//			case "java.lang.string" -> "New Object";
//			case "java.lang.integer" -> 1;
//			case "java.lang.long" -> 1L;
//			case "java.lang.float" -> 1.0f;
//			case "java.lang.double" -> 1.0;
//			default -> {
//				if (exampleObject != null) {
//					try {
//						Method copy = listClass.getDeclaredMethod("copy");
//						yield copy.invoke(exampleObject);
//					} catch (NoSuchMethodException e) {
//						// ignore this one and just use default constructor
//					}
//				}
//				yield listClass.getConstructor().newInstance();
//			}
//		};

		switch (listClass.getTypeName().toLowerCase()) {
			case "java.lang.string":
				return "New Object";
			case "java.lang.integer":
				return 1;
			case "java.lang.long":
				return 1L;
			case "java.lang.float":
				return 1.0f;
			case "java.lang.double":
				return 1.0;
			default:
				if (exampleObject != null) {
					try {
						Method copy = listClass.getDeclaredMethod("copy");
						return copy.invoke(exampleObject);
					} catch (NoSuchMethodException e) {
						// ignore this one and just use default constructor
					}
				}
				return listClass.getConstructor().newInstance();
			}
		}

//	public static Object createNewObjectFromString(Type listType, String str) throws Exception {
//		return switch (listType.getTypeName().toLowerCase()) {
//				case "java.lang.string" -> str;
//				case "java.lang.integer" -> Integer.parseInt(str);
//				case "java.lang.long" -> Long.parseLong(str);
//				case "java.lang.float" -> Float.parseFloat(str);
//				case "java.lang.double" -> Double.parseDouble(str);
//				default -> {
//					try {
//						Class<?> aClass = Class.forName(listType.getTypeName());
//						Constructor<?> constructor = aClass.getConstructor();
//						yield constructor.newInstance();
//					} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | InvocationTargetException | IllegalAccessException e) {
//						throw new Exception(e);
//					}
//				}
//			};
//
//	}
}

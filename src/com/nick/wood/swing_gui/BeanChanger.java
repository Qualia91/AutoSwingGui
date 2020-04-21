package com.nick.wood.swing_gui;

import java.lang.reflect.Field;
import java.util.Stack;
import java.util.function.Consumer;

public class BeanChanger {


	private final Runnable beanChangerActivateConsumer;
	Stack<Change> actionStack = new Stack<>();
	Stack<Change> redoStack = new Stack<>();
	private final Object model;

	public BeanChanger(Object model, Runnable beanChangerActivateConsumer) {
		this.model = model;
		this.beanChangerActivateConsumer = beanChangerActivateConsumer;
	}

	public Stack<Change> getActionStack() {
		return actionStack;
	}

	public Stack<Change> getRedoStack() {
		return redoStack;
	}

	public void undo() {
		if (!actionStack.empty()) {
			Change pop = actionStack.pop();
			redoStack.push(pop);
			pop.getUndo().run();
		}
		beanChangerActivateConsumer.run();
	}

	public void redo() {
		if (!redoStack.empty()) {
			Change pop = redoStack.pop();
			actionStack.push(pop);
			pop.getUndo().run();
		}
		beanChangerActivateConsumer.run();
	}

	public void applyChange(String fieldName, Object value, Consumer<Object> field) {
		try {
			Field declaredField = model.getClass().getDeclaredField(fieldName);
			declaredField.setAccessible(true);
			Object original = declaredField.get(model);
			Change change = new Change(
					() -> {
						try {
							declaredField.set(model, value);
							field.accept(value);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					},
					() -> {
						try {
							declaredField.set(model, original);
							field.accept(original);
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					});
			field.accept(value);
			change.doChange();
			redoStack.clear();
			actionStack.push(change);
			beanChangerActivateConsumer.run();
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}

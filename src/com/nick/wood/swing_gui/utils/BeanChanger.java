package com.nick.wood.swing_gui.utils;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.Stack;
import java.util.function.Consumer;

public class BeanChanger {

	private boolean isActive = false;
	private final Runnable beanChangerActivateConsumer;
	Stack<Change> actionStack = new Stack<>();
	Stack<Change> redoStack = new Stack<>();

	public BeanChanger(Runnable beanChangerActivateConsumer) {
		this.beanChangerActivateConsumer = beanChangerActivateConsumer;
	}

	public Stack<Change> getActionStack() {
		return actionStack;
	}

	public Stack<Change> getRedoStack() {
		return redoStack;
	}

	public void undo() {
		isActive = true;
		if (!actionStack.empty()) {
			Change pop = actionStack.pop();
			redoStack.push(pop);
			pop.getUndo().run();
		}
		beanChangerActivateConsumer.run();
		isActive = false;
	}

	public void redo() {
		isActive = true;
		if (!redoStack.empty()) {
			Change pop = redoStack.pop();
			actionStack.push(pop);
			pop.getChange().run();
		}
		beanChangerActivateConsumer.run();
		isActive = false;
	}

	public void applyChange(Change change) {
		if (!isActive) {
			redoStack.clear();
			actionStack.push(change);
			beanChangerActivateConsumer.run();
		}
	}

	public void applyChange(Field declaredField, Object model, Object oldValue, Object newValue, Consumer<Object> updateViewConsumer) {
		if (!isActive) {
			try {
				declaredField.setAccessible(true);
				Change change = new Change(
						() -> {
							try {
								declaredField.set(model, newValue);
								updateViewConsumer.accept(newValue);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						},
						() -> {
							try {
								declaredField.set(model, oldValue);
								updateViewConsumer.accept(oldValue);
							} catch (IllegalAccessException e) {
								e.printStackTrace();
							}
						});
				declaredField.set(model, newValue);
				redoStack.clear();
				actionStack.push(change);
				beanChangerActivateConsumer.run();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}

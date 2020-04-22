package com.nick.wood.swing_gui.utils;

import javax.swing.*;
import java.lang.reflect.Field;
import java.util.function.Consumer;

public class Change {
	private final Runnable change;
	private final Runnable undo;

	public Change(Object model, Field field, Consumer<Object> updateView, Object newVal, Object oldValue) {
		change = () -> {
			try {
				field.set(model, newVal);
				updateView.accept(newVal);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		};
		undo = () -> {
			try {
				field.set(model, oldValue);
				updateView.accept(oldValue);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		};
	}

	public Change(Runnable change, Runnable undo) {
		this.change = change;
		this.undo = undo;
	}

	public Runnable getChange() {
		return change;
	}

	public Runnable getUndo() {
		return undo;
	}

	public void doChange() {
		change.run();
	}

	public void undoChange() {
		undo.run();
	}
}

package com.nick.wood.swing_gui.utils;

public class Change {
	private final Runnable change;
	private final Runnable undo;

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

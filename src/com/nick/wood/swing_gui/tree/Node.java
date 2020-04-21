package com.nick.wood.swing_gui.tree;

public class Node<T, U> {

	final private T data;
	final private U type;
	Node<T, U> child;

	public Node(T data, U type) {
		this.data = data;
		this.type = type;
	}

	public T getData() {
		return data;
	}

	public U getType() {
		return type;
	}

	public Node<T, U> getChild() {
		return child;
	}

	public void setChild(Node<T, U> child) {
		this.child = child;
	}
}

package com.nick.wood.swing_gui.utils;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class IntegerDocumentFilter extends DocumentFilter {

	private final BeanChanger beanChanger;
	private final JTextField jValue;
	private final Field field;
	private final Object model;

	public IntegerDocumentFilter(BeanChanger beanChanger, JTextField jValue, Field field, Object model) {
		this.field = field;
		this.beanChanger = beanChanger;
		this.jValue = jValue;
		this.model = model;
	}

	@Override
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet attr) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());
		String newText = str;
		if (newText.matches("^[0-9]+")) {
			super.insertString(fb, offs, str, attr);
			int val = Integer.parseInt(newText);
			int oldValue = Integer.parseInt(oldText);

			Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), val, oldValue);
			try {
				field.set(model, val);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			beanChanger.applyChange(change);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	@Override
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet attrs) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offs) + str + oldText.substring(length + offs);

		if (newText.matches("^[0-9]+")) {
			super.replace(fb, offs, length, str, attrs);
			int val = Integer.parseInt(newText);
			int oldValue = Integer.parseInt(oldText);
			Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), val, oldValue);
			try {
				field.set(model, val);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			beanChanger.applyChange(change);
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}
}

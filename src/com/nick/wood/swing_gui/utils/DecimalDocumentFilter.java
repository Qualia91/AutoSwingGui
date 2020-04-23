package com.nick.wood.swing_gui.utils;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DecimalDocumentFilter extends DocumentFilter {

	private final BeanChanger beanChanger;
	private final JTextField jValue;
	private final Field field;
	private final Object model;

	public DecimalDocumentFilter(BeanChanger beanChanger, JTextField jValue, Field field, Object model) {
		this.field = field;
		this.beanChanger = beanChanger;
		this.jValue = jValue;
		this.model = model;
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offset) + oldText.substring(length + offset);

		double newValue = 0.0;
		if (!newText.isEmpty()) {
			newValue = Double.parseDouble(newText);
		}
		double oldValue = Double.parseDouble(oldText);

		Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), newValue, oldValue);

		beanChanger.applyChange(change);
		super.remove(fb, offset, length);
	}

	@Override
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet attr) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offs) + str + oldText.substring(offs);

		if (newText.matches("^[0-9]*[.]?[0-9]*$")) {
			int val = Integer.parseInt(newText);
			double oldValue = Double.parseDouble(oldText);
			Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), val, oldValue);

			beanChanger.applyChange(change);

			super.insertString(fb, offs, str, attr);
		} else {
			if (!newText.isEmpty()) {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}

	@Override
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet attrs) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offs) + str + oldText.substring(length + offs);

		if (newText.matches("^[0-9]*[.]?[0-9]*$")) {
			double val = Double.parseDouble(newText);
			if (oldText.isEmpty()) {
				oldText = "0";
			}
			double oldValue = Double.parseDouble(oldText);
			Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), val, oldValue);

			beanChanger.applyChange(change);

			super.replace(fb, offs, length, str, attrs);
		} else {
			if (!newText.isEmpty()) {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}

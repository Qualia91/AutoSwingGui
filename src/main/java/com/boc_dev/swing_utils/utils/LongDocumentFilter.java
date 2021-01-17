package com.boc_dev.swing_utils.utils;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.lang.reflect.Field;

public class LongDocumentFilter extends DocumentFilter {
	
	private final BeanChanger beanChanger;
	private final JTextField jValue;
	private final Field field;
	private final Object model;

	public LongDocumentFilter(BeanChanger beanChanger, JTextField jValue, Field field, Object model) {
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

		int newValue = 0;
		if (!newText.isEmpty()) {
			newValue = Integer.parseInt(newText);
		}
		int oldValue = Integer.parseInt(oldText);

		Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), newValue, oldValue);
		try {
			field.set(model, newValue);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		beanChanger.applyChange(change);
		super.remove(fb, offset, length);
	}

	@Override
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet attr) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());
		String newText = str;
		if (newText.length() <= 18 && newText.matches("^[0-9]+")) {
			int val = Integer.parseInt(newText);
			int oldValue = Integer.parseInt(oldText);

			Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), val, oldValue);
			try {
				field.set(model, val);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
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

		if (newText.length() <= 18 && newText.matches("^[0-9]+") ) {
			int val = Integer.parseInt(newText);
			int oldValue = Integer.parseInt(oldText);
			Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), val, oldValue);
			try {
				field.set(model, val);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			beanChanger.applyChange(change);
			super.replace(fb, offs, length, str, attrs);
		} else {
			if (!newText.isEmpty()) {
				Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}

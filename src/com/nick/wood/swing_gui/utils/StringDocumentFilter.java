package com.nick.wood.swing_gui.utils;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.lang.reflect.Field;

public class StringDocumentFilter extends DocumentFilter {

	private final BeanChanger beanChanger;
	private final JTextField jValue;
	private final Field field;
	private final Object model;

	public StringDocumentFilter(BeanChanger beanChanger, JTextField jValue, Field field, Object model) {
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

		Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), newText, oldText);

		beanChanger.applyChange(change);
		super.remove(fb, offset, length);
	}

	@Override
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet attr) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), str, oldText);

		beanChanger.applyChange(change);
		super.insertString(fb, offs, str, attr);
	}

	@Override
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet attrs) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offs) + str + oldText.substring(length + offs);

		Change change = new Change(model, field, obj -> jValue.setText(obj.toString()), newText, oldText);

		beanChanger.applyChange(change);
		super.replace(fb, offs, length, str, attrs);
	}
}

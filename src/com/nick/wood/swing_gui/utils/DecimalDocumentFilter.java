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
	public void insertString(FilterBypass fb, int offs, String str, AttributeSet attr) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offs) + str + oldText.substring(0 + offs);

		if (newText.matches("^[0-9]*[.]?[0-9]*$")) {
			super.insertString(fb, offs, str, attr);
			int val = Integer.parseInt(newText);
			beanChanger.applyChange(
					field,
					model,
					Double.parseDouble(oldText),
					val,
					(updateText) -> jValue.setText(updateText.toString()));
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	@Override
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet attrs) throws BadLocationException {
		String oldText = fb.getDocument().getText(0,
				fb.getDocument().getLength());

		String newText = oldText.substring(0, offs) + str + oldText.substring(length + offs);

		if (newText.matches("^[0-9]*[.]?[0-9]*$")) {
			super.replace(fb, offs, length, str, attrs);
			double val = Double.parseDouble(newText);
			beanChanger.applyChange(
					field,
					model,
					Double.parseDouble(oldText),
					val,
					(updateText) -> jValue.setText(updateText.toString()));
		} else {
			Toolkit.getDefaultToolkit().beep();
		}
	}

	private int numOfDecimalPlaces(String str) {
		String[] split = str.split("\\.");
		return (split.length == 0) ? 0 : split[1].length();
	}
}

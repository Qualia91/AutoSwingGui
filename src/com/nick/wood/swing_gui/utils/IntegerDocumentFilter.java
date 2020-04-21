package com.nick.wood.swing_gui.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class IntegerDocumentFilter extends DocumentFilter {

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		// remove non-digits
		fb.insertString(offset, string.replaceAll("\\D++", ""), attr);
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
		fb.replace(offset, length, text.replaceAll("\\D++", ""), attrs);
	}
}

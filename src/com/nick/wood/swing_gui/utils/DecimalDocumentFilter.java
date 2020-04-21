package com.nick.wood.swing_gui.utils;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.util.regex.Pattern;

public class DecimalDocumentFilter extends DocumentFilter {

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
		String fullString = fb.getDocument().getText(0, offset) + string;
		Pattern compile = Pattern.compile("-?([0-9]*)(\\.?)([0-9]*)");
		if (compile.matcher(fullString).matches()) {
			fb.replace(0, fullString.length(), fullString, attr);
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String string, AttributeSet attrs) throws BadLocationException {
		String fullString = fb.getDocument().getText(0, offset) + string;
		Pattern compile = Pattern.compile("-?([0-9]*)(\\.?)([0-9]*)");
		if (compile.matcher(fullString).matches()) {
			fb.replace(offset, length, string, attrs);
		}
	}
}

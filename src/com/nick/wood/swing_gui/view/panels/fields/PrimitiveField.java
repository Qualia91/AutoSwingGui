package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PrimitiveField extends JPanel {

	public PrimitiveField(Field field, Object model, String value, int modifiers, BeanChanger beanChanger, String typeString) {

		String modifierString = Modifier.toString(modifiers);

		JLabel jLabelName = new JLabel(field.getName());

		JComponent jValue;
		if (modifierString.contains("final")) {
			jValue = new JLabel(value);
		} else {
			jValue = new JTextField(value);

			switch (typeString) {
				case "long" -> ((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new LongDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
				case "int" -> ((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new IntegerDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
				case "float", "double" -> ((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new DecimalDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
				default -> ((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new StringDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
			}

		}


		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);

		add(jSplitPane);

	}


}

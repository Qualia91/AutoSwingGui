package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.utils.DecimalDocumentFilter;
import com.nick.wood.swing_gui.utils.IntegerDocumentFilter;
import com.nick.wood.swing_gui.utils.StringDocumentFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class PrimitiveField extends JPanel {

	private final BeanChanger beanChanger;
	private final JLabel jLabelName;
	private final JComponent jValue;

	public PrimitiveField(Field field, Object model, String value, int modifiers, BeanChanger beanChanger, String typeString) {

		this.beanChanger = beanChanger;
		String modifierString = Modifier.toString(modifiers);

		this.jLabelName = new JLabel(field.getName());

		if (modifierString.contains("final")) {
			this.jValue = new JLabel(value);
		} else {
			this.jValue = new JTextField(value);

			switch (typeString) {
				case "long":
				case "int":
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new IntegerDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
				case "float":
				case "double":
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new DecimalDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
				default:
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new StringDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
			}

		}


		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);

		add(jSplitPane);

	}


}

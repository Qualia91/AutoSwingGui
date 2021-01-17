package com.boc_dev.swing_utils.view.panels.fields;

import com.boc_dev.swing_utils.utils.BeanChanger;
import com.boc_dev.swing_utils.utils.Change;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BooleanField extends JPanel {
	private final BeanChanger beanChanger;
	private final JLabel jLabelName;
	private final JCheckBox jValue;

	public BooleanField(Field field, Object model, boolean value, int modifiers, BeanChanger beanChanger) {

		this.beanChanger = beanChanger;
		String modifierString = Modifier.toString(modifiers);

		setLayout(new GridBagLayout());

		this.jLabelName = new JLabel(field.getName());

		this.jValue = new JCheckBox();
		this.jValue.setSelected(value);

		if (modifierString.contains("final")) {
			this.jValue.setEnabled(false);
		}

		this.jValue.addActionListener(e -> {

			Change change = new Change(model, field, (updateText) -> jValue.setSelected((boolean) updateText), this.jValue.isSelected(), !this.jValue.isSelected());
			try {
				field.set(model, this.jValue.isSelected());
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}
			beanChanger.applyChange(change);

		});

		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);
		add(jSplitPane);

	}
}

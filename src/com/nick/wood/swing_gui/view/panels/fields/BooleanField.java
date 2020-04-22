package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.BeanChanger;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

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

		this.jValue.addActionListener(e ->
				beanChanger.applyChange(
						field,
						model,
						!this.jValue.isSelected(),
						this.jValue.isSelected(),
						(updateText) -> jValue.setSelected((boolean) updateText)));


		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);
		add(jSplitPane);

	}
}

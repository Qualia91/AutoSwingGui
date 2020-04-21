package com.nick.wood.swing_gui.view.panels.fields;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class BooleanField extends JPanel {
	private final BiConsumer<Object, Consumer<Object>> valueChangeFunction;
	private final JLabel jLabelName;
	private final JCheckBox jValue;

	public BooleanField(String name, boolean value, int modifiers, BiConsumer<Object, Consumer<Object>> valueChangeFunction) {

		this.valueChangeFunction = valueChangeFunction;
		String modifierString = Modifier.toString(modifiers);

		setLayout(new GridBagLayout());

		this.jLabelName = new JLabel(name);

		this.jValue = new JCheckBox();
		this.jValue.setSelected(value);

		if (modifierString.contains("final")) {
			this.jValue.setEnabled(false);
		}

		this.jValue.addActionListener(e -> valueChangeFunction.accept(jValue.isSelected(), obj -> jValue.setSelected((boolean) obj)));


		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);
		add(jSplitPane);

	}
}

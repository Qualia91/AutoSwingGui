package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.view.panels.objects.ClickableImagePanel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ListField extends JPanel {
	private final Object valueChangeFunction;
	private final JLabel jLabelName;
	private final JList<Object> jValue;

	public ListField(String name, ArrayList<Object> value, int modifiers, Consumer<String> valueChangeFunction) {

		this.valueChangeFunction = valueChangeFunction;
		String modifierString = Modifier.toString(modifiers);

		setLayout(new GridLayout(1, 2, 10, 10));

		this.jLabelName = new JLabel(name);

		this.jValue = new JList<>();
		DefaultListModel<Object> defaultListModel = new DefaultListModel<>();

		for (Object s : value) {
			defaultListModel.addElement(s);
		}
		jValue.setModel(defaultListModel);

		JPanel jPanel = new JPanel();


		add(jLabelName);
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 0.7;
		gbc1.weighty = 1;
		gbc1.fill = GridBagConstraints.BOTH;
		jPanel.add(jValue, gbc1);
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.insets = new Insets(5, 5, 5, 5);
		gbc2.fill = GridBagConstraints.CENTER;
		gbc2.gridx = 1;
		gbc2.gridy = 0;
		gbc2.weighty = 1;
		jPanel.add(new ClickableImagePanel("/icons/icon.png"), gbc2);

		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jPanel);
		add(jSplitPane);
	}
}

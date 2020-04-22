package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.view.panels.objects.ClickableImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ListField extends JPanel {
	private final BeanChanger beanChanger;
	private final JLabel jLabelName;
	private final JList<Object> jValue;
	private final DefaultListModel<Object> defaultListModel;
	private Constructor<? extends Object> constructor;

	public ListField(Field field, Object model, ArrayList<Object> value, int modifiers, BeanChanger beanChanger) {

		this.beanChanger = beanChanger;
		String modifierString = Modifier.toString(modifiers);

		setLayout(new GridLayout(1, 2, 10, 10));

		this.jLabelName = new JLabel(field.getName());

		this.jValue = new JList<>();
		this.defaultListModel = new DefaultListModel<>();

		for (Object s : value) {
			try {
				if (this.constructor == null) {
					this.constructor = s.getClass().getConstructor();
				}
				System.out.println();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
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
		ClickableImagePanel clickableImagePanel = new ClickableImagePanel("/icons/icon.png");
		clickableImagePanel.attachEventListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					Object o = constructor.newInstance();

				} catch (InstantiationException | IllegalAccessException | InvocationTargetException instantiationException) {
					instantiationException.printStackTrace();
				}
			}
		});
		jPanel.add(clickableImagePanel, gbc2);

		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jPanel);
		add(jSplitPane);
	}
}

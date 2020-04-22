package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.utils.Change;
import com.nick.wood.swing_gui.view.GuiBuilder;
import com.nick.wood.swing_gui.view.panels.objects.ClickableImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

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
					this.constructor = s.getClass().getConstructor(String.class);
				}
				System.out.println();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			defaultListModel.addElement(s);
		}
		jValue.setModel(defaultListModel);

		JPanel jPanel = new JPanel();

		beanChanger.attachBeanChangerListener(() -> {
			jValue.repaint();
		});

		jValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (jValue.hasFocus()) {
					if (e.getKeyCode() == KeyEvent.VK_DELETE) {

						try {

							Object selectedValue = jValue.getSelectedValue();

							Runnable changeRun = () -> {
								try {
									((ArrayList) field.get(model)).remove(selectedValue);
									defaultListModel.removeElement(selectedValue);
								} catch (IllegalAccessException illegalAccessException) {
									illegalAccessException.printStackTrace();
								}
							};

							Runnable undoRun = () -> {
								try {
									((ArrayList) field.get(model)).add(selectedValue);
									defaultListModel.addElement(selectedValue);
								} catch (IllegalAccessException illegalAccessException) {
									illegalAccessException.printStackTrace();
								}
							};

							((ArrayList) field.get(model)).remove(selectedValue);
							defaultListModel.removeElement(selectedValue);

							Change change = new Change(changeRun, undoRun);

							beanChanger.applyChange(change);
						} catch (IllegalAccessException illegalAccessException) {
							illegalAccessException.printStackTrace();
						}
					}
				}
			}
		});

		jValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				SwingUtilities.invokeLater(() -> {

					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
						UIManager.getDefaults().put("SplitPane.border", BorderFactory.createEmptyBorder());
					} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
						ex.printStackTrace();
					}

					GuiBuilder guiBuilder = new GuiBuilder(jValue.getSelectedValue(), beanChanger);

				});
			}
		});

		add(jLabelName);
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 0.7;
		gbc1.weighty = 1;
		gbc1.fill = GridBagConstraints.BOTH;
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(jValue);
		jPanel.add(scrollPane, gbc1);
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
					Object o = constructor.newInstance("New Object");

					Runnable changeRun = () -> {
						try {
							((ArrayList) field.get(model)).add(o);
							defaultListModel.addElement(o);
						} catch (IllegalAccessException illegalAccessException) {
							illegalAccessException.printStackTrace();
						}
					};

					Runnable undoRun = () -> {
						try {
							((ArrayList) field.get(model)).remove(o);
							defaultListModel.removeElement(o);
						} catch (IllegalAccessException illegalAccessException) {
							illegalAccessException.printStackTrace();
						}
					};

					((ArrayList) field.get(model)).add(o);
					defaultListModel.addElement(o);

					Change change = new Change(changeRun, undoRun);

					beanChanger.applyChange(change);

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

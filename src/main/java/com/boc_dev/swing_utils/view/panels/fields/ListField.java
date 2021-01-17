package com.boc_dev.swing_utils.view.panels.fields;

import com.boc_dev.swing_utils.utils.BeanChanger;
import com.boc_dev.swing_utils.utils.Change;
import com.boc_dev.swing_utils.view.GuiBuilder;
import com.boc_dev.swing_utils.view.frames.EmptyWindow;
import com.boc_dev.swing_utils.utils.ObjectCreationHelper;
import com.boc_dev.swing_utils.view.panels.objects.ClickableImagePanel;
import com.boc_dev.swing_utils.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.function.Function;

import static javax.swing.ScrollPaneConstants.*;

public class ListField extends JPanel {
	private final BeanChanger beanChanger;
	private final JList<Object> jValue;
	private final DefaultListModel<Object> defaultListModel;
	private Class listType;
	private final Field field;
	private final Object model;

	public ListField(Field field, Object model, ArrayList<Object> value, int modifiers, BeanChanger beanChanger) {

		this.field = field;
		this.model = model;
		this.beanChanger = beanChanger;
		String modifierString = Modifier.toString(modifiers);

		setLayout(new GridLayout(1, 2, 10, 10));

		JLabel jLabelName = new JLabel(field.getName());

		this.jValue = new JList<>();
		this.defaultListModel = new DefaultListModel<>();

		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		for (Type t : pt.getActualTypeArguments()) {
			try {
				// need to check for generic class type and remove it from the name
				// due to type erasure, it wont be found
				String typeName = t.getTypeName();
				if (typeName.contains("<")) {
					typeName = typeName.split("<")[0];
				}
				listType = ClassLoader.getSystemClassLoader().loadClass(typeName);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		for (Object s : value) {
			defaultListModel.addElement(s);
		}
		jValue.setModel(defaultListModel);

		JPanel jPanel = new JPanel();

		beanChanger.attachBeanChangerListener(jValue::repaint);

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

				if (e.getClickCount() == 2) {

					SwingUtilities.invokeLater(() -> {

						switch (jValue.getSelectedValue().getClass().getSimpleName()) {
							case "String":
								String oldValue = (String) jValue.getSelectedValue();
								Object result = JOptionPane.showInputDialog("Edit " + oldValue.getClass().getTypeName(), oldValue);
								usePopupData(result, oldValue, field, model);
								break;
							case "Integer":
								editBox(Integer::parseInt, field, model);
								break;
							case "Long":
								editBox(Long::parseLong, field, model);
								break;
							case "Float":
								editBox(Float::parseFloat, field, model);
							case "Double":
								editBox(Double::parseDouble, field, model);
								break;

							default: {
								GuiBuilder guiBuilder = new GuiBuilder(jValue.getSelectedValue(), beanChanger, new Toolbar("Edit " + jValue.getSelectedValue().getClass().getTypeName()));
								EmptyWindow emptyWindow = new EmptyWindow(800, 600, guiBuilder.getFieldListPanel(), new JMenuBar());
								emptyWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								break;
							}
						}

						jValue.repaint();

					});
				}
			}
		});

		add(jLabelName);
		jPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc1 = new GridBagConstraints();
		gbc1.insets = new Insets(5, 5, 5, 5);
		gbc1.gridx = 0;
		gbc1.gridy = 0;
		gbc1.weightx = 0.1;
		gbc1.weighty = 1;
		gbc1.fill = GridBagConstraints.BOTH;
		JScrollPane scrollPane = new JScrollPane(jValue, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setPreferredSize(new Dimension(0, 0));
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


					Object exampleObject = null;
					if (!((ArrayList) field.get(model)).isEmpty()) {
						exampleObject = ((ArrayList) field.get(model)).get(0);
						// try and find copy() if objects already exist
					}
					Object o = ObjectCreationHelper.createNewObject(listType, exampleObject);


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

				} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException instantiationException) {
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

	private void usePopupData(Object result, Object oldValue, Field field, Object model) {
		int selectedIndex = jValue.getSelectedIndex();
		if (result != null && !oldValue.equals(result)) {
			Runnable changeRun = () -> {
				try {
					((ArrayList) field.get(model)).set(selectedIndex, result);
					defaultListModel.set(jValue.getSelectedIndex(), result);
				} catch (IllegalAccessException illegalAccessException) {
					illegalAccessException.printStackTrace();
				}
			};

			Runnable undoRun = () -> {
				try {
					((ArrayList) field.get(model)).set(selectedIndex, oldValue);
					defaultListModel.set(jValue.getSelectedIndex(), oldValue);
				} catch (IllegalAccessException illegalAccessException) {
					illegalAccessException.printStackTrace();
				}
			};

			try {
				((ArrayList) field.get(model)).set(jValue.getSelectedIndex(), result);
				defaultListModel.set(jValue.getSelectedIndex(), result);
				jValue.repaint();
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}

			Change change = new Change(changeRun, undoRun);

			beanChanger.applyChange(change);
		}
	}

	private <X> void editBox(Function<String, X> castFunction, Field field, Object model) {
		boolean finished = false;
		while (!finished) {
			var oldValue = jValue.getSelectedValue();
			try {
				String result = JOptionPane.showInputDialog("Edit " + oldValue.getClass().getTypeName(), oldValue);
				if (result == null) {
					return;
				}
				var castedResult = castFunction.apply(result);
				usePopupData(castedResult, oldValue, field, model);
				finished = true;
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null, "Could not cast to correct type " + oldValue.getClass().getTypeName() + ", try again.");
			}
		}
	}
}

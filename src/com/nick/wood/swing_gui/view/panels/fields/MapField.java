package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.utils.Change;
import com.nick.wood.swing_gui.utils.ObjectCreationHelper;
import com.nick.wood.swing_gui.view.GuiBuilder;
import com.nick.wood.swing_gui.view.frames.EmptyWindow;
import com.nick.wood.swing_gui.view.panels.objects.ClickableImagePanel;
import com.nick.wood.swing_gui.view.panels.objects.MapEntryPanel;
import com.nick.wood.swing_gui.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class MapField extends JPanel {
	private final BeanChanger beanChanger;
	private final JList<Object> jValue;
	private final DefaultListModel<Object> defaultListModel;
	private Class[] mapTypes = new Class[2];

	public MapField(Field field, Object model, Map<Object, Object> value, int modifiers, BeanChanger beanChanger) {

		this.beanChanger = beanChanger;
		String modifierString = Modifier.toString(modifiers);

		setLayout(new GridLayout(1, 2, 10, 10));

		JLabel jLabelName = new JLabel(field.getName());

		this.jValue = new JList<>();
		this.defaultListModel = new DefaultListModel<>();

		ParameterizedType pt = (ParameterizedType) field.getGenericType();
		for (int i = 0; i < 2; i++) {
			try {
				mapTypes[i] = Class.forName(pt.getActualTypeArguments()[i].getTypeName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}

		for (Object s : value.entrySet()) {
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

							Map.Entry selectedValue = (Map.Entry) jValue.getSelectedValue();

							Runnable changeRun = () -> {
								try {
									((HashMap) field.get(model)).remove(selectedValue.getKey());
									defaultListModel.removeElement(selectedValue);
								} catch (IllegalAccessException illegalAccessException) {
									illegalAccessException.printStackTrace();
								}
							};

							Runnable undoRun = () -> {
								try {
									((HashMap) field.get(model)).put(selectedValue.getKey(), selectedValue.getValue());
									defaultListModel.addElement(selectedValue);
								} catch (IllegalAccessException illegalAccessException) {
									illegalAccessException.printStackTrace();
								}
							};

							((HashMap) field.get(model)).remove(selectedValue.getKey());
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

						Map.Entry selectedValue = (Map.Entry) jValue.getSelectedValue();

						switch (selectedValue.getValue().getClass().getSimpleName()) {
							case "String":
								String oldValue = (String) selectedValue.getValue();
								Object result = JOptionPane.showInputDialog("Edit " + oldValue.getClass().getTypeName(), oldValue);
								usePopupData(result, oldValue, field, model, selectedValue.getKey());
								break;
							case "Integer":
								editBox(Integer::parseInt, field, model, selectedValue.getKey(), selectedValue);
								break;
							case "Long":
								editBox(Long::parseLong, field, model, selectedValue.getKey(), selectedValue);
								break;
							case "Float":
								editBox(Float::parseFloat, field, model, selectedValue.getKey(), selectedValue);
							case "Double":
								editBox(Double::parseDouble, field, model, selectedValue.getKey(), selectedValue);
								break;

							default: {
								GuiBuilder guiBuilder = new GuiBuilder(jValue.getSelectedValue(), beanChanger, new Toolbar("Edit " + jValue.getSelectedValue().getClass().getTypeName()));
								EmptyWindow emptyWindow = new EmptyWindow(800, 600, guiBuilder.getFieldListPanel());
								emptyWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
								break;
							}
						}

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
					Object[] os = createNewMapEntry(mapTypes, field, model);

					Optional<Object[]> resultOptional = getMapEntrySanitised(os, ((HashMap) field.get(model)));

					if (resultOptional.isPresent()) {

						Object key = resultOptional.get()[0];
						Object value = resultOptional.get()[1];

						Map.Entry entry = new AbstractMap.SimpleEntry(key, value);

						Runnable changeRun = () -> {
							try {
								((HashMap) field.get(model)).put(key, value);
								defaultListModel.addElement(entry);
							} catch (IllegalAccessException illegalAccessException) {
								illegalAccessException.printStackTrace();
							}
						};

						Runnable undoRun = () -> {
							try {
								((HashMap) field.get(model)).remove(key);
								defaultListModel.removeElement(entry);
							} catch (IllegalAccessException illegalAccessException) {
								illegalAccessException.printStackTrace();
							}
						};

						((HashMap) field.get(model)).put(key, value);
						defaultListModel.addElement(entry);

						Change change = new Change(changeRun, undoRun);

						beanChanger.applyChange(change);
					}

				} catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException instantiationException) {
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

	private Optional<Object[]> getMapEntrySanitised(Object[] os, HashMap hashMap) {

		MapEntryPanel mapEntryPanel = new MapEntryPanel(mapTypes[0].getTypeName(), os[0], mapTypes[1].getTypeName(), os[1], beanChanger);

		int result = JOptionPane.showConfirmDialog(null, mapEntryPanel, "New map entry of type key", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

		if (result == JOptionPane.YES_OPTION) {
			try {
				Object key = ObjectCreationHelper.createNewObject(mapTypes[0], os[0]);
				Object value = ObjectCreationHelper.createNewObject(mapTypes[1], os[1]);
				if (hashMap.containsKey(key)) {
					JOptionPane.showMessageDialog(null, "Key " + key + "already exists");
				} else {
					return Optional.of(new Object[]{key, value});
				}
			} catch (NumberFormatException numberFormatException) {
				JOptionPane.showMessageDialog(null, numberFormatException);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}

		} else {
			return Optional.empty();
		}
		return getMapEntrySanitised(os, hashMap);
	}

	private Object[] createNewMapEntry(Class[] mapTypes, Field field, Object model) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
		Object exampleKeyObject = null;
		Object exampleValObject = null;
		if (!((HashMap) field.get(model)).isEmpty()) {
			exampleKeyObject = ((HashMap) field.get(model)).keySet().toArray()[0];
			exampleValObject = ((HashMap) field.get(model)).values().toArray()[0];
			// try and find copy() if objects already exist
		}
		Object newObjectOne = ObjectCreationHelper.createNewObject(mapTypes[0], exampleKeyObject);
		Object newObjectTwo = ObjectCreationHelper.createNewObject(mapTypes[1], exampleValObject);
		return new Object[]{newObjectOne, newObjectTwo};
	}

	private void usePopupData(Object result, Object oldValue, Field field, Object model, Object key) {
		if (result != null && !oldValue.equals(result)) {

			Map.Entry oldEntry = new AbstractMap.SimpleEntry(key, oldValue);
			Map.Entry newEntry = new AbstractMap.SimpleEntry(key, result);

			Runnable changeRun = () -> {
				try {
					((HashMap) field.get(model)).put(key, result);
					defaultListModel.set(jValue.getSelectedIndex(), newEntry);
				} catch (IllegalAccessException illegalAccessException) {
					illegalAccessException.printStackTrace();
				}
			};

			Runnable undoRun = () -> {
				try {
					((HashMap) field.get(model)).put(key, oldValue);
					defaultListModel.set(jValue.getSelectedIndex(), oldEntry);
				} catch (IllegalAccessException illegalAccessException) {
					illegalAccessException.printStackTrace();
				}
			};

			try {
				((HashMap) field.get(model)).put(key, result);
				defaultListModel.set(jValue.getSelectedIndex(), newEntry);
				jValue.repaint();
			} catch (IllegalAccessException illegalAccessException) {
				illegalAccessException.printStackTrace();
			}

			Change change = new Change(changeRun, undoRun);

			beanChanger.applyChange(change);
		}
	}

	private <X> void editBox(Function<String, X> castFunction, Field field, Object model, Object key, Map.Entry selectedValue) {
		boolean finished = false;
		while (!finished) {
			var oldValue = selectedValue.getValue();
			try {
				String result = JOptionPane.showInputDialog("Edit " + oldValue.getClass().getTypeName(), oldValue);
				if (result == null) {
					return;
				}
				var castedResult = castFunction.apply(result);
				usePopupData(castedResult, oldValue, field, model, key);
				finished = true;
			} catch (Exception exception) {
				JOptionPane.showMessageDialog(null, "Could not cast to correct type " + oldValue.getClass().getTypeName() + ", try again.");
			}
		}
	}
}

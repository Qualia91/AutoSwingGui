package com.nick.wood.swing_gui.view;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.view.frames.EmptyWindow;
import com.nick.wood.swing_gui.view.panels.fields.BooleanField;
import com.nick.wood.swing_gui.view.panels.fields.ListField;
import com.nick.wood.swing_gui.view.panels.fields.PrimitiveField;
import com.nick.wood.swing_gui.view.panels.objects.FieldListPanel;
import com.nick.wood.swing_gui.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class GuiBuilder {

	private final JButton backButton;
	private final JButton forwardButton;
	private final BeanChanger beanChanger;

	public GuiBuilder(Object model, BeanChanger beanChanger) {
		this.beanChanger = beanChanger;
		ArrayList<JPanel> jPanels = new ArrayList<>();

		this.backButton = new JButton("Back");
		backButton.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				beanChanger.undo();
			}
		});
		this.forwardButton = new JButton("Forward");
		forwardButton.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				beanChanger.redo();
			}
		});
		this.backButton.setEnabled(false);
		this.forwardButton.setEnabled(false);

		ArrayList<JButton> jButtons = new ArrayList<>();
		jButtons.add(backButton);
		jButtons.add(forwardButton);

		jPanels.add(new Toolbar(jButtons));

		for (Field declaredField : model.getClass().getDeclaredFields()) {

			try {
				declaredField.setAccessible(true);
				Class<?> type = declaredField.getType();
				jPanels.add(switch (type.toString()) {
					case "boolean"                   -> new BooleanField(declaredField, model, (boolean) declaredField.get(model), declaredField.getModifiers(), beanChanger);
					case "class java.util.ArrayList" -> new ListField(declaredField, model,  (ArrayList)declaredField.get(model), declaredField.getModifiers(), beanChanger);
					default                          -> new PrimitiveField(declaredField, model, String.valueOf(declaredField.get(model)), declaredField.getModifiers(), beanChanger, type.toString());
				});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		FieldListPanel fieldListPanel = new FieldListPanel(jPanels);

		EmptyWindow emptyWindow = new EmptyWindow(800, 600, fieldListPanel);
	}

	public void beanActive() {
		if (beanChanger.getActionStack().empty()) {
			backButton.setEnabled(false);
		} else {
			backButton.setEnabled(true);
		}
		if (beanChanger.getRedoStack().empty()) {
			forwardButton.setEnabled(false);
		} else {
			forwardButton.setEnabled(true);
		}
	}


}

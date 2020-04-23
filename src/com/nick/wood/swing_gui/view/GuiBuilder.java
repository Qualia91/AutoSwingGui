package com.nick.wood.swing_gui.view;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.view.frames.EmptyWindow;
import com.nick.wood.swing_gui.view.panels.fields.BooleanField;
import com.nick.wood.swing_gui.view.panels.fields.ListField;
import com.nick.wood.swing_gui.view.panels.fields.MapField;
import com.nick.wood.swing_gui.view.panels.fields.PrimitiveField;
import com.nick.wood.swing_gui.view.panels.objects.ClickableImagePanel;
import com.nick.wood.swing_gui.view.panels.objects.FieldListPanel;
import com.nick.wood.swing_gui.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

public class GuiBuilder {

	private final FieldListPanel fieldListPanel;
	private final BeanChanger beanChanger;

	public GuiBuilder(Object model, BeanChanger beanChanger, Toolbar toolbar) {
		this.beanChanger = beanChanger;
		ArrayList<JPanel> jPanels = new ArrayList<>();

		jPanels.add(toolbar);

		for (Field declaredField : model.getClass().getDeclaredFields()) {

			try {
				declaredField.setAccessible(true);
				Class<?> type = declaredField.getType();
				jPanels.add(switch (type.getSimpleName()) {
					case "boolean" -> new BooleanField(declaredField, model, (boolean) declaredField.get(model), declaredField.getModifiers(), beanChanger);
					case "ArrayList" -> new ListField(declaredField, model, (ArrayList) declaredField.get(model), declaredField.getModifiers(), beanChanger);
					case "HashMap" -> new MapField(declaredField, model, (Map) declaredField.get(model), declaredField.getModifiers(), beanChanger);
					default -> new PrimitiveField(declaredField, model, String.valueOf(declaredField.get(model)), declaredField.getModifiers(), beanChanger, type.toString());
				});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		this.fieldListPanel = new FieldListPanel(jPanels);

	}

	public FieldListPanel getFieldListPanel() {
		return fieldListPanel;
	}

	public void beanActive(ClickableImagePanel backButton, ClickableImagePanel forwardButton) {
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

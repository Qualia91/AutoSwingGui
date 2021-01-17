package com.boc_dev.swing_utils.view;

import com.boc_dev.swing_utils.utils.BeanChanger;
import com.boc_dev.swing_utils.view.panels.fields.*;
import com.boc_dev.swing_utils.view.panels.objects.ClickableImagePanel;
import com.boc_dev.swing_utils.view.panels.objects.FieldListPanel;
import com.boc_dev.swing_utils.view.panels.objects.MapEntryPanel;
import com.boc_dev.swing_utils.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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
				switch (type.getSimpleName()) {
					case "boolean":
						jPanels.add(new BooleanField(declaredField, model, (boolean) declaredField.get(model), declaredField.getModifiers(), beanChanger));
						break;
					case "ArrayList":
						jPanels.add(new ListField(declaredField, model, (ArrayList) declaredField.get(model), declaredField.getModifiers(), beanChanger));
						break;
					case "HashMap":
						jPanels.add(new MapField(declaredField, model, (Map) declaredField.get(model), declaredField.getModifiers(), beanChanger));
						break;
					default:
						jPanels.add(new PrimitiveField(declaredField, model, declaredField.get(model), declaredField.getModifiers(), beanChanger, type.toString()));
						break;
				}
//				jPanels.add(switch (type.getSimpleName()) {
//					case "boolean" -> new BooleanField(declaredField, model, (boolean) declaredField.get(model), declaredField.getModifiers(), beanChanger);
//					case "ArrayList" -> new ListField(declaredField, model, (ArrayList) declaredField.get(model), declaredField.getModifiers(), beanChanger);
//					case "HashMap" -> new MapField(declaredField, model, (Map) declaredField.get(model), declaredField.getModifiers(), beanChanger);
//					default -> new PrimitiveField(declaredField, model, String.valueOf(declaredField.get(model)), declaredField.getModifiers(), beanChanger, type.toString());
//				});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		this.fieldListPanel = new FieldListPanel(jPanels);

	}

	public GuiBuilder(HashMap<?, ?> map, BeanChanger beanChanger) {
		ArrayList<JPanel> jPanels = new ArrayList<>();

		this.beanChanger = beanChanger;

		for (Map.Entry entry : map.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			jPanels.add(new MapEntryPanel(key.getClass().getSimpleName(), key, value.getClass().getSimpleName(), value, beanChanger, new GridLayout(1, 4, 2, 2)));
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

package com.boc_dev.swing_utils.view.panels.fields;

import com.boc_dev.swing_utils.utils.*;
import com.boc_dev.swing_utils.view.GuiBuilder;
import com.boc_dev.swing_utils.view.frames.EmptyWindow;
import com.boc_dev.swing_utils.view.panels.objects.Toolbar;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class PrimitiveField extends JPanel {

	private final BeanChanger beanChanger;
	private final JComponent jValue;

	public PrimitiveField(Field field, Object model, Object value, int modifiers, BeanChanger beanChanger, String typeString) {

		this.beanChanger = beanChanger;
		String modifierString = Modifier.toString(modifiers);

		JLabel jLabelName = new JLabel(field.getName());



		if (modifierString.contains("final")) {
			jValue = new JLabel(value.toString());
		}
		else {

			// check if it is actually a primitive and so editable
			switch (typeString) {
				case "long":
				case "int":
				case "float":
				case "double":
				case "String":
					if (value != null) {
						jValue = new JTextField(value.toString());
					} else {
						jValue = new JTextField("No value");
					}
					break;
				default:
					// if not, do label field
					if (value != null) {
						jValue = new JLabel(value.toString());
					} else {
						jValue = new JLabel("No value");
					}
					break;
			}

			switch (typeString) {
				case "long":
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new LongDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
				case "int":
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new IntegerDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
				case "float":
				case "double":
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new DecimalDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
				case "String":
					((AbstractDocument) ((JTextField) jValue).getDocument()).setDocumentFilter(new StringDocumentFilter(beanChanger, ((JTextField) jValue), field, model));
					break;
				default:
					break;
			}

		}

		jValue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (e.getClickCount() == 2) {

					SwingUtilities.invokeLater(() -> {

						GuiBuilder guiBuilder = new GuiBuilder(value, beanChanger, new Toolbar("View " + value.getClass().getTypeName()));
						EmptyWindow emptyWindow = new EmptyWindow(800, 600, guiBuilder.getFieldListPanel(), new JMenuBar());
						emptyWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					});
				}
			}
		});

		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);

		add(jSplitPane);

	}

}

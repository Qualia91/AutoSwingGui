package com.nick.wood.swing_gui.view.panels.fields;

import com.nick.wood.swing_gui.utils.DecimalDocumentFilter;
import com.nick.wood.swing_gui.utils.IntegerDocumentFilter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.Modifier;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class PrimitiveField extends JPanel {

	private final BiConsumer<Object, Consumer<Object>> valueChangeFunction;
	private final JLabel jLabelName;
	private final JComponent jValue;

	public PrimitiveField(String name, String value, int modifiers, BiConsumer<Object, Consumer<Object>> valueChangeFunction, String typeString) {

		this.valueChangeFunction = valueChangeFunction;
		String modifierString = Modifier.toString(modifiers);

		this.jLabelName = new JLabel(name);

		if (modifierString.contains("final")) {
			this.jValue = new JLabel(value);
		} else {
			this.jValue = new JTextField(value);

			switch (typeString) {
				case "long":
					jValue.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							valueChangeFunction.accept(Long.valueOf(((JTextField) jValue).getText()), obj -> ((JTextField) jValue).setText((obj.toString())));
							super.keyTyped(e);
						}
					});
					((AbstractDocument)((JTextField) jValue).getDocument()).setDocumentFilter(new IntegerDocumentFilter());
					break;
				case "int":
					jValue.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							valueChangeFunction.accept(Integer.valueOf(((JTextField) jValue).getText()), obj -> ((JTextField) jValue).setText((obj.toString())));
							super.keyTyped(e);
						}
					});
					((AbstractDocument)((JTextField) jValue).getDocument()).setDocumentFilter(new IntegerDocumentFilter());
					break;
				case "float":
					jValue.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							valueChangeFunction.accept(Float.valueOf(((JTextField) jValue).getText()), obj -> ((JTextField) jValue).setText((obj.toString())));
							super.keyTyped(e);
						}
					});
					((AbstractDocument)((JTextField) jValue).getDocument()).setDocumentFilter(new DecimalDocumentFilter());
					break;
				case "double":
					jValue.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							valueChangeFunction.accept(Double.valueOf(((JTextField) jValue).getText()), obj -> ((JTextField) jValue).setText((obj.toString())));
							super.keyTyped(e);
						}
					});
					((AbstractDocument)((JTextField) jValue).getDocument()).setDocumentFilter(new DecimalDocumentFilter());
					break;
				default:
					jValue.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							valueChangeFunction.accept(((JTextField) jValue).getText(), obj -> ((JTextField) jValue).setText((obj.toString())));
							super.keyTyped(e);
						}
					});
					break;
			}

		}


		setLayout(new GridLayout(1, 1, 10, 10));
		jLabelName.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		jValue.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jLabelName, jValue);
		add(jSplitPane);
	}


}

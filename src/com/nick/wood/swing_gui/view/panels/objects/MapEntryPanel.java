package com.nick.wood.swing_gui.view.panels.objects;

import com.nick.wood.swing_gui.utils.BeanChanger;
import com.nick.wood.swing_gui.view.GuiBuilder;
import com.nick.wood.swing_gui.view.frames.EmptyWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MapEntryPanel extends JPanel {

	private final JTextField val1TextField;
	private final JTextField val2TextField;

	public MapEntryPanel(String typeName1, Object val1, String typeName2, Object val2, BeanChanger beanChanger) {
		JPanel pane = new JPanel();
		pane.setLayout(new GridLayout(2, 2, 2, 2));

		JLabel typeName1Label = new JLabel(typeName1);
		this.val1TextField = new JTextField(val1.toString());
		JLabel typeName2Label = new JLabel(typeName2);
		this.val2TextField = new JTextField(val2.toString());

		beanChanger.attachBeanChangerListener(() -> {
			this.val1TextField.setText(val1.toString());
			this.val1TextField.repaint();
		});
		beanChanger.attachBeanChangerListener(() -> {
			this.val2TextField.setText(val2.toString());
			this.val2TextField.repaint();
		});

		// if value type isnt primitive, dont allow user to edit here and just make JLable
		if (!(typeName1.equals("java.lang.String") ||
			typeName1.equals("java.lang.Integer") ||
			typeName1.equals("java.lang.Long") ||
			typeName1.equals("java.lang.Float") ||
			typeName1.equals("java.lang.Double"))) {
			val1TextField.setEditable(false);
			val1TextField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						GuiBuilder guiBuilder = new GuiBuilder(val1, beanChanger, new Toolbar("Edit " + val1.getClass().getTypeName()));
						JOptionPane.showConfirmDialog(null, guiBuilder.getFieldListPanel(), "New map entry of type key", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}
		if (!(typeName2.equals("java.lang.String") ||
				typeName2.equals("java.lang.Integer") ||
				typeName2.equals("java.lang.Long") ||
				typeName2.equals("java.lang.Float") ||
				typeName2.equals("java.lang.Double"))) {
			val2TextField.setEditable(false);
			val2TextField.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						GuiBuilder guiBuilder = new GuiBuilder(val2, beanChanger, new Toolbar("Edit " + val2.getClass().getTypeName()));
						JOptionPane.showConfirmDialog(null, guiBuilder.getFieldListPanel(), "New map entry of type key", JOptionPane.OK_OPTION, JOptionPane.INFORMATION_MESSAGE);
					}
				}
			});
		}

		pane.add(typeName1Label);
		pane.add(val1TextField);
		pane.add(typeName2Label);
		pane.add(val2TextField);

		add(pane);
	}

	public JTextField getVal1TextField() {
		return val1TextField;
	}

	public JTextField getVal2TextField() {
		return val2TextField;
	}
}

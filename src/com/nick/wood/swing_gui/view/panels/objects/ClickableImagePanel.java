package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.EventListener;

public class ClickableImagePanel extends JPanel {


	private final JLabel label;

	public ClickableImagePanel(String fileName) {

		setLayout(new GridLayout());

		this.label = new JLabel(new ImageIcon(this.getClass().getResource(fileName)));

		add(label);
	}

	public void attachEventListener(EventListener eventListener) {
		label.addMouseListener((MouseListener) eventListener);
	}
}

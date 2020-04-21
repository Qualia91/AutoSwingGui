package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.EventListener;

public class ButtonPanel extends JPanel {

	private final JButton button;

	public ButtonPanel() {

		setLayout(new GridLayout());
		this.button = new JButton("Button");
		add(button);

	}

	public JButton getButton() {
		return button;
	}

	public void attachEventListener(EventListener eventListener) {
		button.addMouseListener((MouseListener) eventListener);
	}
}

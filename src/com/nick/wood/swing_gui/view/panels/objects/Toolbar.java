package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends JPanel {

	public Toolbar(ArrayList<JButton> jButtons) {

		setLayout(new GridLayout(1, jButtons.size(), 10, 10));

		for (JButton jButton : jButtons) {

			add(jButton);

		}

	}

	public Toolbar() {
		JButton button1 = new JButton("B");
		JButton button2 = new JButton("A");

		setLayout(new FlowLayout(FlowLayout.LEFT));

		add(button1);
		add(button2);

	}
}

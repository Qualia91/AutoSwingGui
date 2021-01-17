package com.boc_dev.swing_utils.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends JPanel {

	public Toolbar(ArrayList<JPanel> clickableImagePanels) {

		setLayout(new GridLayout(1, clickableImagePanels.size(), 10, 10));

		for (JPanel jPanel : clickableImagePanels) {

			add(jPanel);

		}

	}

	public Toolbar(String string) {
		setLayout(new GridLayout(1, 1, 10, 10));
		add(new JLabel(string));
	}
}

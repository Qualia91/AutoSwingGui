package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Toolbar extends JPanel {

	public Toolbar(ArrayList<ClickableImagePanel> clickableImagePanels) {

		setLayout(new GridLayout(1, clickableImagePanels.size(), 10, 10));

		for (ClickableImagePanel clickableImagePanel : clickableImagePanels) {

			add(clickableImagePanel);

		}

	}

	public Toolbar(String title) {
		setLayout(new GridLayout(1, 1, 10, 10));
		add(new JLabel(title));
	}
}

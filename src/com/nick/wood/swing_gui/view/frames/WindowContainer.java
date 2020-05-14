package com.nick.wood.swing_gui.view.frames;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WindowContainer extends JFrame {

	private final ArrayList<JPanel> jPanels;

	public WindowContainer(String title, int width, int height, ArrayList<JPanel> jPanels) throws HeadlessException {
		super(title);

		setLayout(new GridLayout(jPanels.size(), 1, 10, 10));

		this.jPanels = jPanels;

		for (int index = 0; index < jPanels.size(); index++) {
			add(jPanels.get(index));
		}

		super.setVisible(true);
		super.setSize(width, height);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}

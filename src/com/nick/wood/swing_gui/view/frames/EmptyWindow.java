package com.nick.wood.swing_gui.view.frames;

import javax.swing.*;
import java.awt.*;

public class EmptyWindow extends JFrame {

	public EmptyWindow(int width, int height, JPanel parentPanel) {

		setLayout(new GridLayout());

		setGlassPane(new JPanel());

		add(parentPanel);

		setVisible(true);
		super.setSize(width, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

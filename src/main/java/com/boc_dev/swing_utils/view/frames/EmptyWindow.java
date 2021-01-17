package com.boc_dev.swing_utils.view.frames;

import javax.swing.*;
import java.awt.*;

public class EmptyWindow extends JFrame {

	public EmptyWindow(int width, int height, JPanel parentPanel) {

		setLayout(new BorderLayout());

		add(parentPanel, BorderLayout.CENTER);

		setVisible(true);
		super.setSize(width, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	public EmptyWindow(int width, int height, JPanel parentPanel, JMenuBar jMenuBar) {

		setLayout(new BorderLayout());

		add(parentPanel, BorderLayout.CENTER);
		setJMenuBar(jMenuBar);

		setVisible(true);
		super.setSize(width, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

}

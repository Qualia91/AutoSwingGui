package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;

public class SidePanel extends JPanel {

	JButton jButton;

	public SidePanel() {

		jButton = new JButton("Close");

		add(jButton);
	}

	public JButton getjButton() {
		return jButton;
	}

}

package com.boc_dev.swing_utils.view.panels.objects;

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

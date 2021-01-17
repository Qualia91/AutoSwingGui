package com.boc_dev.swing_utils.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FieldListPanel extends JPanel {

	private final ArrayList<JPanel> jPanels;

	public FieldListPanel(ArrayList<JPanel> jPanels) {

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		this.jPanels = jPanels;

		for (int index = 0; index < jPanels.size(); index++) {
			gbc.gridy = index;
			add(jPanels.get(index), gbc);
		}

	}
}

package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {

	private JTextArea textArea;

	public TextPanel() {
		this.textArea = new JTextArea();

		setLayout(new BorderLayout());

		add(new JScrollPane(textArea), BorderLayout.CENTER);

		setMinimumSize (new Dimension (0, 0));
		setPreferredSize (new Dimension (Integer.MAX_VALUE, Integer.MAX_VALUE));
	}
}

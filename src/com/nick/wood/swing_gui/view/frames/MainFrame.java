package com.nick.wood.swing_gui.view.frames;

import com.nick.wood.swing_gui.view.panels.objects.TextPanel;
import com.nick.wood.swing_gui.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JPanel {

	private JButton jButton = new JButton("Button");

	private Toolbar toolbar = new Toolbar("");

	public MainFrame(JPanel centerPanel) throws HeadlessException {

		setLayout(new BorderLayout(10, 10));

		//setJMenuBar(createMenuBar());
		add(toolbar, BorderLayout.NORTH);
		add(jButton, BorderLayout.EAST);
		add(centerPanel, BorderLayout.CENTER);

	}


}

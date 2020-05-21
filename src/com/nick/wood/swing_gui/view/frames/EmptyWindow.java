package com.nick.wood.swing_gui.view.frames;

import javax.swing.*;
import java.awt.*;

public class EmptyWindow extends JFrame {

	public EmptyWindow(int width, int height, JPanel parentPanel) {

		setLayout(new BorderLayout());

		add(parentPanel, BorderLayout.CENTER);
		setJMenuBar(createMenuBar());

		setVisible(true);
		super.setSize(width, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
	}

	private JMenuBar createMenuBar() {

		JMenuBar jMenuBar = new JMenuBar();

		JMenu menu1 = new JMenu("menu1");
		JMenu menu2 = new JMenu("menu2");

		JMenuItem menuItem1 = new JMenuItem("MenuItem1");
		JMenuItem menuItem2 = new JMenuItem("MenuItem2");

		menu1.add(menuItem1);
		menu1.add(menuItem2);

		JMenu menu3 = new JMenu("menu3");
		JMenuItem menuItem4 = new JMenuItem("MenuItem4");
		JMenuItem menuItem5 = new JMenuItem("MenuItem5");
		JRadioButton jRadioButton = new JRadioButton("jRadioButton");
		JCheckBoxMenuItem jCheckBoxMenuItem = new JCheckBoxMenuItem("jCheckBoxMenuItem");
		menu3.add(menuItem4);
		menu3.add(menuItem5);
		menu3.add(jRadioButton);
		menu3.add(jCheckBoxMenuItem);
		menu2.add(menu3);

		jMenuBar.add(menu1);
		jMenuBar.add(menu2);

		return jMenuBar;

	}

}

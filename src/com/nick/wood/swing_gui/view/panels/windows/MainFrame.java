package com.nick.wood.swing_gui.view.panels.windows;

import com.nick.wood.swing_gui.tree.Node;
import com.nick.wood.swing_gui.view.panels.objects.TextPanel;
import com.nick.wood.swing_gui.view.panels.objects.Toolbar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JPanel {

	private TextPanel textArea = new TextPanel();
	private JButton jButton = new JButton("Button");

	private Toolbar toolbar = new Toolbar("");

	Node rootNode;

	public MainFrame() throws HeadlessException {

		add(toolbar);
		add(jButton);
		add(textArea);
		add(textArea);
		add(textArea);
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

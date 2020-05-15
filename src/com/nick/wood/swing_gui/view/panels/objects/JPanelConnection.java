package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;

public class JPanelConnection {

	private final JLabel jPanelOne;
	private final JLabel jPanelTwo;

	public JPanelConnection(JLabel jPanelOne, JLabel jPanelTwo) {
		this.jPanelOne = jPanelOne;
		this.jPanelTwo = jPanelTwo;
	}

	public JLabel getjPanelOne() {
		return jPanelOne;
	}

	public JLabel getjPanelTwo() {
		return jPanelTwo;
	}
}

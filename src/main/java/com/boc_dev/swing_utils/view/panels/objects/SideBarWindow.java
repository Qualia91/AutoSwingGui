package com.boc_dev.swing_utils.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EventListener;
import java.util.function.Consumer;

import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;

public class SideBarWindow extends JPanel {

	private final Runnable sideBarAction;
	double openRatio;
	double ratio = 0.0;
	double delta = 0.005;

	public SideBarWindow(double openRatio, JPanel panelOne, JPanel panelTwo) throws HeadlessException {

		setLayout(new GridLayout());

		this.openRatio = openRatio;

		JScrollPane jScrollPane = new JScrollPane(panelOne, VERTICAL_SCROLLBAR_AS_NEEDED, HORIZONTAL_SCROLLBAR_NEVER);

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, jScrollPane, panelTwo);

		ActionListener closedAction = timerAction -> {
			ratio -= delta;
			if (ratio <= 0.0) {
				ratio = 0.0;
				jSplitPane.setDividerLocation(ratio);
				((Timer) timerAction.getSource()).stop();
				return;
			}
			jSplitPane.setDividerLocation(ratio);
		};

		ActionListener openAction = timerAction -> {
			ratio += delta;
			if (ratio >= openRatio) {
				ratio = openRatio;
				jSplitPane.setDividerLocation(ratio);
				((Timer) timerAction.getSource()).stop();
				return;
			}
			jSplitPane.setDividerLocation(ratio);
		};

		Timer timerClose = new Timer(0, closedAction);
		Timer timerOpen = new Timer(0, openAction);

		sideBarAction = new Runnable() {
			public void run() {
				if (ratio > openRatio - 0.01) {
					delta = openRatio / 5.0;
					timerClose.start();
				} else {
					delta = openRatio / 5.0;
					timerOpen.start();
				}
			}
		};

		add(jSplitPane);

		jSplitPane.setDividerLocation(0.0);

	}

	public Runnable getSideBarAction() {
		return sideBarAction;
	}
}

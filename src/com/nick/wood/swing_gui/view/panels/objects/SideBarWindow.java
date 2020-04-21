package com.nick.wood.swing_gui.view.panels.objects;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Consumer;

public class SideBarWindow extends JPanel {

	double openRatio;
	double ratio = 0.0;
	double delta = 0.005;
	private final JPanel panelOne;
	private final JPanel panelTwo;

	public SideBarWindow(double openRatio, JPanel panelOne, JPanel panelTwo, Consumer<MouseListener> changeActionOne, Consumer<MouseListener> changeActionTwo) throws HeadlessException {

		setLayout(new GridLayout());

		this.openRatio = openRatio;
		this.panelOne = panelOne;
		this.panelTwo = panelTwo;

		JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, panelOne, panelTwo);

		ActionListener closedAction = timerAction -> {
			ratio -= delta;
			if (ratio <= 0.0) {
				ratio = 0.0;
				jSplitPane.setDividerLocation(ratio);
				jSplitPane.setLeftComponent(null);
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

		Timer timerClose = new Timer(1, closedAction);
		Timer timerOpen = new Timer(1, openAction);

		changeActionOne.accept(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				delta = ratio / 10.0;
				timerClose.start();
			}
		});

		changeActionTwo.accept(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				delta = openRatio / 10.0;
				timerOpen.start();
				jSplitPane.setLeftComponent(panelOne);
			}
		});

		add(jSplitPane);

		jSplitPane.setDividerLocation(0.0);

	}

}

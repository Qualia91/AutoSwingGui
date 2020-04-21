package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class SwitchablePanel extends JPanel {

	private static final int X_DRAG_DIST = 150;
	private ArrayList<JPanel> list;
	private int currentIndex = 0;

	public SwitchablePanel(ArrayList<JPanel> list, ArrayList<Consumer<MouseListener>> nextConsumers, ArrayList<Consumer<MouseListener>> prevConsumers) {
		super();

		setLayout(new GridLayout());
		this.list = list;
		add(this.list.get(currentIndex));

		for (Consumer<MouseListener> switchConsumer : nextConsumers) {
			switchConsumer.accept(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					nextPanel();
				}
			});
		}
		for (Consumer<MouseListener> switchConsumer : prevConsumers) {
			switchConsumer.accept(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					previousPanel();
				}
			});
		}

		setUpListeners();
	}

	MouseAdapter mouseAdapter = new MouseAdapter() {

		private int mouseY;
		private int mouseX;

		@Override
		public void mouseClicked(MouseEvent e) {
			// just consume
			e.consume();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// set initial x and y position
			this.mouseX = e.getX();
			this.mouseY = e.getY();
			e.consume();
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// set final x and y position
			int xDrag =  e.getX() - this.mouseX;
			int yDrag =  e.getY() - this.mouseY;
			if (xDrag < -X_DRAG_DIST) {
				previousPanel();
			} else if (xDrag > X_DRAG_DIST) {
				nextPanel();
			}
			System.out.println(xDrag);
			e.consume();
		}

	};

	private void setUpListeners() {
		addMouseListener(mouseAdapter);
	}

	public ArrayList<JPanel> getList() {
		return list;
	}

	public void switchPanel(int index) {
		remove(0);
		currentIndex = index;
		add(list.get(index));
		revalidate();
		repaint();
	}

	public void nextPanel() {
		remove(0);
		currentIndex++;
		if (currentIndex >= list.size()) {
			currentIndex = 0;
		}
		add(list.get(currentIndex));
		revalidate();
		repaint();
	}

	public void previousPanel() {
		remove(0);
		currentIndex--;
		if (currentIndex < 0) {
			currentIndex = list.size()  - 1;
		}
		add(list.get(currentIndex));
		revalidate();
		repaint();
	}

}

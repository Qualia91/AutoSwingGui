package com.nick.wood.swing_gui.listeners;

import com.nick.wood.swing_gui.view.frames.DragContextWindow;
import com.nick.wood.swing_gui.view.panels.objects.DraggableItemPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseDragObjectListener implements MouseMotionListener, MouseListener {

	private final DraggableItemPanel jPanel;
	private final DragContextWindow context;
	private Point mousePressed;

	private boolean resizeModeWidth = false;
	private boolean resizeModeHeight = false;

	public MouseDragObjectListener(DraggableItemPanel jPanel, DragContextWindow context) {
		this.jPanel = jPanel;
		this.context = context;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		context.setLastSelected(jPanel);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = e.getPoint();
		context.setLastSelected(jPanel);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		context.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		resizeModeWidth = false;
		resizeModeHeight = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {
		context.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int dx = e.getX() - mousePressed.x;
		int dy = e.getY() - mousePressed.y;
		if (resizeModeHeight) {
			this.jPanel.setSize((int) this.jPanel.getSize().getWidth(), (int) this.jPanel.getSize().getHeight() + dy);
			mousePressed = e.getPoint();
			//this.jPanel.setLocation(this.jPanel.getLocation().x, this.jPanel.getLocation().y - (dy/2));
		} else if (resizeModeWidth) {
			this.jPanel.setSize((int) this.jPanel.getSize().getWidth() + dx, (int) this.jPanel.getSize().getHeight());
			mousePressed = e.getPoint();
			//this.jPanel.setLocation(this.jPanel.getLocation().x - (dx/2), this.jPanel.getLocation().y);
		} else {
			this.jPanel.setLocation(this.jPanel.getLocation().x + dx, this.jPanel.getLocation().y + dy);
			context.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		}
		context.repaint();
		jPanel.repaint();
		jPanel.revalidate();
	}

	@Override
	public void mouseMoved(MouseEvent e) {

		Point point = e.getPoint();
		Dimension size = jPanel.getSize();

		if ((point.x > (size.getWidth() - 10) && point.x < size.getWidth())) {
			//|| (point.x > 0 && point.x < 10)) {
			context.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			resizeModeWidth = true;
		} else if ((point.y > (size.getHeight() - 10) && point.y < size.getHeight())) {
				//|| (point.y > 0 && point.y < 10)) {
			context.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			resizeModeHeight = true;
		} else {
			context.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			resizeModeWidth = false;
			resizeModeHeight = false;
		}
	}
}

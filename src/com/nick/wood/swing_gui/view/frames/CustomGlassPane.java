package com.nick.wood.swing_gui.view.frames;

import com.nick.wood.swing_gui.utils.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CustomGlassPane extends JComponent {
	private final DragContextWindow dragContextWindow;
	private Point startPoint;
	private Point endPoint;
	private boolean drawing;

	public CustomGlassPane(DragContextWindow dragContextWindow) {
		this.dragContextWindow = dragContextWindow;
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e){
				dragContextWindow.drawLineComplete(new Line(startPoint.x, startPoint.y, e.getX(), e.getY(), Color.BLACK));
				startPoint = null;
				endPoint = null;
			}
		});

		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				endPoint = e.getPoint();
				repaint();
			}
		});

		// Block all other input events
		addMouseMotionListener(new MouseMotionAdapter(){});
		addKeyListener(new KeyAdapter(){});
		addComponentListener(new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent e){
				requestFocusInWindow();
			}
		});
		setFocusTraversalKeysEnabled(false);
	}

	@Override
	protected void paintComponent(Graphics g){
		if(startPoint != null && endPoint != null){
			Graphics2D g2 = (Graphics2D) g.create();

			g2.setRenderingHint(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.RED);
			g2.drawLine((int)startPoint.getX(), (int)startPoint.getY(), (int)endPoint.getX(), (int)endPoint.getY());

			g2.dispose();
		}
	}

	public void setStartPoint(Point point) {
		startPoint = point;
	}
}

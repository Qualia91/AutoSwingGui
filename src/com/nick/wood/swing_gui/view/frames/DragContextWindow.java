package com.nick.wood.swing_gui.view.frames;

import com.nick.wood.swing_gui.listeners.MouseDragObjectListener;
import com.nick.wood.swing_gui.utils.Line;
import com.nick.wood.swing_gui.view.panels.objects.ClickableImagePanel;
import com.nick.wood.swing_gui.view.panels.objects.DraggableItemPanel;
import com.nick.wood.swing_gui.view.panels.objects.DrawableLayeredPane;
import com.nick.wood.swing_gui.view.panels.objects.JPanelConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DragContextWindow extends JFrame {

	private final ArrayList<DraggableItemPanel> panels;
	private final DrawableLayeredPane jLayeredPane;
	private DraggableItemPanel lastSelected;

	private Point mousePressed;
	private Line line = null;
	private boolean drawing = false;

	ClickableImagePanel startClickableButton = null;

	public DragContextWindow(int width, int height, ArrayList<DraggableItemPanel> other) {

		this.jLayeredPane = new DrawableLayeredPane();
		jLayeredPane.setMinimumSize(new Dimension(width, height));

		add(jLayeredPane);

		this.panels = new ArrayList<>();
		CustomGlassPane customGlassPane = new CustomGlassPane(this);

		for (int i = 0; i < 10; i++) {
			DraggableItemPanel draggableItemPanel = new DraggableItemPanel("" + i);
			for (ClickableImagePanel eastClickableButton : draggableItemPanel.getEastClickableButtons()) {
				eastClickableButton.attachEventListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (!drawing) {
							drawing = true;
							Point point = SwingUtilities.convertPoint(eastClickableButton.getLabel(), eastClickableButton.getLabel().getX(), eastClickableButton.getLabel().getY(), DragContextWindow.this);
							customGlassPane.setStartPoint(point);
							getGlassPane().setVisible(true);
							startClickableButton = eastClickableButton;
						}
					}
				});
			}
			panels.add(draggableItemPanel);
		}

		int starting = 0;

		for (DraggableItemPanel panel : panels) {
			panel.setLocation(panel.getX() + starting, panel.getY() + starting);
			starting += 50;
			jLayeredPane.add(panel);
			MouseDragObjectListener mouseDragObjectListener = new MouseDragObjectListener(panel, this);
			panel.addMouseListener(mouseDragObjectListener);
			panel.addMouseMotionListener(mouseDragObjectListener);
		}

		jLayeredPane.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// if line is drawing then the event has just been dispatched from glass pane to here
				// as it doesn't match up with a connection point, just hid glass pane and set line to null
				requestFocus();
				mousePressed = e.getPoint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				mousePressed = e.getPoint();
			}
		});

		jLayeredPane.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int dx = e.getX() - mousePressed.x;
				int dy = e.getY() - mousePressed.y;
				for (DraggableItemPanel panel : panels) {
					panel.setLocation(panel.getLocation().x + dx, panel.getLocation().y + dy);
				}
				mousePressed = e.getPoint();
				setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
				repaint();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyCode() == 0) {
					if (lastSelected != null) {
						System.out.println(lastSelected);
						panels.remove(lastSelected);
						jLayeredPane.remove(lastSelected);
						ArrayList<JPanelConnection> remove = new ArrayList<>();
						for (JPanelConnection connection : jLayeredPane.getConnections()) {
							for (ClickableImagePanel westClickableButton : lastSelected.getWestClickableButtons()) {
								if (westClickableButton.getLabel().equals(connection.getjPanelTwo())) {
									remove.add(connection);
								}
							}
							for (ClickableImagePanel eastClickableButton : lastSelected.getEastClickableButtons()) {
								if (eastClickableButton.getLabel().equals(connection.getjPanelOne())) {
									remove.add(connection);
								}
							}
						}
						jLayeredPane.getConnections().removeAll(remove);
						lastSelected = null;
						jLayeredPane.revalidate();
						jLayeredPane.repaint();
					}
				}
			}
		});


		setGlassPane(customGlassPane);

		setVisible(true);
		super.setSize(width, height);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setLastSelected(DraggableItemPanel lastSelected) {
		this.lastSelected = lastSelected;
		for (DraggableItemPanel panel : panels) {
			panel.setBackground(Color.WHITE);
		}
		lastSelected.setBackground(Color.GRAY);
	}

	public void drawLineComplete(Line line) {
		this.line = line;
		this.drawing = false;
		getGlassPane().setVisible(false);
		// now find if the end position of the line is on a button

		ClickableImagePanel endClickableButton = null;

		for (DraggableItemPanel panel : panels) {
			for (ClickableImagePanel westClickableButton : panel.getWestClickableButtons()) {
				// find position of button
				Point pt = new Point(westClickableButton.getLabel().getLocation());
				Point point = SwingUtilities.convertPoint(westClickableButton.getLabel(), pt.x, pt.y, DragContextWindow.this);

				// find width and height
				int width = westClickableButton.getLabel().getWidth();
				int height = westClickableButton.getLabel().getHeight();

				// do some maths
				if (checkConstraints(line, point, width, height) == 2) {
					endClickableButton = westClickableButton;
				}
			}
		}

		if (endClickableButton != null) {
			jLayeredPane.getConnections().add(new JPanelConnection(startClickableButton.getLabel(), endClickableButton.getLabel()));
			jLayeredPane.repaint();
		}
	}

	public DrawableLayeredPane getjLayeredPane() {
		return jLayeredPane;
	}

	/**
	 * @param line
	 * @param pt
	 * @param width
	 * @param height
	 * @return
	 * 0 = none match
	 * 1 = first match
	 * 2 = second match
	 * 3 = both match
	 */
	private int checkConstraints(Line line, Point pt, int width, int height) {
		int returnCode = 0;
		// check first point on line
		if (checkConstraints(line.getX1(), pt.getX(), width) && checkConstraints(line.getY1(), pt.getY(), height)) {
			returnCode++;
		}
		// check second point on line
		if (checkConstraints(line.getX2(), pt.getX(), width) && checkConstraints(line.getY2(), pt.getY(), height)) {
			returnCode += 2;
		}
		return returnCode;
	}

	private boolean checkConstraints(int checkVale, double thisVale, int spread) {
		return (checkVale > thisVale - spread / 2.0) && (checkVale < thisVale + spread / 2.0);
	}
}

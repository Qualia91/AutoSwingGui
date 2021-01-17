package com.boc_dev.swing_utils.view.panels.objects;

import com.boc_dev.swing_utils.listeners.MouseDragObjectListener;
import com.boc_dev.swing_utils.view.frames.CustomGlassPane;
import com.boc_dev.swing_utils.utils.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DragContextPanel extends JPanel {

	private final ArrayList<DraggableItemPanel> panels;
	private final DrawableLayeredPane jLayeredPane;
	private final CustomGlassPane customGlassPane;
	private DraggableItemPanel lastSelected;

	private Point mousePressed;
	private Line line = null;
	private boolean drawing = false;

	ClickableImagePanel startClickableButton = null;

	public DragContextPanel() {

		setLayout(new BorderLayout());

		this.jLayeredPane = new DrawableLayeredPane();

		add(jLayeredPane, BorderLayout.CENTER);

		this.panels = new ArrayList<>();
		this.customGlassPane = new CustomGlassPane(this);

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
			public void keyPressed(KeyEvent e) {
				System.out.println(e.getKeyCode());
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
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

	}

	public void addPanel(DraggableItemPanel draggableItemPanel, int xPos, int yPos) {
		for (ClickableImagePanel eastClickableButton : draggableItemPanel.getEastClickableButtons()) {
			eastClickableButton.attachEventListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (!drawing) {
						drawing = true;
						Point point = SwingUtilities.convertPoint(eastClickableButton.getLabel(), eastClickableButton.getLabel().getX() + eastClickableButton.getLabel().getWidth() / 2, eastClickableButton.getLabel().getY() + eastClickableButton.getLabel().getHeight() / 2, getRootPane());
						customGlassPane.setStartPoint(point);
						getRootPane().getGlassPane().setVisible(true);
						startClickableButton = eastClickableButton;
					}
				}
			});
		}
		panels.add(draggableItemPanel);

		draggableItemPanel.setLocation(xPos, yPos);
		jLayeredPane.add(draggableItemPanel);
		MouseDragObjectListener mouseDragObjectListener = new MouseDragObjectListener(draggableItemPanel, this);
		draggableItemPanel.addMouseListener(mouseDragObjectListener);
		draggableItemPanel.addMouseMotionListener(mouseDragObjectListener);
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
		getRootPane().getGlassPane().setVisible(false);
		// now find if the end position of the line is on a button

		ClickableImagePanel endClickableButton = null;

		for (DraggableItemPanel panel : panels) {
			for (ClickableImagePanel westClickableButton : panel.getWestClickableButtons()) {
				// find position of button
				Point pt = westClickableButton.getLabel().getLocation();
				Point point = SwingUtilities.convertPoint(westClickableButton.getLabel(), pt.x, pt.y, getRootPane().getGlassPane());

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
	 * @return 0 = none match
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

	public CustomGlassPane getCustomGlassPane() {
		return customGlassPane;
	}
}

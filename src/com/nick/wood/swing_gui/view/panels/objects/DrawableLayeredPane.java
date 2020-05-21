package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class DrawableLayeredPane extends JLayeredPane {

	private final ArrayList<JPanelConnection> connections = new ArrayList<>();

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for (JPanelConnection connection : connections) {

			Point locationA = connection.getjPanelOne().getLocation();
			Point locationB = connection.getjPanelTwo().getLocation();

			int widthA = connection.getjPanelOne().getWidth() / 2;
			int widthB = connection.getjPanelTwo().getWidth() / 2;

			int heightA = connection.getjPanelOne().getHeight() / 2;
			int heightB = connection.getjPanelTwo().getHeight() / 2;

			Point locationAConverted = SwingUtilities.convertPoint(connection.getjPanelOne(), (int) locationA.getX() + widthA, (int) locationA.getY() + heightA, this);
			Point locationBConverted = SwingUtilities.convertPoint(connection.getjPanelTwo(), (int) locationB.getX() + widthB, (int) locationB.getY() + heightB, this);

			Graphics2D g2 = (Graphics2D) g;
			Line2D lin = new Line2D.Double(locationAConverted.getX(), locationAConverted.getY(), locationBConverted.getX(), locationBConverted.getY());
			g2.draw(lin);
		}
	}

	public ArrayList<JPanelConnection> getConnections() {
		return connections;
	}
}

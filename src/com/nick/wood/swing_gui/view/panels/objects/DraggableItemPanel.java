package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DraggableItemPanel extends JPanel {

	private final ArrayList<ClickableImagePanel> westClickableButtons = new ArrayList<>();
	private final ArrayList<ClickableImagePanel> eastClickableButtons = new ArrayList<>();

	public DraggableItemPanel(String title) {

		setLayout(new BorderLayout());

		// title label
		JLabel titleLabel = new JLabel(title, JLabel.CENTER);
		titleLabel.setBackground(Color.WHITE);
		add(titleLabel, BorderLayout.NORTH);
		titleLabel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));


		// east buttons
		ArrayList<JPanel> eastButtons = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			ClickableImagePanel clickableImagePanel = new ClickableImagePanel("/icons/icon.png");
			eastClickableButtons.add(clickableImagePanel);
			eastButtons.add(clickableImagePanel);
		}
		FieldListPanel eastFieldListPanel = new FieldListPanel(eastButtons);
		add(eastFieldListPanel, BorderLayout.EAST);
		eastFieldListPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));


		// west buttons
		ArrayList<JPanel> westButtons = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			ClickableImagePanel clickableImagePanel = new ClickableImagePanel("/icons/icon.png");
			westClickableButtons.add(clickableImagePanel);
			westButtons.add(clickableImagePanel);
		}
		FieldListPanel westFieldListPanel = new FieldListPanel(westButtons);
		add(westFieldListPanel, BorderLayout.WEST);
		westFieldListPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));


		// Center text area
		JTextArea jTextArea = new JTextArea();
		add(jTextArea, BorderLayout.CENTER);
		jTextArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		setBorder(new BevelBorder(1, Color.white, Color.lightGray, Color.gray, Color.darkGray));

		setBackground(Color.WHITE);
		setName(title);
		setSize(new Dimension(200, 200));
		setName(title);

	}

	public ArrayList<ClickableImagePanel> getWestClickableButtons() {
		return westClickableButtons;
	}

	public ArrayList<ClickableImagePanel> getEastClickableButtons() {
		return eastClickableButtons;
	}
}

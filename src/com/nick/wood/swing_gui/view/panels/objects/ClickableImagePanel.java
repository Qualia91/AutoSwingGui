package com.nick.wood.swing_gui.view.panels.objects;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;

public class ClickableImagePanel extends JPanel {

	private ImageIcon imageIcon;
	private ImageIcon disabledImageIcon;
	private JLabel label;
	LineBorder hoverOverLineBorder = new LineBorder(new Color(0, 0, 0), 1, false);
	LineBorder normalLineBorder = new LineBorder(new Color(0, 0, 0, 0), 1, false);

	private final MouseAdapter mouseAdapter;

	public ClickableImagePanel(String fileName) {

		setLayout(new GridLayout(1, 1, 10, 10));

		this.imageIcon = new ImageIcon(this.getClass().getResource(fileName));
		this.disabledImageIcon = new ImageIcon(GrayFilter.createDisabledImage(imageIcon.getImage()));
		this.label = new JLabel(imageIcon);
		this.label.setDisabledIcon(disabledImageIcon);

		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isEnabled()) {
					label.setBorder(hoverOverLineBorder);
					getRootPane().repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setBorder(normalLineBorder);
				getRootPane().repaint();
			}
		};

		label.setBorder(normalLineBorder);

		add(label);

		label.addMouseListener(mouseAdapter);

	}

	public ClickableImagePanel(ImageIcon imageIcon) {

		setLayout(new GridLayout(1, 1, 10, 10));

		this.imageIcon = imageIcon;
		this.disabledImageIcon = new ImageIcon(GrayFilter.createDisabledImage(imageIcon.getImage()));
		this.label = new JLabel(imageIcon);
		this.label.setDisabledIcon(disabledImageIcon);

		mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (isEnabled()) {
					label.setBorder(hoverOverLineBorder);
					getRootPane().repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				label.setBorder(normalLineBorder);
				getRootPane().repaint();
			}
		};

		label.setBorder(normalLineBorder);

		add(label);

		label.addMouseListener(mouseAdapter);

	}

	@Override
	public void setEnabled(boolean enabled) {
		label.setEnabled(enabled);
		super.setEnabled(enabled);
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}

	public void attachEventListener(EventListener eventListener) {
		label.addMouseListener((MouseListener) eventListener);
	}

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImage(String imageFile) {
		this.imageIcon = new ImageIcon(this.getClass().getResource(imageFile));
		this.disabledImageIcon = new ImageIcon(GrayFilter.createDisabledImage(imageIcon.getImage()));
		this.label.setIcon(imageIcon);
		this.label.setDisabledIcon(disabledImageIcon);
		repaint();
	}
}

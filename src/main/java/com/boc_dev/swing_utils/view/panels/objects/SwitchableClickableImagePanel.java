package com.boc_dev.swing_utils.view.panels.objects;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.EventListener;

public class SwitchableClickableImagePanel extends JPanel {

	private final ArrayList<ImageIcon> imageIcons = new ArrayList<>();
	private ImageIcon disabledImageIcon;
	private JLabel label;
	LineBorder hoverOverLineBorder = new LineBorder(new Color(0, 0, 0), 1, false);
	LineBorder normalLineBorder = new LineBorder(new Color(0, 0, 0, 0), 1, false);

	private final MouseAdapter mouseAdapter;

	public SwitchableClickableImagePanel(String ... fileNames) {

		setLayout(new GridLayout(1, 1, 10, 10));

		for (String fileName : fileNames) {

			ImageIcon imageIcon = new ImageIcon(this.getClass().getResource(fileName));
			imageIcons.add(imageIcon);
		}

		this.disabledImageIcon = new ImageIcon(GrayFilter.createDisabledImage(imageIcons.get(0).getImage()));
		this.label = new JLabel(imageIcons.get(0));
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

	public void setImageFromIndex(int index) {
		setImage(imageIcons.get(index));
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

	private void setImage(ImageIcon imageIcon) {
		this.disabledImageIcon = new ImageIcon(GrayFilter.createDisabledImage(imageIcon.getImage()));
		this.label.setIcon(imageIcon);
		this.label.setDisabledIcon(disabledImageIcon);
		repaint();
	}
}

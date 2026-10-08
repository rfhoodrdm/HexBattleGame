package com.rfhoodrdm.hexbattle.gui.screens.setupscreen;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JPanel;

class SquarePanelContainer extends JPanel {

	private static final long serialVersionUID = 1L;

	SquarePanelContainer(Component squareComponent) {
		setLayout(null);
		setOpaque(false);
		add(squareComponent);
	}

	@Override
	public void doLayout() {
		Insets insets = getInsets();
		int availableWidth = getWidth() - insets.left - insets.right;
		int availableHeight = getHeight() - insets.top - insets.bottom;
		int squareSize = Math.max(0, Math.min(availableWidth, availableHeight));
		int squareX = insets.left + (availableWidth - squareSize) / 2;
		int squareY = insets.top + (availableHeight - squareSize) / 2;
		getComponent(0).setBounds(squareX, squareY, squareSize, squareSize);
	}
}

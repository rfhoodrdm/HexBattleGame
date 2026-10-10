package com.rfhoodrdm.hexbattle.gui.basecomponent.label;

import javax.swing.JLabel;

public class GameTextLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	private final LabelStyle style;
	
	public GameTextLabel(String text, LabelStyle style) {
		super(text);
		
		this.style = style;
		applyStyle();
	}

	private void applyStyle() {
		setFont(style.font());
		setForeground(style.foreground());
		setOpaque(false);
	}
}

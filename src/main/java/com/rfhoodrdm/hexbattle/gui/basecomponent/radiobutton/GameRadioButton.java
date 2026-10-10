package com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton;

import javax.swing.JRadioButton;

public class GameRadioButton extends JRadioButton {

	private static final long serialVersionUID = 1L;
	
	private final RadioButtonStyle style;

	public GameRadioButton(String text, boolean isSelected, RadioButtonStyle style) {
		super(text, isSelected);
		
		this.style = style;
		applyStyle();
	}

	private void applyStyle() {
		setFont(style.font());
		setFocusPainted(false);
		setOpaque(false);
		getModel().addChangeListener(event -> applyStateStyle());
		applyStateStyle();
	}

	private void applyStateStyle() {
		if (isSelected()) {
			setForeground(style.selectedForeground());
		} else {
			setForeground(style.foreground());
		}
	}
}

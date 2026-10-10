package com.rfhoodrdm.hexbattle.gui.basecomponent.button;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * Base class for buttons that display text.
 * Take the text to display and a chosen button style.
 */
public class GameTextButton extends JButton {

	private static final long serialVersionUID = 1L;
	
	private final ButtonStyle style;
	
	public GameTextButton(String text, ButtonStyle style) {
		super(text);
		this.style = style;
		
		applyStyle();
	}

	private void applyStyle() {
        setFont(style.font());
        setForeground(style.foreground());
        setBackground(style.background());
        setMargin(style.margin());
        setBorder(
            BorderFactory.createLineBorder(style.borderColor())
        );

        setFocusPainted(false);
        setRolloverEnabled(true);
        setContentAreaFilled(true);
        setOpaque(true);
        getModel().addChangeListener(event -> applyStateBackground());
    }

	private void applyStateBackground() {
		if (getModel().isPressed()) {
			setBackground(style.pressedBackground());
		} else if (getModel().isRollover()) {
			setBackground(style.rolloverBackground());
		} else {
			setBackground(style.background());
		}
	}
}

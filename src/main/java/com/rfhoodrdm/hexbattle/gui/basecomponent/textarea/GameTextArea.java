package com.rfhoodrdm.hexbattle.gui.basecomponent.textarea;

import javax.swing.JTextArea;

/**
 * Base class for text area widgets that display text.
 * Take the text to display and a chosen text area style.
 */
public class GameTextArea extends JTextArea {

	private static final long serialVersionUID = 8897993881023140020L;
	
	private final TextAreaStyle style;
	
	
	public GameTextArea(String text, TextAreaStyle style) {
		super(text);	
		this.style = style;
		
		applyStyle();
	}


	private void applyStyle() {
		setFont(style.font());
		setForeground(style.foreground());
		setEditable(false);
		setFocusable(false);
		setLineWrap(true);
		setWrapStyleWord(true);
		setOpaque(false);
	}

}

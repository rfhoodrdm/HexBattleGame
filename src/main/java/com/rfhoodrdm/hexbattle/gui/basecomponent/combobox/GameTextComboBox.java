package com.rfhoodrdm.hexbattle.gui.basecomponent.combobox;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;

public class GameTextComboBox<T> extends JComboBox<T> {

	private static final long serialVersionUID = 1L;

	private final ComboBoxStyle style;
	
	public GameTextComboBox(ComboBoxStyle style) {
		super();
		this.style = style;
		applyStyle();
	}

	public GameTextComboBox(T[] items, ComboBoxStyle style) {
		super(items);
		this.style = style;
		applyStyle();
	}

	private void applyStyle() {
		setFont(style.font());
		addPropertyChangeListener("enabled", event -> applyEnabledStyle());
		applyEnabledStyle();
	}

	private void applyEnabledStyle() {
		if (isEnabled()) {
			setForeground(style.foreground());
			setBackground(style.background());
			setBorder(BorderFactory.createLineBorder(style.borderColor()));
		} else {
			setForeground(style.disabledForeground());
			setBackground(style.disabledBackground());
			setBorder(BorderFactory.createLineBorder(style.disabledBorderColor()));
		}
		repaint();
	}
	
}

package com.rfhoodrdm.hexbattle.gui.basecomponent;

import java.awt.Component;
import java.util.function.Function;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class DisplayNameListCellRenderer<T> extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;

	private final Class<T> valueType;
	private final Function<T, String> displayNameProvider;

	public DisplayNameListCellRenderer(
			Class<T> valueType,
			Function<T, String> displayNameProvider) {
		this.valueType = valueType;
		this.displayNameProvider = displayNameProvider;
	}

	@Override
	public Component getListCellRendererComponent(
			JList<?> list,
			Object value,
			int index,
			boolean isSelected,
			boolean cellHasFocus) {
		Component component = super.getListCellRendererComponent(
				list, value, index, isSelected, cellHasFocus);
		if (valueType.isInstance(value)) {
			T typedValue = valueType.cast(value);
			setText(displayNameProvider.apply(typedValue));
		}
		return component;
	}
}

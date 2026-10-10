package com.rfhoodrdm.hexbattle.gui.basecomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.border.LineBorder;

import org.junit.jupiter.api.Test;

import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.ComboBoxStyle;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.ComboBoxStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.GameTextComboBox;

class GameTextComboBoxTests {

	@Test
	void enabledStateAutomaticallyChangesImperialMenuColors() {
		ComboBoxStyle style = ComboBoxStyles.IMPERIAL_MENU;
		GameTextComboBox<String> comboBox = new GameTextComboBox<>(style);

		assertEquals(style.foreground(), comboBox.getForeground());
		assertEquals(style.background(), comboBox.getBackground());
		assertEquals(style.borderColor(), ((LineBorder) comboBox.getBorder()).getLineColor());

		comboBox.setEnabled(false);

		assertEquals(style.disabledForeground(), comboBox.getForeground());
		assertEquals(style.disabledBackground(), comboBox.getBackground());
		assertEquals(
				style.disabledBorderColor(),
				((LineBorder) comboBox.getBorder()).getLineColor());

		comboBox.setEnabled(true);

		assertEquals(style.foreground(), comboBox.getForeground());
		assertEquals(style.background(), comboBox.getBackground());
		assertEquals(style.borderColor(), ((LineBorder) comboBox.getBorder()).getLineColor());
	}

}

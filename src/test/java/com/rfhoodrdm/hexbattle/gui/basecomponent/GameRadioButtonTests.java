package com.rfhoodrdm.hexbattle.gui.basecomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.ButtonGroup;

import org.junit.jupiter.api.Test;

import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.GameRadioButton;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.RadioButtonStyle;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.RadioButtonStyles;

class GameRadioButtonTests {

	@Test
	void groupedButtonsAutomaticallyApplySelectedForeground() {
		RadioButtonStyle style = RadioButtonStyles.IMPERIAL_SELECTION;
		GameRadioButton firstButton = new GameRadioButton("First", true, style);
		GameRadioButton secondButton = new GameRadioButton("Second", false, style);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(firstButton);
		buttonGroup.add(secondButton);

		assertEquals(style.selectedForeground(), firstButton.getForeground());
		assertEquals(style.foreground(), secondButton.getForeground());

		secondButton.setSelected(true);

		assertEquals(style.foreground(), firstButton.getForeground());
		assertEquals(style.selectedForeground(), secondButton.getForeground());
	}
}

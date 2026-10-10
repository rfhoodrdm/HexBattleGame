package com.rfhoodrdm.hexbattle.gui.screens.setupscreen.subpanels;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.nio.file.Path;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.ButtonStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.button.GameTextButton;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.ComboBoxStyles;
import com.rfhoodrdm.hexbattle.gui.basecomponent.combobox.GameTextComboBox;
import com.rfhoodrdm.hexbattle.gui.screens.setupscreen.event.MapSelectionChangedEvent;
import com.rfhoodrdm.hexbattle.gui.basecomponent.panel.TitledGamePanel;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.GameRadioButton;
import com.rfhoodrdm.hexbattle.gui.basecomponent.radiobutton.RadioButtonStyles;
import com.rfhoodrdm.hexbattle.service.maploader.MapFileLoader;
import com.rfhoodrdm.hexbattle.state.app.SetupState;
import com.rfhoodrdm.hexbattle.state.map.MapSelectionOption;
import com.rfhoodrdm.hexbattle.state.map.MapTemplate;
import com.rfhoodrdm.hexbattle.state.map.MapTemplates;
import com.rfhoodrdm.hexbattle.state.map.MapTemplatesLoadedEvent;

import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Component
@Slf4j
public class MapSelectSubpanel extends TitledGamePanel {

	private static final long serialVersionUID = 1L;
	private static final int COMPONENT_GAP = 12;

	private final MapTemplates mapTemplates;
	private final SetupState setupState;
	private final MapFileLoader mapFileLoader;
	private final ApplicationEventPublisher eventPublisher;
	private final GameRadioButton builtInRadioButton;
	private final GameRadioButton fromFileRadioButton;
	private final GameTextComboBox<MapSelectionOption> builtInMapComboBox;
	private final GameTextButton loadMapFileButton;

	private boolean refreshingMapOptions;

	public MapSelectSubpanel(
			MapTemplates mapTemplates,
			SetupState setupState,
			MapFileLoader mapFileLoader,
			ApplicationEventPublisher eventPublisher) {
		super("Map Select");
		this.mapTemplates = mapTemplates;
		this.setupState = setupState;
		this.mapFileLoader = mapFileLoader;
		this.eventPublisher = eventPublisher;

		setLayout(new GridBagLayout());
		builtInRadioButton = new GameRadioButton(
				"Built-in", true, RadioButtonStyles.IMPERIAL_SELECTION);
		fromFileRadioButton = new GameRadioButton(
				"From File", false, RadioButtonStyles.IMPERIAL_SELECTION);
		ButtonGroup mapSource = new ButtonGroup();
		mapSource.add(builtInRadioButton);
		mapSource.add(fromFileRadioButton);

		builtInMapComboBox = new GameTextComboBox<>(ComboBoxStyles.IMPERIAL_MENU);
		builtInMapComboBox.setRenderer(new MapSelectionOptionRenderer());

		loadMapFileButton = new GameTextButton("Load Map File", ButtonStyles.IMPERIAL_ACTION);
		Dimension loadButtonSize = new Dimension(
				ButtonStyles.MENU_BUTTON_WIDTH,
				ButtonStyles.MENU_BUTTON_HEIGHT);
		loadMapFileButton.setPreferredSize(loadButtonSize);
		loadMapFileButton.setMinimumSize(loadButtonSize);
		loadMapFileButton.setMaximumSize(loadButtonSize);
		addComponents();
		addListeners();
		updateSourceControlState();
	}

	@EventListener
	public void receiveMapTemplatesLoadedEvent(MapTemplatesLoadedEvent event) {
		Runnable refreshOptions = this::refreshBuiltInMapOptions;
		if (SwingUtilities.isEventDispatchThread()) {
			refreshOptions.run();
		} else {
			SwingUtilities.invokeLater(refreshOptions);
		}
	}

	private void addComponents() {
		add(builtInRadioButton, createConstraints(0, 0, 0.30));
		add(builtInMapComboBox, createConstraints(1, 0, 0.70));
		add(fromFileRadioButton, createConstraints(0, 1, 0.30));
		add(loadMapFileButton, createConstraints(1, 1, 0.70));
	}

	private GridBagConstraints createConstraints(int gridX, int gridY, double weightX) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.weightx = weightX;
		constraints.weighty = 0.5;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(
				COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP, COMPONENT_GAP);
		return constraints;
	}

	private void addListeners() {
		builtInRadioButton.addActionListener(event -> selectBuiltInSource());
		fromFileRadioButton.addActionListener(event -> selectFileSource());
		builtInMapComboBox.addActionListener(event -> selectBuiltInMap());
		loadMapFileButton.addActionListener(event -> chooseMapFile());
	}

	private void selectBuiltInSource() {
		if (!builtInRadioButton.isSelected()) {
			return;
		}

		updateSourceControlState();
		selectBuiltInMap();
	}

	private void selectFileSource() {
		if (!fromFileRadioButton.isSelected()) {
			return;
		}

		updateSourceControlState();
		clearSelectedMap();
	}

	private void updateSourceControlState() {
		boolean useBuiltInMap = builtInRadioButton.isSelected();
		builtInMapComboBox.setEnabled(useBuiltInMap);
		loadMapFileButton.setEnabled(!useBuiltInMap);
	}

	private void refreshBuiltInMapOptions() {
		refreshingMapOptions = true;
		try {
			builtInMapComboBox.removeAllItems();
			for (MapSelectionOption mapOption : mapTemplates.getDefaultMapIds()) {
				builtInMapComboBox.addItem(mapOption);
			}
			if (builtInMapComboBox.getItemCount() > 0) {
				builtInMapComboBox.setSelectedIndex(0);
			}
		} finally {
			refreshingMapOptions = false;
		}

		if (builtInRadioButton.isSelected()) {
			selectBuiltInMap();
		}
	}

	private void selectBuiltInMap() {
		if (refreshingMapOptions || !builtInRadioButton.isSelected()) {
			return;
		}

		MapSelectionOption selectedOption =
				(MapSelectionOption) builtInMapComboBox.getSelectedItem();
		if (selectedOption == null) {
			clearSelectedMap();
			return;
		}

		mapTemplates.getMapById(selectedOption.mapId())
				.ifPresentOrElse(
						this::selectMapTemplate,
						() -> log.error("No built-in map exists with id: {}", selectedOption.mapId()));
	}

	private void chooseMapFile() {
		JFileChooser fileChooser = createMapFileChooser();
		int selectedOption = fileChooser.showOpenDialog(this);
		if (selectedOption != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path selectedPath = fileChooser.getSelectedFile().toPath();
		MapTemplate loadedMapTemplate = mapFileLoader.loadMapTemplateFromFile(selectedPath);
		selectMapTemplate(loadedMapTemplate);
	}

	private void selectMapTemplate(MapTemplate mapTemplate) {
		setupState.setSelectedMapTemplate(mapTemplate);
		MapSelectionChangedEvent event = new MapSelectionChangedEvent(
				setupState.getSelectedMapTemplateMaybe());
		eventPublisher.publishEvent(event);
	}

	private void clearSelectedMap() {
		setupState.clearSelectedMap();
		MapSelectionChangedEvent event = new MapSelectionChangedEvent(
				setupState.getSelectedMapTemplateMaybe());
		eventPublisher.publishEvent(event);
	}

	private JFileChooser createMapFileChooser() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Load Map File");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setAcceptAllFileFilterUsed(false);
		FileNameExtensionFilter jsonFilter = new FileNameExtensionFilter("JSON map files (*.json)", "json");
		fileChooser.setFileFilter(jsonFilter);
		return fileChooser;
	}

	private static class MapSelectionOptionRenderer extends DefaultListCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getListCellRendererComponent(
				JList<?> list,
				Object value,
				int index,
				boolean isSelected,
				boolean cellHasFocus) {
			Component component = super.getListCellRendererComponent(
					list, value, index, isSelected, cellHasFocus);
			if (value instanceof MapSelectionOption mapOption) {
				setText(mapOption.mapName());
			}
			return component;
		}
	}
}

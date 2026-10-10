# Description
This epic will add all of the widgets for setting up a game in the setup screen.

## Map Select Subpanel
- GridBagLayout 2 cells wide, and 2 cells high
    - Cell 0,0 Has a GameRadioButton. Text is "Built-in". 
        - Belongs to button group called MapSource.
        - Selected by default.
        - When switching from From File to Built-In, SetupState's selected map template should use the selection the JComboBox, or cleared otherwise.
        - Uses style Imperial_Selection from RadioButtonStyles.
    - Cell 0,1 has a GameRadioButton. Text is "From File"
        - Belongs to button group called MapSource
        - Not selected by default
        - When switching from Built-in to From File, SetupState's selected map template should immediately be cleared until a file is successfully loaded.
        - Uses style Imperial_Selection from RadioButtonStyles.
    - Cell 1, 0 has a GameTextComboBox selector widget. 
        - Style is Imperial Menu from ComboBoxStyles
        - Populated with values from MapTemplates.getDefaultMapIds(). 
            - Only the Name field from the MapSelectionOption is used to display in the drop-down.
                - Use a list-cell renderer so only MapSelectionOption.mapName() appears.
            - The UUID is used to fetch the map data from the MapTemplates.getMapById() when a selection is made.
        - Is enabled only if "Built-in" radio option is selected.
            - Disabled otherwise.
        - If a map template is selected from the combo box:
            - sent to SetupState.setSelectedMapTemplate();
    - Cell 1,1 Has a GameTextButton.
        - Text is "Load Map File"
        - Styling is IMPERIAL_ACTION from Button styles.
        - Enabled only if "From File" radio button is selected. 
            - Disabled otherwise.
        - When clicked, opens a file selector dialog box.
            - Selects one file only.
            - File filter shows JSON files only.
            - When the file is selected, the file is sent to the MapFileLoader service.
                - The MapTemplate that is loaded is sent to SetupState.setSelectedMapTemplate();
            - If no file is selected, then no change to the SetupState occurs.

## Map Options Subpanel
- If no map is currently selected at all, disable the tileset selector, settings radios, and Forces panels until a map becomes available.
    - Remember that the forces panel settings being enabled also depends on the "Use Presets" vs "Custom Settings" options.
- GridBag Layout
    - Cell 0,0 has a GameTextLabel.
        - Text is "Preferred Tileset"
        - Style is Imperial_Information, defined in LabelStyles.
    - Cell 0,1 has a GameTextComboBox
        - Style is Imperial Menu from ComboBoxStyles
        - Loaded with values from Tilset's values, using displayNames.
        - When a new map is selected, selected value defaults to the defaultTileset of the MapTemplate that is loaded.  
    - Cell 1,0 Has a GameRadioButton
        - Text is "Use Presets"
        - Selected by default
        - Belongs to button group settings_type
        - Uses style Imperial_Selection from RadioButtonStyles.
    - Cell 1,1 has a GameRadioButton
        - Text is "Custom Settings"
        - Not Selected by default
        - Belongs to button group settings_type
        - Uses style Imperial_Selection from RadioButtonStyles.
    - Cell 2,0 has a separate "Forces Options" Subpanel for Player 1
    - Cell 2,1 has a separate "Forces Options" Subpanel for Player 2
    
### Forces Subpanel
- If "Use Presets" is selected from radio group settings_type, then forces panel widgets are populated, but not changeable.
    - If radio button from settings_type is changed from Custom Settings to Use Presets, then populate each Forces subpanel with the defaults from the Map Template.
- If "Custom Settings" is selected from radio group settings_type, then forces panel widgets are changeable.
- Gridbag layout
    - Cell 0,0 has a GameTextLabel.
        - Text is "Faction"
        - Style is Imperial_Information from LabelStyles.
    - Cell 0,1 has a GameTextComboBox
        - Style is Imperial Menu from ComboBoxStyles
        - Values populated from Faction enum, using displayName for each value.
    - Cell 1,0 has a GameTextLabel.
        - Text is "Deployment Type"
        - Style is Imperial_Information from LabelStyles.
    - Cell 1,1 has a GameTextComboBox
        - Style is Imperial Menu from ComboBoxStyles
        - Values populated from DeploymentType enum, using displayName for each value.
    - Cell 2,0 has a GameTextLabel.
        - Text is "Map Side"
        - Style is Imperial_Information from LabelStyles.
    - Cell 2,1 has a GameTextComboBox
        - Style is Imperial Menu from ComboBoxStyles
        - Values populated from MapSide enum, using displayName for each value.      
    - Cell 3,0 has a GameTextLabel.
        - Text is "Win Condition"
        - Style is Imperial_Information from LabelStyles.
    - Cell 3,1 has a GameTextComboBox
        - Style is Imperial Menu from ComboBoxStyles
        - Values populated from Win Condition enum, using displayName for each value.   
        
## Player Options Subpanel
- GridBag layout
    - Cell 0,0 contains a PlayerSettings sub panel for Player one.
    - Cell 1,0 contains a PlayerSettings sub panel for Player two

### Player Settings Subpanel
- GridBag Layout
    - Cell 0,0 Contains a GameRadioButton
        - Text "Human"
        - Style Imperial_Selection
        - Belongs to radio button group Player_Controller
        - Selected by default
    - Cell 1,0 Contains a GameRadioButton
        - Text "Computer"
        - Style Imperial_Selection
        - Belongs to radio button group Player_Controller
        - Not Selected by default
    - Cell 0,1 Contains a GameTextLabel
        - Text: "CPU Strategy"
        - Style Imperial Information
    - Cell 1,1 Contains a GameTextComboBox
        - Style is Imperial Menu from ComboBoxStyles
        - Values populated from CPuStrategy enum, using displayName for each value.     
            - Is Enabled only if "Computer" radio button option is selected from Player_controller group.
            - Is disabled if "Human" radio button is selected.
            - Default value is Aggressive.
    - The Player_Controller button group is independent for each Player Settings subpanel.
    - For now, both players default to human.
    
## Map Preview Subpanel
- Override paintComponent() to draw map preview onto its surface.
    - MapRendererUtil.renderMapPreview() will return the preview image given the MapTemplate and other data to be specified later.
    - You may cache the preview in a BufferedImage field instead of calling the utility every frame to make a new image.
        - Make a new image when a MapSelectionChangedEvent is received.

## Additionally:
- If there IS at least one default map loaded by the data loader:
    - The SetupState should be set to have the selected map template set to the first entry in the list of map templates.
- Since we use the Data loader to load default map templates, populate the JComboFox for Built-In map template selection on receive of the assets from the dataloader.
- Imperial_Information Label style should be similar to Radio Button's Imperial_Selection style.
- When a new Map is selected or loaded, a MapSelectionChangedEvent should be transmitted AFTER the map selection is changed in the SetupState.
- When the map is cleared completely, also send a MapSElectionChangedEvent, with the MapTemplate optional set to an empty optional. 
- Panels that react to Map Selections being changed, for instance to populate defaults, should listen for MapSelectionChangedEvent.

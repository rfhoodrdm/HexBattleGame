# Description
We will add the skeletons of the remaining screens, along with relevant screen transitions to the game's user interface. 
- Outline of Setup screen
- Outline of Skirmish screen

We will add an additional menu and menu item.
- New "Main" menu item: Return to Title
    - This should be 2nd in the list, with "New Game" above it, and "Exit" below.
    
## Menu Behavior
- On the Title screen OR the Credits Screen:
    - New game -> Transition to Setup Screen.
    - Return to Title -> does nothing
    - Exit -> Closes the application
    
- On the Setup Screen:
    - New game -> does nothing.
    - Return to Title -> transitions to title screen.
    - Exit -> Closes the application.
    
- On the Skirmish screen:
    - For each option, we show a dialog questioning if the user wants to quit the current game.
        - If the answer is no, disregard the menu selection.
        - If the answer is yes:
            - New Game -> Transition to Setup Screen.
            - Return to Title -> Transition to Title Screen.
            - Exit -> Closes the application.


## Setup Screen
- Made from a JPanel
- Background should be SetupScreen.png
    - Fill/Cover the image in the Frame.
    - Draw the image onto the Panel's background by Overriding paintComponent(Graphics g);
    - Have a field of type BufferedImage and leverage the dataloader service to fetch the image on startup.
- Panel is split into several sections.
    - Left side has 2 sub-sections, arranged vertically.
        - Top portion is the Map Select subpanel.
            - A JPanel with a titled border, with the title of "Map Select"
            - Background color is PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR
        - Bottom portion is the Map preview subpanel.
            - A JPanel with a titled border, with the title of "Map Preview"
            - Square in proportions. 
            - Background color is PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR
        - Give both sub-panels a little padding, but I think the left section should favor the map preview with whatever is left of the space.
    - Right side has 3 sub-sections, arranged vertically.
        - Top portion is the Map Options subpanel. 
            - A Jpanel with a titled Border, with the title of "Map Options"
            - Background color is PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR
        - Middle portion is the Player Options subpanel.
            - A Jpanel with a titled Border, with the title of "Player Options"
            - Background color is PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR
        - Bottom portion is the StartControlSubpanel
            - A Jpanel. No titled border, unlike the other subpanels.
            - Background color is PanelStyles.BACKGROUND_TRANSLUCENT_PANEL_COLOR
            - Has two buttons: "Start Game" and "Cancel"
                - "Start Game" will transition to the Skirmish screen.
                    - There will eventually be a capture of information from this screen to construct the game state to use on the skirmish screen.
                        - That will come in another epic, however.
                - "Cancel" will transition back to the Title Screen.
                - Use the Imperial Option button style for these buttons.
         - For spacing and layout
            - Spare vertical space can be split between the Map Options and Player options subpanels.
    - If the sub-panels in this screen require styling options for the borders, put the constants into the PanelStyles class.
    - The left side should favored with any leftover spare in the layout, horizontally speaking.
    - Give the sections and sub-panels a little bit of padding, so they don't look crammed in.
            

## Skirmish screen
- Made from a JPanel
- Background should be SkirmishScreen.png
    - Fill/Cover the image in the Frame.
    - Draw the image onto the Panel's background by Overriding paintComponent(Graphics g);
    - Have a field of type BufferedImage and leverage the dataloader service to fetch the image on startup.
    
    
## Additionally:
- On the Title screen:
    - The New Game button should transition to the Setup screen.
- On the Skirmish screen:
    - If the user attempts to close the application by clicking the X button on the frame:
        - Show the same pop-up dialog mentioned earlier, asking if the user truly wants to quit the game
            - If the user clicks No, then disregard.
            - If the user clicks yes, then exit the application.
- We can keep track of which is the currently active screen being shown in the AppState object, if it's needed.
    - This can be injected into whichever components that need to reference it.
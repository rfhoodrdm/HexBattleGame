# Description
In this epic, we will introduce switching between screens of the game.

## Important points:
- All actual Swing screen changes should be made on the event dispatch thread.
- Title screen should be the default screen shown on startup.
    - TITLE_AND_CREDITS should be the corresponding sound track sequence played.
    - Request this through the screen navigator for consistency. 
    

## Screens Names
- There are several screens we use for the user interface of the game.
    - Title: Splash screen shown on game startup.
    - Setup: Selecting options for the next game to be played.
    - Skirmish: The screen showing the game board and action options.
    - Credits:  Shows info on the creators and collaborators for this game.
- Defined in the ScreenName enum.

## New screen: Credits
- Made from a JPanel
- Should fit entirely inside of the Game Window, taking up the entire space
- Background should be CreditsScreen.png
    - Fill/Cover the image in the Frame.
    - Draw the image onto the Panel's background by Overriding paintComponent(Graphics g);
    - Have a field of type BufferedImage and leverage the dataloader service to fetch the image on startup.
- Has one button, "Back"
    - Centered horizontally.
    - Along the bottom of the screen, though not directly adjacent to it; give it a tiny bit of padding.
    - Use TextButton as the class.
    - Imperial Option is the Button style to use.
    - Back button transitions to Title Screen.
- Has a Panel that dominates the screen, with a modest amount of padding between it and the edges of the screen.
    - Type CreditsTranslucentPanel.
    - Instead of setting an opaque background using a color with an alpha channel, have the panel paint its own translucent color inside of paintComponent().
        - Background should be black with an alpha value giving about 60% opacity.
            - Define the color as a field inside of CreditsTranslucentPanel so it can be found and modified easily.
    - Inside the panel sits a GameTextArea, which takes up the entire space of the panel, with a bit of padding around it.
        - Text to display in the text area comes from CreditsConfig class, field creditsText.
        - The text area should be non-editable, non-focusable.
        - Should have line wrapping enabled and word wrapping enabled.
        - Text starts at the top of the area. Left-aligned.
        - Define a new TextAreaStyle in TextAreaStyles.
            - Named Imperial_Decree
            - Should be relatively light and be themed appropriately for a sword-and-sorcery style game.
    
    
## New transition from Title Screen
- "Credits" button should transition to Credits screen.

## Transition mechanism:
- Each panel that can request a screen transition has a reference to ScreenNavigator.
- ScreenNavigator service constructs the actual request to transition to a new screen and publishes it.
- ScreenManagerService receives the request.
    - Notifies Gui to show the screen in question via gui.showGameScreen()
        - If there is a request for an unregistered screen, then log an error.
    - Notifies SoundManager to start playing the sound track sequence appropriate to the screen. (See below)

### Also:
- We should signal the SoundManager to start playing the music appropriate to the given screen when we transition screens.
    - Done by invoking soundManager.playSoundTrackSequence() and passing the appropriate sequence to play.
        - sequences are listed in the SoundTrackSequence 
        - For Title screen and Credit Screen -> TITLE_AND_CREDITS
        - For Setup screen -> SETUP
        - For Skirmish screen -> SKIRMISH
        - This mapping should live in the ScreenManagerService

## New organization
- Use a CardLayout to hold the various game screen panels.
    - This CardLayout lives in SwingGui.
- Register each panel using its corresponding ScreenName.


# Acceptance criteria:
  1. Game startup displays Title and begins its soundtrack.
  2. Selecting Credits displays Credits without opening another window.
  3. Selecting Back restores Title.
  4. Title ↔ Credits does not restart the shared soundtrack.
  5. All transitions happen through ScreenNavigator.
  6. The background and Back button remain correctly positioned after resizing.
  7. Missing required assets prevent startup consistently with the existing loader behavior.

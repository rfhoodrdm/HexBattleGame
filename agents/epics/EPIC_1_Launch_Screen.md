# Description
This document describes the first major epic for the game. Goals:
- Create a game window
- Show the title screen

We will be using the Java Swing library for this game.

## Game Window
The window should be a JFrame.
- Max windowed size by default.
- Close application on close of frame.
- Title of Window Frame should be <game name> then a space, then <version number>
    - These values can be read from the GameConfig class.

### Menu 
- One menu for now. Name is "Main"
    - Options of New Game, Exit.
        - Exit should close the application.
        - New Game will eventually cause a transition to the setup screen, but we'll cover that later.

## Title Screen
- Made from a JPanel
- Should fit entirely inside of the Game Window, taking up the entire space
- Background should be SplashScreen.png
    - Fill/Cover the image in the Frame.
    - Draw the image onto the Panel's background by Overriding paintComponent(Graphics g);
    - Have a field of type BufferedImage and leverage the dataloader service to fetch the image on startup.
- Has two buttons, "New Game", "Credits", and "Exit"
    - Centered horizontally.
    - Towards the bottom, but not directly against the bottom. Maybe starting at around 65% down the screen's height.
    - Use the TextButton as the base class, and specify a ButtonStyle from the ButtonStyles library class.
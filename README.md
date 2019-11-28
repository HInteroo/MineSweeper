# MineSweeper
A MineSweeper Game in Java Using GUI (Swing)


![Image description](https://github.com/HInteroo/MineSweeper/blob/master/MineSweeper.png)

# About:

About MineSweeper Wikipedia and how to play via Wikipedia (https://en.wikipedia.org/wiki/Minesweeper_(video_game)):
> Minesweeper is a single-player puzzle computer game. The objective of the game is to clear a rectangular board containing hidden "mines" or bombs without detonating any of them, with help from clues about the number of neighboring mines in each field. The game originates from the 1960s, and has been written for many computing platforms in use today.

# Description of each Methods:

#### MineSweeper();
 
 - Called from MineSweeper's Class main method that creates a Graphical User Interface (GUI) making sure that the game itself can be playable.
 

#### Class GamePanel;

 - The GamePanel Class creates a JPanel for the whole entire game itself. GamePanel holds the functionalities needed to play the game, creates Tiles/buttons that holds the mines, Tiles with numbers and empty safe tiles.
 
#### GamePanel();
 
 - The GamePanel() method creates the layout of each individual panels and calls other methods to create tiles for the game.
 - The main panel is called MainPanel with a GridLayout of 1 column and 2 rows.
 - The 2 rows consist of the QuitBtnPanel with the QUit Button enabled so that the user can quit the game and TilePanel where the buttons with the mines, the clues and empty mines are defined in.
 - TilePanel is made using the CreateTiles(int x,int y) method. X being number of columns and y being the number of rows.
 - Calls CreateMines() which creates the Mines in TilePanel using a specific arraylist.
 - CreateNumTiles() is also called on GamePanel which creates the clues around the mines. Clues are in numbers and this number gives the users a clue of how many mines sorrounds that tile/button.
 
#### disconnect();
 
 - disconnect() : Closes the GUI
 
#### NewGame();
			
 - Not done yet (11/28/19)
 - Would like to give it a New Game button allowing the user to restart the game with different tiles

#### CreateTiles(int x,int y);
			
 - x = Columns
 - y = Rows
 - Side-Note : Should be fixed meaning X and Y should be nothing but 9. Reason why it's not is because i'd like to give it a "Difficulty" button allowing the user to change the difficulty. The more difficult the more mines, Columns and Rows there is.
 - TilesPanel is defined here and is added toe the main Panel.
 - I set the preferred size of the buttons here: Dimension(40, 40));
 - I add ActionListener(this) to all the Tile Buttons here.
 - I setMargin( new Insets(5, 5, 5, 5) ) because i wanted the font of the text inside the buttons to not be "...".
 - i setBackground(Color.white) to all Tile Buttons here.
 - I setOpaque(true) to allow the setBackground(Color.White) to take place. 
 - I give each Tiles a specific name, sort of like an ID (TilesBtns[xaxis][yaxis].setName("("+xaxis+","+yaxis+")")) reason behind it is to know which buttons were clicked via the actionPerformed(ActionEvent e) method. The names of each buttons are their coordinates like so: "(0,0)"...
 
					TilePanel.add(TilesBtns[xaxis][yaxis],BorderLayout.CENTER);

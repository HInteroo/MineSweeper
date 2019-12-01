# MineSweeper
A MineSweeper Game in Java Using GUI (Swing)


![Image description](https://github.com/HInteroo/MineSweeper/blob/master/MineSweeper.png)

# About:

About MineSweeper Wikipedia and how to play via Wikipedia (https://en.wikipedia.org/wiki/Minesweeper_(video_game)):
> Minesweeper is a single-player puzzle computer game. The objective of the game is to clear a rectangular board containing hidden "mines" or bombs without detonating any of them, with help from clues about the number of neighboring mines in each field. The game originates from the 1960s, and has been written for many computing platforms in use today.

# Work In Progress:

#### NewGame();
			
 - Would like to give it a New Game button allowing the user to restart the game with different tiles
 - Stopping users from clicking on buttons after a gameover
 
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
 
#### CreateMines();
 - I create the 14 mines here by putting the coordinates into an arrayList called Coords as Strings. Coords is needed to determine where i would be setting the Mine icon to which Tiles
 
#### CreateNumTiles();

 - Looks on the Tiles sorrounding the mines, there would atleast be 8 TIles sorrounding a mine therefore 8 if-statements are needed in case the -1< x-coordinates < 10 or -1 < y-coordinate < 10. 
 
 - Calls checkMines(int xCoord, int yCoord) to store in the number of mines a tile is sorrounding with by using HashMap/Map. Since Hashmaps's Keys are unique, it saves me the trouble of repeated Xcoordinates and yCoordinates
 
#### checkMines(int xCoord, int yCoord);
 - tilesAroundMines is a HashMap : <xCoordinate, Map<yCoordinate, Value> -> (x,y) : Value
 - if a mine is sorrounded by another mine, i don't give that mine a value. Nothing happens.
 - if the mine is sorrounded by a non-mine than i store the x-coordinate and y-Coordinate to tilesAroundMines.
 - The value of (x,y), if it's the first time, i give it a value of 1 -> 1 mine sorrounds (x,y)
 - if (x,y) passes through checkMines(x,y) than i increment its Value of 1. 
 
#### actionPerformed(ActionEvent e);

 - for every buttons i have defined, i gave it an addActionListener(this); and whenever its a button is clicked, it passes through actionPerformed(ActionEvent e);
 - i make sure whether or not its the QUit button being clicked or the tile buttons being clicked.
 - if its the tile buttons, i grab it's name ("(0,0)","(0,1)",...,"(9,9)") etc..
 - I parse the string to integer 
 - calls in checkClickedTiles(xAxis,yAxis);
 - checkClickedTiles(xAxis,yAxis) checks if the button is a mine, a clue or empty tile.

#### checkClickedTiles(int x,int y);

 - Checks whether or not the clicked button is a mine by checking if the x and y are inside the arrayList Coords
 - Coords has the Coordinates of every mines creates.
 - If its not a mine, then it might be a clue or an empty tile.
 

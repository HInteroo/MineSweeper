import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@SuppressWarnings("serial")

public class MineSweeper extends JFrame {
	public MineSweeper() {
		GamePanel gamePanel = new GamePanel();
		add(gamePanel);
	}

	public static class GamePanel extends JPanel implements ActionListener {
		private Map<Integer, Map<Integer, Integer>> tilesAroundMines = new HashMap<Integer, Map<Integer, Integer>>();
		private Map <Integer, Integer> secondMap = new HashMap <Integer, Integer>();
		private JButton[] jbtns = new JButton[1];			//Array that only contains the Quit Button. Might give it a difficulty button soon (Easy, Hard, Super Duper Hard)
		private JButton[][] TilesBtns; 						//TilesBtns: are the buttons that contains clues, empty tiles or mines.
		private ImageIcon mine; 							//Icon image for mines
		private ArrayList<String> Coords;					//ArrayList, in it contains the name of each mines in string (14 mines) like {"(1,1)","(2,1)","(9,9)",...}
		private JPanel TilePanel = new JPanel();			//A panel that contains the JButtons of each tiles
		private boolean blankTile = false;
		private int[][] eightNeighbor = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {1, 0}, 
    		{1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
//		private int[][] eightNeighbor = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        		
		public GamePanel() {				//Creating the main Panel that holds both TilePanel and QuitBtnPanel
			setLayout(new BorderLayout());
			
			mine = new ImageIcon(getClass().getResource("Mine.png"));
			Image image = mine.getImage();
			Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			mine = new ImageIcon(newimg);  												 // transform it back
			
			JPanel MainPanel = new JPanel();
			MainPanel.setLayout(new GridLayout(2, 1));
		
			jbtns[0] = new JButton("Quit");
			jbtns[0].addActionListener(this);
			
			JPanel QuitBtnPanel = new JPanel();
			QuitBtnPanel.setLayout(new GridLayout(1, 1));
			QuitBtnPanel.add(jbtns[0],BorderLayout.CENTER);
			MainPanel.add(QuitBtnPanel, BorderLayout.SOUTH);
			add(MainPanel,BorderLayout.SOUTH);
			
			CreateTiles(10,10); //creates Tiles to TilePanel
			createMines();  	//Creates the 14 mines 
			createClues();	    //Creates the Clues surrounding the mines
		}
	
		public void CreateTiles(int x,int y) {			//Creates the buttons inside TilePanel
			TilePanel.setLayout(new GridLayout(x,y));	//Setting the layout by 9 columns and 9 rows
			TilesBtns = new JButton[x][y];				//an arraylist of JButtons
			
			for(int xaxis=0; xaxis<x; xaxis++){
				for(int yaxis=0; yaxis<y; yaxis++){
					TilesBtns[xaxis][yaxis] = new JButton("");
					TilesBtns[xaxis][yaxis].setPreferredSize(new Dimension(40, 40));
					TilesBtns[xaxis][yaxis].addActionListener(this);
					TilesBtns[xaxis][yaxis].setMargin( new Insets(5, 5, 5, 5) );
					TilesBtns[xaxis][yaxis].setBackground(Color.white);
					TilesBtns[xaxis][yaxis].setOpaque(true);
					TilesBtns[xaxis][yaxis].setName("("+xaxis+","+yaxis+")");
					TilePanel.add(TilesBtns[xaxis][yaxis],BorderLayout.CENTER);
				}
			}
			add(TilePanel,BorderLayout.CENTER);
		}
		
		public void createMines() { //Creating 14 mines at different location using random()
	        final Random r = new Random();
	        Coords = new ArrayList<String>(0);
	        do {
	            int xnum = r.nextInt(9);
	            int ynum = r.nextInt(9);
            	if(!Coords.contains("("+xnum+","+ynum+")")){
            		Coords.add("("+xnum+","+ynum+")");
            	}
	        }while(Coords.size()!=14);
		}
		
		public void checkSorroundings(int xCoord, int yCoord) {
			if(!Coords.contains("("+xCoord+","+yCoord+")")) {				//Coords is a map: ((x,y) -> Value) or in map: <X<Y,Value>>
			    if (!tilesAroundMines.containsKey(xCoord)) { 				//if x isn't repeated, put it inside the map 
			    	tilesAroundMines.put(xCoord, null);
			    	if(tilesAroundMines.get(xCoord) == null) {
				    	secondMap = new HashMap<Integer, Integer>();
				    	secondMap.put(yCoord,1);
				    	tilesAroundMines.put(xCoord, secondMap);
				    }
			    }
			    else { 														//if it does contains the key
			    	if(!tilesAroundMines.get(xCoord).containsKey(yCoord)) { //if 2 is not in [0:{ 0:1, 1:1}]
				    	tilesAroundMines.get(xCoord).put(yCoord,1);
				    }
				    else if(tilesAroundMines.get(xCoord).containsKey(yCoord)) { //works perfectly
				    	tilesAroundMines.get(xCoord).put(yCoord, tilesAroundMines.get(xCoord).get(yCoord)+1);
				    }
			    }
		    }
		}
		
		public void closeGame() {
			System.exit(1);
		}
	
		public void createClues() {

			int xCoord;
			int yCoord;
			for(String i: Coords) {
				xCoord = Integer.parseInt(i.substring(1, 2));
        		yCoord = Integer.parseInt(i.substring(3, 4));
            	for (int index = 0; index < eightNeighbor.length; index++) { 
            		if(-1<xCoord+eightNeighbor[index][0] && xCoord+eightNeighbor[index][0]<10 
            				&& yCoord+eightNeighbor[index][1] <10 && -1< yCoord+eightNeighbor[index][1]) {
    					checkSorroundings(xCoord +eightNeighbor[index][0],yCoord+eightNeighbor[index][1]);
            		}
            	}
			}
		}
		public void checkClickedTiles(int x,int y) {
			if(Coords.contains("("+x+","+y+")") == false && (tilesAroundMines.containsKey(x) 
					&& tilesAroundMines.get(x).containsKey(y) == false)){
    			TilesBtns[x][y].setBorderPainted(false);
				if (TilesBtns[x][y].isEnabled()) {
					blankTile = true;

	            	for (int index = 0; index < eightNeighbor.length; index++) { 
	            		if(-1<x+eightNeighbor[index][0] && x+eightNeighbor[index][0]<10 
	            				&& y+eightNeighbor[index][1] <10 && -1< y+eightNeighbor[index][1]) {
	            			checkClickedTiles((x +eightNeighbor[index][0]),(y+eightNeighbor[index][1]));
	            		}
	        			TilesBtns[x][y].setEnabled(false);
	            	}
					blankTile = true;

				}
//				blankTile = false;

				System.out.println(x +" , "+y);

			}
			else {
				if(tilesAroundMines.containsKey(x) && tilesAroundMines.get(x).containsKey(y) && blankTile == false) { 
					//if it has a mine around Tile (x,y);
        			System.out.println(3);

					TilesBtns[x][y].setText(""+tilesAroundMines.get(x).get(y)+"");
					TilesBtns[x][y].setFont(new Font("Arial", Font.BOLD, 15));
					TilesBtns[x][y].setBorderPainted(false);
				
				}
				
				else if(Coords.contains("("+x+","+y+")")) {	//if its a mine:
						System.out.println("Mine!");
						for (String MinesCoords: Coords) {
							TilesBtns[Integer.parseInt(MinesCoords.substring(1, 2))]
			        				[Integer.parseInt(MinesCoords.substring(3, 4))].setIcon(mine);
						}
						TilesBtns[x][y].setBorderPainted(false);
						
					}
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			if (btnClicked.equals(jbtns[0])) {
				closeGame();
			}
			else {
				String name = btnClicked.getName();
				int xAxis = Integer.parseInt(name.substring(1, 2));
				int yAxis = Integer.parseInt(name.substring(3, 4));
				checkClickedTiles(xAxis,yAxis);
			}
		}
	}
	
	public static void main(String[] args) {
		
		MineSweeper gui = new MineSweeper();
		gui.setSize(600, 600);
		gui.setVisible(true);
		gui.pack();
		
	}
}


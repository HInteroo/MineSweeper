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
import java.util.Random;


@SuppressWarnings("serial")

public class MineSweeper extends JFrame {
	public MineSweeper() {
		GamePanel gamePanel = new GamePanel();
		add(gamePanel);
	}

	public static class GamePanel extends JPanel implements ActionListener {
		private mapCoordinate clueArray = new mapCoordinate();
		private arrayCoordinate blankArray = new arrayCoordinate();
		private JButton[] jbtns = new JButton[1];			//Array that only contains the Quit Button. Might give it a difficulty button soon (Easy, Hard, Super Duper Hard)
		private JButton[][] TilesBtns; 						//TilesBtns: are the buttons that contains clues, empty tiles or mines.
		private ImageIcon mine; 							//Icon image for mines
		private arrayCoordinate mineArray = new arrayCoordinate();	//ArrayList, in it contains the name of each mines in string (14 mines) like {"(1,1)","(2,1)","(9,9)",...}
		private JPanel TilePanel = new JPanel();			//A panel that contains the JButtons of each tiles
		private boolean isBlankTile = false;
		private int[][] eightNeighbors = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {1, 0}, 
    		{1, 1}, {0, 1}, {-1, 1}, {-1, 0}};
		private int[][] fourNeighbors = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};

    		
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
			createMines();
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
					blankArray.put(xaxis, yaxis);
				}
			}
			add(TilePanel,BorderLayout.CENTER);
		}
		public void createMines() { //Creating 14 mines at different location using random()
	        final Random r = new Random();
	        do {
	            Integer xnum = r.nextInt(9);
	            Integer ynum = r.nextInt(9);
	        	if(!mineArray.contains(xnum,ynum)){
	        		mineArray.put(xnum,ynum);
	        		blankArray.remove(xnum, ynum);
	        	}
	        }while(mineArray.size()!=14);
		}
		

		
		public void closeGame() {
			System.exit(1);
		}
	
		public void createClues() {

			for(ArrayList<Integer> i: mineArray.getArray()) {
            	for (int index = 0; index < eightNeighbors.length; index++) { 
            		if(-1<i.get(0)+eightNeighbors[index][0] && i.get(0)+eightNeighbors[index][0]<10 
            				&& i.get(1)+eightNeighbors[index][1] <10 && -1< i.get(1)+eightNeighbors[index][1]) {
            			if(!mineArray.contains(i.get(0) +eightNeighbors[index][0],i.get(1)+eightNeighbors[index][1])) {
            				int xaxis = i.get(0) +eightNeighbors[index][0];
            				int yaxis = i.get(1)+eightNeighbors[index][1];
            				clueArray.checkPut(xaxis,yaxis);
            				blankArray.remove(xaxis, yaxis);

            			}
        			}
            	}
			}
			
		}
		
		public void checkClickedTiles(int x,int y) {
			if(blankArray.contains(x, y) ){
				System.out.println("its a blank");
				if (TilesBtns[x][y].isEnabled()) {
	            	for (int index = 0; index < fourNeighbors.length; index++) { 
	            		if(-1<x+fourNeighbors[index][0] && x+fourNeighbors[index][0]<10 
	            				&& y+fourNeighbors[index][1] <10 && -1< y+fourNeighbors[index][1]) {
	            			int xaxis = x +fourNeighbors[index][0];
	            			int yaxis = y+fourNeighbors[index][1];
	            			TilesBtns[x][y].setBorderPainted(false);
	            			checkClickedTiles(xaxis,yaxis);
	            		}
	        			TilesBtns[x][y].setEnabled(false);
	            	}
				}
			}
			else {
				if(clueArray.contains(x,y) && isBlankTile == false) { 
					//if it has a mine around Tile (x,y);
					TilesBtns[x][y].setText(""+clueArray.showMap().get(x).get(y)+"");
					TilesBtns[x][y].setFont(new Font("Arial", Font.BOLD, 15));
					TilesBtns[x][y].setBorderPainted(false);
				
				}
				
				else if(mineArray.contains(x,y)) {	//if its a mine:
						for(ArrayList<Integer> i: mineArray.getArray()) {
							TilesBtns[i.get(0)][i.get(1)].setIcon(mine);
							TilesBtns[i.get(0)][i.get(1)].setBorderPainted(false);
						}
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
				if(blankArray.contains(xAxis, yAxis)){
					isBlankTile = true;
				}
				else {
					isBlankTile = false;
				}
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


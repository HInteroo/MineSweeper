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
		private JButton[] jbtns = new JButton[1];								//Array that only contains the Quit Button. Might give it a difficulty button soon (Easy, Hard, Super Duper Hard)
		private JButton[][] TilesBtns = {}; 											//TilesBtns: are the buttons that contains clues, empty tiles or mines.
		private ImageIcon mine; 												//Icon image for mines
		private arrayCoordinate mineArray = new arrayCoordinate();				//ArrayList, in it contains the name of each mines in string (14 mines) like {"(1,1)","(2,1)","(12,12)",...}
		private JPanel TilePanel = new JPanel();								//A panel that contains the JButtons of each tiles
		private int xTilesLength;
		private int yTilesLength;
		private int[][] eightNeighbors = new int[][]{{-1, -1}, {0, -1}, {1, -1}, {1, 0}, 
    		{1, 1}, {0, 1}, {-1, 1}, {-1, 0}};

    		
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
			
			CreateTiles(12,15); //creates Tiles to TilePanel
			createMines();
			createClues();	    //Creates the Clues surrounding the mines
		}
	
		public void CreateTiles(int x,int y) {			//Creates the buttons inside TilePanel
			TilePanel.setLayout(new GridLayout(x,y));	//Setting the layout by x columns and y rows
			TilesBtns = new JButton[x][y];				//an arraylist of JButtons
			xTilesLength = TilesBtns.length;
			yTilesLength = TilesBtns[0].length;
			System.out.println(xTilesLength);

			System.out.println(yTilesLength);
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
		public void createMines() { //Creating 22 mines at different location using random()
	        final Random r = new Random();
	        do {
	            Integer xnum = r.nextInt(xTilesLength);
	            Integer ynum = r.nextInt(yTilesLength);
	        	if(!mineArray.contains(xnum,ynum)){
	        		mineArray.put(xnum,ynum);
	        		blankArray.remove(xnum, ynum);
	        	}
	        }while(mineArray.size()!=35);
		}
		
		public void createClues() {

			for(ArrayList<Integer> i: mineArray.getArray()) {
            	for (int index = 0; index < eightNeighbors.length; index++) { 
            		if(-1<i.get(0)+eightNeighbors[index][0] && i.get(0)+eightNeighbors[index][0]<yTilesLength 
            				&& i.get(1)+eightNeighbors[index][1] <yTilesLength && -1< i.get(1)+eightNeighbors[index][1]) {
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
	            	for (int index = 0; index < eightNeighbors.length; index++) { 
	            		if(-1<x+eightNeighbors[index][0] && x+eightNeighbors[index][0]<xTilesLength 
	            				&& y+eightNeighbors[index][1] <yTilesLength && -1< y+eightNeighbors[index][1]) {
	            			int xaxis = x +eightNeighbors[index][0];
	            			int yaxis = y+eightNeighbors[index][1];
	            			TilesBtns[x][y].setBorderPainted(false);
	            			checkClickedTiles(xaxis,yaxis);
	            		}
	        			TilesBtns[x][y].setEnabled(false);
	            	}
				}
			}
			else {
				if(clueArray.contains(x,y)) { 
					//if it has a mine around Tile (x,y);
					TilesBtns[x][y].setText(""+clueArray.showMap().get(x).get(y)+"");
					TilesBtns[x][y].setFont(new Font("Arial", Font.BOLD, 15));
					TilesBtns[x][y].setBorderPainted(false);
				
				}
				
				else if(mineArray.contains(x,y)) {	//if its a mine:
					GameOver();
					}
			}
		}
		

		public void GameOver() {
			for(ArrayList<Integer> i: mineArray.getArray()) {
				TilesBtns[i.get(0)][i.get(1)].setIcon(mine);
				TilesBtns[i.get(0)][i.get(1)].setDisabledIcon(mine);
				TilesBtns[i.get(0)][i.get(1)].setBorderPainted(false);
			}
			buttonSwitch(false);
		}
		
		public void buttonSwitch(boolean onoff) {
			for(int indexx = 0; indexx<xTilesLength; indexx++) {
				for(int indexy = 0; indexy<yTilesLength; indexy++) {
						TilesBtns[indexx][indexy].setEnabled(onoff);
				}
			}
		}
		public void closeGame() {
			System.exit(1);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			if (btnClicked.equals(jbtns[0])) {
				closeGame();
			}
			else {
				String name = btnClicked.getName();
				int comma = name.indexOf(",");
				int xAxis = Integer.parseInt(name.substring(1, comma));
				int yAxis = Integer.parseInt(name.substring(comma+1, name.length()-1));
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


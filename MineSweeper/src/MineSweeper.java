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
		Map <Integer, Integer> secondMap = new HashMap <Integer, Integer>();
		Map<Integer, Map<Integer, Integer>> tilesAroundMines = new HashMap<Integer, Map<Integer, Integer>>();
		 int i = 0;

		private String[] jbtnNames = { "Beginner","Intermediate","Expert", "Quit"};
		private JButton[] jbtns = new JButton[jbtnNames.length];
		private JButton[][] TilesBtns;
		private final int BTN_INDEX_Quit = 3;
		boolean done = false;
		
		ImageIcon mine;
		String[][] Coordinates;
		JPanel TilePanel = new JPanel();
		private ArrayList<String> Coords;
		
		public GamePanel() {
			setLayout(new BorderLayout());
			
			mine = new ImageIcon(getClass().getResource("Mine.png"));
			Image image = mine.getImage();
			Image newimg = image.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
			mine = new ImageIcon(newimg);  // transform it back
			
			JPanel MainPanel = new JPanel();
			MainPanel.setLayout(new GridLayout(2, 1));
		
			jbtns[BTN_INDEX_Quit] = new JButton(jbtnNames[BTN_INDEX_Quit]);
			jbtns[BTN_INDEX_Quit].addActionListener(this);
			JPanel QuitBtnPanel = new JPanel();
			QuitBtnPanel.setLayout(new GridLayout(1, 1));
			QuitBtnPanel.add(jbtns[BTN_INDEX_Quit],BorderLayout.CENTER);
			MainPanel.add(QuitBtnPanel, BorderLayout.SOUTH);
			add(MainPanel,BorderLayout.SOUTH);
			
			CreateTiles(10,10);
			CreateMines();
			CreateNumTiles();
		}
	
	
		public void disconnect() {
			done = true;
			System.exit(1);
		}
		public void NewGame() {
			
		}
		public void CreateTiles(int x,int y) {
			TilePanel.setLayout(new GridLayout(x,y));
			TilesBtns = new JButton[x][y];
			int xaxis=0;
			for(; xaxis<x; xaxis++){					//(x, y) axis
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
		
		public void CreateMines() {
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
		
		public void checkMines(int xCoord, int yCoord) {
			if(!Coords.contains("("+xCoord+","+yCoord+")")) {
			    if (!tilesAroundMines.containsKey(xCoord)) { // if 0 is not in []
			    	tilesAroundMines.put(xCoord, null);
			    	if(tilesAroundMines.get(xCoord) == null) {
				    	secondMap = new HashMap<Integer, Integer>();
				    	secondMap.put(yCoord,1);
				    	tilesAroundMines.put(xCoord, secondMap);
				    }
			    }
			    else { //if it does contains the key
			    	if(!tilesAroundMines.get(xCoord).containsKey(yCoord)) { //if 2 is not in [0:{ 0:1, 1:1}]
				    	tilesAroundMines.get(xCoord).put(yCoord,1);
				    }
				    else if(tilesAroundMines.get(xCoord).containsKey(yCoord)) { //works perfectly
				    	tilesAroundMines.get(xCoord).put(yCoord, tilesAroundMines.get(xCoord).get(yCoord)+1);
				    }
			    }
		    }
		}
		
		public void CreateNumTiles() {

			int xCoord;
			int yCoord;
			
			for(String i: Coords) {
				xCoord = Integer.parseInt(i.substring(1, 2));
        		yCoord = Integer.parseInt(i.substring(3, 4));

				if(-1<xCoord-1 && xCoord-1 <10) {
					checkMines(xCoord-1,yCoord);
				}
				if(-1<xCoord+1 && xCoord+1 <10) {
					checkMines(xCoord+1,yCoord);
				}
				if(-1<yCoord+1 && yCoord+1 <10 ) {
					checkMines(xCoord,yCoord+1);
				}
				if(-1<yCoord-1 && yCoord-1 <10) {
					checkMines(xCoord,yCoord-1);
				}
				if(-1<xCoord+1 && xCoord+1 <10 && -1<yCoord+1 && yCoord+1 <10) { //(7,6)
					checkMines(xCoord+1,yCoord+1);

				}
				if(-1<xCoord+1 && xCoord+1 <10 && -1<yCoord-1 && yCoord-1 <10) { 
					checkMines(xCoord+1,yCoord-1);

				}
				if(-1<xCoord-1 && xCoord-1 <10 && -1<yCoord+1 && yCoord+1 <10) {
					checkMines(xCoord-1,yCoord+1);

				}
				if(-1<xCoord-1 && xCoord-1 <10 && -1<yCoord-1 && yCoord-1 <10) {
					checkMines(xCoord-1,yCoord-1);

				}
			}
		}
		public void checkClickedTiles(int x,int y) {
			if(Coords.contains("("+x+","+y+")")) {
				System.out.println("Mine!");
				for (String MinesCoords: Coords) {
					TilesBtns[Integer.parseInt(MinesCoords.substring(1, 2))]
	        				[Integer.parseInt(MinesCoords.substring(3, 4))].setIcon(mine);
				}
			}
			else { 
					if(tilesAroundMines.containsKey(x)) {
						if(tilesAroundMines.get(x).containsKey(y)){
						TilesBtns[x][y].setText(""+tilesAroundMines.get(x).get(y)+"");
						TilesBtns[x][y].setFont(new Font("Arial", Font.BOLD, 15));
						TilesBtns[x][y].setBorderPainted(false);
						}
					}
					TilesBtns[x][y].setBorderPainted(false);
			}
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btnClicked = (JButton) e.getSource();
			if (btnClicked.equals(jbtns[BTN_INDEX_Quit])) {
				disconnect();
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


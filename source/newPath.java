import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;

import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.text.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class newPath {
	private static int[] counter = {0,0,0};
	private static int[][][] previous_moves = new int[3][9][2];
	private static int totalX = 0;
    private static int totalY = 0;
	private static int mode = 0;
	private static int previousMode = -2;
	private static int ticks=0;
	private static int initialization = 0;
	private static JTextPane jlabel = new JTextPane();
	
	private static JLabel title = new JLabel();
	private static boolean[] previousModes = new boolean[3];
	
	private static SpecialMoves[] specialMoves = {new SpecialMoves()};
	
	//timer pause
	private static boolean pause = false;
	private static boolean decision = true;

	//track program count
	private static int programNum = -1;

	//speed
	private static int[] currentSpeed = new int[3];
	private static int[] speedValues = {1,2,3,4};
	private static String[][] symbols = {{"Vt","Kt"},{"Mt","Bt"},{"Et","Nt"},{"Ft","St"}}; 
	private static Speed[] Speeds = {new Speed()};
	

	//merge
	private static int merge = 2;
	private static int mergeDelay = 80;
	private static int mergeTotalT = 10000;

	

	//keep track for tick counting
	private static int tickCounter = 0;

	//declare robots
	private static Robot[] RobotClass = {new Robot()};
    private static JButton[][] labelCellsRow ;
    private static JButton[][] labelCellsCol ;
	private static int labelgap =2;
	static Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    static int base = (size.height/32);
	private static Timer t ;


	//switch to variable mode
	private static boolean switchVarMode = false;
	private static JLabel legend = new JLabel();
	private static ArrayList<String[]> storeLegend = new ArrayList<String[]>();
	private static String name = "";

	//auto
	private static int[] autoMoveValues = {4,5};
	private static int automove_mode = 0;
	private static int autoMoveHeight =4;
	private static int autoMoveWide =5;
	private static Color[] colors ;

	//buttons
	private static JButton[][] buttonCells = new JButton[21][21];
	private static JButton[][] specialButtons = new JButton[23][23];
	
	//font
	private static int font = 19;
	private static int tutorial = 1;

	private  static int dimension_y=21;
	private  static int  dimension_x=21;
	private  int xShift = 0;
    private  int yShift = 0;
	private static boolean activeSpeed = true;
	private static boolean activeSymbol = true;
	JFrame jFrame;
	JScrollPane jsp;

	private static int length = 0;
	private static int width = 0;


	//returnboard
	private static int returnBoard = 0;

	public newPath (int tutorial1, int font1, int call,int l , int w){
		tutorial = 2;
		font = font1;
		returnBoard = call;
		length = l;
		width = w;
	}
	

	public  class BListener implements ActionListener{

			private int row;
			private int col;
			private JFrame jFrame;
			private JScrollPane jsp;

			public BListener (int row, int col, JFrame jFrame, JScrollPane jsp){
				super();
				this.row = row;
				this.col = col;
				this.jFrame = jFrame;
				this.jsp = jsp;
			}
			private void actionPerformed_org()
			{

				boolean gotoprevious = false;
				for(int i = 0; i < 4;i++){
					if(mode != -1 &&  previous_moves[mode][i][0]==row && previous_moves[mode][i][1] == col&&currentSpeed[mode]!=0){
						gotoprevious=true;
					}
				}

				if(mode != -1 && RobotClass[mode].storedMoves.size()==0 && currentSpeed[mode]!=0){
					gotoprevious=true;
				}


				if(gotoprevious){
					if(mode>=0 && checkSquare( row, col)){
						int index = 0;
						for(int i = 0; i < speedValues.length;i++){
							if(speedValues[i]==currentSpeed[mode]){
								index = i;
							}
						}

						for(int i =0; i< currentSpeed[mode];i++){
							RobotClass[mode].addMoves(row,col,index);
						}
					
						Speeds[mode].addSpeed(currentSpeed[mode],index);

						for(int i = 0; i < 4;i++){
							int rPrev = previous_moves[mode][i][0];
							int cPrev = previous_moves[mode][i][1];
							buttonCells[rPrev][cPrev].setText("");
							buttonCells[rPrev][cPrev].setIcon(null);
						}

						for(int i = 0; i < (dimension_y );i++){
							for(int j=0;j< (dimension_x);j++){
								for (ActionListener B : buttonCells[i][j].getActionListeners()){
									buttonCells[i][j].removeActionListener(B);
								}

								buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp));
							}
							
						}

						
						int r = previous_moves[mode][4][0];
					    int c = previous_moves[mode][4][1];
						deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame);
				

						r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
						c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

						previous_moves[mode][4][0] = r;
						previous_moves[mode][4][1] = c;

						if((initialization==2) && row == 10 && col == 10){
				
							initialization++;
						}
                        printRobot(buttonCells,1);
						for(int i = 0; i < (dimension_y + 2);i++){
							for(int j=0;j< (dimension_x + 2);j++){
								specialButtons[i][j].setText("");
								specialButtons[i][j].setIcon(null);
								jFrame.remove(specialButtons[i][j]);
							}
						}
						insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp);
						
						counter[mode]++;
					
						insertArrows (r,  c, buttonCells);
						}

					
				}
					jlabel.setText("");
                    autoPrint(jFrame);
	
		    }
			public void actionPerformed(ActionEvent e){
				if(programNum==0 && row == 10 && col == 10 && currentSpeed[mode] > 0 ){
					
				}
               
					actionPerformed_org();
				
					
			}

			
		} 

	class specialButtonListener implements ActionListener{

			private int t;
			private int i;
			private int j;
			private int board;
			private int numClass;
			private JButton[][] buttonCells;
			private JFrame jFrame;
			private JScrollPane jsp;

			public specialButtonListener (int t,int i,int j,int board,int numClass, JButton[][] buttonCells, JFrame jFrame, JScrollPane jsp){
				this.t=t;
				this.i=i;
				this.j=j;
				this.board=board;
				this.numClass=numClass;
				this.buttonCells = buttonCells;
				this.jFrame = jFrame;
				this.jsp = jsp;
			}
		
			public void actionPerformed(ActionEvent event) {

			
				if(numClass==1 && mergeFunc(i,j,jFrame,jsp)){
					if (merge == 0){
						buttonCells[i][j].setIcon(null);
						
						buttonCells[i][j].setText("");
						buttonCells[i][j].setIcon(new ImageIcon("1.png"));
						int cycleNum = (mergeTotalT)/(4*mergeDelay);
						specialMoves[mode].addSMove(t,i,j,board,1,cycleNum*4,mergeDelay);
					}

					if(merge == 1){
						buttonCells[i][j].setIcon(null);
						buttonCells[i][j].setText("");
						buttonCells[i][j].setIcon(new ImageIcon("2.png"));
						int cycleNum = (mergeTotalT)/(4*mergeDelay);
						specialMoves[mode].addSMove(t,i,j,board,2,cycleNum*4,mergeDelay);
					}

					if(merge == 2){
						buttonCells[i][j].setIcon(null);
						buttonCells[i][j].setText("");
						buttonCells[i][j].setIcon(new ImageIcon("3.png"));
						int cycleNum = (mergeTotalT)/(4*mergeDelay);
						specialMoves[mode].addSMove(t,i,j,board,3,cycleNum*4,mergeDelay);
					}

					if(merge == 3){
						buttonCells[i][j].setText("");
						buttonCells[i][j].setIcon(new ImageIcon("4.png"));
						int cycleNum = (mergeTotalT)/(4*mergeDelay);
						specialMoves[mode].addSMove(t,i,j,board,4,cycleNum*4,mergeDelay);
					}
					
					
				}
				
			}
		}

    public boolean mergeFunc(int i , int j,JFrame jFrame,JScrollPane jsp){

		boolean returnThing = true;
		if(merge != -1){
			int cycleNum = (mergeTotalT)/(4*mergeDelay);

			if(cycleNum == 0){
				returnThing = false;
			}

			int counting = 0;
			if (merge == 0) {
			
			   if( ((i>=0 && i<=(dimension_y -1)) && (j-1>=0&&j-1<=(dimension_x -1))) && ((i-1>=0 && i-1<=(dimension_y-1)) && (j-1>=0&&j-1<=(dimension_x-1))) && ((i-1>=0 && i-1<=(dimension_y-1)) && (j>=0&&j<=(dimension_x -1)))){
					for(int a = 0; a < cycleNum; a++){

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j-1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i-1,j-1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i-1,j,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j,-1);
						}
					}

					counting++;

				}
				
			}

			if (merge == 1) {
			
				if( ((i>=0 && i<=(dimension_y -1)) && (j+1>=0&&j+1<=(dimension_x -1))) && ((i-1>=0 && i-1<=(dimension_y -1)) && (j+1>=0&&j+1<=(dimension_x -1))) && ((i-1>=0 && i-1<=(dimension_y -1)) && (j>=0&&j<=(dimension_x-1)))){
				
					for(int a = 0; a < cycleNum; a++){
						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j+1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i-1,j+1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i-1,j,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j,-1);
						}
					}

					counting++;
					
				}

				
			}

			if (merge == 2) {
				if( ((i>=0 && i<=23) && (j+1>=0&&j+1<=23)) && ((i+1>=0 && i+1<=23) && (j+1>=0&&j+1<=23)) && ((i+1>=0 && i+1<=23) && (j>=0&&j<=23))){
					for(int a = 0; a < cycleNum; a++){
						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j+1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i+1,j+1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i+1,j,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j,-1);
						}
					}

					counting++;

					
				}
			}

			if (merge == 3) {
				
				if( ((i>=0 && i<=(dimension_y-1)) && (j-1>=0&&j-1<=(dimension_x -1))) && ((i+1>=0 && i+1<=(dimension_y-1)) && (j-1>=0&&j-1<=(dimension_x-1))) && ((i+1>=0 && i+1<=(dimension_y-1)) && (j>=0&&j<=(dimension_x-1)))){
					for(int a = 0; a < cycleNum; a++){
						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j-1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i+1,j-1,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i+1,j,-1);
						}

						Speeds[mode].addSpeed(mergeDelay, -1);
						for(int b = 0; b<mergeDelay;b++){
							RobotClass[mode].addMoves(i,j,-1);
						}
					}

					counting++;

					
				}
			}

			if(counting == 0){
				returnThing = false;
			}
			
		}

		
					

		for(int a = 0; a < 4;a++){
			int rPrev = previous_moves[mode][a][0];
			int cPrev = previous_moves[mode][a][1];
			buttonCells[rPrev][cPrev].setText("");
			buttonCells[rPrev][cPrev].setIcon(null);
		}

		for(int a = 0; a < (dimension_y );a++){
			for(int b=0;b<(dimension_x);b++){
				for (ActionListener B : buttonCells[a][b].getActionListeners()){
					buttonCells[a][b].removeActionListener(B);
				}
				buttonCells[a][b].addActionListener(new BListener(a,b,jFrame,jsp));
			}
			
		}

		for(int a = 0; a < (dimension_y  + 2);a++){
			for(int b=0;b<(dimension_x + 2);b++){
				for (ActionListener B : specialButtons[a][b].getActionListeners()){
					specialButtons[a][b].removeActionListener(B);
				}
			}
			
		}

		
		int r = previous_moves[mode][4][0];
		int c = previous_moves[mode][4][1];
		deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame);


		r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
		c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

		previous_moves[mode][4][0] = r;
		previous_moves[mode][4][1] = c;
		printRobot(buttonCells,1);
		


	
		for(int a= 0; a < (dimension_y  +2);a++){
			for(int b=0;b<(dimension_x  + 2);b++){
				specialButtons[i][j].setText("");
				specialButtons[i][j].setIcon(null);
				jFrame.remove(specialButtons[a][b]);
			}
		}
		insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp);
		
		counter[mode]++;
	
		insertArrows (r,  c, buttonCells);

		jlabel.setText("");
		

		autoPrint(jFrame);

		return returnThing;
	}
   
	 void insertSpecialButton(int r, int c, JButton[][] specialButtons, JButton[][] buttonCells,JFrame jFrame,JScrollPane jsp)
	    {
			deleteSpecialButton(r, c, specialButtons, buttonCells, jFrame);
			if(r==0 && c==0){
						specialButtons[0][0].setIcon(null);
						specialButtons[0][0].setIcon(new ImageIcon((merge+1)+".png"));
					
						specialButtons[0][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame,jsp));
						specialButtons[0][2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2,buttonCells,jFrame,jsp));
						buttonCells[r+1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,3, buttonCells,jFrame,jsp));
						specialButtons[2][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp));

						jFrame.add(specialButtons[0][0]);
					
						previous_moves[mode][5][0] = 0;
						previous_moves[mode][5][1] = 0;

						previous_moves[mode][6][0] = 0;
						previous_moves[mode][6][1] = 2;

						previous_moves[mode][7][0] = r+1;
						previous_moves[mode][7][1] = c+1;

						previous_moves[mode][8][0] = 2;
						previous_moves[mode][8][1] = 0;
						
					}

					else if(r==0 && c==(dimension_x-1)){
						specialButtons[0][(dimension_x-1)].setIcon(null);
						specialButtons[0][(dimension_x-1)].setIcon(new ImageIcon((merge+1)+".png"));
					
						specialButtons[0][(dimension_x-1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame, jsp));
						specialButtons[0][(dimension_x+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2, buttonCells,jFrame, jsp));
						specialButtons[2][(dimension_x+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame, jsp));
						buttonCells[r+1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,4, buttonCells,jFrame, jsp));

						jFrame.add(specialButtons[0][(dimension_x-1)]);

						previous_moves[mode][5][0] = 0;
						previous_moves[mode][5][1] = (dimension_x-1);

						previous_moves[mode][6][0] = 0;
						previous_moves[mode][6][1] = (dimension_x+1);

						previous_moves[mode][7][0] = 2;
						previous_moves[mode][7][1] = (dimension_x+1);

						previous_moves[mode][8][0] = r+1;
						previous_moves[mode][8][1] = c-1;
						
					}

					else if(r==(dimension_y-1) && c==0){
						specialButtons[(dimension_y-1)][0].setIcon(null);
						specialButtons[(dimension_y-1)][0].setIcon(new ImageIcon((merge+1)+".png"));
						
						specialButtons[(dimension_y-1)][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame,jsp));
						buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2, buttonCells,jFrame,jsp));
						specialButtons[(dimension_y+1)][2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp));
						specialButtons[(dimension_y+1)][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp));
						
						jFrame.add(specialButtons[(dimension_y-1)][0]);

						previous_moves[mode][5][0] = (dimension_y-1);
						previous_moves[mode][5][1] = 0;

						previous_moves[mode][6][0] = r-1;
						previous_moves[mode][6][1] = c+1;

						previous_moves[mode][7][0] = (dimension_y+1);
						previous_moves[mode][7][1] = 2;

						previous_moves[mode][8][0] = (dimension_y+1);
						previous_moves[mode][8][1] = 0;

					}

					else if(r==(dimension_y-1) && c==(dimension_x-1)){

						buttonCells[r-1][c-1].setIcon(null);
						buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));

						buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1, buttonCells,jFrame,jsp));
						specialButtons[(dimension_y-1)][(dimension_x+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2, buttonCells,jFrame,jsp));
						specialButtons[(dimension_y+1)][(dimension_x+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp));
						specialButtons[(dimension_y+1)][(dimension_x-1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp));

						previous_moves[mode][5][0] = r-1;
						previous_moves[mode][5][1] = c-1;

						previous_moves[mode][6][0] = (dimension_y-1);
						previous_moves[mode][6][1] = (dimension_x+1);

						previous_moves[mode][7][0] = (dimension_y+1);
						previous_moves[mode][7][1] = (dimension_x+1);

						previous_moves[mode][8][0] = (dimension_y+1);
						previous_moves[mode][8][1] = (dimension_x-1);

						
					}

					else if(r==0){
						specialButtons[r][c].setIcon(null);
						specialButtons[r][c].setIcon(new ImageIcon((merge+1)+".png"));

						specialButtons[r][c].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,2,1, buttonCells,jFrame,jsp));
						specialButtons[r][c+2].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,2,2, buttonCells,jFrame,jsp));
						buttonCells[r+1][c+1].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,1,3, buttonCells,jFrame,jsp));
						buttonCells[r+1][c-1].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,1,4, buttonCells,jFrame,jsp));

						jFrame.add(specialButtons[r][c]);
						

						previous_moves[mode][5][0] = r;
						previous_moves[mode][5][1] = c;

						previous_moves[mode][6][0] = r;
						previous_moves[mode][6][1] = c+2;

						previous_moves[mode][7][0] = r+1;
						previous_moves[mode][7][1] = c+1;

						previous_moves[mode][8][0] = r+1;
						previous_moves[mode][8][1] = c-1;
					}

					else if(c==0){
						specialButtons[r][c].setIcon(null);
						specialButtons[r][c].setIcon(new ImageIcon((merge+1)+".png"));

						specialButtons[r][c].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame,jsp));
						buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2, buttonCells,jFrame,jsp));
						buttonCells[r+1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,3, buttonCells,jFrame,jsp));
						specialButtons[r+2][c].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp));

						jFrame.add(specialButtons[r][c]);
						previous_moves[mode][5][0] = r;
						previous_moves[mode][5][1] = c;

						previous_moves[mode][6][0] = r-1;
						previous_moves[mode][6][1] = c+1;

						previous_moves[mode][7][0] = r+1;
						previous_moves[mode][7][1] = c+1;

						previous_moves[mode][8][0] = r+2;
						previous_moves[mode][8][1] = c;

					}

					else if(r==(dimension_y-1)){
						buttonCells[r-1][c-1].setIcon(null);
						buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));
						
						buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1, buttonCells,jFrame,jsp));
						buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2, buttonCells,jFrame,jsp));
						specialButtons[r+2][c+2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp));
						specialButtons[r+2][c].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp));


						previous_moves[mode][5][0] = r-1;
						previous_moves[mode][5][1] = c-1;

						previous_moves[mode][6][0] = r-1;
						previous_moves[mode][6][1] = c+1;

						previous_moves[mode][7][0] = r+2;
						previous_moves[mode][7][1] = c+2;

						previous_moves[mode][8][0] = r+2;
						previous_moves[mode][8][1] = c;
						
					}

					else if(c==(dimension_x-1)){
						buttonCells[r-1][c-1].setIcon(null);
						buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));
					

						buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1, buttonCells,jFrame,jsp));
						specialButtons[r][c+2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2, buttonCells,jFrame,jsp));
						specialButtons[r+2][c+2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp));
						buttonCells[r+1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,4, buttonCells,jFrame,jsp));

						previous_moves[mode][5][0] = r-1;
						previous_moves[mode][5][1] = c-1;

						previous_moves[mode][6][0] = r;
						previous_moves[mode][6][1] = c+2;

						previous_moves[mode][7][0] = r+2;
						previous_moves[mode][7][1] = c+2;

						previous_moves[mode][8][0] = r+1;
						previous_moves[mode][8][1] = c-1;
					}
					else{

						buttonCells[r-1][c-1].setIcon(null);
						buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));
					

						buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1,buttonCells,jFrame,jsp));
						buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2,buttonCells,jFrame,jsp));
						buttonCells[r+1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,3,buttonCells,jFrame,jsp));
						buttonCells[r+1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,4,buttonCells,jFrame,jsp));

						previous_moves[mode][5][0] =r-1;
						previous_moves[mode][5][1] =c-1;

						previous_moves[mode][6][0] =r-1;
						previous_moves[mode][6][1] =c+1;

						previous_moves[mode][7][0] =r+1;
						previous_moves[mode][7][1] =c+1;
						
						previous_moves[mode][8][0] =r+1;
						previous_moves[mode][8][1] =c-1;
						

					}
	}
	
	
    static void deleteSpecialButton (int r, int c, JButton[][] specialButtons, JButton[][] buttonCells,JFrame jFrame)
	{
		if(r==0 && c==0){
			
			
			specialButtons[0][0].setText("");
			
			specialButtons[0][0].setIcon(null);
			
			jFrame.remove(specialButtons[0][0]);
			jFrame.remove(specialButtons[0][2]);
			jFrame.remove(specialButtons[2][0]);
			
		}

		else if(r==0 && c==(dimension_x-1)){
			specialButtons[0][(dimension_x-1)].setText("");
		
			specialButtons[0][(dimension_x-1)].setIcon(null);
			
			jFrame.remove(specialButtons[0][(dimension_x-1)]);
			jFrame.remove(specialButtons[0][(dimension_x+1)]);
			jFrame.remove(specialButtons[2][(dimension_x-1)]);

			
			
		}

		else if(r==(dimension_y-1) && c==0){
			specialButtons[(dimension_y-1)][0].setText("");
			

			specialButtons[(dimension_y-1)][0].setIcon(null);
			
			jFrame.remove(specialButtons[(dimension_y-1)][0]);
			jFrame.remove(specialButtons[(dimension_y+1)][2]);
			jFrame.remove(specialButtons[(dimension_y+1)][0]);

		
			
		}

		else if(r==(dimension_y-1) && c==(dimension_x-1)){
			buttonCells[r-1][c-1].setText("");
			
			buttonCells[r-1][c-1].setIcon(null);
			
			
			jFrame.remove(specialButtons[(dimension_y-1)][(dimension_x+1)]);
			jFrame.remove(specialButtons[(dimension_y+1)][(dimension_x+1)]);
			jFrame.remove(specialButtons[(dimension_y+1)][(dimension_x-1)]);

			
		}

		else if(r==0){
			specialButtons[r][c].setText("");
			

			specialButtons[r][c].setIcon(null);
		

			jFrame.remove(specialButtons[r][c]);
			jFrame.remove(specialButtons[r][c+2]);
									
		}

		else if(c==0){
			specialButtons[r][c].setText("");
			

			specialButtons[r][c].setIcon(null);
			

			jFrame.remove(specialButtons[r][c]);
			jFrame.remove(specialButtons[r+2][c]);



		}

		else if(r==(dimension_y-1)){
			buttonCells[r-1][c-1].setText("");
			

			buttonCells[r-1][c-1].setIcon(null);
		
			jFrame.remove(specialButtons[r+2][c+2]);
			jFrame.remove(specialButtons[r+2][c]);

			
		}

		else if(c==(dimension_x-1)){
			buttonCells[r-1][c-1].setText("");
		
			buttonCells[r-1][c-1].setIcon(null);
			
			jFrame.remove(specialButtons[r][c+2]);
			jFrame.remove(specialButtons[r+2][c+2]);

		}

		else{
			buttonCells[r-1][c-1].setText("");			

			buttonCells[r-1][c-1].setIcon(null);
			
		} 
        
	}
	static void insertArrows (int r, int c, JButton[][] buttonCells)
    {


		if((r-1)>=0 && (dimension_y -1)>=(r-1) && (c)>=0&&(dimension_x -1)>=(c)){
			if(checkSquare(r-1,c)){
				buttonCells[r-1][c].setIcon(null);
				buttonCells[r-1][c].setText("\u2191");
				previous_moves[mode][0][0] = r-1;
				previous_moves[mode][0][1] = c;
				
			}
		}
	
		if((r)>=0 && (dimension_y -1)>=(r) && (c+1)>=0&&(dimension_x -1)>=(c+1)){
			if(checkSquare(r,c+1)){
				buttonCells[r][c+1].setIcon(null);
			buttonCells[r][c+1].setText("\u2192 ");
			previous_moves[mode][1][0] = r;
			previous_moves[mode][1][1] = c+1;
			}
		}
		if((r)>=0 && (dimension_y -1)>=(r) && (c-1)>=0&&(dimension_x -1)>=(c-1)){
			if(checkSquare(r,c-1)){
				buttonCells[r][c-1].setIcon(null);
				buttonCells[r][c-1].setText("\u2190");
				previous_moves[mode][2][0] = r;
				previous_moves[mode][2][1] = c-1;
			}
		}
		if((r+1)>=0 && (dimension_y -1)>=(r+1) && (c)>=0&&(dimension_x -1)>=(c)){
			if(checkSquare(r+1,c)){
				buttonCells[r+1][c].setIcon(null);
				buttonCells[r+1][c].setText("\u2193");
				previous_moves[mode][3][0] = r+1;
				previous_moves[mode][3][1] = c;
			}
		}

	}
	
	static void printRobot(JButton[][] buttonCells, int f)
	{
		int max = 0;
		for(int i = 0; i < RobotClass.length;i++){
			if(RobotClass[i].storedMoves.size()>max) max = RobotClass[i].storedMoves.size();
		}

		buttonCells[10][10].setText("X");
		for(int a = 1;a<= max;a++){
			for(int i = 0; i < RobotClass.length;i++){
				Robot x = RobotClass[i];
				if(x.storedMoves.size() >= a && a > Speeds[i].getSpeed(0)){
					int speedNum = 0;
					int total = 0;
					int index = -1;
					for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
						total += Speeds[i].getSpeed(j);
						if(total<=a){
							index = j;
							speedNum = Speeds[i].getNum(j);
						}
					}

					

					if (speedNum == 3 ){
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setBackground(Color.YELLOW);
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setOpaque(true);
						
					}

					else if (speedNum == 2){
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get( a-1)[1]].setBackground(new Color(56,202,109));
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setOpaque(true);
					}

					else if (speedNum == 1){
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get( a-1)[1]].setBackground(new Color(0,155,200));
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setOpaque(true);
					}

					else if(speedNum == 0 ){
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get( a-1)[1]].setBackground(Color.RED);
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setOpaque(true);

					}

					else{
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setBackground(Color.LIGHT_GRAY);
						buttonCells[x.storedMoves.get( a-1)[0]][x.storedMoves.get(a-1)[1]].setOpaque(true);
					}

				

					for(int d = 0; d < specialMoves[i].storedSMoves.size();d++){
						if((specialMoves[i].storedSMoves.get(d)[0])==index && index != -1){
						
							if(specialMoves[i].storedSMoves.get(d)[4]==1){
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("");
								
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(new ImageIcon("1.png"));
							}
							if(specialMoves[i].storedSMoves.get(d)[4]==2){
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("");
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(new ImageIcon("2.png"));
							}
							if(specialMoves[i].storedSMoves.get(d)[4]==3){
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("");
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(new ImageIcon("3.png"));
							}
							if(specialMoves[i].storedSMoves.get(d)[4]==4){
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("");
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(new ImageIcon("4.png"));
							}

							if(specialMoves[i].storedSMoves.get(d)[4]==5){
								
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(null);
							
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("Φ");
							}

							if(specialMoves[i].storedSMoves.get(d)[4]==6){
								
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(null);
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("Σ");
							}

							if(specialMoves[i].storedSMoves.get(d)[4]==7){
								
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setIcon(null);
								buttonCells[specialMoves[i].storedSMoves.get(d)[1]][specialMoves[i].storedSMoves.get(d)[2]].setText("ψ");
							}

						}
					}

		
			
				}

			}
		}

		
	}
	
	/*labeloutput */
	static void autoPrint(JFrame jFrame)
	{
		jlabel.setFont(new Font("Calibri", Font.PLAIN,font));
		
		if(labelOutput()!=null) {
			String[][] print = filterArray(labelOutput());
			
			String output ="" ;
			
			for(int i = 0; i < print.length;i++){
				String output1 = "" ;
				String out1 = "";

				for(int j = 0; j <print[i].length-1;j++){
					output1 += print[i][j] + ", ";
				}


				String out = "";
				if(Speeds[mode].getNum(i) != -1){
					String input1 = symbols[Speeds[mode].getNum(i)][0];
					String input2 = symbols[Speeds[mode].getNum(i)][1];
					

					out = input1 + ", " + input2 + ",";
					

			
				}

				else{
					int thing = Integer.parseInt(print[i][4]);
					int second = thing / 1000;
					int centiSec = (thing - second * 1000) / 10;

					out = second + ", " + centiSec + ",";
				}

				
				if(i>0){
					out1= "\n" + output1 +  out;
				}

				else{
					out1= output1 +  out;
				}

				output+=out1;

			}

			output+="";

			jlabel.setText(output);
			jFrame.repaint();
		}
				
	}
	
	
	private static void draw_label(JFrame jFrame){
		//create array of labelbutton
		int addR = 0;
		
		for(int i = 0; i < 4; i++){
			int addC = 1;
			for(int j = 0; j <12; j++){
				labelCellsRow[i][j] = new JButton();
				labelCellsRow[i][j].setMargin(new Insets(0, 0, 0, 0));
				labelCellsRow[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
				labelCellsRow[i][j].setMargin( new Insets(4, 4, 4, 4) );
				labelCellsRow[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				labelCellsRow[i][j].setBackground(Color.LIGHT_GRAY);
				labelCellsRow[i][j].setOpaque(true);
                labelCellsRow[i][j].setText(Integer.toString(j+1));

				if((j+1)%12 == 1){
					addC++;
				}

				
				if (i==0) labelCellsRow[i][j].setBounds((2+j+1)*base + (base/10)*addC-base/10, (1)*base ,base, base);
				if (i==1) labelCellsRow[i][j].setBounds((2+12+j+1)*base +base/10+ (base/10)*addC-base/10, base ,base, base);
			
				jFrame.add(labelCellsRow[i][j]);
			
			}

			
		}
		for(int i = 0; i < 4; i++){
			int addC = 1;

			
			for(int j = 0; j <12; j++){
				labelCellsCol[i][j] = new JButton();
				labelCellsCol[i][j].setMargin(new Insets(0, 0, 0, 0));
				labelCellsCol[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
				labelCellsCol[i][j].setMargin( new Insets(4, 4, 4, 4) );
				labelCellsCol[i][j].setBackground(Color.LIGHT_GRAY);
				labelCellsCol[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				labelCellsCol[i][j].setOpaque(true);
                labelCellsCol[i][j].setText(Integer.toString(j+1));

				if((j+1)%12 == 1){
					addC++;
				}

				
				if (i==0) labelCellsCol[i][j].setBounds((1)*base, (2+j+1)*base +  (base/10)*addC -base/10,base, base);
				if (i==1) labelCellsCol[i][j].setBounds((1)*base, (2+12+j+1)*base + base/10 + (base/10)*addC-base/10, base, base);
			
				
				jFrame.add(labelCellsCol[i][j]);
				
			
			}

			
		}
	}
	

	public void createAndShowGUI() {
		programNum++;

		initialization++;
    
		//base dimsion
		Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();

        jFrame = new JFrame();
		JButton[] speedButtons = new JButton[4];
		JButton[] mergeDirect = new JButton[4];
		JTextPane robotDirectory = new JTextPane();
		JLabel welcomeDirectory = new JLabel();
		JTextField fontPane = new JTextField();
		JTextField[] speedField = new JTextField[4];
		JTextField[] speedField2 = new JTextField[4];
		JTextField[] symbolField = new JTextField[4];
		JTextField[] symbolField2 = new JTextField[4];
		final JTextField mergeDelayField = new JTextField();
		final JTextField mergeTotalTField = new JTextField();
		JScrollPane jsp = new JScrollPane(jlabel);

		JButton reset = new JButton("<html>\u21BA<html>");
		JButton goBackButton = new JButton("\u2190");

		JButton saveMacro = new JButton();

		JTextField macroNameField = new JTextField();
		JButton macroSaver = new JButton();
		

		
		class speedListener implements ActionListener{
			private int speedNum;

			public speedListener (int speedClassifier){
				this.speedNum = speedClassifier;
			}
			public void actionPerformed(ActionEvent e){
				if(mode != -1){
					
					jFrame.remove(robotDirectory);
					jFrame.repaint();
					currentSpeed[mode] = speedValues[speedNum];

					for(int i = 0; i < 4;i ++){
						if(i != speedNum){
							if(i == 0){
								speedButtons[0].setBackground(Color.RED);
							}

							if(i == 1){
								speedButtons[1].setBackground(new Color(0,155,200));
							}

							if(i == 2){
								speedButtons[2].setBackground(new Color(56,202,109));
							}

							if(i == 3){
								speedButtons[3].setBackground(Color.YELLOW);
							}
							speedButtons[i].setBackground(adjustSaturation(speedButtons[i].getBackground(),0.25f));
						}

					}

					if(speedNum == 0){
						speedButtons[0].setBackground(Color.RED);
					}

					if(speedNum == 1){
						speedButtons[1].setBackground(new Color(0,155,200));
					}

					if(speedNum == 2){
						speedButtons[2].setBackground(new Color(56,202,109));
					}

					if(speedNum == 3){
						speedButtons[3].setBackground(Color.YELLOW);
					}


					if(initialization==1){
						
						buttonCells[10][10].doClick();
			
					}

					if(programNum==0 && tutorial == 1){
						jFrame.getContentPane().setBackground(new Color(236, 236, 236));
						jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jFrame.setLocationRelativeTo(null);


						jFrame.add(mergeDirect[0]);
						jFrame.add(mergeDirect[1]);
						jFrame.add(mergeDirect[2]);
						jFrame.add(mergeDirect[3]);

						jFrame.add(reset);
						jFrame.add(saveMacro);
						jFrame.add(goBackButton);

						for(int i = 0; i < 21;i++){
							for(int j= 0;j < 21;j++){
								jFrame.add(buttonCells[i][j]);
							}
						}

						jFrame.repaint();

					}

				}
				
			}

			
		}

		class speedField2Listener implements DocumentListener{

			private int speedFieldNum;
			

			public speedField2Listener (int speedClassifier){
				speedFieldNum = speedClassifier;
			}
		
			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSpeed)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSpeed)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
						String input = speedField2[speedFieldNum].getText();

						if(! isInteger(input) || (input.equals(""))){
							return;
						}

						String[] inputArray = input.split("");
						String input2 = "";
						for(int i = 0; i < inputArray.length;i++){
							if(!(inputArray[i].equals("0"))){
								for(int j= i; j < inputArray.length;j++){
									input2 += inputArray[j];
								}
								break;
							}
						}

						if(input.equals("00") || input.equals("0") ){
							input = "0";
						}

						else
						input = input2;



						if(Integer.parseInt(input)==0 &&Integer.parseInt(speedField[speedFieldNum].getText())==0  ){
							return;
						}

						
						if(isInteger(input) &&  Integer.parseInt(input) >= 0  ){
							int intInput = Integer.parseInt(input);

							boolean tracker = false;

							if(mode != -1 && currentSpeed[mode]==speedValues[speedFieldNum]){
								tracker = true;
							}

							for(int i = 0; i < RobotClass.length;i++){
								for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
									if(Speeds[i].getNum(j)==speedFieldNum){
										int thing = (Speeds[i].getSpeed(j)/1000)*1000 + intInput*10;
										int[] newThing = {thing,speedFieldNum};
										Speeds[i].storedSpeeds.set(j,newThing);
									}
								}
							}

							speedValues[speedFieldNum] = (speedValues[speedFieldNum]/1000)*1000 + intInput*10;
							for(int i = 0; i < RobotClass.length;i++){
								ArrayList<int[]> using = filterArray2(RobotClass[i].storedMoves);
								ArrayList<int[]> output = new ArrayList<int[]>();

								
								for(int j = 0; j < using.size();j++){
									for(int a = 0; a < Speeds[i].getSpeed(j);a++){
										output.add(using.get(j).clone());
									}							
								}

								RobotClass[i].storedMoves = output;


							}

							if (tracker == true) currentSpeed[mode]= speedValues[speedFieldNum] ;
							activeSymbol = false;
							
							String input1 = symbols[speedFieldNum][1];
							if(isInteger(input1)){

								if(input.length() < 2){
									symbolField2[speedFieldNum].setText(intInput+"");
								}

								else{
									symbolField2[speedFieldNum].setText(intInput+"");
								}

								symbols[speedFieldNum][1]=intInput + "";
							}

							activeSymbol = true;

							activeSpeed = false;
							if(input.length() == 1){
								speedField2[speedFieldNum].setText("0" + input);
							}

							

							else{
								speedField2[speedFieldNum].setText( input);
							}

							activeSpeed = true;
							
							autoPrint(jFrame);
		
							jFrame.repaint();
						}
					}
				};       
				SwingUtilities.invokeLater(doHighlight);
			}
		
			
		}

		class speedFieldListener implements DocumentListener{

			private int speedFieldNum;
			

			public speedFieldListener (int speedClassifier){
				speedFieldNum = speedClassifier;
			}
		
			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSpeed)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSpeed)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
						String input = speedField[speedFieldNum].getText();

						if(! isInteger(input) || (input.equals(""))){
							return;
						}
						
						if(isInteger(input) &&  Integer.parseInt(input) >=0 ){
							int intInput = Integer.parseInt(input);


							if(Integer.parseInt(input)==0 &&Integer.parseInt(speedField2[speedFieldNum].getText())==0  ){
							return;
							}

							boolean tracker = false;

							if(mode != -1 && currentSpeed[mode]==speedValues[speedFieldNum]){
								tracker = true;
							}

							for(int i = 0; i < RobotClass.length;i++){
								for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
									if(Speeds[i].getNum(j)==speedFieldNum){
										int thing = Speeds[i].getSpeed(j)%1000 + intInput*1000;
										int[] newThing = {thing,speedFieldNum};
										Speeds[i].storedSpeeds.set(j,newThing);
									}
								}
							}
							speedValues[speedFieldNum] = (speedValues[speedFieldNum])%1000 +intInput*1000;

							for(int i = 0; i < RobotClass.length;i++){
								ArrayList<int[]> using = filterArray2(RobotClass[i].storedMoves);
								ArrayList<int[]> output = new ArrayList<int[]>();

								
								for(int j = 0; j < using.size();j++){
									for(int a = 0; a < Speeds[i].getSpeed(j);a++){
										output.add(using.get(j).clone());
									}							
								}

								RobotClass[i].storedMoves = output;

							}

							if (tracker == true) {currentSpeed[mode]= speedValues[speedFieldNum];}



							activeSymbol = true;
							String input1 = symbols[speedFieldNum][0];
							if(isInteger(input1)){
								
								symbolField[speedFieldNum].setText(intInput+"");
								symbols[speedFieldNum][0]=intInput + "";
							}

							activeSymbol = true;
							


							autoPrint(jFrame);
		
							jFrame.repaint();
						}
					}
				};       
				SwingUtilities.invokeLater(doHighlight);
			}
		
			
		}

		class symbolField2Listener implements DocumentListener{

			private int symbolFieldNum;
			

			public symbolField2Listener (int Classifier){
				symbolFieldNum = Classifier;
			}
		
			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSymbol)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSymbol)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
						String input = symbolField2[symbolFieldNum].getText();

				

						if(!(input.equals(""))&& !isInteger(input))
						symbols[symbolFieldNum][1] = input;

						if(isInteger(input) &&  Integer.parseInt(input) >= 0  && input.length() <= 2){
							int intInput = Integer.parseInt(input);

							if(Integer.parseInt(input)==0 &&Integer.parseInt(speedField[symbolFieldNum].getText())==0  ){
								return;
							}
							boolean tracker = false;

							if(mode != -1 && currentSpeed[mode]==speedValues[symbolFieldNum]){
								tracker = true;
							}

							for(int i = 0; i < RobotClass.length;i++){
								for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
									if(Speeds[i].getNum(j)==symbolFieldNum){
										int thing = (Speeds[i].getSpeed(j)/1000)*1000 + intInput*10;
										int[] newThing = {thing,symbolFieldNum};
										Speeds[i].storedSpeeds.set(j,newThing);
									}
								}
							}
							speedValues[symbolFieldNum] = (speedValues[symbolFieldNum]/1000)*1000 + intInput*10;

							for(int i = 0; i < RobotClass.length;i++){
								ArrayList<int[]> using = filterArray2(RobotClass[i].storedMoves);
								ArrayList<int[]> output = new ArrayList<int[]>();

								
								for(int j = 0; j < using.size();j++){
									for(int a = 0; a < Speeds[i].getSpeed(j);a++){
										output.add(using.get(j).clone());
									}							
								}

								RobotClass[i].storedMoves = output;


							}

							activeSpeed = false;
							activeSymbol = false;
							
							if (tracker == true) currentSpeed[mode]= speedValues[symbolFieldNum];

							if(input.length() < 2){
								speedField2[symbolFieldNum].setText("0" + input);
								symbolField2[symbolFieldNum].setText(input);
							}

							else{
								speedField2[symbolFieldNum].setText(input);
								symbolField2[symbolFieldNum].setText(input);
							}

							activeSpeed = true;
							activeSymbol = true;
							symbols[symbolFieldNum][1] = input;

						}
						autoPrint(jFrame);

						jFrame.repaint();
					}
				};       
				SwingUtilities.invokeLater(doHighlight);
			}
		}

		

		class symbolFieldListener implements DocumentListener{

			private int symbolFieldNum;
			

			public symbolFieldListener (int Classifier){
				symbolFieldNum = Classifier;
			}

			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSymbol)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSymbol)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
						String input = symbolField[symbolFieldNum].getText();

			
						if(!(input.equals(""))&&!isInteger(input))
						symbols[symbolFieldNum][0] = input;

						if(isInteger(input) &&  Integer.parseInt(input) >= 0  ){
							int intInput = Integer.parseInt(input);

							if(Integer.parseInt(input)==0 &&Integer.parseInt(speedField2[symbolFieldNum].getText())==0  ){
							return;
							}

							boolean tracker = false;

							if(mode != -1 && currentSpeed[mode]==speedValues[symbolFieldNum]){
								tracker = true;
							}

							for(int i = 0; i < RobotClass.length;i++){
								for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
									if(Speeds[i].getNum(j)==symbolFieldNum){
										int thing = Speeds[i].getSpeed(j)%1000 + intInput*1000;
										int[] newThing = {thing,symbolFieldNum};
										Speeds[i].storedSpeeds.set(j,newThing);
									}
								}
							}
							speedValues[symbolFieldNum] = (speedValues[symbolFieldNum])%1000 +intInput*1000;
							
							for(int i = 0; i < RobotClass.length;i++){
								ArrayList<int[]> using = filterArray2(RobotClass[i].storedMoves);
								ArrayList<int[]> output = new ArrayList<int[]>();

								
								for(int j = 0; j < using.size();j++){
									for(int a = 0; a < Speeds[i].getSpeed(j);a++){
										output.add(using.get(j).clone());
									}							
								}

								RobotClass[i].storedMoves = output;


							}
							if (tracker == true) currentSpeed[mode]= speedValues[symbolFieldNum];

							
							activeSpeed = false;

							speedField[symbolFieldNum].setText(input);

							activeSpeed = true;
							symbols[symbolFieldNum][0] = input;


						}



						autoPrint(jFrame);

						jFrame.repaint();
					}
				};       
				SwingUtilities.invokeLater(doHighlight);
			}
		
			
		}

		

		class mergeDelayFieldListener implements DocumentListener{

			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSymbol)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSymbol)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
					String input = mergeDelayField.getText();
					
					if(isInteger(input) &&  Integer.parseInt(input) > 0 && Integer.parseInt(input) %10==0 ){
						int intInput = Integer.parseInt(input);

						mergeDelay = intInput;
	
						jFrame.repaint();
					}

					};       
				
				};
				SwingUtilities.invokeLater(doHighlight);
				
			}
		}

		class mergeTotalTFieldListener implements DocumentListener{

			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSymbol)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSymbol)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {

		
			
						String input = mergeTotalTField.getText();
						
						if(isDouble(input) &&  Double.parseDouble(input) > 0 ){
							double doubleInput = Double.parseDouble(input);

							mergeTotalT = ((int) (doubleInput * 100))*10;
		
							jFrame.repaint();
						}
					}

				};       
				
				SwingUtilities.invokeLater(doHighlight);
			
				
			}
		}

		class mergeDirectListener implements ActionListener{

			private int mergeNum;
			

			public mergeDirectListener (int mergeNum){
				this.mergeNum = mergeNum;
			}

		
			public void actionPerformed(ActionEvent event) {
				merge = mergeNum;

				mergeDirect[merge].setBackground(Color.LIGHT_GRAY);
				mergeDirect[merge].setOpaque(true);



				for(int i = 0; i < mergeDirect.length;i++){
					if (i!=merge) mergeDirect[i].setBackground(Color.GRAY);
				}

				if(mode != -1 && RobotClass[mode].storedMoves.size()!=0){
					int r = previous_moves[mode][4][0];
					int c = previous_moves[mode][4][1];
					
						for(int i = 0; i < (dimension_x +2);i++){
							for(int j=0;j< (dimension_y + 2) ;j++){
								specialButtons[i][j].setText("");
								specialButtons[i][j].setIcon(null);
								jFrame.remove(specialButtons[i][j]);
							}
						}
						insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp );
					

				}
				jFrame.repaint();
				
			}
		}

		class macroSaverListener implements ActionListener{
			public void actionPerformed(ActionEvent event){

				if(Speeds[mode].storedSpeeds.size() <= 1 || macroNameField.getText().length() < 1){
					return;
				}

				for(int i = 0; i < Speeds[mode].getSpeed(0);i++){
					RobotClass[mode].storedMoves.remove(0);
				}

				Speeds[mode].storedSpeeds.remove(0);

				String fileName = "Macro_" + macroNameField.getText();

				//if jframe fills out name, then save the text in text file
				FileWriter geek_file;
				try
				{
					geek_file = new FileWriter(fileName+".txt");
					
					// Initializing BufferedWriter
					BufferedWriter geekwrite = new BufferedWriter(geek_file);
					
					
					// Use of write() method to write the value in 'ABC' file
					// Printing E

					int counting1 = 0;
					ArrayList<int[]> macroMoves = new ArrayList<int[]>();

					macroMoves = filterArray2(RobotClass[mode].storedMoves);

					geekwrite.write("RobotClass");
					for(int i = 0; i < macroMoves.size();i++){
						geekwrite.write("\n" + (macroMoves.get(i)[0]-10) + " " + (macroMoves.get(i)[1]-10));
					}


					geekwrite.write("\n" + "Speeds");
					for(int i = 0; i < Speeds[mode].storedSpeeds.size();i++){
						geekwrite.write("\n" + (Speeds[mode].storedSpeeds.get(i)[0]) + " " + Speeds[mode].storedSpeeds.get(i)[1]);
					}

					geekwrite.write("\n" + "SpecialMoves");
					for(int i = 0; i < specialMoves[mode].storedSMoves.size();i++){
						geekwrite.write("\n" + (specialMoves[mode].storedSMoves.get(i)[0]) + " " + (specialMoves[mode].storedSMoves.get(i)[1]-10) + " "+(specialMoves[mode].storedSMoves.get(i)[2]-10) + " " + (specialMoves[mode].storedSMoves.get(i)[3]) + " " + (specialMoves[mode].storedSMoves.get(i)[4]));
					}
					// Closing BufferWriter to end operation
					
					geekwrite.close();
					
				}
				catch (IOException except)
				{
					except.printStackTrace();
				} 

				specialMoves[mode].storedSMoves.clear();
				RobotClass[mode].storedMoves.clear();
				Speeds[mode].storedSpeeds.clear();

				programNum = -1;
				initialization=0;


				if (returnBoard == 1){
					ferrobot2x3 start2x3 = new ferrobot2x3(length,width);
					start2x3.runProgram();
					jFrame.dispose();
					
				}
				
			}
		}

		class saveMacroListener implements ActionListener{
			public void actionPerformed(ActionEvent event){

				if(Speeds[mode].storedSpeeds.size() <= 1){
					return;
				}

				//open up jframe to enter name

				robotDirectory.setEditable(false); // as before
				robotDirectory.setBorder(null); // remove the border
				robotDirectory.setMargin(new Insets(0, 0, 0, 0));
				StyledDocument doc5= robotDirectory.getStyledDocument();
				SimpleAttributeSet center5 = new SimpleAttributeSet();
				StyleConstants.setAlignment(center5, StyleConstants.ALIGN_CENTER);
				doc5.setParagraphAttributes(0, doc5.getLength(), center5, false);
				robotDirectory.setFont(new Font("Calibri", Font.BOLD,font));
				robotDirectory.setBackground(null);
				robotDirectory.setForeground(Color.BLACK);
				robotDirectory.setText("Enter macro name:");
				robotDirectory.setBounds(25 *base, base +9*base-base/4-base/2, base*4, base/2+base/4);
				robotDirectory.setOpaque(true);

				
				macroNameField.setHorizontalAlignment(JTextField.CENTER);
				macroNameField.setBackground(Color.white);
				macroNameField.setOpaque(true);
				macroNameField .setBorder(BorderFactory.createLineBorder(Color.gray));
				macroNameField .setFont(new Font("Calibri", Font.PLAIN,font));
				macroNameField .setText("");
				macroNameField.setBounds(25 *base+base/2+base/4, base +9*base, base*2+base/2, base);


				macroSaver.setMargin(new Insets(0, 0, 0, 0));
				
				macroSaver.setBorder(BorderFactory.createLineBorder(Color.gray));
				macroSaver.setFont(new Font("Calibri", Font.PLAIN,font));
				macroSaver.setBackground(new Color (255,100,100));
				macroSaver.setOpaque(true);
				macroSaver.setText("Save Name");

				macroSaver.addActionListener(new macroSaverListener());
				//goBackButton.setBounds(totalX+base*2, totalY+base*2, base*2, base);
				macroSaver.setBounds(25 *base+base/2+base/4, base +base*10, base*2+base/2, base);
				macroSaver.setForeground(Color.BLACK);
				jFrame.add(macroNameField);
				jFrame.add(macroSaver);

				jFrame.add(robotDirectory);

				jFrame.repaint();

				
			}
		}

		class fontListener implements DocumentListener{

			public void changedUpdate(DocumentEvent e) {
				// warn();
			}
			public void removeUpdate(DocumentEvent e){
				if(activeSymbol)
				warn();
			}
			public void insertUpdate(DocumentEvent e) {
				if(activeSymbol)
				warn();
			}

			private void warn() {

				Runnable doHighlight = new Runnable() {
					@Override
					public void run() {
						String input = fontPane.getText();
						
						if(isInteger(input) &&  Integer.parseInt(input) > 5  ){
							font = Integer.parseInt(input);
							if(programNum==0){
								programNum = -1;
							}

							createAndShowGUI();
							jFrame.dispose();
							
		
						}
					}

				};       
				SwingUtilities.invokeLater(doHighlight);
			}
				
		}
		   
				jFrame.setBackground(adjustSaturation(new Color (255,209,0),0.5f));

               
				jFrame.setLayout(null);
				jFrame.setSize(base*32+base/2,base*25);
				
				jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				jFrame.setLocationRelativeTo(null);
				

				int addR=0;
                for(int i = 0; i <21; i++){
					int addC = 0;
					for(int j = 0; j < 21; j++){
						buttonCells[i][j] = new JButton();
						buttonCells[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
						buttonCells[i][j].setMargin(new Insets(0, 0, 0, 0));
						buttonCells[i][j].setBackground(Color.WHITE);
						buttonCells[i][j].setOpaque(true);
						buttonCells[i][j].setFont(new Font("Calibri", Font.PLAIN, 20));
						buttonCells[i][j].setHorizontalAlignment(JButton.CENTER);
					
				
						buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp));
						
						buttonCells[i][j].setBounds((j+1)*base + (base/10)*addC, (i+1)*base + (base/10)*addR,base, base);
						jFrame.add(buttonCells[i][j]);
						
						
					}

			
				}

				
				goBackButton.setMargin(new Insets(0, 0, 0, 0));
				goBackButton.addActionListener(new GoBack());
				goBackButton.setBorder(BorderFactory.createLineBorder(Color.gray));
				goBackButton.setFont(new Font("Calibri", Font.PLAIN,font));
				goBackButton.setBackground(new Color(4,92,140));
				goBackButton.setOpaque(true);
				
				goBackButton.setBounds(23 *base, base , base*3, base);
				goBackButton.setForeground(Color.WHITE);
				jFrame.add(goBackButton);

				
				reset.setMargin(new Insets(0, 0, 0, 0));
				reset.addActionListener(new Reset());
				reset.setBorder(BorderFactory.createLineBorder(Color.gray));
				reset.setBackground(new Color(131,187,236));
				reset.setOpaque(true);
				reset.setFont(new Font("Calibri", Font.PLAIN,font));
			
				reset.setBounds(23 *base, 2 * base , base*3, base);
				reset.setForeground(Color.WHITE);
				jFrame.add(reset);


				saveMacro.setBackground(new Color (255,209,0));
				saveMacro.setOpaque(true);
				saveMacro.setMargin(new Insets(0, 0, 0, 0));
				saveMacro.setBorder(BorderFactory.createLineBorder(Color.gray));
				saveMacro.setFont(new Font("Calibri", Font.PLAIN,font));
				saveMacro.setBounds(29 *base, base*6+base, base*2, base+base/3);
				saveMacro.addActionListener(new saveMacroListener());
				saveMacro.setText("<html><div style='text-align: center;'> Save<br>Macro<html>");
				jFrame.add(saveMacro);

				if (tutorial ==1){
					robotDirectory.setEditable(false); // as before
					robotDirectory.setBorder(null); // remove the border
					robotDirectory.setMargin(new Insets(0, 0, 0, 0));
					robotDirectory.setBounds(28 *base - base/2, base +4*base, base*6, base);
					StyledDocument doc6= robotDirectory.getStyledDocument();
					SimpleAttributeSet center6 = new SimpleAttributeSet();
					StyleConstants.setAlignment(center6, StyleConstants.ALIGN_CENTER);
					doc6.setParagraphAttributes(0, doc6.getLength(), center6, false);
					robotDirectory.setFont(new Font("Calibri", Font.BOLD,font+5));
					robotDirectory.setBackground(null);
					robotDirectory.setForeground(Color.WHITE);
					robotDirectory.setOpaque(true);
					
					jFrame.add(robotDirectory);

				}
		

				for(int i = 0; i < speedButtons.length;i++){
					speedButtons[i] = new JButton();
					speedButtons[i].setMargin(new Insets(0, 0, 0, 0));
					speedButtons[i].setFont(new Font("Calibri", Font.PLAIN,font));
					speedButtons[i].setBounds(27 *base, base +i*base, base, base);
					speedButtons[i].addActionListener(new speedListener(i));
					speedButtons[i].setBorder(BorderFactory.createLineBorder(Color.gray));
					jFrame.add(speedButtons[i]);
				}
				speedButtons[0].setBackground(Color.RED);
				speedButtons[1].setBackground(new Color(0,155,200));
				speedButtons[2].setBackground(new Color(56,202,109));
				speedButtons[3].setBackground(Color.YELLOW);

                for(int i = 0; i < mergeDirect.length;i++){
					mergeDirect[i] = new JButton();
					mergeDirect[i].setMargin(new Insets(0, 0, 0, 0));
					mergeDirect[i].setFont(new Font("Calibri", Font.PLAIN,font));
					mergeDirect[i].addActionListener(null);
					mergeDirect[i].setBackground(Color.LIGHT_GRAY);
					
					if (i!=merge) mergeDirect[i].setBackground(Color.GRAY);
					
					mergeDirect[i].setBorder(BorderFactory.createLineBorder(Color.gray));
					mergeDirect[i].setOpaque(true);
					mergeDirect[i].addActionListener(new mergeDirectListener(i));

					
					jFrame.add(mergeDirect[i]);
				}

				mergeDirect[0].setIcon(new ImageIcon("1.png"));
				mergeDirect[1].setIcon(new ImageIcon("2.png"));
				mergeDirect[2].setIcon(new ImageIcon("3.png"));
				mergeDirect[3].setIcon(new ImageIcon("4.png"));

				mergeDirect[0].setBounds(29 *base , base, base, base);
				mergeDirect[1].setBounds(30 *base , base, base, base);
				mergeDirect[2].setBounds(29 *base , base*2, base, base);
				mergeDirect[3].setBounds(30 *base , base*2, base, base);
				mergeTotalTField.setText(mergeTotalT/1000 + "");
				mergeTotalTField.setBorder(BorderFactory.createLineBorder(Color.gray));
				mergeTotalTField.setHorizontalAlignment(JTextField.CENTER);
				mergeTotalTField.setFont(new Font("Calibri", Font.PLAIN,font));
				mergeTotalTField.setBounds(29 *base+base, base *3, base, base+base/2);
				mergeTotalTField.getDocument().addDocumentListener(new mergeTotalTFieldListener());
				jFrame.add(mergeTotalTField);

				
				mergeDelayField.setText(mergeDelay+ "");
				mergeDelayField.setBorder(BorderFactory.createLineBorder(Color.gray));
				mergeDelayField.setHorizontalAlignment(JTextField.CENTER);
				mergeDelayField.setFont(new Font("Calibri", Font.PLAIN,font));
				mergeDelayField.setBounds(29*base+base, base * 4+base/2, base, base+base/2);
				mergeDelayField.getDocument().addDocumentListener(new mergeDelayFieldListener());
				jFrame.add(mergeDelayField);

				

				JLabel delayDescrip = new JLabel();
				delayDescrip.setBounds(29*base, base * 4+base/2, base, base+base/2);
				delayDescrip.setHorizontalAlignment(JLabel.CENTER);
				delayDescrip.setBorder(BorderFactory.createLineBorder(Color.gray));
				delayDescrip.setFont(new Font("Calibri", Font.PLAIN,font-5));
				delayDescrip.setText("<html><div style='text-align: center;'>" + "Delay<br>(ms)" +  "</div></html>");
				
				jFrame.add(delayDescrip);

				JLabel totalDescrip = new JLabel();
				totalDescrip.setBounds(29*base, base * 3, base, base+base/2);
				totalDescrip.setHorizontalAlignment(JLabel.CENTER);
				totalDescrip.setBorder(BorderFactory.createLineBorder(Color.gray));
				totalDescrip.setFont(new Font("Calibri", Font.PLAIN,font-5));
				totalDescrip.setText("<html><div style='text-align: center;'>" + "Total<br>(s)" +  "</div></html>");
				
				jFrame.add(totalDescrip);

                int SaddR = 0;
				int StotalX = 0;
				int StotalY = 0;
				for(int i = 0; i < (dimension_x +2); i++){
					
					for(int j = 0; j < (dimension_y+2); j++){
						specialButtons[i][j] = new JButton();
						specialButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
						specialButtons[i][j].setHorizontalAlignment(JButton.CENTER);
						specialButtons[i][j].setMargin(new Insets(0, 0, 0, 0));
						specialButtons[i][j].setBackground(Color.WHITE);
						specialButtons[i][j].setOpaque(true);
						specialButtons[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
					
						specialButtons[i][j].setBounds((j)*base, (i)*base ,base, base);

						
					}

				}
				printRobot(buttonCells, addR);


				
				if(mode != -1){
					int speed = -1;
					for(int i = 0; i < 4;i ++){
						if(currentSpeed[mode]==speedValues[i]){
							speed = i;
						}
		
						else if(programNum != 0){
							speedButtons[i].setBackground(adjustSaturation(speedButtons[i].getBackground(),0.25f));
						}
						
		
					}
		
					if(speed == 0){
						speedButtons[0].setBackground(Color.RED);
					}
		
					if(speed == 1){
						speedButtons[1].setBackground(new Color(0,155,200));
					}
		
					if(speed == 2){
						speedButtons[2].setBackground(new Color(56,202,109));
					}
		
					if(speed == 3){
						speedButtons[3].setBackground(Color.YELLOW);
					}
				}
				if(programNum==0 && tutorial ==1){
					jFrame.getContentPane().removeAll();
					jFrame.getContentPane().setBackground(Color.BLACK);
					jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jFrame.setLocationRelativeTo(null);
					
					jFrame.getContentPane().removeAll();
					
					jFrame.getContentPane().setBackground(Color.BLACK);
					jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jFrame.setLocationRelativeTo(null);
					
					robotDirectory.setText("\u2191" + " Select Speed");
					robotDirectory.setBounds(27 *base, base +4*base, base*20, base*5);
					StyledDocument doc10= robotDirectory.getStyledDocument();
					SimpleAttributeSet center10= new SimpleAttributeSet();
					StyleConstants.setAlignment(center10, StyleConstants.ALIGN_LEFT);
					doc10.setParagraphAttributes(0, doc10.getLength(), center10, false);
					
					jFrame.add(speedButtons[0]);
					jFrame.add(speedButtons[1]);
					jFrame.add(speedButtons[2]);
					jFrame.add(speedButtons[3]);
					jFrame.add(robotDirectory);
						
	
					jFrame.repaint();
				}

				
				speedButtons[2].doClick();
				jFrame.setVisible(true);

			
		}
    
		
		class Reset implements ActionListener {


			public void actionPerformed(ActionEvent e){

				if(currentSpeed[mode] <= 0 ){
					return;
				}
				
				for(int i = 0; i < 23;i++){
					for(int j=0;j<23;j++){
						specialButtons[i][j].setText("");
						specialButtons[i][j].setIcon(null);
						jFrame.remove(specialButtons[i][j]);
						jFrame.repaint();
					}
				}

				
				int[][] temp = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
				previous_moves[mode] = temp;

				for(int i = 0; i < RobotClass.length;i++){
					RobotClass[i].storedMoves.clear();
					Speeds[i].storedSpeeds.clear();
					previousModes[i] = false;
					specialMoves[i].storedSMoves.clear();
					
				}

				for(int i = 0; i < 21; i++){
					for(int j = 0; j < 21; j++){
						buttonCells[i][j].setBackground(Color.WHITE);
						buttonCells[i][j].setOpaque(true);
						buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp));
						buttonCells[i][j].setText("");
						buttonCells[i][j].setIcon(null);
					}
				}

				for(int i = 0; i < 21;i++){
					for(int j=0;j<21;j++){
						for (ActionListener B : buttonCells[i][j].getActionListeners()){
							buttonCells[i][j].removeActionListener(B);
						}
						buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp));
					}
					
				}

				for(int i = 0; i < 23;i++){
					for(int j=0;j<23;j++){
						for (ActionListener B : specialButtons[i][j].getActionListeners()){
							specialButtons[i][j].removeActionListener(B);
						}
					}
					
				}
				jlabel.setText("");
				initialization = 2;
				buttonCells[10][10].doClick();
				jFrame.repaint();

			}
		}
		
		class GoBack implements ActionListener {
			public void actionPerformed(ActionEvent e){

				if(currentSpeed[mode] <= 0 ){
					return;
				}
				
				if(RobotClass[mode].storedMoves.size()==0){
					return;
				}

				

				if(Speeds[mode].storedSpeeds.size()==1){

					
					for(int i = 0; i < Speeds[mode].getSpeed(0);i++){
						RobotClass[mode].storedMoves.remove(RobotClass[mode].storedMoves.size()-1);
					}
					Speeds[mode].storedSpeeds.remove(0);

					for(int i = 0; i < 4;i++){
						int rPrev = previous_moves[mode][i][0];
						int cPrev = previous_moves[mode][i][1];
						buttonCells[rPrev][cPrev].setText("");
						buttonCells[rPrev][cPrev].setIcon(null);
						
				    } 
					
				
					int rPrev = previous_moves[mode][4][0];
					int cPrev = previous_moves[mode][4][1];

					
					buttonCells[rPrev][cPrev].setBackground(Color.WHITE);
					buttonCells[rPrev][cPrev].setOpaque(true);

					int r = rPrev;
					int c = cPrev;
					deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame );
				

					for(int i = 0; i < 21;i++){
						for(int j=0;j<21;j++){
							for (ActionListener B : buttonCells[i][j].getActionListeners()){
								buttonCells[i][j].removeActionListener(B);
							}
							buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp));
						}
						
					}

					for(int i = 0; i < 23;i++){
						for(int j=0;j<23;j++){
							for (ActionListener B : specialButtons[i][j].getActionListeners()){
								specialButtons[i][j].removeActionListener(B);
							}
						}
				    }

					if(specialMoves[mode].storedSMoves.size()>0){
						specialMoves[mode].storedSMoves.remove(specialMoves[mode].storedSMoves.size()-1);
					}


					int[][] temp = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
					previous_moves[mode] = temp;

					jlabel.setText("");
					
					initialization = 2;

					
					buttonCells[10][10].doClick();
					jFrame.repaint();

					
					
				}

				else{

					for(int i = 0; i < 21;i++){
						for(int j=0;j<21;j++){
							for (ActionListener B : buttonCells[i][j].getActionListeners()){
								buttonCells[i][j].removeActionListener(B);
							}
							buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp));
							buttonCells[i][j].setBackground(Color.WHITE);
							buttonCells[i][j].setOpaque(true);
							buttonCells[i][j].setText("");
							buttonCells[i][j].setIcon(null);
						}
						
					}

					for(int i = 0; i < 23;i++){
						for(int j=0;j<23;j++){
							for (ActionListener B : specialButtons[i][j].getActionListeners()){
								specialButtons[i][j].removeActionListener(B);
							}
						}
						
					}


					int ref = -1;
					if(specialMoves[mode].storedSMoves.size()>0){
						ref = specialMoves[mode].storedSMoves.get(specialMoves[mode].storedSMoves.size()-1)[4];
					}
					

					if(  specialMoves[mode].storedSMoves.size() > 0 && ref <= 4 &&  Speeds[mode].getNum(Speeds[mode].storedSpeeds.size()-1)==-1) {

						
						for(int i = 0; i < 4;i++){
							int rPrev = previous_moves[mode][i][0];
							int cPrev = previous_moves[mode][i][1];
							buttonCells[rPrev][cPrev].setText("");
							buttonCells[rPrev][cPrev].setIcon(null);
						}
						
						

						int rPrev = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-2)[0];
						int cPrev = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-2)[1];

						int r = rPrev;
						int c = cPrev;
						deleteSpecialButton (r, c, specialButtons, buttonCells, jFrame);


						while (specialMoves[mode].storedSMoves.size() >= 1 && specialMoves[mode].storedSMoves.get(specialMoves[mode].storedSMoves.size()-1)[4]==ref &&  Speeds[mode].getNum(Speeds[mode].storedSpeeds.size()-1)==-1){
							specialMoves[mode].storedSMoves.remove(specialMoves[mode].storedSMoves.size()-1);

							for(int j = 0; j < Speeds[mode].storedSpeeds.get(Speeds[mode].storedSpeeds.size()-1)[0];j++){
								RobotClass[mode].storedMoves.remove(RobotClass[mode].storedMoves.size()-1);
							}
							
							Speeds[mode].storedSpeeds.remove(Speeds[mode].storedSpeeds.size()-1);
							
						}

						buttonCells[RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0]][RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1]].setText("");

						
					}

					else{
						if( specialMoves[mode].storedSMoves.size() > 0 && Speeds[mode].storedSpeeds.size()-1 == specialMoves[mode].storedSMoves.get(specialMoves[mode].storedSMoves.size()-1)[0]) {
							specialMoves[mode].storedSMoves.remove(specialMoves[mode].storedSMoves.size()-1);
						}

						for(int i = 0; i < 4;i++){
							int rPrev = previous_moves[mode][i][0];
							int cPrev = previous_moves[mode][i][1];
							buttonCells[rPrev][cPrev].setText("");
							buttonCells[rPrev][cPrev].setIcon(null);
						}
						
						

						int rPrev = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-2)[0];
						int cPrev = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-2)[1];

						int r = rPrev;
						int c = cPrev;
						deleteSpecialButton (r, c, specialButtons, buttonCells, jFrame);
						

						for(int i = 0; i < Speeds[mode].getSpeed(Speeds[mode].storedSpeeds.size()-1);i++){
							RobotClass[mode].storedMoves.remove(RobotClass[mode].storedMoves.size()-1);
						}

						Speeds[mode].storedSpeeds.remove(Speeds[mode].storedSpeeds.size()-1);
					}
				int r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
				int c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

				buttonCells[r][c].setIcon(null);
				buttonCells[r][c].setText("");

				previous_moves[mode][4][0] = r;
				previous_moves[mode][4][1] = c;

				
					for(int i = 0; i < 23;i++){
						for(int j=0;j<23;j++){
							specialButtons[i][j].setText("");
							specialButtons[i][j].setIcon(null);
							jFrame.remove(specialButtons[i][j]);
						}
					}
					deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame );
				
				
				r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
				c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

				previous_moves[mode][4][0] = r;
				previous_moves[mode][4][1] = c;
                printRobot(buttonCells,1);
				for(int i = 0; i < 23;i++){
					for(int j=0;j<23;j++){
						specialButtons[i][j].setText("");
						specialButtons[i][j].setIcon(null);
						jFrame.remove(specialButtons[i][j]);
					}
				}
				insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp);
				int[] newest = {RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0],RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1]};
				previous_moves[mode][4] = newest;

				r = newest[0];
				c = newest[1];

					
                autoPrint(jFrame);
				
				int[][] store = {{r-1,c},{r,c+1},{r,c-1},{r+1,c}};
				    insertArrows (r,  c, buttonCells);
				
			}

			}
		}

	
	
	public void runProgram() {

		createAndShowGUI();
	}

	public static boolean isInList(
        final ArrayList<int[]> list, final int[] candidate){

		for(final int[] item : list){
			if(Arrays.equals(item, candidate)){
				return true;
			}
		}
		return false;
    }


	//makes sure the distance is >= 10 between any two
	public static boolean checkDistances(ArrayList<int[]> coordinates) {
        int size = coordinates.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                int[] coord1 = coordinates.get(i);
                int[] coord2 = coordinates.get(j);
                double distance = calculateDistance(coord1, coord2);
                if (distance < 12) {
                    return false;
                }
            }
        }
        return true;
    }

    public static double calculateDistance(int[] coord1, int[] coord2) {
        int xDiff = coord2[0] - coord1[0];
        int yDiff = coord2[1] - coord1[1];
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

	//check unit 
	public static boolean checkUnit(ArrayList<int[]> arrays) {
        int size = arrays.size();
        for (int i = 0; i < size; i++) {
            int[] array1 = arrays.get(i);
            int boardNum1 = getBoardNum(array1[0], array1[1]);
            for (int j = i + 1; j < size; j++) {
                int[] array2 = arrays.get(j);
                int boardNum2 = getBoardNum(array2[0], array2[1]);
                if (boardNum1 == boardNum2) {
                    return false;
                }
            }
        }
        return true;
    }

	//check valid square{
	public static boolean checkSquare(int row, int col) {
		int mode_size = RobotClass[mode].storedMoves.size();

		boolean pass = false;
		
		RobotClass[mode].addMoves(row,col);

		ArrayList<int[]> arrays = new ArrayList<>();
		
		for(Robot x : RobotClass){
			if(x.storedMoves.size()>=(mode_size+1) ){
				arrays.add(x.storedMoves.get(mode_size));
			}
		}

		if (checkUnit(arrays) && checkDistances(arrays)){
			pass = true;
		}

		RobotClass[mode].storedMoves.remove(mode_size);

		return pass;
	}

	//board num from r,c
	public static int getBoardNum (int i,int j){
		if(i < 12 && j < 12){
			return 1;
		}

		if(i < 12 && j >= 12){
			return 2;
		}

		if(i >= 12 && j<12){
			return 3;
		}

		if(i >= 12 && j >= 12){
			return 4;
		}

		return 0;
	}

	public static boolean isInteger(String s) {
		try { 
			Integer.parseInt(s); 
		} catch(NumberFormatException e) { 
			return false; 
		} catch(NullPointerException e) {
			return false;
		}
		// only got here if we didn't return false
		return true;
	}

	public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	public static int[] findDuplicateValues(Robot[] robots) {
        int n = robots.length;
        for (int i = 0; i < n; i++) {
            ArrayList<int[]> moves1 = robots[i].storedMoves;
            int m1 = moves1.size();
            for (int j = 0; j < m1; j++) {
                int[] move1 = moves1.get(j);
                for (int k = i + 1; k < n; k++) {
                    ArrayList<int[]> moves2 = robots[k].storedMoves;
                    int m2 = moves2.size();
                    for (int l = 0; l < m2; l++) {
                        int[] move2 = moves2.get(l);
                        if (move1[0] == move2[0] && move1[1] == move2[1]) {
                            int lowerIndex = (j < l) ? j : l;
                            return new int[]{move1[0], move1[1], lowerIndex, (i < k) ? i : k};
                        }
                    }
                }
            }
        }
        return null; // No duplicate values found
    }

	public static String[][] labelOutput(){
		int max = 0;
		for(int i = 0; i < RobotClass.length;i++){
			if(RobotClass[i].storedMoves.size()>max) max = RobotClass[i].storedMoves.size();
		}

		if(max == 0){
			return null;
		}
		String[][] output = new String[max][5];
		for(int i = 0; i < max;i++){
			for(int j = 0; j < RobotClass.length;j++){
				if(RobotClass[j].storedMoves.size() >= i+1){
					int r=RobotClass[j].storedMoves.get(i)[0];
					int c=RobotClass[j].storedMoves.get(i)[1];
					int boardNum = getBoardNum(r, c);
					int singleNum = convertToSingleNumber(convertToLocalBoardCoordinate(r,c));

					output[i][boardNum-1] = (singleNum) + "";


				}
				
			}
		}

		for(int i = 0; i < max;i++){
			for(int j = 0; j < output[0].length;j++){

				if(j==4){
					output[i][j] = (i+1) + "";
				}

				else if( (output[i][j]==null || output[i][j].equals(""))){
					output[i][j] = "OFF";
				}

			}
		}

		return output;
	}

	public static int convertToLocalBoardCoordinate(int globalRow, int globalColumn) {
        int localRow = globalRow % 12;
        int localColumn = globalColumn % 12;
        return localRow * 12 + localColumn;
    }

    public static int convertToSingleNumber(int localBoardCoordinate) {
        int localRow = localBoardCoordinate / 12;
        int localColumn = localBoardCoordinate % 12;
        return localRow * 12 + localColumn;
    }

	public static String[][] filterArray(String[][] inputArray) {
        ArrayList<String[]> outputRows = new ArrayList<>();

        for (int i = 0; i < inputArray.length; i++) {
            boolean isDuplicate = false;
            int var = 0;
			int start = 0;

            for (int j = i + 1; j < inputArray.length; j++) {
                if (areSubarraysEqual(inputArray[i], inputArray[j])) {
                    isDuplicate = true;
                    int lastDuplicateValue = Integer.parseInt(inputArray[j][4]);
                    int firstDuplicateValue = Integer.parseInt(inputArray[i][4]);
                    var = lastDuplicateValue - firstDuplicateValue;
					
                }

				if(!(areSubarraysEqual(inputArray[i], inputArray[j])) ){
					start = j;
					break;
				}

				if(j==inputArray.length-1){
					start = j;
					break;
				}

				
            }

            if (isDuplicate) {
				i = start-1;
                String[] duplicateRow = inputArray[i].clone();
                duplicateRow[4] = String.valueOf(var+1);
                outputRows.add(duplicateRow);
            }
        }

        // Convert ArrayList to 2D array
        String[][] outputArray = new String[outputRows.size()][5];
        for (int i = 0; i < outputRows.size(); i++) {
            outputArray[i] = outputRows.get(i);
        }

        return outputArray;
    }

	public static ArrayList<int[]> filterArray2(ArrayList<int[]> inputArray) {
        ArrayList<int[]> outputRows = new ArrayList<>();

        for (int i = 0; i < inputArray.size(); i++) {
            boolean isDuplicate = false;

			int start = 0;

            for (int j = i + 1; j < inputArray.size(); j++) {
                if (areSubarraysEqual(inputArray.get(i), inputArray.get(j)) ) {
                    isDuplicate = true;
					
                }

				if(!(areSubarraysEqual(inputArray.get(i), inputArray.get(j))) ){
					start = j;
					break;
				}

				if(j==inputArray.size()-1 ){
					start = j;
					break;
				}

				
            }


            if (isDuplicate) {
				i = start-1;
				int[] duplicateRow = inputArray.get(i).clone();
            	outputRows.add(duplicateRow);
                
            }

			
        }

        
        return outputRows;
    }


    public static boolean areSubarraysEqual(String[] subarray1, String[] subarray2) {
        if (subarray1.length != subarray2.length) {
            return false;
        }

        for (int i = 0; i < subarray1.length - 1; i++) {
            if (!subarray1[i].equals(subarray2[i])) {
                return false;
            }
        }

        return true;
    }

	public static boolean areSubarraysEqual(int[] subarray1, int[] subarray2) {
        if (subarray1.length != subarray2.length) {
            return false;
        }

        for (int i = 0; i < subarray1.length ; i++) {
            if (!(subarray1[i] == subarray2[i])) {
                return false;
            }
        }

		

        return true;
    }

	public static void resizeImage(String inputImagePath, String outputImagePath, int width, int height) {
        try {
            // Read the input image from the given file path
            File inputFile = new File(inputImagePath);
            BufferedImage inputImage = ImageIO.read(inputFile);
			
            // Create a new BufferedImage with the desired width, height, and transparency support
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

            // Create a Graphics2D object to draw the resized image
            Graphics2D g2d = resizedImage.createGraphics();

            // Set rendering hints to improve the quality of the resized image
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

            // Clear the background to make it transparent
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, width, height);

            // Draw the input image onto the resized image
            g2d.drawImage(inputImage, 0, 0, width, height, null);
            g2d.dispose();

            // Get the file extension of the output image
            String extension = getFileExtension(outputImagePath);

            // Write the resized image to the output file
            ImageIO.write(resizedImage, extension, new File(outputImagePath));

            // System.out.println("Image resized and saved to: " + outputImagePath);
        } catch (IOException e) {
            System.err.println("Error resizing the image: " + e.getMessage());
        }
    }

    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

	public static Color adjustSaturation(Color originalColor, float saturationFactor) {
        // Ensure the saturationFactor is within the range [0, 1]
        saturationFactor = Math.max(0.0f, Math.min(1.0f, saturationFactor));

        // Convert RGB to HSB (Hue, Saturation, Brightness)
        float[] hsbValues = new float[3];
        Color.RGBtoHSB(originalColor.getRed(), originalColor.getGreen(), originalColor.getBlue(), hsbValues);

        // Adjust saturation
        hsbValues[1] *= saturationFactor;

        // Convert back to RGB
        int rgb = Color.HSBtoRGB(hsbValues[0], hsbValues[1], hsbValues[2]);

        return new Color(rgb);
    }
	
}


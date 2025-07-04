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
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ferrobot2x3 {

    private static int[] counter = {0,0,0};
	private static int[][][] previous_moves = new int[3][9][2];
	private static int totalX = 0;
    private static int totalY = 0;
	private static int mode = -1;
	private static int previousMode = -2;
	private static int ticks=0;
	private static int initialization = 0;
	private static JTextPane jlabel = new JTextPane();
	private static JPanel jPane = new JPanel();
	
	private static JScrollPane title = new JScrollPane();
	private static JLabel macroTitle = new JLabel();
	private static boolean[] previousModes = new boolean[3];
	
	private static SpecialMoves[] specialMoves = {new SpecialMoves()};
	//timer pause
	private static boolean pause = false;
	private static boolean decision = true;

	//track program count
	private static int programNum = -1;

	//speed
	private static int[] currentSpeed = new int[3];
	private static int[] speedValues = {100,200,400,800};
	private static String[][] symbols = {{"Vt","Kt"},{"Mt","Bt"},{"Et","Nt"},{"Ft","St"}}; 
	private static Speed[] Speeds = {new Speed()};
	
	//merge
	private static int merge = 2;
	private static int mergeDelay = 80;
	private static int mergeTotalT = 10000;
	//keep track for tick counting
	private static int tickCounter = 0;

	//declare robots (stores robot moves)
	private static Robot[] RobotClass = {new Robot()};
    private static JButton[][] labelCellsRow = new JButton[4][12];
    private static JButton[][] labelCellsCol = new JButton[4][12];
	private static int labelgap =-1;
	static Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();

	//base size of a grid cell
    static int base = (size.height/36);
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

    //private  int dimension_row, dimension_col;
	private  static int dimension_row=2, dimension_col=3;
	private  int xShift = 0;
    private  int yShift = 0;
	
	private  static JButton[][] buttonCells ;
	private  static JButton[][] specialButtons;

	//font
	private static int font = 19;
	private static int tutorial = 1;

	//fields/symbols
    static JScrollPane scrollP ; 
	static JScrollPane scrollUp ; 
	static JScrollPane scrollDown ; 
	static JScrollPane scrollLeft ;
	static JScrollPane scrollRight ;
	static JPanel upPanel = new JPanel();
	static JPanel downPanel = new JPanel();
	static JPanel leftPanel = new JPanel();
	static JPanel rightPanel = new JPanel();
    static JButton[][] labelUp = new JButton[8][12];
    static JButton[][] labelDown = new JButton[8][12];
	static JButton[][] labelLeft = new JButton[8][12];
	static JButton[][] labelRight = new JButton[8][12];
	private static boolean activeSpeed = true;
	private static boolean activeSymbol = true;

	//saveMacro
	private static ArrayList<JButton> macroButtons = new ArrayList<JButton>();
	private static ArrayList<ArrayList<int[]>> macroStore = new ArrayList<ArrayList<int[]>>();
	private static int macroMode = 1;

	//timeMode
	private static int timeMode = 1;
	private static int speedFieldNumTimer =0;

	private static JButton reset = new JButton("<html>\u21BA<html>");
	private static int length = 0;
	private static int width = 0;
	private static int global_label_col=0;
	private static int global_label_row=0;

	static JButton[] labelRow ;
			
	static JButton[] labelCol ;

	//constructor sets the paramaters for length and width; resets the coordinates if the desired length and width have changed
	public ferrobot2x3(int l, int w){
		if(l != length || w != width){
		    reset.doClick();
			length = l;
			width = w;

			
			dimension_row=w;
			dimension_col=l;

			global_label_col=0;
			global_label_row=0;
		}
	}


	//listener that shifts the whole board left, right, up, down
	static class scrollmove implements ActionListener{
        private JScrollPane labaljsp;
		private int command_id =0;
		private JFrame jFrame;

        public scrollmove (JFrame jFrame, JScrollPane labaljsp, int command_id){
				super();				
				this.labaljsp = labaljsp;
				this.command_id = command_id;
				this.jFrame = jFrame;
		}
		public void actionPerformed(ActionEvent e){
			int value =0;
			int valuelabel =0;
			boolean print = false;

		   
			switch(command_id){
				case 0: /* left */
				       value = labaljsp.getHorizontalScrollBar().getValue();
					   valuelabel = scrollUp.getHorizontalScrollBar().getValue();
			           //System.out.println("left hor:" + value +"valuelabel:" + valuelabel);
				       if(global_label_col>0) {
							value = value -12*base - (base/10);
							global_label_col--;
							print = true;

						}
						//System.out.println(value);
						labaljsp.getHorizontalScrollBar().setValue(value);	   
						scrollUp.getHorizontalScrollBar().setValue(value);	            
						
				break;
				case 1: /* right */
				       value = labaljsp.getHorizontalScrollBar().getValue();
			           valuelabel = scrollUp.getHorizontalScrollBar().getValue();
			           //System.out.println("right hor:" + value +"valuelabel:" + valuelabel);
				       if(global_label_col < dimension_col-1) {
							value = value +12*base + (base/10);
							global_label_col++;
							print = true;

						}
						//System.out.println("global_label_col" + global_label_col+ "value:"+value);
						
						labaljsp.getHorizontalScrollBar().setValue(value);	    
						scrollUp.getHorizontalScrollBar().setValue(value);	        
				break;
				case 2: /*up */
				        value = labaljsp.getVerticalScrollBar().getValue();
						valuelabel = scrollLeft.getVerticalScrollBar().getValue();
			            //System.out.println("up veri:" + value +"valuelabel:" + valuelabel);
				        if(global_label_row > 0) {
							value = value -12*base - (base/10);
							global_label_row--;
							print = true;

						}
						//System.out.println(value);
						
						labaljsp.getVerticalScrollBar().setValue(value);	   
						scrollLeft.getVerticalScrollBar().setValue(value);	     
				break;
				case 3: /* down */
				       value = labaljsp.getVerticalScrollBar().getValue();
					   valuelabel = scrollLeft.getVerticalScrollBar().getValue();
			           // System.out.println("down veri:" + value +"valuelabel:" + valuelabel);
				        if(global_label_row < dimension_row-1) {
							value = value +12*base + (base/10);
							global_label_row++;
							print = true;

						}
						//System.out.println(value);
						
						labaljsp.getVerticalScrollBar().setValue(value);	
						scrollLeft.getVerticalScrollBar().setValue(value);	     
     
				break;
			}
           
			
			if(global_label_row+1+1 <= width){
				for (int i =0; i < 2;i++)
				{
				
					labelRow[i].setText(Integer.toString(global_label_row+i+1));
					
				}
				
			}

			if(global_label_col+1+2 <= length){
				for (int i =0; i < 3;i++)
				{
						
						labelCol[i].setText(Integer.toString(global_label_col+i+1));
				}
			}
            jFrame.repaint();
		 
		}
	}

	//any click on the grid for up,down,left,right movement arrows for the robot is registeerd here
	static class BListener implements ActionListener{

		private int row;
		private int col;
		private JFrame jFrame;
		private JScrollPane jsp;
		private JPanel jp;

		public BListener (int row, int col, JFrame jFrame, JScrollPane jsp,  JPanel jp){
			super();
			this.row = row;
			this.col = col;
			this.jFrame = jFrame;
			this.jsp = jsp;
			this.jp = jp;
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
					RobotClass[mode].addMoves(row,col,index);
					
				
					Speeds[mode].addSpeed(currentSpeed[mode],index);
					for(int i = 0; i < 4;i++){
						int rPrev = previous_moves[mode][i][0];
						int cPrev = previous_moves[mode][i][1];
						buttonCells[rPrev][cPrev].setText("");
						buttonCells[rPrev][cPrev].setIcon(null);
					}

					for(int i = 0; i < (dimension_row * 12);i++){
						for(int j=0;j< (dimension_col * 12);j++){
							for (ActionListener B : buttonCells[i][j].getActionListeners()){
								buttonCells[i][j].removeActionListener(B);
							}
							buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp, jp));
						}
						
					}

					for(int i = 0; i < (dimension_row * 12 +2);i++){
						for(int j=0;j<(dimension_col * 12 +2);j++){
							for (ActionListener B : specialButtons[i][j].getActionListeners()){
								specialButtons[i][j].removeActionListener(B);
							}
						}
						
					}
					int r = previous_moves[mode][4][0];
					int c = previous_moves[mode][4][1];
					deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame,jp);
			

					r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
					c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

					previous_moves[mode][4][0] = r;
					previous_moves[mode][4][1] = c;
					printRobot(buttonCells,1);
					for(int i = 0; i < (dimension_row * 12 + 2);i++){
						for(int j=0;j< (dimension_col * 12 + 2);j++){
							specialButtons[i][j].setText("");
							specialButtons[i][j].setIcon(null);
							jFrame.remove(specialButtons[i][j]);
						}
					}
					insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp,jp);					
					counter[mode]++;
					insertArrows (r,  c, buttonCells);
							
				}

					

				
			}
				jlabel.setText("");
				
				
				jFrame.add(jsp);
				autoPrint(jFrame);

				if(programNum==1 && tutorial ==1){
				createAndShowGUI();
				jFrame.dispose();

				
				}

	    }
		
			public void actionPerformed(ActionEvent e){
						actionPerformed_org();
			
						
			}

			
	} 

	//any click on the grid for a special button (including mixing) is registered here
	public static class specialButtonListener implements ActionListener{

		private int t;
		private int i;
		private int j;
		private int board;
		private int numClass;
		private JButton[][] buttonCells;
		private JFrame jFrame;
		private JScrollPane jsp;
		private JPanel jp;

		public specialButtonListener (int t,int i,int j,int board,int numClass, JButton[][] buttonCells, JFrame jFrame, JScrollPane jsp,  JPanel jp){
			this.t=t;
			this.i=i;
			this.j=j;
			this.board=board;
			this.numClass=numClass;
			this.buttonCells = buttonCells;
			this.jFrame = jFrame;
			this.jsp = jsp;
			this.jp = jp;
		}
		
		public void actionPerformed(ActionEvent event) {
			System.out.println("special button action");
			if(numClass==1 && mergeFunc(i,j,jFrame,jsp)){
				if (merge == 0){
					buttonCells[i][j].setIcon(null);
					buttonCells[i][j].setIcon(new ImageIcon("1.png"));
					int cycleNum = (mergeTotalT)/(4*mergeDelay);
					specialMoves[mode].addSMove(t,i,j,board,1,cycleNum*4,mergeDelay);
				}

				if(merge == 1){
					buttonCells[i][j].setIcon(null);
					buttonCells[i][j].setIcon(new ImageIcon("2.png"));
					int cycleNum = (mergeTotalT)/(4*mergeDelay);
					specialMoves[mode].addSMove(t,i,j,board,2,cycleNum*4,mergeDelay);
				}

				if(merge == 2){
					buttonCells[i][j].setIcon(null);
					buttonCells[i][j].setIcon(new ImageIcon("3.png"));
					int cycleNum = (mergeTotalT)/(4*mergeDelay);
					specialMoves[mode].addSMove(t,i,j,board,3,cycleNum*4,mergeDelay);
				}

				if(merge == 3){
					buttonCells[i][j].setIcon(new ImageIcon("4.png"));
					int cycleNum = (mergeTotalT)/(4*mergeDelay);
					specialMoves[mode].addSMove(t,i,j,board,4,cycleNum*4,mergeDelay);
				}
				
				
			}

			for(int a = 0; a < 4;a++){
				int rPrev = previous_moves[mode][a][0];
				int cPrev = previous_moves[mode][a][1];
				buttonCells[rPrev][cPrev].setText("");
				buttonCells[rPrev][cPrev].setIcon(null);
			}

			for(int a = 0; a < (dimension_row*12);a++){
				for(int b=0;b<(dimension_col * 12);b++){
					for (ActionListener B : buttonCells[a][b].getActionListeners()){
						buttonCells[a][b].removeActionListener(B);
					}
					buttonCells[a][b].addActionListener(new BListener(a,b,jFrame,jsp,jp));
				}
				
			}

			for(int a = 0; a < (dimension_row* 12 + 2);a++){
				for(int b=0;b<(dimension_col * 12 + 2);b++){
					for (ActionListener B : specialButtons[a][b].getActionListeners()){
						specialButtons[a][b].removeActionListener(B);
					}
				}
				
			}

			
			int r = previous_moves[mode][4][0];
			int c = previous_moves[mode][4][1];
			deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame,jp);
			r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
			c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

			previous_moves[mode][4][0] = r;
			previous_moves[mode][4][1] = c;
			printRobot(buttonCells,1);
		
			for(int a= 0; a < (dimension_row* 12 +2);a++){
				for(int b=0;b<(dimension_col * 12 + 2);b++){
					specialButtons[i][j].setText("");
					specialButtons[i][j].setIcon(null);
					jFrame.remove(specialButtons[a][b]);
				}
			}
			insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp,jp);
			
			counter[mode]++;
		
			insertArrows (r,  c, buttonCells);

			jlabel.setText("");
			
			jFrame.add(jsp);
			autoPrint(jFrame);
			
		}
	}

	//adds mixing to the robot storage array and reprints the board
	static boolean mergeFunc(int i , int j,JFrame jFrame,JScrollPane jsp){

			//System.out.println("merge");
	
			boolean returnThing = true;
			if(merge != -1){
				int cycleNum = (mergeTotalT)/(4*mergeDelay);
	
				if(cycleNum == 0){
					returnThing = false;
					
				}
	
				int counting = 0;
				if (merge == 0) {
					if( ((i>=0 && i<=(dimension_row*12-1)) && (j-1>=0&&j-1<=(dimension_col*12-1))) && ((i-1>=0 && i-1<=(dimension_row*12-1)) && (j-1>=0&&j-1<=(dimension_col*12-1))) && ((i-1>=0 && i-1<=(dimension_row*12-1)) && (j>=0&&j<=(dimension_col*12-1)))){
						for(int a = 0; a < cycleNum; a++){
	
							Speeds[mode].addSpeed(mergeDelay, -1);
								RobotClass[mode].addMoves(i,j-1,-1);
							Speeds[mode].addSpeed(mergeDelay, -1);
								RobotClass[mode].addMoves(i-1,j-1,-1);
							Speeds[mode].addSpeed(mergeDelay, -1);
								RobotClass[mode].addMoves(i-1,j,-1);
							Speeds[mode].addSpeed(mergeDelay, -1);
								RobotClass[mode].addMoves(i,j,-1);
							
						}
	
						counting++;
	
					}
					
				}
	
				if (merge == 1) {
					if( ((i>=0 && i<=(dimension_row*12-1)) && (j+1>=0&&j+1<=(dimension_col*12-1))) && ((i-1>=0 && i-1<=(dimension_row*12-1)) && (j+1>=0&&j+1<=(dimension_col*12-1))) && ((i-1>=0 && i-1<=(dimension_row*12-1)) && (j>=0&&j<=(dimension_col*12-1)))){
						for(int a = 0; a < cycleNum; a++){
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i,j+1,-1);				
	
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i-1,j+1,-1);						
	
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i-1,j,-1);						
	
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i,j,-1);
							
						}
	
						counting++;
	
						
					}
	
					
				}
	
				if (merge == 2) {
					if( ((i>=0 && i<=(dimension_row*12-1)) && (j+1>=0&&j+1<=(dimension_col*12-1))) && ((i+1>=0 && i+1<=(dimension_row*12-1)) && (j+1>=0&&j+1<=(dimension_col*12-1))) && ((i+1>=0 && i+1<=(dimension_row*12-1)) && (j>=0&&j<=(dimension_col*12-1)))){
						for(int a = 0; a < cycleNum; a++){
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i,j+1,-1);							
	
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i+1,j+1,-1);					
	
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i+1,j,-1);						
	
							Speeds[mode].addSpeed(mergeDelay, -1);						
								RobotClass[mode].addMoves(i,j,-1);
							
						}
	
						counting++;
	
						
					}
				}
	
				if (merge == 3) {
					if( ((i>=0 && i<=(dimension_row*12-1)) && (j-1>=0&&j-1<=(dimension_col*12-1))) && ((i+1>=0 && i+1<=(dimension_row*12-1)) && (j-1>=0&&j-1<=(dimension_col*12-1))) && ((i+1>=0 && i+1<=(dimension_row*12-1)) && (j>=0&&j<=(dimension_col*12-1)))){
						for(int a = 0; a < cycleNum; a++){
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i,j-1,-1);							
	
							Speeds[mode].addSpeed(mergeDelay, -1);							
								RobotClass[mode].addMoves(i+1,j-1,-1);							
	
							Speeds[mode].addSpeed(mergeDelay, -1);						
								RobotClass[mode].addMoves(i+1,j,-1);							
	
							Speeds[mode].addSpeed(mergeDelay, -1);						
								RobotClass[mode].addMoves(i,j,-1);
							
						}
	
						counting++;
	
					}
				}
	
				if(counting == 0){
					returnThing = false;
				}
	
				
			}
			return returnThing;
		}
    /* insertSpecialButton */

	//adds a button listener for special moves (including mixing arrow) onto a grid cell that a user can press
	static void insertSpecialButton(int r, int c, JButton[][] specialButtons, JButton[][] buttonCells, JFrame jFrame, JScrollPane jsp, JPanel jp)
        {
			deleteSpecialButton(r, c, specialButtons, buttonCells, jFrame,jp);

			//System.out.println("task");
				int val_x, val_y;	
				if(r==0 && c==0){
				       
				specialButtons[0][0].setIcon(null);
				specialButtons[0][0].setIcon(new ImageIcon((merge+1)+".png"));
				specialButtons[0][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame,jsp,jp));
				specialButtons[0][2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2,buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,3, buttonCells,jFrame,jsp,jp));
				specialButtons[2][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp,jp));

				specialButtons[0][0].setBounds(3 * base, 3 *base, base, base);
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

			else if(r==0 && c==(dimension_col * 12-1)){
			
				specialButtons[0][(dimension_col * 12-1)].setIcon(null);
				specialButtons[0][(dimension_col * 12-1)].setIcon(new ImageIcon((merge+1)+".png"));
				

				specialButtons[0][(dimension_col * 12-1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame, jsp,jp));
				specialButtons[0][(dimension_col * 12+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2, buttonCells,jFrame, jsp,jp));
				specialButtons[2][(dimension_col * 12+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame, jsp,jp));
				buttonCells[r+1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,4, buttonCells,jFrame, jsp,jp));
				
						
				val_x = (labelgap+c)*base + (base/10)*((c/12));			
				val_y = 0;
							
				specialButtons[0][(dimension_col * 12-1)].setBounds(val_x , (val_y)*base ,base, base);
				
				specialButtons[0][(dimension_col * 12-1)].setBorder(BorderFactory.createEmptyBorder());
				specialButtons[0][(dimension_col * 12-1)].setBackground(Color.GRAY);
				upPanel.add(specialButtons[0][(dimension_col * 12-1)]);
				

				previous_moves[mode][5][0] = 0;
				previous_moves[mode][5][1] = (dimension_col * 12-1);

				previous_moves[mode][6][0] = 0;
				previous_moves[mode][6][1] = (dimension_col * 12+1);

				previous_moves[mode][7][0] = 2;
				previous_moves[mode][7][1] = (dimension_col * 12+1);

				previous_moves[mode][8][0] = r+1;
				previous_moves[mode][8][1] = c-1;
				
			}

			else if(r==(dimension_row * 12-1) && c==0){
				specialButtons[(dimension_row * 12-1)][0].setIcon(null);
				specialButtons[(dimension_row * 12-1)][0].setIcon(new ImageIcon((merge+1)+".png"));
				

				specialButtons[(dimension_row * 12-1)][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame,jsp,jp));
				buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2, buttonCells,jFrame,jsp,jp));
				specialButtons[(dimension_row * 12+1)][2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp,jp));
				specialButtons[(dimension_row * 12+1)][0].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp,jp));
				
				val_x = 0;
				
				val_y = (labelgap+r)*base + (base/10)*((r/12));
						
				specialButtons[(dimension_row * 12-1)][0].setBounds(val_x , (val_y) ,base, base);
				specialButtons[(dimension_row * 12-1)][0].setBorder(BorderFactory.createEmptyBorder());
				specialButtons[(dimension_row * 12-1)][0].setBackground(Color.GRAY);
				specialButtons[(dimension_row * 12-1)][0].setOpaque(false);
				leftPanel.add(specialButtons[(dimension_row * 12-1)][0]);
				

				previous_moves[mode][5][0] = (dimension_row * 12-1);
				previous_moves[mode][5][1] = 0;

				previous_moves[mode][6][0] = r-1;
				previous_moves[mode][6][1] = c+1;

				previous_moves[mode][7][0] = (dimension_row * 12+1);
				previous_moves[mode][7][1] = 2;

				previous_moves[mode][8][0] = (dimension_row * 12+1);
				previous_moves[mode][8][1] = 0;

				
			}

			else if(r==(dimension_row * 12-1) && c==(dimension_col * 12-1)){

				buttonCells[r-1][c-1].setIcon(null);
				buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));
			

				buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1, buttonCells,jFrame,jsp,jp));
				specialButtons[(dimension_row * 12-1)][(dimension_col * 12+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2, buttonCells,jFrame,jsp,jp));
				specialButtons[(dimension_row * 12+1)][(dimension_col * 12+1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp,jp));
				specialButtons[(dimension_row * 12+1)][(dimension_col * 12-1)].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp,jp));

			
				previous_moves[mode][5][0] = r-1;
				previous_moves[mode][5][1] = c-1;

				previous_moves[mode][6][0] = (dimension_row * 12-1);
				previous_moves[mode][6][1] = (dimension_col * 12+1);

				previous_moves[mode][7][0] = (dimension_row * 12+1);
				previous_moves[mode][7][1] = (dimension_col * 12+1);

				previous_moves[mode][8][0] = (dimension_row * 12+1);
				previous_moves[mode][8][1] = (dimension_col * 12-1);

				
			}

			else if(r==0){
				specialButtons[r][c].setIcon(null);
				specialButtons[r][c].setIcon(new ImageIcon((merge+1)+".png"));
				


				specialButtons[r][c].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,2,1, buttonCells,jFrame,jsp,jp));
				specialButtons[r][c+2].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,2,2, buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c+1].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,1,3, buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c-1].addActionListener(new specialButtonListener(RobotClass[mode].storedMoves.size()-1,r,c,1,4, buttonCells,jFrame,jsp,jp));

				val_x = (labelgap+c)*base + (base/10)*((c/12));
							
				val_y = 0;
						
				specialButtons[r][c].setBounds(val_x, (val_y)*base ,base, base);
				
				specialButtons[r][c].setBorder(BorderFactory.createEmptyBorder());
			
				specialButtons[r][c].setMargin( new Insets(4, 4, 4, 4) );
				specialButtons[r][c].setBackground(Color.GRAY);
				specialButtons[r][c].setOpaque(false);
				specialButtons[r][c].setFont(new Font("Calibri", Font.PLAIN,font));
				
			
				upPanel.add(specialButtons[r][c]);

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
			
				specialButtons[r][c].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,1, buttonCells,jFrame,jsp,jp));
				buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2, buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,3, buttonCells,jFrame,jsp,jp));
				specialButtons[r+2][c].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp,jp));

									
				val_x = 0;					
				
				val_y = (labelgap+r)*base + (base/10)*((r/12));
				specialButtons[r][c].setBorder(BorderFactory.createEmptyBorder());
				specialButtons[r][c].setBackground(Color.GRAY);
				specialButtons[r][c].setOpaque(false);
								
				specialButtons[r][c].setBounds(val_x*base , (val_y) ,base, base);
				
				leftPanel.add(specialButtons[r][c]);
				
				previous_moves[mode][5][0] = r;
				previous_moves[mode][5][1] = c;

				previous_moves[mode][6][0] = r-1;
				previous_moves[mode][6][1] = c+1;

				previous_moves[mode][7][0] = r+1;
				previous_moves[mode][7][1] = c+1;

				previous_moves[mode][8][0] = r+2;
				previous_moves[mode][8][1] = c;
			}

			else if(r==(dimension_row * 12-1)){
				buttonCells[r-1][c-1].setIcon(null);
				buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));
			

				buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1, buttonCells,jFrame,jsp,jp));
				buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2, buttonCells,jFrame,jsp,jp));
				specialButtons[r+2][c+2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp,jp));
				specialButtons[r+2][c].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,4, buttonCells,jFrame,jsp,jp));

				previous_moves[mode][5][0] = r-1;
				previous_moves[mode][5][1] = c-1;

				previous_moves[mode][6][0] = r-1;
				previous_moves[mode][6][1] = c+1;

				previous_moves[mode][7][0] = r+2;
				previous_moves[mode][7][1] = c+2;

				previous_moves[mode][8][0] = r+2;
				previous_moves[mode][8][1] = c;
				
			}

			else if(c==(dimension_col * 12-1)){
				buttonCells[r-1][c-1].setIcon(null);
				buttonCells[r-1][c-1].setIcon(new ImageIcon((merge+1)+".png"));			

				buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1, buttonCells,jFrame,jsp,jp));
				specialButtons[r][c+2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,2, buttonCells,jFrame,jsp,jp));
				specialButtons[r+2][c+2].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,2,3, buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,4, buttonCells,jFrame,jsp,jp));

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
			
				buttonCells[r-1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,1,buttonCells,jFrame,jsp,jp));
				buttonCells[r-1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,2,buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c+1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,3,buttonCells,jFrame,jsp,jp));
				buttonCells[r+1][c-1].addActionListener(new specialButtonListener(Speeds[mode].storedSpeeds.size()-1,r,c,1,4,buttonCells,jFrame,jsp,jp));

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
	
	//deletes a button listener for special moves (including mixing arrow) from the previous grid move
	static void deleteSpecialButton (int r, int c, JButton[][] specialButtons, JButton[][] buttonCells, JFrame jFrame, JPanel jp)
    
	{

	
		if(r==0 && c==0){
			
			specialButtons[0][0].setText("");

			specialButtons[0][0].setIcon(null);
			jFrame.remove(specialButtons[0][0]);
			jFrame.remove(specialButtons[0][2]);
			jFrame.remove(specialButtons[2][0]);			
		}

		else if(r==0 && c==(dimension_col * 12-1)){
			specialButtons[0][(dimension_col * 12-1)].setText("");			

			specialButtons[0][(dimension_col * 12-1)].setIcon(null);
			jFrame.remove(specialButtons[0][(dimension_col * 12-1)]);
			jFrame.remove(specialButtons[0][(dimension_col * 12+1)]);
			jFrame.remove(specialButtons[2][(dimension_col * 12-1)]);
            upPanel.remove(specialButtons[0][(dimension_col * 12-1)]);
			upPanel.remove(specialButtons[0][(dimension_col * 12+1)]);
			upPanel.remove(specialButtons[2][(dimension_col * 12-1)]);
			
			
		}

		else if(r==(dimension_row * 12-1) && c==0){
			specialButtons[(dimension_row * 12-1)][0].setText("");
			specialButtons[(dimension_row * 12-1)][0].setIcon(null);
			
			jFrame.remove(specialButtons[(dimension_row * 12-1)][0]);
			jFrame.remove(specialButtons[(dimension_row * 12+1)][2]);
			jFrame.remove(specialButtons[(dimension_row * 12+1)][0]);
            leftPanel.remove(specialButtons[(dimension_row * 12-1)][0]);
			leftPanel.remove(specialButtons[(dimension_row * 12+1)][2]);
			leftPanel.remove(specialButtons[(dimension_row * 12+1)][0]);
		
			
		}

		else if(r==(dimension_row * 12-1) && c==(dimension_col * 12-1)){
			buttonCells[r-1][c-1].setText("");
			buttonCells[r-1][c-1].setIcon(null);
			jFrame.remove(specialButtons[(dimension_row * 12-1)][(dimension_col * 12+1)]);
			jFrame.remove(specialButtons[(dimension_row * 12+1)][(dimension_col * 12+1)]);
			jFrame.remove(specialButtons[(dimension_row * 12+1)][(dimension_col * 12-1)]);

		}

		else if(r==0){
			specialButtons[r][c].setText("");
			jFrame.remove(specialButtons[r][c]);
			jFrame.remove(specialButtons[r][c+2]);
            upPanel.remove(specialButtons[r][c]);
			upPanel.remove(specialButtons[r][c+2]);
			
									
		}

		else if(c==0){
			specialButtons[r][c].setText("");
			specialButtons[r][c].setIcon(null);
			jFrame.remove(specialButtons[r][c]);
			jFrame.remove(specialButtons[r+2][c]);
            leftPanel.remove(specialButtons[r][c]);
			leftPanel.remove(specialButtons[r+2][c]);

		}

		else if(r==(dimension_row * 12-1)){
			buttonCells[r-1][c-1].setText("");

			buttonCells[r-1][c-1].setIcon(null);
			jFrame.remove(specialButtons[r+2][c+2]);
			jFrame.remove(specialButtons[r+2][c]);

			
		}

		else if(c==(dimension_col * 12-1)){
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

   	//displays arrows for users to press for the robot
    static void insertArrows (int r, int c, JButton[][] buttonCells)
	
    {
		if((r-1)>=0 && (dimension_row * 12-1)>=(r-1) && (c)>=0&&(dimension_col * 12-1)>=(c)){
			if(checkSquare(r-1,c)){
				buttonCells[r-1][c].setIcon(null);
				buttonCells[r-1][c].setText("\u2191");
			

				previous_moves[mode][0][0] = r-1;
				previous_moves[mode][0][1] = c;
				
			}
		}
				

				
		if((r)>=0 && (dimension_row * 12-1)>=(r) && (c+1)>=0&&(dimension_col * 12-1)>=(c+1)){
			if(checkSquare(r,c+1)){
				buttonCells[r][c+1].setIcon(null);
			buttonCells[r][c+1].setText("\u2192 ");
			previous_moves[mode][1][0] = r;
			previous_moves[mode][1][1] = c+1;
			}
		}
	


		if((r)>=0 && (dimension_row * 12-1)>=(r) && (c-1)>=0&&(dimension_col * 12-1)>=(c-1)){
			if(checkSquare(r,c-1)){
				buttonCells[r][c-1].setIcon(null);
				buttonCells[r][c-1].setText("\u2190");
				previous_moves[mode][2][0] = r;
				previous_moves[mode][2][1] = c-1;
			}
		}
	

	
		if((r+1)>=0 && (dimension_row * 12-1)>=(r+1) && (c)>=0&&(dimension_col * 12-1)>=(c)){
			if(checkSquare(r+1,c)){
				buttonCells[r+1][c].setIcon(null);
				buttonCells[r+1][c].setText("\u2193");
				previous_moves[mode][3][0] = r+1;
				previous_moves[mode][3][1] = c;
			}
		}



	}
	/* printRobot */

	//reprints the board
	static void printRobot(JButton[][] buttonCells, int f)
	
	{
		int max = 0;
		ArrayList<Integer> totalThings = new ArrayList<Integer>();
		for(int i = 0; i < RobotClass.length;i++){
			Integer thingy = 0;
			for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
				thingy+=Speeds[i].getSpeed(j);
			}
			totalThings.add(thingy);
		}

		for(int i = 0; i < RobotClass.length;i++){
			if(totalThings.get(i) > max) max = totalThings.get(i);
		}


		int d = 0;
		if (mode == -1) return;
			for(int i = 0; i < RobotClass[mode].storedMoves.size();i++){
				Robot x = RobotClass[mode];
				int index = i;
				int speedNum = Speeds[mode].storedSpeeds.get(i)[1];

					if(index == -1) continue;

					if (speedNum == 3){
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setBackground(Color.YELLOW);
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
						
					}

					else if (speedNum == 2){
						buttonCells[x.storedMoves.get(index)[0]][x.storedMoves.get( index)[1]].setBackground(new Color(56,202,109));
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
					}

					else if (speedNum == 1){
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get( index)[1]].setBackground(new Color(0,155,200));
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
					}

					else if(speedNum == 0 ){
						buttonCells[x.storedMoves.get(index)[0]][x.storedMoves.get( index)[1]].setBackground(Color.RED);
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);

					}

					else{
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setBackground(Color.LIGHT_GRAY);
						buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
					}

				

				
					if(Speeds[mode].getNum(index)==-1){
					
						
						if(specialMoves[mode].storedSMoves.get(d)[4]==1){
							buttonCells[specialMoves[mode].storedSMoves.get(d)[1]][specialMoves[mode].storedSMoves.get(d)[2]].setIcon(new ImageIcon("1.png"));
						}
						else if(specialMoves[mode].storedSMoves.get(d)[4]==2){
							buttonCells[specialMoves[mode].storedSMoves.get(d)[1]][specialMoves[mode].storedSMoves.get(d)[2]].setIcon(new ImageIcon("2.png"));
						}
						else if(specialMoves[mode].storedSMoves.get(d)[4]==3){
							buttonCells[specialMoves[mode].storedSMoves.get(d)[1]][specialMoves[mode].storedSMoves.get(d)[2]].setIcon(new ImageIcon("3.png"));
						}
						else if(specialMoves[mode].storedSMoves.get(d)[4]==4){
							buttonCells[specialMoves[mode].storedSMoves.get(d)[1]][specialMoves[mode].storedSMoves.get(d)[2]].setIcon(new ImageIcon("4.png"));
						}
						d++;
					}
				}

	}
	
	//prints automation
	static void autoPrint(JFrame jFrame)	
	{
		jlabel.setFont(new Font("Calibri", Font.PLAIN,font));
		
		if(labelOutput()!=null) {
			String[][] print = labelOutput();
			
			String output ="" ;
			
			for(int i = 0; i < print.length;i++){
				String output1 = "" ;
				String out1 = "";

				for(int j = 0; j <print[i].length-1;j++){
					output1 += print[i][j] + ",";
				}


				String out = "";
				if(Speeds[mode].getNum(i) != -1){
					String input1 = symbols[Speeds[mode].getNum(i)][0];
					String input2 = symbols[Speeds[mode].getNum(i)][1];
					out = input1 + "," + input2 + ",";
				}

				else{
					int thing = Integer.parseInt(print[i][width*length]);
					int second = thing / 1000;
					int centiSec = (thing - second * 1000) / 10;

					out = second + "," + centiSec + ",";
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
	
	//prints column and row labels (for grids larger than 2x3)
	private static void drawLabelByInput(JFrame jFrame, int row, int col){
		//create array of labelbutton
		int addR = 0;
		
		
		for(int i = 0; i < row; i++){
			int addC = 1;
			for(int j = 0; j <12; j++){
				labelCellsRow[i][j] = new JButton();
				labelCellsRow[i][j].setMargin(new Insets(0, 0, 0, 0));
				labelCellsRow[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
				labelCellsRow[i][j].setMargin( new Insets(4, 4, 4, 4) );
				labelCellsRow[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				labelCellsRow[i][j].setBackground(Color.LIGHT_GRAY);
				labelCellsRow[i][j].setOpaque(true);
                labelCellsRow[i][j].setText("");

				if((j+1)%12 == 1){
					addC++;
				}

				labelCellsRow[i][j].setBounds((2+i*12+j+1)*base + i * (base/10) +  (base/10)*addC-base/10, base ,base, base);
				jFrame.add(labelCellsRow[i][j]);
			
			}

			
		}
		for(int i = 0; i < col; i++){
			int addC = 1;

		
			for(int j = 0; j <12; j++){
				labelCellsCol[i][j] = new JButton();
				labelCellsCol[i][j].setMargin(new Insets(0, 0, 0, 0));
				labelCellsCol[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
				labelCellsCol[i][j].setMargin( new Insets(4, 4, 4, 4) );
				labelCellsCol[i][j].setBackground(Color.LIGHT_GRAY);
				labelCellsCol[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				labelCellsCol[i][j].setOpaque(true);
                labelCellsCol[i][j].setText("");

				if((j+1)%12 == 1){
					addC++;
				}

				labelCellsCol[i][j].setBounds((1)*base, (2+i*12+j+1)*base +   i* (base/10) + (base/10)*addC- base/10, base, base);
				jFrame.add(labelCellsCol[i][j]);
				
			
			}

			
		}
	}
	
	//prints column and row labels (set grids less than 2x3 in size)
	private static void drawLabel(JFrame jFrame){
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

				
				if (i==0) labelCellsRow[i][j].setBounds((3+j+1)*base + (base/10)*addC-base/10, 1*base ,base, base);
				if (i==1) labelCellsRow[i][j].setBounds((3+12+j+1)*base +base/10+ (base/10)*addC-base/10, base ,base, base);
				if (i==2) labelCellsRow[i][j].setBounds((3+24+j+1)*base + (base/10)*addC+base/10, base ,base, base);
				//if (i==2) labelCellsRow[i][j].setBounds((2+j+(i-1)/2*12+1)*base + (base/10)*addC, (i/2*26+2)*base ,base, base);
				// if (i==3) labelCellsRow[i][j].setBounds((2+j+(i-1)/2*12+1)*base + (base/10)*addC, (i/2*26+2)*base ,base, base);
				
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

				
				if (i==0) labelCellsCol[i][j].setBounds((1)*base, (3+j+1)*base +  (base/10)*addC -base/10,base, base);
				if (i==1) labelCellsCol[i][j].setBounds((1)*base, (3+12+j+1)*base + base/10 + (base/10)*addC-base/10, base, base);
				
				
				jFrame.add(labelCellsCol[i][j]);
				
			
			}

			
		}
	}
	

	//preview window that shows robot moving with correct time steps

	public static void createAndShowPreview(){

		jlabel.setFont(new Font("Calibri", Font.PLAIN,font));
		
		labelUp = new JButton[dimension_col][12];
		if (dimension_col>4)
         labelCellsRow = new JButton[dimension_col][12];
		 
		if(dimension_row>4) 
         labelCellsCol = new JButton[dimension_row][12];

		 //System.out.println("Row:"+dimension_row + "col:" +dimension_col);
		
		//set jFrame
		JFrame jFrame = new JFrame();
		jFrame.setLayout(new FlowLayout());
		
		jFrame.setSize((3-2) * 12 * base +base*45,base*35);
		jFrame.getContentPane().setBackground(Color.BLACK);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);

		labelgap = 2;
		base = (size.height/36)/3;
        int base2 = (size.height/36);
		if(Speeds[0].storedSpeeds.size() == 0){
			jFrame.setVisible(false);
			tickCounter = 0 ;
			ticks=0;
			if(programNum==0){
				programNum = -1;
			}
			title.setForeground(Color.BLACK);
		
			title.setBounds(dimension_col * 12 * base +5 * base2,base2*4+ base2*2+base2*7,base2*12+base2/2,base2+base2/10);
			jlabel.setForeground(Color.BLACK);
			title.setBackground(null);
				title.setOpaque(true);
			title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			
			jlabel.setBounds(dimension_col * 12 * base +5 * base2, base2*6 + base2/2, base2*6 + base2/2, base2*19);
			createAndShowGUI();
			jFrame.dispose();
			return;
		}

		title.setForeground(Color.WHITE);
		title.setBackground(Color.BLACK);
		title.setOpaque(true);

		jlabel.setForeground(Color.WHITE);
	

		//grid cell
		buttonCells = new JButton[(dimension_row*12)][(dimension_col*12)];
		jFrame.setLayout(null);

		JButton[] speedButtons = new JButton[4];
       
		
		int[] store = {Speeds[0].getNum(0)};
		


		//at every time point, we print the robot moveements on the grid and update the automation
		class TimerListener implements ActionListener{

			private int indexthing = -1;
			private String output = "";
			public TimerListener(){
               super();
			   ticks =0;
            }
			public void actionPerformed(ActionEvent e){
				ticks ++;
				// System.out.println("tick"+ ticks + "counter" + tickCounter);
				if(ticks %  2 == 0){
					tickCounter++;

					tickCounter*=10;

					int m = 0;
					ArrayList<Integer> totalThings = new ArrayList<Integer>();
					for(int i = 0; i < RobotClass.length;i++){
						Integer thingy = 0;
						for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
							thingy+=Speeds[i].getSpeed(j);
						}
						totalThings.add(thingy);
					}

					for(int i = 0; i < RobotClass.length;i++){
						if(totalThings.get(i) >  m) m = totalThings.get(i);
					}
					

					if(tickCounter > m || tickCounter < 0){
						return;
					}

					
					for(int i = 0; i < RobotClass.length;i++){
						Robot x = RobotClass[i];
								
						int index = -1;
						int speedNum = store[i];
						int total = 0;
				
						for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
							total += Speeds[i].getSpeed(j);
							if(total==tickCounter){
								speedNum = Speeds[i].getNum(j);
								index = j;
								
								if(j+1< Speeds[i].storedSpeeds.size()){
									
									store[i] = Speeds[i].getNum(j+1);
								}

								break;
								
							}

							
						}

							if(index == -1) continue;

							if (speedNum == 3){
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setBackground(Color.YELLOW);
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
								
							}

							else if (speedNum == 2){
								buttonCells[x.storedMoves.get(index)[0]][x.storedMoves.get( index)[1]].setBackground(new Color(56,202,109));
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
							}

							else if (speedNum == 1){
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get( index)[1]].setBackground(new Color(0,155,200));
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
							}

							else if(speedNum == 0 ){
								buttonCells[x.storedMoves.get(index)[0]][x.storedMoves.get( index)[1]].setBackground(Color.RED);
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);

							}

							else{
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setBackground(Color.LIGHT_GRAY);
								buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setOpaque(true);
							}

							for(int a = 0; a < (dimension_row*12); a++){
								for(int b = 0; b < (dimension_col*12);b++){
									buttonCells[a][b].setText("");
									buttonCells[a][b].setIcon(null);
								}
							}
							
							buttonCells[x.storedMoves.get( index)[0]][x.storedMoves.get(index)[1]].setText("");

						
					}
					output = "";
					String[][] inputArray = labelOutput();
					int max = m;

					int total = 0;

					for(int j = 0; j < Speeds[mode].storedSpeeds.size();j++){
						total += Speeds[mode].getSpeed(j);
						if(total==tickCounter){
							
							indexthing = j;							
							break;
							
						}
						
					}
							
					for(int i = 0; i < indexthing+1;i++){

						

						String output1 = "" ;
						String out1 = "";

						for(int j = 0; j <inputArray[i].length-1;j++){
							output1 += inputArray[i][j] + ",";
						}


						String out = "";
						
						int thing = Integer.parseInt(inputArray[i][width*length]);
						int second = thing / 1000;
						int centiSec = (thing - second * 1000) / 10;

						out = second + "," + centiSec + ",";
						
						if(i>0){
							out1= "\n" + output1 +  out;
						}

						else{
							out1= output1 +  out;
						}

						output+=out1;
										
										
					}
						jlabel.setText(output);
					
					

					tickCounter/=10;

					jFrame.repaint();
					
				}

				
			}
		}
		
	
		//switches back to the creation board
		class gameSwitch implements ActionListener{

			public gameSwitch(){
				super();
			}

			public void actionPerformed(ActionEvent e){
				t.stop();
				base = (size.height/36);
				labelgap = -1;
				createAndShowGUI();
				jFrame.dispose();
				tickCounter = 0 ;
				ticks=0;
				title.setForeground(Color.BLACK);
				
				title.setBounds(dimension_col * 12 * base +5 * base2,base2*4+ base2*2+base2*7,base2*12+base2/2,base2+base2/10);
				jlabel.setForeground(Color.BLACK);
				title.setBackground(null);
				title.setOpaque(true);
				title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
				
				jlabel.setBounds(dimension_col * 12 * base +5 * base2, base2*6 + base2/2, base2*6 + base2/2, base2*19);
				
				
		    }

		}

		
		jlabel.setBounds(dimension_col * 12 * base +5 * base2, base2*5 + base2/2, base2*6 + base2/2, base2*20);
	
	    title.setBounds(dimension_col * 12 * base +5 * base2, base2*4 + base2*2,base2*12+base2/2,base2+base2/10);
        for(int i = 0; i < speedButtons.length;i++){
			speedButtons[i] = new JButton();
			speedButtons[i].setMargin(new Insets(0, 0, 0, 0));
			speedButtons[i].setFont(new Font("Calibri", Font.PLAIN,font));
			
			speedButtons[i].setBounds(dimension_col * 12 * base +9 * base2, base2 +i*base2, base2, base2);
			speedButtons[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			jFrame.add(speedButtons[i]);
		}

		speedButtons[0].setBackground(Color.RED);
		speedButtons[1].setBackground(new Color(0,155,200));
		speedButtons[2].setBackground(new Color(56,202,109));
		speedButtons[3].setBackground(Color.YELLOW);

		speedButtons[0].setOpaque(true);
		speedButtons[1].setOpaque(true);
		speedButtons[2].setOpaque(true);
		speedButtons[3].setOpaque(true);
		
		t = new Timer(1, new TimerListener());
		
		ticks=0;
		tickCounter=0;
		t.stop();
		t.start();

		JTextField[] speedField = new JTextField[4];
		JTextField[] speedField2 = new JTextField[4];

		for(int i = 0; i < speedField.length;i++){
			speedField[i] = new JTextField();
			speedField[i].setEditable(false);
			speedField[i].setText(speedValues[i]/1000 + "");
			speedField[i].setHorizontalAlignment(JTextField.CENTER);
			speedField[i].setFont(new Font("Calibri", Font.PLAIN,font));
			
			speedField[i].setBounds(dimension_col * 12 * base +10 * base2, base2 +i*base2, base2, base2);
			speedField[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			jFrame.add(speedField[i]);
		}

		for(int i = 0; i < speedField2.length;i++){
			speedField2[i] = new JTextField();
			speedField2[i].setEditable(false);
			speedField2[i].setDocument(new JTextFieldLimit(2));

			if(((speedValues[i]%1000)/10 + "").length() < 2){
				speedField2[i].setText("0" + (speedValues[i]%1000)/10 + "");
			}

			else {
				speedField2[i].setText((speedValues[i]%1000)/10 + "");
			}
			speedField2[i].setHorizontalAlignment(JTextField.CENTER);
			speedField2[i].setFont(new Font("Calibri", Font.PLAIN,font));
		
			speedField2[i].setBounds(dimension_col * 12 * base +11 * base2, base2 +i*base2, base2, base2);
			speedField2[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			
			jFrame.add(speedField2[i]);
		}

		JTextPane timeLabel = new JTextPane();
		timeLabel.setEditable(false); // as before
		timeLabel.setBorder(null); // remove the border
		
		timeLabel.setBounds(dimension_col * 12 * base +10 * base2,base2/4, base2*2, base2/2 + base2/4);
		StyledDocument doc5= timeLabel.getStyledDocument();
		SimpleAttributeSet center5 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center5, StyleConstants.ALIGN_CENTER);
		doc5.setParagraphAttributes(0, doc5.getLength(), center5, false);
		timeLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
		timeLabel.setFont(new Font("Calibri", Font.PLAIN,font));
		timeLabel.setBackground(Color.LIGHT_GRAY);
		timeLabel.setOpaque(true);
		timeLabel.setText("Time");

		jFrame.add(timeLabel);


		JTextField[] timeUnits = new JTextField[4];

		for(int i = 0; i < timeUnits.length;i++){
			timeUnits[i] = new JTextField();
			timeUnits[i].setEditable(false); // as before
			timeUnits[i].setBorder(null); // remove the border
			
			timeUnits[i].setBounds(dimension_col * 12 * base +12 * base2, base2 + i*base2, base2+base2/2+base2/4, base2 );
			timeUnits[i].setHorizontalAlignment(JTextField.CENTER);
		
			timeUnits[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			timeUnits[i].setFont(new Font("Calibri", Font.PLAIN,font));
			timeUnits[i].setBackground(Color.LIGHT_GRAY);
			timeUnits[i].setOpaque(true);
			timeUnits[i].setText("0 (ms)");
			jFrame.add(timeUnits[i]);
		}


		//create array of buttons
		int addR = 0;
		for(int i = 0; i < (dimension_row * 12 ); i++){
			int addC = 0;

			if((i+1)%12 == 1){
					addR++;
			}
			for(int j = 0; j < (dimension_col * 12 ); j++){
				buttonCells[i][j] = new JButton();
				buttonCells[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				buttonCells[i][j].setMargin(new Insets(0, 0, 0, 0));
				buttonCells[i][j].setBackground(Color.WHITE);
				buttonCells[i][j].setOpaque(true);
				buttonCells[i][j].setHorizontalAlignment(JButton.CENTER);
				buttonCells[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
				

				if((j+1)%12 == 1){
					addC++;
				}

				
				buttonCells[i][j].setBounds((labelgap+j+1)*base + (base/10)*addC, (labelgap+i+1)*base + (base/10)*addR,base, base);
				
				jFrame.add(buttonCells[i][j]);
				
				if(j==(dimension_col * 12 -1) && i == 0){
					totalX = (j+1)*base + (base/2)*addC;
					totalY = (i+1)*base + (base/2)*addR;
				}
			}

			
		}
      
		JScrollPane jsp = new JScrollPane(jlabel);
		
		jsp.setBounds(dimension_col * 12 * base +5 * base2, base2*7 + base2/2, base2*12 + base2/2, base2*19);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.getViewport().setBackground(Color.BLACK);
		jsp.getViewport().setOpaque(true);

		Synchronizer synchronizer = new Synchronizer(jsp,title);
		jsp.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
		title.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
		jFrame.add(jsp);
		jFrame.add(title);

		jFrame.add(jsp);
		jFrame.add(title);

        //drawLabel(jFrame);
		drawLabelByInput(jFrame,   dimension_col, dimension_row);

    

		JButton buttonSwitch = new JButton("Return");
		buttonSwitch.setMargin(new Insets(0, 0, 0, 0));

		buttonSwitch.addActionListener(new gameSwitch());
		buttonSwitch.setBackground(new Color(4,92,140));
		buttonSwitch.setBorder(BorderFactory.createLineBorder(Color.gray));
		buttonSwitch.setOpaque(true);
		buttonSwitch.setFont(new Font("Calibri", Font.PLAIN,font));
	
		buttonSwitch.setBounds(dimension_col * 12 * base +5 * base2, 1 * base2, base2*3, base2);
		
		buttonSwitch.setForeground(Color.WHITE);

		JButton RobotA = new JButton("Ferrobot");
		RobotA.setMargin(new Insets(0, 0, 0, 0));
		JButton RobotB = new JButton("Ferrobot 2");
		RobotB.setMargin(new Insets(0, 0, 0, 0));
		JButton RobotC = new JButton("Ferrobot 3");
		RobotC.setMargin(new Insets(0, 0, 0, 0));

		RobotA.setBackground(Color.WHITE);
		RobotB.setBackground(Color.WHITE);
		RobotC.setBackground(Color.WHITE);

		RobotA.setOpaque(true);
		RobotB.setOpaque(true);
		RobotC.setOpaque(true);

		RobotA.setBorder(BorderFactory.createLineBorder(Color.gray));
		RobotB.setBorder(BorderFactory.createLineBorder(Color.gray));
		RobotC.setBorder(BorderFactory.createLineBorder(Color.gray));

		RobotA.setBounds( dimension_col* 12 * base +5 * base2,2 * base2 , base2*3, base2);
		
		RobotB.setBounds((3-2) * 12 * base +29 *base, 3 * base, base*3, base);
		
		RobotC.setBounds((3-2) * 12 * base +29 *base, 4 * base , base*3, base);

		RobotA.setFont(new Font("Calibri", Font.PLAIN,font));
		RobotB.setFont(new Font("Calibri", Font.PLAIN,font));
		RobotC.setFont(new Font("Calibri", Font.PLAIN,font));

		
		jFrame.add(RobotA);
		jFrame.add(buttonSwitch);

		//upon close,prompt the user if they want the settings to be saved
		jFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			int confirmed = JOptionPane.showConfirmDialog(null, 
				"Do you want to save settings?", "Exit Program Message Box",
				JOptionPane.YES_NO_OPTION);
				if(confirmed == JOptionPane.YES_OPTION) {
					FileWriter geek_file;
					try
					{
						geek_file = new FileWriter("storage.txt");						
						// Initializing BufferedWriter
						BufferedWriter geekwrite = new BufferedWriter(geek_file);				
						
						// Use of write() method to write the value in 'ABC' file
						// Printing E
						geekwrite.write(symbols[0][0] + " " + symbols[0][1]);
						for(int i = 1; i <4;i++){
							geekwrite.write("\n" + symbols[i][0] + " " + symbols[i][1]);
						}

						for(int i = 0; i <4;i++){
							geekwrite.write("\n" +speedValues[i] +"");
						}

						geekwrite.write("\n" +mergeTotalT + "");
						geekwrite.write("\n" +mergeDelay + "");
						geekwrite.write("\n" + tutorial + "");
						geekwrite.write("\n" +font +"");
						geekwrite.write("\n" + 1);
						// Closing BufferWriter to end operation
						
						geekwrite.close();
						
					}
					catch (IOException except)
					{
						except.printStackTrace();
					} 
					jFrame.dispose(); 
				} 
				else { 
					jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					jFrame.dispose();  
				}

			
		}
		});


		//listener for a button when user wants to go to settings
		class switchListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				FileWriter geek_file;
				try
				{
					geek_file = new FileWriter("storage.txt", true);
					
					// Initializing BufferedWriter
					BufferedWriter geekwrite = new BufferedWriter(geek_file);
					geekwrite.write("\n" + 0);
		
					// Closing BufferWriter to end operation
					
					geekwrite.close();
					
				}
				catch (IOException except)
				{
					except.printStackTrace();
				} 


				tickCounter = 0 ;
				ticks=0;
				title.setForeground(Color.BLACK);
				
				title.setBounds(dimension_col* 12 * base +5 * base2,base2*4 + base2*2,base2*5,base2+base2/10);
				jlabel.setForeground(Color.BLACK);
				title.setBackground(null);
				title.setOpaque(true);
				title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			
				jlabel.setBounds(dimension_col* 12 * base +5 * base2, base2*6 + base2/2, base2*6 + base2/2, base2*19);


				controlSystem startUp = new controlSystem();
				startUp.runProgram();
				jFrame.dispose();

			}
		}

		JButton switchGrid = new JButton();
		switchGrid .setBounds(base,base, base, base);
		switchGrid.setHorizontalAlignment(JTextField.CENTER);
		switchGrid.setBackground(Color.white);
		switchGrid.setMargin(new Insets(0, 0, 0, 0));
		switchGrid .setBorder(BorderFactory.createLineBorder(Color.gray));
		switchGrid .setFont(new Font("Calibri", Font.PLAIN,font+5));
		switchGrid .setText("");
		switchGrid.setBackground(new Color(255, 250, 160));
		switchGrid.setOpaque(true);	
		
		switchGrid.addActionListener(new switchListener());
		jFrame.add(switchGrid);



		jFrame.setVisible(true);
	}


	//display the user creation board
	public static void createAndShowGUI() {
		programNum++;
		jlabel.setFont(new Font("Calibri", Font.PLAIN,font));
		
        labelUp = new JButton[dimension_col][12];


		//base dimsion
		Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();


		//set jFram
		JFrame jFrame = new JFrame();
		JPanel jp = new JPanel();
		
		jp.setLayout(null);
		jp.setBackground(null);
		jFrame.setLayout(new FlowLayout());
		jFrame.setSize((3-2) * 12 * base +base*46,base*36);
       
        jp.setPreferredSize(new Dimension( base * dimension_col*12 + (base/10)*(dimension_col+1), base * dimension_row*12 +  (base/10)*(dimension_row+1 )));
		

		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLocationRelativeTo(null);

		String setTexting = "";
		for(int i = 0; i < length*width;i++ ){
			setTexting += "U" + (i+1) + " ";
		}

		setTexting+="s  cs";

		JLabel titleText = new JLabel();
		title = new JScrollPane(titleText);

		titleText.setText(setTexting);
		
		titleText.setPreferredSize(new Dimension((base-base/10)*length*width,base+base/10));
		title.setBounds((3-2) * 12 * base +31*base,base*4+ base*2+base*7,base*12+base/2,base+base/4);
		title.setBorder(BorderFactory.createEmptyBorder());
		title.getViewport().setBackground(null);
		title.getViewport().setOpaque(true);
		title.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		title.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		titleText.setHorizontalAlignment(JLabel.LEFT);
	
	
		titleText.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
	
		titleText.setFont(new Font("Calibri", Font.PLAIN,font));
		JPanel macroPanel = new JPanel();
		macroPanel.setLayout(null);
		macroPanel.setBackground(null);
		macroPanel.setOpaque(true);
		
	
		JScrollPane macroScroll = new JScrollPane(macroPanel);
		macroScroll.setBounds((3-2) * 12 * base +31 *base, base*9+base/2+base -base, base*11-base*2, base+base+base/2);
		macroScroll.setBorder(BorderFactory.createEmptyBorder());
		macroScroll.getViewport().setBackground(null);
		macroScroll.getViewport().setOpaque(true);

		
		macroButtons.clear();

		macroTitle.setText("<html> Saved Macros <html>");
		macroTitle.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		macroTitle.setBounds((3-2) * 12 * base +31 *base,base*4+ base*2+base*3-base,base*9,base+base/10);
		macroTitle.setHorizontalAlignment(JLabel.CENTER);
		macroTitle.setFont(new Font("Calibri", Font.PLAIN,font));

		

		JButton deleteButton = new JButton();

		jFrame.add(title);
		jFrame.add(macroTitle);

		

		//create array of buttons
		buttonCells = new JButton[(dimension_row*12)][(dimension_col*12)];
        JTextField[] speedField = new JTextField[4];
		JTextField[] speedField2 = new JTextField[4];
		JTextField[] symbolField = new JTextField[4];
		JTextField[] symbolField2 = new JTextField[4];
		JButton[] speedButtons = new JButton[4];
		specialButtons = new JButton[(dimension_row*12+2)][(dimension_col*12+2)];

		JButton[] mergeDirect = new JButton[4];
		final JTextField mergeDelayField = new JTextField();
		final JTextField mergeTotalTField = new JTextField();
		JTextPane robotDirectory = new JTextPane();
		JLabel welcomeDirectory = new JLabel();
		JTextField fontPane = new JTextField();

		JTextPane symbolField1Label = new JTextPane();
		JTextPane symbolField2Label = new JTextPane();
		final JTextField[] timeUnits = new JTextField[4];
		JTextPane timeLabel = new JTextPane();

		JButton save = new JButton();
        JButton newFrame = new JButton();

        //declare selector for robots
		JButton[] RobotButtons = {new JButton("Ferrobot"),new JButton("Ferrobot 2"), new JButton("Ferrobot 3")};
		RobotButtons[0].setMargin(new Insets(0, 0, 0, 0));
		RobotButtons[1].setMargin(new Insets(0, 0, 0, 0));
		RobotButtons[2].setMargin(new Insets(0, 0, 0, 0));
        RobotButtons[0].setBorder(BorderFactory.createLineBorder(Color.gray));
		RobotButtons[1].setBorder(BorderFactory.createLineBorder(Color.gray));
		RobotButtons[2].setBorder(BorderFactory.createLineBorder(Color.gray));

		

        JScrollPane jsp = new JScrollPane(jlabel);

		Synchronizer synchronizer = new Synchronizer(jsp,title);
		jsp.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
		title.getHorizontalScrollBar().addAdjustmentListener(synchronizer);
		

		//listener for the speed buttons (colored)
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

					if(programNum==1 && tutorial ==1){

						jFrame.getContentPane().removeAll();
				
				        jFrame.setVisible(true);
						jFrame.repaint();
						
						jFrame.setSize((3-2) * 12 * base +base*46,base*36);
						jFrame.getContentPane().setBackground(Color.BLACK);
						jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						jFrame.setLocationRelativeTo(null);
						
                       // System.out.println("add scrollIP base:"+ base +"dimension_row: " + dimension_row + "dimension_col:" + dimension_col);
						
						JPanel jp1 = new JPanel();		               
						jp1.setLayout(null);
						jp1.setBackground(null);						
						jp1.setBounds(0, 0,base * dimension_col*12 + (base/10)*(dimension_col+1), base * dimension_row*12 +  (base/10)*(dimension_row+1));
						jp1.setVisible(true);
						
                        int addR=0;
						labelgap =-1;
						Dimension  size = Toolkit. getDefaultToolkit(). getScreenSize();
						base = (size.height/36);
						// System.out.println("add scrollIP base:"+ base +"dimension_row: " + dimension_row + "dimension_col:" + dimension_col);
						for(int i = 0; i < (dimension_row * 12 ); i++){
							int addC = 0;

							if((i+1)%12 == 1){
									addR++;
							}
							for(int j = 0; j < (dimension_col * 12 ); j++){
								buttonCells[i][j] = new JButton();
								buttonCells[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
								buttonCells[i][j].setMargin(new Insets(0, 0, 0, 0));
								buttonCells[i][j].setBackground(Color.WHITE);
								buttonCells[i][j].setOpaque(true);
								buttonCells[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
								buttonCells[i][j].setHorizontalAlignment(JButton.CENTER);
								buttonCells[i][j].setIcon(null);
								//buttonCells[i][j].setText("1");
							
						
								buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp,jp1));
								

								if((j+1)%12 == 1){
									addC++;
								}
							
								buttonCells[i][j].setBounds((labelgap+j+1)*base + (base/10)*addC, (labelgap+i+1)*base + (base/10)*addR,base, base);
								
								jp1.add(buttonCells[i][j]);
								
								if(j==(dimension_col * 12 -1) && i == 0){
									totalX = (j+1)*base + (base/2)*addC;
									totalY = (i+1)*base + (base/2)*addR;
								}
							}

			
						}
						
						
						JScrollPane scrollP1 = new JScrollPane();
						scrollP1 = new JScrollPane(jp1 );   
						
						//scrollP1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						//scrollP1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
						scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
						scrollP.setWheelScrollingEnabled(false);
						System.out.println(	"false:"+ scrollP.isWheelScrollingEnabled());
                        scrollP1.setBackground(Color.GREEN);					
						scrollP1.setLocation(4* base,  4* base );					
						scrollP1.setSize(base*36+ base/2, base*24 + base/2-base/10);		
						jFrame.add(scrollP1);
						robotDirectory.setText("Select a cell for the first move." );
						jFrame.add(robotDirectory);
						drawLabel(jFrame);
						jFrame.repaint();
						
					}

				}
				
			}

			
		}

	

		//listener for the centisecond textfield for speed
		class speedField2Listener implements DocumentListener{

			private int speedFieldNum;
			private int countingThing = 0;
			

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
							if(input.length() == 1 ){
								t.start();
								speedFieldNumTimer=speedFieldNum;
								timeMode=2;
								
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

	    //listener for the second textfield for speed
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

		//listener for the special symbol representing the speed (only the centisecond part as automation displays second and centisecond)
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

		
		//listener for the special symbol representing the speed (only the second part as automation displays second and centisecond)
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


		//chooses the delay for mixing on each cell (this is a continous updater so any text inputted is saved)
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

		//chooses the total time for mixing 
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


		//choses which direction for mixing
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
					
						for(int i = 0; i < (dimension_row*12+2);i++){
							for(int j=0;j<(dimension_col*12+2);j++){
								specialButtons[i][j].setText("");
								specialButtons[i][j].setIcon(null);
								jFrame.remove(specialButtons[i][j]);
							}
						}
						insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp,jp );
					

				}
				jFrame.repaint();
				
			}
		}


		//chooses font size
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
		
		//starts a timer; this function is complex. In essence, several textfields and buttons requiure a time window to function correctly. 
		// In this window, the button or textfield can be modified. 
		class TimerListener implements ActionListener{

			
			
			public TimerListener(){
               super();
			   ticks =0;
			   
            }
			public void actionPerformed(ActionEvent e){
				ticks ++;
				// System.out.println("tick"+ ticks + "counter" + tickCounter);
				if(ticks > 150){
					if(timeMode == 1){
						save.setBackground(new Color (255,209,0));
						t.stop();
						ticks = 0;
					}

					else if (timeMode == 2){
						String input = speedField2[speedFieldNumTimer].getText();

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



						if(Integer.parseInt(input)==0 &&Integer.parseInt(speedField[speedFieldNumTimer].getText())==0  ){
							return;
						}

						
						if(isInteger(input) &&  Integer.parseInt(input) >= 0  ){
							int intInput = Integer.parseInt(input);
							boolean tracker = false;

							if(mode != -1 && currentSpeed[mode]==speedValues[speedFieldNumTimer]){
								tracker = true;
							}

							for(int i = 0; i < RobotClass.length;i++){
								for(int j = 0; j < Speeds[i].storedSpeeds.size();j++){
									if(Speeds[i].getNum(j)==speedFieldNumTimer){
										int thing = (Speeds[i].getSpeed(j)/1000)*1000 + intInput*10;
										int[] newThing = {thing,speedFieldNumTimer};
										Speeds[i].storedSpeeds.set(j,newThing);
									}
								}
							}
							speedValues[speedFieldNumTimer] = (speedValues[speedFieldNumTimer]/1000)*1000 + intInput*10;


							if (tracker == true) currentSpeed[mode]= speedValues[speedFieldNumTimer] ;
							activeSymbol = false;
							
							String input1 = symbols[speedFieldNumTimer][1];
							if(isInteger(input1)){

								if(input.length() < 2){
									symbolField2[speedFieldNumTimer].setText(intInput+"");
								}

								else{
									symbolField2[speedFieldNumTimer].setText(intInput+"");
								}

								symbols[speedFieldNumTimer][1]=intInput + "";
							}

							activeSymbol = true;
							activeSpeed = false;
							if(input.length() == 1 ){
								speedField2[speedFieldNumTimer].setText( "0" + input);
								
							}

							
							else{
								speedField2[speedFieldNumTimer].setText( input);
								
							}

							activeSpeed = true;
							


                          
							autoPrint(jFrame);
		
							jFrame.repaint();

							
						}
						timeMode = 1;
						t.stop();
						ticks = 0;
					}

					else{
						

						deleteButton.setText("Delete Macro");
						deleteButton.setBackground(new Color(255,100,100));
						timeMode = 1;
						macroMode = 1;
						t.stop();
						ticks = 0;
					}
					
				}

			}
		}

		t = new Timer(1, new TimerListener());


		//this opens a new window to create a macro
		class newFrameListener implements ActionListener{

		
			public void actionPerformed(ActionEvent event) {
				jFrame.dispose();

				int index = 0;

				if(mode == -1 || currentSpeed[mode]==-1){
					index = -1;
				}

				else{
					for(int i = 0; i < speedValues.length;i++){
						if(speedValues[i]==currentSpeed[mode]){
							index = i;
						}
					}
				}
				


				newPath newframe = new newPath(tutorial, font,1,length,width);
				newframe.runProgram();
			}
		}


		//this deletes a macro
		class deleteListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				macroMode = 2;

				deleteButton.setText("<html><div style='text-align: center;'> Choose<br>Macro<html>");

				deleteButton.setBackground(new Color(180,100,100));

				timeMode = 3;

				t.start();
				
			}
		}


		//this saves the settings
		class saveListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				t.start();

				save.setBackground(adjustSaturation(new Color (255,209,0),0.5f));


				
				FileWriter geek_file;
				try
				{
					geek_file = new FileWriter("storage.txt");
					
					// Initializing BufferedWriter
					BufferedWriter geekwrite = new BufferedWriter(geek_file);
					
					
					// Use of write() method to write the value in 'ABC' file
					// Printing E
					geekwrite.write(symbols[0][0] + " " + symbols[0][1]);
					for(int i = 1; i <4;i++){
						geekwrite.write("\n" + symbols[i][0] + " " + symbols[i][1]);
					}

					for(int i = 0; i <4;i++){
						geekwrite.write("\n" +speedValues[i] +"");
					}

					geekwrite.write("\n" +mergeTotalT + "");
					geekwrite.write("\n" +mergeDelay + "");
					geekwrite.write("\n" +tutorial + "");
					geekwrite.write("\n" +font +"");
					geekwrite.write("\n" + 1);

					// Closing BufferWriter to end operation
					
					geekwrite.close();
					
				}
				catch (IOException except)
				{
					except.printStackTrace();
				}
						
			}
		}

		//this is when the user presses the settings button; it opens up the menu page
		class switchListener implements ActionListener{
			public void actionPerformed(ActionEvent event) {
				FileWriter geek_file;
				try
				{
					geek_file = new FileWriter("storage.txt",true);
					
					// Initializing BufferedWriter
					BufferedWriter geekwrite = new BufferedWriter(geek_file);
				
					geekwrite.write("\n" + 0);
					// Closing BufferWriter to end operation
					
					geekwrite.close();
					
				}
				catch (IOException except)
				{
					except.printStackTrace();
				} 


				controlSystem startUp = new controlSystem();
				startUp.runProgram();
				jFrame.dispose();

			}
		}


		//this is the button if you press "ferrobot." Its main function is to set the robot id.
		//If there are mutliple robots, this should be the only thing that may need changing. Granted, some functions will be buggy and need fixes
		//Particularly, it will be all the printing functions for the board 
		class ClassListener implements ActionListener{
            int robot_id;
			public ClassListener(int id){
				super();
				robot_id = id;
			}

			public void actionPerformed(ActionEvent e){
				
				if(mode ==-1 && tutorial ==1){
					
					jFrame.getContentPane().removeAll();
					//jFrame.setSize(base*45,base*30);
					jFrame.setSize((3-2) * 12 * base +base*45,base*30);
					jFrame.getContentPane().setBackground(Color.BLACK);
					jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jFrame.setLocationRelativeTo(null);
					programNum++;
					robotDirectory.setText("\u2191" + " Customize & Select Speed");
					robotDirectory.setBounds((3-2) * 12 * base +29 *base+base*4, base +4*base, base*20, base*8);
					StyledDocument doc10= robotDirectory.getStyledDocument();
					SimpleAttributeSet center10= new SimpleAttributeSet();
					StyleConstants.setAlignment(center10, StyleConstants.ALIGN_LEFT);
					doc10.setParagraphAttributes(0, doc10.getLength(), center10, false);
					jFrame.add(robotDirectory);
					for(int i = 0; i < 4;i++){
						timeUnits[i].setBackground(new Color(238,238,238));
						jFrame.add(speedButtons[i]);
						jFrame.add(speedField[i]);
						jFrame.add(speedField2[i]);
						jFrame.add(symbolField[i]);
						jFrame.add(symbolField2[i]);
						jFrame.add(timeUnits[i]);
						
					}

					timeLabel.setBackground(new Color(238,238,238));
					symbolField1Label.setBackground(new Color(238,238,238));
					symbolField2Label.setBackground(new Color(238,238,238));


					

					jFrame.add(timeLabel);
					jFrame.add(symbolField1Label);
					jFrame.add(symbolField2Label);

					
					
					jFrame.repaint();
				}

				previousModes[0]=true;
				mode = robot_id;

				if (initialization == 0){
					
					initialization++;
					return;
				}

				if (initialization == 1){
					
					if(mode !=0){
						RobotButtons[0].setBackground(new Color(120,0,0)); 
						RobotButtons[0].setOpaque(true);
					}
				}
				// System.out.println(mode);
			}
		
		}

		//Clears the board of moves
		class Reset implements ActionListener {


			public void actionPerformed(ActionEvent e){
				 
				for(int i = 0; i < (dimension_row *12 +2);i++){
					for(int j=0;j<(dimension_col *12 +2);j++){
						specialButtons[i][j].setText("");
						specialButtons[i][j].setIcon(null);
						jFrame.remove(specialButtons[i][j]);
						jFrame.repaint();
					}
				}

				if(initialization==0) return;
				int[][] temp = {{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0},{0,0}};
				previous_moves[mode] = temp;

				for(int i = 0; i < RobotClass.length;i++){
					RobotClass[i].storedMoves.clear();
					Speeds[i].storedSpeeds.clear();
					previousModes[i] = false;
					specialMoves[i].storedSMoves.clear();
					
				}

				for(int i = 0; i < (dimension_row *12 ); i++){
					for(int j = 0; j < (dimension_col *12); j++){
						buttonCells[i][j].setBackground(Color.WHITE);
						buttonCells[i][j].setOpaque(true);
						buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp,jp));
						buttonCells[i][j].setText("");
						buttonCells[i][j].setIcon(null);
					}
				}

				for(int i = 0; i < (dimension_row *12);i++){
					for(int j=0;j<(dimension_col *12);j++){
						for (ActionListener B : buttonCells[i][j].getActionListeners()){
							buttonCells[i][j].removeActionListener(B);
						}
						buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp,jp));
					}
					
				}

				for(int i = 0; i < (dimension_row *12 +2);i++){
					for(int j=0;j<(dimension_col *12+2);j++){
						for (ActionListener B : specialButtons[i][j].getActionListeners()){
							specialButtons[i][j].removeActionListener(B);
						}
					}
					
				}
				jlabel.setText("");
				jFrame.repaint();

			}
		}

		//if a macro is pressed, it will be added to the board
		class macroListener implements ActionListener {

			int index = -1;
			int realIndex1 = -1;
			String fileName;

			public macroListener(int abc,String fileName){
				index = 0;
				realIndex1 = abc;
				this.fileName = fileName;



			}
			public void actionPerformed(ActionEvent e){
				if(macroMode==1){
					macroStore.clear();
					macroReader(fileName);

					if(mode==-1||RobotClass[mode].storedMoves.size()==0){
						return;
					}

					//first check and then add
					for(int i = 0; i < macroStore.get(index*3).size();i++){
						if(macroStore.get(index*3).get(i)[0] < dimension_row*12 && macroStore.get(index*3).get(i)[0] >= 0 && macroStore.get(index*3).get(i)[1] < dimension_col*12  && macroStore.get(index*3).get(i)[1] >= 0 ){

						}

						else{
							//System.out.print("Not in bound");
							return;
						}
					}

					//add

				
					for(int i = 0; i < macroStore.get(index*3+1).size();i++){
						if(macroStore.get(index*3+1).get(i)[1]==-1){
							Speeds[mode].storedSpeeds.add(macroStore.get(index*3+1).get(i));
							
						}

						else{
							Speeds[mode].storedSpeeds.add(macroStore.get(index*3+1).get(i));
						}
					}

					for(int i = 0; i < macroStore.get(index*3+2).size();i++){
						specialMoves[mode].storedSMoves.add(macroStore.get(index*3+2).get(i));
					}
							
					for(int i = 0; i < macroStore.get(index*3).size();i++){
						if(macroStore.get(index*3).get(i)[0] < dimension_row*12  && macroStore.get(index*3).get(i)[0] >= 0 && macroStore.get(index*3).get(i)[1] < dimension_col*12 && macroStore.get(index*3).get(i)[1] >= 0 ){
							RobotClass[mode].storedMoves.add(macroStore.get(index*3).get(i));
						}

						else{
							
						}
					}

					for(int a = 0; a < 4;a++){
						int rPrev = previous_moves[mode][a][0];
						int cPrev = previous_moves[mode][a][1];
						buttonCells[rPrev][cPrev].setText("");
						buttonCells[rPrev][cPrev].setIcon(null);
					}

					for(int a = 0; a < (dimension_row * 12);a++){
						for(int b=0;b< (dimension_col *12);b++){
							for (ActionListener B : buttonCells[a][b].getActionListeners()){
								buttonCells[a][b].removeActionListener(B);
							}
							buttonCells[a][b].addActionListener(new BListener(a,b,jFrame,jsp,jp));
						}
						
					}

					for(int a = 0; a < (dimension_row * 12);a++){
						for(int b=0;b< (dimension_col * 12);b++){
							for (ActionListener B : specialButtons[a][b].getActionListeners()){
								specialButtons[a][b].removeActionListener(B);
							}
						}
						
					}

					
					int r = previous_moves[mode][4][0];
					int c = previous_moves[mode][4][1];
					deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame,jp);


					r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
					c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

					previous_moves[mode][4][0] = r;
					previous_moves[mode][4][1] = c;
					printRobot(buttonCells,1);
					


				
					for(int a= 0; a < (dimension_row * 12 + 2);a++){
						for(int b=0;b<(dimension_col * 12 + 2);b++){
							specialButtons[a][b].setText("");
							specialButtons[a][b].setIcon(null);
							jFrame.remove(specialButtons[a][b]);
						}
					}
					insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp,jp);
					
					counter[mode]++;
				
					insertArrows (r,  c, buttonCells);

					jlabel.setText("");
					
					jFrame.add(jsp);
					autoPrint(jFrame);

				}

				else{
					macroMode = 1;
					String thing = fileName;
				
					
					File myObj = new File(thing); 
					if (myObj.delete()) { 
					//System.out.println("Deleted the file: " + myObj.getName());
					} else {
					//System.out.println("Failed to delete the file.");
					} 

					macroPanel.removeAll();
					macroButtons.clear();

					File directoryPath = new File(".");
					//List of all files and directories
					File filesList[] = directoryPath.listFiles();
					int counting1 = 0;

					
				

					int rowShifty = 0;
					
					for(File file : filesList) {
						String filename= file.getName();
						
						if(filename.startsWith("Macro_")){
							String[] thing1 = filename.split("_");
							String[] thing2 = thing1[1].split(".txt");
							String buttonName = thing2[0];
							macroButtons.add(new JButton(buttonName));

							if((counting1)%3==0&& counting1!=0){
								rowShifty++;
							}
							macroButtons.get(counting1).setBackground(new Color (220,220,240));
								macroButtons.get(counting1).setBounds(0+(counting1%3)*(base*2+base/2+base/4), rowShifty*(base+base/4),base*2+base/2,base);
								macroButtons.get(counting1).setMargin(new Insets(0, 0, 0, 0));
								macroButtons.get(counting1).setBorder(BorderFactory.createLineBorder(Color.gray));
								macroButtons.get(counting1).setFont(new Font("Calibri", Font.PLAIN,font));
								macroButtons.get(counting1).addActionListener(new macroListener(counting1,filename));
								macroButtons.get(counting1).setText(buttonName);
								macroPanel.add(macroButtons.get(counting1));

								counting1++;
								
						}
					
					}


					if(rowShifty >0){
						counting1 = 3;
					}

					else{
						counting1 = counting1%3;
					}

					macroPanel.setPreferredSize(new Dimension((counting1)*(base*2+base/2+base/4),(rowShifty+1)*(base+base/4)));
					

					deleteButton.setText("Delete Macro");
					deleteButton.setBackground(new Color(255,100,100));

					jFrame.repaint();


				}
			}
		}
		
		//goes back to the previous move
		class GoBack implements ActionListener {
			public void actionPerformed(ActionEvent e){
				if(initialization==0) return;
				if(RobotClass[mode].storedMoves.size()==0){
					return;
				}

				if(Speeds[mode].storedSpeeds.size()==1){
					
					RobotClass[mode].storedMoves.remove(RobotClass[mode].storedMoves.size()-1);
					
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
					deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame,jp );
				

					for(int i = 0; i < (dimension_row *12);i++){
						for(int j=0;j<(dimension_col *12);j++){
							for (ActionListener B : buttonCells[i][j].getActionListeners()){
								buttonCells[i][j].removeActionListener(B);
							}
							buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp,jp));
						}
						
					}

					for(int i = 0; i < (dimension_row *12 +2);i++){
						for(int j=0;j<(dimension_col *12 +2);j++){
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
					jFrame.repaint();
					
				}

				else{

					for(int i = 0; i < (dimension_row *12 );i++){
						for(int j=0;j<(dimension_col *12 );j++){
							for (ActionListener B : buttonCells[i][j].getActionListeners()){
								buttonCells[i][j].removeActionListener(B);
							}
							buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp,jp));
							buttonCells[i][j].setBackground(Color.WHITE);
							buttonCells[i][j].setOpaque(true);
							buttonCells[i][j].setText("");
							buttonCells[i][j].setIcon(null);
						}
						
					}

					for(int i = 0; i < (dimension_row *12 +2);i++){
						for(int j=0;j<(dimension_col *12 +2);j++){
							for (ActionListener B : specialButtons[i][j].getActionListeners()){
								specialButtons[i][j].removeActionListener(B);
							}
						}
						
					}


					int ref = -1;
					if(specialMoves[mode].storedSMoves.size()>0){
						ref = specialMoves[mode].storedSMoves.get(specialMoves[mode].storedSMoves.size()-1)[4];
					}
					

					if( specialMoves[mode].storedSMoves.size() >= 1 && specialMoves[mode].storedSMoves.get(specialMoves[mode].storedSMoves.size()-1)[4]==ref &&  Speeds[mode].getNum(Speeds[mode].storedSpeeds.size()-1)==-1) {

						
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
						deleteSpecialButton (r, c, specialButtons, buttonCells, jFrame,jp);


						while (specialMoves[mode].storedSMoves.size() >= 1 && specialMoves[mode].storedSMoves.get(specialMoves[mode].storedSMoves.size()-1)[4]==ref &&  Speeds[mode].getNum(Speeds[mode].storedSpeeds.size()-1)==-1){
							specialMoves[mode].storedSMoves.remove(specialMoves[mode].storedSMoves.size()-1);

							
							RobotClass[mode].storedMoves.remove(RobotClass[mode].storedMoves.size()-1);
							
							
							Speeds[mode].storedSpeeds.remove(Speeds[mode].storedSpeeds.size()-1);
							
						}

						buttonCells[RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0]][RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1]].setText("");

						
					}

					else{
						

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
						deleteSpecialButton (r, c, specialButtons, buttonCells, jFrame,jp);
						

						
						RobotClass[mode].storedMoves.remove(RobotClass[mode].storedMoves.size()-1);
						

						Speeds[mode].storedSpeeds.remove(Speeds[mode].storedSpeeds.size()-1);
					}

				int r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
				int c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

				buttonCells[r][c].setIcon(null);
				buttonCells[r][c].setText("");

				previous_moves[mode][4][0] = r;
				previous_moves[mode][4][1] = c;

				
					for(int i = 0; i < (dimension_row *12 +2);i++){
						for(int j=0;j<(dimension_col *12 +2);j++){
							specialButtons[i][j].setText("");
							specialButtons[i][j].setIcon(null);
							jFrame.remove(specialButtons[i][j]);
						}
					}
					deleteSpecialButton(r, c, specialButtons, buttonCells,jFrame,jp );
				
				
				r = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0];
				c = RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1];

				previous_moves[mode][4][0] = r;
				previous_moves[mode][4][1] = c;
                printRobot(buttonCells,1);
			
				for(int i = 0; i < (dimension_row *12 +2);i++){
					for(int j=0;j<(dimension_col *12 +2);j++){
						specialButtons[i][j].setText("");
						specialButtons[i][j].setIcon(null);
						jFrame.remove(specialButtons[i][j]);
					}
				}
				insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp,jp);

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


		//if the user wants to preview the robot moving across the board, this function will open that screen
		class gameSwitch implements ActionListener{

			public gameSwitch(){
				super();
			}

			public void actionPerformed(ActionEvent e){
				
				
				jFrame.dispose();
				tickCounter=0;
				ticks=0;
				jlabel.setForeground(Color.WHITE);
				title.setForeground(Color.WHITE);
				titleText.setForeground(Color.WHITE);
				title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
				createAndShowPreview();
				

				
			}

		}

		jlabel.setEditable(false); // as before
		jlabel.setBorder(null); // remove the border
		
		jsp.setBounds((3-2) * 12 * base +31 *base, base*7 + base/2+base*7, base*12+base/2, base*13);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		jsp.getViewport().setBackground(null);
		jsp.getViewport().setOpaque(true);

		for(int i = 0; i < speedButtons.length;i++){
			speedButtons[i] = new JButton();
			speedButtons[i].setMargin(new Insets(0, 0, 0, 0));
			speedButtons[i].setFont(new Font("Calibri", Font.PLAIN,font));
			speedButtons[i].setBounds((3-2) * 12 * base +35 *base, base +i*base, base, base);
			speedButtons[i].addActionListener(new speedListener(i));
			speedButtons[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			jFrame.add(speedButtons[i]);
		}

	

		colors = new Color[5];
		colors[0] = Color.WHITE;
		colors[1] = Color.BLUE;
		colors[2] = Color.GREEN;
		colors[3] = Color.GRAY;
		colors[4] = Color.YELLOW;
		//AutoMove

		legend.setBounds((3-2) * 12 * base +38 *base, base*6 + base/2, base*3 + base/2, base*19);
		legend.setFont(new Font("Calibri", Font.PLAIN,font));
		legend.setText("");
		legend.setVerticalAlignment(JButton.CENTER);
		jFrame.add(legend);
		
		for(int i = 0; i < speedField.length;i++){
			speedField[i] = new JTextField();
			speedField[i].setText(speedValues[i]/1000 + "");
			speedField[i].setHorizontalAlignment(JTextField.CENTER);
			speedField[i].setFont(new Font("Calibri", Font.PLAIN,font));
			speedField[i].setBounds((3-2) * 12 * base +38*base, base +i*base, base, base);
			speedField[i].getDocument().addDocumentListener(new speedFieldListener(i));
			speedField[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			jFrame.add(speedField[i]);
		}

		for(int i = 0; i < speedField2.length;i++){
			speedField2[i] = new JTextField();
			speedField2[i].setDocument(new JTextFieldLimit(2));

			if(((speedValues[i]%1000)/10 + "").length() == 1){
				speedField2[i].setText("0" + (speedValues[i]%1000)/10 + "");
			}

			else {
				speedField2[i].setText((speedValues[i]%1000)/10 + "");
			}
			speedField2[i].setHorizontalAlignment(JTextField.CENTER);
			speedField2[i].setFont(new Font("Calibri", Font.PLAIN,font));
			speedField2[i].setBounds((3-2) * 12 * base +39*base, base +i*base, base, base);
			speedField2[i].getDocument().addDocumentListener(new speedField2Listener(i));
			speedField2[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			
			jFrame.add(speedField2[i]);
		}

		for(int i = 0; i < symbolField.length;i++){
			symbolField[i] = new JTextField();
			symbolField[i].setText(symbols[i][0]+ "");
			symbolField[i].setHorizontalAlignment(JTextField.CENTER);
			symbolField[i].setFont(new Font("Calibri", Font.PLAIN,font));
			symbolField[i].setBounds((3-2) * 12 * base +36 *base, base +i*base, base, base);
			symbolField[i].getDocument().addDocumentListener(new symbolFieldListener(i));
			symbolField[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			jFrame.add(symbolField[i]);
		}

		

		for(int i = 0; i < symbolField2.length;i++){
			symbolField2[i] = new JTextField();
			// symbolField2[i].setDocument(new JTextFieldLimit(2));
			symbolField2[i].setText(symbols[i][1]+ "");
			symbolField2[i].setHorizontalAlignment(JTextField.CENTER);
			symbolField2[i].setFont(new Font("Calibri", Font.PLAIN,font));
			symbolField2[i].setBounds((3-2) * 12 * base +37 *base, base +i*base, base, base);
			symbolField2[i].getDocument().addDocumentListener(new symbolField2Listener(i));
			symbolField2[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			jFrame.add(symbolField2[i]);
		}

	
		symbolField1Label.setEditable(false); // as before
		symbolField1Label.setBorder(null); // remove the border
		symbolField1Label.setBounds((3-2) * 12 * base +36 *base,base/4, base, base/2+base/4);
		StyledDocument doc3= symbolField1Label.getStyledDocument();
		SimpleAttributeSet center3 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center3, StyleConstants.ALIGN_CENTER);
		doc3.setParagraphAttributes(0, doc3.getLength(), center3, false);
		symbolField1Label.setBorder(BorderFactory.createLineBorder(Color.gray));
		symbolField1Label.setFont(new Font("Calibri", Font.PLAIN,font));
		symbolField1Label.setBackground(null);
		symbolField1Label.setOpaque(true);
		symbolField1Label.setText("s");

		jFrame.add(symbolField1Label);

	
		symbolField2Label.setEditable(false); // as before
		symbolField2Label.setBorder(null); // remove the border
		symbolField2Label.setBounds((3-2) * 12 * base +37 *base, base/4, base, base/2+base/4);
		StyledDocument doc4= symbolField2Label.getStyledDocument();
		SimpleAttributeSet center4 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center4, StyleConstants.ALIGN_CENTER);
		doc4.setParagraphAttributes(0, doc4.getLength(), center4, false);
		symbolField2Label.setBorder(BorderFactory.createLineBorder(Color.gray));
		symbolField2Label.setFont(new Font("Calibri", Font.PLAIN,font));
		symbolField2Label.setBackground(null);
		symbolField2Label.setOpaque(true);
		symbolField2Label.setText("cs");

		jFrame.add(symbolField2Label);

		
		timeLabel.setEditable(false); // as before
		timeLabel.setBorder(null); // remove the border
		timeLabel.setBounds((3-2) * 12 * base +38*base,base/4, base*2, base/2 + base/4);
		StyledDocument doc5= timeLabel.getStyledDocument();
		SimpleAttributeSet center5 = new SimpleAttributeSet();
		StyleConstants.setAlignment(center5, StyleConstants.ALIGN_CENTER);
		doc5.setParagraphAttributes(0, doc5.getLength(), center5, false);
		timeLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
		timeLabel.setFont(new Font("Calibri", Font.PLAIN,font));
		timeLabel.setBackground(null);
		timeLabel.setOpaque(true);
		timeLabel.setText("Time");

		jFrame.add(timeLabel);

		

		for(int i = 0; i < timeUnits.length;i++){
			timeUnits[i] = new JTextField();
			timeUnits[i].setEditable(false); // as before
			timeUnits[i].setBorder(null); // remove the border
			timeUnits[i].setBounds((3-2) * 12 * base +40 *base, base + i*base, base+base/2, base );
			timeUnits[i].setHorizontalAlignment(JTextField.CENTER);
			timeUnits[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			timeUnits[i].setFont(new Font("Calibri", Font.PLAIN,font));
			timeUnits[i].setBackground(null);
			timeUnits[i].setOpaque(true);
			timeUnits[i].setText("0 (ms)");
			jFrame.add(timeUnits[i]);
		}

		

		for(int i = 0; i < mergeDirect.length;i++){
			mergeDirect[i] = new JButton();
			mergeDirect[i].setMargin(new Insets(0, 0, 0, 0));
			mergeDirect[i].setFont(new Font("Calibri", Font.PLAIN,font));
			mergeDirect[i].addActionListener(null);
			mergeDirect[i].setBackground(Color.LIGHT_GRAY);
			mergeDirect[i].setBorder(BorderFactory.createLineBorder(Color.gray));
			mergeDirect[i].setOpaque(true);
			mergeDirect[i].addActionListener(new mergeDirectListener(i));

			
			jFrame.add(mergeDirect[i]);
		}

		mergeDirect[0].setIcon(new ImageIcon("1.png"));
		mergeDirect[1].setIcon(new ImageIcon("2.png"));
		mergeDirect[2].setIcon(new ImageIcon("3.png"));
		mergeDirect[3].setIcon(new ImageIcon("4.png"));

		mergeDirect[0].setBounds((3-2) * 12 * base +42 *base + base/2, base, base, base);
		mergeDirect[1].setBounds((3-2) * 12 * base +43 *base + base/2, base, base, base);
		mergeDirect[2].setBounds((3-2) * 12 * base +43 *base + base/2, base*2, base, base);
		mergeDirect[3].setBounds((3-2) * 12 * base +42 *base + base/2, base*2, base, base);

		
		mergeTotalTField.setText(mergeTotalT/1000 + "");
		mergeTotalTField.setBorder(BorderFactory.createLineBorder(Color.gray));
		mergeTotalTField.setHorizontalAlignment(JTextField.CENTER);
		mergeTotalTField.setFont(new Font("Calibri", Font.PLAIN,font));
		mergeTotalTField.setBounds((3-2) * 12 * base +43 *base + base/2, base *3, base, base+base/2);
		mergeTotalTField.getDocument().addDocumentListener(new mergeTotalTFieldListener());
		jFrame.add(mergeTotalTField);

		
		mergeDelayField.setText(mergeDelay+ "");
		mergeDelayField.setBorder(BorderFactory.createLineBorder(Color.gray));
		mergeDelayField.setHorizontalAlignment(JTextField.CENTER);
		mergeDelayField.setFont(new Font("Calibri", Font.PLAIN,font));
		mergeDelayField.setBounds((3-2) * 12 * base +43 *base + base/2, base * 4+base/2, base, base+base/2);
		mergeDelayField.getDocument().addDocumentListener(new mergeDelayFieldListener());
		jFrame.add(mergeDelayField);

		for(int i = 0; i < mergeDirect.length;i++){
			if (i!=merge)
			mergeDirect[i].setBackground(Color.GRAY);
			mergeDirect[i].setOpaque(true);
		}

		JLabel delayDescrip = new JLabel();
		delayDescrip.setBounds((3-2) * 12 * base +42 *base + base/2, base * 4+base/2, base, base+base/2);
		delayDescrip.setHorizontalAlignment(JLabel.CENTER);
		delayDescrip.setBorder(BorderFactory.createLineBorder(Color.gray));
		delayDescrip.setFont(new Font("Calibri", Font.PLAIN,font-5));
		delayDescrip.setText("<html><div style='text-align: center;'>" + "Delay<br>(ms)" +  "</div></html>");
		
		jFrame.add(delayDescrip);

	
		JLabel totalDescrip = new JLabel();
		totalDescrip.setBounds((3-2) * 12 * base +42 *base + base/2, base * 3, base, base+base/2);
		totalDescrip.setHorizontalAlignment(JLabel.CENTER);
		totalDescrip.setBorder(BorderFactory.createLineBorder(Color.gray));
		totalDescrip.setFont(new Font("Calibri", Font.PLAIN,font-5));
		totalDescrip.setText("<html><div style='text-align: center;'>" + "Total<br>(s)" +  "</div></html>");
		
		jFrame.add(totalDescrip);


		JTextPane fontSize = new JTextPane();
		fontSize.setEditable(false); // as before
		fontSize.setBorder(null); // remove the border
		fontSize.setBounds((3-2) * 12 * base +42 *base + base/2, base*7, base*2, base/2+base/4);
		StyledDocument doc = fontSize.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		fontSize.setBorder(BorderFactory.createLineBorder(Color.gray));
		fontSize.setFont(new Font("Calibri", Font.PLAIN,font));
		fontSize.setBackground(null);
		fontSize.setOpaque(true);
		fontSize.setText("Font");

		jFrame.add(fontSize);

		
		fontPane .setBounds((3-2) * 12 * base +42 *base + base/2, base*8-base/4, base*2, base);
		fontPane.setHorizontalAlignment(JTextField.CENTER);
		fontPane.setBackground(Color.white);
		fontPane.setOpaque(true);
		fontPane .setBorder(BorderFactory.createLineBorder(Color.gray));
		fontPane .setFont(new Font("Calibri", Font.PLAIN,font));
		fontPane .setText(font + "");

		fontPane.getDocument().addDocumentListener(new fontListener());

		jFrame.add(fontPane);

		
		save.setBounds((3-2) * 12 * base +31 *base, base*5+base/2, base*3, base+base/3);
		save.setHorizontalAlignment(JLabel.CENTER);
	
	
		save.setBackground(new Color (255,209,0));
		save.setOpaque(true);
		save.setMargin(new Insets(0, 0, 0, 0));
		save.setBorder(BorderFactory.createLineBorder(Color.gray));
		save.setFont(new Font("Calibri", Font.PLAIN,font));
		save.addActionListener(new saveListener());
		save.setText("Save Settings");

		jFrame.add(save);
		newFrame.setBounds((3-2) * 12 * base +31 *base+base*4, base*5+base/2, base*3, base+base/3);
		newFrame.setHorizontalAlignment(JLabel.CENTER);	
		newFrame.setBackground(new Color (100,255,100));
		newFrame.setOpaque(true);
		newFrame.setMargin(new Insets(0, 0, 0, 0));
		newFrame.setBorder(BorderFactory.createLineBorder(Color.gray));
		newFrame.setFont(new Font("Calibri", Font.PLAIN,font));
		newFrame.addActionListener(new newFrameListener());
		newFrame.setText("<html><div style='text-align: center;'> Create<br>Macro<html>");

		jFrame.add(newFrame);

		save.setBounds((3-2) * 12 * base +31 *base, base*5+base/2, base*3, base+base/3);
		save.setHorizontalAlignment(JLabel.CENTER);
	
		//loop to pull files up

		//Creating a File object for directory
		File directoryPath = new File(".");
		//List of all files and directories
		File filesList[] = directoryPath.listFiles();
		int counting1 = 0;
		
		int rowShifty = 0;
					
		for(File file : filesList) {
			String filename= file.getName();
			
			if(filename.startsWith("Macro_")){
				String[] thing1 = filename.split("_");
				String[] thing2 = thing1[1].split(".txt");
				String buttonName = thing2[0];
				macroButtons.add(new JButton(buttonName));

				if((counting1)%3==0 && counting1!=0){
					rowShifty++;
				}
				macroButtons.get(counting1).setBackground(new Color (220,220,240));
					macroButtons.get(counting1).setBounds(0+(counting1%3)*(base*2+base/2+base/4), rowShifty*(base+base/4),base*2+base/2,base);
					macroButtons.get(counting1).setMargin(new Insets(0, 0, 0, 0));
					macroButtons.get(counting1).setBorder(BorderFactory.createLineBorder(Color.gray));
					macroButtons.get(counting1).setFont(new Font("Calibri", Font.PLAIN,font));
					macroButtons.get(counting1).addActionListener(new macroListener(counting1,filename));
					macroButtons.get(counting1).setText(buttonName);

					macroPanel.add(macroButtons.get(counting1));

					counting1++;
					
			}
		
		}


		if(rowShifty >0){
			counting1 = 3;
		}

		else{
			counting1 = counting1%3;
		}
		macroPanel.setPreferredSize(new Dimension((counting1)*(base*2+base/2+base/4),(rowShifty+1)*(base+base/4)));
		macroScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		macroScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jFrame.add(macroScroll);

		deleteButton.setBackground(new Color (255,100,100));
		deleteButton.setBounds((3-2) * 12 * base +31 *base+base*7+base/2, base*5+base/2, base*3, base+base/3);
		deleteButton.setOpaque(true);
		deleteButton.setMargin(new Insets(0, 0, 0, 0));
		deleteButton.setBorder(BorderFactory.createLineBorder(Color.gray));
		deleteButton.setFont(new Font("Calibri", Font.PLAIN,font));
		deleteButton.addActionListener(new deleteListener());
		deleteButton.setText("Delete Macro");

		jFrame.add(deleteButton);

		JButton switchGrid = new JButton();
		switchGrid .setBounds(base,base, base, base);
		switchGrid.setHorizontalAlignment(JTextField.CENTER);
		switchGrid.setMargin(new Insets(0, 0, 0, 0));
		switchGrid.setBackground(Color.white);
		switchGrid .setBorder(BorderFactory.createLineBorder(Color.gray));
		switchGrid .setFont(new Font("Calibri", Font.PLAIN,font+5));
		switchGrid .setText("<html><div style='text-align: center;'>" + " &#9881" +  "</div></html>");
		switchGrid.setBackground(new Color(255, 250, 160));
		switchGrid.setOpaque(true);
	
		
		switchGrid.addActionListener(new switchListener());
		jFrame.add(switchGrid);

		speedButtons[0].setBackground(Color.RED);
		speedButtons[1].setBackground(new Color(0,155,200));
		speedButtons[2].setBackground(new Color(56,202,109));
		speedButtons[3].setBackground(Color.YELLOW);
		
		speedButtons[0].setOpaque(true);
		speedButtons[1].setOpaque(true);
		speedButtons[2].setOpaque(true);
		speedButtons[3].setOpaque(true);

		int SaddR = 0;
		int StotalX = 0;
		int StotalY = 0;
		for(int i = 0; i <  (dimension_row * 12 + 2); i++){
			int SaddC = 0;

			if((i+1)%13 == 1){
					SaddR++;
			}
			for(int j = 0; j < (dimension_col * 12 + 2); j++){
				specialButtons[i][j] = new JButton();
				specialButtons[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				specialButtons[i][j].setHorizontalAlignment(JButton.CENTER);
				specialButtons[i][j].setMargin(new Insets(0, 0, 0, 0));
				specialButtons[i][j].setBackground(Color.WHITE);
				specialButtons[i][j].setOpaque(true);
				specialButtons[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
              
				if((j+1)%13 == 1){
					SaddC++;
				}

				specialButtons[i][j].setBounds((labelgap+j+3)*base + (base/10)*(SaddC), (labelgap+i+3)*base + (base/10)*SaddR,base, base);
				
				if(j==25 && i == 0){
					StotalX = (j)*base + (base/2)*SaddC;
					StotalY = (i)*base + (base/2)*SaddR;
				}
			}

		}
	

		//declare button listener for robot selection
		RobotButtons[0].addActionListener(new ClassListener(0));
		RobotButtons[0].setBackground(Color.WHITE);
		RobotButtons[0].setFont(new Font("Calibri", Font.PLAIN,font));
		RobotButtons[1].addActionListener(new ClassListener(1));
		RobotButtons[1].setBackground(Color.WHITE);
		RobotButtons[0].setFont(new Font("Calibri", Font.PLAIN,font));
		RobotButtons[2].addActionListener(new ClassListener(2));
		RobotButtons[0].setFont(new Font("Calibri", Font.PLAIN,font));
		RobotButtons[2].setBackground(Color.WHITE);

		RobotButtons[0].setOpaque(true);
		RobotButtons[1].setOpaque(true);
		RobotButtons[2].setOpaque(true);


        
		jFrame.setLayout(null);
		
		//create array of buttons
		int addR = 0;
		int totalX = 0;
		int totalY = 0;
		for(int i = 0; i < (dimension_row * 12 ); i++){
			int addC = 0;

			if((i+1)%12 == 1){
					addR++;
			}
			for(int j = 0; j < (dimension_col * 12 ); j++){
				buttonCells[i][j] = new JButton();
				buttonCells[i][j].setBorder(BorderFactory.createLineBorder(Color.gray));
				buttonCells[i][j].setMargin(new Insets(0, 0, 0, 0));
				buttonCells[i][j].setBackground(Color.WHITE);
				buttonCells[i][j].setOpaque(true);
				buttonCells[i][j].setFont(new Font("Calibri", Font.PLAIN,font));
				buttonCells[i][j].setHorizontalAlignment(JButton.CENTER);
               
		
				buttonCells[i][j].addActionListener(new BListener(i,j,jFrame,jsp,jp));
				

				if((j+1)%12 == 1){
					addC++;
				}

				//buttonCells[i][j].setBounds((j+1)*base + (base/10)*addC, (i+1)*base + (base/10)*addR,base, base);
				buttonCells[i][j].setBounds((labelgap+j+1)*base + (base/10)*addC, (labelgap+i+1)*base + (base/10)*addR,base, base);
				
				jp.add(buttonCells[i][j]);
				
				if(j==(dimension_col * 12 -1) && i == 0){
					totalX = (j+1)*base + (base/2)*addC;
					totalY = (i+1)*base + (base/2)*addR;
				}
			}

			
		}

		
		
        RobotButtons[0].setBounds((3-2) * 12 * base +31 *base, 3 * base +base, base*3, base);
		RobotButtons[1].setBounds((3-2) * 12 * base +31 *base, 3 * base + base *2, base*3, base);
		RobotButtons[2].setBounds((3-2) * 12 * base +31 *base, 3 * base + base *3, base*3, base);
		jFrame.add(RobotButtons[0]);
	
		drawLabel(jFrame);
        scrollP = new JScrollPane(jp );   
        //scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);	
		scrollP.setLocation(4* base,  4* base );
		scrollP.setWheelScrollingEnabled(false);
		System.out.println(	"false:"+ scrollP.isWheelScrollingEnabled());
	
		scrollP.setSize(base*36+ base/2, base*24 + base/2-base/10);	

		
        jFrame.add(scrollP);

		downPanel.setLayout(null);
		downPanel.setBackground(Color.GREEN);
	    downPanel.setOpaque(false);
    
        downPanel.setPreferredSize(new Dimension( base * dimension_col*12 + (base/10)*(dimension_col+1), base));
       
	
        resizeImage("arrow_up_icon.png","arrow_up.png", base, base);
		resizeImage("arrow_down_icon.png","arrow_down.png", base, base);
		resizeImage("arrow_left_icon.png","arrow_left.png", base, base);
	 	resizeImage("arrow_right_icon.png","arrow_right.png", base, base);

		//************   Up Panel ***************** */
		

		//JPanel upPanel = new JPanel();
		upPanel.setLayout(null);
		upPanel.setBackground(Color.GREEN);
	    upPanel.setOpaque(false);
        
        upPanel.setPreferredSize(new Dimension( base * dimension_col*12 + (base/10)*(dimension_col+1), base));

        for(int i = 0; i < 3; i++){
			int addC = 1;
			for(int j = 0; j <12; j++){
				labelUp[i][j] = new JButton();
				
				labelUp[i][j].setBorder(BorderFactory.createEmptyBorder());
				labelUp[i][j].setBackground(Color.GRAY);
				labelUp[i][j].setOpaque(false);
				
                labelUp[i][j].addActionListener(new scrollmove( jFrame, scrollP, 2));
				labelUp[i][j].setIcon(new ImageIcon("arrow_up.png"));
				
				if((j+1)%12 == 1){
					addC++;
				}
				labelUp[i][j].setBounds((i*12+4+j)*base + i * (base/10) +  (base/10)*addC-base/10, 2 * base,base, base);
				
				
			    jFrame.add(labelUp[i][j]);
			}

			
		}


		
		scrollUp = new JScrollPane(upPanel );   
      
	
		scrollUp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollUp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollUp.setOpaque(false);
		scrollUp.setLocation(4* base,  3* base );
		scrollUp.setSize(36 * base + base/2, base);		
		jFrame.add(scrollUp);

		leftPanel.setLayout(null);
		leftPanel.setBackground(Color.GREEN);
	    leftPanel.setOpaque(false);
        
        leftPanel.setPreferredSize(new Dimension( base, base * dimension_row*12 + (base/10)*(dimension_row+1)));

		scrollLeft = new JScrollPane(leftPanel );   
      
	
		scrollLeft.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollLeft.setOpaque(false);
		scrollLeft.setLocation(3* base,  4* base );
		scrollLeft.setSize(base, 24 * base + base/2);
		jFrame.add(scrollLeft);
		//************   down Panel ***************** */
       

		downPanel.setLayout(null);
		downPanel.setBackground(Color.GREEN);
	    downPanel.setOpaque(false);
        
        downPanel.setPreferredSize(new Dimension( base * dimension_col*12 + (base/10)*(dimension_col+1), base));

        for(int i = 0; i < 3; i++){
			int addC = 1;
			for(int j = 0; j <12; j++){
				labelDown[i][j] = new JButton();
				
				labelDown[i][j].setBorder(BorderFactory.createEmptyBorder());
				labelDown[i][j].setBackground(Color.GRAY);
				labelDown[i][j].setOpaque(false);
				
                labelDown[i][j].addActionListener(new scrollmove( jFrame, scrollP, 3));
				labelDown[i][j].setIcon(new ImageIcon("arrow_down.png"));

				if((j+1)%12 == 1){
					addC++;
				}
				labelDown[i][j].setBounds((i*12+4+j)*base + i * (base/10) +  (base/10)*addC-base/10, 29 * base,base, base);
				
				
			    jFrame.add(labelDown[i][j]);
			}

			
		}
		
        //************   left  Panel ***************** */
		
        for(int i = 0; i < 2; i++){
			int addC = 1;
			for(int j = 0; j <12; j++){
				labelLeft[i][j] = new JButton();
				
				labelLeft[i][j].setBorder(BorderFactory.createEmptyBorder());
				labelLeft[i][j].setBackground(Color.GRAY);
				labelLeft[i][j].setOpaque(false);
				
                labelLeft[i][j].addActionListener(new scrollmove( jFrame, scrollP, 0));
				labelLeft[i][j].setIcon(new ImageIcon("arrow_left.png"));

				if((j+1)%12 == 1){
					addC++;
				}
				
				labelLeft[i][j].setBounds(2 * base, (i*12+4+j)*base + i * (base/10) +  (base/10)*addC-base/10,base, base);
				
			    jFrame.add(labelLeft[i][j]);
			}

			
		}


		//************   right Panel ***************** */
       

		rightPanel.setLayout(null);
		rightPanel.setBackground(Color.GREEN);
	    rightPanel.setOpaque(false);
        
        rightPanel.setPreferredSize(new Dimension( base * dimension_col*12 + (base/10)*(dimension_col+1), base));

        for(int i = 0; i < 2; i++){
			int addC = 1;
			for(int j = 0; j <12; j++){
				labelRight[i][j] = new JButton();
				
				labelRight[i][j].setBorder(BorderFactory.createEmptyBorder());
				labelRight[i][j].setBackground(Color.GRAY);
				labelRight[i][j].setOpaque(false);
				
				labelRight[i][j].addActionListener(new scrollmove( jFrame, scrollP, 1));
				labelRight[i][j].setIcon(new ImageIcon("arrow_right.png"));

				if((j+1)%12 == 1){
					addC++;
				}
				
				labelRight[i][j].setBounds(41 * base, (i*12+4+j)*base + i * (base/10) +  (base/10)*addC-base/10,base, base);
				
				
			    jFrame.add(labelRight[i][j]);
			}

			
		}
      
        ///////////////////////////////////////////////////////
		
		 labelRow = new JButton[dimension_row];
			
		 labelCol = new JButton[dimension_col];
			for (int i =0; i < 2;i++)
			{
			    labelRow[i] = new JButton();
				labelRow[i].setMargin(new Insets(0, 0, 0, 0));
				labelRow[i].setFont(new Font("Calibri", Font.PLAIN,font));
				labelRow[i].setMargin( new Insets(4, 4, 4, 4) );
				labelRow[i].setBorder(BorderFactory.createLineBorder(Color.gray));
				labelRow[i].setBackground(Color.GREEN);
				labelRow[i].setOpaque(true);
				//System.out.println(global_label_row+i);
				labelRow[i].setText(Integer.toString(global_label_row+i+1));
				labelRow[i].setBounds(0,9*base + 12 *i * base, base, base);
				jFrame.add(labelRow[i]);
		    }
			for (int i =0; i < 3;i++)
			{
			    labelCol[i] = new JButton();
				labelCol[i].setMargin(new Insets(0, 0, 0, 0));
				labelCol[i].setFont(new Font("Calibri", Font.PLAIN,font));
				labelCol[i].setMargin( new Insets(4, 4, 4, 4) );
				labelCol[i].setBorder(BorderFactory.createLineBorder(Color.gray));
				labelCol[i].setBackground(Color.GREEN);
				labelCol[i].setOpaque(true);
				//System.out.println(global_label_row+i);
				labelCol[i].setText(Integer.toString(global_label_col+i+1));
				//labelRow[i].setBounds(0,3*base + 12 *i * base, base, base);
				labelCol[i].setBounds(9* base + 12 *i *base,0, base, base);
				jFrame.add(labelCol[i]);
		    }
		

		JButton goBackButton = new JButton("\u2190");
		goBackButton.setMargin(new Insets(0, 0, 0, 0));
		goBackButton.addActionListener(new GoBack());
		goBackButton.setBorder(BorderFactory.createLineBorder(Color.gray));
		goBackButton.setFont(new Font("Calibri", Font.PLAIN,font));
		goBackButton.setBackground(new Color(4,92,140));
		goBackButton.setOpaque(true);
		//goBackButton.setBounds(totalX+base*2, totalY+base*2, base*2, base);
		goBackButton.setBounds((3-2) * 12 * base +31 *base, 2 * base , base*3, base);
		goBackButton.setForeground(Color.WHITE);

		
		reset.setMargin(new Insets(0, 0, 0, 0));
		reset.addActionListener(new Reset());
		reset.setBorder(BorderFactory.createLineBorder(Color.gray));
		reset.setBackground(new Color(131,187,236));
		reset.setOpaque(true);
		reset.setFont(new Font("Calibri", Font.PLAIN,font));
		//reset.setBounds(totalX+base*2, totalY+base, base*2, base);
		reset.setBounds((3-2) * 12 * base +31 *base, 3 * base , base*3, base);
		
		reset.setForeground(Color.WHITE);

		jFrame.add(reset);

		//jPanel for data points
    	
		
	
		jlabel.setBackground(null);
		jlabel.setOpaque(true);
	
		jlabel.setBounds(30 *base, base*6 + base/2, base*6 + base/2, base*19);
		jFrame.add(jsp);

			

		JButton buttonSwitch = new JButton("Preview");
		buttonSwitch.setMargin(new Insets(0, 0, 0, 0));
		buttonSwitch.setBorder(BorderFactory.createLineBorder(Color.gray));

       
		buttonSwitch.setBackground(new Color(4,60,92));
		buttonSwitch.setOpaque(true);
		buttonSwitch.addActionListener(new gameSwitch());
		buttonSwitch.setFont(new Font("Calibri", Font.PLAIN,font));
		buttonSwitch.setMargin( new Insets(4, 4, 4, 4) );
	
		buttonSwitch.setBounds((3-2) * 12 * base + 31 *base, base , base*3, base);
		
		buttonSwitch.setForeground(Color.WHITE);
        jFrame.add(buttonSwitch); 


		if(mode==-1 && tutorial ==1){
			robotDirectory.setEditable(false); // as before
			robotDirectory.setBorder(null); // remove the border
			robotDirectory.setMargin(new Insets(0, 0, 0, 0));
			robotDirectory.setBounds((3-2) * 12 * base +31 *base - base/2, base +4*base, base*6, base);
			StyledDocument doc6= robotDirectory.getStyledDocument();
			SimpleAttributeSet center6 = new SimpleAttributeSet();
			StyleConstants.setAlignment(center6, StyleConstants.ALIGN_CENTER);
			doc6.setParagraphAttributes(0, doc6.getLength(), center6, false);
			robotDirectory.setFont(new Font("Calibri", Font.BOLD,font+5));
			robotDirectory.setBackground(null);
			robotDirectory.setForeground(Color.WHITE);
			robotDirectory.setOpaque(true);
			robotDirectory.setText("\u2191" + " Select Robot" + " \u2191");
			jFrame.add(robotDirectory);
			
			welcomeDirectory.setBorder(null); // remove the border
	
		
			welcomeDirectory.setBounds((((3-2) * 12 * base +base*45)-base*11)/2, 12*base, base*20, base*4);
			
			welcomeDirectory.setFont(new Font("Calibri", Font.BOLD,font+5));
			welcomeDirectory.setText("<html><div style='text-align: center;'>" + "Welcome to the Ferrobot App<br><br>To disable the tutorial, go to the Settings (&#9881)" +  "</div></html>" );
			welcomeDirectory.setBackground(null);
			welcomeDirectory.setForeground(Color.WHITE);
			welcomeDirectory.setOpaque(true);
			jFrame.add(welcomeDirectory);
			
			
		}
		

		jFrame.addWindowListener(new WindowAdapter() {
		public void windowClosing(WindowEvent e) {
			int confirmed = JOptionPane.showConfirmDialog(null, 
				"Do you want to save settings?", "Exit Program Message Box",
				JOptionPane.YES_NO_OPTION);



				if(confirmed == JOptionPane.YES_OPTION) {
					FileWriter geek_file;
					try
					{
						geek_file = new FileWriter("storage.txt");
						
						// Initializing BufferedWriter
						BufferedWriter geekwrite = new BufferedWriter(geek_file);
						
						
						// Use of write() method to write the value in 'ABC' file
						// Printing E
						geekwrite.write(symbols[0][0] + " " + symbols[0][1]);
						for(int i = 1; i <4;i++){
							geekwrite.write("\n" + symbols[i][0] + " " + symbols[i][1]);
						}

						for(int i = 0; i <4;i++){
							geekwrite.write("\n" +speedValues[i] +"");
						}

						geekwrite.write("\n" +mergeTotalT + "");
						geekwrite.write("\n" +mergeDelay + "");
						geekwrite.write("\n" +tutorial + "");
						geekwrite.write("\n" +font +"");
						geekwrite.write("\n" + 1);
			
						// Closing BufferWriter to end operation
						
						geekwrite.close();
						
					}
					catch (IOException except)
					{
						except.printStackTrace();
					} 
					jFrame.dispose(); 
				} 
				else { 
					jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					jFrame.dispose();  
				}

			
		}
		});

		
      
		jFrame.add(goBackButton);

	


		//on intalization, must show the whole grid;
		printRobot(buttonCells, 0);
		

	


		if(mode!=-1){
			// jlabel.setText("You are moving Robot " +(mode+1)+ ".");
			int r = previous_moves[mode][4][0];
			int c = previous_moves[mode][4][1];

			int count = 0;

			for(int i = 0; i < 5; i++){
				if(previous_moves[mode][i][0]==0 && previous_moves[mode][i][0]==0){
					count++;
				}
			}

			if(count !=5){
				

				int[][] store = {{r-1,c},{r,c+1},{r,c-1},{r+1,c}};
				
			
			    insertArrows (r,  c, buttonCells);				

		    }


		}

		
		
		int r = 0;
		int c = 0;

		if(mode != -1 && RobotClass[mode].storedMoves.size()!=0){
			r = previous_moves[mode][4][0];
			c = previous_moves[mode][4][1];
			
				for(int i = 0; i < (dimension_row * 12 +2);i++){
					for(int j=0;j<(dimension_col * 12 +2);j++){
						specialButtons[i][j].setText("");
						specialButtons[i][j].setIcon(null);
						jFrame.remove(specialButtons[i][j]);
					}
				}
				insertSpecialButton(r, c, specialButtons, buttonCells,jFrame,jsp,jp );
			

		}

		if(mode != -1){
			int speed = 0;
			for(int i = 0; i < 4;i ++){
				if(currentSpeed[mode]==speedValues[i]){
					speed = i;
				}

				else{
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

		autoPrint(jFrame);

		if(mode==-1 && tutorial ==1){
			
			jFrame.getContentPane().removeAll();
			
			jFrame.setSize((3-2) * 12 * base +base*45,base*30);
			jFrame.getContentPane().setBackground(Color.BLACK);
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setLocationRelativeTo(null);
			jFrame.add(robotDirectory);
			jFrame.add(welcomeDirectory);
			jFrame.add(RobotButtons[0]);
			jFrame.repaint();
		}
		jFrame.setVisible(true);
	
	}

	
	//on startup, this is the first method calls. This pulls up the saved moves and then begins the user creation board
	public void runProgram() {
		

		
		if(programNum==-1){
			File file = new File("storage.txt");
			try (BufferedReader br = new BufferedReader(new FileReader("storage.txt"))) {

				String currentLine;

				int countingThing = 1;

				while ((currentLine = br.readLine()) != null) {
				if(countingThing <= 4) {
					String[] input = currentLine.split(" ");
					symbols[countingThing-1] = input;
				}

				else if(countingThing <= 8){
					int input = Integer.parseInt(currentLine);
					speedValues[countingThing-5] = input;
				}

				else if(countingThing == 9){
					mergeTotalT = Integer.parseInt(currentLine);
				}

				else if(countingThing == 10){
					mergeDelay = Integer.parseInt(currentLine);
				}

				else if(countingThing == 11){
					tutorial = Integer.parseInt(currentLine);
				}

				else if(countingThing == 12){
					font = Integer.parseInt(currentLine);
				}


				countingThing++;
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		

		   FileWriter geek_file;
			try
			{
				geek_file = new FileWriter("storage.txt",true);
				
				// Initializing BufferedWriter
				BufferedWriter geekwrite = new BufferedWriter(geek_file);
				geekwrite.write("\n" + 1);
				// Closing BufferWriter to end operation
				
				geekwrite.close();
				
			}
			catch (IOException except)
			{
				except.printStackTrace();
			} 

		
	   
		resizeImage("A1.png","1.png",base/2+base/3,base/2+base/3);
		resizeImage("B1.png","2.png",base/2+base/3,base/2+base/3);
		resizeImage("C1.png","3.png",base/2+base/3,base/2+base/3);
		resizeImage("D1.png","4.png",base/2+base/3,base/2+base/3);


		createAndShowGUI();
	}

	//checks if a boolean is in a list
	public static boolean isInList(
        final ArrayList<int[]> list, final int[] candidate){

		for(final int[] item : list){
			if(Arrays.equals(item, candidate)){
				return true;
			}
		}
		return false;
    }


	//makes sure the distance is >= 10 between any two (not used; but for the future could be useful)
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

	//calculates the distance between two coordinates (not used; but for the future could be useful)
    public static double calculateDistance(int[] coord1, int[] coord2) {
        int xDiff = coord2[0] - coord1[0];
        int yDiff = coord2[1] - coord1[1];
        return Math.sqrt(xDiff * xDiff + yDiff * yDiff);
    }

	//checks if two arrauys are euqal.
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

	//check if a square is valid (not used; but for the future could be useful)
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

	//gets the board number from an i and j coordinate
	public static int getBoardNum (int i,int j){
		int jNum = j/(12);
		int iNum = i/(12);
 
		
		return ((jNum+1)+(iNum)*length);

		
	}

	//check if String is an Integer
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


	//check if string is a double
	public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

	//checks if there are duplicates in the robot array that stores moves
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

	//gets the text that will be printed in the automation phase
	public static String[][] labelOutput(){
		int max = 0;
		for(int i = 0; i < RobotClass.length;i++){
			if(RobotClass[i].storedMoves.size()>max) max = RobotClass[i].storedMoves.size();
		}

		if(max == 0){
			return null;
		}


		
		String[][] output = new String[max][length*width+1];

		

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
				int thing = 0;
			
				thing=Speeds[mode].getSpeed(i);
				

				if(j==length*width){
					output[i][j] = thing + "";
				}

				else if( (output[i][j]==null || output[i][j].equals(""))){
					output[i][j] = "OFF";
				}

			}
		}
		return output;
	}

	//converts global coordiantes of (i and j) to a boardnumber that is scalar (1 to however many 12x12 boards)
	public static int convertToLocalBoardCoordinate(int globalRow, int globalColumn) {
        int localRow = globalRow % 12;
        int localColumn = globalColumn % 12;
        return localRow * 12 + localColumn;
    }

	
	//converts a local board coordinate (i and j ) to a coordinate number (1 to 144)
    public static int convertToSingleNumber(int localBoardCoordinate) {
        int localRow = localBoardCoordinate / 12;
        int localColumn = localBoardCoordinate % 12;
        return localRow * 12 + localColumn;
    }

	//this removes duplicates in an string array
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

	//this removes duplicates in an integer arraylist
	public static ArrayList<int[]> filterArray2(ArrayList<int[]> inputArray) {
        ArrayList<int[]> outputRows = new ArrayList<>();

        for (int i = 0; i < inputArray.size(); i++) {
            boolean isDuplicate = false;

			int start = 0;

            for (int j = i + 1; j < inputArray.size(); j++) {
             
				 if (areSubarraysEqual(inputArray.get(i),inputArray.get(j)))  {
                    isDuplicate = true;
					
                }
                else {
			
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


	//checks if two subarrays are equal (String)
    public static boolean areSubarraysEqual(String[] subarray1, String[] subarray2) {
        if (subarray1.length != subarray2.length) {
            return false;
        }

        for (int i = 0; i < subarray1.length-1 ; i++) {
            if (!subarray1[i].equals(subarray2[i])) {
                return false;
            }
        }

        return true;
    }

	//checks if two subarrays are equal (int)
	public static boolean areSubarraysEqual(int[] subarray1, int[] subarray2) {
        if (subarray1.length != subarray2.length) {
            return false;
        }

        for (int i = 0; i < subarray1.length; i++) {
            if (!(subarray1[i] == subarray2[i])) {
                return false;
            }
        }

		

        return true;
    }

	//resizes images to the correct size of a computer screen
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

    
	//returns file names
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

	//adjust button color to be lighter
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

	//reads the stored files for macro and adds to special arrays to be called upon when adding a macro move to the board
	public static void macroReader(String fileName){
		if(mode==-1){
			return;
		}

		if(RobotClass[mode].storedMoves.size()==0){
			return;
		}
		File file = new File(fileName);
		ArrayList<int[]> macroMoves = new ArrayList<int[]>();
		ArrayList<int[]> macroSpeeds = new ArrayList<int[]>();
		ArrayList<int[]> macroSpecialMoves = new ArrayList<int[]>();

		ArrayList<int[]> macroMovesF = new ArrayList<int[]>();
		ArrayList<int[]> macroSpeedsF = new ArrayList<int[]>();
		ArrayList<int[]> macroSpecialMovesF = new ArrayList<int[]>();
		
			try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

				String currentLine;

				int countingThing = 1;

				currentLine = br.readLine();

				

				while ( !((currentLine = br.readLine()).equals("Speeds")) ){
					String[] Thing1 = currentLine.split(" ");
					int[] Thing2 = {Integer.parseInt(Thing1[0])+RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0],Integer.parseInt(Thing1[1])+RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1]};
					macroMoves.add(Thing2);
				}

				while ( !((currentLine = br.readLine()).equals("SpecialMoves")) ){
					String[] Thing1 = currentLine.split(" ");
					int[] Thing2 = {Integer.parseInt(Thing1[0]),Integer.parseInt(Thing1[1])};
					macroSpeeds.add(Thing2);
				}

				while ( !((currentLine = br.readLine()) == null) ){
					String[] Thing1 = currentLine.split(" ");
					int[] Thing2 = {Integer.parseInt(Thing1[0]),Integer.parseInt(Thing1[1])+RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[0],Integer.parseInt(Thing1[2])+RobotClass[mode].storedMoves.get(RobotClass[mode].storedMoves.size()-1)[1],Integer.parseInt(Thing1[1]),Integer.parseInt(Thing1[4])};
					macroSpecialMoves.add(Thing2);
				}
			
			} catch (IOException e) {
				e.printStackTrace();
			}

		

		int cycleNum = (mergeTotalT)/(4*mergeDelay);
		int counter1 = 0;
		for(int i = 0; i < macroSpeeds.size();i++){

			if(macroSpeeds.get(i)[1] != -1){
				int[] thing1 = {speedValues[macroSpeeds.get(i)[1]],macroSpeeds.get(i)[1]};
				macroSpeedsF.add(thing1);
					macroMovesF.add(macroMoves.get(i));
			}

			else{

				macroSpeedsF.add(macroSpeeds.get(i));				
				macroMovesF.add(macroMoves.get(i));		
				macroSpecialMovesF.add(macroSpecialMoves.get(counter1));

				counter1+=1;
				
			}
			
		}

		// System.out.print(counter1);

		macroStore.add(macroMovesF);
		macroStore.add(macroSpeedsF);
		macroStore.add(macroSpecialMovesF);


	}
	
}

class Synchronizer implements AdjustmentListener
{
    JScrollBar v1, h1, v2, h2;
  
    public Synchronizer(JScrollPane sp1, JScrollPane sp2)
    {
        v1 = sp1.getVerticalScrollBar();
        h1 = sp1.getHorizontalScrollBar();
        v2 = sp2.getVerticalScrollBar();
        h2 = sp2.getHorizontalScrollBar();
    }
  
    public void adjustmentValueChanged(AdjustmentEvent e)
    {
        JScrollBar scrollBar = (JScrollBar)e.getSource();
        int value = scrollBar.getValue();
        JScrollBar target = null;
  
        if(scrollBar == v1)
            target = v2;
        if(scrollBar == h1)
            target = h2;
        if(scrollBar == v2)
            target = v1;
        if(scrollBar == h2)
            target = h1;
  
        target.setValue(value);
    }
}


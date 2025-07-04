import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;

import java.awt.geom.Line2D;

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


public class controlSystem{
    public static Dimension size = Toolkit. getDefaultToolkit(). getScreenSize();
    public static int base = (size.height/30);

    private static int[] counter = {0,0,0};
	private static int[][][] previous_moves = new int[3][9][2];
	private static int totalX = 0;
    private static int totalY = 0;
	private static int mode =-1;
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

	//program counter
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

	//declare robots
	private static Robot[] RobotClass = {new Robot()};
    private static JButton[][] labelCellsRow = new JButton[4][12];
    private static JButton[][] labelCellsCol = new JButton[4][12];
	private static int labelgap =2;
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
	private static JButton[][] buttonCells = new JButton[12][48];
	private static JButton[][] specialButtons = new JButton[14][50];

	//font
	private static int font = 19;

    //shift
    private static int xShift = 26*base-2*(base/10);
    private static int yShift = 16 * base;

	//tutorial
	private static int tutorial = 1;

    private static int length = 3;
    private static int width = 2;

    private static int counterVar = 0;
    private static JFrame jFrame = new JFrame();
    private static  JTextField lengthField = new JTextField();
    private static  JTextField widthField = new JTextField();
    private static  JButton toggleButton = new JButton();



    //calls user creation board with the directed size
    private static class BListener2x3 implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            String input = lengthField.getText();
            String input1 = widthField.getText();

            if(! isInteger(input) || (input.equals(""))|| ! isInteger(input1) || (input1.equals(""))){
                return;
            }
            
            if(isInteger(input) &&  Integer.parseInt(input) >= 3 && Integer.parseInt(input) <= 9 && isInteger(input1) &&  Integer.parseInt(input1) >= 2  && Integer.parseInt(input1) <= 8){
                length = Integer.parseInt(input);
                width = Integer.parseInt(input1);

                ferrobot2x3 grid = new ferrobot2x3(length,width);
                //System.out.println("l:"+ length + "w:"+width);
                grid.runProgram();
                jFrame.dispose();
            }
        }
    }

    //toggle tutorial mode
    private static  class Toggle implements ActionListener{
        public void actionPerformed(ActionEvent event) {
            
            
            if(tutorial==1){
                    toggleButton.setText("Tutorial OFF");
                toggleButton.setBackground(adjustSaturation(Color.RED,0.75f));
                
                jFrame.repaint();
                tutorial = 2;
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
                    geekwrite.write("\n" + 2);

                    // Closing BufferWriter to end operation
                    
                    geekwrite.close();
                    
                }
                catch (IOException except)
                {
                    except.printStackTrace();
                } 
            }

            else{
                toggleButton.setText("Tutorial ON");
                toggleButton.setBackground(Color.GREEN);
                toggleButton.setForeground(Color.BLACK);
                jFrame.repaint();
                tutorial = 1;
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
                    geekwrite.write("\n" + 2);
        
                    // Closing BufferWriter to end operation
                    
                    geekwrite.close();
                    
                }
                catch (IOException except)
                {
                    except.printStackTrace();
                } 
            }
                
            
        }
             
    };

    //when class is created, this is what runs first
    public static void runProgram() {
        

        String selectionMadeST = "";
        int selectionMade = 0;
      
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

            

            selectionMadeST = currentLine;


            countingThing++;
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        selectionMade = -1;
       
		jFrame.setSize(base*8,base*7);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(null);

        JLabel title = new JLabel();
        title.setText("Please choose your grid:");

		title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font(null, Font.PLAIN,14));
        title.setBounds(base+base/4,base/3 - base/4,base*5+base/2,base);
        title.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        jFrame.add(title);
        

        JLabel lengthDescrip = new JLabel();
		lengthDescrip.setBounds(base*2+base/2-base,base+base/2,base/4+base/2,base/2+base/4);
		lengthDescrip.setHorizontalAlignment(JLabel.CENTER);
		lengthDescrip.setBorder(BorderFactory.createLineBorder(Color.gray));
		lengthDescrip.setFont(new Font("Calibri", Font.PLAIN,12));
		lengthDescrip.setText("C");
		
		jFrame.add(lengthDescrip);

        JLabel widthDescrip = new JLabel();
		widthDescrip.setBounds(base*2+base/2+base,base+base/2,base/2+base/4,base/2+base/4);
		widthDescrip.setHorizontalAlignment(JLabel.CENTER);
		widthDescrip.setBorder(BorderFactory.createLineBorder(Color.gray));
		widthDescrip.setFont(new Font("Calibri", Font.PLAIN,12));
		widthDescrip.setText("R");
		
		jFrame.add(widthDescrip);

       
        lengthField.setText(length + "");
		lengthField.setBorder(BorderFactory.createLineBorder(Color.gray));
		lengthField.setHorizontalAlignment(JTextField.CENTER);
		lengthField.setFont(new Font("Calibri", Font.PLAIN,12));
		lengthField.setBounds(base*2+base/2-base/4,base+base/2,base/2+base/4,base/2+base/4);
		jFrame.add(lengthField);

		
     
		widthField.setText(width+ "");
		widthField.setBorder(BorderFactory.createLineBorder(Color.gray));
		widthField.setHorizontalAlignment(JTextField.CENTER);
		widthField.setFont(new Font("Calibri", Font.PLAIN,12));
		widthField.setBounds(base*2+base/2+base+base/2+base/4,base+base/2,base/2+base/4,base/2+base/4);
		jFrame.add(widthField);
        

       

        JButton button2x3 = new JButton();
        button2x3.addActionListener(new BListener2x3());
		button2x3.setFont(new Font("Calibri", Font.PLAIN,12));
		button2x3.setBounds(base*2+base/2+base*2+base,base+base/2,base,base/2+base/4);
        button2x3.setMargin(new Insets(0, 0, 0, 0));

        button2x3.setText("Enter");
        button2x3.setBorder(BorderFactory.createLineBorder(Color.gray));
        button2x3.setBackground(new Color(255, 250, 160));
        button2x3.setOpaque(true);

        jFrame.add(button2x3);

      

        JLabel title1 = new JLabel();
        title1.setText("Toggle Tutorial:");

		title1.setHorizontalAlignment(JLabel.CENTER);
        title1.setFont(new Font(null, Font.PLAIN,14));
        title1.setBounds(base+base/4,base/3 - base/4 + base*3,base*5+base/2,base);
        title1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        jFrame.add(title1);

        toggleButton.setFont(new Font("Calibri", Font.PLAIN,12));
   
        toggleButton.setBackground(new Color(255, 250, 160));
     
        toggleButton.setBounds(base*3+base/2-base,base+base/2+base*3,base*3,base/2+base/4);
        toggleButton.setMargin(new Insets(0, 0, 0, 0));
        toggleButton.setBorder(BorderFactory.createLineBorder(Color.gray));
        toggleButton.setOpaque(true);
        toggleButton.addActionListener(new Toggle());
        if(tutorial == 1){
            toggleButton.setText("Tutorial ON");
            toggleButton.setBackground(Color.GREEN);
        }

        else{
            toggleButton.setText("Tutorial OFF");
            toggleButton.setBackground(adjustSaturation(Color.RED,0.735f));
        }


        JLabel titlehoward = new JLabel();
        titlehoward.setText("By Howard Ji:");
        titlehoward.setBackground(Color.BLUE);
		titlehoward.setHorizontalAlignment(JLabel.CENTER);
        titlehoward.setFont(new Font(null, Font.PLAIN,16));
        titlehoward.setBounds(1* base+base/4,base/3 - base/4 + base*5,base*5+base/2,base);
        titlehoward.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        jFrame.add(titlehoward);
       

        jFrame.add(toggleButton);

        jFrame.setVisible(true);
        
    }

    public static void main(String[] args) {
        runProgram();
    }

    //change buttons to be lighter
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

    //checks if a string is an integer
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

}
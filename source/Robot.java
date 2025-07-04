import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import java.util.ArrayList;


//robot moves
public class Robot{
    //screen settings

    public ArrayList<int[]> storedMoves ;

    public Robot(){
        storedMoves = new ArrayList<int[]>();
    }

    public void addMoves(int i , int j){
        int[] addThing = {i,j};
        storedMoves.add(addThing);
    }

    public void addMoves(int i , int j, int c){
        int[] addThing = {i,j, c};
        storedMoves.add(addThing);
    }
}
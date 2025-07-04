import java.util.ArrayList;

//special moves like mixing are stored here
public class SpecialMoves {
    public ArrayList<int[]> storedSMoves ;


    private static int counter = 0;

    public SpecialMoves(){
        storedSMoves = new ArrayList<int[]>();
    }

    public void addSMove(int t,int i, int j, int board, int numClass){
        int[]addThing = {t,i,j,board,numClass};
        storedSMoves.add(addThing);
    }

     public void addSMove(int t,int i, int j, int board, int numClass,int time, int mergeDelay){
      
        for(int a = 0; a < time; a++){
            int[] addThing = {a+1,i,j,board,numClass};
            storedSMoves.add(addThing);
           
        }
        
    }

    public int getNumClass(int i ){
        return storedSMoves.get(i)[4];
    }
    
}
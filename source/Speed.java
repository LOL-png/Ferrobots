import java.util.ArrayList;


//speeds are stored here
public class Speed {
    public ArrayList<int[]> storedSpeeds ;

    

    public Speed(){
        storedSpeeds = new ArrayList<int[]>();
    }

    public void addSpeed(int i, int j){
        int[] addThing = {i,j};
        storedSpeeds.add(addThing);
    }

    public int getSpeed(int i){
        return (int) storedSpeeds.get(i)[0];
    }

    public int getNum(int i){
        return (int) storedSpeeds.get(i)[1];
    }
    
}
package Model;


/**
 * 
 * @author Edwin
 */
  
import java.util.ArrayList;

public final class PlacePetri{
    
    private String name;
    private boolean marked;
    private int numMark;
    private ArrayList toutputs;
    private ArrayList tinputs;
    
    public PlacePetri(String name,boolean marked,int nummark,ArrayList tout,ArrayList tin){
        setName(name);
        setMarked(marked);  
        setNumMark(nummark);
        this.toutputs=tout;
        this.tinputs=tin;
    }            

    public ArrayList getToutputs() {
        return toutputs;
    }

    public void setToutputs(ArrayList toutputs) {
        this.toutputs = toutputs;
    }

    public ArrayList getTinputs() {
        return tinputs;
    }

    public void setTinputs(ArrayList tinputs) {
        this.tinputs = tinputs;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public boolean getMarked(){
        return this.marked;
    }
            
    public void setMarked(boolean marked){
        this.marked=marked;
    }
    
    public void setNumMark(int numMark){
        this.numMark=numMark;
    }
    
    public int getNumMark(){
        return this.numMark;
    } 
    
}



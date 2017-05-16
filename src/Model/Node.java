package Model;


import java.util.ArrayList;

/**
 *
 * @author Edwin
 */

public class Node {    
    
    public String id;
    public int level,posX,posY;
    public int[] mark;
    public ArrayList<Node> childs;
    
    public Node(int mark[],String id,int n){
        this.mark=mark;
        this.id=id;
        level=n;
        childs=new ArrayList<>();
    }
    
    public String printNode(){
        String chain="Sequence : "+(id.length()!=0?id:"ROOT")+"  Level : "+level+"-> ( ";
        if(mark != null){
            for(int n : mark)
                chain+=n+"  ";
        }        
        return chain+") \n\n"; 
    }
    
    public boolean compareNodeVector(int vector[]){
        if(this.mark.length != vector.length) return false;
        for(int i=0;i < vector.length;i++){
            if(this.mark[i] != vector[i]) return false;
        }
        return true;
    }        
    
    public String printVectorNode(){
        String chain="( ";
        for(int n : mark)
                chain+=n+"  ";
        return chain+" )";
    }
    
    public String getLastFire(){
        if(id.length() == 0) return "root";
        String aux=id;
        while(aux.contains(",")){            
            aux=aux.substring(aux.indexOf(",")+1,aux.length());
        }
        return aux;
    }
        
}

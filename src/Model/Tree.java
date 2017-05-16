package Model;


import java.util.ArrayList;

/**
 *
 * @author Edwin
 */ 

public class Tree {
    
    private final int increaseLevel=50;
    private int level;
    public Node root;
    public ArrayList<Node> nodesList;
    public ArrayList<Node> leafs;
    
    public Tree(){
        root=null; 
        nodesList=new ArrayList<>();
        leafs=new ArrayList<>();
    }
    
    public Tree(Node n){        
        root=n;
        root.posX=600;
        root.posY=20;
        level=1;
        nodesList=new ArrayList<>();
        nodesList.add(n);
        leafs=new ArrayList<>();
    }
    
    public void addNode(Node parent,Node child){
        parent.childs.add(child);
        nodesList.add(child);
    }
    
    public void printTree(Node node,StringBuffer chain){        
        if(node != null){
            chain.append(node.printNode());
            for(Node n : node.childs)
                printTree(n,chain);
        }
    }
    
    public void getLeafNodes(){
        for(Node node : nodesList)
            if(node.childs.isEmpty()|| node.childs == null)
                leafs.add(node);
    }
    
    public boolean isLeaf(Node node){
        return node.childs.isEmpty() || node.childs == null;
    }
    
    public ArrayList pruneTree(){
        getLeafNodes();
        ArrayList<Node> listToFire=new ArrayList<>();
        for(Node node : leafs){            
            if(searchDeepNode(node))
                listToFire.add(node);
        }
        return listToFire;
    }
    
    public boolean searchDeepNode(Node node){
        for(Node n : nodesList){
            if(!n.id.equals(node.id) && node.compareNodeVector(n.mark) && node.level < n.level && isLeaf(node)){                
                n.childs=new ArrayList<>();
                return true;
            }
        }
        return false;
    }
    
    public void assignPositionToNodes(Node node,int posX,int posY){
        if( ! node.childs.isEmpty() || node.childs != null){
            int newPosX,newPosY;
            if(node.childs.size() == 1)
                newPosX=posX;                            
            else{
                int blockSizeChildren=node.childs.size()*150;
                newPosX=posX - (blockSizeChildren / 2);
            }           
            newPosY=posY+increaseLevel;
            boolean hasChildrenPair=(node.childs.size() % 2 == 0);
            for(int i=0;i < node.childs.size();i++){
                Node n=node.childs.get(i);
                if(hasChildrenPair && i == (node.childs.size() / 2))
                    newPosX+=150;                
                n.posX=newPosX; n.posY=newPosY;
                newPosX+=150;
                assignPositionToNodes(n, n.posX, n.posY);
            }            
        }
    }
        
}

package View;


import Model.Node;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JPanel;

/** 
 * 
 * @author Edwin
 */
 
public class TreePanel extends JPanel{ 
    
    public ArrayList<Node> nodeslist;
    
    @Override 
    public void paint(Graphics g){
        g.setColor(Color.BLUE);
        paintTree(g, nodeslist.get(0));
    }    
    
    public void paintTree(Graphics g,Node nodo){
        g.setColor(Color.WHITE);
        g.fillOval(nodo.posX-300, nodo.posY, 140, 20);
        g.setColor(Color.BLACK);
        g.drawString(nodo.getLastFire()+" - "+nodo.printVectorNode(), nodo.posX+20-300, nodo.posY+15);
        for(Node hijo : nodo.childs){
            g.setColor(Color.RED);
            g.drawLine(nodo.posX+60-300, nodo.posY+20, hijo.posX+60-300, hijo.posY);            
            paintTree(g, hijo);
        }            
    }
        
}

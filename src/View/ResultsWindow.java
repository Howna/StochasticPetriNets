
package View;

import Model.Tree;
import java.awt.BorderLayout;
import java.awt.Label;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Edwin
 */

public class ResultsWindow extends JFrame{     

    private JTextArea textareaResults;                            
    private JScrollPane scrollpane;
    private TreePanel treePanel;                
    
    public ResultsWindow(){
        setTitle("Resultados - Red de Petri Estocastica");
        setSize(1100,600);        
        setLocationRelativeTo(null);
        setDefaultCloseOperation(this.EXIT_ON_CLOSE);       
    }    
    
    public void showResultsWindow( StringBuffer results , Tree reachabilityTree ){        
        textareaResults = new JTextArea(35,35);               
        textareaResults.append("RESULTADOS:\n\n"+results.toString());                        
        scrollpane = new JScrollPane(textareaResults);           
        treePanel = new TreePanel();                
        //treePanel.add(new Label("ARBOL DE ALCANZABILIDAD"));
        treePanel.nodeslist = reachabilityTree.nodesList;                                                                            
        getContentPane().add(scrollpane,BorderLayout.WEST); 
        getContentPane().add(treePanel,BorderLayout.CENTER);                
        setVisible(true);        
    }                     
    
}

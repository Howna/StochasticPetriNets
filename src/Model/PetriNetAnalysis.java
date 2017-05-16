package Model;


/**
 *
 * @author Edwin
 */

import org.jdom2.Document;
import org.jdom2.Element;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.jdom2.input.SAXBuilder;
import java.util.List;
import org.jdom2.JDOMException;
import java.util.ArrayList;

public final class PetriNetAnalysis {
    
    private File file;    
    private SAXBuilder builder; 
    private StringBuffer info,calculations;    
    private ArrayList<PlacePetri> places;
    private ArrayList<DeterministicTransition> tin;
    private ArrayList<DeterministicTransition> tout;
    private ArrayList<String> l;
    private ArrayList<String> transitions;
    private int[] marking;
    private int[][] d1;
    private int[][] d2;
    private int[][] d;
    private ArrayList<double[]> treeVector;
    private double exd[];
    private Matrix matrix;
    private Tree reachabilityTree;
    
    public PetriNetAnalysis(){        
        setFile(new File("PetriNet.xml"));
        this.builder=new SAXBuilder();
        this.info=new StringBuffer(); 
        this.calculations=new StringBuffer();
        this.places=new ArrayList<>();        
        this.l=new ArrayList<>();
        this.transitions=new ArrayList<>();       
        this.treeVector=new ArrayList<>();
        this.matrix=new Matrix();              
    }
    
    public StringBuffer analyzePetriNet(){
        try{            
            Document document=(Document)builder.build(this.file);
            Element root=document.getRootElement();
            if(root.getName().equals("RedPetri")){
                this.info.append("CARACTERISTICAS DE LA RED \n");
                List<Element> listOfItems=root.getChildren();
                if(listOfItems.size() == 1){
                    Element at1=listOfItems.get(0);                    
                    if(at1.getName().equals("lugares")){                        
                        List<Element> places=at1.getChildren();
                        for(int i=0;i < places.size();i++){
                            Element l1=places.get(i);
                            String name=l1.getAttributeValue("name");
                            String marcado=l1.getAttributeValue("marcado");
                            boolean mar=(marcado.equals("si")) ? true : false;
                            int nummarcas=Integer.valueOf(l1.getAttributeValue("num"));                            
                            List<Element> elementoslugar=l1.getChildren();
                            Element i1=elementoslugar.get(0);
                            Element i2=elementoslugar.get(1);
                            List<Element> entradas=i1.getChildren();
                            List<Element> salidas=i2.getChildren();
                            this.tin=new ArrayList<DeterministicTransition>();
                            this.tout=new ArrayList<DeterministicTransition>();
                            if(entradas.size() != 0){                                
                                for(int e=0;e < entradas.size();e++){                                    
                                    Element t=entradas.get(e);                                       
                                    String nt=t.getAttributeValue("name");
                                    int num=Integer.valueOf(t.getAttributeValue("num"));
                                    this.tin.add(new DeterministicTransition(nt,num,false));
                                }                                                                        
                            }
                            if(salidas.size() != 0){                                
                                for(int e=0;e < salidas.size();e++){
                                    Element t=salidas.get(e);  
                                    String nt=t.getAttributeValue("name");
                                    int num=Integer.valueOf(t.getAttributeValue("num"));
                                    this.tout.add(new DeterministicTransition(nt,num,false));
                                }
                            }
                            this.places.add(new PlacePetri(name,mar,nummarcas,this.tout,this.tin));                            
                        }                        
                        infoPetriNet();
                    }else{
                        this.info.append("ERROR !\n");
                    }
                }
            }else{ 
                this.info.append("NO"); 
                return getResults();
            }
            this.info.append("\nFIN DE ANALISIS");
        }catch(JDOMException | IOException e){
            JOptionPane.showMessageDialog(null,e.getCause(),"ERROR",JOptionPane.ERROR_MESSAGE);
        }        
        return getResults();
    }
    
    public void getPlacesPetri(){
        if(this.places.size() == 0) return;
        else{
            for(int i=0;i < this.places.size();i++){
                String nomlugar=this.places.get(i).getName();
                boolean existe=false;
                for(int j=0;j < this.l.size();j++){
                    existe=false;
                    if(this.l.get(j).equals(nomlugar)){
                        existe=true;
                        break;
                    }
                }
                if(!existe) this.l.add(nomlugar);
            }
        }
    }
    
    public void getDeterministicTransitions(){
        if(this.places.size() == 0) return;
        else{
            for(int i=0;i < this.places.size();i++){
                ArrayList<DeterministicTransition> tr=this.places.get(i).getTinputs();
                for(int j=0;j < tr.size();j++){
                    boolean existe=false;
                    String nomtran=tr.get(j).getName();
                    for(int k=0;k < this.transitions.size();k++){                        
                        existe=false;
                        if(this.transitions.get(k).equals(nomtran)){
                            existe=true;
                            break;
                        }
                    }
                    if(!existe) this.transitions.add(nomtran);
                }                
                tr=this.places.get(i).getToutputs();
                for(int j=0;j < tr.size();j++){
                    boolean existe=false;
                    String nomtran=tr.get(j).getName();
                    for(int k=0;k < this.transitions.size();k++){                        
                        existe=false;
                        if(this.transitions.get(k).equals(nomtran)){
                            existe=true;
                            break;
                        }
                    }
                    if(!existe) this.transitions.add(nomtran);
                }
            }
        }
    }
    
    public void getInitialMarking(){
        this.marking=new int[this.places.size()];
        for(int i=0;i < this.places.size();i++){
            int num=this.places.get(i).getNumMark();
            this.marking[i]=num;
        }
    }
    
    public void getDMas(){
        this.d2=new int[this.transitions.size()][this.places.size()];
        for(int i=0;i < this.d2.length;i++){
            for(int j=0;j < this.d2[i].length;j++){
                this.d2[i][j]=0;
            }
        }
        for(int i=0;i < this.places.size();i++){
            PlacePetri l=this.places.get(i);
            ArrayList<DeterministicTransition> ts=l.getTinputs();
            for(int j=0;j < ts.size();j++){
                DeterministicTransition t=ts.get(j);
                String nom=t.getName();
                int num=t.getNum();
                for(int k=0;k < this.transitions.size();k++){
                    if(this.transitions.get(k).equals(nom)){
                        this.d2[k][i]=num;
                    }
                }
            }        
        }
    }
    
    public void getDMenos(){
        this.d1=new int[this.transitions.size()][this.places.size()];
        for(int i=0;i < this.d1.length;i++){
            for(int j=0;j < this.d1[i].length;j++){
                this.d1[i][j]=0;
            }
        }
        for(int i=0;i < this.places.size();i++){
            PlacePetri l=this.places.get(i);
            ArrayList<DeterministicTransition> ts=l.getToutputs();
            for(int j=0;j < ts.size();j++){
                DeterministicTransition t=ts.get(j);
                String nom=t.getName();
                int num=t.getNum();
                for(int k=0;k < this.transitions.size();k++){
                    if(this.transitions.get(k).equals(nom)){
                        this.d1[k][i]=num;
                    }
                }
            }
        }
    }
    
    public void getD(){
        this.d=this.matrix.subtract(d2, d1);
    }
    
    public boolean enabled(double marcacion[]){        
        double copiad[][]=this.matrix.convertToDouble(this.d);        
        exd=this.matrix.multiply(marcacion,copiad);        
        this.calculations.append("M*D: \n\n[");
        for(int i=0;i < exd.length;i++){
        	if(exd[i] >= 0) this.calculations.append(" "+exd[i]+"   ");
        	else this.calculations.append(exd[i]+"   ");
        }              
        this.calculations.append("]");
        marcacion=this.matrix.convertToDouble(this.marking);
        exd=this.matrix.add(marcacion, exd);
        this.calculations.append("\n\nM0 - M*D: \n\n[ ");
        for(int i=0;i < exd.length;i++){
        	if(exd[i] >= 0) this.calculations.append(" "+exd[i]+"   ");
        	else this.calculations.append(exd[i]+"   ");
        }
        this.calculations.append("]\n");
        boolean valido=true;
        for(int i=0;i < exd.length;i++){ if(exd[i] < 0 || exd[i] % 1 != 0) valido=false; }
        if(valido) this.calculations.append("\nES UNA MARCACION VALIDA.\n\n");
        else this.calculations.append("\nNO ES UNA MARCACION VALIDA.\n\n");
        return valido;
    }
    
    public ArrayList<String> sacarRafaga(char c[]){
    	ArrayList<String> rt=new ArrayList<String>();    	
    	String palabra=new String();    	
    	for(int i=0;i < c.length;i++){       		
    		if(c[i] != ',' && c[i] != '.') palabra=palabra+c[i];
    		else{    	    			
    			rt.add(palabra);
    			palabra=new String();
    		}
    	}
    	boolean valida=true;    	
    	for(int i=0;i < rt.size();i++){
    		if(this.transitions.indexOf(rt.get(i)) == -1){
    			valida=false;    	
    			break;
    		}
    	}
    	if(valida) return rt;
    	return null;
    }
    
    private int[] getVectorFire(String transicion){
    	int vector[]=new int[this.transitions.size()];
    	for(int i=0;i < this.transitions.size();i++){
    		if(this.transitions.get(i).equals(transicion)) vector[i]=1;
    		else vector[i]=0;
    	}
    	return vector;
    }       
    
    public double[] rafagaDisparos(char c[]){
        double vector[]=null;
    	this.calculations.setLength(0);
    	ArrayList<String> rafagas=sacarRafaga(c);
    	if(rafagas == null) {
    		JOptionPane.showMessageDialog(null,"NO ES UNA RAFAGA VALIDA","ERROR",JOptionPane.ERROR_MESSAGE);    		
    	}else{
    		ArrayList<int[]> listaVectores=new ArrayList<>();    		
    		for(int i=0;i < rafagas.size();i++){
    			int vec[]=getVectorFire(rafagas.get(i));
    			listaVectores.add(vec);
    		}
    		vector=this.matrix.convertToDouble(listaVectores.get(0));
    		for(int i=1;i < listaVectores.size();i++){
    			double v[]=this.matrix.convertToDouble(listaVectores.get(i));
    			vector=this.matrix.add(vector, v);
    		}
    		this.calculations.append("\nVECTOR DE LA RAFAGA INTRODUCIDA :\n\n[   ");
    		for(int i=0;i < vector.length;i++){
    			this.calculations.append(vector[i]+"   ");
    		}
    		this.calculations.append("]\n");    		
    		getCalculations(vector);
    	}
        return vector;
    }      
    
    public void getCalculations(double marcacion[]){  
    	getD();
        this.calculations.append("\nMATRIZ D= D+ - D-\n\n");       
        for(int i=0;i < this.d.length;i++){
            for(int j=0;j < this.d[i].length;j++){
            	if(this.d[i][j] >= 0) this.calculations.append(" "+this.d[i][j]+"   ");
            	else this.calculations.append(this.d[i][j]+"   ");
            }
            this.calculations.append("\n");
        }       
        this.calculations.append("\n");
        enabled(marcacion);        
    } 
    
    public Tree getReachabilityTree(){
        return this.reachabilityTree;
    }
    
    public void createReachabilityTree(){
        if(reachabilityTree == null){            
            Node root=new Node(marking,"",0);
            reachabilityTree=new Tree(root);
            treeVector.add(matrix.convertToDouble(marking));                        
            for(int i=0;i < transitions.size();i++){
                generarArbol(transitions.get(i),reachabilityTree.root,1);                
            }
            ArrayList<Node> nodosARedisparar=reachabilityTree.pruneTree();
            for(Node n : nodosARedisparar){
                for(String tran : transitions)
                    generarArbol(n.id+","+tran, n, n.level+1);
            }
        }
        reachabilityTree.assignPositionToNodes(reachabilityTree.root,
                reachabilityTree.root.posX, reachabilityTree.root.posY);
        this.calculations=new StringBuffer();
        reachabilityTree.printTree(reachabilityTree.root,this.calculations);         
    }            
    
    public void generarArbol(String rafaga,Node padre,int nivel){           
        double nuevaMarcacion[]=rafagaDisparos((rafaga + ".").toCharArray());
        if(nuevaMarcacion != null && enabled(nuevaMarcacion)){                                               
            Node nuevo=new Node(matrix.convertToInteger(exd),rafaga,nivel);
            reachabilityTree.addNode(padre, nuevo);
            if( ! existeMarcacion(exd) && ! esMarcacionW(exd)){
                treeVector.add(exd);                
                for(int i=0;i < transitions.size();i++){                    
                    generarArbol(rafaga + ","+transitions.get(i),nuevo,nivel+1);
                }   
            }          
        }
    }   
    
    public boolean existeMarcacion(double m[]){                
        if(treeVector.size() > 0){
            for(double vector[] : treeVector){
                if(matrix.compareVectors(m,vector) ) return true;
            }            
        }
        return false;
    }
    
    public boolean esMarcacionW(double marca[]){
        int contadorVectores=0;        
        for(int i=0;i < treeVector.size();i++){            
            int contadorNumeroCambios=0;
            double v[]=treeVector.get(i);
            for(int j=0;j < v.length;j++){
                if(v[j] != marca[j]) contadorNumeroCambios++;
            }
            if(contadorNumeroCambios == 1) contadorVectores++;
        }
        return contadorVectores >= 2;
    }    
    
    public void infoPetriNet(){
        reachabilityTree=null;
        getDeterministicTransitions();
        getPlacesPetri();
        getInitialMarking();
        getDMas();
        getDMenos();
        this.info.append("\nLUGARES:\n\n");
        for(int i=0;i < this.l.size();i++){
            this.info.append("Lugar : "+this.l.get(i)+"\n");
        }
        this.info.append("\nTRANSICIONES:\n\n");
        for(int i=0;i < this.transitions.size();i++){
            this.info.append("Transicion : "+this.transitions.get(i)+"\n");
        }
        this.info.append("\nMARCACION INICIAL :\n\n[  ");
        for(int i=0;i < this.marking.length;i++){
            this.info.append(this.marking[i]+"  ");
        }
        this.info.append("]\n");
        this.info.append("\nMATRIZ DE INCIDENCIA POSTERIOR:\n\n");
        for(int i=0;i < this.d2.length;i++){
            for(int j=0;j < this.d2[i].length;j++){
                this.info.append(this.d2[i][j]+"  ");
            }
            this.info.append("\n");
        }
        this.info.append("\nMATRIZ DE INCIDENCIA PREVIA:\n\n");
        for(int i=0;i < this.d1.length;i++){
            for(int j=0;j < this.d1[i].length;j++){
                this.info.append(this.d1[i][j]+"  ");
            }
            this.info.append("\n");
        }
    }
    
    public int[] getMarcacion(char c[]){
    	ArrayList<String> marca=new ArrayList<String>();
    	String palabra=new String();    	
    	for(int i=0;i < c.length;i++){
    		if(c[i] != ',' && c[i] != '.') palabra=palabra+c[i];
    		else{
    			marca.add(palabra);    			
    			palabra=new String();
    		}
    	} 
    	boolean valida=true;
    	int v[]=new int[marca.size()];
    	if(marca.size() == this.marking.length){    		
    		for(int i=0;i < marca.size();i++){
    			try{
    				palabra=marca.get(i);
    				v[i]=Integer.valueOf(palabra);    		    				
    			}catch(Exception e){
    				valida=false;
    				JOptionPane.showMessageDialog(null,"LA MARCACION NO VALIDA","ERROR",JOptionPane.ERROR_MESSAGE);
    				break;    				
    			}
    		}    		
    	}else{
    		JOptionPane.showMessageDialog(null,"LA MARCACION NO VALIDA","ERROR",JOptionPane.ERROR_MESSAGE);
    		valida=false;
    	}
    	if(valida) return v;
    	else return null;
    }
           
    public StringBuffer getCalculations(){
    	return this.calculations;
    }

    public void setFile(File file) {
        this.file = file;
    }
    
    public File getFile(){
        return this.file;
    }
    
    public StringBuffer getResults(){
        return this.info;
    }
        
} 

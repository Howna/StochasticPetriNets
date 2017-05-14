
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by kevincastro on 5/12/17.
 */
public class Gui extends PApplet{

    private static Petrinet pn = new Petrinet("PetriNet");
    
    public static void main(String[] args) {

        PApplet.main("Gui", args);
        
        Transition t1 = pn.transition("t1",2);
        Transition t2 = pn.transition("t2",1.5);

        Place p1 = pn.place("p1", 1);
        Place p2 = pn.place("p2");

        Arc a1 = pn.arc("a1", p1, t1);
        Arc a2 = pn.arc("a2", t1, p2);
        Arc a3 = pn.arc("a3", p2, t2);
        Arc a4 = pn.arc("a4", t2, p1);

        //test
        /*
        try {
            System.out.println("Examples of fires");                
            t1.setDelayTime(t1.getRate());
            System.out.println("Transition 1 with delay time: "+t1.getDelayTime());
            Thread.sleep((long)t1.getDelayTime()*1000);            
            System.out.println("Fire 1");
            t2.setDelayTime(t2.getRate());
            System.out.println("Transition 2 with delay time: "+t2.getDelayTime());
            Thread.sleep((long) t2.getDelayTime()*1000);
            System.out.println("Fire 2");
        } catch (InterruptedException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }
        */


        for(int i = 0; i < pn.getArcs().size(); i++){
            System.out.println(pn.getArcs().get(i).place);
            System.out.println(pn.getArcs().get(i).transition);
            System.out.println(pn.getArcs().get(i).direction);
            System.out.println("-------");
        }
                
    }
    
    public void settings(){
        size(500,500);

    }

    public void setup(){
        background(255);
    }

    public void draw(){
        //drawPetriNet();
    }

    public void drawPetriNet(){

        ArrayList<Place> places = new ArrayList<>(pn.getPlaces());
        ArrayList<Transition> transitions = new ArrayList<>(pn.getTransitions());

    }
    
}

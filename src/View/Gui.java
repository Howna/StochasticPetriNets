package View;

import Model.PetriNetAnalysis;
import Model.Arc;
import Model.Place;
import Model.Petrinet;
import Model.Transition;
import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by kevincastro on 5/12/17.
 */

public class Gui extends PApplet{

    private static Petrinet pn = new Petrinet("PetriNet");
    
    public static void main(String[] args) {
        
        PApplet.main("View.Gui",args);                
        Transition t1 = pn.transition("t1",2);
        Transition t2 = pn.transition("t2",1.5);
        Transition t3 = pn.transition("t3",1.2);
        Transition t4 = pn.transition("t4",1);
        Transition t5 = pn.transition("t5",2.1);
        Place p1 = pn.place("p1", 1);
        Place p2 = pn.place("p2");
        Place p3 = pn.place("p3");
        Place p4 = pn.place("p4");
        Place p5 = pn.place("p5");
        Arc a1 = pn.arc("a1", p1, t1);
        Arc a2 = pn.arc("a2", t1, p3);
        Arc a3 = pn.arc("a3", p3, t3);               
        Arc a4 = pn.arc("a4", t3, p4);        
        Arc a5 = pn.arc("a5", p4, t5);        
        Arc a6 = pn.arc("a6", t5, p1);
        Arc a7 = pn.arc("a7", t1, p2);
        Arc a8 = pn.arc("a8", p2, t2);
        Arc a9 = pn.arc("a9", t2, p5);
        Arc a10 = pn.arc("a10", p5, t5);
        Arc a11 = pn.arc("a11", p5, t4);
        Arc a12 = pn.arc("a12", t4, p2);                
        Petrinet.createPetriNet(pn);
        PetriNetAnalysis pna = new PetriNetAnalysis();
        StringBuffer results = pna.analyzePetriNet();                
        pna.createReachabilityTree();
        new ResultsWindow().showResultsWindow(results,pna.getReachabilityTree());         
        
    }
    
    public void settings(){
        size(500,500);
    }

    public void setup(){
        background(255);
    }

    public void draw(){
        drawPetriNet();
    }

    public void drawPetriNet(){

        int xPos = 100;
        int yPos = height/2;

        ArrayList<Place> places = new ArrayList<>(pn.getPlaces());
        ArrayList<Transition> transitions = new ArrayList<>(pn.getTransitions());


        for(int i = 0; i < pn.getArcs().size(); i++){
            if(places.contains(pn.getArcs().get(i).place)){
                fill(255);
                ellipse(xPos, yPos, 30,30);

                fill(0);
                text(Integer.toString(pn.getArcs().get(i).place.getTokens()), xPos - 10, yPos);

                places.remove(pn.getArcs().get(i).place);

                xPos += 50;
            }

            if(transitions.contains(pn.getArcs().get(i).transition)){
                fill(255);
                rect(xPos, yPos - 15, 15,30);

                transitions.remove(pn.getArcs().get(i).transition);

                xPos += 70;
            }

        }
    }
    
}

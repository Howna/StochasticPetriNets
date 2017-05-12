import processing.core.PApplet;

import java.util.ArrayList;
import java.util.StringJoiner;

/**
 * Created by kevincastro on 5/12/17.
 */
public class Gui extends PApplet{

    public static void main(String[] args) {

        PApplet.main("Gui", args);
    }

    Petrinet pn = new Petrinet("PetriNet");
    Transition t1 = pn.transition("t1");
    Transition t2 = pn.transition("t2");

    Place p1 = pn.place("p1", 1);
    Place p2 = pn.place("p2");

    Arc a1 = pn.arc("a1", p1, t1);
    Arc a2 = pn.arc("a2", t1, p2);
    Arc a3 = pn.arc("a3", p2, t2);
    Arc a4 = pn.arc("a4", t2, p1);

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

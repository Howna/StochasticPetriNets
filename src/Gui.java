
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by kevincastro on 5/12/17.
 */
public class Gui extends PApplet{

    //private static Petrinet pn = new Petrinet("PetriNet");

    public static void main(String[] args) {

        PApplet.main("Gui", args);

    }

    Petrinet pn = new Petrinet("PetriNet");

    /*
    //Test case 1
    Transition t1 = pn.transition("t1",2, 1);
    Transition t2 = pn.transition("t2",1.5, 1);
    Transition t3 = pn.transition("t3",1.5, 2);

    Place p1 = pn.place("p1", 1, 1);
    Place p2 = pn.place("p2", 1, 1);
    Place p3 = pn.place("p3", 2);
    Place p4 = pn.place("p4", 2);

    Arc a1 = pn.arc("a1", p1, t1);
    Arc a2 = pn.arc("a2", p2, t2);
    Arc a3 = pn.arc("a3", t1, p3);
    Arc a4 = pn.arc("a4", t1, p4);
    Arc a5 = pn.arc("a5", t2, p3);
    Arc a6 = pn.arc("a6", p3, t3);
    Arc a7 = pn.arc("a7", t3, p1);
    Arc a8 = pn.arc("a8", t3, p2);
    */

    /*
    //Test case 2
    Transition t1 = pn.transition("t1",2, 1);
    Transition t2 = pn.transition("t2",1.5, 1);
    Transition t3 = pn.transition("t3",1.5, 2);

    Place p1 = pn.place("p1", 1, 1);
    Place p2 = pn.place("p2", 1, 1);
    Place p3 = pn.place("p3", 1);
    Place p4 = pn.place("p4", 2);
    Place p5 = pn.place("p5", 2);

    Arc a1 = pn.arc("a1", p1, t1);
    Arc a2 = pn.arc("a2", p2, t2);
    Arc a3 = pn.arc("a3", p3, t2);
    Arc a4 = pn.arc("a4", t1, p4);
    Arc a5 = pn.arc("a5", t1, p5);
    Arc a6 = pn.arc("a6", t2, p4);
    Arc a7 = pn.arc("a7", p4, t3);
    Arc a8 = pn.arc("a8", t3, p1);
    Arc a9 = pn.arc("a9", t3, p2);
    */


    //Test case 3
    Place p1 = pn.place("p1", 5, 1);
    Place p2 = pn.place("p2",2);

    Transition t1 = pn.transition("t1", 2, 1);
    Transition t2 = pn.transition("t2",1.5, 2);

    Arc a1 = pn.arc("a1", p1, t1);
    Arc a2 = pn.arc("a2", t1, p2);
    Arc a3 = pn.arc("a3", p2, t2);
    Arc a4 = pn.arc("a4", t2, p1);



    //------------------------- Processing ---------------------------//

    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private ArrayList<Arc> arcs;


    public void settings(){
        size(500,500);
    }

    public void setup(){
        places = new ArrayList<>(pn.getPlaces());
        transitions = new ArrayList<>(pn.getTransitions());
        arcs = new ArrayList<>(pn.getArcs());
        background(255);
    }

    public void draw(){
        drawPetriNet();
    }

    public void drawPetriNet(){
        Place p;
        Transition t;
        Arc a;

        for(int i = 0; i < places.size(); i++){
            //Check all places except the last one
            if(i < places.size() - 1 ){
                //Only one place per lvl
                if(i == 0){
                    if(places.get(i).lvl < places.get(i + 1).lvl){
                        //Position first lvl
                        places.get(i).inX = 100;
                        places.get(i).inY = height/2;
                        places.get(i).outX = places.get(i).inX + 30;
                        places.get(i).outY = places.get(i).inY;
                    }
                    //More than one place per lvl
                    else{
                        if(places.get(i).lvl == places.get(i + 1).lvl){
                            places.get(i).inX = 100;
                            places.get(i).inY = height/6;
                            places.get(i).outX = places.get(i).inX + 30;
                            places.get(i).outY = places.get(i).inY;
                            places.get(i + 1).inX = places.get(i).inX;
                            places.get(i + 1).inY = places.get(i).inY + 50;
                            places.get(i + 1).outX = places.get(i + 1).inX + 30;
                            places.get(i + 1).outY = places.get(i + 1).inY;
                        }
                    }
                }
                else{
                    if(places.get(i).lvl < places.get(i + 1).lvl && places.get(i - 1).lvl < places.get(i).lvl){
                        //Position first lvl
                        places.get(i).inX = places.get(i).connectedFrom.outX + 50;
                        places.get(i).inY = places.get(i).connectedFrom.outY;
                        places.get(i).outX = places.get(i).inX + 30;
                        places.get(i).outY = places.get(i).inY;
                    }
                    //More than one place per lvl
                    else{
                        if(places.get(i).lvl == places.get(i + 1).lvl){
                            if(places.get(i - 1).lvl == places.get(i).lvl){
                                places.get(i + 1).inX = places.get(i).inX;
                                places.get(i + 1).inY = places.get(i).inY + 50;
                                places.get(i + 1).outX = places.get(i + 1).inX + 30;
                                places.get(i + 1).outY = places.get(i + 1).inY;
                            }
                            else{
                                //Position of other lvls base on the Pos of previous lvl
                                places.get(i).inX = places.get(i).connectedFrom.outX + 50;
                                places.get(i).inY = places.get(i).connectedFrom.outY;
                                places.get(i).outX = places.get(i).inX + 30;
                                places.get(i).outY = places.get(i).inY;
                                places.get(i + 1).inX = places.get(i).inX;
                                places.get(i + 1).inY = places.get(i).inY + 50;
                                places.get(i + 1).outX = places.get(i + 1).inX + 30;
                                places.get(i + 1).outY = places.get(i + 1).inY;
                            }
                        }
                    }
                }
            }
            //Check last place
            else{
                //Only one place per lvl
                if(places.get(i).lvl > places.get(i - 1).lvl){
                    places.get(i).inX = places.get(i).connectedFrom.outX + 50;
                    places.get(i).inY = places.get(i).connectedFrom.outY;
                    places.get(i).outX = places.get(i).inX + 30;
                    places.get(i).outY = places.get(i).inY;
                }
                //More than one place per lvl
                else{
                    if(places.get(i).lvl == places.get(i - 1).lvl){
                        places.get(i).inX = places.get(i - 1).inX;
                        places.get(i).inY = places.get(i - 1).inY + 50;
                        places.get(i).outX = places.get(i).inX + 30;
                        places.get(i).outY = places.get(i).inY;
                    }
                }
            }

            //Compare all transitions with each place
            for(int j = 0; j < transitions.size(); j++){
                //Check all transitions except the last one
                if(j < transitions.size() - 1){
                    //Transition goes after the place
                    if(transitions.get(j).lvl == places.get(i).lvl){
                        if(j == 0){
                            if(transitions.get(j).lvl < transitions.get(j + 1).lvl){
                                transitions.get(j).inX =  transitions.get(j).connectedFrom.outX + 50;
                                transitions.get(j).inY =  transitions.get(j).connectedFrom.outY;
                                transitions.get(j).outX = transitions.get(j).inX + 10;
                                transitions.get(j).outY = transitions.get(j).inY;
                            }
                            //More than one transition per lvl
                            else{
                                if(transitions.get(j).lvl == transitions.get(j + 1).lvl){
                                    transitions.get(j).inX =  transitions.get(j).connectedFrom.outX + 50;
                                    transitions.get(j).inY =  transitions.get(j).connectedFrom.outY;
                                    transitions.get(j).outX = transitions.get(j).inX + 10;
                                    transitions.get(j).outY = transitions.get(j).inY;
                                    transitions.get(j + 1).inX =  transitions.get(j).inX;
                                    transitions.get(j + 1).inY =  transitions.get(j).inY + 50;
                                    transitions.get(j + 1).outX = transitions.get(j + 1).inX + 10;
                                    transitions.get(j + 1).outY = transitions.get(j + 1).inY;
                                }
                            }
                        }
                        else{
                            if(transitions.get(j).lvl < transitions.get(j + 1).lvl && transitions.get(j - 1).lvl < transitions.get(j).lvl){
                                transitions.get(j).inX =  transitions.get(j).connectedFrom.outX + 50;
                                transitions.get(j).inY =  transitions.get(j).connectedFrom.outY;
                                transitions.get(j).outX = transitions.get(j).inX + 10;
                                transitions.get(j).outY = transitions.get(j).inY;
                            }
                            //More than one transition per lvl
                            else{
                                if(transitions.get(j).lvl == transitions.get(j + 1).lvl){
                                    transitions.get(j).inX =  transitions.get(j).connectedFrom.outX + 50;
                                    transitions.get(j).inY =  transitions.get(j).connectedFrom.outY;
                                    transitions.get(j).outX = transitions.get(j).inX + 10;
                                    transitions.get(j).outY = transitions.get(j).inY;
                                    transitions.get(j + 1).inX =  transitions.get(j).inX;
                                    transitions.get(j + 1).inY =  transitions.get(j).inY + 50;
                                    transitions.get(j + 1).outX = transitions.get(j + 1).inX + 10;
                                    transitions.get(j + 1).outY = transitions.get(j + 1).inY;
                                }
                            }
                        }
                    }
                }
                //Check last transition
                else{
                    if(transitions.get(j).lvl == places.get(i).lvl){
                        //Only one transition per lvl
                        if(transitions.get(j).lvl > transitions.get(j - 1).lvl){
                            transitions.get(j).inX =  transitions.get(j).connectedFrom.outX + 50;
                            transitions.get(j).inY =  transitions.get(j).connectedFrom.outY;
                            transitions.get(j).outX = transitions.get(j).inX + 10;
                            transitions.get(j).outY = transitions.get(j).inY;
                        }
                        //More than one transition per lvl
                        else{
                            transitions.get(j).inX =  transitions.get(j - 1).outX;
                            transitions.get(j).inY =  transitions.get(j - 1).outY + 50;
                            transitions.get(j).outX = transitions.get(j).inX + 10;
                            transitions.get(j).outY = transitions.get(j).inY;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < arcs.size(); i++){
            p = arcs.get(i).place;
            t = arcs.get(i).transition;
            a = arcs.get(i);

            fill(0);
            textSize(10);
            text(t.getName(), t.inX , t.inY - 16);
            text(p.getName(), p.inX + 10, p.inY - 16);

            if(a.direction == Arc.Direction.PLACE_TO_TRANSITION){
                fill(255);
                ellipse(p.inX + 15, p.inY, 30, 30);//Place created
                fill(0);
                text(Integer.toString(p.getTokens()),p.inX + 12, p.inY + 3);//create tokens

                drawArrow(p.outX, p.outY, t.inX, t.inY);
                fill(0);
                text(Integer.toString(a.getWeight()),p.outX + (t.inX - p.outX)/2, p.outY + (t.inY - p.outY)/2 - 2);
                fill(255);
                rect(t.inX, t.inY - 15, 10, 30);
            }
            else{
                if(t.outX > p.outX){
                    fill(255);
                    drawArrow(t.outX , t.outY, t.outX + 10, t.outY);
                    fill(0);
                    text(Integer.toString(a.getWeight()), t.outX + 12, t.outY - 12);
                    fill(255);
                    ellipse(p.inX + 15, p.inY, 30, 30);//Place created
                    fill(0);
                    text(Integer.toString(p.getTokens()),p.inX + 12, p.inY + 3);//create tokens
                    fill(255,0,0);
                    ellipse(t.outX + 15 , t.outY, 10, 10);
                    drawArrow(p.inX - 10 , p.inY, p.inX , p.inY);
                    ellipse(p.inX - 15 , p.inY, 10, 10);
                }
                else{
                    fill(255);
                    drawArrow(t.outX , t.outY, p.inX , p.inY);
                    fill(0);
                    text(Integer.toString(a.getWeight()),t.outX + (p.inX - t.outX)/2, t.outY + (p.inY - t.outY)/2 - 2);
                    fill(255);
                    ellipse(p.inX + 15, p.inY, 30, 30);//Place created
                    fill(0);
                    text(Integer.toString(p.getTokens()),p.inX + 12, p.inY + 3);//create tokens
                }

            }
        }

        //Fire
        for(int i = 0; i < transitions.size(); i++){
            if(transitions.get(i).canFire()){
                fill(0, 255, 0);
                rect(transitions.get(i).inX + 3, transitions.get(i).inY + 17, 5, 5);
            }
            else{
                fill(255, 0, 0);
                rect(transitions.get(i).inX + 3, transitions.get(i).inY + 17, 5, 5);
            }
        }
    }

    public void fireTransition(){
        for(int i = 0; i < transitions.size(); i++){
            if(transitions.get(i).canFire()){
                transitions.get(i).fire();
            }
        }
    }

    public void mousePressed() {

    }




    public void drawArrow(float x1, float y1, float x2, float y2) {
        float a = 2;
        pushMatrix();
        translate(x2, y2);
        rotate(atan2(y2 - y1, x2 - x1));
        triangle(- a * 2 , - a, 0, 0, - a * 2, a);
        popMatrix();
        line(x1, y1, x2, y2);
    }

}

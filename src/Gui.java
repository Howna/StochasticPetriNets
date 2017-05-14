
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

    /*
    Transition t1 = pn.transition("t1",2, 1);
    Transition t2 = pn.transition("t2",1.5, 2);

    Place p1 = pn.place("p1", 1, 1);
    Place p2 = pn.place("p2", 1, 1);
    Place p3 = pn.place("p3", 2);

    Arc a1 = pn.arc("a1", p1, t1);
    Arc a2 = pn.arc("a1", p2, t1);
    Arc a3 = pn.arc("a2", t1, p3);
    Arc a4 = pn.arc("a3", p3, t2);
    Arc a5 = pn.arc("a4", t2, p1);
    */

    Petrinet pn = new Petrinet("PetriNet");

    Place p1 = pn.place("p1", 1, 1);
    Place p2 = pn.place("p2", 2);

    Transition t1 = pn.transition("t1", 2, 1);
    Transition t2 = pn.transition("t2",1.5, 2);

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


    //------------------------- Processing ---------------------------//

    ArrayList<Place> places;
    ArrayList<Transition> transitions;
    ArrayList<Arc> arcs;

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
                if(places.get(i).lvl < places.get(i + 1).lvl){
                    //Position first lvl
                    if(i == 0){
                        places.get(i).inX = 100;
                        places.get(i).inY = height/2;
                        places.get(i).outX = places.get(i).inX + 30;
                        places.get(i).outY = places.get(i).inY;
                    }
                    //Position of other lvls base on the Pos of previous lvl
                    else{
                        places.get(i).inX = places.get(i - 1).outX + 90;
                        places.get(i).inY = places.get(i - 1).outY;
                        places.get(i).outX = places.get(i).inX + 30;
                        places.get(i).outY = places.get(i).inY;
                    }
                }
                //More than one place per lvl
                else{

                }
            }
            //Check last place
            else{
                //Only one place per lvl
                if(places.get(i).lvl > places.get(i - 1).lvl){
                    places.get(i).inX = places.get(i - 1).outX + 90;
                    places.get(i).inY = places.get(i - 1).outY;
                    places.get(i).outX = places.get(i).inX + 30;
                    places.get(i).outY = places.get(i).inY;
                }
                //More than one place per lvl
                else{

                }
            }

            //Compare all transitions with each place
            for(int j = 0; j < transitions.size(); j++){
                //Check all transitions except the last one
                if(j < transitions.size() - 1){
                    //Transition goes after the place
                    if(transitions.get(j).lvl == places.get(i).lvl){
                        //Only one transition per lvl
                        if(transitions.get(j).lvl < transitions.get(j + 1).lvl){
                            transitions.get(j).inX =  places.get(i).outX + 40;
                            transitions.get(j).inY = height/2;
                            transitions.get(j).outX = transitions.get(j).inX + 10;
                            transitions.get(j).outY = transitions.get(j).inY;
                        }
                        //More than one transition per lvl
                        else{

                        }
                    }
                }
                //Check last transition
                else{
                    if(transitions.get(j).lvl == places.get(i).lvl){
                        //Only one transition per lvl
                        if(transitions.get(j).lvl > transitions.get(j - 1).lvl){
                            transitions.get(j).inX =  places.get(i).outX + 40;
                            transitions.get(j).inY = height/2;
                            transitions.get(j).outX = transitions.get(j).inX + 10;
                            transitions.get(j).outY = transitions.get(j).inY;
                        }
                        //More than one transition per lvl
                        else{

                        }
                    }
                }
            }
        }

        for(int i = 0; i < arcs.size(); i++){
            p = arcs.get(i).place;
            t = arcs.get(i).transition;
            a = arcs.get(i);

            if(a.direction == Arc.Direction.PLACE_TO_TRANSITION){
                fill(255);
                ellipse(p.inX + 15, p.inY, 30, 30);
                drawArrow(p.outX, p.outY, t.inX, t.inY);
                rect(t.inX, t.inY - 15, 10, 30);
            }
            else{
                if(t.outX > p.outX){
                    drawArrow(t.outX , t.outY, t.outX + 10, t.outY);
                    fill(255,0,0);
                    ellipse(t.outX + 15 , t.outY, 10, 10);
                    drawArrow(p.inX - 10 , p.inY, p.inX , p.inY);
                    ellipse(p.inX - 15 , p.inY, 10, 10);
                }
                else{
                    drawArrow(t.outX , t.outY, p.inX , p.inY);
                }

            }
        }
    }

    void drawArrow(float x1, float y1, float x2, float y2) {
        float a = 2;
        pushMatrix();
        translate(x2, y2);
        rotate(atan2(y2 - y1, x2 - x1));
        triangle(- a * 2 , - a, 0, 0, - a * 2, a);
        popMatrix();
        line(x1, y1, x2, y2);
    }
    
}

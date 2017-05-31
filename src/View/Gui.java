package View;

import Model.PetriNetAnalysis;
import Model.Arc;
import Model.Place;
import Model.Petrinet;
import Model.Transition;
import processing.core.PApplet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.text.DecimalFormat;

import java.util.ArrayList;

/**
 * Created by kevincastro on 5/12/17.
 */

public class Gui extends PApplet{

    private static Petrinet pn = new Petrinet("PetriNet");


    Transition t1 = pn.transition("t1",2, 1);
    Transition t2 = pn.transition("t2",1.5, 2);
    Transition t3 = pn.transition("t3",1.2, 2);
    Transition t4 = pn.transition("t4",1, 3);
    Transition t5 = pn.transition("t5",2.1, 3);
    Place p1 = pn.place("p1", 1, 1);
    Place p2 = pn.place("p2", 2);
    Place p3 = pn.place("p3", 2);
    Place p4 = pn.place("p4", 3);
    Place p5 = pn.place("p5", 3);
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



    public static void main(String[] args) {
        
        PApplet.main("View.Gui",args);

        Petrinet.createPetriNet(pn);
        PetriNetAnalysis pna = new PetriNetAnalysis();
        StringBuffer results = pna.analyzePetriNet();
        pna.createReachabilityTree();
        new ResultsWindow().showResultsWindow(results,pna.getReachabilityTree());

    }

    //------------------------- Processing ---------------------------//

    //Stocasthic petri net drawing and animation

    private ArrayList<Place> places;
    private ArrayList<Transition> transitions;
    private ArrayList<Arc> arcs;
    //boolean simulate;

    float r ;
    float g ;
    float b ;

    int circleX, circleY, circleColor, circleHighlight;
    boolean circleOver = false;

    public void settings(){
        size(1000,600);
    }

    public void setup(){
        places = new ArrayList<>(pn.getPlaces());
        transitions = new ArrayList<>(pn.getTransitions());
        arcs = new ArrayList<>(pn.getArcs());
        background(255);

        for(int i = 0; i < transitions.size(); i++){
            transitions.get(i).setDelayTime(transitions.get(i).getRate());
        }

        r = random(0,255);
        g = random(0,255);
        b = random(0,255);

        circleColor = color(255);
        circleHighlight = color(0,255,0);
        circleX = width - 50;
        circleY = 50;
        //simulate = false;
    }

    public void draw(){
        background(255);
        drawPetriNet();
        playButton();

        /*if(simulate){
            fireTransition();
        }*/
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
                        places.get(i).inY = height/4;
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
                            places.get(i + 1).inY = places.get(i).inY + 90;
                            places.get(i + 1).outX = places.get(i + 1).inX + 30;
                            places.get(i + 1).outY = places.get(i + 1).inY;
                        }
                    }
                }
                else{
                    if(places.get(i).lvl < places.get(i + 1).lvl && places.get(i - 1).lvl < places.get(i).lvl){
                        //Position first lvl
                        places.get(i).inX = places.get(i).connectedFrom.get(0).outX + 70;
                        places.get(i).inY = places.get(i).connectedFrom.get(0).outY;
                        places.get(i).outX = places.get(i).inX + 30;
                        places.get(i).outY = places.get(i).inY;
                    }
                    //More than one place per lvl
                    else{
                        if(places.get(i).lvl == places.get(i + 1).lvl){
                            if(places.get(i - 1).lvl == places.get(i).lvl){
                                places.get(i + 1).inX = places.get(i).inX;
                                places.get(i + 1).inY = places.get(i).inY + 90;
                                places.get(i + 1).outX = places.get(i + 1).inX + 30;
                                places.get(i + 1).outY = places.get(i + 1).inY;
                            }
                            else{
                                //Position of other lvls base on the Pos of previous lvl
                                places.get(i).inX = places.get(i).connectedFrom.get(0).outX + 70;
                                places.get(i).inY = places.get(i).connectedFrom.get(0).outY;
                                places.get(i).outX = places.get(i).inX + 30;
                                places.get(i).outY = places.get(i).inY;
                                places.get(i + 1).inX = places.get(i).inX;
                                places.get(i + 1).inY = places.get(i).inY + 90;
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
                    places.get(i).inX = places.get(i).connectedFrom.get(0).outX + 70;
                    places.get(i).inY = places.get(i).connectedFrom.get(0).outY;
                    places.get(i).outX = places.get(i).inX + 30;
                    places.get(i).outY = places.get(i).inY;
                }
                //More than one place per lvl
                else{
                    if(places.get(i).lvl == places.get(i - 1).lvl){
                        places.get(i).inX = places.get(i - 1).inX;
                        places.get(i).inY = places.get(i - 1).inY + 90;
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
                                transitions.get(j).inX =  transitions.get(j).connectedFrom.get(0).outX + 70;
                                transitions.get(j).inY =  transitions.get(j).connectedFrom.get(0).outY;
                                transitions.get(j).outX = transitions.get(j).inX + 10;
                                transitions.get(j).outY = transitions.get(j).inY;
                            }
                            //More than one transition per lvl
                            else{
                                if(transitions.get(j).lvl == transitions.get(j + 1).lvl){
                                    transitions.get(j).inX =  transitions.get(j).connectedFrom.get(0).outX + 70;
                                    transitions.get(j).inY =  transitions.get(j).connectedFrom.get(0).outY;
                                    transitions.get(j).outX = transitions.get(j).inX + 10;
                                    transitions.get(j).outY = transitions.get(j).inY;
                                    transitions.get(j + 1).inX =  transitions.get(j).inX;
                                    transitions.get(j + 1).inY =  transitions.get(j).inY + 90;
                                    transitions.get(j + 1).outX = transitions.get(j + 1).inX + 10;
                                    transitions.get(j + 1).outY = transitions.get(j + 1).inY;
                                }
                            }
                        }
                        else{
                            if(transitions.get(j).lvl < transitions.get(j + 1).lvl && transitions.get(j - 1).lvl < transitions.get(j).lvl){
                                transitions.get(j).inX =  transitions.get(j).connectedFrom.get(0).outX + 70;
                                transitions.get(j).inY =  transitions.get(j).connectedFrom.get(0).outY;
                                transitions.get(j).outX = transitions.get(j).inX + 10;
                                transitions.get(j).outY = transitions.get(j).inY;
                            }
                            //More than one transition per lvl
                            else{
                                if(transitions.get(j).lvl == transitions.get(j + 1).lvl){
                                    transitions.get(j).inX =  transitions.get(j).connectedFrom.get(0).outX + 70;
                                    transitions.get(j).inY =  transitions.get(j).connectedFrom.get(0).outY;
                                    transitions.get(j).outX = transitions.get(j).inX + 10;
                                    transitions.get(j).outY = transitions.get(j).inY;
                                    transitions.get(j + 1).inX =  transitions.get(j).inX;
                                    transitions.get(j + 1).inY =  transitions.get(j).inY + 90;
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
                        if(transitions.size() == 1){
                            transitions.get(j).inX =  transitions.get(j).connectedFrom.get(0).outX + 70;
                            transitions.get(j).inY =  transitions.get(j).connectedFrom.get(0).outY;
                            transitions.get(j).outX = transitions.get(j).inX + 10;
                            transitions.get(j).outY = transitions.get(j).inY;
                        }
                        else{
                            if(transitions.get(j).lvl > transitions.get(j - 1).lvl){
                                transitions.get(j).inX =  transitions.get(j).connectedFrom.get(0).outX + 70;
                                transitions.get(j).inY =  transitions.get(j).connectedFrom.get(0).outY;
                                transitions.get(j).outX = transitions.get(j).inX + 10;
                                transitions.get(j).outY = transitions.get(j).inY;
                            }
                            //More than one transition per lvl
                            else{
                                transitions.get(j).inX =  transitions.get(j - 1).inX;
                                transitions.get(j).inY =  transitions.get(j - 1).inY + 90;
                                transitions.get(j).outX = transitions.get(j).inX + 10;
                                transitions.get(j).outY = transitions.get(j).inY;
                            }
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
                text("r: " + Double.toString(t.getRate()), t.inX - 10, t.inY + 35);
                textSize(8);
                text("t: " + new DecimalFormat("#.###").format(t.getDelayTime()), t.inX - 10, t.inY + 45);
                fill(255);
                rect(t.inX, t.inY - 15, 10, 30);

            }
            else{
                if(t.outX > p.outX){
                    if(p.connectedFrom.size() > 1){

                        fill(255);
                        drawArrow(t.outX , t.outY, t.outX + 10, t.outY);
                        fill(0);
                        text(Integer.toString(a.getWeight()), t.outX + 12, t.outY - 12);
                        fill(r,g,b);
                        ellipse(t.outX + 15 , t.outY, 10, 10);
                        ellipse(p.inX + 15, p.inY - 45, 10, 10);
                        fill(0);
                        drawArrow(p.inX + 15, p.outY - 40, p.inX + 15, p.outY - 30);
                    }
                    else{

                        fill(255);
                        drawArrow(t.outX , t.outY, t.outX + 10, t.outY);
                        fill(0);
                        text(Integer.toString(a.getWeight()), t.outX + 12, t.outY - 12);
                        fill(255);
                        ellipse(p.inX + 15, p.inY, 30, 30);//Place created
                        fill(0);
                        text(Integer.toString(p.getTokens()),p.inX + 12, p.inY + 3);//create tokens
                        drawArrow(p.inX - 10 , p.inY, p.inX , p.inY);
                        fill(r * 2,g / 2,b * 3.5f);
                        ellipse(t.outX + 15 , t.outY, 10, 10);
                        ellipse(p.inX - 15 , p.inY, 10, 10);
                    }
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

        //simulate = false;

        ArrayList<Transition> fireableTransitions = new ArrayList<>();

        for(int i = 0; i < transitions.size(); i++){

            if(transitions.get(i).canFire()) {
                fireableTransitions.add(transitions.get(i));
            }

            if(i == transitions.size() - 1){

                ArrayList<Transition> sortedT = new ArrayList<>();

                //sort transitions in shooting order
                for(int t = 0; t < fireableTransitions.size(); t++){
                    if(t == 0){
                        sortedT.add(fireableTransitions.get(t));
                    }
                    else{
                        for(int j = 0; j < sortedT.size(); j++){
                            if( fireableTransitions.get(t).getDelayTime() < sortedT.get(j).getDelayTime() ){
                                sortedT.add(j, fireableTransitions.get(t));
                                break;
                            }
                            else{
                                if(j == sortedT.size() - 1){
                                    sortedT.add(fireableTransitions.get(t));
                                    break;
                                }
                            }
                        }
                    }
                }

                //for(int l = 0; l < sortedT.size(); l++){
                    //if(sortedT.get(l).canFire()){
                        //sortedT.get(l).fire();
                    //}
                //}
               sortedT.get(0).fire();

                for(int k = 0; k < transitions.size(); k++){
                    transitions.get(k).setDelayTime(transitions.get(k).getRate());
                }


            }


        }
    }

    public void mousePressed() {
        /*if (circleOver) {
            if(simulate == false){
                System.out.println("Simulacion iniciada");
                simulate = true;
            }
            else{
                System.out.println("Simulacion detenida");
                simulate = false;
            }

        }*/
        if (circleOver) {fireTransition();}
    }

    public void playButton(){
        update(mouseX, mouseY);
        if (circleOver) {
            fill(circleHighlight);
        } else {
            fill(circleColor);
        }
        ellipse(width - 50, 50, 50,50);
    }

    public boolean overCircle(int x, int y, int diameter) {
        float disX = x - mouseX;
        float disY = y - mouseY;
        if (sqrt(sq(disX) + sq(disY)) < diameter/2 ) {
            return true;
        } else {
            return false;
        }
    }

    void update(int x, int y) {
        if ( overCircle(circleX, circleY, 50) ) {
            circleOver = true;
        }
        else {
            circleOver = false;
        }
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

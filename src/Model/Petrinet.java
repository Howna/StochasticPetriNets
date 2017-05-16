package Model;

/**
 *
 * @author Edwin
 */

import View.Gui;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Petrinet
extends PetrinetObject {

    private static final String nl = "\n";
    List<Place> places              = new ArrayList<Place>();
    List<Transition> transitions    = new ArrayList<Transition>();
    List<Arc> arcs                  = new ArrayList<Arc>();
    List<InhibitorArc> inhibitors   = new ArrayList<InhibitorArc>();
    
    public Petrinet(String name) {
        super(name);
    }

    public void add(PetrinetObject o) {
        if (o instanceof InhibitorArc) {
            inhibitors.add((InhibitorArc) o);
        } else if (o instanceof Arc) {
            arcs.add((Arc) o);
        } else if (o instanceof Place) {
            places.add((Place) o);
        } else if (o instanceof Transition) {
            transitions.add((Transition) o);
        }
    }
    
    public List<Transition> getTransitionsAbleToFire() {
        ArrayList<Transition> list = new ArrayList<Transition>();
        for (Transition t : transitions) {
            if (t.canFire()) {
                list.add(t);
            }
        }
        return list;
    }
    
    public Transition transition(String name,double rate) {
        Transition t = new Transition(name,rate);
        transitions.add(t);
        return t;
    }
    
    public Place place(String name) {
        Place p = new Place(name);
        places.add(p);
        return p;
    }
    
    public Place place(String name, int initial) {
        Place p = new Place(name, initial);
        places.add(p);
        return p;
    }
    
    public Arc arc(String name, Place p, Transition t) {
        Arc arc = new Arc(name, p, t);
        arcs.add(arc);
        return arc;
    }
    
    public Arc arc(String name, Transition t, Place p) {
        Arc arc = new Arc(name, t, p);
        arcs.add(arc);
        return arc;
    }
    
    public InhibitorArc inhibitor(String name, Place p, Transition t) {
        InhibitorArc i = new InhibitorArc(name, p, t);
        inhibitors.add(i);
        return i;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Petrinet ");
        sb.append(super.toString()).append(nl);
        sb.append("---Transitions---").append(nl);
        for (Transition t : transitions) {
            sb.append(t).append(nl);
        }
        sb.append("---Places---").append(nl);
        for (Place p : places) {
            sb.append(p).append(nl);
        }
        return sb.toString();
    }

    public List<Place> getPlaces() {
        return places;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }

    public List<Arc> getArcs() {
        return arcs;
    }

    public List<InhibitorArc> getInhibitors() {
        return inhibitors;
    }
     
    public static void createPetriNet(Petrinet pn){
        String xml = "<RedPetri>\n\t\t<lugares>\n";
        String nametransition;
        for (int i = 0; i < pn.places.size(); i++) {
            xml += "\t\t\t\t<lugar name=\""+pn.getPlaces().get(i).getName()+
                    "\" marcado=\""+(pn.getPlaces().get(i).getTokens()!=0 ? "si" : "no" )+
                    "\" num=\""+pn.getPlaces().get(i).getTokens()+"\">\n";
            xml += "\t\t\t\t\t\t<entradas>\n";
            for ( Arc ar : pn.getArcs() ) {
                if( ar.place.getName() == pn.getPlaces().get(i).getName() && ar.direction.name() == "TRANSITION_TO_PLACE" ){
                    nametransition = ar.transition.getName().substring(0, 2);
                    xml += "\t\t\t\t\t\t\t\t<t name=\""+nametransition+"\" num=\""+1+"\" />\n";                    
                } 
            }                    
            xml += "\t\t\t\t\t\t</entradas>\n";            
            xml += "\t\t\t\t\t\t<salidas>\n";
            for ( Arc ar : pn.getArcs() ) {
                if( ar.place.getName() == pn.getPlaces().get(i).getName() && ar.direction.name() == "PLACE_TO_TRANSITION" ){
                    nametransition = ar.transition.getName().substring(0, 2);
                    xml += "\t\t\t\t\t\t\t\t<t name=\""+nametransition+"\" num=\""+1+"\" />\n";                    
                }
            }                    
            xml += "\t\t\t\t\t\t</salidas>\n";
            xml += "\t\t\t\t</lugar>\n";            
        }	        
        xml += "\t\t</lugares>\n";
        xml += "</RedPetri>\n";               
        try {
            PrintStream ps = new PrintStream("PetriNet.xml");
            ps.print(xml);
            ps.close();            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
        }            
    }
    
}

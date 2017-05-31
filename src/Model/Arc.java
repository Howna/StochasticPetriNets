package Model;

/**
 * Created by kevincastro on 5/12/17.
 */

//Arc object
public class Arc
extends PetrinetObject {

    public Place place; //Place the arc is connected to/from
    public Transition transition; //Transition the arc is connected to/from
    public Direction direction;
    public int weight = 1; //How many tokenks each arc consumes

    //Direction of the arc. The arc goes from a transition to a place or the arc goes from a place to a trasition
    public enum Direction {
        
        PLACE_TO_TRANSITION {
            @Override
            public boolean canFire(Place p, int weight) {
                return p.hasAtLeastTokens(weight);
            }

            @Override
            public void fire(Place p, int weight) {
                p.removeTokens(weight);
            }

        },
        
        TRANSITION_TO_PLACE {
            @Override
            public boolean canFire(Place p, int weight) {
                return ! p.maxTokensReached(weight);
            }

            @Override
            public void fire(Place p, int weight) {
                p.addTokens(weight);
            }

        };

        public abstract boolean canFire(Place p, int weight);

        public abstract void fire(Place p, int weight);
    }

    //Class constructors

    private Arc(String name, Direction d, Place p, Transition t) {
        super(name);
        this.direction = d;
        this.place = p;
        this.transition = t;
    }

    protected Arc(String name, Place p, Transition t) {
        this(name, Direction.PLACE_TO_TRANSITION, p, t);
        p.connectedTo.add(t);
        t.connectedFrom.add(p);
        t.addIncoming(this);
    }

    protected Arc(String name, Transition t, Place p) {
        this(name, Direction.TRANSITION_TO_PLACE, p, t);
        t.connectedTo.add(p);
        p.connectedFrom.add(t);
        t.addOutgoing(this);
    }

    public boolean canFire() {
        return direction.canFire(place, weight);
    }
    
    public void fire() {
        this.direction.fire(place, this.weight);
    }

    //Set hoy many tokens each arc consumes
    public void setWeight(int weight) {
        this.weight = weight;
    }
    
    public int getWeight() {
        return weight;
    }
}

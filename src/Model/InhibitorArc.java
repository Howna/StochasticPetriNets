package Model;

/**
 * Created by kevincastro on 5/12/17.
 */

public class InhibitorArc
extends Arc {

    
    protected InhibitorArc(String name, Place p, Transition t) {
        super(name, p, t);
    }

    @Override
    public boolean canFire() {
        return (place.getTokens() < this.getWeight());
    }

    @Override
    public void fire() {
        // do nothing
    }

    
}

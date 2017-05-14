import java.util.ArrayList;
import java.util.List;

public class Transition
extends PetrinetObject{

    private List<Arc> incoming = new ArrayList<Arc>();
    private List<Arc> outgoing = new ArrayList<Arc>();
    private double rate;
    private double delayTime;
    public int lvl;
    public float inX;
    public float inY;
    public float outX;
    public float outY;
    public Place connectedTo;
    public Place connectedFrom;

    protected Transition(String name, double rate, int lvl) {
        super(name);
        this.rate = rate;
        this.delayTime = -rate*Math.log(Math.random());
        this.lvl = lvl;
    }
    
    public double getRate() {
        return rate;
    }
                
    public double getDelayTime(){
        return this.delayTime;
    }
    
    public void setDelayTime(double rate){
        this.delayTime = -rate*Math.log(Math.random());
    }
    
    public boolean canFire() {
        boolean canFire = true;

        canFire = ! this.isNotConnected();
        
        for (Arc arc : incoming) {
            canFire = canFire & arc.canFire();
        }
        
        for (Arc arc : outgoing) {
            canFire = canFire & arc.canFire();
        }
        return canFire;
    }

    public void fire() {
        for (Arc arc : incoming) {
            arc.fire();
        }
        
        for (Arc arc : outgoing) {
            arc.fire();
        }
    }

    public void addIncoming(Arc arc) {
        this.incoming.add(arc);
    }

    public void addOutgoing(Arc arc) {
        this.outgoing.add(arc);
    }

    public boolean isNotConnected() {
        return incoming.isEmpty() && outgoing.isEmpty();
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               (isNotConnected() ? " IS NOT CONNECTED" : "" ) +
               (canFire()? " READY TO FIRE" : "");
    }
    
}

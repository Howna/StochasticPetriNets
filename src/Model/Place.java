package Model;

import java.util.ArrayList;

public class Place
extends PetrinetObject {

    public static final int UNLIMITED = -1;
    
    private int tokens = 0;
    private int maxTokens = UNLIMITED;
    public int lvl;
    public float inX;
    public float inY;
    public float outX;
    public float outY;
    public ArrayList<Transition> connectedTo = new ArrayList<>();
    public ArrayList<Transition> connectedFrom = new ArrayList<>();


    protected Place(String name, int lvl) {
        super(name);
        this.lvl = lvl;
    }

    protected Place(String name, int initial, int lvl) {
        super(name);
        this.tokens = initial;
        this.lvl = lvl;
    }

    public boolean hasAtLeastTokens(int threshold) {
        return (tokens >= threshold);
    }


    public boolean maxTokensReached(int newTokens) {
        if (hasUnlimitedMaxTokens()) {
            return false;
        }
        
        return (tokens+newTokens > maxTokens);
    }

    private boolean hasUnlimitedMaxTokens() {
        return maxTokens == UNLIMITED;
    }

    
    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public void setMaxTokens(int max) {
        this.maxTokens = max;
    }

    public void addTokens(int weight) {
        this.tokens += weight;
    }

    public void removeTokens(int weight) {
        this.tokens -= weight;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               " Tokens=" + this.tokens +
               " max=" + (hasUnlimitedMaxTokens()? "unlimited" : this.maxTokens);
    }
}

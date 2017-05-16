package Model;

/**
 * 
 * @author Edwin
 */
 
public final class DeterministicTransition{
       
    private String name;
    private boolean active;
    private int num;

    public DeterministicTransition(String name,int n,boolean active){
        setName(name);
        setActive(active);
        setNum(n);
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
}

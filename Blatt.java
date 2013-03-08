import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Blaetter, die hinunterfallen, wenn in einen Baum geschossen wird.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Blatt extends Akteur {

    private int zustand = 0;        //0 = ganz
                                    //1 = zerschossen
    
    public Blatt() {
        setEntfernung(1.2f);
        
        Animation fallen = new Animation(19, true, false, true);
        fallen.generiereDateinamen("leaf", 1, 19, ".png");
        setAnimation(fallen);
    }
    
    /** Laesst Blaetter vom Baum fallen */
    public void bewegen() {
        setGlobalX(getGlobalX() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        setGlobalY(getGlobalY() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        
        if(getAnimation().istBeendet()) Welt.welt.removeObject(this);
    }
    
    /** Zerschiesst ein Blatt */
    public boolean abschiessen() {
        if(zustand == 1) return false;
        zustand = 1;
        
        Animation zerplatzen = new Animation(25, false, false, true, 25);
        zerplatzen.generiereDateinamen("leafhit", 1, 25, ".png");
        setAnimation(zerplatzen);
        
        return true;
    }
}

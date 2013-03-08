import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Hut der Vogelscheuche,
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Hut extends Vogelscheuche {

    private String basisName = "hat";
    private final int ANZAHLBILDER = 20;

    private int zaehler = 1;
    
    private int zustand = 0;
    
    public Hut() {
        gibtPunkte = 25;
    }
    
    /**
     * Laesst den Hut hinunterfallen, wenn er abgeschossen wurde.
     */
    public void bewegen() {
        if(zustand == 1)
            setGlobalY(getGlobalY() + 70 * Welt.sekundenSeitLetztemAct);
    }
        
    /**
     * Schiesst den Hut von der Vogelscheuche herunter.
     * 
     * @returns erfolg  Wurde der Hut erfolgreich hinuntergeschossen?
     */
    public boolean abschiessen() {
        if(zustand == 1) return false;
        zustand = 1;
        
        Animation herunterfallen = new Animation(20, true, false, true);
        herunterfallen.generiereDateinamen("hat", 1, 20, ".png");
        setAnimation(herunterfallen);
        
        return true;
    }
}
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Fahne, die das Flugzeug hinter sich herzieht.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Fahne extends Akteur {

    Flugzeug angehaengtAn;
    
    int zustand;        //Zustaende
                        //0 = angehaengt
                        //1 = abgeloest
    
    public Fahne(Flugzeug ah) {
        angehaengtAn = ah;
        gibtPunkte = -25;
    }
    
    /** Zieht den Fahnen hinter einem Flugzeug her */
    public void bewegen() {
        if(zustand == 0) {
            setGlobalX(angehaengtAn.getGlobalX() + 30);
            setGlobalY(angehaengtAn.getGlobalY());
        }else if(zustand == 1) {
            setGlobalX(getGlobalX() + 100 * Welt.sekundenSeitLetztemAct);
            setGlobalY(getGlobalY() + 30 * Welt.sekundenSeitLetztemAct);
        }
    }
    
    /**
     * Loest die Fahne vom Flugzeug.
     * 
     * @returns erfolg  Fahne erfolgreich losgeloest?
     */
    public boolean abschiessen() {
        if(zustand == 1) return false;
        zustand = 1;
        
        return true;
    }
}

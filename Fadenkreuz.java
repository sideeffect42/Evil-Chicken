import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Malt ein Fadenkreuz dahin, wo die Maus gerade ist.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Fadenkreuz extends Actor {

    /**
     * Passt die Position des Fadenkreuzes der Maus-Position entsprechend an.
     */
    public void act() {
        setLocation(Maus.getMausX(), Maus.getMausY());
    }    
}

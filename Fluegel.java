import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Die Fluegel der Windmuehle
 * Die Huehner werden den internationalen Tierschutzbestimmungen entsprechend gehalten.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Fluegel extends Windmuehle {

    boolean abgeschossen = false;
    
    /**
     * Erstellt einen neuen Fluegel
     * 
     * @param pStellung Frame-ID, in welchem sich der Fluegel befindet
     */
    public Fluegel(int pStellung){
        gibtPunkte = 25;
        
        Animation drehen = new Animation(37, pStellung, true, false, true);
        drehen.generiereDateinamen("wing", 1, 37, ".png");
        setAnimation(drehen);
    }
    
    /**
     * Laesst den Fluegel animiert herunterfallen, wenn er weggeschossen wurde.
     */
    public void bewegen(){
        if(abgeschossen) {
            setGlobalY(getGlobalY() + 200 * Welt.sekundenSeitLetztemAct);
            
            if(getGlobalY() > Welt.volleHoehe) {
                Welt.welt.removeObject(this);
            }
        }
    }
    
    /**
     * Loest den Fluegel von der Halterung an der Windmuehle.
     * 
     * @returns erfolg  Fluegel erfolgreich von der Muehle geschossen?
     */
    public boolean abschiessen(){
        if(abgeschossen) return false;
        abgeschossen = true;
        
        return true;
    }
}
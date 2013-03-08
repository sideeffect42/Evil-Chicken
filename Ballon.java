import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ein Ballon funktioniert beinahe gleich wie ein Moorhuhn.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Ballon extends Akteur {

    private int finaleHoehe;
    private int zustand;        //0 = aufsteigend
                                //1 = geradeaus fliegend
                                //2 = absinkend
                                //3 = abgeschossen
                                
    private boolean richtung; // false: links -- true: rechts
    
    public Ballon(boolean pRichtung) {
        richtung = pRichtung;
        gibtPunkte = -25;
        setEntfernung(0.5f);
        
        Animation drehen = new Animation(21, true, true, true, 120);
        drehen.generiereDateinamen("balloon", 1, 21, ".png");
        setAnimation(drehen);
    }
    
    /**
     * Setzt die Hoehe, die dieser Ballon erreichen kann.
     * 
     * @params finaleHoehe  Hoehe (in Pixeln) vom unteren Rand des Canvas.
     */
    public void setFinaleHoehe(int neueFinaleHoehe) { finaleHoehe = neueFinaleHoehe; }
    
    /** Beschreibt die Bewegung des Ballons */
    public void bewegen() {
        if(richtung) {
            setGlobalX(getGlobalX() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        }else{
            setGlobalX(getGlobalX() - getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        }
        
        switch(zustand){
            case 0:
                setGlobalY(getGlobalY() - getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                
                if(getGlobalY() <= Welt.canvasHoehe - finaleHoehe) {
                    zustand = 1;
                }
                break;
            case 1:
                if(alter > 40000){
                    zustand = 2;
                }
                break;
            case 2:
                setGlobalY(getGlobalY() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                if(getGlobalY() > Welt.volleHoehe) {
                    Welt.welt.removeObject(this);
                }
                break;         
            case 3:
                setGlobalY(getGlobalY() + 2 * getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                if(getCanvasY() > Welt.canvasHoehe) {
                    Welt.welt.removeObject(this);
                }
                break;
        }
    }
    
    /** 
     * Beschreibt was passiert, wenn der Ballon von einer Schrottkugel getroffen wird 
     * 
     * @returns erfolg  Getroffen? :D
     */
    public boolean abschiessen() {
        if(zustand == 3) return false;        //Ein Loch reicht ;)
        zustand = 3;
        
        Animation fallen = new Animation(20, false, false, true);
        fallen.generiereDateinamen("balloon2", 1, 20, ".png");
        setAnimation(fallen);
        
        Greenfoot.playSound("fall22.mp3");
        
        return true;
    }
}
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Eine Patronenhuelse (zaehlt als Schuss)
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Huelse extends Akteur {

    public int zustand = 0;     //Zustaende:
                                //0 = normal
                                //1 = wurde verschossen, bewegt sich vom Bildschirm

    /** Bewegt die Huelse vom Canvas. */
    @Override
    public void bewegen(){
        if(zustand == 1){
            setGlobalX(getGlobalX() + 400 * Welt.sekundenSeitLetztemAct);
            setGlobalY(getGlobalY() - 50 * Welt.sekundenSeitLetztemAct);
            if(getCanvasX() > Welt.canvasBreite){
                Welt.welt.removeObject(this);
            }
        }
    }
}

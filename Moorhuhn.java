import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Moorhuehner sind die hauptsaechlichen Gegner im gleichnahmigen Spiel
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Moorhuhn extends Akteur {

    /**
     * was für eine Art Moorhuhn ist das
     * "","1" oder "2"
     */
    String typ;
    /**
     * in welche Richtung fliegt das Huhn?
     */
    char richtung;
       
    private int zustand; /* 0: aufsteigend
                            1: fliegend
                            2: absteigend
                            3: tot
                          */
                         
                         
    /**
     * bis zu welchem y-Wert soll das Moorhuhn nach oben fliegen?
     */
    private int finaleHoehe;
    
    public Moorhuhn(String pTyp, char pRichtung){
        typ = pTyp;
        richtung = pRichtung;
        
        Animation fliegen = new Animation(19, true, false, true);
        fliegen.generiereDateinamen("chick" + richtung + typ, 1, 19, ".png");
        setAnimation(fliegen);
    }
    
    /**
     * Setzt die Hoehe, auf die das Moorhuhn maximal fliegen kann.
     * 
     * @params hoehe    Hoehe in Pixel, gemessen vom unteren Bildschirmrand.
     */
    public void setFinaleHoehe(int neueFinaleHoehe) {
        finaleHoehe = neueFinaleHoehe;
    }
    
    /**
     * Laesst das Moorhuhn fliegen/sich bewegen.
     */
    public void bewegen() {
        if(richtung == 'r') {
            setGlobalX(getGlobalX() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        }else{
            setGlobalX(getGlobalX() - getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        }
        
        switch(zustand) {
            case 0:
                setGlobalY(getGlobalY() - 0.75f * getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                
                if(getGlobalY() <= Welt.canvasHoehe - finaleHoehe) {
                    zustand = 1;        //Falls wir die finale Flughoehe erreicht haben, servieren wir das Mittagessen! (no joke!)
                }
                break;
            case 1:
                if(alter > 8000 && Greenfoot.getRandomNumber(1000) > 9997) {
                    zustand = 2;        //Beginne den Landeanflug
                }
                break;
            case 2:
              //Wir beginnen nun den Landeanflug. Die Aussentemperatur betraegt 19°C.
                setGlobalY(getGlobalY() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                if(getGlobalY() > Welt.volleHoehe) {
                    Welt.welt.removeObject(this);
                }
                break;   
            case 3:
              //Baeaem! Headshot, voll in den Flügel!
                setGlobalY(getGlobalY() + 2f * getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                if(getCanvasY() > Welt.canvasHoehe) {
                    Welt.welt.removeObject(this);
                }
                break;
        }
    }
    
    /**
     * Toetet das Huhn.
     * 
     * @returns abgeschossen    Abschiessen erfolgreich?
     */
    @Override
    public boolean abschiessen() {
        if(zustand == 3) return false;    //Nicht nochmal abschiessen, wenn das Huhn schon tot ist.
        zustand = 3;
        
        Animation fallen = new Animation(19, false, false, true);
        fallen.generiereDateinamen("chickd" + typ, 1, 19, ".png");
        setAnimation(fallen);
        
        return true;
    }
}

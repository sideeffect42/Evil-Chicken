import greenfoot.*;  //(World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.HashMap;

/**
 * Klasse fuer die Flugzeuge.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */

public class Flugzeug extends Akteur {

    private int zustand = 0;                                  //Definiert des Flugzeug' Zustand
                                                                //0 = startend
                                                                //1 = fliegt geradeaus
                                                                //2 = sinkend
                                                                //3 = fallend (wurde abgeschossen)
   
    
    private HashMap<String, GreenfootSound> musik;
    
    public Flugzeug() {
        setEntfernung(0.45f);
        gibtPunkte = -25;
        
      //Musik einlesen
        musik = new HashMap<String, GreenfootSound>();
        musik.put("normal", new GreenfootSound("plane22.mp3"));
        musik.put("abstuerzend", new GreenfootSound("plane222.mp3"));
        musik.put("explodiert", new GreenfootSound("explo22.mp3"));
        
        Animation fliegen = new Animation(20, true, false, true);
        fliegen.generiereDateinamen("plane", 1, 20, ".png");
        setAnimation(fliegen);
        
        musik.get("normal").playLoop();
    }

    /**
     * Simuliert den Motor und die Propeller des Flugzeugs.
     */
    public void bewegen() {
        
      //Alter erhoehen
        alter++;

      //Hole x und y Position.
        float x = getCanvasX();
        float y = getCanvasY();
        
      //Geradeaus fliegen
        setGlobalX(getGlobalX() - 2.5f * getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
        
      
      
      switch(zustand) {
            case 0: //startend
            
              //Hoehe veraendern
                setGlobalY(getGlobalY() - 1.5f * getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                    
                if(y <= Welt.canvasHoehe - getFinaleHoehe()) zustand = 1; //Falls wir die Flughoehe erreichen, servieren wir das Mittagessen.
                
                break;

            case 1: //fliegend
                if(alter > 50000 && Greenfoot.getRandomNumber(100) > 95) zustand = 2;
                
                break;
                
            case 2: //sinkend
                if(y > Welt.canvasHoehe) {     //Entfernen, wenn unten angekommen
                    musik.get("normal").stop();
                    Welt.welt.removeObject(this);
                    return;
                }

              //Hoehe veraendern
                setGlobalY(getGlobalY() + getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                
                break;
                
            case 3: //fallend
                if(y > Welt.canvasHoehe) {     //Entfernen, wenn vollstaendig abgestuerzt
                    musik.get("abstuerzend").stop();
                    musik.get("explodiert").play();
                    Welt.welt.removeObject(this);
                    return;
                }
            
              //Abstuerzen
                setGlobalY(getGlobalY() + 2f * getEntfernung() * Optionen.bewegungsGeschwindigkeit * Welt.sekundenSeitLetztemAct);
                    
            break;
        }
        
    }
    
    /**
     * Schiesst das Flugzeug ab.
     * 
     * @returns erfolg  Wurde das Flugzeug erfolgreich abgeschossen?
     */
    @Override
    public boolean abschiessen() {
        if(zustand == 3) return false;                          //Flugzeug nicht abstuerzen lassen, wenn es schon abstuerzt.
        zustand = 3;
        
        musik.get("normal").stop();
        musik.get("abstuerzend").playLoop();
        
        return true;
    }
    
    /** Wird aufgerufen, wenn die Simulation pausiert wurde und stoppt die Musik */
    public void stopped() {
        if(zustand < 3) musik.get("normal").stop();
        if(zustand == 3) musik.get("abstuerzend").stop();
    }
    
    /** Wird aufgerufen, wenn die Simulation wieder gestartet wurde und startet die Musik */
    public void started() {
        if(zustand < 3) musik.get("normal").playLoop();
        if(zustand == 3) musik.get("abstuerzend").playLoop();
    }
}

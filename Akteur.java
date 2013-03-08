import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;

/**
 * Erweiterung des Actors. Kann Parallax-Scrolling, Punkte, Transparenz und Animationen.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public abstract class Akteur extends Actor {

    /** Koordinaten im globalen Koordinatensystem. */
    private float globalX;
    private float globalY;
    
    /**
     * Entfernung dieses Objekts. (Von hinten. Groesser = naeher bei der Kamera)
     * (Bestimmt die Stärke des Parallax-Scrollings)
     */
    private float entfernung = 0;
    
    /** Punkte die dieses Objekt gibt, wenn es abgeschossen wird. */
    public int gibtPunkte;
    
    /** Momentan angewendete/abgespielte Animation. */
    private Animation momentaneAnimation;
  
    /** Alter (in Millisekunden) des Akteurs. */
    protected int alter;
    
    /** Hoehe, die dieses Objekt hoechstens erreichen wird. */
    private int finaleHoehe;
    
    @Override
    public final void act(){
        if(momentaneAnimation != null){
          //Tick Animation
            momentaneAnimation.tick();
          
          //Hole Dateinamen fuer aktuellen Frame und setze Bild
            setImage(momentaneAnimation.getAktuellenFrame());
        }
        
        alter += Welt.millisekundenSeitLetztemAct;
    }    
    
    /**
     * Gibt globale X-Koordinate zurueck.
     * 
     * @returns globalX Globale X-Koordinate
     * @see setGlobalX(float)
     * @see getGlobalY()
     */
    public float getGlobalX() { return globalX; }
    
    /**
     * Gibt globale Y-Koordinate zurueck.
     * 
     * @returns globalY Globale Y-Koordinate
     * @see setGlobalY(float)
     * @see getGlobalX()
     */
    public float getGlobalY() { return globalY; }
    
    /**
     * Setzt neue globale X-Koordinate.
     * 
     * @params neuesX    Neue globale X-Koordinate.
     * @see getGlobalX()
     * @see setGlobalY()
     */
    public void setGlobalX(float neuesX) { globalX = neuesX; }
    
    /**
     * Setzt neue globale Y-Koordinate.
     * 
     * @params neuesY    Neue globale Y-Koordinate.
     * @see getGlobalX()
     * @see setGlobalX()
     */
    public void setGlobalY(float neuesY) { globalY = neuesY; }
    
    /**
     * Setzt Entfernung dieses Objekts.
     * 
     * @params entfernung    Entfernung.
     * @see getEntfernung()
     */
    public void setEntfernung(float neueEntfernung) { entfernung = neueEntfernung; }
    
    /**
     * Gibt Entfernung dieses Objekts zurueck.
     * 
     * @returns entfernung  Entfernung.
     * @see setEntfernung(float)
     */
    public float getEntfernung() { return entfernung; }
    
    
    
    /**
     * Vorlage fuer Methode, die regelt, wie sich das Objekt bewegen soll.
     * (Es sollen nur die globalX und globalY Variablen veraendert werden. Die Objekte werden automatisch neu (richtig) gezeichnet)
     */
    public void bewegen() {}
    
    /**
     * Vorlage fuer Methode, die regelt, was geschehen soll, wenn das Objekt abgeschossen wurde.
     * Gibt true zurueck, wenn das Abschiessen erfolgreich war.
     * False, wenn nicht. Z.B. Wenn das Objekt schon abgeschossen wurde.
     */
    public boolean abschiessen() { return false; }
    
    /**
     * Vorlage fuer Methode, die aufgerufen wird, wenn der User den Run-Button klickt.
     */
    public void started() {}
    
    /**
     * Vorlage fuer Methode, die aufgerufen wird, wenn der User den Pause-Button klickt.
     */
    public void stopped() {}

    /**
     * Setzt das GreenfootImage an die richtige Stelle im Canvas.
     */
    public void positionAktualisieren(){
        setLocation((int) getCanvasX(), (int) getCanvasY());
    }
    
    /**
     * Berechnet die X-Koordinate dieses Objekts im Canvas.
     * 
     * @returns x   X-Koordinate
     * @see getCanvasY()
     */
    public float getCanvasX(){
        if(entfernung == 0){
            return globalX + getImage().getWidth() / 2;
        }else{
            return entfernung * (globalX - Welt.kameraX) + getImage().getWidth() / 2;
        }
    }
    
    /**
     * Berechnet die Y-Koordinate des Objekts im Canvas.
     * 
     * @returns y   Y-Koordinate
     * @see getCanvasX()
     */
    public float getCanvasY(){
        return globalY + getImage().getHeight() / 2;
    }
    
    /**
     * Ueberprueft ob das Bild dieses Objekts an der Position der Maus transparent ist.
     * 
     * @returns transparent Transparent - true/false.
     * @see istTransparentBei(int, int)
     */
    public boolean istTransparentBeiMaus() {
        // Mauskoordinaten zu Koordinaten im Bild umrechnen
        int x = (int) (Maus.getMausX() - getCanvasX() + getImage().getWidth() / 2);
        int y = (int) (Maus.getMausY() - getCanvasY() + getImage().getHeight() / 2);
        
        return istTransparentBei(x, y);
    }
    
    /**
     * Ueberprueft ob das Bild dieses Objekt an der uebermittelten Koordinate transparent ist
     * 
     * @params x X-Koordinate
     * @params y Y-Koordinate
     * 
     * @returns transparent Transparent true/false
     * @see istTransparentBeiMaus()
     */
    public boolean istTransparentBei(int x, int y) {
        if(x < 0) { return true; }
        if(x >= getImage().getWidth()) { return true; }
        if(y < 0) { return true; }
        if(y >= getImage().getHeight()) { return true; }
        
        Color hintergrundFarbeBei = this.getImage().getColorAt(x, y);
        return hintergrundFarbeBei.getAlpha() == 0;
    }
    
    /**
     * Liefert die momentan abgespielte Animation.
     * Falls keine Animation vorhanden ist, wird eine Nullvariable zurueckgegeben
     * 
     * @returns animation   Animation
     * @see setAnimation()
     */
    public Animation getAnimation() { return momentaneAnimation; }
    
    /**
     * (Er)Setzt eine neue Animation fuer dieses Objekt und startet diese.
     * 
     * @params animation Animation, die neu angewendet werden soll.
     * @see getAnimation()
     */
    public void setAnimation(Animation a) {
        if(momentaneAnimation != null) momentaneAnimation.stop();
        momentaneAnimation = a;
        a.start();  
    }
    
    /**
     * Gibt die momentan gesetzte finale Hoehe zurueck.
     * 
     * @returns finaleHoehe Hoehe, auf die dieses Objekt maximal fliegt.
     * @see setFinaleHoehe()
     */
    public int getFinaleHoehe() { return finaleHoehe; }
    
    /**
     * Setzt eine neue finale Hoehe.
     * 
     * @params finaleHoehe   Hoehe, auf die dieses Objekt maximal fliegen koennen soll.
     * @see getFinaleHoehe()
     */
    public void setFinaleHoehe(int neueFinaleHoehe) { finaleHoehe = neueFinaleHoehe; }
}

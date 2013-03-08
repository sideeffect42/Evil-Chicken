import greenfoot.Greenfoot;

/**
 * Speichert die Mausposition, falls die Maus das Bild verlaesst oder sonstige boese Dinge passieren.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Maus {

    /** Man soll kein Objekt einer Maus erstellen */
    private Maus() {}
    
    /** Wo sich die Maus als letztes befunden hat. */
    private static int letztePositionX = Welt.canvasBreite / 2;
    private static int letztePositionY = Welt.canvasHoehe / 2;
    
    
    /**
     * gibt die momentane X-Position der Maus zurück
     * falls nicht bekannt, die letzte bekannte
     */
    public static int getMausX(){
        try{
            letztePositionX = Greenfoot.getMouseInfo().getX();
        }catch(NullPointerException e) {
            // Wir nutzen einfach die letzte bekannte Mausposition
        }
        
        return letztePositionX;
    }
    
    /**
     * gibt die momentane Y-Position der Maus zurück
     * falls nicht bekannt, die letzte bekannte
     */
    public static int getMausY(){
        try{
            letztePositionY = Greenfoot.getMouseInfo().getY();
        }catch(NullPointerException e) {
            // Wir nutzen einfach die letzte bekannte Mausposition
        }
        
        return letztePositionY;
    }
    
    /**
     * Gibt die aktuell gedrueckte Maus-Taste zurueck.
     * 
     * @returns buttonNummer    1=links, 2=mitte, 3=rechts
     */
    public static int getButton() {
        try{
            return Greenfoot.getMouseInfo().getButton();
        }catch(NullPointerException e) {
            return 0;
        }
    }
}

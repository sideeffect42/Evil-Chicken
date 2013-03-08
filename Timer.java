import greenfoot.*;

/**
 * Stoppt die Zeit.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Timer {
    private static int rest = Optionen.spielZeit;
    private static long letzterTick;
    
    /**
     * Aktualisiert den Timer.
     */
    static void tick() {
      //Intervall bestimmen
        int intervall = getFireIntervall();
        
      //Ton abspielen wenn in den letzten 10 Sekunden
        if( (int) rest/1000 > (int) (rest-intervall)/1000 && rest - intervall < 10000) Greenfoot.playSound("timeup22.mp3");
      
      //Restzeit anpassen
        rest -= intervall;
      
      //Spiel beenden, wenn Zeit abgelaufen ist.
        if(rest <= 0) {
            Greenfoot.playSound("over22.mp3");
            Welt.welt.spielEnde();
        }
    }
    
    /**
     * Setzt den Timer auf die Startzeit zurueck.
     */
    static void reset() {
        rest = Optionen.spielZeit;
    }
    
    /**
     * Gibt die Restzeit, die der Spieler noch hat, in Millisekunden zurueck.
     * 
     * @returns rest    Restzeit in Millisekunden
     */
    static int getRestzeit() {
        return rest;
    }
    
    /**
     * Errechnet die Zeitdifferenz seit dem letzten Tick.
     * 
     * @returns intervall   Zeitdifferenz in Millisekunden.
     * @see tick()
     */
    static int getFireIntervall() {
      //Falls dies der erste Aufruf ist, einfach die jetztige Zeit schreiben.
        if(letzterTick == 0) {
            letzterTick = System.currentTimeMillis(); 
            return 0;
        }
        
      //Intervall berechnen
        long aktuelleZeit = System.currentTimeMillis();
        int intervall = (int) aktuelleZeit - (int) letzterTick;        
       
        letzterTick = aktuelleZeit;
        return intervall;
    }
}

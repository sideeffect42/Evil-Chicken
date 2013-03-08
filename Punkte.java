/**
 * Zaehlt die Punkte.
 * 
 * @author Takashi Yoshi & Philipp Riegger 
 * @version 1.0
 */
public class Punkte {

    public static int punktestand = 0;

    /**
     * Erhoeht den Punktestand um einen bestimmten Wert.
     * 
     * @params punkte   Punkte, um die erhoeht werden soll.
     */
    static void punktestandErhoehenUm(int punkte) {
        if(punktestand + punkte < 0) {
            punktestand = 0;
            return;
        }
        
        punktestand += punkte;
        Welt.welt.addObject(new PunktAenderung(punkte), Maus.getMausX(), Maus.getMausY());
    }
    
    /**
     * Gibt den aktuellen Punktestand zurueck.
     * 
     * @returns punktestand Aktueller Punktestand des Spielers.
     */
    static int getPunktestand() {
        return punktestand;
    }
    
    /**
     * Setzt den Punktezaehler auf 0 zurueck.
     */
    static void reset() {
        punktestand = 0;
    }
}

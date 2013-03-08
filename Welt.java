/**
 * Statische Dinge, die wir ueber unsere Welt wissen sollten.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Welt {

    /** Breite des Spielfensters */
    public static int canvasBreite;
    /** Hoehe des Spielfensters */
    public static int canvasHoehe;
    
    /** Gesamte Breite der Spielwelt. */
    public static int volleBreite;
    /** Gesamte Hoehe der Spielwelt. */
    public static int volleHoehe;
    
    /** Referenz zur Welt */
    public static SpielWelt welt;
    
    /** Millisekunden, die zwischen dem vorletzten und dem letzten Act-Aufruf vergangen sind. */
    public static int millisekundenSeitLetztemAct;
    /** Sekunden, die zwischen dem vorletzten und dem letzten Act-Aufruf vergangen sind. */
    public static float sekundenSeitLetztemAct;
    
    /** Aktuelle X-Position der Kamera */
    public static float kameraX;
}

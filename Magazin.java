import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Das Magazin verwaltet die Schuesse sowie die Erstellung und das Abfeuern der Huelsen
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Magazin {
 
    /** Wie viele Schuesse sind momentan im Magazin */
    private static int anzahlSchuesse = 0;
    
    private static Huelse[] huelsen = new Huelse[Optionen.maxSchuesse];
    
    /**
     * Es wird ein Schuss abgefeuert.
     * 
     * @returns erfolg  War ueberhaupt noch ein Schuss da?
     */
    static boolean schiessen() {
        if(anzahlSchuesse > 0) {
          //Schiessen!
            anzahlSchuesse--;
            Greenfoot.playSound("shoot22.mp3");
            
          //Huelse vom Bildschirm bewegen
            Animation schiessen = new Animation(20, false, false, true, 40);
            schiessen.generiereDateinamen("huls", 1, 20, ".png");
            huelsen[anzahlSchuesse].setAnimation(schiessen);
            huelsen[anzahlSchuesse].zustand = 1;
            
            trefferBerechnen();
            
            return true;
        }else{
          //Magazin ist leer
            Greenfoot.playSound("empty22.mp3");  
          
            return false;
        }
    }
    
    /**
     * Falls neu geladen werden kann, wird neu geladen.
     */
    static void neuLaden() {
        if(anzahlSchuesse == 0 || Optionen.erlaubeNeuLadenWennMagazinNichtLeer) {
            for(int i = anzahlSchuesse; i < Optionen.maxSchuesse; i++) {
                huelsen[i] = new Huelse();
                Welt.welt.addObject(huelsen[i], Welt.canvasBreite - (i * 35) - 60, 385);
            }
            
            anzahlSchuesse = Optionen.maxSchuesse;
        }
    }
    
    /**
     * Berechnet, welches Objekt vom Schuss getroffen wurde und schiesst diesen endgueltig ab.
     */
    static void trefferBerechnen(){
        List objekte = Welt.welt.getObjectsAt(Maus.getMausX(), Maus.getMausY(), Akteur.class);

        Akteur amNaechsten = null;
        for(Object obj : objekte) {
            Akteur akteur = (Akteur) obj;

            if(!akteur.istTransparentBeiMaus()){
                if(amNaechsten == null || akteur.getEntfernung() > amNaechsten.getEntfernung()){ // Wir suchen den, der am naechsten zur Kamera ist
                    amNaechsten = akteur;
                }
            }

        }

        if(amNaechsten != null) {                                                   //Wir haben jemanden, den's erwischt hat.
            if(amNaechsten.abschiessen() && amNaechsten.gibtPunkte != 0)
                Punkte.punktestandErhoehenUm(amNaechsten.gibtPunkte);
        }
    }
}

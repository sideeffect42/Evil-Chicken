import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JPanel;
import greenfoot.core.WorldHandler;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

/**
 * Die Welt, in der sich das Spiel abspielt
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class SpielWelt extends World {

    private long letzterTimeStamp;

    private CopyOnWriteArrayList<Akteur> sichtbareObjekte;
    private CopyOnWriteArrayList<Akteur> versteckteObjekte;
    
    private Class[] paintOrder = {Fadenkreuz.class,
                                  Anzeige.class, Huelse.class, PunktAenderung.class,
                                  Blatt.class, Wegweiser.class, Loch.class,
                                  Ebene5.class, MoorhuhnRiesig.class, 
                                  Ebene4.class, Moorhuhn1.class, 
                                  Ebene3.class, Moorhuhn2.class, Ballon.class, Flugzeug.class, Fluegel.class, Windmuehle.class, Vogelscheuche.class, Hut.class,
                                  Ebene2.class, Moorhuhn3.class, 
                                  Ebene1.class, 
                                  Ebene0.class
                                 };

    private Anzeige punktAnzeige;
    private Anzeige zeitAnzeige;
    
  //Cheat-Variablen
    private boolean punkteCheatBenutzt = false;
    private boolean maschinenGewehrAktiviert = false;

    
    /** Vogelgezwitscher */
    public GreenfootSound hintergrundMusik = new GreenfootSound("bird22.mp3");

    public SpielWelt() {       
      //Welt erstellen
        super(640, 480, 1, false); 
        
      //Eigenschaften speichern
        Welt.canvasBreite = 640;
        Welt.canvasHoehe = 480;
        Welt.volleBreite = 3840;
        Welt.volleHoehe = 480;
        Welt.welt = this;

      //Listen erstellen
        sichtbareObjekte = new CopyOnWriteArrayList<Akteur>();
        versteckteObjekte = new CopyOnWriteArrayList<Akteur>();

      //Punktestand und Timer zuruecksetzen.
        Punkte.reset();
        Timer.reset();
        
      //Magazin fuellen
        Magazin.neuLaden();

      //Setzt die korrekte z-Position für die einzelnen Klassen.
        setPaintOrder(paintOrder);

      //Mauszeiger erstellen
        addObject(new Fadenkreuz(), 320, 240);

      //Punkt- und Zeit-Anzeigen erstellen
        punktAnzeige = new Anzeige('r');
        punktAnzeige.schreibe(".");
        zeitAnzeige = new Anzeige('l');
        zeitAnzeige.schreibe(".");

        addObject(punktAnzeige, Welt.canvasBreite - 70, 20);
        addObject(zeitAnzeige, 70, 20);
        
        anzeigenAktualisieren();

      //Wichtigste Objekte hinzufuegen
        Generator.basisSetHinzufuegen();

      //Uhr starten
        letzterTimeStamp = System.currentTimeMillis();
      
      //Setze alle Objekte an ihren Platz und platziere die Kamera beim Wegweiser.
        Welt.kameraX = 1800;
        act();
        
      //Startsound abspielen
        Greenfoot.playSound("ready22.mp3");
        
      //Mauszeiger verstecken
        versteckeMaus();
    }
    
    /**
     * @overrides addObject der Mutterklasse World.
     * 
     * Fuegt ein Objekt der Welt hinzu.
     * Wenn das Objekt eine (Sub)Klasse von ActorHolder ist, 
     * sind die x und y Koordinaten berechnet fuer die 
     * gesamte Groesse der Welt.
     * 
     * @param objekt    Welches Objekt soll es denn sein?
     * @param x         x Koordinate, die das Objekt haben soll.
     * @param y         x Koordinate, die das Objekt haben soll.
     * 
     * @see addObject(Actor, float, float)
     * @see removeObject(Actor)
     */
    @Override
    public void addObject(Actor objekt, int x, int y)
    {
        if(objekt instanceof Akteur) {
            if(x >= Welt.volleBreite)
                x = Welt.volleBreite - 1;
            else if(x < 0)
                x = 0;
                
            if(y >= Welt.canvasHoehe)
                y = Welt.canvasHoehe - 1;
            else if(y < 0)
                y = 0;
                
            Akteur sa = (Akteur) objekt;
            
          //globalWerte setzen, falls nicht schon geschehen
            if(sa.getGlobalX() == 0) sa.setGlobalX(x);
            if(sa.getGlobalY() == 0) sa.setGlobalY(y);
            
            super.addObject(sa, (int) (x - (Welt.kameraX - (Welt.canvasBreite / 2))), y);
            sichtbareObjekte.add(sa);
        }else
            super.addObject(objekt, x, y);
    }
    
    /**
     * Erlaubt das hinzufuegen von Objekten mit float-Koordinaten.
     * 
     * @see addObject(Actor, int, int)
     * @see removeObject(Actor)
     */
    public void addObject(Actor objekt, float x, float y) {
        addObject(objekt, (int) x, (int) y);
        
        if(objekt instanceof Akteur) {
            Akteur a = (Akteur) objekt;
            if(x != 0) a.setGlobalX(x);
            if(y != 0) a.setGlobalY(y);
        }

    }
    
    /**
     * @overrides removeObjekt der Mutterklasse World.
     * 
     * Entfernt ein Objekt von der Welt.
     * @param objekt        Das Objekt, dass entfernt werden soll.
     * @see addObject(Actor, int, int)
     * @see addObject(Actor, float, float)
     */
    @Override
    public void removeObject(Actor objekt) {
        if(versteckteObjekte.contains(objekt))
            versteckteObjekte.remove(objekt);
        else{
            super.removeObject(objekt);
            sichtbareObjekte.remove(objekt);
        }
    }
    

    public void act() {
      //Vergangene Zeit berechnen (seit letztem Act)
        Welt.millisekundenSeitLetztemAct = (int) (System.currentTimeMillis() - letzterTimeStamp);
        Welt.sekundenSeitLetztemAct = Welt.millisekundenSeitLetztemAct / 1000f;
        letzterTimeStamp = System.currentTimeMillis();

      //Periodische Methoden aufrufen
        verarbeiteInput();
        erzeugeObjekte();
        bewegeObjekte();
        anzeigenAktualisieren();
        cheatsUeberpruefen();
    }

    /**
     * Schiesst/Laedt neu, wenn Maus geklickt wurde oder verschiebt die Kamera, wenn sich die Maus am Rand des Canvas befindet.
     */
    public void verarbeiteInput() {
      // auf die Maus hören
        if(Greenfoot.mousePressed(null)) {
            if(Maus.getButton() == 1)  // linksklick: schießen
                Magazin.schiessen();
                
            else if(Maus.getButton() == 3) // rechtsklick: neu laden
                Magazin.neuLaden();
            
        }

      // befindet sich die Maus am Rand?
        if(Maus.getMausX() < Optionen.randBreite) {
            Welt.kameraX -= Welt.sekundenSeitLetztemAct * Optionen.scrollPixelProSekunde;
            if(Welt.kameraX < 0) Welt.kameraX = 0;
        }
        if(Maus.getMausX() > Welt.canvasBreite - Optionen.randBreite) {
            Welt.kameraX += Welt.sekundenSeitLetztemAct * Optionen.scrollPixelProSekunde;
            if(Welt.kameraX > Welt.volleBreite - Welt.canvasBreite) Welt.kameraX = Welt.volleBreite - Welt.canvasBreite;
        }
    }

    /**
     * Bewegt alle Objekte (versteckt, sichtbar) und sorgt dafür, dass Objekte versteckt werden, wenn sie nicht mehr auf dem Bildschirm sind oder wieder eingesetzt werden,
     * wenn sie wieder auf dem Bildschirm sind.
     */
    public void bewegeObjekte(){
        for(Akteur akteur : versteckteObjekte) {
            
            akteur.bewegen();

          //Ueberpruefen, ob sich dieses Objekt wieder im sichtbaren Bereich befindet.
            if(akteur.getCanvasX() + akteur.getImage().getWidth() / 2 > 0  && 
               akteur.getCanvasX() - akteur.getImage().getWidth() / 2 < Welt.canvasBreite) {
                addObject(akteur, (int) akteur.getCanvasX(),(int) akteur.getCanvasX()); // Akteur wieder in die Welt packen
                versteckteObjekte.remove(akteur);
                sichtbareObjekte.add(akteur);
                continue;
            }

        }

        for(Akteur akteur : sichtbareObjekte) {

            akteur.bewegen();
            akteur.positionAktualisieren();

          //Ueberpruefen, ob sich dieses Objekt nicht mehr im sichtbaren Bereich befinde.
            try{
                if(akteur.getX() + akteur.getImage().getWidth() / 2 < 0 || 
                   akteur.getX() - akteur.getImage().getWidth() / 2 > Welt.canvasBreite) {
                  //Objekt von der Welt entfernen.
                    removeObject(akteur);
                    sichtbareObjekte.remove(akteur);
                    versteckteObjekte.add(akteur);
                    continue;
                }
            }catch(IllegalStateException e) {
              //Dieses Objekt befindet sich nicht in der Welt, obowhl es eigentlich noch da sein muesste.
              //Naja egal, schmeissen wir ihn raus.
                removeObject(akteur);
                sichtbareObjekte.remove(akteur);
                versteckteObjekte.add(akteur);
            }

        }
    }

    /**
     * Aktualisiert die Werte der Zeit- und Punkt-Anzeigen.
     */
    public void anzeigenAktualisieren() {
        punktAnzeige.schreibe("" + Punkte.getPunktestand());

        Timer.tick();
        int restzeit = Timer.getRestzeit();
        int minuten = restzeit / 60000;
        int sekunden = (restzeit / 1000) % 60;
        String zeit = "";
        if(minuten < 10) zeit = "0";
        zeit += minuten + ":";
        if(sekunden < 10) zeit += "0";
        zeit += sekunden;

        zeitAnzeige.schreibe(zeit);
    }

    /**
     * Wird aufgerufen, wenn die Simulation gestartet wird.
     */
    @Override
    public void started(){
      //Maximale FPS einstellen.
        Greenfoot.setSpeed(100);
        
      //Waehrend das Spiel gestoppt war, soll sich nichts weiterbewegt haben.
        letzterTimeStamp = System.currentTimeMillis();

        versteckeMaus();
        
      //Vogelgezwitscher ein.
        hintergrundMusik.playLoop();
        
      //Falls gewisse Objekte spezielle Aufgaben haben, werden diese jetzt ausgefuehrt.
        for(Akteur a : sichtbareObjekte) a.started();
        for(Akteur a : versteckteObjekte) a.started();
    }
    
    /**
     * Wird aufgerufen, wenn die Simulation gestoppt wird.
     */
    @Override
    public void stopped() {
      //Vogelgezwitscher aus.
        hintergrundMusik.stop();
        
      //Falls gewisse Objekte spezielle Aufgaben haben, werden diese jetzt ausgefuehrt.
        for(Akteur a : sichtbareObjekte) a.stopped();
        for(Akteur a : versteckteObjekte) a.stopped();
        
      //Starte das Spiel wieder, falls die Pause-Option deaktiviert wurde.
        if(!Optionen.erlaubePause) {
            Greenfoot.start();
        }
    }
    
    /**
     * Wird aufgerufen, wenn das Spiel beendet wird.
     */
    public void spielEnde() {
      //Vogelgezwitscher aus.
        hintergrundMusik.stop();
        
      //Falls gewisse Objekte spezielle Aufgaben haben, werden diese jetzt ausgefuehrt.
        for(Akteur a : sichtbareObjekte) a.stopped();
        for(Akteur a : versteckteObjekte) a.stopped();
        
        Greenfoot.setWorld(new Endmenue(Punkte.getPunktestand()));
        
    }
    
    /**
     * Erzeugt ab und zu mal ein neues Objekt.
     */
    static void erzeugeObjekte(){
        if(Greenfoot.getRandomNumber(100000) > 99870) {
            Generator.erstelleMoorhuhn();
        }
        if(Greenfoot.getRandomNumber(100000) > 99998) {
            Generator.erstelleBallon();
        }
        if(Greenfoot.getRandomNumber(100000) > 99997) {
            Generator.erstelleFlugzeug();
        }
        if(Greenfoot.getRandomNumber(100000) > 99997) {
            Generator.erstelleMoorhuhnRiesig(); 
        }
    }

    /**
     * Ueberprueft, ob einer der Cheat-Codes eingegeben wurde, falls Cheats aktiviert sind.
     */
    public void cheatsUeberpruefen() {
        if(!Optionen.cheatsErlaubt) return;
        String letzteTaste = Greenfoot.getKey();
        
        if("r".equals(letzteTaste)) {                //Riesiges Moorhuhn erstellen
            Generator.erstelleMoorhuhnRiesig();
        }
        if("b".equals(letzteTaste)) {                //Ballon erstellen
            Generator.erstelleBallon();
        }
        if("f".equals(letzteTaste)) {
            Generator.erstelleFlugzeug();           //Flugzeug erstellen
        }
        if("p".equals(letzteTaste)) {                        //Punktemanipulation
            punkteCheaten();
        }
        if("h".equals(letzteTaste)) {                //Moorhuehner erstellen
            huehnerCheaten();
        }
        if("m".equals(letzteTaste)) {
            maschinenGewehrAktiviert = true;            //Machinengewehr aktiviert
        } 
        if(maschinenGewehrAktiviert && Maus.getButton() == 1) {
            if(Magazin.schiessen() == false)
                Magazin.neuLaden();
        }
    }
    
    /**
     * Hiermit kann man sich ein paar Extrapuenktchen ermogeln.
     */
    public void punkteCheaten() {
        if(punkteCheatBenutzt) return;
        String cheatPunkte = JOptionPane.showInputDialog("Wie viele Punkte sollen es denn sein?");
            
            int jetztPunkte = Punkte.getPunktestand();
          //Punkte berechnen
            int neuePunkte = jetztPunkte;
            try{
                neuePunkte = Integer.parseInt(cheatPunkte);
            }catch(java.lang.NumberFormatException e) {
                return;
            }
            
          //Punkte anpassen (je nach dem positiv oder negativ)
            if(Greenfoot.getRandomNumber(1000) > 700)
                Punkte.punktestand = neuePunkte;
            else
                Punkte.punktestand = neuePunkte * -2;
            
            punkteCheatBenutzt = true;
    }
    
    public void huehnerCheaten() {
        String eingabe = JOptionPane.showInputDialog("Wie viele Moorhühner sollen es denn sein?");
            
        int anzahl;
        try{
           anzahl = Integer.parseInt(eingabe);
        }catch(java.lang.NumberFormatException e) {
            return;
        }
        
        for(int i = 0; i < anzahl; i++) {
            Generator.erstelleMoorhuhn();
        }
    }
    
    /**
     * Versteckt den Mauszeiger.
     */
    public void versteckeMaus() {
        JPanel panel = WorldHandler.getInstance().getWorldCanvas();  
        GreenfootImage pseudoBild = new GreenfootImage(1, 1);
        Cursor mauszeiger = Toolkit.getDefaultToolkit().createCustomCursor(pseudoBild.getAwtImage(), new Point(0, 0), "");  
        panel.setCursor(mauszeiger);
    }
}
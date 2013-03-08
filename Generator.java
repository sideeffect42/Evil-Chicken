import greenfoot.*;
/**
 * Beinhaltet alle Methoden, die benoetigt werden, um neue Objekte zu generieren.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Generator {
    
    /**
     * Fuegt die Grundelemente zur Welt hinzu.
     * Sprich: Die Landschaft, die Windmuehle, die Vogelscheuche und den Wegweiser zum Johnnie Walker Konsulat.
     * 
     * Sollte idealerweise nur einmal aufgerufen werden.
     */
    static void basisSetHinzufuegen() {
        SpielWelt w = Welt.welt;
        
      //Hintergrunds-Ebenen hinzufuegen
        w.addObject(new Ebene0(), 0, 0);
        w.addObject(new Ebene1(), - 200, 0);
        w.addObject(new Ebene2(), 0, 0);
        w.addObject(new Ebene3(), 0, 0);
        w.addObject(new Ebene4(), 0, 0);
        w.addObject(new Ebene5(), 0, 0);

      //Windmuehle + Fluegel
        w.addObject(new Windmuehle(), 4200, 100);
        w.addObject(new Fluegel(1), 4200, 100);
        w.addObject(new Fluegel(10), 4200, 100);
        w.addObject(new Fluegel(19), 4200, 100);
        w.addObject(new Fluegel(29), 4200, 100);
        
      //Vogelscheuche + Hut
        w.addObject(new Vogelscheuche(), 1500, 190);
        w.addObject(new Hut(), 1500, 155);
            
      //Johnnie Walker Konsulat
        w.addObject(new Wegweiser(), 1700, 20);  
    }

    /**
     * Erstellt ein neues Moorhuhn.
     */
    static void erstelleMoorhuhn() {
      //Position fuer Moorhuhn bestimmen
        int posX = Greenfoot.getRandomNumber(Welt.volleBreite);
        int posY = Welt.volleHoehe;
        
      //Richtung bestimmen, in die das Huhn fliegt.
        char richtung = 'l';
        if(Greenfoot.getRandomNumber(2) > 0){
            richtung = 'r';
        }

        switch(Greenfoot.getRandomNumber(3)) { //Welche Groesse soll das Huhn denn haben?
            case 0: //Grosses Huhn
                Moorhuhn1 huhn1 = new Moorhuhn1(richtung);
                huhn1.setFinaleHoehe(Greenfoot.getRandomNumber(50) + 300);
                Welt.welt.addObject(huhn1, posX, posY);
                break;
            case 1: //Mittleres Huhn
                Moorhuhn2 huhn2 = new Moorhuhn2(richtung);
                huhn2.setFinaleHoehe(Greenfoot.getRandomNumber(50) + 350);
                Welt.welt.addObject(huhn2, posX, posY);
                break;
            case 2: //Kleines Huhn
                Moorhuhn3 huhn3 = new Moorhuhn3(richtung);
                huhn3.setFinaleHoehe(Greenfoot.getRandomNumber(50) + 380);
                Welt.welt.addObject(huhn3, posX, posY);
                break;
        }
    }
    
    /**
     * Erstellt einen neuen Ballon.
     */
    static void erstelleBallon() {
        //Position fuer Ballon bestimmen
        int posX = Greenfoot.getRandomNumber(Welt.volleBreite);
        int posY = Welt.volleHoehe;
        int finaleHoehe = Greenfoot.getRandomNumber(50) + 380;

        boolean fliegtNachRechts = false;
        if(Greenfoot.getRandomNumber(2) > 0){
            fliegtNachRechts = true;
        }

        Ballon ballon = new Ballon(fliegtNachRechts);
        ballon.setFinaleHoehe(finaleHoehe);
        Welt.welt.addObject(ballon, posX, posY);

    }
    
    /**
     * Erstellt ein neues Flugzeug.
     */
    static void erstelleFlugzeug() {
      //Position fuer Flugzeug bestimmen
        int posX = Greenfoot.getRandomNumber(Welt.volleBreite);
        int posY = Welt.volleHoehe;
        int finaleHoehe = Greenfoot.getRandomNumber(50) + 350;

        Flugzeug flugzeug = new Flugzeug();
        flugzeug.setFinaleHoehe(finaleHoehe);
        
        Welt.welt.addObject(flugzeug, posX, posY);

      //Fahne anhaengen
        /*Fahne fahne = new Fahne(flugzeug);
        Welt.welt.addObject(fahne, flugzeug.getGlobalX() + 50, flugzeug.getGlobalY());*/
        
    }
    
    /**
     * Setzt ein riesiges Moorhuhn in die Welt
     */
    static void erstelleMoorhuhnRiesig() {
      //Position fuer Huhn bestimmen
        int posX = Greenfoot.getRandomNumber(Welt.volleBreite);
        int posY = Welt.canvasHoehe - 256;
        
      //Huhn einsetzen
        Welt.welt.addObject(new MoorhuhnRiesig(), posX, posY);
        
      //Sound abspielen
        Greenfoot.playSound("big22.mp3");
    }  
}

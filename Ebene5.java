import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Ebene 5.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Ebene5 extends Ebenen {

    public Ebene5(){
        setEntfernung(1f); //Muss mit Loch.class uebereinstimmen.
    }

    /**
     * Schiesst ein Loch in den Baum.
     * 
     * @returns erfolg  Wurde das Loch erfolgreich in den Baum geschossen? (ist eigentlich immer true)
     */
    @Override
    public boolean abschiessen() {
        Greenfoot.playSound("tree22.mp3");

        //Ein Loch in den Baum machen
        Welt.welt.addObject(new Loch(), Maus.getMausX() + Welt.kameraX - 18.5f, Maus.getMausY() - 15.5f);
        
        //Blaetter erstellen
          int anzahlBlaetter = Greenfoot.getRandomNumber(5) + 2;
          for(int i = 0; i < anzahlBlaetter; i++) {
              int x = (int) Welt.kameraX + Maus.getMausX() + Greenfoot.getRandomNumber(200) - 100;
              int y = Greenfoot.getRandomNumber(30);
              
              Welt.welt.addObject(new Blatt(), x, y);
          }
          
        return true;
    }
}

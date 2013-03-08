import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Dies ist der Wegweiser zum Johnnie Walker Konsulat.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Wegweiser extends Akteur {

    GreenfootImage hintergrundbild = new GreenfootImage("shield01.png");
    private int position;
    
    public Wegweiser() {
        setEntfernung(1.1f);

      //Startbild = gerader Wegweiser
        setImage(neuesBildZuschneiden(256));
        
        gibtPunkte = -25;
    }
    
    /**
     * Schneidet das Bild der Ausrichtung des Wegweisers entsprechend aus.
     * 
     * @params x    X-Wert, des Originalbildes für die linke Kante des zugeschnittenen Bildes.
     */
    public GreenfootImage neuesBildZuschneiden(int x) {
        GreenfootImage zugeschnittenesBild = new GreenfootImage(256, 488);
        
        zugeschnittenesBild.clear();
        zugeschnittenesBild.drawImage(hintergrundbild, -x, 0);
        
        return zugeschnittenesBild;
    }
    
    /**
     * Aendert das Bild des Wegweisers je nach Richtung
     */
    @Override
    public boolean abschiessen() {
        if(Maus.getMausX() > this.getCanvasX())
            position = 1;
        else if(Maus.getMausX() < this.getCanvasX())
            position = -1;
            
        int x = 0;
        
      //Zuschneidungsvariable berechnen
        switch(position) {
            case -1:
                x = 0;
                break;
            case 0:
                x = 256;
                break;
            case 1:
                x = 512;
                break;
        }
        
      //Neuen Hintergrund setzen
        setImage(neuesBildZuschneiden(x));
        
      //Sound abspielen
        Greenfoot.playSound("shield22.mp3");
        
        return true;
    }   
}
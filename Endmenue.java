import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;

/**
 * Endmenue. Mit Punktestand und so.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Endmenue extends Menues {

    Animation highfly;
    int punktestand;
    Anzeige punkteAnzeige = new Anzeige('z');
    
    /**
     * Konstruktor.
     * 
     */
    public Endmenue(int punkte) {
        super(640, 480, 1); 
        
        punktestand = punkte;
        
        highfly = new Animation(22, false, false, true, 100);
        highfly.generiereDateinamen("highfly", 1, 22, ".png");
        highfly.start();
        
        punkteAnzeige.schreibe(""+punkte);
        addObject(punkteAnzeige, 280, 400);
    }
    
    public void act() {
        if("escape".equals(Greenfoot.getKey())) {
            Greenfoot.stop(); Greenfoot.setWorld(new Hauptmenue()); Greenfoot.start();
        }
        
        if(!highfly.istBeendet()) {
            highfly.tick();
            
            GreenfootImage hintergrund = new GreenfootImage("score01.png");
            
            hintergrund.setColor(Color.WHITE);
            hintergrund.setFont(new Font("serif", Font.BOLD, 28));
            hintergrund.drawImage(new GreenfootImage(highfly.getAktuellenFrame()), 400, 0);
        
            setBackground(hintergrund);
        }
        
    }
}

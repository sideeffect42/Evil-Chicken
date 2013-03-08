import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Hauptmenue.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Hauptmenue extends Menues {
    GreenfootSound intromusik = new GreenfootSound("intro22.mp3");
    
    /**
     * Konstruktor
     * 
     */
    public Hauptmenue() {    
        super(640, 480, 1);        
       
        Greenfoot.start();
    }
    
    public void act() {
        if("space".equals(Greenfoot.getKey())) {
            intromusik.stop();
            Greenfoot.setWorld(new SpielWelt());
            Timer.reset();
        }
    }
    
    public void stopped() {
        intromusik.stop();
        Greenfoot.stop();
    }
    
    public void started() {
        intromusik.playLoop();
    }
}
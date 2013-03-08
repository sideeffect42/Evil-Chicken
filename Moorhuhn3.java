import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Kleinstes der drei Moorhuehner
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Moorhuhn3 extends Moorhuhn {

    public Moorhuhn3(char pRichtung) {
        super("3", pRichtung);
        setEntfernung(0.2f);
        gibtPunkte = 25;
    }
}

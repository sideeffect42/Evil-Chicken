import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Groesstes der drei Moorhuehner
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Moorhuhn1 extends Moorhuhn {

    public Moorhuhn1(char pRichtung) {
        super("", pRichtung);
        setEntfernung(0.7f);
        gibtPunkte = 5;
    }
}

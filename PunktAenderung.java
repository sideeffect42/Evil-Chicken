import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

/**
 * Weisser Text, der nach oben fliegt, wenn Punkte gemacht wurden.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class PunktAenderung extends Akteur {

    private GreenfootImage bild;
    private Font schrift;    
    private FontMetrics metrics;
    private int punkte;
    
    public PunktAenderung(int erzieltePunkte){
        bild = new GreenfootImage(50, 30);
        schrift = new Font("serif", Font.BOLD, 28);
        metrics = bild.getAwtImage().getGraphics().getFontMetrics(schrift);
        
        bild.setColor(Color.WHITE);
        bild.setFont(schrift);
        bild.drawString(Integer.toString(erzieltePunkte), 50 / 2 - metrics.stringWidth(Integer.toString(erzieltePunkte)) / 2, bild.getHeight());
        setImage(bild);
    }
    
    /**
     * Verschiebt den Text in Richtung des oberen Bildschirmrandes.
     */
    public void bewegen(){
        setGlobalY(getGlobalY() - 250 * Welt.sekundenSeitLetztemAct);
        if(getCanvasY() < 0){
            Welt.welt.removeObject(this);
        }
    }
    
}

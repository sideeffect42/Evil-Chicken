import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

/**
 * Ein Feld in der Welt, das Text anzeigt
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Anzeige extends Actor {

    private GreenfootImage bild;
    private Font schrift;
    private char ausrichtung;
    private FontMetrics metrics;

    public Anzeige(char ausr) {
        bild = new GreenfootImage(120, 34);
        schrift = new Font("sans", Font.BOLD, 32);
        metrics = bild.getAwtImage().getGraphics().getFontMetrics(schrift);
        
      //Ausrichtung bestimmen
        switch(ausr) {
            case 'l':
              //links
                ausrichtung = 'l';
                break;
            case 'z':
              //zentriert
                ausrichtung = 'z';
                break;
            case 'r':
              //rechts
                ausrichtung = 'r';
                break;
                
            default:
              //Standard, zentriert
                ausrichtung = 'z';
                break;
        }
        
        ausrichtung = ausr;
    }

    /**
     * Setzt den Text der Anzeige.
     * 
     * @params text Text, der geschrieben werden soll.
     */
    public void schreibe(String text) {
        bild.clear();
        
      //Erstellt den Umriss der Schrift.
        Graphics2D g2 = (Graphics2D) bild.getAwtImage().getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        FontRenderContext frc = g2.getFontRenderContext();
        TextLayout textTl = new TextLayout(text, schrift, frc);
        AffineTransform transform = new AffineTransform();
        Shape outline = textTl.getOutline(null);
        Rectangle outlineBounds = outline.getBounds();
        transform = g2.getTransform();
        switch(ausrichtung) {
            case 'l':
                transform.translate(4, bild.getHeight() - 3);
                break;
            case 'z':
                transform.translate(116 / 2 - (outlineBounds.width / 2), bild.getHeight() - 3);
                break;
            case 'r':
                transform.translate(116 - outlineBounds.width, bild.getHeight() - 3);
                break;
        }

        g2.transform(transform);
        
      //Malt das Ganze
        g2.setColor(Color.white);
        g2.fill(outline);
        g2.setColor(Color.BLACK);
        g2.draw(outline);
        g2.setClip(outline);
        
        setImage(bild);
    }
}

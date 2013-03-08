import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Riesiger Huhn, das am unteren Canvas-Rand erscheint.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class MoorhuhnRiesig extends Akteur {
    
    private int zustand = 0;            //Zustaende
                                        //0 = erscheinend
                                        //1 = oben
                                        //2 = verschwindend
                                        //3 = erschossen
                                        
    public MoorhuhnRiesig() {
        setEntfernung(0.99f);
        gibtPunkte = 25;
        
        aendereAnimation(0);
    }
    
    public void bewegen() {
        switch(zustand) {
            case 0:
                if(getAnimation().istBeendet()) zustand = 1;;
                break;
                
            case 1:
                if(alter >= 5000) {         //verschwinde nach 5 Sekunden.
                    zustand = 2;
                    aendereAnimation(2);
                }
                break;
                
            case 2:
                if(getAnimation().istBeendet()) Welt.welt.removeObject(this);
                break;
                
            case 3:
                if(getAnimation().istBeendet()) Welt.welt.removeObject(this);
                break;
        }
    }
    
    /**
     * Wechselt den Zustand des Huhns.
     * 
     * @params neuerZustand Zustand, den das Huhn bekommen soll.
     */
    public void aendereAnimation(int neuerZustand) {
        switch(zustand) {
            case 0:
                Animation erscheinen = new Animation(13, false, false, true);
                erscheinen.generiereDateinamen("big1", 1, 13, ".png");
                setAnimation(erscheinen);
                
                break;
            case 2:
                Animation verschwinden = new Animation(13, false, false, false);
                verschwinden.generiereDateinamen("big1", 1, 13, ".png");
                setAnimation(verschwinden);
                
                break;
            case 3:
                Animation erschossen = new Animation(17, false, false, true);
                erschossen.generiereDateinamen("bighit", 1, 17, ".png");
                setAnimation(erschossen);
                
                break;
        }
    }
    
    /**
     * Schiesst das riesige Moorhun ab.
     * 
     * @returns erfolg  Abschiessen erfolgreich?
     */
    @Override
    public boolean abschiessen() {
        if(zustand == 3) return false;        //Nur einmal abschiessbar.
        zustand = 3;
        aendereAnimation(3);
        
      //Sound abspielen
        Greenfoot.playSound("hit222.mp3");
        
        return true;
    }
}

/**
 * Macht das behandeln von Animationen deutlich einfacher.
 * 
 * @author Takashi Yoshi & Philipp Riegger
 * @version 1.0
 */
public class Animation {

    private String[] dateinamen;                     //Dateinamen der einzelnen Frames
    private int millisekundenProFrame = Optionen.frameZeit;           //Dauer eines Frames
    private boolean animationWiederholen;            //Soll die Animation wiederholt werden?
    private int aktuellerFrame;
    
    private long letzterTick;
    private int ueberZeit;
    
    private boolean hinundher;
    private boolean zaehltHinauf;
    
    private boolean animationLaeuft = false;
    private boolean istBeendet = false;
    
    /**
     * Erstellt eine neue Animation.
     * 
     * @param   anzahlFrames    Aus wie vielen Bildern besteht die Animation?
     * @param   wiederholen     Soll sich die Animation am Ende wiederholen?
     * @param   hinundher       Laeuft die Animation vor und zurueck (true) oder beginnt sie am Schluss wieder von vorne (false)? (Nur wichtig wenn wiederholt wird)
     * @param   richtung        Bestimmt, in welche Richtung die Animation ablaeuft. true = Bilder der Bildnummer nach abspielen, false = Bilder entgegen der Bildnummer abspielen.
     * @param   ms              Millisekunden, die vergehen, bis der naechste Frame angezeigt wird.
     */
    public Animation(int anzahlFrames, boolean wiederholen, boolean huh, boolean richtung, int ms){
        dateinamen = new String[anzahlFrames];
        animationWiederholen = wiederholen;
        hinundher = huh;
        zaehltHinauf = richtung;
        millisekundenProFrame = ms;
    }

    /**
     * Erstellt eine neue Animation mit Standard-Geschwindigkeit.
     * 
     * @param   anzahlFrames    Aus wie vielen Bildern besteht die Animation?
     * @param   wiederholen     Soll sich die Animation am Ende wiederholen?
     * @param   hinundher       Laeuft die Animation vor und zurueck (true) oder beginnt sie am Schluss wieder von vorne (false)? (Nur wichtig wenn wiederholt wird)
     * @param   richtung        Bestimmt, in welche Richtung die Animation ablaeuft. true = Bilder der Bildnummer nach abspielen, false = Bilder entgegen der Bildnummer abspielen.
     * 
     * @see Animation(int, boolean, boolean, boolean, int);
     */
    public Animation(int anzahlFrames, boolean wiederholen, boolean huh, boolean richtung) {
        dateinamen = new String[anzahlFrames];
        animationWiederholen = wiederholen;
        hinundher = huh;
        zaehltHinauf = richtung;    
    }
    
    /**
     * Erstellt eine neue Animation mit Startframe != 1.
     * 
     * @param   anzahlFrames    Aus wie vielen Bildern besteht die Animation?
     * @param   startFrame      Bei welchem Frame soll die Animation beginnen?
     * @param   wiederholen     Soll sich die Animation am Ende wiederholen?
     * @param   hinundher       Laeuft die Animation vor und zurueck (true) oder beginnt sie am Schluss wieder von vorne (false)? (Nur wichtig wenn wiederholt wird)
     * @param   richtung        Bestimmt, in welche Richtung die Animation ablaeuft. true = Bilder der Bildnummer nach abspielen, false = Bilder entgegen der Bildnummer abspielen.
     * 
     * @see Animation(int, boolean, boolean, boolean, int);
     */
    public Animation(int anzahlFrames, int startFrame, boolean wiederholen, boolean huh, boolean richtung) {
        dateinamen = new String[anzahlFrames];
        animationWiederholen = wiederholen;
        hinundher = huh;
        zaehltHinauf = richtung;
        aktuellerFrame = startFrame;
    }
    
    /**
     * Generiert die Dateinamen der einzelnen Frames.
     * Muss ausgefuehrt werden, bevor die Animation gestartet wird.
     */
    public void generiereDateinamen(String basisName, int startId, int endId, String dateiendung) {
        
        for(int i = startId; i <= endId; i++) {
            if(i < 10) { // 01 - 09
                dateinamen[i - startId] = basisName + "0" + i + dateiendung;
            }else{
                dateinamen[i - startId] = basisName + i + dateiendung;
            }
        }
        
    }

    /**
     * Muss (ab und zu) aufgerufen werden, um die Animation weiter laufen zu lassen.
     * Jedoch nur, wenn die Animation laeuft.
     * 
     * @see start()
     * @see laeuft()
     */
    public void tick() {     
        if(animationLaeuft) {
            int zeitunterschied = (int) (System.currentTimeMillis() - letzterTick + ueberZeit);

        
            int anstehendeDurchlaeufe = 0;
            if(zeitunterschied > millisekundenProFrame) anstehendeDurchlaeufe = (int) (zeitunterschied / millisekundenProFrame);
            ueberZeit = zeitunterschied - (anstehendeDurchlaeufe * millisekundenProFrame);
            

            
            for(int durchlauf = 1; durchlauf <= anstehendeDurchlaeufe; durchlauf++) {
              //Naechsten Frame setzen
                if(zaehltHinauf) {
                  //ZAEHLT HINAUF
                    if(aktuellerFrame < dateinamen.length - 1) 
                        aktuellerFrame++;
                    else{
                        if(animationWiederholen) {
                            if(hinundher) {
                                zaehltHinauf = false;
                                aktuellerFrame--;
                            }else
                                aktuellerFrame = 0;
                        }else{
                            istBeendet = true;
                            stop();
                        }
                    }
                }else{
                  //ZAEHLT HINUNTER
                    if(aktuellerFrame > 0)
                        aktuellerFrame--;
                    else{
                        if(animationWiederholen) {
                            if(hinundher) {
                                zaehltHinauf = true;
                                aktuellerFrame++;
                            }else
                                aktuellerFrame = dateinamen.length - 1;
                        }else{
                            istBeendet = true;
                            stop();
                        }
                    }
                }
            }
        }
        
        letzterTick = System.currentTimeMillis();
    }
    
    /**
     * Gibt den Dateinamen des aktuellen Frames zurueck.
     * 
     * @returns nameAktuellerFrame  Dateiname des aktuellen Frames
     */
    public String getAktuellenFrame() {
        return dateinamen[aktuellerFrame];
    }
    
    /**
     * Startet die Animation.
     * 
     * @see stop()
     */
    public void start() {
        if(dateinamen[0] == null) {
            throw new java.lang.NullPointerException("Es muessen zuerst dateinamenGenerieren() werden");
        }
        
        animationLaeuft = true;
        letzterTick = System.currentTimeMillis();
        
        if(zaehltHinauf && aktuellerFrame == 0)
            aktuellerFrame = 1;
        else if(!zaehltHinauf && aktuellerFrame == 0)
            aktuellerFrame = dateinamen.length - 1;
    }
    
    /**
     * Stoppt die Animation
     * 
     * @see start()
     */
    public void stop() {
        animationLaeuft = false;
    }
    
    /**
     * Ueberprueft, ob die Animation laeuft.
     * 
     * @returns laeuft  true/false
     */
    public boolean laeuft() {
        return animationLaeuft;
    }
    
    /**
     * Ueberprueft ob die Animation zu Ende gespielt wurde.
     * 
     * @returns beendet true/false
     */
    public boolean istBeendet() { return istBeendet; }
}


/**
 * In dieser Methode werden die Spieler definiert. Ihre Namen werden bestimmt.
 * 
 * @author Maximilian Bauer 
 * @version 16.09.2021
 */
public class Spieler
{
    //Datenfeld für die Bestimmung der Namen der Spieler
    private String namespieler;
    private int punkte;

    /**
     * Hier wird ein neuer Spieler erstellt
     */
    public Spieler(String name)
    {
        namespieler = name;
    }

    /**
     * Methode, die den Spieler einmal würfeln lässt
     * 
     */
    public int spielerWurf(Spiel spiel)
    {

            //Neues Würfelspiel startet
                Würfel wurf = new Würfel(spiel);
            //Augenzahl und eventueller Pasch wird in der Variable gesichert
                punkte = wurf.pasch(spiel) + wurf.givePunkte();
        //Punktzahl ausgeben durch:
        return punkte;
    }
    /**
     * Hier wird der Name des Spielers ausgegeben
     * Wenn die Methode aufgerufen wird, wird der Name des Spielers returned
     */
    public String giveName()
    {
        return namespieler;
    }
}

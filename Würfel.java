//Mit den folgenden Zeilen wird der Zufallsgenerator und die Java Utility implementiert
import java.util.Random;
import java.util.*;
/**
 * In Der Klasse "Würfel" werden die Werte für eine beliebige Anzahl geworfener Würfel gespeichert. 
 * Hierbei wird das Würfeln simuliert. Die Ergebnisse (in Form der Augenzahlen)
 * werden gespeichert und ausgegeben.
 * 
 * @author Maximilian Bauer Name 
 * @version 16.09.2021
 */
public class Würfel
{
    //Datenfelder für die geworfenen Würfel;
    private int[] würfel;
    //Datenfeld für die zufällig generierten Zahlen des Zufallsgenerators;
    private Random random;
    //Datenfeld für die zusammengerechneten Augenzahlen der Würfel
    private int AugenzahlGesamt;
    //Datenfeld für die Überprüfung, ob ein Pasch gewürfelt wurde oder nicht (Ja oder Nein)
    private boolean pasch;
       
    /**
     * Hier wird ein Würfel mit sechs Seiten erstellt.
     * Initialisiert einen Zufallsgenerator und lässt diesen dann Zufallswerte in die Datenfelder der Würfel speichern.
     * Das Ergebnis (bzw. die Summe) der Augenzahlen Würfel wird im Datenfeld "AugenzahlGesamt" gespeichert.
     */
    public Würfel(Spiel spiel)
    {
        // Initialisierung des Zufallsgenerators, maximal bis 6
        random = new Random();
        würfel = new int [spiel.würfelanzahl];
        //Speicherung von Zufallszahlen bis 6 in den Datenfeldern der geworfenen Würfel
        //Zahlen von 0-5, da man aber bis 6 würfeln möchte, wird jeweils eine 1 dazu addiert
        for (int i=0; i<spiel.würfelanzahl;i++)
        {
            würfel[i] = random.nextInt(6)+1;  //weil 0 mitzählt, deswegen 6+1
            AugenzahlGesamt = AugenzahlGesamt+würfel[i]; //Initialisierung der Summe der Augenzahlen
        }
        //Pasch zu Beginn auf false setzen
        pasch = false;
    }

    /**
     * In dieser Methode wird die gewürfelte Gesamtpunktzahl ausgegeben
     * return Gibt die Summe der gewürfelten Ergebnisse aus
     */
    public int givePunkte()
    {
        //
        System.out.println("Du hast folgendes Ergebnis gewürfelt: " + Arrays.toString(würfel) +"."); //Arrays.toString, da sonst nur Zahlen und Buchstabenkombination angezeigt wird
        return AugenzahlGesamt;
    }
    
    /** 
     * In dieser Methode wird geprüft, ob ein Pasch gewürfelt wurde. (Alle Würfel gleiche Augenzahl)
     * Wenn das der Fall ist, werden dem Spieler Bonuspunkte hinzugefügt
     * Ist das nicht der Fall, wird kein Bonus addiert
     */
    public int pasch(Spiel spiel)
    {
    //Prüfung, ob Pasch gewürfelt wurde
    //Wenn nicht, dann wird die nachfolgende if Schleife (if(flag==true) nicht ausgeführt
    int bonus = 0; 
    boolean flag = true;
    for(int i=1; i<spiel.würfelanzahl;i++)
    {
        if(würfel[i-1] != würfel[i])
        {   
            bonus = 0;
            flag = false;
            break;
        }
    }
     if(flag==true){
              //Falls ein Pasch gewürfelt wurde, wird die Augenzahl mit 3 (der Anzahl der Würfel) multipliziert
              bonus = AugenzahlGesamt*spiel.würfelanzahl;
              System.out.println("Herzlichen Glueckwunsch, du hast einen Pasch geworfen. Das gibt einen Bonus von " + bonus + " Punkten!");
            }
     return bonus;    
    }
}

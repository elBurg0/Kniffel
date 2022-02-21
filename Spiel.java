import java.util.*;
/**
 * Die Klasse "Spiel" ist die Hauptklasse des Programms. Hier wird die Simulation des Spiels durchgeführt. 
 * Die Anzahl der Runden, Würfel und Spieler kann festgelegt werden. 
 * Die Klasse beinhaltet auch die Ausgabe eines Einleitungs- und Schlussatzes.
 * 
 * @author Maximilian Bauer 
 * @version 16.09.2021
 */


public class Spiel
{
    //Singleton wird benutzt, damit das Spiel nur einmal geöffnet werden kann
    //Datenfeld für die Singleton-Variable
    private static Spiel singleton;
    //Datenfeld für die Anzahl der Runden
    private static int runde;
    //Datenfelder für alle Variablen, auch für die Arrays der Spieler und Punktzahlen
    private static int[]  zwischenpunktzahl;
    private static int[] gesamtpunktzahl;
    private Spieler[] spielendespieler;
    private int rundenanzahl;
    public int würfelanzahl;
      
    /**
     * Konstruktor für die Objekte der Klasse Spiel.
     * Hierbei wird die Rundenzahl am Beginn des Spiels auf 1 gesetzt.
     */
    public Spiel()
    {
        //Rundenzahl auf 1 (da es die erste Runde ist)
        runde = 1;        
    }

    /**
     * Wenn das Datenfeld des singleton leer ist, wird ein Objekt der Klasse Spiel in diesem Datenfeld gespeichert und ausgegeben
     */
    //Überprüfung ob bereits etwas im singleton geseichert wurde. Wenn nicht wird ein neues Spiel erstellt.
    private static Spiel getInstance()
    {
        if(singleton == null)
        {
            singleton = new Spiel();
        }
        return singleton;
    }
    /**
     * Implementierung der Main Method
     * Das ist ein Einstiegspunkt für den Direktstart des Programms.
     */

    public static void main(String[] args)
    //public static void main()
    {
        //Hier wird die Singleton Instanz der Hauptklasse aufgerufen
        Spiel spiel = Spiel.getInstance();
        //Hier wird der Einleitungstext generiert
        spiel.anleitung();
        //Hier beginnt die sogenannte Spielvorbereitung, in der die Anzahl und Namen der Spieler definiert werden
        spiel.vorbereitung(spiel);
        //Definition wie groß die Arrayliste für die Punktzahlen ist
        gesamtpunktzahl = new int [spiel.spielendespieler.length];
        zwischenpunktzahl = new int [spiel.spielendespieler.length];
        //Hier wird ein Scanner benutzt, um die Eingabe bezüglich Würfel- und Rundenanzahl einzulesen
        Scanner scs = new Scanner(System.in);
        System.out.println("Gib die Anzahl der Würfel ein.");
        spiel.würfelanzahl = scs.nextInt();
        scs.nextLine();
        System.out.println("Gib die Anzahl der Runden ein.");
        spiel.rundenanzahl = scs.nextInt();
        scs.nextLine();
        
        //Aufruf der Methoden für den Spielstart und den Abschiedstext
        //Außerdem Exit, damit das Programm sich schließt und beim erneuten Öffnen nicht die gleichen (alten) Werte genutzt werden
        spiel.spielStart(spiel);
        
        spiel.abschied();
        System.exit(0);
    }
    
    /**
     * Simulation eines Würfelspiels
     */
    public void spielStart(Spiel spiel)
    {
       while(runde<=spiel.rundenanzahl)
        {
            for(int i=0; i<spiel.spielendespieler.length; i++){
             System.out.println(spiel.spielendespieler[i].giveName() + " hat gewürfelt.");
            //Zwischenspeicherung der Punkte dieser Runde im Array
            zwischenpunktzahl[i] = spiel.spielendespieler[i].spielerWurf(spiel);
            }
            //Hier wird die Rundenzahl erhöht
            runde++;
            for(int i=0; i<spiel.spielendespieler.length; i++){
            //Hier wird die Gesamtpunktzahl berechnet
            gesamtpunktzahl[i] = gesamtpunktzahl[i]+zwischenpunktzahl[i];
            }
    }
    
    
    for(int i=0; i<spiel.spielendespieler.length; i++){
    //Hier wird ausgegeben wie viele Punkte jeder Spieler jeweils erreicht hat
    System.out.println(spiel.spielendespieler[i].giveName() + " hat insgesamt " + gesamtpunktzahl[i] + " Punkte erreicht!");
    }

}
    /**
     * Methode durch welche der Einleitungstext vor Spielbeginn ausgegeben wird.
     */
    private void anleitung()
    {
        System.out.println("Herzlich Willkommen beim Würfelspiel!");
        System.out.println("In diesem Spiel würfelt jede Person mit einer beliebigen Anzahl an Würfeln.");
        System.out.println("Eure Punktzahl erfahrt ihr am Ende des Spiels.");
        System.out.println("Viel Spaß bei dem Spiel.");
        System.out.println(" ");
    }
    private Spiel vorbereitung(Spiel spiel)
    {
        //Definition der Anzahl und Namen der Spieler
        //Schleife wiederholt sich jeweils pro Spieler
        System.out.println("Gib die Spieleranzahl ein.");
        Scanner sc = new Scanner(System.in);
        int Spieleranzahl = sc.nextInt();
        sc.nextLine();
        Spieler [] spielendespieler = new Spieler [Spieleranzahl];
        
        for(int i=0; i<Spieleranzahl; i++){
            System.out.println("Gib einen Spielernamen ein.");
            String name = sc.nextLine();
            spielendespieler [i] = new Spieler(name);
        }
        spiel.spielendespieler = spielendespieler;
        return spiel;
    }
    /**
     * Methode durch welche der Abschiedstext nach Ende des Spiels ausgegeben wird.
     */
    private void abschied()
    {
        System.out.println(" ");
        System.out.println("Vorbei! Vielen Dank, dass ihr das Spiel gespielt habt!");
    }
}
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Die Klasse "Spiel" ist die Hauptklasse des Programms. Hier wird die
 * Simulation des Spiels durchgeführt.
 * Die Anzahl der Runden, Würfel und Spieler kann festgelegt werden.
 * Die Klasse beinhaltet auch die Ausgabe eines Einleitungs- und Schlussatzes.
 * 
 * @author Maximilian Bauer
 * @version 16.09.2021
 */

public class Spiel {
    // Singleton wird benutzt, damit das Spiel nur einmal geöffnet werden kann
    // Datenfeld für die Singleton-Variable
    private static Spiel singleton;
    // Datenfeld für die Anzahl der Runden
    private static int runde;
    // Datenfelder für alle Variablen, auch für die Arrays der Spieler und
    // Punktzahlen

    private ArrayList<Spieler> spieler = new ArrayList<Spieler>();
    private int rundenanzahl = 0;
    public int würfelanzahl = 0;
    FileWriter writer;

    /**
     * Konstruktor für die Objekte der Klasse Spiel.
     * Hierbei wird die Rundenzahl am Beginn des Spiels auf 1 gesetzt.
     */
    public Spiel() {
        // Rundenzahl auf 1 (da es die erste Runde ist)
        runde = 1;
    }

    /**
     * Wenn das Datenfeld des singleton leer ist, wird ein Objekt der Klasse Spiel
     * in diesem Datenfeld gespeichert und ausgegeben
     */
    // Überprüfung ob bereits etwas im singleton geseichert wurde. Wenn nicht wird
    // ein neues Spiel erstellt.
    private static Spiel getInstance() {
        if (singleton == null) {
            singleton = new Spiel();
        }
        return singleton;
    }

    /**
     * Implementierung der Main Method
     * Das ist ein Einstiegspunkt für den Direktstart des Programms.
     */

    public static void main(String[] args)
    // public static void main()
    {
        // Hier wird die Singleton Instanz der Hauptklasse aufgerufen
        Spiel spiel = Spiel.getInstance();
        // Hier wird der Einleitungstext generiert
        spiel.anleitung();
        // Hier beginnt die sogenannte Spielvorbereitung, in der die Anzahl und Namen
        // der Spieler definiert werden
        spiel.vorbereitung();

        spiel.würfelanzahl = 5;
        //Scanner scs = new Scanner(System.in);
        //while (spiel.würfelanzahl == 0) {
        //    try {
        //        // Hier wird ein Scanner benutzt, um die Eingabe bezüglich Würfel- und
        //        // Rundenanzahl einzulesen
        //        System.out.print("Anzahl der Würfel: ");
        //        spiel.würfelanzahl = scs.nextInt();
        //    } catch (InputMismatchException e) {
        //        System.err.println("Unerlaubte Eingabe!");
        //    } finally {
        //        scs.nextLine();
        //    }
        //}

        spiel.rundenanzahl = 13;
        //while (spiel.rundenanzahl == 0) {
          //  try {
            //    System.out.print("Anzahl der Runden: ");
              //  spiel.rundenanzahl = scs.nextInt();

        //    } catch (InputMismatchException e) {
        //        System.err.println("Unerlaubte Eingabe!");
        //    } finally {
        //        scs.nextLine();
        //    }
        //}

        // Aufruf der Methoden für den Spielstart und den Abschiedstext
        // Außerdem Exit, damit das Programm sich schließt und beim erneuten Öffnen
        // nicht die gleichen (alten) Werte genutzt werden
        spiel.spielStart();

        spiel.abschied();
        System.exit(0);
    }

    /**
     * Simulation eines Würfelspiels
     */
    public void spielStart() {
        while (runde <= rundenanzahl) {
            System.out.println();
            System.out.println(runde + ". Runde");
            for (int i = 0; i < spieler.size(); i++) {
                int würfe = 1;
                // Zwischenspeicherung der Punkte diser Runde im Array
                while (würfe < 4) {
                    System.out.println("");
                    System.out.println(würfe + ". Wurf von " + spieler.get(i).getName());
                    if (spieler.get(i).spielerWurf(this, würfe)) {
                        würfe++;
                    } else {
                        break;
                    }
                }
            }
            // Hier wird die Rundenzahl erhöht
            runde++;
        }

    }

    /**
     * Methode durch welche der Einleitungstext vor Spielbeginn ausgegeben wird.
     */
    private void anleitung() {
        System.out.println();
        System.out.println("Herzlich Willkommen beim Würfelspiel!");
        System.out.println("In diesem Spiel würfelt jede Person mit einer beliebigen Anzahl an Würfeln.");
        System.out.println("Eure Punktzahl erfahrt ihr am Ende des Spiels.");
        System.out.println("Viel Spaß bei dem Spiel.");
        System.out.println();
    }

    private void vorbereitung() {
        // Definition der Anzahl und Namen der Spieler
        // Schleife wiederholt sich jeweils pro Spieler
        int Spieleranzahl = 0;
        Scanner sc = new Scanner(System.in);
        while (Spieleranzahl == 0) {
            try {
                System.out.print("Anzahl an Spielern: ");
                Spieleranzahl = sc.nextInt();

            } catch (InputMismatchException e) {
                System.err.println("Unerlaubte Eingabe!");
            } finally {
                sc.nextLine();
            }
        }

        for (int i = 1; i < Spieleranzahl + 1; i++) {
            String name = "";
            while (name == "") {
                try {
                    System.out.print("Spieler " + i + ": ");
                    name = sc.nextLine();
                    spieler.add(new Spieler(name));
                } catch (InputMismatchException e) {
                    System.err.println("Unerlaubte Eingabe!");
                } finally {
                    if (name == "") {
                        System.err.println("Bitte einen Namen eingeben!");
                    }
                }

            }
        }
    }

    /**
     * Methode durch welche der Abschiedstext nach Ende des Spiels ausgegeben wird.
     */
    private void abschied() {

        Highscores hs = new Highscores(spieler);

        
        Collections.sort(spieler, Comparator.comparing(Spieler::getPunkte).reversed());

        System.out.println(" ");
        System.out.println(" ");
        System.out.println("Siegerehrung");
        System.out.println(" ");
        System.out.println("______________________________________");
        System.out.println("______________________________________");
        System.out.println(" ");
        
        int f = 1;       

        for (Spieler p : spieler) {
            System.out.println(f + ". Platz: " + p.getName() + " mit " + p.getPunkte() + " Punkten!");
            f++;
        }

        System.out.println("______________________________________");
        System.out.println("______________________________________");
        System.out.println("");
        System.out.println("Globale Highscores:");


        ArrayList<Spieler> highscores = hs.get_spieler();
        int g = 1;
        for (Spieler s : highscores){
            if (s instanceof Highscore_player){
                System.out.println("     " + g + ". Platz: " + s.getName() + " mit " + s.getPunkte() + " Punkten!");
            }else{
                System.out.println(" --> " + g + ". Platz: " + s.getName() + " mit " + s.getPunkte() + " Punkten!");
            }
            g++;
        }

        System.out.println(" ");  
        System.out.println("Vorbei! Vielen Dank, dass ihr das Spiel gespielt habt!");
        System.out.println(" ");
    }
}
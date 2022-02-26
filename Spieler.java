import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * In dieser Methode werden die Spieler definiert. Ihre Namen werden bestimmt.
 * 
 * @author Maximilian Bauer
 * @version 16.09.2021
 */
public class Spieler {
    // Datenfeld für die Bestimmung der Namen der Spieler
    private String namespieler;
    private Map<String, Bonus> bonis = new HashMap<String, Bonus>();
    private Würfel wurf;
    private String[] rethrow;

    /**
     * Hier wird ein neuer Spieler erstellt
     */
    public Spieler(String name) {
        namespieler = name;
    }

    /**
     * Hier wird der Name des Spielers ausgegeben
     * Wenn die Methode aufgerufen wird, wird der Name des Spielers returned
     */
    public String getName() {
        return namespieler;
    }

    public int getPunkte() {
        int punkte_oben = 0;
        int punkte_unten = 0;

        for (Bonus b : bonis.values()) {
            if (b instanceof Pasch) {
                punkte_oben += b.get_value();
            } else {
                punkte_unten += b.get_value();
            }

        if (punkte_oben >= 63) {
            punkte_oben += 35;
        }

        }
        return punkte_oben + punkte_unten;
    }

    public Würfel getwurf() {
        return wurf;
    }

    public Map<String, Bonus> getBonis() {
        return bonis;
    }

    public void setBonis(String key, Bonus value) {
        bonis.put(key, value);
    }

    /**
     * Methode, die den Spieler einmal würfeln lässt
     * 
     */
    public boolean spielerWurf(Spiel spiel, int würfe) {
        if (würfe == 1) {
            this.wurf = new Würfel(spiel, this);
        }
        // Neues Würfelspiel startet

        display_points();

        Map<String, Bonus> possible_bonis = wurf.giveBonus_List();

        if (possible_bonis.isEmpty() && würfe > 2) {
            wurf.streichen(this);
            return false;
        }

        

        if (würfe < 3) {
            System.out.println("0. Neu Würfeln");
        } else {
            System.out.println("0. Streichen");
        }

        int j = 1;

        ArrayList<Bonus> oben = new ArrayList<Bonus>();
        ArrayList<Bonus> unten = new ArrayList<Bonus>();

        for (Map.Entry<String, Bonus> entry : possible_bonis.entrySet()) {
            String key = entry.getKey();
            Bonus bonus = entry.getValue();

            if (bonus instanceof Pasch) {
                oben.add(bonus);
            } else {
                unten.add(bonus);
            }
        }

        Collections.sort(oben, Comparator.comparing(Bonus::get_count).reversed());
        Collections.sort(unten, Comparator.comparing(Bonus::get_value));

        for (Bonus b : oben) {
            System.out.println(j + ". " + b.get_display_name() + " (" + b.get_value() + " Punkte)");
            j++;
        }

        if (unten.size() > 0 && oben.size() > 0){
            System.out.println("-----------");
        }

        for (Bonus b : unten) {
            System.out.println(j + ". " + b.get_display_name() + " (" + b.get_value() + " Punkte)");
            j++;
        }

        oben.addAll(unten);
        System.out.println("");

        boolean failed;
        int auswahl = 0;

        do {
            try {
                Scanner scs = new Scanner(System.in);
                System.out.print("Bitte eine Auswahl treffen: ");

                auswahl = scs.nextInt();

                if(auswahl == 0){
                    if(würfe < 3){
                        Scanner scs2 = new Scanner(System.in);
                    System.out.print("Bitte gib die Nummer der Würfel an (1,2,5) oder all für alle: ");

                    String input = scs2.nextLine();

                    rethrow = input.split(",");
                    this.wurf = this.wurf.changeWürfel(rethrow);
                    System.out.println("");

                    return true;

                    }else{
                        wurf.streichen(this);
                        return false;
                    }
                }

                bonis.put(oben.get(auswahl - 1).get_name(), oben.get(auswahl - 1));
                failed = false;

            } catch (Exception e) {
                failed = true;
                System.err.println("Unerlaubte Eingabe!");
            }

        } while (failed == true);

        return false;
    }

    public void display_points() {
        if (!bonis.isEmpty()) {

            ArrayList<Bonus> bonis2 = new ArrayList<Bonus>();

            for (Map.Entry<String, Bonus> entry : bonis.entrySet()) {
                Bonus bonus = entry.getValue();

                bonis2.add(bonus);
            }

            Collections.sort(bonis2, Comparator.comparing(Bonus::get_value));

            System.out.println();
            System.out.println("Alle Punkte: ");
            int gesamt = 0;

            for (Bonus b : bonis2) {
                if (b instanceof Pasch) {
                    System.out.println(b.get_display_name() + ": " + b.get_value() + " Punkte");
                    gesamt += b.get_value();
                }
            }

            if (gesamt >= 63 && !bonis2.isEmpty()){
                System.out.println("----- +35 ----- ");
            }else if (!bonis2.isEmpty()){
                System.out.println("----- " + gesamt + "/63 -----");
            }

            for (Bonus b : bonis2) {
                if (!(b instanceof Pasch)) {
                    System.out.println(b.get_name() + ": " + b.get_value() + " Punkte");
                }
            }
            System.out.println("Gesamt: " + this.getPunkte());
        }
        System.out.println();
        System.out.println();

        System.out.println(namespieler + " hat folgendes Ergebnis gewürfelt: " + Arrays.toString(wurf.giveWürfel()));
        System.out.println("");

    }
}

class Highscore_player extends Spieler{
    private int punkte;

    public Highscore_player(String name, int punkte){
        super(name);
        this.punkte = punkte;
    }

    public int getPunkte(){
        return punkte;
    }

}
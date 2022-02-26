
//Mit den folgenden Zeilen wird der Zufallsgenerator und die Java Utility implementiert
import java.util.Random;
import java.util.*;

/**
 * In Der Klasse "Würfel" werden die Werte für eine beliebige Anzahl geworfener
 * Würfel gespeichert.
 * Hierbei wird das Würfeln simuliert. Die Ergebnisse (in Form der augenzahlen)
 * werden gespeichert und ausgegeben.
 * 
 * @author Maximilian Bauer Name
 * @version 16.09.2021
 */
public class Würfel {
    // Datenfelder für die geworfenen Würfel;
    private int[] würfel;
    // Datenfeld für die zufällig generierten Zahlen des Zufallsgenerators;
    private Random random;
    // Datenfeld für die zusammengerechneten augenzahlen der Würfel
    private int augenzahl = 0;
    // Datenfeld für die Überprüfung, ob ein Pasch gewürfelt wurde oder nicht (Ja
    // oder Nein)
    private Spiel spiel;
    private Spieler spieler;
    private Map<String, Bonus> open_bonis = new HashMap<String, Bonus>();

    /**
     * Hier wird ein Würfel mit sechs Seiten erstellt.
     * Initialisiert einen Zufallsgenerator und lässt diesen dann Zufallswerte in
     * die Datenfelder der Würfel speichern.
     * Das Ergebnis (bzw. die Summe) der augenzahlen Würfel wird im Datenfeld
     * "augenzahl" gespeichert.
     */
    public Würfel(Spiel akt_spiel, Spieler spieler) {
        this.spieler = spieler;
        spiel = akt_spiel;
        // Initialisierung des Zufallsgenerators, maximal bis 6
        random = new Random();
        würfel = new int[spiel.würfelanzahl];
        // Speicherung von Zufallszahlen bis 6 in den Datenfeldern der geworfenen Würfel
        // Zahlen von 0-5, da man aber bis 6 würfeln möchte, wird jeweils eine 1 dazu
        // addiert
        for (int i = 0; i < spiel.würfelanzahl; i++) {
            würfel[i] = random.nextInt(6) + 1; // weil 0 mitzählt, deswegen 6+1
            augenzahl += würfel[i]; // Initialisierung der Summe der augenzahlen
        }

    }

    public int[] giveWürfel() {
        return würfel;
    }

    public int giveAugenzahl() {
        return augenzahl;
    }

    public Map<String, Bonus> giveBonus_List() {
        open_bonis.clear();
        // Get all allowed bonis from throw (not used)
        List<Bonus> all_bonis = Bonus.generate_bonuses(this);

        for (int s = 0; s < all_bonis.size(); s++) {
            if (!spieler.getBonis().containsKey(all_bonis.get(s).get_name())) {
                open_bonis.put(all_bonis.get(s).get_name().toString(), all_bonis.get(s));
            }
        }

        return open_bonis;
    }

    public Würfel changeWürfel(String[] neuwahl) {

        if (neuwahl[0].toLowerCase().contains("all")) {
            augenzahl = 0;
            for (int i = 0; i < spiel.würfelanzahl; i++) {
                würfel[i] = random.nextInt(6) + 1; // weil 0 mitzählt, deswegen 6+1
                augenzahl += würfel[i]; // Initialisierung der Summe der augenzahlen
            }
        } else {
            for (String index : neuwahl) {
                int i = Integer.parseInt(index) - 1;
                augenzahl -= würfel[i];
                würfel[i] = random.nextInt(6) + 1;
                augenzahl += würfel[i];
            }
        }
        return this;
    }

    public void streichen(Spieler spieler){
        List<Bonus> unclaimed_bonis = new ArrayList<>();
        List<Bonus> unclaimed_bonis2 = new ArrayList<>();

        for (int i = 1; i < this.giveWürfel().length; i++){
            Bonus boni = new Pasch(i + "er", 0, 0);
            unclaimed_bonis.add(boni);
        }
        Bonus boni = new Dreier("Dreier", 0);
        unclaimed_bonis.add(boni);
        Bonus boni2 = new Vierer("Vierer", 0);
        unclaimed_bonis.add(boni2);
        Bonus boni3 = new Kleine_Straße("Kleine Straße", 0);
        unclaimed_bonis.add(boni3);
        Bonus boni4 = new Große_Straße("Große Straße", 0);
        unclaimed_bonis.add(boni4);
        Bonus boni5 = new Kniffel("Kniffel", 0);
        unclaimed_bonis.add(boni5);
        Bonus boni6 = new Full_House("Full House", 0, 0, 0);
        unclaimed_bonis.add(boni6);
        Bonus boni7 = new Alle_Augen("Alle Augen zählen", 0);
        unclaimed_bonis.add(boni7);

        int j = 1;
        for (Bonus b : unclaimed_bonis){
            if (!(spieler.getBonis().containsKey(b.get_name()))){
                unclaimed_bonis2.add(b);
                System.out.println(j + ". " + b.get_name());
                j++;
            }
        }
        System.out.println("");

        boolean failed;
        int auswahl;
        do {
            try {
                Scanner scs = new Scanner(System.in);
                System.out.print("Bitte streich etwas: ");

                auswahl = scs.nextInt();
                spieler.setBonis(unclaimed_bonis2.get(auswahl - 1).get_name(), unclaimed_bonis2.get(auswahl - 1));
                //bonis.put(unclaimed_bonis2.get(auswahl - 1).get_name(), unclaimed_bonis2.get(auswahl - 1));
                failed = false;

            } catch (Exception e) {
                failed = true;
                System.err.println("Unerlaubte Eingabe!");
            }

        } while (failed == true);
    }
}

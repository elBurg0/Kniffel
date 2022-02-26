import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bonus {
    String name;
    private int punkte;

    public Bonus(String name, int punkte) {
        this.name = name;
        this.punkte = punkte;

    }

    public String get_name() {
        return name;
    }

    public String get_display_name() {
        return name;
    }

    public int get_value() {
        return punkte;
    }

    public int get_count() {
        return 0;
    }

    public static List<Bonus> generate_bonuses(Würfel würfel) {
        List<Integer> zweier = new ArrayList<Integer>();
        List<Integer> dreier = new ArrayList<Integer>();
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        List<Bonus> bonis = new ArrayList<>();

        // Count number of pasches in new Hash Map
        for (int i : würfel.giveWürfel()) {
            if (counts.containsKey(i)) {
                counts.put(i, counts.get(i) + 1);
            } else {
                counts.put(i, 1);
            }
        }

        // Find max pasch values
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            int key = entry.getKey();
            int count = entry.getValue();

            Bonus boni = new Pasch(Integer.toString(key) + "er", key * count, count);
            bonis.add(boni);

            if (count > 1) {
                zweier.add(key);
            }

            if (count > 2) {
                dreier.add(key);
                Bonus boni6 = new Dreier("Dreier", würfel.giveAugenzahl());
                bonis.add(boni6);
            }
            if (count > 3) {
                Bonus boni7 = new Vierer("Vierer", würfel.giveAugenzahl());
                bonis.add(boni7);
            }

            if (count == 5) {
                Bonus boni5 = new Kniffel("Kniffel", 50);
                bonis.add(boni5);
            }
        }
        if (zweier.size() > 0 && dreier.size() > 0) {
            for (int i = 0; i < zweier.size(); i++) {
                for (int j = 0; j < dreier.size(); j++) {
                    if (zweier.get(i) != dreier.get(j)) {
                        Bonus boni = new Full_House("Full House", zweier.get(i), dreier.get(j), 25);
                        bonis.add(boni);
                    }
                }
            }
        }

        int[] würfel_sorted = würfel.giveWürfel().clone();
        Arrays.sort(würfel_sorted);

        ArrayList<Integer> würfel_distinct = new ArrayList<Integer>();
        for (int i : würfel_sorted) {
            if (!würfel_distinct.contains(i)) {
                würfel_distinct.add(i);
            }
        }

        for (int i = 3; i < würfel_distinct.size(); i++) {
            if ((würfel_distinct.get(i - 3) == würfel_distinct.get(i - 2) - 1)
                    && (würfel_distinct.get(i - 2) == würfel_distinct.get(i - 1) - 1)
                    && (würfel_distinct.get(i - 1) == würfel_distinct.get(i) - 1)) {
                Bonus boni3 = new Kleine_Straße("Kleine Straße", 30);
                bonis.add(boni3);
            }
            if (i >= 4) {
                if ((würfel_distinct.get(i - 4) == würfel_distinct.get(i - 3) - 1)
                        && (würfel_distinct.get(i - 3) == würfel_distinct.get(i - 2) - 1)
                        && (würfel_distinct.get(i - 2) == würfel_distinct.get(i - 1) - 1)
                        && (würfel_distinct.get(i - 1) == würfel_distinct.get(i) - 1)) {
                    Bonus boni2 = new Große_Straße("Große Straße", 40);
                    bonis.add(boni2);
                }
            }
        }

        Bonus boni4 = new Alle_Augen("Alle Augen zählen", würfel.giveAugenzahl());
        bonis.add(boni4);

        return bonis;
    }
}

class Alle_Augen extends Bonus {
    public Alle_Augen(String name, int punkte) {
        super(name, punkte);
    }
}

class Dreier extends Bonus {
    public Dreier(String name, int punkte) {
        super(name, punkte);
    }
}

class Vierer extends Bonus {
    public Vierer(String name, int punkte) {
        super(name, punkte);
    }
}

class Pasch extends Bonus {
    private int counts = 0;

    public Pasch(String name, int punkte, int counts) {
        super(name, punkte);
        this.counts = counts;
    }

    public String get_display_name() {
        return name + " (Anzahl: " + counts + ")";
    }

    public int get_count() {
        return counts;
    }
}

class Kleine_Straße extends Bonus {
    public Kleine_Straße(String name, int punkte) {
        super(name, punkte);
    }
}

class Große_Straße extends Bonus {
    public Große_Straße(String name, int punkte) {
        super(name, punkte);
    }
}

class Full_House extends Bonus {
    int zweier;
    int dreier;

    public Full_House(String name, int zweier, int dreier, int punkte) {
        super(name, punkte);
        this.zweier = zweier;
        this.dreier = dreier;
    }

    public String get_display_name() {
        return name + " (" + zweier + "er und " + dreier + "er)";
    }
}

class Kniffel extends Bonus {
    public Kniffel(String name, int punkte) {
        super(name, punkte);
    }
}
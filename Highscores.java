import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.BufferedReader;
import java.io.File;

public class Highscores {

    ArrayList<Spieler> spieler = new ArrayList<Spieler>();
    File myObj = new File("highscores.txt");

    public Highscores(ArrayList<Spieler> spieler_game) {
        try {
            myObj.createNewFile();
            BufferedReader br = new BufferedReader(new FileReader("highscores.txt"));

            for (String line; (line = br.readLine()) != null;) {
                String[] player = line.split(",");
                spieler.add(new Highscore_player(player[0], Integer.parseInt(player[1])));
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        this.set_spieler(spieler_game);
        spieler.sort(Comparator.comparing(Spieler::getPunkte).reversed());

        try {
            this.safe_highscores();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Spieler> get_spieler() {
        return spieler;
    }

    public void set_spieler(ArrayList<Spieler> spieler_game) {
        for (Spieler s : spieler_game) {
            this.spieler.add(s);
        }
    }

    public void safe_highscores() throws IOException {
        FileWriter writer = new FileWriter("highscores.txt");

        for (Spieler p : this.spieler) {
            try {
                writer.write(p.getName() + "," + p.getPunkte());
                writer.write(System.getProperty("line.separator"));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        writer.close();
    }

}
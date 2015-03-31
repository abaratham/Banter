package ca.banter;

/**
 * Created by abaratham on 3/29/15.
 */
public class GameEvent {

    public static final int EVENT_KEY = 0;

    private String homeTeam, awayTeam;
    private int homeScore, awayScore;

    public GameEvent(String home, String away, int homes, int aways) {
        this.homeTeam = home;
        this.awayScore = aways;
        this.awayTeam = away;
        this.homeScore = homes;
    }

}

import java.util.Date;

/**
 * Created by abaratham on 3/28/15.
 */
public class Game {

    private String homeTeam, awayTeam;
    private Date date;

    public Game(String home, String away, Date date) {
        this.homeTeam = home;
        this.awayTeam = away;
        this.date = date;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public Date getDate() {
        return date;
    }
}

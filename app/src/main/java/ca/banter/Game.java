package ca.banter;

import java.util.Date;

/**
 * Created by abaratham on 3/28/15.
 */
public class Game {

    public static int EVENT_KEY = 0;
    private String homeTeam, awayTeam;
    private Date date;
    private boolean inProgress;
    private String timeLeft;
    private int id;

    public Game(String home, String away, Date date) {
        this.homeTeam = home;
        this.awayTeam = away;
        this.date = date;
        this.inProgress = false;
        this.timeLeft = "";
        this.id = Game.EVENT_KEY++;
    }

    public Game(String home, String away, String timeLeft) {
        this.homeTeam = home;
        this.awayTeam = away;
        this.inProgress = true;
        this.timeLeft = timeLeft;
        Game.EVENT_KEY++;
    }

    @Override
    public String toString() {
        if (inProgress)
            return awayTeam + " @ " + homeTeam + ": " + timeLeft;
        return awayTeam + " @ " + homeTeam + ": " + date.toString();
    }

    public int getID(){
        return id;
    }

    public int generateID() {
        String s = homeTeam + awayTeam;
        int counter = 0, result = 0;
        for (int i = (homeTeam+awayTeam).length() - 1; i > 0 ;i--) {
            result += (int) (s.charAt(i) * Math.pow(10, counter));
            counter++;
        }
        return result;
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

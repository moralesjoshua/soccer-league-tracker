package soccer;

public class Match {
    private final Team homeTeam;
    private final Team awayTeam;
    private final int homeGoals;
    private final int awayGoals;

    public Match(Team homeTeam, Team awayTeam, int homeGoals, int awayGoals) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }
    public Team getHomeTeam() {
        return homeTeam;
    }
    public Team getAwayTeam() {
        return awayTeam;
    }
    public int getHomeGoals() {
        return homeGoals;
    }
    public int getAwayGoals() {
        return awayGoals;
    }

    public Results getHomeResult() {
        if (homeGoals > awayGoals) {
            return Results.WIN;
        } else if (homeGoals < awayGoals) {
            return Results.LOSS;
        } else {
            return Results.DRAW;
        }
    }
}

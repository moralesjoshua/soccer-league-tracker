package soccer;

public class Team {
    private String name;
    private int wins;
    private int losses;
    private int draws;
    private int goalsFor;
    private int goalsAgainst;

    public Team(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public int getWins() {
        return wins;
    }
    public int getLosses() {
        return losses;
    }
    public int getDraws() {
        return draws;
    }
    public int getGoalsFor() {
        return goalsFor;
    }
    public int getGoalsAgainst() {
        return goalsAgainst;
    }
    public int getPoints() {
        return wins * 3 + draws;
    }
    public int getGoalDifference() {
        return goalsFor - goalsAgainst;
    }
    public void addWin() {
        wins++;
    }
    public void addLoss() {
        losses++;
    }
    public void addDraw() {
        draws++;
    }
    public void addGoalsFor(int goals) {
        goalsFor += goals;
    }
    public void addGoalsAgainst(int goals) {
        goalsAgainst += goals;
    }
    /*public void recordMatch(int goalsFor, int goalsAgainst) {
        this.goalsFor += goalsFor;
        this.goalsAgainst += goalsAgainst;
        if (goalsFor > goalsAgainst) {
            wins++;
        } else if (goalsFor < goalsAgainst) {
            losses++;
        } else {
            draws++;
        }
	*/
}

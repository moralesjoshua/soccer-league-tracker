package soccer;

import java.util.ArrayList;
import java.util.List;

public class League {
    /*
     * League manages the teams and matches in the league. 
     * Adds teams, matches, and calculates the standings based on points, goal difference, and goals scored.
     */
    private List<Team> teams;
    private List<Match> matches;

    public League() {
        teams = new ArrayList<>();
        matches = new ArrayList<>();
    }
    public void addTeam(Team team) {
        teams.add(team);
    }
    public List<Team> getTeams() {
        return teams;
    }
    public List<Match> getMatches() {
        return matches;
    }
    /*
     * When a match is added, this updates the goals for and against for both teams, 
     * as well as their wins, losses, and draws based on the match result.
     */
    public void addMatch(Match match) {
        matches.add(match);
        Team homeTeam = match.getHomeTeam();
        Team awayTeam = match.getAwayTeam();

        int homeGoals = match.getHomeGoals();
        int awayGoals = match.getAwayGoals();

        homeTeam.addGoalsFor(homeGoals);
        homeTeam.addGoalsAgainst(awayGoals);
        awayTeam.addGoalsFor(awayGoals);
        awayTeam.addGoalsAgainst(homeGoals);

        /*
         * Update wins, losses, and draws based on match result using enum
         */
        Results result = match.getHomeResult();
        switch (result) {
            case WIN:
                homeTeam.addWin();
                awayTeam.addLoss();
                break;
            case LOSS:
                homeTeam.addLoss();
                awayTeam.addWin();
                break;
            case DRAW:
                homeTeam.addDraw();
                awayTeam.addDraw();
                break;
        }
    }
    /*
     * This method returns the standings sorted by points, then goal difference, then goals for
     */
    public List<Team> getStandings() {
        List<Team> standings = new ArrayList<>(teams);

        standings.sort((t1, t2) -> {
            if (t2.getPoints() != t1.getPoints()) {
                return t2.getPoints() - t1.getPoints();
            }

            if (t2.getGoalDifference() != t1.getGoalDifference()) {
                return t2.getGoalDifference() - t1.getGoalDifference();
            }

            return t2.getGoalsFor() - t1.getGoalsFor();
    });

    return standings;
}
}
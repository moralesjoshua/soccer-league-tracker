package soccer;

import java.util.ArrayList;
import java.util.List;

public class League {
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
        
        if (homeGoals > awayGoals) {
            homeTeam.addWin();
            awayTeam.addLoss();
        } else if (homeGoals < awayGoals) {
            homeTeam.addLoss();
            awayTeam.addWin();
        } else {
            homeTeam.addDraw();
            awayTeam.addDraw();
        }
    }
    
}

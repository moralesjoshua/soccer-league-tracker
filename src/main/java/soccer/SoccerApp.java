package soccer;

public class SoccerApp {
    public static void main(String[] args) {
        League league = new League();
        
        Team arsenal = new Team("Arsenal");
        Team newcastle = new Team("Newcastle");

        league.addTeam(arsenal);
        league.addTeam(newcastle);
        
        Match match1 = new Match(arsenal, newcastle, 2, 1);
        league.addMatch(match1);

        System.out.println(arsenal.getName() + ": " + arsenal.getPoints() + " points");
        System.out.println(newcastle.getName() + ": " + newcastle.getPoints() + " points");

        System.out.println(arsenal.getName() + " Wins: " + arsenal.getWins());
        System.out.println(arsenal.getName() + " Draws: " + arsenal.getDraws());
        System.out.println(arsenal.getName() + " Losses: " + arsenal.getLosses());
        System.out.println(arsenal.getName() + " GF: " + arsenal.getGoalsFor());
        System.out.println(arsenal.getName() + " GA: " + arsenal.getGoalsAgainst());
        System.out.println(arsenal.getName() + " GD: " + arsenal.getGoalDifference());

        System.out.println(newcastle.getName() + " Wins: " + newcastle.getWins());
        System.out.println(newcastle.getName() + " Draws: " + newcastle.getDraws());
        System.out.println(newcastle.getName() + " Losses: " + newcastle.getLosses());
        System.out.println(newcastle.getName() + " GF: " + newcastle.getGoalsFor());
        System.out.println(newcastle.getName() + " GA: " + newcastle.getGoalsAgainst());
        System.out.println(newcastle.getName() + " GD: " + newcastle.getGoalDifference());
    
    }
}

package soccer;

public class SoccerApp {
    public static void main(String[] args) {

        Team arsenal = new Team("Arsenal");
        Team newcastle = new Team("Newcastle");
        Team liverpool = new Team("Liverpool");

        League league = new League();
        league.addTeam(arsenal);
        league.addTeam(newcastle);
        league.addTeam(liverpool);

        // Adding matches
        league.addMatch(new Match(arsenal, newcastle, 2, 0));  // Arsenal win
        league.addMatch(new Match(liverpool, arsenal, 3, 1));  // Liverpool win

        // Both teams have 3 points but Liverpool has better goal difference

        System.out.println("=== Standings ===");

        int position = 1;
        for (Team t : league.getStandings()) {
            System.out.println(position + ". " + t);
            position++;
        }
    }
}
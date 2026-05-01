package soccer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SoccerTest {
    /*
     * Hard coded values since point allocation is based on specific numbers
     * This test verifies that when a match is added to the league, the points, wins, losses, and goal difference for both teams are updated correctly.
     */
    @Test
    public void testWinLossUpdates() {
        Team home = new Team("Home");
        Team away = new Team("Away");

        League league = new League();
        league.addTeam(home);
        league.addTeam(away);
        league.addMatch(new Match(home, away, 3, 1));

        assertEquals(3, home.getPoints());
        assertEquals(1, home.getWins());
        assertEquals(2, home.getGoalDifference());

        assertEquals(0, away.getPoints());
        assertEquals(1, away.getLosses());
        assertEquals(-2, away.getGoalDifference());
    }

    /*
     * This test verifies that when a match ends in a draw, both teams receive 1 point, 
     * their draw count increases by 1, and their goal difference remains unchanged.
     */
    @Test
    public void testDrawUpdates() {
        Team home = new Team("Home");
        Team away = new Team("Away");

        League league = new League();
        league.addTeam(home);
        league.addTeam(away);
        league.addMatch(new Match(home, away, 2, 2));

        assertEquals(1, home.getPoints());
        assertEquals(1, home.getDraws());
        assertEquals(0, home.getGoalDifference());
        assertEquals(0, home.getWins());
        assertEquals(0, home.getLosses());
        assertEquals(2, home.getGoalsFor());
        assertEquals(2, home.getGoalsAgainst());

        assertEquals(1, away.getPoints());
        assertEquals(1, away.getDraws());
        assertEquals(0, away.getGoalDifference());
        assertEquals(0, away.getWins());
        assertEquals(0, away.getLosses());
        assertEquals(2, away.getGoalsFor());
        assertEquals(2, away.getGoalsAgainst());
    }

    /*
     * Test to show that the standings are correctly sorted based on points, etc.
     */
    @Test
    public void testStandings() {
        Team teamA = new Team("Team A");
        Team teamB = new Team("Team B");
        Team teamC = new Team("Team C");

        League league = new League();
        league.addTeam(teamA);
        league.addTeam(teamB);
        league.addTeam(teamC);
        /*
         * Team A wins against Team B
         * Team B draws against Team C
         * Team A should be in first
         */
        league.addMatch(new Match(teamA, teamB, 1, 0)); 
        league.addMatch(new Match(teamB, teamC, 2, 2)); 

        assertEquals("Team A", league.getStandings().get(0).getName());
        assertEquals("Team C", league.getStandings().get(1).getName());
        assertEquals("Team B", league.getStandings().get(2).getName());
    }

    /*
     * This test verifies that the getHomeResult method of the Match class correctly identifies whether the home team won, 
     * lost, or drew the match based on the goals scored.
     */
    @Test
    public void testMatchResultForHomeTeam() {
        Team home = new Team("Home");
        Team away = new Team("Away");

        Match homeWin = new Match(home, away, 2, 0);
        Match homeLoss = new Match(home, away, 0, 2);
        Match draw = new Match(home, away, 1, 1);

        assertEquals(Results.WIN, homeWin.getHomeResult());
        assertEquals(Results.LOSS, homeLoss.getHomeResult());
        assertEquals(Results.DRAW, draw.getHomeResult());
}
}   

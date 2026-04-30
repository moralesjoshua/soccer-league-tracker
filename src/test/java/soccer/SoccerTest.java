// Premier
package soccer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SoccerTest {
    @Test
    public void testMatchUpdatesTeamStats() {
        League league = new League();
        Team arsenal = new Team("Arsenal");
        Team newcastle = new Team("Newcastle");
        league.addTeam(arsenal);
        league.addTeam(newcastle);

        Match match = new Match(arsenal, newcastle, 2, 1);
        league.addMatch(match);

        assertEquals(3, arsenal.getPoints());
        assertEquals(1, arsenal.getWins());
        assertEquals(2, arsenal.getGoalsFor());
        assertEquals(1, arsenal.getGoalsAgainst());
        assertEquals(1, arsenal.getGoalDifference());

        assertEquals(0, newcastle.getPoints());
        assertEquals(1, newcastle.getLosses());
        assertEquals(1, newcastle.getGoalsFor());
        assertEquals(2, newcastle.getGoalsAgainst());
        assertEquals(-1, newcastle.getGoalDifference());
    }
}
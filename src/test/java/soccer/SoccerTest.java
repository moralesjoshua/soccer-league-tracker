package soccer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SoccerTest {
    @Test
    public void testMatchUpdatesTeamStats() {
        League league = new League();
        Team arsenal = new Team("Arsenal");
        Team newcastle = new Team("Newcastle");
        Team liverpool = new Team("Liverpool");
        league.addTeam(arsenal);
        league.addTeam(newcastle);
        league.addTeam(liverpool);

        Match match = new Match(arsenal, newcastle, 2, 0);
        Match match2 = new Match(liverpool, arsenal, 3, 1);
        league.addMatch(match);
        league.addMatch(match2);

        List<Team> standings = league.getStandings();
        assertEquals("Liverpool", standings.get(0).getName());
        assertEquals("Arsenal", standings.get(1).getName());
        assertEquals("Newcastle", standings.get(2).getName());

    }
}
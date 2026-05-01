package soccer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LeagueFileManager {
    private static final String FILE_NAME = "league_data.txt";

    /*
     * The league data is saved in a simple text format. 
     * Each team is saved as "TEAM,TeamName" 
     * and each match is saved as "MATCH,HomeTeam,AwayTeam,HomeGoals,AwayGoals".
     */
    public static void saveLeague(League league) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            
            for (Team team : league.getTeams()) {
                writer.write("TEAM," + team.getName());
                writer.newLine();
            }

            for (Match match : league.getMatches()) {
                writer.write("MATCH," + match.getHomeTeam().getName() + "," +
                             match.getAwayTeam().getName() + "," +
                             match.getHomeGoals() + "," +
                             match.getAwayGoals());
                writer.newLine();
            }
        }
    }

    /*
     * When loading the league, we read the file line by line. 
     * For each line, we check if it starts with "TEAM" or "MATCH".
     */
    public static League loadLeague() throws IOException {
        League league = new League();

        Map <String, Team> teamMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("TEAM")) {
                    Team team = new Team(parts[1]);
                    league.addTeam(team);
                    teamMap.put(parts[1], team);
                } else if (parts[0].equals("MATCH")) {
                    Team homeTeam = teamMap.get(parts[1]);
                    Team awayTeam = teamMap.get(parts[2]);
                    int homeGoals = Integer.parseInt(parts[3]);
                    int awayGoals = Integer.parseInt(parts[4]);
                    league.addMatch(new Match(homeTeam, awayTeam, homeGoals, awayGoals));
                }
            }
        }
        return league;
    }
}

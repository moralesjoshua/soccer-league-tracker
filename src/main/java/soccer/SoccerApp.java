package soccer;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SoccerApp extends Application {
    private League league;

    // The league is loaded when the application starts. If loading fails, a new league with is placed.
    @Override
    public void start(Stage stage) {
        if (league == null) {
            try { 
                league = LeagueFileManager.loadLeague();
            } catch (IOException e) {
                league = startingLeague();
            }
        }
            standingsScreen(stage);
        }
        

    // The standings screen displays the league table and provides buttons to add matches, reset the league, or exit the application.
    private void standingsScreen(Stage stage) {
        Label title = new Label("Soccer League Tracker");

        // Style title
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        HBox titleBox = new HBox(title);
        titleBox.setAlignment(Pos.CENTER);

        // Create table and columns
        TableView<Team> table = new TableView<>();

        TableColumn<Team, String> nameColumn = new TableColumn<>("Team");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Team, Integer> pointsColumn = new TableColumn<>("Pts");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));

        TableColumn<Team, Integer> winsColumn = new TableColumn<>("W");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));

        TableColumn<Team, Integer> drawsColumn = new TableColumn<>("D");
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));

        TableColumn<Team, Integer> lossesColumn = new TableColumn<>("L");
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));

        TableColumn<Team, Integer> gfColumn = new TableColumn<>("GF");
        gfColumn.setCellValueFactory(new PropertyValueFactory<>("goalsFor"));

        TableColumn<Team, Integer> gaColumn = new TableColumn<>("GA");
        gaColumn.setCellValueFactory(new PropertyValueFactory<>("goalsAgainst"));

        TableColumn<Team, Integer> gdColumn = new TableColumn<>("GD");
        gdColumn.setCellValueFactory(new PropertyValueFactory<>("goalDifference"));

        table.getColumns().addAll(nameColumn, pointsColumn, winsColumn, drawsColumn, lossesColumn, gfColumn, gaColumn, gdColumn);

        // Set table height
        table.setPrefHeight(250);
        table.setMaxHeight(320);

        // Style bottom 3 teams with red background for relegation zone

        table.setRowFactory(tv -> new TableRow<Team>() {
        @Override
        public void updateItem(Team team, boolean empty) {
            super.updateItem(team, empty);
            if (team == null || empty) {
                setStyle("");
            } else {
                int index = getIndex();
                int totalTeams = table.getItems().size();
                if (index >= totalTeams - 3) {
                    setStyle("-fx-background-color: #d26a6aff;");
                } else {
                    setStyle("");
                }
            }
        }
        });                                    
            

        table.setItems(FXCollections.observableArrayList(league.getStandings()));

        // Create add match button
        Button addMatchButton = new Button("Add Match");
        addMatchButton.setOnAction(e -> addMatchScreen(stage, league));

        // Create reset button
        Button resetButton = new Button("Reset League");
        resetButton.setOnAction(e -> {
            league = startingLeague();
            try {
                LeagueFileManager.saveLeague(league);
            } catch (IOException ex) {
            }
            standingsScreen(stage);
        });

        // Create exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> stage.close());

        VBox root = new VBox(10);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(addMatchButton, resetButton, exitButton);
        root.getChildren().addAll(titleBox, table, buttonBox);

        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("Soccer League Tracker");
        stage.setScene(scene);
        stage.show();
        
    }

    /*
     * The add match screen allows the user to select two teams, enter the goals scored by each team, and submit the match. 
     * It includes validation to ensure that both teams are selected, they are not the same team, and that the goals entered are valid non-negative integers. 
     * After submitting a match, the league data is saved and a confirmation screen is shown.
     */
    private void addMatchScreen(Stage stage, League league) {
        Label titleAddMatch = new Label("Add Match");
        Label errorLabel = new Label();

        ComboBox<Team> homeTeamBox = new ComboBox<>();
        homeTeamBox.setItems(FXCollections.observableArrayList(league.getStandings()));

        ComboBox<Team> awayTeamBox = new ComboBox<>();
        awayTeamBox.setItems(FXCollections.observableArrayList(league.getStandings()));

        Label homeLabel = new Label("Home Team:");
        Label awayLabel = new Label("Away Team:");

        TextField homeGoalsField = new TextField();
        homeGoalsField.setPromptText("Home Goals");

        TextField awayGoalsField = new TextField();
        awayGoalsField.setPromptText("Away Goals");

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            Team homeTeam = homeTeamBox.getValue();
            Team awayTeam = awayTeamBox.getValue();
            
            if (homeTeam == null || awayTeam == null) {
                errorLabel.setText("Select both teams.");
                return;
            }
            if (homeTeam == awayTeam) {
                errorLabel.setText("One team cannot play itself.");
                return;
            }
            try {
                int homeGoals = Integer.parseInt(homeGoalsField.getText());
                int awayGoals = Integer.parseInt(awayGoalsField.getText());
                if (homeGoals < 0 || awayGoals < 0) {
                    errorLabel.setText("Goals cannot be negative.");
                    return;
                }
                league.addMatch(new Match(homeTeam, awayTeam, homeGoals, awayGoals));
                try {
                    LeagueFileManager.saveLeague(league);
                } catch (IOException ex) {
                    errorLabel.setText("Error saving league data.");
                }
                confirmationScreen(stage, league);
            } catch (NumberFormatException ex) {
                errorLabel.setText("Enter valid numbers for goals.");
            }
        });

        Button backButton = new Button("Back");

        HBox titleAddMatchBox = new HBox(titleAddMatch);
        titleAddMatchBox.setAlignment(Pos.CENTER);
        titleAddMatchBox.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox homeBox = new HBox(10);
        homeBox.setAlignment(Pos.CENTER);
        homeBox.getChildren().addAll(homeLabel, homeTeamBox);

        HBox awayBox = new HBox(10);
        awayBox.setAlignment(Pos.CENTER);
        awayBox.getChildren().addAll(awayLabel, awayTeamBox);
        
        homeGoalsField.setMaxWidth(100);
        awayGoalsField.setMaxWidth(100);

        VBox formBox = new VBox(10);
        formBox.setAlignment(Pos.CENTER);
        formBox.getChildren().addAll( 
            homeBox,
            awayBox,
            homeGoalsField, 
            awayGoalsField, 
            submitButton, 
            backButton, 
            errorLabel);
            
        VBox root = new VBox(10);
        root.getChildren().addAll(titleAddMatchBox, formBox);
        Scene scene = new Scene(root, 300, 300);
        stage.setScene(scene);
        backButton.setOnAction(e -> standingsScreen(stage));
    }

    // When a match is added, this screen confirms the addition and provides a button to return to the standings.
    private void confirmationScreen(Stage stage, League league) {
        Label confirmationLabel = new Label("Match added successfully!");
        Button backButton = new Button("Back to Standings");
        backButton.setOnAction(e -> standingsScreen(stage));

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(confirmationLabel, backButton);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
    }

    // Premier League teams for the 2025-2026

    private League startingLeague() {
        Team arsenal = new Team("Arsenal");
        Team astonVilla = new Team("Aston Villa");
        Team bournemouth = new Team("Bournemouth");
        Team brentford = new Team("Brentford");
        Team brighton = new Team("Brighton");
        Team burnley = new Team("Burnley");
        Team chelsea = new Team("Chelsea");
        Team crystalPalace = new Team("Crystal Palace");
        Team everton = new Team("Everton");
        Team fulham = new Team("Fulham");
        Team leeds = new Team("Leeds");
        Team liverpool = new Team("Liverpool");
        Team manCity = new Team("Manchester City");
        Team manUnited = new Team("Manchester United");
        Team newcastle = new Team("Newcastle");
        Team nottinghamForest = new Team("Nottingham Forest");
        Team sunderland = new Team("Sunderland");
        Team tottenham = new Team("Tottenham Hotspur");
        Team westHam = new Team("West Ham");
        
        Team wolves = new Team("Wolves");
        

        League newLeague = new League();
        newLeague.addTeam(arsenal);
        newLeague.addTeam(astonVilla);
        newLeague.addTeam(bournemouth);
        newLeague.addTeam(brentford);
        newLeague.addTeam(brighton);
        newLeague.addTeam(burnley);
        newLeague.addTeam(chelsea);
        newLeague.addTeam(crystalPalace);
        newLeague.addTeam(everton);
        newLeague.addTeam(fulham);
        newLeague.addTeam(leeds);
        newLeague.addTeam(liverpool);
        newLeague.addTeam(manCity);
        newLeague.addTeam(manUnited);
        newLeague.addTeam(newcastle);
        newLeague.addTeam(nottinghamForest);
        newLeague.addTeam(sunderland);
        newLeague.addTeam(tottenham);
        newLeague.addTeam(westHam);
        newLeague.addTeam(wolves);
        

        return newLeague;
    }

    public static void main(String[] args) {
        launch(args);
    }

}
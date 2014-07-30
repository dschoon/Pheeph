package controllers;

import models.Game;
import models.Team;
import models.TeamStats;
import services.TeamStatsService;

import java.util.ArrayList;
import java.util.List;

/**
 * User: daniel.schoonmaker
 * Date: 7/28/14
 * Time: 7:32 PM
 */
public class GameController extends CRUD {

    public static List<Game> getAllGames() {
        return Game.findAll();
    }

    public static List<Game> getAllGamesByWeek(long week) {
        return Game.find("SELECT g FROM Game g WHERE g.week = ?", week).fetch();
    }

    public static List<List<Game>> getAllGamesForAllWeeks(int numOfWeeks) {
        List<List<Game>> allWeeks = new ArrayList<List<Game>>();

        for(int i = 0; i <= numOfWeeks; i++) {
            List<Game> allGames = getAllGamesByWeek(i+1);
            allWeeks.add(i, allGames);
        }

        return allWeeks;
    }

    public static List<Game> getGamesByTeamId(long team_id) {
        return Game.getGamesByTeamId(team_id);
    }

    public static List<Game> getHomeGamesByTeamId(long team_id) {
        return Game.getHomeGamesByTeamId(team_id);
    }

    public static List<Game> getAwayGamesByTeamId(long team_id) {
        return Game.getAwayGamesByTeamId(team_id);
    }

    public static void addGame(long season_id, long home_team_id, long away_team_id, long home_score, long away_score, long match_date) {
        Game game = new Game(home_team_id, away_team_id, true);
        game.setHomeScore(home_score);
        game.setAwayScore(away_score);
        game.setMatchDate(match_date);
        game.save();

        TeamStatsService.updateStats(season_id, home_team_id, away_team_id, home_score, away_score);
    }

}

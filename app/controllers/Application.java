package controllers;

import controllers.securesocial.SecureSocial;
import models.Game;
import models.Season;
import models.Team;
import models.TeamStats;
import models.User;
import play.Logger;
import play.data.validation.Required;
import play.mvc.*;
import securesocial.provider.SocialUser;
import services.TeamStatsService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@With(SecureSocial.class)
public class Application extends Controller {

    public static long season_id;

    public static void home() {
        String title = "Lithium Champions League";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<Season> seasons = Season.getAllSeasons();
        season_id = seasons.size();

        renderArgs.put("userTeam", TeamController.getTeamByUserId(userModel.id, season_id));

        render(title, user, userModel, seasons);
    }

    public static void standings(@Required long season_id) {
        String title = "League Standings";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<Team> teams = Team.getAllTeamsForSeason(season_id);
        List<TeamStats> teamStats = TeamStats.getByRank(season_id);

        renderArgs.put("userTeam", TeamController.getTeamByUserId(userModel.id, season_id));
        renderArgs.put("season_id", season_id);

        render(title, user, userModel, teams, teamStats);
    }

    public static void schedule(@Required long season_id) {
        String title = "League Schedule";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();

        Season season = Season.getSeasonById(season_id);
        Logger.error("Season ID: " + season_id);
        List<List<Game>> weeks = GameController.getAllGamesForAllWeeks(season.getWeekCount()-1, season_id);
        Logger.error("Week Size: " + weeks.size());

        renderArgs.put("userTeam",TeamController.getTeamByUserId(userModel.id, season_id));
        renderArgs.put("season_id", season_id);

        render(title, user, userModel, weeks);
    }

    public static void teamSchedule(@Required long team_id, @Required long season_id) {
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();

        Team userTeam = TeamController.getTeamByUserId(userModel.id, season_id);
        Team currentTeam = Team.getTeamById(team_id, season_id);

        List<Game> games = GameController.getGamesByTeamId(team_id);
        renderArgs.put("season_id", season_id);

        String title = currentTeam.getName();

        render(title, user, userModel, userTeam, games, currentTeam);
    }

    public static void gameResult(@Required long game_id, @Required long season_id) {
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();

        Team userTeam = TeamController.getTeamByUserId(userModel.id, season_id);

        Game currentGame = Game.getGameById(game_id);
        Team homeTeam = Team.getTeamById(currentGame.home_team_id, season_id);
        Team awayTeam = Team.getTeamById(currentGame.away_team_id, season_id);

        User homeUser = User.getUserById(homeTeam.user_id);
        User awayUser = User.getUserById(awayTeam.user_id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date gameDate = new Date(currentGame.getMatchDate());
        String gameTime = df.format(gameDate);
        renderArgs.put("season_id", season_id);

        String title = homeTeam.name +" vs. " +awayTeam.name;

        render(title, user, userModel, userTeam, currentGame, homeTeam, awayTeam, homeUser, awayUser, gameTime);
    }

    public static void setGameResult(@Required long game_id, @Required long home_score, @Required long away_score, @Required long season_id) {
        Game currentGame = Game.getGameById(game_id);

        long seasonId = Team.getTeamById(currentGame.home_team_id, season_id).season_id;

        currentGame.setHomeScore(home_score);
        currentGame.setAwayScore(away_score);
        currentGame.setMatchDate(new Date().getTime());
        currentGame.setPlayed(true);
        currentGame.save();

        TeamStatsService.updateStats(seasonId, currentGame.home_team_id, currentGame.away_team_id,
                                                currentGame.home_score, currentGame.away_score);

        renderJSON("[{ \"result\":\"SUCCESS\" }]");
    }






    public static void FourOhFour() {
        render("errors/404.html");
    }
}
package controllers;

import controllers.securesocial.SecureSocial;
import models.Game;
import models.Team;
import models.TeamStats;
import models.User;
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

    public static void home() {
        String title = "Lithium Champions League";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<Team> teams = Team.findAll();
        List<TeamStats> teamStats = TeamStats.getByRank();

        renderArgs.put("userTeam", TeamController.getTeamByUserId(userModel.id));

        render(title, user, userModel, teams, teamStats);
    }

    public static void schedule() {
        String title = "League Schedule";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<List<Game>> weeks = GameController.getAllGamesForAllWeeks(16);

        renderArgs.put("userTeam",TeamController.getTeamByUserId(userModel.id));

        render(title, user, userModel, weeks);
    }

    public static void teamSchedule(@Required long team_id) {
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();

        Team userTeam = TeamController.getTeamByUserId(userModel.id);
        Team currentTeam = Team.getTeamById(team_id);

        List<Game> games = GameController.getGamesByTeamId(team_id);

        String title = currentTeam.getName();

        render(title, user, userModel, userTeam, games, currentTeam);
    }

    public static void gameResult(@Required long game_id) {
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();

        Team userTeam = TeamController.getTeamByUserId(userModel.id);

        Game currentGame = Game.getGameById(game_id);
        Team homeTeam = Team.getTeamById(currentGame.home_team_id);
        Team awayTeam = Team.getTeamById(currentGame.away_team_id);

        User homeUser = User.getUserById(homeTeam.user_id);
        User awayUser = User.getUserById(awayTeam.user_id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date gameDate = new Date(currentGame.getMatchDate());
        String gameTime = df.format(gameDate);

        String title = homeTeam.name +" vs. " +awayTeam.name;

        render(title, user, userModel, userTeam, currentGame, homeTeam, awayTeam, homeUser, awayUser, gameTime);
    }

    public static void setGameResult(@Required long game_id, @Required long home_score, @Required long away_score) {
        Game currentGame = Game.getGameById(game_id);

        long season_id = Team.getTeamById(currentGame.home_team_id).season_id;

        currentGame.setHomeScore(home_score);
        currentGame.setAwayScore(away_score);
        currentGame.setMatchDate(new Date().getTime());
        currentGame.setPlayed(true);
        currentGame.save();

        TeamStatsService.updateStats(season_id, currentGame.home_team_id, currentGame.away_team_id,
                                                currentGame.home_score, currentGame.away_score);

        renderJSON("[{ \"result\":\"SUCCESS\" }]");
    }






    public static void FourOhFour() {
        render("errors/404.html");
    }
}
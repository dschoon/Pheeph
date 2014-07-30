package controllers;

import controllers.securesocial.SecureSocial;
import models.Game;
import models.Team;
import models.TeamStats;
import models.User;
import play.Logger;
import play.mvc.*;
import securesocial.provider.SocialUser;

import java.util.List;

@With(SecureSocial.class)
public class Application extends Controller {

    public static void home() {
        String title = "Standings";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<Team> teams = Team.findAll();
        List<TeamStats> teamStats = TeamStats.getByRank();

        renderArgs.put("userTeam", TeamController.getTeamByUserId(userModel.id));

        render(title, user, userModel, teams, teamStats);
    }

    public static void schedule() {
        String title = "Schedule";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<List<Game>> weeks = GameController.getAllGamesForAllWeeks(16);

        renderArgs.put("userTeam",TeamController.getTeamByUserId(userModel.id));

        render(title, user, userModel, weeks);
    }

    public static void userSchedule(long team_id) {
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();

        Team userTeam = TeamController.getTeamByUserId(userModel.id);
        Team currentTeam = Team.getTeamById(team_id);

        List<Game> games = GameController.getGamesByTeamId(team_id);

        String title = currentTeam.getName();

        render(title, user, userModel, userTeam, games, currentTeam);
    }
}
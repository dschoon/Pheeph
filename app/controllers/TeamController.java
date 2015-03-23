package controllers;

import models.Team;
import services.TeamStatsService;

import java.util.List;

public class TeamController extends CRUD {

    public static Team getTeamByUserId(long user_id, long season_id) {
        return Team.find("select t from Team t where t.user_id = ? and t.season_id = ?", user_id, season_id).first();
    }

    public static List<Team> getTeamsBySeasonId(long season_id) {
        return Team.find("select t from Team t where t.season_id = ?", season_id).fetch();
    }
}

package controllers;

import models.Team;
import services.TeamStatsService;

/**
 * User: daniel.schoonmaker
 * Date: 7/28/14
 * Time: 7:32 PM
 */
public class TeamController extends CRUD {

    public static Team getTeamByUserId(long user_id) {
        return Team.find("select t from Team t where t.user_id = ?", user_id).first();
    }

}

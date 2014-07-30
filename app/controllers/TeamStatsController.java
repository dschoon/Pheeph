package controllers;

import models.TeamStats;
import services.TeamStatsService;

/**
 * User: daniel.schoonmaker
 * Date: 7/28/14
 * Time: 7:32 PM
 */
public class TeamStatsController extends CRUD {

    public static TeamStats getTeamStats(long season_id, long team_id) {
        return TeamStatsService.getTeamStatsById(season_id, team_id);
    }
}

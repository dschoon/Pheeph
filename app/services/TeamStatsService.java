package services;

import models.Game;
import models.Team;
import models.TeamStats;

public class TeamStatsService {

    public static void updateStats(long season_id, long home_team_id, long away_team_id, long home_score, long away_score) {
        Team homeTeam = Team.findById(home_team_id);
        Team awayTeam = Team.findById(away_team_id);

        TeamStats homeTeamStats = homeTeam.getTeamStatsByTeamAndSeasonId(home_team_id, season_id);
        TeamStats awayTeamStats = awayTeam.getTeamStatsByTeamAndSeasonId(away_team_id, season_id);

        if(home_score > away_score) {
            homeTeamStats.addWin();
            homeTeamStats.addPoints(3);
            awayTeamStats.addLoss();
        } else if(home_score < away_score){
            homeTeamStats.addLoss();
            awayTeamStats.addWin();
            awayTeamStats.addPoints(3);
        } else {
            homeTeamStats.addDraw();
            homeTeamStats.addPoints(1);
            awayTeamStats.addDraw();
            awayTeamStats.addPoints(1);
        }

        //Adding the goals scored to the team's "Goals For" stat
        homeTeamStats.setGoalsFor(homeTeamStats.getGoalsFor() + home_score);
        awayTeamStats.setGoalsFor(awayTeamStats.getGoalsFor() + away_score);

        //Adding the oppositions goals to the team's "Goals Against" stat
        homeTeamStats.setGoalsAgainst(homeTeamStats.getGoalsAgainst() + away_score);
        awayTeamStats.setGoalsAgainst(awayTeamStats.getGoalsAgainst() + home_score);

        //Getting Home Team Counts
        int team1HomeGameCount = Game.find("COUNT(g) FROM Game g WHERE g.home_user_id = ?", home_team_id).first();
        int team1AwayGameCount = Game.find("COUNT(g) FROM Game g WHERE g.away_user_id = ?", home_team_id).first();
        int team1TotalGameCount = team1HomeGameCount + team1AwayGameCount;

        //Getting Away Team Counts
        int team2HomeGameCount = Game.find("COUNT(g) FROM Game g WHERE g.home_user_id = ?", away_team_id).first();
        int team2AwayGameCount = Game.find("COUNT(g) FROM Game g WHERE g.away_user_id = ?", away_team_id).first();
        int team2TotalGameCount = team2HomeGameCount + team2AwayGameCount;

        //Calculate the team's new "Points Per Match"
        homeTeamStats.setPointsPerMatch(homeTeamStats.getPoints() / team1TotalGameCount);
        awayTeamStats.setPointsPerMatch(awayTeamStats.getPoints() / team2TotalGameCount);

        //Calculate the team's new "Goal Differential"
        homeTeamStats.setGoalDifferential(homeTeamStats.getGoalsFor() - homeTeamStats.getGoalsAgainst());
        awayTeamStats.setGoalDifferential(awayTeamStats.getGoalsFor() - awayTeamStats.getGoalsAgainst());

        homeTeamStats.save();
        awayTeamStats.save();
    }

    public static TeamStats getTeamStatsById(long season_id, long team_id) {
        Team team = Team.findById(team_id);

        return team.getTeamStatsByTeamAndSeasonId(team_id, season_id);
    }
}

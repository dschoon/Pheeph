package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * User: daniel.schoonmaker
 * Date: 7/28/14
 * Time: 6:28 PM
 */
@Entity
@Table(name = "team_stats", uniqueConstraints = {@UniqueConstraint(columnNames = {"team_id","season_id"})})
public class TeamStats extends Model {

    @Required
    @Column(name = "team_id")
    public long team_id;

    @Required
    @Column(name = "season_id")
    public long season_id;

    @Column(name = "win")
    public long wins;

    @Column(name = "loss")
    public long losses;

    @Column(name = "draw")
    public long draws;

    @Column(name = "points")
    public long points;

    @Column(name = "points_per_match")
    public long points_per_match;

    @Column(name = "goals_for")
    public long goals_for;

    @Column(name = "goals_against")
    public long goals_against;

    @Column(name = "goal_differential")
    public long goal_differential;

    public TeamStats(long team_id, long season_id) {
        this.team_id = team_id;
        this.season_id = season_id;
    }

    public void setSeasonId(long season_id) {
        this.season_id = season_id;
    }

    public long getSeasonId() {
        return season_id;
    }

    // ---------------------

    public void setWins(long wins) {
        this.wins = wins;
    }

    public long getWins() {
        return wins;
    }


    public void setLosses(long losses) {
        this.losses = losses;
    }

    public long getLosses() {
        return losses;
    }


    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getDraws() {
        return draws;
    }


    public void setPoints(long points) {
        this.points = points;
    }

    public void addPoints(long points) {
        this.points = this.points + points;
    }

    public long getPoints() {
        return points;
    }

    public void setPointsPerMatch(long points_per_match) {
        this.points_per_match = points_per_match;
    }

    public long getPointsPerMatch() {
        return points_per_match;
    }


    public void setGoalsFor(long goals_for) {
        this.goals_for = goals_for;
    }

    public long getGoalsFor() {
        return goals_for;
    }

    public void setGoalsAgainst(long goals_against) {
        this.goals_against = goals_against;
    }

    public long getGoalsAgainst() {
        return goals_against;
    }

    public void setGoalDifferential(long goal_differential) {
        this.goal_differential = goal_differential;
    }

    public long getGoalDifferential() {
        return goal_differential;
    }


    public static List<TeamStats> getByRank() {
        return TeamStats.find("SELECT ts FROM TeamStats ts ORDER BY ts.points DESC").fetch();
    }

}

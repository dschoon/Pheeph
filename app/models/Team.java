package models;

import controllers.TeamController;
import play.data.validation.Required;
import play.db.jpa.Model;
import services.TeamStatsService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * User: daniel.schoonmaker
 * Date: 7/28/14
 * Time: 6:28 PM
 */
@Entity
@Table(name = "team", uniqueConstraints = {@UniqueConstraint(columnNames = {"id","user_id"})})
public class Team extends Model {

    @Required
    @Column(name = "user_id")
    public long user_id;

    @Required
    @Column(name = "season_id")
    public long season_id;

    @Required
    @Column(name = "name")
    public String name;

    @Column(name = "logo_url")
    public String logo_url;

    public TeamStats team_stats;

    public Team(long user_id, long season_id, String name, String logo_url) {
        this.user_id = user_id;
        this.name = name;
        this.logo_url = logo_url;
        TeamStats team_stats = new TeamStats(id, season_id);
        team_stats.save();
    }

    public void setUserId(long user_id) {
        this.user_id = user_id;
    }

    public long getUserId() {
        return user_id;
    }

    public void setTeamStats(TeamStats team_stats) {
        this.team_stats = team_stats;
    }

    public TeamStats getTeamStats() {
        return team_stats;
    }

    public TeamStats getTeamStatsByTeamAndSeasonId(long team_id, long season_id) {
        return TeamStats.find("SELECT ts FROM team_stats ts WHERE ts.team_id = ? and ts.season_id = ?", team_id, season_id).first();
    }

    public static Team getTeamById(long team_id) {
        return Team.find("SELECT t FROM Team t WHERE t.id = ?", team_id).first();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLogoUrl(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getLogoUrl() {
        return logo_url;
    }

    public User getUserOfTeam(long user_id) {
        return User.find("SELECT u FROM User u WHERE u.id = ?", user_id).first();
    }
}

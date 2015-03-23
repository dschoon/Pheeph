package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Entity
@Table(name = "season", uniqueConstraints = {@UniqueConstraint(columnNames = {"id","season_id"})})
public class Season extends Model {

    @Required
    @Column(name = "season_id")
    public long season_id;

    @Required
    @Column(name = "team_count")
    public long team_count;

    @Required
    @Column(name = "week_count")
    public long week_count;

    @Required
    @Column(name = "name")
    public String name;

    public Season(long season_id, long team_count, String name) {
        this.season_id = season_id;
        this.team_count = team_count;
        this.name = name;
    }

    public void setSeasonId(long season_id) {
        this.season_id = season_id;
    }

    public long getSeasonId() {
        return season_id;
    }

    public void setTeamCount(long team_count) {
        this.team_count = team_count;
    }

    public long getTeamCount() {
        return team_count;
    }

    public void setWeekCount(long week_count) {
        this.week_count = week_count;
    }

    public long getWeekCount() {
        return week_count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static List<Season> getAllSeasons() {
        return Season.find("SELECT s FROM Season s").fetch();
    }
}

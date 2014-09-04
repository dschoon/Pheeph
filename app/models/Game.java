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
@Table(name = "game", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Game extends Model {

    @Required
    @Column(name = "home_team_id")
    public long home_team_id;

    @Required
    @Column(name = "away_team_id")
    public long away_team_id;

    @Column(name = "home_score")
    public long home_score;

    @Column(name = "away_score")
    public long away_score;

    @Column(name = "match_date")
    public long match_date;

    @Column(name = "week")
    public long week;

    @Required
    @Column(name = "played")
    public boolean played;

    public Game(long home_team_id, long away_team_id, boolean played) {
        this.home_team_id = home_team_id;
        this.away_team_id = away_team_id;
        this.played = played;
    }

    public void setHomeTeamId(long home_team_id) {
        this.home_team_id = home_team_id;
    }

    public long getHomeTeamId() {
        return home_team_id;
    }

    public void setAwayTeamId(long away_team_id) {
        this.away_team_id = away_team_id;
    }

    public long getAwayTeamId() {
        return away_team_id;
    }

    public void setHomeScore(long home_score) {
        this.home_score = home_score;
    }

    public long getHomeScore() {
        return home_score;
    }

    public void setAwayScore(long away_score) {
        this.away_score = away_score;
    }

    public long getAwayScore() {
        return away_score;
    }

    public void setWeek(long week) {
        this.week = week;
    }

    public long getWeek() {
        return week;
    }

    public void setMatchDate(long match_date) {
        this.match_date = match_date;
    }

    public long getMatchDate() {
        return match_date;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    public boolean getPlayed() {
        return played;
    }

    public static Game getGameById(long game_id) {
        return Game.find("SELECT g FROM Game g WHERE g.id = ?", game_id).first();
    }

    public static Team getTeamById(long team_id) {
        return Team.find("SELECT t FROM Team t WHERE t.id = ?", team_id).first();
    }

    public static List<Game> getGamesByTeamId(long team_id) {
        return Game.find("SELECT g FROM Game g WHERE g.home_team_id = ? OR g.away_team_id = ?", team_id, team_id).fetch();
    }

    public static List<Game> getHomeGamesByTeamId(long team_id) {
        return Game.find("SELECT g FROM Game g WHERE g.home_team_id = ?", team_id).fetch();
    }

    public static List<Game> getAwayGamesByTeamId(long team_id) {
        return Game.find("SELECT g FROM Game g WHERE g.away_team_id = ?", team_id).fetch();
    }

    public static int getGameCountByTeamId(long team_id) {
        int homeGames = Game.find("SELECT g FROM Game g WHERE g.home_team_id = ?", team_id).fetch().size();
        int awayGames = Game.find("SELECT g FROM Game g WHERE g.away_team_id = ?", team_id).fetch().size();

        return homeGames + awayGames;
    }

}
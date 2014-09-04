package models;

import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

/**
 * Created by daniel.schoonmaker on 9/3/14.
 */
@Entity
@Table(name = "media", uniqueConstraints = {@UniqueConstraint(columnNames = {"id"})})
public class Media extends Model {

    @Required
    @Column(name = "source")
    public String source;

    @Required
    @Column(name = "game_id")
    public long game_id;

    public Media(String source, long game_id) {
        this.source = source;
        this.game_id = game_id;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setGameId(long game_id) {
        this.game_id = game_id;
    }

    public long getGameId() {
        return game_id;
    }

    public static List<Media> getMediabyGame(long game_id) {
        return Game.find("SELECT m FROM Media m WHERE m.game_id = ?", game_id).fetch();
    }

    public static List<Media> getRecentMedia(int count) {
        return Game.find("SELECT m FROM Media m ORDER BY m.id desc").fetch(count);
    }
}

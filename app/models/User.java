package models;

import java.util.Date;
import javax.persistence.*;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

/**
 * User: daniel.schoonmaker
 * Date: 3/13/14
 * Time: 5:28 PM
 */
@Entity
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class User extends Model {

    @Email
    @Required
    @Column(name = "email")
    private String email;

    @Required
    @Column(name = "name")
    public String name;

    @Column(nullable = false, name = "admin")
    public boolean isAdmin;

    @Column(name = "last_login")
    private Date lastLogin;

    @Column(name = "avatarUrl")
    private String avatarUrl;

    @Column(nullable = false, name = "deleted")
    private Boolean deleted;

    public User(String email, String name, String avatarUrl, Date lastLogin) {
        this.email = email;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.lastLogin = lastLogin;
        deleted = false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /*
     * This function is used in a Unit test
     */
    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }
}

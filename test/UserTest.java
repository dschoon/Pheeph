import models.User;
import org.junit.Test;
import play.test.UnitTest;

public class UserTest extends UnitTest {

    @Test
    public void createAndRetrieveUser() {
        // Create a new user and save it
        new User("dschoon@gmail.com", "Dan", null, null).save();

        // Retrieve the user with e-mail address dan@gmail.com
        User dan = User.find("byEmail", "dschoon@gmail.com").first();

        // Test
        assertNotNull(dan);
        assertEquals("Dan", dan.name);
    }

    @Test
    public void tryConnectAsUser() {
        // Create a new user and save it
        new User("dschoon@gmail.com", "Dan", null, null).save();

        // Test
        assertNotNull(User.connect("dschoon@gmail.com", "secret"));
        assertNull(User.connect("dschoon@gmail.com", "badpassword"));
        assertNull(User.connect("blahblah@gmail.com", "secret"));
    }
}

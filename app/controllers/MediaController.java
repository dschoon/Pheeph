package controllers;

import controllers.securesocial.SecureSocial;
import models.Game;
import models.Media;
import models.User;
import securesocial.provider.SocialUser;
import play.mvc.*;

import java.util.List;

/**
 * Created by daniel.schoonmaker on 9/3/14.
 */
public class MediaController extends Controller  {

    final static private int RECENT_COUNT = 5;

    public static void media(int video_index) {
        video_index = video_index-1;
        String title = "Recent Videos";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        List<List<Game>> weeks = GameController.getAllGamesForAllWeeks(16);
        Media video = getRecentMedia(RECENT_COUNT).get(video_index);
        int results = getRecentMedia(RECENT_COUNT).size();

        renderArgs.put("userTeam",TeamController.getTeamByUserId(userModel.id));

        render("Application/media.html", title, weeks, video, video_index, results);
    }

    public static List<Media> getMediaByGameId(long game_id) {
        return Media.getMediabyGame(game_id);
    }

    public static List<Media> getRecentMedia(int count) {
        return Media.getRecentMedia(count);
    }

}

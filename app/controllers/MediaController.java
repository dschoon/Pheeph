package controllers;

import controllers.securesocial.SecureSocial;
import models.Game;
import models.Media;
import models.Team;
import models.User;
import securesocial.provider.SocialUser;
import play.mvc.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by daniel.schoonmaker on 9/3/14.
 */
public class MediaController extends Controller  {

    final static private int RECENT_COUNT = 5;

    public static void media(int video_index, long season_id) {
        video_index = video_index-1;
        String title = "Plays of the Week";
        SocialUser user = SecureSocial.getCurrentUser();
        User userModel = User.find("byEmail", user.email).first();
        Media video = getRecentMedia(RECENT_COUNT).get(video_index);
        int results = getRecentMedia(RECENT_COUNT).size();

        Game game = Game.getGameById(video.game_id);
        Team homeTeam = Team.getTeamById(game.home_team_id, season_id);
        Team awayTeam = Team.getTeamById(game.away_team_id, season_id);
        User homeUser = User.getUserById(homeTeam.user_id);
        User awayUser = User.getUserById(awayTeam.user_id);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date gameDate = new Date(game.getMatchDate());
        String gameTime = df.format(gameDate);

        renderArgs.put("userTeam",TeamController.getTeamByUserId(userModel.id, season_id));

        render("Application/media.html", title, game, video, video_index, results, homeTeam, awayTeam, homeUser, awayUser, gameTime);
    }

    public static List<Media> getMediaByGameId(long game_id) {
        return Media.getMediabyGame(game_id);
    }

    public static List<Media> getRecentMedia(int count) {
        return Media.getRecentMedia(count);
    }

}

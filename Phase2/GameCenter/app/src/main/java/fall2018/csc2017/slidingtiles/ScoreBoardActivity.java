package fall2018.csc2017.slidingtiles;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


//prefs.getString(GameLaunchCentreActivity.emailString + "/" + StartingActivity.gameName + "/" + "grade"

/**
 * The scoreboard Activity for user scores in the currently selected game.
 */
public class ScoreBoardActivity extends ScoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String highScoresKey = "highScoresKey" + "/" + StartingActivity.gameName + "/" + GameLaunchCentreActivity.emailString;
        emptyBoard(prefs, highScoresKey);

        highScores[0] = (TextView) findViewById(R.id.userGrade1);
        highScores[1] = (TextView) findViewById(R.id.userGrade2);
        highScores[2] = (TextView) findViewById(R.id.userGrade3);
        highScores[3] = (TextView) findViewById(R.id.userGrade4);


        String userHighScoresKey = GameLaunchCentreActivity.emailString + "/" + StartingActivity.gameName + "/" + "grade";
        nonEmptyBoard(prefs, highScoresKey, userHighScoresKey, false);
    }
}

package fall2018.csc2017.slidingtiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * A class for the global leaderboard.
 */
public class LeaderBoardActivity extends ScoreActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String globalHighScoresKey = "globalHighScoresKey" + "/" + "SlidingTiles";
        emptyBoard(prefs, globalHighScoresKey);

        highScores[0] = (TextView) findViewById(R.id.worldGrade1);
        highScores[1] = (TextView) findViewById(R.id.worldGrade2);
        highScores[2] = (TextView) findViewById(R.id.worldGrade3);
        highScores[3] = (TextView) findViewById(R.id.worldGrade4);

        String globalUserHighScoresKey = "globalHighScore" + "/" + "SlidingTiles" + "/";
        nonEmptyBoard(prefs, globalHighScoresKey, globalUserHighScoresKey, true);
    }
}
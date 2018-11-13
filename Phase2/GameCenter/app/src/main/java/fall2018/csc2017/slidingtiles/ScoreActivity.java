package fall2018.csc2017.slidingtiles;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


/**
 * Activity ScoreActivity
 */
public class ScoreActivity extends AppCompatActivity {

    /**
     * An static int contains number of Scores.
     */
    public static int SCORES = 4;

    /**
     * A list holding textview of highscores.
     */
    TextView highScores[] = new TextView[SCORES];

    /**
     * a defaultKey string variable.
     */
    String defaultHighScoresKey = "0123456789".substring(0,SCORES);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * a method that empties the Board.
     * @param prefs
     * @param highScoresKey
     */
    public void emptyBoard(SharedPreferences prefs, String highScoresKey){
        if (prefs.getString(highScoresKey,"").equals("")) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(highScoresKey, defaultHighScoresKey);
            editor.apply();
        }
    }

    /**
     * non EmptyBoard method.
     * @param prefs
     * @param highScoresKey
     * @param userHighScoresKey
     * @param addName
     */
    public void nonEmptyBoard(SharedPreferences prefs, String highScoresKey, String userHighScoresKey, boolean addName){
        String key;
        String ID;
        for (int i = 0; i < SCORES; i++) {
            key = prefs.getString(highScoresKey,"");
            ID = key.substring(i, i+1);
            String score = prefs.getString(userHighScoresKey + ID, "N/A");
            if (addName && !score.equals("N/A")) {
                int splitAt = (score.indexOf("/") > -1) ? score.indexOf("/") : score.length();
                score = score.substring(0, splitAt) + "     " +
                        score.substring(splitAt, score.length());
            }
            highScores[i].setText(Integer.toString(i+1) + ".       " + score);
        }
    }

    /**
     * A method to update scores
     * @param prefs
     * @param score
     * @param highScoreKeyPath
     * @param highScorePath
     * @param addName
     */
    public static void updateScores(SharedPreferences prefs, int score, String highScoreKeyPath, String highScorePath, boolean addName) {
        int i = 0;
        int highScore;
        String highScoreKey = prefs.getString(highScoreKeyPath,"");
        String lowestScoreID = highScoreKey.substring(SCORES - 1, SCORES);
        while (i < SCORES) {
            String highScoreID = highScoreKey.substring(i, i+1);

            if (addName) {
                highScore = Integer.parseInt(prefs.getString(highScorePath + highScoreID, "0").substring(0,1));
            } else {
                highScore = Integer.parseInt(prefs.getString(highScorePath + highScoreID, "0"));
            }

            if (highScore > score || highScore == 0 ) {
                SharedPreferences.Editor editor = prefs.edit();

                if (addName) {
                    String newHighScorePath = Integer.toString(score) +
                            "/" + StatusSave.getNickname(GameLaunchCentreActivity.emailString);
                    editor.putString(highScorePath + lowestScoreID, newHighScorePath);
                } else {
                    editor.putString(highScorePath + lowestScoreID, Integer.toString(score));
                }

                String newHighScoreKey = highScoreKey.substring(0, i) +
                        lowestScoreID + highScoreKey.substring(i, highScoreKey.length()-1);
                editor.putString(highScoreKeyPath, newHighScoreKey);
                editor.apply();
                i = SCORES;
            }
            i += 1;
        }
    }

}



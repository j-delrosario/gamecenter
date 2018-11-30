package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import fall2018.csc2017.slidingtiles.LeaderBoardActivity;
import fall2018.csc2017.slidingtiles.R;

/**
 * Activity for game matching tiles.
 */
public class GameActivityMatch extends AppCompatActivity {

    /**
     * Current user's email
     */
    public static String emailFileName = "";

    /**
     * The main save file.
     */
    public static String SAVE_FILENAME = "save_match.ser";
    /**
     * A temporary save file.
     */
    public static String TEMP_SAVE_FILENAME = "save_match_tmp.ser";

    /**
     * A textView for current score.
     */
    private static TextView currentScore;

    /**
     * Show the current score on screen.
     */
    public static void showCurrentScore(){
        currentScore.setText(Integer.toString(BoardMatch.getScore()));
    }

    /**
     * Update the file names
     */
    public void updateFileNames(String name){
        SAVE_FILENAME =  emailFileName + "_MT.ser";
        TEMP_SAVE_FILENAME = emailFileName + "_MT_tmp.ser";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        updateView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameViewMatch v = findViewById(R.id.gameViewMatch);
        v.save(SAVE_FILENAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        GameViewMatch matchView = findViewById(R.id.gameViewMatch);
        if (matchView.load(GameActivityMatch.SAVE_FILENAME)) {
            matchView.getBoard().refresh();
            showCurrentScore();
        }
    }

    /**
     * A start button listener.
     */
    private void addStartButtonListener() {
        Button newGameButton = findViewById(R.id.new_game);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((ViewGroup)v.getParent()) != null){
                    ((ViewGroup)v.getParent()).removeView(v);
                }
                updateView();
            }
        });
    }

    /**
     * A score board listener.
     */
    private void addScoreBoardButtonListener() {
        Button scoreBoardButton = findViewById(R.id.scoreboard_2048);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaderBoardActivity.GameName = "mmmatching";
                startActivity(new Intent(GameActivityMatch.this, LeaderBoardActivity.class));
            }
        });
    }

    /**
     * update the current views.
     */
    private void updateView() {
        setContentView(R.layout.match_main);
        currentScore = (TextView) findViewById(R.id.score);
        showCurrentScore();
        addStartButtonListener();
        addScoreBoardButtonListener();
        final String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        currentEmail.replace("@", "_");
        currentEmail.replace(".", "_");
        emailFileName = currentEmail;
        updateFileNames(emailFileName);
    }
}

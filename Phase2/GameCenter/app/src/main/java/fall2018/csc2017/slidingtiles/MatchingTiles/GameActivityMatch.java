package fall2018.csc2017.slidingtiles.MatchingTiles;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import fall2018.csc2017.slidingtiles.R;

public class GameActivityMatch extends AppCompatActivity {

    private static TextView currentScore;

    public static void showCurrentScore(){
        currentScore.setText(Integer.toString(BoardMatch.getScore()));
    }

    private Handler autosaveTimer = new Handler();
    private int autosaveInterval = 5000;
    private boolean saving = true;

    private Runnable autoSave = new Runnable(){
        @Override
        public void run() {
            if (saving) {
                autosaveTimer.postDelayed(this, autosaveInterval);
                GameViewMatch v = findViewById(R.id.gameViewMatch);
                v.save();
                makeToastSavedText();
            }
        }
    };

    public void stopSaving(){saving = false;}

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        updateView();
        autosaveTimer.postDelayed(autoSave, autosaveInterval);
    }

    @Override
    protected void onPause() {
        super.onPause();
        GameViewMatch v = findViewById(R.id.gameViewMatch);
        v.save();
        autosaveTimer.removeCallbacks(autoSave);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autosaveTimer.removeCallbacks(autoSave);
        autosaveTimer.postDelayed(autoSave, autosaveInterval);
    }

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

    private void addScoreBoardButtonListener() {
        Button scoreBoardButton = findViewById(R.id.scoreboard_2048);
        scoreBoardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: uncomment this: startActivity(new Intent(GameActivity2048.this, LeaderBoardActivity.class));
            }
        });
    }

    private void updateView() {
        setContentView(R.layout.match_main);
        currentScore = (TextView) findViewById(R.id.score);
        showCurrentScore();
        addStartButtonListener();
        addScoreBoardButtonListener();
    }

    private void makeToastSavedText() {
        Toast toast = Toast.makeText(this, R.string.saved_game, Toast.LENGTH_SHORT);
        View view = toast.getView();

        view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        TextView textview = (TextView) view.findViewById(android.R.id.message);
        Typeface font = Typeface.createFromAsset(this.getAssets(),
                "fonts/Audiowide-Regular.ttf");
        textview.setTypeface(font);
        textview.setTextSize(12);
        textview.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkMT));
        toast.show();
    }
}

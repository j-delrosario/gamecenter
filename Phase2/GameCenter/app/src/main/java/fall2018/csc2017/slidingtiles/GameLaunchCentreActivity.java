package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * An activity for GameLaunchCentre.
 */
public class GameLaunchCentreActivity extends AppCompatActivity {
    /**
     * The button to launch the game.
     */
    private Button Enterslidingtiles;
    /**
     * The button to enter the scoreboard;
     */
    private Button EnterScoreBoard;

    /**
     * Grade1.
     */
    private TextView GradeShow1;
    /**
     * Grade2
     */
    private TextView GradeShow2;
    /**
     * Grade3
     */
    private TextView GradeShow3;

    /**
     * an String containing email of current player.
     */
    public static String emailString;

    public static String nicknameString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch_centre);
        Enterslidingtiles = (Button)findViewById(R.id.stbutton);
        EnterScoreBoard = (Button)findViewById(R.id.leaderboard);
        GradeShow1 = findViewById(R.id.Grade1);
        GradeShow2 = findViewById(R.id.Grade2);
        GradeShow3 = findViewById(R.id.Grade3);
        getGrade(GradeShow1, GradeShow2, GradeShow3);
        Enterslidingtiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StartingActivity.class);
                startActivity(intent);
            }
        });
        EnterScoreBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), LeaderBoardActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * a get grade method.
     * @param Grade11
     * @param Grade12
     * @param Grade13
     */
    private void getGrade(TextView Grade11, TextView Grade12, TextView Grade13){
        Grade11.setText(StatusSave.Grade1);
        Grade12.setText(StatusSave.Grade2);
        Grade13.setText(StatusSave.Grade3);
    }
}

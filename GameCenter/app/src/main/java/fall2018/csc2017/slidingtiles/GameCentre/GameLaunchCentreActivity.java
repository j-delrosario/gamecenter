package fall2018.csc2017.slidingtiles.GameCentre;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;
import fall2018.csc2017.slidingtiles.PersonLeaderBoard;
import fall2018.csc2017.slidingtiles.R;

/**
 * The game launch center activity.
 */
public class GameLaunchCentreActivity extends AppCompatActivity {

    /**
     * A floating button for the scoreBoard.
     */
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_centre);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PersonLeaderBoard.class);
                startActivity(intent);
            }
        });
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        RecyclerView recycleView = findViewById(R.id.board_list);
        List<game_centre_item> mlist = new ArrayList<>();
        mlist.add(new game_centre_item(R.drawable.sliding, "SlidingTiles"));
        mlist.add(new game_centre_item(R.drawable.tilematching, "MatchingTiles"));
        mlist.add(new game_centre_item(R.drawable.s_2048, "2048"));
        CentreAdapter adapter = new CentreAdapter(getApplicationContext(), mlist);
        recycleView.setAdapter(adapter);
        recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}

package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fall2018.csc2017.slidingtiles.LeaderBoardComponent.ScoreAdapter;
import fall2018.csc2017.slidingtiles.LeaderBoardComponent.score_board_item;
import fall2018.csc2017.slidingtiles.R;

public class LeaderBoardActivity extends AppCompatActivity {
    public DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    private String GameName;
    public ArrayList getName(Map<String, Object> users) {
        ArrayList<String> name_list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : users.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            name_list.add((String) singleUser.get("email"));
        }
        return name_list;
    }

    public ArrayList getScore(Map<String, Object> users){
        CheckGameName();
        ArrayList<Long> score_list = new ArrayList<>();
        for (Map.Entry<String, Object> entry: users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            score_list.add((Long) singleUser.get(GameName));
        }
        return score_list;
    }

    private String CheckGameName(){
        //TODO: CHANGE ACCESS TO GAME NAME, prob just pass in parameter or context when call scoreboard
        /*if (StartingActivity.gameName.equals("SlidingTiles")){
            GameName = "mmsliding";
        }else if (StartingActivity.gameName.equals("MatchingTiles")) {
            GameName = "mmmatching";
        }else if (StartingActivity.gameName.equals("2048(Change Later)")) {
            GameName = "mm2048";
        }*/
        return GameName;
    }

    public void bubblesrt(ArrayList<Long> list, ArrayList<String> list_name)
    {
        Long temp;
        String temp_name;
        if (list.size()>1) // check if the number of orders is larger than 1
        {
            for (int x=0; x<list.size(); x++) // bubble sort outer loop
            {
                for (int i=0; i < list.size() - 1; i++) {
                    if (list.get(i) < (list.get(i+1)))
                    {
                        temp = list.get(i);
                        list.set(i,list.get(i+1) );
                        list.set(i+1, temp);
                        temp_name = list_name.get(i);
                        list_name.set(i,list_name.get(i+1) );
                        list_name.set(i+1, temp_name);
                    }
                }
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score_board);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RecyclerView recycleView = findViewById(R.id.board_list);
                List<score_board_item> mlist = new ArrayList<>();
                ArrayList<String> name_list = getName((Map<String, Object>) dataSnapshot.getValue());
                ArrayList<Long> score_list = getScore((Map<String, Object>) dataSnapshot.getValue());
                bubblesrt(score_list, name_list);
                for (int i=0; i < score_list.size(); i++) {
                    mlist.add(new score_board_item(name_list.get(i), score_list.get(i).toString()));
                }

                ScoreAdapter adapter = new ScoreAdapter(getApplicationContext(), mlist);
                recycleView.setAdapter(adapter);
                recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

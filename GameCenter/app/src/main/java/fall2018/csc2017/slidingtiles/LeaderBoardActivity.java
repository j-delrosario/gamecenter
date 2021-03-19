package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
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

/**
 * Leaderboard activity for 2048.
 */
public class LeaderBoardActivity extends AppCompatActivity {

    /**
     * Firebase database reference.
     */
    public DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();

    /**
     * String keeps the name of the game.
     */
    public static String GameName;

    /**
     * get the username based on the email.
     * @param users
     * @return ArrayList
     */
    public ArrayList getName(Map<String, Object> users) {
        ArrayList<String> name_list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : users.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            name_list.add((String) singleUser.get("nickname"));
        }
        return name_list;
    }

    /**
     * get all the emails
     * @param users
     * @return ArrayList
     */
    public ArrayList getEmail(Map<String, Object> users) {
        ArrayList<String> email_list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : users.entrySet()) {
            Map singleUser = (Map) entry.getValue();
            email_list.add((String) singleUser.get("email"));
        }
        return email_list;
    }

    /**
     * Get the score of the user.
     * @param users
     * @return Arraylist.
     */
    public ArrayList getScore(Map<String, Object> users){
        ArrayList<Long> score_list = new ArrayList<>();
        for (Map.Entry<String, Object> entry: users.entrySet()){
            Map singleUser = (Map) entry.getValue();
            score_list.add((Long) singleUser.get(GameName));
        }
        return score_list;
    }

    /**
     * A bubblesort for to sort scores.
     * @param list
     * @param list_name
     */
    public void bubblesrt(ArrayList<Long> list, ArrayList<String> list_name, ArrayList<String> email_name)
    {
        Long temp;
        String temp_name;
        String temp_email_name;
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
                        temp_email_name = email_name.get(i);
                        email_name.set(i,email_name.get(i+1) );
                        email_name.set(i+1, temp_email_name);
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
                int temp_int = 0;
                String currentEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                @SuppressWarnings("unchecked")
                ArrayList<String> name_list = getName((Map<String, Object>) dataSnapshot.getValue());
                @SuppressWarnings("unchecked")
                ArrayList<Long> score_list = getScore((Map<String, Object>) dataSnapshot.getValue());
                @SuppressWarnings("unchecked")
                ArrayList<String> email_list = getEmail((Map<String, Object>) dataSnapshot.getValue());
                bubblesrt(score_list, name_list,email_list);
                for (int i=0; i < email_list.size(); i++){
                    if (currentEmail.equals(email_list.get(i))){
                        temp_int = i;
                    }
                }
                TextView ranking;
                ranking = findViewById(R.id.myranking);
                ranking.setText("Your ranking: " + String.valueOf(temp_int + 1) + " (Score: " + score_list.get(temp_int) + ")");
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

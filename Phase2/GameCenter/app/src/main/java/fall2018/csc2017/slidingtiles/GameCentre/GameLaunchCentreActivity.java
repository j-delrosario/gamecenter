package fall2018.csc2017.slidingtiles.GameCentre;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.slidingtiles.LeaderBoardActivity;
import fall2018.csc2017.slidingtiles.R;

public class GameLaunchCentreActivity extends AppCompatActivity {
    private DatabaseReference mRef;
    public static String Email;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_centre);
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoardActivity.class);
                startActivity(intent);
            }
        });



        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer mark1 = dataSnapshot.child(Email).child("mmsliding").getValue(Integer.class);
                RecyclerView recycleView = findViewById(R.id.board_list);
                List<game_centre_item> mlist = new ArrayList<>();
                mlist.add(new game_centre_item(R.drawable.gcbackground1, "SlidingTiles", mark1.toString()));
                Integer mark2 = dataSnapshot.child(Email).child("mmmatching").getValue(Integer.class);
                mlist.add(new game_centre_item(R.drawable.gcbackground2, "MatchingTiles", mark2.toString()));
                Integer mark3 = dataSnapshot.child(Email).child("mm2048").getValue(Integer.class);
                mlist.add(new game_centre_item(R.drawable.gcbackground3, "2048", mark3.toString()));
                CentreAdapter adapter = new CentreAdapter(getApplicationContext(), mlist);
                recycleView.setAdapter(adapter);
                recycleView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }
}

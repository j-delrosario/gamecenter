package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * LeaderBaord for the user.
 */
public class PersonLeaderBoard extends AppCompatActivity {

    /**
     * A reference for database.
     */
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_leader_board);
        final TextView tvsliding = (TextView)findViewById(R.id.TVSliding);
        final TextView tvmatching = (TextView)findViewById(R.id.TVMatching);
        final TextView tvtw = (TextView)findViewById(R.id.TVTwfoutyeight);
        final TextView tvmessage = (TextView)findViewById(R.id.TVmessage);
        mRef = FirebaseDatabase.getInstance().getReference();
        final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Integer mark1 = dataSnapshot.child(currentUid).child("mmsliding").getValue(Integer.class);
                tvsliding.setText("SlidingTiles: " + mark1.toString());
                Integer mark2 = dataSnapshot.child(currentUid).child("mmmatching").getValue(Integer.class);
                tvmatching.setText("MatchingTiles: " + mark2.toString());
                Integer mark3 = dataSnapshot.child(currentUid).child("mm2048").getValue(Integer.class);
                tvtw.setText("2048: " + mark3.toString());
                String nickname = dataSnapshot.child(currentUid).child("nickname").getValue(String.class);
                tvmessage.setText("Welcome " + nickname);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

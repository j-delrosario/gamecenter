package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;

/**
 * An Activity of the user registery.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * EditTexts for Email, Password, Nickname.
     */
    private EditText Email;

    /**
     * An text for password.
     */
    private EditText Password;

    /**
     * An text for Nickname.
     */
    private EditText Nickname;

    /**
     * A reference for the batabase.
     */
    private DatabaseReference mRef;

    /**
     * A button represents Register.
     */
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email= findViewById(R.id.EmailRegister);
        Password= findViewById(R.id.PasswordRegister);
        Nickname = findViewById(R.id.NickNameRegister);
        Register = findViewById(R.id.regbutton);
        mRef = FirebaseDatabase.getInstance().getReference(Email.getText().toString());
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString().replaceAll("\\s", "").equals("") ||
                        Password.getText().toString().replaceAll("\\s", "").equals("") ||
                        Nickname.getText().toString().replaceAll("\\s", "").equals("")) {
                    Toast.makeText(getApplicationContext(), "The register information cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    Read();
                }
            }
        });

    }

    /**
     * Write user info.
     */
    private void Write() {
        User user = new User(Email.getText().toString(), Password.getText().toString(), Nickname.getText().toString(), 0, 0, 0);
        mRef.child(Email.getText().toString()).setValue(user);
        Intent intent = new Intent (getApplicationContext(), GameLaunchCentreActivity.class);
        startActivity(intent);
        GameLaunchCentreActivity.Email = Email.getText().toString();
    }

    /**
     * Read the user info.
     */
    private void Read(){
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.child(Email.getText().toString()).exists();
                if (value){
                    Toast.makeText(getApplicationContext(), "You have registered. Please log in.", Toast.LENGTH_SHORT).show();
                }else{
                    Write();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}

package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
                    createUser(view);
                }
            }
        });

    }

    public void createUser(View view){
        String email = Email.getText().toString();
        String password = Password.getText().toString();
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    User user = new User(Email.getText().toString(), Password.getText().toString(), Nickname.getText().toString(), 0, 0, 0);
                    Toast.makeText(getApplicationContext(), "Registered", Toast.LENGTH_SHORT).show();
                    mRef.child(firebaseUser.getUid()).setValue(user);
                    Intent intent = new Intent (getApplicationContext(), GameLaunchCentreActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Fail to register. Either Email has been registered or register conditions are violated.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}

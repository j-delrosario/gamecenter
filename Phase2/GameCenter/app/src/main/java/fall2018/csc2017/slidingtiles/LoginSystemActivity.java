package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;


public class LoginSystemActivity extends AppCompatActivity {

    /**
     * editTexts for Email, password, SignUp.
     */
    private EditText Email;
    private EditText Password;
    private TextView SignUp;

    /**
     * A button represents the Login.
     */
    private Button Login;

    /**
     * A checkbox to Remember the user account.
     */
    private CheckBox Remember;




    /** sharedoreferences.
     *
     */
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    /**
     * A Constant for calling preferences.
     */
    private static final String PREFERENCE_NAME = "preference";

    /**
     * A stirng containing text.
     */
    private String text;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginsys);
        sharedPreferences = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Email = findViewById(R.id.EmailAddress);
        Password = findViewById(R.id.etpassword);
        Login = findViewById(R.id.btnlogin);
        SignUp = findViewById(R.id.tvRegister);
        mRef = FirebaseDatabase.getInstance().getReference();
        Remember = findViewById(R.id.Loginchecbox);
        if (sharedPreferences.getBoolean("Remember", false)) {
            Remember.setChecked(true);
        }else{
            Remember.setChecked(false);
        }
        LoadData();



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString().replaceAll("\\s", "").equals("") ||
                        Password.getText().toString().replaceAll("\\s", "").equals("")){
                    Toast.makeText(getApplicationContext(), "Login information cannot be empty", Toast
                    .LENGTH_SHORT).show();
                }else{
                EnterGameCentre();


                }
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), RegisterActivity
                .class);
                startActivity(intent);
            }
        });

    }

    /**
     * A function describes behaviours entering the GAME CENTRE.

     */
    private void EnterGameCentre(){

        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean value = dataSnapshot.child(Email.getText().toString()).exists();
                if (value){
                    String Psd = dataSnapshot.child(Email.getText().toString()).child("password").getValue(String.class);
                    if (Psd.equals(Password.getText().toString())){
                        GameLaunchCentreActivity.Email = Email.getText().toString();
                        Intent intent = new Intent (getApplicationContext(), GameLaunchCentreActivity.class);
                        startActivity(intent);
                        SaveData();
                    }else{
                        Toast.makeText(getApplicationContext(), "Password is wrong", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Please Register", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    /**
     * Save username/password.
     */
    private void SaveData(){
        if (Remember.isChecked()){
            editor.putString("Email", Email.getText().toString());
            editor.putString("Password", Password.getText().toString());
            editor.putBoolean("Remember", true);
            editor.apply();
        }else{
            editor.putString("Email", "");
            editor.putString("Password", "");
            editor.putBoolean("Remember", false);
            editor.apply();
        }
    }

    /**
     * Load username/password.
     */
    public void LoadData() {
        text = sharedPreferences.getString("Email", "");
        Email.setText(text);
        text = sharedPreferences.getString("Password", "");
        Password.setText((text));
    }


}

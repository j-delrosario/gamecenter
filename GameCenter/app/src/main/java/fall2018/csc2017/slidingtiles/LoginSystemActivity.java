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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import fall2018.csc2017.slidingtiles.GameCentre.GameLaunchCentreActivity;

/**
 * Activity for the login system.
 */
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
    /**
     * A firebase authentication to check user's email account.
     */
    private FirebaseAuth auth;

    /** sharedoreferences.
     *
     */
    SharedPreferences sharedPreferences;

    /**
     * An preference editor.
     */
    SharedPreferences.Editor editor;

    /**
     * A Constant for calling preferences.
     */
    private static final String PREFERENCE_NAME = "preference";

    /**
     * A stirng containing text.
     */
    private String text;


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
        auth = FirebaseAuth.getInstance();
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
                    auth.signInWithEmailAndPassword(Email.getText().toString(), Password.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "You have logged in", Toast.LENGTH_SHORT).show();
                                        finish();
                                        Intent intent = new Intent (getApplicationContext(), GameLaunchCentreActivity.class);
                                        SaveData();
                                        startActivity(intent);
                                    }else{
                                        Toast.makeText(getApplicationContext(), "Fail to log in", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
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

package fall2018.csc2017.slidingtiles;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


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
     * An Array containing all the users and scores.
     */
    public String[][] userList = StatusSave.Create();

    /**
     * An string defines the useraccount save file.
     */
    public static final String USER_FILENAME = "user_file.ser";

    /**
     * An string representing the current player's email.
     */
    public String emailString;

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

        emailString = Email.getText().toString();
        loadFromFile(USER_FILENAME);
        if (!(userList == null)) {
            StatusSave.updateList(userList);
        }
        else{
            userList = StatusSave.Create();
        }

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
                EnterGameCentre(Email.getText().toString(), Password.getText().toString());
                userList = StatusSave.NameNPassword;
                saveToFile(USER_FILENAME);

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
     * @param username
     * @param password
     */
    private void EnterGameCentre(String username, String password){
        if (StatusSave.FindUser(username)){
            if (StatusSave.RightUser(username, password)){
                GameLaunchCentreActivity.emailString = emailString;
                Intent intent = new Intent(this, GameLaunchCentreActivity.class);
                startActivity(intent);
                SaveData();
            }else{Toast.makeText(getApplicationContext(), "wrong password", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Please Register", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                userList = (String[][]) input.readObject();
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "Username not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(userList);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
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

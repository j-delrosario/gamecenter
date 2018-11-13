package fall2018.csc2017.slidingtiles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * An Activity of the user registery.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * EditTexts for Email, Password, Nickname.
     */
    private EditText Email;
    private EditText Password;
    private EditText Nickname;

    /**
     * A button represents Register.
     */
    private Button Register;

    /**
     * A string represents the current user's Email.
     */
    public String emailStr;

    /**
     * the User list.
     */
    public String[][] userList = StatusSave.Create();

    /**
     * The constant represents the useraccount save file.
     */
    public static final String USER_FILENAME = "user_file.ser";

    /**
     * a string represents the Email.
     */
    public static String emailString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Email = findViewById(R.id.EmailRegister);
        Password = findViewById(R.id.PasswordRegister);
        Nickname = findViewById(R.id.NickNameRegister);
        Register = findViewById(R.id.regbutton);

        emailString = Email.getText().toString();
        loadFromFile(USER_FILENAME);
        if (!(userList == null)) {
            StatusSave.updateList(userList);
        }
        else{
            userList = StatusSave.Create();
        }
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Email.getText().toString().replaceAll("\\s", "").equals("") ||
                        Password.getText().toString().replaceAll("\\s", "").equals("") ||
                        Nickname.getText().toString().replaceAll("\\s", "").equals("")) {
                    Toast.makeText(getApplicationContext(), "The register information cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    if (StatusSave.FindUser(Email.getText().toString())){
                        Toast.makeText(getApplicationContext(), "You have registered this account before, please log in", Toast.LENGTH_SHORT).show();
                    }else{
                        StatusSave.AddUser(Email.getText().toString(), Password.getText().toString(), Nickname.getText().toString());
                        GameLaunchCentreActivity.emailString = emailString;
                        Intent intent = new Intent(getApplicationContext(), GameLaunchCentreActivity.class);
                        startActivity(intent);
                    }
                }
                emailStr = Email.getText().toString();
                userList = StatusSave.NameNPassword;
                saveToFile(USER_FILENAME);
            }
        });

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
                userList = (String[][])input.readObject();
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
}

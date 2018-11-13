package fall2018.csc2017.slidingtiles;


/**
 * A class contains the Useraccount info.
 */
public class StatusSave {

    /**
     * The Array containing the Useraccount info.
     */
    public static String[][] NameNPassword = Create();

    /**
     * 3 Scores.
     */
    public static String Grade1 = new String();
    public static String Grade2 = new String();
    public static String Grade3 = new String();

    /**
     * A string represents the current user.
     */
    public static String player_name;

    /**
     * Return nickName from email
     * @param e
     * @return
     */
    public static String getNickname(String e){
        String nickName = "";
        for (int i = 0; i < 200; i++){
            for (int l = 0; l < 7; l++){
                if (NameNPassword[i][0] == e){
                    nickName = NameNPassword[i][2];
                }

            }
        }
        return nickName;
    }

    /**
     * Update the current list with s.
     * @param s
     */
    public static void updateList(String[][] s){
        NameNPassword = s;
    }

    /**
     * Add a new user.
     * @param UserEmail
     * @param UserPassword
     * @param Nickname
     */
    public static void AddUser(String UserEmail, String UserPassword, String Nickname){
        int a  = 1;
        int i = 0;

        while (i < NameNPassword.length && a == 1){
            if (NameNPassword[i][0].equals("")){
                NameNPassword[i][0] = UserEmail;
                NameNPassword[i][1] = UserPassword;
                NameNPassword[i][2] = Nickname;
                Grade1 = "";
                Grade2 = "";
                Grade3 = "";
                player_name = UserEmail;
                a = 0;
            }
            i++;
        }
    }


    /**
     * Returns true is userEmail exists in the history. false otherwise.
     * @param UserEmail
     * @return
     */
    public static boolean FindUser(String UserEmail){
        boolean success = false;
        for (int i = 0; i < NameNPassword.length; i ++){
            if (UserEmail.equals(NameNPassword[i][0])){
                success = true;
            }
        }
        return success;
    }

    /**
     * Returns true if the user is the right user.
     * @param UserEmail
     * @param Password
     * @return
     */
    public static boolean RightUser(String UserEmail, String Password){
        boolean success = false;
        for (int i = 0; i < NameNPassword.length; i ++){
            if (NameNPassword[i][0].equals(UserEmail) && NameNPassword[i][1].equals(Password)){
                success = true;
                Grade1 = NameNPassword[i][4];
                Grade2 = NameNPassword[i][5];
                Grade3 = NameNPassword[i][6];
                player_name = UserEmail;
            }
        }
        return success;
    }

    /**
     * Create the string as needed.
     * @return
     */
    public static String[][] Create(){
        String[][] stuff = new String[200][7];
        for (int i = 0; i < 200; i ++){
            for (int j = 0; j < 6; j ++) {
                stuff[i][j] = "";
            }
        }
        return stuff;
    }

}


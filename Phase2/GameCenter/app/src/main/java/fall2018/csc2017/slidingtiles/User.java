package fall2018.csc2017.slidingtiles;

/**
 * The use class contains user info.
 */
public class User {

    /**
     * string for Email.
     */
    private String Email;

    /**
     * String for password.
     */
    private String Password;

    /**
     * String for Nickname.
     */
    private String Nickname;

    /**
     * Score for Sliding Tiles.
     */
    private Integer MMSliding = 0;

    /**
     * Score represent matchingTiles.
     */
    private Integer MMMatching = 0;

    /**
     * Score for 2048.
     */
    private Integer MM2048 = 0;

    /**
     * Initialization
     * @param email
     * @param password
     * @param nickname
     * @param mmsliding
     * @param mmmatching
     * @param mm2048
     */
    public User(String email, String password, String nickname, Integer mmsliding, Integer mmmatching, Integer mm2048) {
        Email = email;
        Password = password;
        Nickname = nickname;
        MMSliding = mmsliding;
        MMMatching = mmmatching;
        MM2048 = mm2048;

    }

    /**
     * get the Email of the user.
     * @return String
     */
    public String getEmail() {
        return Email;
    }
    /**
     * set the Email of the user.
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Get the password of the user.
     * @return String
     */
    public String getPassword() {
        return Password;
    }

    /**
     * Set the password for the user.
     * @param password
     */
    public void setPassword(String password) {
        Password = password;
    }

    /**
     * Get the nickname of the user.
     * @return String
     */
    public String getNickname() {
        return Nickname;
    }

    /**
     * Set the nickname of the user.
     * @param nickname
     */
    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    /**
     * Get the score for sliding tiles.
     * @return Int
     */
    public Integer getMMSliding() {
        return MMSliding;
    }

    /**
     * Get the Matching Tile game score.
     * @return Int
     */
    public Integer getMMMatching() {
        return MMMatching;
    }

    /**
     * Set the Matching tile game score.
     */
    public void setMMMatching(Integer MMMatching) {
        this.MMMatching = MMMatching;
    }

    /**
     * Get the score of game 2048.
     * @return int
     */
    public Integer getMM2048() {
        return MM2048;
    }

    /**
     * Set the score of game 2048.
     * @param MM2048
     */
    public void setMM2048(Integer MM2048) {
        this.MM2048 = MM2048;
    }

    /**
     * Set the score for Sliding tile game.
     * @param MMSliding
     */
    public void setMMSliding(Integer MMSliding) {
        this.MMSliding = MMSliding;
    }
}

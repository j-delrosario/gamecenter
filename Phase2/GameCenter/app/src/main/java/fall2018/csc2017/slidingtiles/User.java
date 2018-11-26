package fall2018.csc2017.slidingtiles;

public class User {
    private String Email;
    private String Password;
    private String Nickname;
    private Integer MMSliding;
    private Integer MMMatching;
    private Integer MM2048;

    public User(String email, String password, String nickname, Integer mmsliding, Integer mmmatching, Integer mm2048) {
        Email = email;
        Password = password;
        Nickname = nickname;
        MMSliding = mmsliding;
        MMMatching = mmmatching;
        MM2048 = mm2048;

    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String nickname) {
        Nickname = nickname;
    }

    public Integer getMMSliding() {
        return MMSliding;
    }

    public Integer getMMMatching() {
        return MMMatching;
    }

    public void setMMMatching(Integer MMMatching) {
        this.MMMatching = MMMatching;
    }

    public Integer getMM2048() {
        return MM2048;
    }

    public void setMM2048(Integer MM2048) {
        this.MM2048 = MM2048;
    }

    public void setMMSliding(Integer MMSliding) {
        this.MMSliding = MMSliding;
    }
}

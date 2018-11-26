package fall2018.csc2017.slidingtiles.GameCentre;

import android.content.Intent;

public class game_centre_item {
    int background;
    String ProfileName;
    String ProfileMaxMark;

    public game_centre_item(int background, String profileName, String profileMaxMark) {
        this.background = background;
        ProfileName = profileName;
        ProfileMaxMark = profileMaxMark;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getProfileName() {
        return ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    public String getProfileMaxMark() {
        return ProfileMaxMark;
    }

    public void setProfileMaxMark(String profileMaxMark) {
        ProfileMaxMark = profileMaxMark;
    }



}

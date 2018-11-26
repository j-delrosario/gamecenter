package fall2018.csc2017.slidingtiles.GameCentre;

import android.content.Intent;

/**
 * A class holding game centre items.
 */
public class game_centre_item {

    /**
     * Background int.
     */
    int background;

    /**
     * A string keeping the file name
     */
    String ProfileName;

    /**
     * A string keeping the max mark
     */
    String ProfileMaxMark;

    /**
     * Initialize a game cetner item class
     * @param background
     * @param profileName
     * @param profileMaxMark
     */
    public game_centre_item(int background, String profileName, String profileMaxMark) {
        this.background = background;
        ProfileName = profileName;
        ProfileMaxMark = profileMaxMark;
    }

    /**
     * return the background int.
     * @return
     */
    public int getBackground() {
        return background;
    }

    /**
     * Set the background int.
     * @param background
     */
    public void setBackground(int background) {
        this.background = background;
    }

    /**
     * Return the profile name
     * @return
     */
    public String getProfileName() {
        return ProfileName;
    }

    /**
     * Set the profile name.
     * @param profileName
     */
    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    /**
     * return the profile max mark.
     * @return
     */
    public String getProfileMaxMark() {
        return ProfileMaxMark;
    }

    /**
     * Set the profile max mark.
     * @param profileMaxMark
     */
    public void setProfileMaxMark(String profileMaxMark) {
        ProfileMaxMark = profileMaxMark;
    }



}

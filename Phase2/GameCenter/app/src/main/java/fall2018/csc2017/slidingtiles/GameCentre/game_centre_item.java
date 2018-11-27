package fall2018.csc2017.slidingtiles.GameCentre;


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
     * Initialize a game cetner item class
     * @param background
     * @param profileName
     */
    public game_centre_item(int background, String profileName) {
        this.background = background;
        ProfileName = profileName;
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




}

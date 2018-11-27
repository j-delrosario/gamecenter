package fall2018.csc2017.slidingtiles.LeaderBoardComponent;

/**
 * An class for the score board.
 */
public class score_board_item {

    /**
     * A string keeping the GameName.
     */
    String GameName;

    /**
     * A string keeping the Game mark.
     */
    String GameMark;

    /**
     * Initialize a score board item.
     * @param gameName
     * @param gameMark
     */
    public score_board_item(String gameName, String gameMark) {
        GameName= gameName;
        GameMark = gameMark;
    }

    /**
     * Return the game name.
     * @return
     */
    public String getGameName() {
        return GameName;
    }

    /**
     * Return the game mark.
     * @return
     */
    public String getGameMark() {
        return GameMark;
    }

}

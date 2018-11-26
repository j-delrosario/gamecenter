package fall2018.csc2017.slidingtiles.LeaderBoardComponent;

public class score_board_item {

    String GameName;
    String GameMark;

    public score_board_item(String gameName, String gameMark) {
        GameName= gameName;
        GameMark = gameMark;

    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public String getGameMark() {
        return GameMark;
    }

    public void setGameMark(String gameMark) {
        GameMark = gameMark;
    }
}

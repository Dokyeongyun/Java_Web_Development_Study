package Board;

/*
create table board
(
    bID        int           not null
        primary key,
    bTitle     varchar(50)   null,
    userID     varchar(20)   null,
    bDate      datetime      null,
    bContent   varchar(5012) null,
    bAvailable int           null
)
    charset = utf8mb4;
 */
public class Board {
    private int bID;
    private String bTitle;
    private String userID;
    private String bDate;
    private String bContent;
    private int bAvailable;

    public Board() {
    }

    public Board(int bID, String bTitle, String userID, String bDate, String bContent, int bAvailable) {
        this.bID = bID;
        this.bTitle = bTitle;
        this.userID = userID;
        this.bDate = bDate;
        this.bContent = bContent;
        this.bAvailable = bAvailable;
    }


    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getbID() {
        return bID;
    }

    public void setbID(int bID) {
        this.bID = bID;
    }

    public String getbTitle() {
        return bTitle;
    }

    public void setbTitle(String bTitle) {
        this.bTitle = bTitle;
    }

    public String getbDate() {
        return bDate;
    }

    public void setbDate(String bDate) {
        this.bDate = bDate;
    }

    public String getbContent() {
        return bContent;
    }

    public void setbContent(String bContent) {
        this.bContent = bContent;
    }

    public int getbAvailable() {
        return bAvailable;
    }

    public void setbAvailable(int bAvailable) {
        this.bAvailable = bAvailable;
    }
}

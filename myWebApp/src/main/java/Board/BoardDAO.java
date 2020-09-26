package Board;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class BoardDAO {

    // 데이터베이스에 접근하기 위한 객체
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public BoardDAO() {
        try {
            // 데이터베이스 접근 URL
            String dbURL = "jdbc:mysql://localhost:3306/mywebapp?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF8";
            String dbID = "root";
            String dbPassword = "1234";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*글 번호*/
    public int getNextID() {
        String SQL = "SELECT bID FROM board ORDER BY bID DESC";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) + 1;
            }
            return 1; // bID값이 없으면 첫번째 글
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // DB오류
    }

    /*DB에 글이 등록되는 시점*/
    public String getDate() {
        String SQL = "SELECT NOW()";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ""; //DB 오류
    }

    public int writePost(String bTitle, String userID, String bContent, String bPassword) {
        String SQL = "INSERT INTO board VALUES(?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNextID());
            pstmt.setString(2, bTitle);
            pstmt.setString(3, userID);
            pstmt.setString(4, getDate());
            pstmt.setString(5, bContent);
            pstmt.setInt(6, 1); // 1이면 삭제하지 않았다는 뜻
            pstmt.setString(7, bPassword);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public Board getPost(int bID) {
        String SQL = "SELECT * FROM board WHERE bID = ?";
        Board board = new Board();
        board.setbID(bID);
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, bID);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                // bAvailable 이 1이면 가져오기
                if (rs.getInt(6) == 1) {
                    board.setbTitle(rs.getString(2));
                    board.setUserID(rs.getString(3));
                    board.setbDate(rs.getString(4));
                    board.setbContent(rs.getString(5));
                    board.setbAvailable(rs.getInt(6));
                    board.setbPassword(rs.getString(7));
                    return board;
                } else {
                    return new Board();
                }
            }
            return new Board(); // 해당 글 없음
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Board(); // DB 오류
    }

    public ArrayList<Board> getList(int pageNum) {
        String SQL = "SELECT * FROM BOARD WHERE bID < ? AND bAvailable = 1 ORDER BY bID DESC LIMIT 10";
        ArrayList<Board> list = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNextID() - (pageNum - 1) * 10);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Board board = new Board();
                board.setbID(rs.getInt(1));
                board.setbTitle(rs.getString(2));
                board.setUserID(rs.getString(3));
                board.setbDate(rs.getString(4));
                board.setbContent(rs.getString(5));
                board.setbAvailable(rs.getInt(6));

                list.add(board);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public boolean nextPage(int pageNum) {
        String SQL = "SELECT * FROM BOARD WHERE bID < ? AND bAvailable = 1 ORDER BY bID DESC LIMIT 10";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setInt(1, getNextID() - (pageNum - 1) * 10);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getPostNum() {
        String SQL = "SELECT COUNT(*) FROM board WHERE bAvailable = 1";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int update(int bId, String bTitle, String bContent, String bPassword) {
        String SQL = "UPDATE board SET bTitle = ?, bContent = ?, bPassword = ? WHERE bID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, bTitle);
            pstmt.setString(2, bContent);
            pstmt.setString(3, bPassword);
            pstmt.setInt(4, bId);

            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

}

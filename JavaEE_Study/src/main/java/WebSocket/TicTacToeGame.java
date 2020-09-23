package WebSocket;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

/*
    Tic-Tac-Toe 게임 로직 클래스
 */
public class TicTacToeGame {
    private static long gameIdSequence = 1L;
    private static final Hashtable<Long, String> pendingGames = new Hashtable<>();
    private static final Map<Long, TicTacToeGame> activeGames = new Hashtable<>();
    private final long id;
    private final String player1;
    private final String player2;
    private Player nextMove = Player.random();
    private Player[][] grid = new Player[3][3];
    private boolean over;
    private boolean draw;
    private Player winner;

    /**
     * 생성자
     */
    private TicTacToeGame(long id, String player1, String player2) {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Getter
     */
    public long getId() {
        return id;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getNextMoveBy() {
        return nextMove == Player.PLAYER1 ? player1 : player2;
    }

    public boolean isOver() {
        return over;
    }

    public boolean isDraw() {
        return draw;
    }
    public Player getWinner() {
        return winner;
    }

    @SuppressWarnings("unchecked")
    public static Map<Long, String> getPendingGames() {
        return (Map<Long, String>) TicTacToeGame.pendingGames.clone();
    }


    public static TicTacToeGame getActiveGame(long gameId) {
        return TicTacToeGame.activeGames.get(gameId);
    }

    /**
     * 이동가능한지 확인 후 이동, 이동할 때마다 게임이 끝났는지 / 승자가 누구인지 확인
     */
    @JsonIgnore
    public synchronized void move(Player player, int row, int column) {
        if (player != this.nextMove)
            throw new IllegalArgumentException("It is not your turn!");

        if (row > 2 || column > 2)
            throw new IllegalArgumentException("Row and column must be 0-3.");

        if (this.grid[row][column] != null)
            throw new IllegalArgumentException("Move already made at " + row + "," + column);

        this.grid[row][column] = player;
        this.nextMove = this.nextMove == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.winner = this.calculateWinner();
        if (this.getWinner() != null || this.isDraw()) {
            this.over = true;
        }
        if (this.isOver()) {
            TicTacToeGame.activeGames.remove(this.id);
        }
    }


    public synchronized void forfeit(Player player) {
        TicTacToeGame.activeGames.remove(this.id);
        this.winner = player == Player.PLAYER1 ? Player.PLAYER2 : Player.PLAYER1;
        this.over = true;
    }

    /**
     * 승자 결정 (가로 1줄 또는 세로 1줄 또는 대각선 1줄 완성하면 승)
     */
    private Player calculateWinner() {
        boolean draw = true;

        // Horizontal wins
        for (int i = 0; i < 3; i++) {
            if (this.grid[i][0] == null || this.grid[i][1] == null || this.grid[i][2] == null)
                draw = false;
            if (this.grid[i][0] != null && this.grid[i][0] == this.grid[i][1] && this.grid[i][0] == this.grid[i][2])
                return this.grid[i][0];
        }

        // vertical wins
        for (int i = 0; i < 3; i++) {
            if (this.grid[0][i] != null && this.grid[0][i] == this.grid[1][i] && this.grid[0][i] == this.grid[2][i])
                return this.grid[0][i];
        }

        // diagonal wins
        if (this.grid[0][0] != null && this.grid[0][0] == this.grid[1][1] && this.grid[0][0] == this.grid[2][2])
            return this.grid[0][0];
        if (this.grid[0][2] != null && this.grid[0][2] == this.grid[1][1] && this.grid[0][2] == this.grid[2][0])
            return this.grid[0][2];

        this.draw = draw;

        return null;
    }

    /**
     * 대기열 큐에 게임 추가
     */
    public static long queueGame(String user1) {
        long id = TicTacToeGame.gameIdSequence++;
        TicTacToeGame.pendingGames.put(id, user1);
        return id;
    }

    /**
     * 대기열 큐에서 게임 제거
     */
    public static void removeQueuedGame(long queueId){
        TicTacToeGame.pendingGames.remove(queueId);
    }

    /**
     * 대기열에 2명의 유저 생기면 게임 시작
     */
    public static TicTacToeGame startGame(long queuedId, String user2){
        String user1 = TicTacToeGame.pendingGames.remove(queuedId);
        TicTacToeGame game = new TicTacToeGame(queuedId,user1,user2);
        TicTacToeGame.activeGames.put(queuedId,game);
        return game;
    }

    /**
     * Player 열거형
     */
    public enum Player {
        PLAYER1, PLAYER2;
        private static final Random random = new Random();

        private static Player random() {
            return Player.random.nextBoolean() ? PLAYER1 : PLAYER2;
        }
    }
}

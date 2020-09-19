package WebSocket;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


/*
두 웹소켓 세션간의 게이트웨이 역할을 함
 */
// @ServerEndpoint 어노테이션으로 서버 엔드포인트 등록
@ServerEndpoint("/ticTacToe/{gameId}/{username}")
public class TicTacToeServer {
    private static Map<Long, Game> games = new Hashtable<>();
    private static ObjectMapper mapper = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, @PathParam("gameId") long gameId, @PathParam("username") String username) {
        try {
            // URL 로 주어진 gameId 로 활성화된 게임이 있다면 세션 종료
            TicTacToeGame ticTacToeGame = TicTacToeGame.getActiveGame(gameId);
            if (ticTacToeGame != null) {
                session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, "게임이 이미 시작하였습니다."));
            }
            List<String> actions = session.getRequestParameterMap().get("action");
            if (actions != null && actions.size() == 1) {
                String action = actions.get(0);

                // action 이 start 면
                if ("start".equalsIgnoreCase(action)) {
                    Game game = new Game();
                    game.gameId = gameId;
                    game.player1 = session;
                    TicTacToeServer.games.put(gameId, game);
                } else if ("join".equalsIgnoreCase(action)) {
                    // 서버에 열려 있는 게임 중 요청된 gameId 에 참가
                    // player1 은 start 로 게임을 생성한 사용자이고
                    // join 으로 게임에 참가한 사람이 player2
                    Game game = TicTacToeServer.games.get(gameId);
                    game.player2 = session;
                    // 게임을 시작하여 activateGames 에 추가
                    game.ticTacToeGame = TicTacToeGame.startGame(gameId, username);
                    // 각 사용자에게 게임이 시작되었다는 메시지 전송
                    this.sendJsonMessage(game.player1, game, new GameStartedMessage(game.ticTacToeGame));
                    this.sendJsonMessage(game.player2, game, new GameStartedMessage(game.ticTacToeGame));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            try{
                session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, e.toString()));
            } catch (IOException ignored) {}
        }
    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("gameId") long gameId){
        Game game = TicTacToeServer.games.get(gameId);
        boolean isPlayer1 = session == game.player1;
        try{
            Move move = TicTacToeServer.mapper.readValue(message, Move.class);
            game.ticTacToeGame.move(isPlayer1 ? TicTacToeGame.Player.PLAYER1 : TicTacToeGame.Player.PLAYER2
                ,move.getRow(), move.getColumn());

            // Player1 이면 Player2 에게, Player2 면 Player1 에게 움직일 수 없다는 상태 메시지 전송
            this.sendJsonMessage((isPlayer1 ? game.player2 : game.player1), game, new OpponentMadeMoveMessage(move));

            if(game.ticTacToeGame.isOver()){
                if(game.ticTacToeGame.isDraw()){
                    // 비겼으면 양쪽 사용자에게 비겼다는 메시지 전송
                    this.sendJsonMessage(game.player1, game, new GameIsDrawMessage());
                    this.sendJsonMessage(game.player2, game, new GameIsDrawMessage());
                }else{
                    // PLAYER1 이 이겼으면 true / 졌으면 false
                    boolean wasPlayer1 = game.ticTacToeGame.getWinner() == TicTacToeGame.Player.PLAYER1;
                    this.sendJsonMessage(game.player1,game, new GameOverMessage(wasPlayer1));
                    this.sendJsonMessage(game.player2,game, new GameOverMessage(!wasPlayer1));
                }
                // 게임이 종료되었으므로 세션 닫기
                game.player1.close();
                game.player2.close();
            }
        } catch (IOException e) {
            this.handleException(e, game);
        }
    }

    @OnClose
    public void onClose(Session session, @PathParam("gameId") long gameId) throws IOException {
        Game game = TicTacToeServer.games.get(gameId);
        if(game == null){
            return;
        }
        // 게임중에 사용자의 세션이 종료되었다면 -> 게임 강제종료 후 상대방이 나갔다는 메시지 전송
        // 게임종료 후에 사용자의 세션이 종료되었다면 -> 게임 대기열에서 해당 게임방 제거
        boolean isPlayer1 = session == game.player1;
        if(game.ticTacToeGame == null){
            TicTacToeGame.removeQueuedGame(game.gameId);
        }else if(!game.ticTacToeGame.isOver()){
            game.ticTacToeGame.forfeit(isPlayer1 ? TicTacToeGame.Player.PLAYER1 : TicTacToeGame.Player.PLAYER2);

            // 상대 플레이어의 세션 획득
            Session opponent = (isPlayer1 ? game.player2 : game.player1);

            // 상대방에게 게임종료 메시지 전송
            this.sendJsonMessage(opponent, game, new GameForfeitedMessage());

            try{
                opponent.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * JSON 메시지를 보냄
     */
    private void sendJsonMessage(Session session, Game game, Message message) {
        try{
            session.getBasicRemote().sendText(TicTacToeServer.mapper.writeValueAsString(message));
        }catch(IOException e){
            this.handleException(e,game);
        }
    }

    /**
     * 게임 중 오류발생으로 종료될 때 사용자에게 오류메시지 전송
     */
    private void handleException(Throwable t, Game game) {
        t.printStackTrace();
        String message = t.toString();
        try {
            game.player1.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, message));
        } catch(IOException ignore) { }

        try {
            game.player2.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, message));
        } catch(IOException ignore) { }
    }

    private static class Game {
        public long gameId;
        public Session player1;
        public Session player2;
        public TicTacToeGame ticTacToeGame;
    }

    /**
     * 브라우저에서 서버로 전송되는 내용
     */
    public static class Move {
        private int row;
        private int column;
        public int getRow() {
            return row;
        }
        public void setRow(int row) {
            this.row = row;
        }
        public int getColumn() {
            return column;
        }
        public void setColumn(int column) {
            this.column = column;
        }
    }

    /**
     * 서버에서 브라우저로 전송되는 내용
     */
    public static abstract class Message {
        private final String action;
        public Message(String action) {
            this.action = action;
        }
        public String getAction() {
            return this.action;
        }
    }

    /**
     * 게임이 시작되었다는 것을 알리는 메시지 클래스
     */
    public static class GameStartedMessage extends Message {
        private final TicTacToeGame game;

        public GameStartedMessage(TicTacToeGame game) {
            super("gameStarted");
            this.game = game;
        }

        public TicTacToeGame getGame() {
            return game;
        }
    }

    /**
     * 움직일 수 없는 상태라는 것을 알리는 메시지 클래스
     */
    public static class OpponentMadeMoveMessage extends Message {
        private final Move move;
        public OpponentMadeMoveMessage(Move move) {
            super("opponentMadeMove");
            this.move = move;
        }
        public Move getMove()
        {
            return move;
        }
    }

    /**
     * 게임이 종료되었다는 것을 알리는 메시지 클래스
     */
    public static class GameOverMessage extends Message {
        private final boolean winner;
        public GameOverMessage(boolean winner) {
            super("gameOver");
            this.winner = winner;
        }
        public boolean isWinner()
        {
            return winner;
        }
    }

    /**
     * 게임이 비겼다는 것을 알리는 메시지 클래스
     */
    public static class GameIsDrawMessage extends Message {
        public GameIsDrawMessage() {
            super("gameIsDraw");
        }
    }

    /**
     * 상대방이 게임을 나갔다는 것을 알리는 메시지 클래스
     */
    public static class GameForfeitedMessage extends Message {
        public GameForfeitedMessage()
        {
            super("gameForfeited");
        }
    }
}

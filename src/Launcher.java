import chess.ChessController;
import chess.ChessView;
import chess.views.gui.GUIView;
import engine.ChessBoardController;

public class Launcher {

    public static void main(String[] args) {

        ChessController chessBoard = new ChessBoardController();
        ChessView view = new GUIView(chessBoard);
        chessBoard.start(view);

    }
}

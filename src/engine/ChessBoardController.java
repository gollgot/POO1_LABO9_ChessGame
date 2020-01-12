package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.pieces.*;

import java.util.ArrayList;
import java.util.Arrays;

public class ChessBoardController implements ChessController {
    private static final int dimension = 8;
    private ChessView view;
    private Cell[][] board;
    private int turn;
    private Piece whiteKing;
    private Piece blackKing;

    /**
     * Constructor, create the board  (array of 8 * 8 Cell)
     */
    public ChessBoardController() {
        board = new Cell[8][8];
        for (int row = 0; row < dimension; ++row) {
            for (int col = 0; col < dimension; ++col) {
                board[row][col] = new Cell(col, row);
            }
        }
        // /!\ Our board is board[row][col] => board[y][x]
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Cell fromCell = board[fromY][fromX];
        Cell toCell = board[toY][toX];

        if (fromCell.empty())
            return false;

        // Piece doing the move
        Piece p = fromCell.getPiece();

        // Even if we do an invalid move, display the chess if there is one
        if (isKingCheck(getOpponentColor(p.getColor())))
            view.displayMessage("Echec");


        // Odd turn is white player and even is black (turn begin to 1)
        // if(turn % 2 == 1 && p.getColor() != PlayerColor.WHITE || turn % 2 == 0 && p.getColor() != PlayerColor.BLACK)
            // return false;

        MoveType move = p.isValidMove(board, toX, toY, turn);

        // The move we want to do must be correct
        if (move == MoveType.IMPOSSIBLE)
            return false;

        // Specific board game update for castling movement type
        if (move == MoveType.KING_SIDE_CASTLE || move == MoveType.QUEEN_SIDE_CASTLE)
            return doCastle((King) p, move, toX, toY);

        // Simulate the move
        fromCell.removePiece();
        toCell.addPiece(p);

        // The move causes a check -> rollback
        if (isKingCheck(getOpponentColor(p.getColor()))) {
            toCell.removePiece();
            fromCell.addPiece(p);

            return false;
        }

        // The move saved the king or didn't cause a check -> the move can be applied to the view
        view.displayMessage("");
        view.removePiece(fromX, fromY);
        view.putPiece(p.getType(), p.getColor(), toX, toY);

        // Specific board game update for "En Passant" movement type
        if (move == MoveType.EN_PASSANT) {
            doEnPasant(fromY, toX, toY);
        }
        // Specific board game update for pawn promotion
        else if (move == MoveType.PROMOTION) {
            doPromotion(p);
        }

        ++turn;
        return true;
    }

    /**
     * Specific board game update for castling movement type
     * @param k The king doing the castle move
     * @param castleType While castle to do (KING_SIDE_CASTLE or QUEEN_SIDE_CASTLE)
     * @param toX Target X coordinate
     * @param toY Target Y coordinates
     * @return True if castle move was correct, otherwise false
     */
    private boolean doCastle(King k, MoveType castleType, int toX, int toY) {
        Cell kingOgCell = board[k.getY()][k.getX()];
        // get the kings next cells
        Cell kingNextCell = castleType == MoveType.KING_SIDE_CASTLE ? board[k.getY()][k.getX() + 1] : board[k.getY()][k.getX() - 1];
        Cell kingTargetCell = board[toY][toX];

        // check if the king is in check in his original cell
        if (isKingCheck(getOpponentColor(k.getColor()))) {
            k.setMoved(false);
            return false;
        }

        // move the king to the next cell and see if it puts him in `check`
        kingOgCell.removePiece();
        kingNextCell.addPiece(k);
        if (isKingCheck(getOpponentColor(k.getColor()))) {
            // yes, then put the king back to his og cell
            kingNextCell.removePiece();
            kingOgCell.addPiece(k);

            k.setMoved(false);

            return false;
        }

        // all gucci, now move to king to the target cell and check if puts him in `check`
        kingNextCell.removePiece();
        kingTargetCell.addPiece(k);
        if (isKingCheck(getOpponentColor(k.getColor()))) {
            // yes, then put the king back to his og cell
            kingTargetCell.removePiece();
            kingOgCell.addPiece(k);

            k.setMoved(false);

            return false;
        }

        view.putPiece(k.getType(), k.getColor(), kingTargetCell.getX(), kingTargetCell.getY());
        view.removePiece(kingOgCell.getX(), kingOgCell.getY());

        Cell oldRookCell = castleType == MoveType.KING_SIDE_CASTLE ? board[k.getY()][k.getX() + 1] : board[k.getY()][k.getX() - 2];
        Cell newRookCell = castleType == MoveType.KING_SIDE_CASTLE ? board[k.getY()][k.getX() - 1] : board[k.getY()][k.getX() + 1];

        // Add new rook on the board
        newRookCell.addPiece(oldRookCell.getPiece());
        view.putPiece(oldRookCell.getPiece().getType(), oldRookCell.getPiece().getColor(), newRookCell.getX(), newRookCell.getY());
        // Remove old rook from the board
        oldRookCell.removePiece();
        view.removePiece(oldRookCell.getX(), oldRookCell.getY());

        ++turn;

        return true;
    }

    /**
     * Specific board game update for "en passant" movement type
     * @param fromY From Y coordinate
     * @param toX To X coordinate
     * @param toY To Y coordinate
     */
    private void doEnPasant(int fromY, int toX, int toY) {
        int eateeYPos = fromY - toY > 0 ? toY + 1 : toY - 1;
        Cell eateeCell = board[eateeYPos][toX];

        eateeCell.removePiece();
        view.removePiece(eateeCell.getX(), eateeCell.getY());
    }

    /**
     * Specific board game update for the promotion movement type
     * @param p The Piece object which doing the promotion
     */
    private void doPromotion(Piece p) {
        // New piece suggestions
        Piece queen = new Queen(p.getColor());
        Piece knight = new Knight(p.getColor());
        Piece rook = new Rook(p.getColor());
        Piece bishop = new Bishop(p.getColor());

        Piece selectedPiece = view.askUser(
                "Promotion", "Choose to which piece you wish to promote to", queen, knight, rook, bishop
        );
        board[p.getY()][p.getX()].removePiece();
        board[p.getY()][p.getX()].addPiece(selectedPiece);
        view.removePiece(p.getX(), p.getY());
        view.putPiece(selectedPiece.getType(), selectedPiece.getColor(), selectedPiece.getX(), selectedPiece.getY());
    }

    /**
     * Check if a king is in check
     * @param opponentColor The color of the opponent
     * @return True if the king is in check, otherwise false
     */
    private boolean isKingCheck(PlayerColor opponentColor) {
        // get the king to test
        Piece king = opponentColor == PlayerColor.BLACK ? whiteKing : blackKing;

        for (int row = 0; row < dimension; ++row) {
            for (int col = 0; col < dimension; ++col) {
                Cell currentCell = board[row][col];

                if (currentCell.empty() || currentCell.getPiece().getColor() == king.getColor())
                    continue;

                if (currentCell.getPiece().isValidMove(board, king.getX(), king.getY(), turn + 1) != MoveType.IMPOSSIBLE)
                    return true;
            }
        }

        // no opponents were able to reach the king. He is safe!
        return false;
    }

    /**
     * Return the color of the opponent
     * @param color Player color
     * @return The color of the opponent
     */
    private PlayerColor getOpponentColor(PlayerColor color) {
        return color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    @Override
    public void newGame() {
        // set turn number to 1
        turn = 1;

        // Remove all piece on the board
        for (int row = 0; row < dimension; ++row) {
            for (int col = 0; col < dimension; ++col) {
                Cell currentCell = board[row][col];
                if (!currentCell.empty()) {
                    board[row][col].removePiece();
                }
            }
        }

        // Add all pawn
        for (int col = 0; col < dimension; ++col) {
            Piece pawnWhite = new Pawn(PlayerColor.WHITE);
            Piece pawnBlack = new Pawn(PlayerColor.BLACK);
            board[1][col].addPiece(pawnWhite);
            board[6][col].addPiece(pawnBlack);

            view.putPiece(pawnWhite.getType(), pawnWhite.getColor(), col, 1);
            view.putPiece(pawnBlack.getType(), pawnBlack.getColor(), col, 6);
        }


        whiteKing = new King(PlayerColor.WHITE);
        blackKing = new King(PlayerColor.BLACK);

        // White pieces
        ArrayList<Piece> whitePieces = new ArrayList<>(
            Arrays.asList(
                new Rook(PlayerColor.WHITE),
                new Knight(PlayerColor.WHITE),
                new Bishop(PlayerColor.WHITE),
                new Queen(PlayerColor.WHITE),
                whiteKing,
                new Bishop(PlayerColor.WHITE),
                new Knight(PlayerColor.WHITE),
                new Rook(PlayerColor.WHITE)
            )
        );

        // Black pieces
        ArrayList<Piece> blackPieces = new ArrayList<>(
            Arrays.asList(
                new Rook(PlayerColor.BLACK),
                new Knight(PlayerColor.BLACK),
                new Bishop(PlayerColor.BLACK),
                new Queen(PlayerColor.BLACK),
                blackKing,
                new Bishop(PlayerColor.BLACK),
                new Knight(PlayerColor.BLACK),
                new Rook(PlayerColor.BLACK)
            )
        );

        // Add all white pieces on the board and display them
        for(int i = 0; i < dimension; ++i){
            Piece p = whitePieces.get(i);
            // Add piece on the board
            board[0][i].addPiece(p);
            // Display piece
            view.putPiece(p.getType(), p.getColor(), i, 0);
        }

        // Add all black pieces on the board and display them
        for(int i = 0; i < dimension; ++i){
            Piece p = blackPieces.get(i);
            // Add piece on the board
            board[7][i].addPiece(p);
            // Display piece
            view.putPiece(p.getType(), p.getColor(), i, 7);
        }
    }
}

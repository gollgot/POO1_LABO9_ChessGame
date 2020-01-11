package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.*;

public class King extends Restricted {

    /**
     * Constructor
     * @param color The Piece color
     */
    public King(PlayerColor color) {
        super(
                PieceType.KING,
                color,
                new Move[]{
                        new Vertical(Direction.UP),
                        new Vertical(Direction.DOWN),
                        new Horizontal(Direction.LEFT),
                        new Horizontal(Direction.RIGHT),
                        new Diagonal(Direction.DIAG_TOP_LEFT),
                        new Diagonal(Direction.DIAG_TOP_RIGHT),
                        new Diagonal(Direction.DIAG_BOT_LEFT),
                        new Diagonal(Direction.DIAG_BOT_RIGHT)
                },
                1
        );
    }

    /**
     * Check if the move is valid
     * @param board The current board game
     * @param toX ToX coordinate
     * @param toY ToY coordinate
     * @param turn The turn (begin to 1 (white))
     * @return True if the move is valid, false otherwise
     */
    public MoveType isValidMove(Cell[][] board, int toX, int toY, int turn) {
        // Check the normal move into the super class
        MoveType move = super.isValidMove(board, toX, toY, turn);

        // Check specific move if normal failed
        if (move == MoveType.IMPOSSIBLE) {
            // Check castling
            if (isKingSideCastle(board, getX(), getY(), toX, toY)) {
                move = MoveType.KING_SIDE_CASTLE;
            } else if (isQueenSideCastle(board, getX(), getY(), toX, toY)) {
                move = MoveType.QUEEN_SIDE_CASTLE;
            }

            // Update already move only for the first move we did
            if (move != MoveType.IMPOSSIBLE && !alreadyMoved()) {
                setMoved(true);
            }
        }

        return move;
    }

    /**
     * Check if the current move can be a castle one
     * @param board The current game board
     * @param rookCell The Rook cell
     * @param move The move
     * @param fromX The fromX coordinate
     * @param fromY The fromY coordinate
     * @return True if the move can be a castle one, false otherwise
     */
    private boolean isCastle(Cell[][] board, Cell rookCell, Move move, int fromX, int fromY) {
        if (rookCell.empty() || rookCell.getPiece().getType() != PieceType.ROOK)
            return false;

        Rook rook = (Rook) rookCell.getPiece();

        if (rook.alreadyMoved()) return false;

        return move.isPathClear(
                board, fromX, fromY, rookCell.getX(), rookCell.getY(), 8, getColor()
        );
    }

    /**
     * Check if the move can be a King side castle
     * @param board The current game board
     * @param fromX The fromX coordinate
     * @param fromY The fromY coordinate
     * @param toX The toX coordinate
     * @param toY The toY coordinate
     * @return True if the move is a King side castle
     */
    private boolean isKingSideCastle(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        if (alreadyMoved() || (toX != 6 && toY != getY())) return false;

        Cell rookCell = board[getY()][7];
        Move rightMove = new Horizontal(Direction.RIGHT);

        return isCastle(board, rookCell, rightMove, fromX, fromY);
    }

    /**
     * Check if the move can be a Queen side castle
     * @param board The current game board
     * @param fromX The fromX coordinate
     * @param fromY The fromY coordinate
     * @param toX The toX coordinate
     * @param toY The toY coordinate
     * @return True if the move is a Queen side castle
     */
    private boolean isQueenSideCastle(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        if (alreadyMoved() || (toX != 3 && toY != getY())) return false;

        Cell rookCell = board[getY()][0];
        Move leftMove = new Horizontal(Direction.LEFT);

        return isCastle(board, rookCell, leftMove, fromX, fromY);
    }

    @Override
    public String toString() {
        return "King";
    }

}

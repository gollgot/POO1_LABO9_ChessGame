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
     * @param board Board
     * @param toX Target X coordinate
     * @param toY Target Y coordinate
     * @param turn Turn n°
     * @return True if the move is valid, otherwise false
     */
    public MoveType isValidMove(Cell[][] board, int toX, int toY, int turn) {
        // Check the normal move into the super class
        MoveType move = super.isValidMove(board, toX, toY, turn);

        // If the normal move failed BUT it's the kings first time moving,
        // if might by a special move i.e. Castle
        if (move == MoveType.IMPOSSIBLE && !alreadyMoved()) {
            // Check castling
            if (isKingSideCastle(board, toX, toY)) {
                move = MoveType.KING_SIDE_CASTLE;
            } else if (isQueenSideCastle(board, toX, toY)) {
                move = MoveType.QUEEN_SIDE_CASTLE;
            }
        }

        // if it was the kings first movement, update the flag
        if (move != MoveType.IMPOSSIBLE && !alreadyMoved())
            setMoved(true);

        return move;
    }

    /**
     * Check if a move is a castle
     * @param board Board
     * @param rookCell The Rook cell
     * @param move Move to check
     * @return True if the move is a castle, otherwise false
     */
    private boolean isCastle(Cell[][] board, Cell rookCell, Move move) {
        if (rookCell.empty() || rookCell.getPiece().getType() != PieceType.ROOK)
            return false;

        Rook rook = (Rook) rookCell.getPiece();

        if (rook.alreadyMoved()) return false;

        return move.isPathClear(
                board, getX(), getY(), rookCell.getX(), rookCell.getY(), 8, getColor()
        );
    }

    /**
     * Check if the wanted move is a King side castle
     * @param board Board
     * @param toX Target X coordinate
     * @param toY Target Y coordinate
     * @return True if it's a King side castle
     */
    private boolean isKingSideCastle(Cell[][] board, int toX, int toY) {
        if (alreadyMoved() || (toX != 6 || toY != getY())) return false;

        Cell rookCell = board[getY()][7];
        Move rightMove = new Horizontal(Direction.RIGHT);

        return isCastle(board, rookCell, rightMove);
    }

    /**
     * Check if the wanted move is a Queen side castle
     * @param board Board
     * @param toX Target X coordinate
     * @param toY Target Y coordinate
     * @return True if it's a Queen side castle
     */
    private boolean isQueenSideCastle(Cell[][] board, int toX, int toY) {
        if (alreadyMoved() || (toX != 3 && toY != getY())) return false;

        Cell rookCell = board[getY()][0];
        Move leftMove = new Horizontal(Direction.LEFT);

        return isCastle(board, rookCell, leftMove);
    }

    @Override
    public String toString() {
        return "King";
    }

}

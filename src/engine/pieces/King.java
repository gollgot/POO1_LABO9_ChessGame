package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.*;

public class King extends Restricted {

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

    private boolean isCastle(Cell[][] board, Cell rookCell, Move move, int fromX, int fromY) {
        if (rookCell.empty() || rookCell.getPiece().getType() != PieceType.ROOK)
            return false;

        Rook rook = (Rook) rookCell.getPiece();

        if (rook.alreadyMoved()) return false;

        return move.isClickedCellAndWayValid(
                board, fromX, fromY, rookCell.getX(), rookCell.getY(), 8, getColor()
        );
    }

    private boolean isKingSideCastle(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        if (alreadyMoved() || (toX != 6 && toY != getY())) return false;

        Cell rookCell = board[getY()][7];
        Move rightMove = new Horizontal(Direction.RIGHT);

        return isCastle(board, rookCell, rightMove, fromX, fromY);
    }

    private boolean isQueenSideCastle(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        if (alreadyMoved() || (toX != 3 && toY != getY())) return false;

        Cell rookCell = board[getY()][0];
        Move leftMove = new Horizontal(Direction.LEFT);

        return isCastle(board, rookCell, leftMove, fromX, fromY);
    }


    @Override
    public String textValue() {
        return "Roi";
    }
}

package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Diagonal;
import engine.movements.Direction;
import engine.movements.Move;
import engine.movements.Vertical;

public class Pawn extends Restricted {
    public Pawn(PlayerColor color) {
        super(
                PieceType.PAWN,
                color,
                new Move[]{
                        new Vertical(Direction.UP),
                },
                1
        );
    }

    public MoveType isValidMove(Cell[][] board, int toX, int toY, int turn) {
        MoveType movement = MoveType.IMPOSSIBLE;
        int distance = alreadyMoved() ? getDistance() : 2; // First time, can move a distance 2

        // Check normal move
        for (Move move : getMoves()) {
            if (move.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, distance, getColor()) &&
                    board[toY][toX].empty()) {
                movement = MoveType.NORMAL;
                break;
            }
        }

        // if the move isn't a normal, it's could ba a special
        movement = movement != MoveType.NORMAL ? specialMove(board, toX, toY, turn) : movement;

        if (movement != MoveType.IMPOSSIBLE) {
            // update the last turn the current pawn was played
            setLastPlayedTurn(turn);

            // if it's the pawns first movement, update already move flag
            if (!alreadyMoved()) setMoved(true);
        }

        return movement;
    }

    private MoveType specialMove(Cell[][] board, int toX, int toY, int turn) {
        Diagonal topLeft = new Diagonal(Direction.DIAG_TOP_LEFT);
        Diagonal topRight = new Diagonal(Direction.DIAG_TOP_RIGHT);

        if (topLeft.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor()) ||
            topRight.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor())) {

            if (eating(board[toY][toX]))
                return MoveType.NORMAL;

            // check if the pawn is doing an `En passant`
            if (enPassant(board, toX, toY, turn))
                return MoveType.EN_PASSANT;
        }

        if (promotable(toY))
            return MoveType.PROMOTION;

        return MoveType.IMPOSSIBLE;
    }

    private boolean eating(Cell targetCell) {
        return !targetCell.empty() && targetCell.getPiece().getColor() != getColor();
    }

    private boolean enPassant(Cell[][] board, int toX, int toY, int turn) {
        // check if the pawn is doing an `En passant`
        int eateeYPos = getY() - toY > 0 ? toY + 1 : toY - 1;
        if (!board[toY][toX].empty() || board[eateeYPos][toX].empty())
            return false;

        Piece eatee = board[eateeYPos][toX].getPiece();
        if (eatee.getType() == PieceType.PAWN && eatee.getColor() != getColor() && (turn - eatee.getLastPlayedTurn()) == 1) {
            return true;
        }

        return false;
    }

    private boolean promotable(int toY){
        int lastRow = getColor() == PlayerColor.WHITE ? 7 : 0;
        return toY == lastRow && getY() == Math.abs(lastRow - 1);
    }

    @Override
    public String textValue() {
        return "Pion";
    }
}

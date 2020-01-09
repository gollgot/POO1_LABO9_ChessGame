package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Diagonal;
import engine.movements.Direction;
import engine.movements.Move;
import engine.movements.Vertical;

public class Pawn extends Piece {
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
        int distance = isAlreadyMoved() ? getDistance() : 2; // First time, can move a distance 2

        // Check normal move
        for (Move move : getMoves()) {
            if (move.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, distance, getColor()) &&
                    board[toY][toX].empty()) {
                movement = MoveType.NORMAL;
                break;
            }
        }

        Diagonal topLeft = new Diagonal(Direction.DIAG_TOP_LEFT);
        Diagonal topRight = new Diagonal(Direction.DIAG_TOP_RIGHT);

        // check if there's a valid diagonal movement
        if (topLeft.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor()) ||
                topRight.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor())) {

            // check if the pawn is eating another piece
            if (!board[toY][toX].empty() && board[toY][toX].getPiece().getColor() != getColor()) {
                movement = MoveType.NORMAL;
            }

            // check if the pawn is doing an `En passant`
            int eateeYPos = getY() - toY > 0 ? toY + 1 : toY - 1;
            if (!board[toY][toX].empty() || board[eateeYPos][toX].empty())
                return MoveType.IMPOSSIBLE;

            Piece eatee = board[eateeYPos][toX].getPiece();
            if (eatee.getType() == PieceType.PAWN && eatee.getColor() != getColor() && (turn - eatee.getLastTurnPlayed()) == 1) {
                movement = MoveType.EN_PASSANT;
            }
        }

        if (movement != MoveType.IMPOSSIBLE) {
            // update the last turn the current pawn was played
            setLastTurnPlayed(turn);

            // if it's the pawns first movement, update already move flag
            if (!isAlreadyMoved()) setAlreadyMoved(true);
        }

        return movement;
    }
}

package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Diagonal;
import engine.movements.Direction;
import engine.movements.Move;
import engine.movements.Vertical;

public class Pawn extends Piece {

    public Pawn(PlayerColor color){
        super(
            PieceType.PAWN,
            color,
            new Move[]{
                new Vertical(Direction.UP),
            },
            1
        );
    }

    @Override
    public boolean isValidMove(Cell[][] board, int toX, int toY) {
        boolean isValidMove = false;
        int distance = isAlreadyMoved() ? getDistance() : 2; // First time, can move a distance 2

        // Check normal move
        for(Move move : getMoves()) {
            if(move.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, distance, getColor()) && board[toY][toX].empty()){
                isValidMove = true;
                break;
            }
        }

        Diagonal topLeft = new Diagonal(Direction.DIAG_TOP_LEFT);
        Diagonal topRight = new Diagonal(Direction.DIAG_TOP_RIGHT);

        if ((topLeft.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor()) ||
            topRight.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor())) &&
            (!board[toY][toX].empty() && board[toY][toX].getPiece().getColor() != getColor())) {

            return true;
        }

        // Update already move only for the first move we did
        if(isValidMove && !isAlreadyMoved()){
            setAlreadyMoved(true);
        }

        return isValidMove;
    }
}

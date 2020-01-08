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
                        new Diagonal(Direction.DIAG_TOP_LEFT),
                        new Diagonal(Direction.DIAG_TOP_RIGHT)
                },
                1
        );
    }

    @Override
    public boolean isValidMove(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        boolean isValidMove = false;
        int distance = isAlreadyMoved() ? getDistance() : 2; // First time, can move a distance 2

        for(Move move : getMoves()){
            if(move.isValid(board, fromX, fromY, toX, toY, distance, getColor())){
                isValidMove = true;
                if(!isAlreadyMoved()){
                    setAlreadyMoved(true);
                }
                break;
            }
        }

        return isValidMove;
    }


}

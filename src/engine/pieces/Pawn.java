package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
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
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        boolean isValidMove = false;
        int distance = isAlreadyMoved() ? getDistance() : 2; // First time, can move a distance 2
        Direction dirToMove;

        for(Move move : getMoves()){
           dirToMove = move.isValid(fromX, fromY, toX, toY, distance, getColor());
           switch(dirToMove){
               case DIAG_TOP_LEFT:
               case DIAG_TOP_RIGHT:
               case UP:
                   // Check obstacle etc...
                   // ...
                   // All check ok -> move

                   if(!isAlreadyMoved()){
                       setAlreadyMoved(true);
                   }
                   isValidMove = true;
                   break;
               default:
                   break;
           }
        }

        return isValidMove;
    }


}
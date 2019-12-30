package engine;

import chess.PieceType;
import chess.PlayerColor;

class Pawn extends Piece{

    Pawn(PieceType type, PlayerColor color, Move[] moves, int distance){
        super(type, color, moves, distance);
    }

    @Override
    boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        int distance = isAlreadyMoved() ? getDistance() : 2; // First time, can move a distance 2
        Direction dirToMove;

        for(Move move : getMoves()){
           dirToMove = move.isValid(fromX, fromY, toX, toY, distance);
           switch(dirToMove){
               case INVALID:
                   System.out.println("INVALID movement");
                   return false;
               default:
                   System.out.println("VALID movement");
                   return true;
           }
        }

        return false;
    }


}

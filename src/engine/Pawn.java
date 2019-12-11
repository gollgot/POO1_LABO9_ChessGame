package engine;

import chess.PieceType;
import chess.PlayerColor;

class Pawn extends Piece{

    Pawn(PieceType type, PlayerColor color){
        super(type, color);
    }

    @Override
    boolean isMoveable(int targetX, int targetY) {
        return false;
    }

}

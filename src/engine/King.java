package engine;

import chess.PieceType;
import chess.PlayerColor;

class King extends Piece {

    King(PieceType type, PlayerColor color, Move[] moves, int distance){
        super(type, color, moves, distance);
    }

    @Override
    boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}

package engine;

import chess.PieceType;
import chess.PlayerColor;

class Bishop extends Piece{

    Bishop(PieceType type, PlayerColor color, Move[] moves, int distance){
        super(type, color, moves, distance);
    }

    @Override
    boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}

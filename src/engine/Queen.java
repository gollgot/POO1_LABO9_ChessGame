package engine;

import chess.PieceType;
import chess.PlayerColor;

class Queen extends Piece {

    Queen(PieceType type, PlayerColor color, Move[] moves, int distance){
        super(type, color, moves, distance);
    }

    @Override
    boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}

package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.Move;

public class Knight extends Piece {

    public Knight(PlayerColor color, Move[] moves, int distance){
        super(PieceType.KNIGHT, color, moves, distance);
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}

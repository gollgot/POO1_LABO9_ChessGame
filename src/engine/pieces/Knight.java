package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.Move;

public class Knight extends Piece {

    public Knight(PlayerColor color){
        super(
                PieceType.KNIGHT,
                color,
                new Move[0],
                3 // Or 1 ? a voir
        );
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return false;
    }
}

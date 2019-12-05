package engine;

import chess.PieceType;
import chess.PlayerColor;

abstract class Piece {
    private int x;
    private int y;
    private PlayerColor color;
    private PieceType type;

    abstract boolean isMoveable(int targetX, int targetY);

}

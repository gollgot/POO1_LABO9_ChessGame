package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.Move;

public abstract class Restricted extends Piece {

    private boolean moved = false;

    public boolean alreadyMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public Restricted(PieceType type, PlayerColor color, Move[] moves, int distance) {
        super(type, color, moves, distance);
    }
}

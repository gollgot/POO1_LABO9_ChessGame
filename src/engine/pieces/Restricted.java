package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.Move;

public abstract class Restricted extends Piece {

    private boolean moved = false;

    /**
     * Restricted constructor
     * @param type The Piece type
     * @param color The Piece color
     * @param moves All moves that the Piece can do
     * @param distance The maximum distance that the piece can move
     */
    public Restricted(PieceType type, PlayerColor color, Move[] moves, int distance) {
        super(type, color, moves, distance);
    }

    // Getter
    public boolean alreadyMoved() {
        return moved;
    }

    // Setter
    public void setMoved(boolean moved) {
        this.moved = moved;
    }

}

package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;

abstract class Piece {
    private Cell cell;
    private PlayerColor color;
    private PieceType type;

    Piece( PieceType type, PlayerColor color) {
        this.color = color;
        this.type = type;
    }

    abstract boolean isMoveable(int targetX, int targetY);

    // Getters
    PlayerColor getColor() {
        return color;
    }

    PieceType getType() {
        return type;
    }

    // Setters
    void setCell(Cell cell) {
        this.cell = cell;
    }

}

package engine;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;

abstract class Piece {
    private Cell cell;
    private PlayerColor color;
    private PieceType type;
    private int distance;
    private Move[] moves;
    private boolean alreadyMoved = false;

    Piece(PieceType type, PlayerColor color, Move[] moves, int distance) {
        this.color = color;
        this.type = type;
        this.distance = distance;
        this.moves = new Move[moves.length];
        for(int i = 0; i < this.moves.length; ++i){
            this.moves[i] = moves[i];
        }
    }

    abstract boolean isValidMove(int fromX, int fromY, int toX, int toY);

    // Getters
    PlayerColor getColor() {
        return color;
    }

    PieceType getType() {
        return type;
    }

    int getDistance(){
        return distance;
    }

    Move[] getMoves() {
        return moves;
    }

    boolean isAlreadyMoved(){
        return alreadyMoved;
    }

    // Setters
    void setCell(Cell cell) {
        this.cell = cell;
    }

    void setAlreadyMoved(boolean b){
        this.alreadyMoved = b;
    }

    @Override
    public String toString() {
        return "x: " + cell.getX() + " y : " + cell.getY();
    }
}

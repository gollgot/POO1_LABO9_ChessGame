package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Move;

public abstract class Piece {
    private Cell cell;
    private PlayerColor color;
    private PieceType type;
    private int distance;
    private Move[] moves;
    private boolean alreadyMoved = false;

    public Piece(PieceType type, PlayerColor color, Move[] moves, int distance) {
        this.color = color;
        this.type = type;
        this.distance = distance;
        this.moves = new Move[moves.length];
        for(int i = 0; i < this.moves.length; ++i){
            this.moves[i] = moves[i];
        }
    }

    public abstract boolean isValidMove(Cell[][] board, int fromX, int fromY, int toX, int toY);

    // Getters
    public PlayerColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public int getDistance(){
        return distance;
    }

    public Move[] getMoves() {
        return moves;
    }

    public boolean isAlreadyMoved(){
        return alreadyMoved;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    // Setters
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public void setAlreadyMoved(boolean b){
        this.alreadyMoved = b;
    }

    @Override
    public String toString() {
        return "x: " + cell.getX() + " y : " + cell.getY();
    }
}

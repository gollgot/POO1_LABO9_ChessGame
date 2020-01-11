package engine;

import engine.pieces.Piece;

public class Cell {
    private int x;
    private int y;
    private Piece piece;


    public Cell(int x, int y) {
        this.y = y;
        this.x = x;
    }

    public boolean empty() {
        return piece == null;
    }

    public void removePiece() {
        piece = null;
    }

    public void addPiece(Piece p) {
        piece = p;
        piece.setCell(this);
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Piece getPiece() {
        return piece;
    }
}

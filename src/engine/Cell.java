package engine;

import engine.Piece;

class Cell {
    private int x;
    private int y;
    private Piece piece;


    Cell(int x, int y){
        this.y = y;
        this.x = x;
    }

    boolean empty() {
        return piece == null;
    }

    void removePiece(){

    }

    void addPiece(Piece p){
        piece = p;
        piece.setCell(this);
    }

    // Getters
    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    Piece getPiece() {
        return piece;
    }
}

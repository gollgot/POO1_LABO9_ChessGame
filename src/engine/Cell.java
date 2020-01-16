package engine;

import engine.pieces.Piece;

public class Cell {
    private int x;
    private int y;
    private Piece piece;

    /**
     * Constructor
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     */
    public Cell(int x, int y) {
        this.y = y;
        this.x = x;
    }

    /**
     * Check if the Cell is occupied
     *
     * @return True if there is a piece on the Cell, false otherwise
     */
    public boolean empty() {
        return piece == null;
    }

    /**
     * Remove the Piece on the cell
     */
    public void removePiece() {
        piece.setCell(null);
        piece = null;
    }

    /**
     * Add a piece on the Cell
     *
     * @param p The Piece that will be add on the Cell
     */
    public void addPiece(Piece p) {
        if (piece != null)
            removePiece();
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

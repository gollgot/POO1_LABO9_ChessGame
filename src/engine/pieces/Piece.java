package engine.pieces;

import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Move;

public abstract class Piece implements ChessView.UserChoice {
    private Cell cell;
    private PlayerColor color;
    private PieceType type;
    private int distance;
    private Move[] moves;
    private int lastPlayedTurn;

    public Piece(PieceType type, PlayerColor color, Move[] moves, int distance) {
        this.color = color;
        this.type = type;
        this.distance = distance;
        this.moves = new Move[moves.length];
        for(int i = 0; i < this.moves.length; ++i){
            this.moves[i] = moves[i];
        }
    }

    public MoveType isValidMove(Cell[][] board, int toX, int toY, int turn) {

        // Check normal move
        for (Move move : getMoves()) {
            if (move.isPathClear(board, getX(), getY(), toX, toY, getDistance(), getColor()) && move.isDestinationOccupied(board, toX, toY, getColor())) {
                lastPlayedTurn = turn;
                return MoveType.NORMAL;
            }
        }

        return MoveType.IMPOSSIBLE;
    }

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

    /*
    public boolean isAlreadyMoved(){
        return alreadyMoved;
    }
     */

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getLastPlayedTurn() {
        return lastPlayedTurn;
    }

    // Setters
    public void setCell(Cell cell) {
        this.cell = cell;
    }

    /*
    public void setAlreadyMoved(boolean b){
        this.alreadyMoved = b;
    }
     */

    public void setLastPlayedTurn(int turn) {
        this.lastPlayedTurn = turn;
    }

}

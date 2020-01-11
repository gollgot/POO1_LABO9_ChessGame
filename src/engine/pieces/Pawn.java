package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Diagonal;
import engine.movements.Direction;
import engine.movements.Move;
import engine.movements.Vertical;

public class Pawn extends Restricted {

    /**
     * Constructor
     * @param color The Piece color
     */
    public Pawn(PlayerColor color) {
        super(
                PieceType.PAWN,
                color,
                new Move[]{
                        new Vertical(Direction.UP),
                },
                1
        );
    }

    /**
     * Check if the move is valid
     * @param board The current board game
     * @param toX ToX coordinate
     * @param toY ToY coordinate
     * @param turn The turn (begin to 1 (white))
     * @return True if the move is valid, false otherwise
     */
    public MoveType isValidMove(Cell[][] board, int toX, int toY, int turn) {
        MoveType movement = MoveType.IMPOSSIBLE;
        int distance = alreadyMoved() ? getDistance() : 2; // First time, can move a distance 2

        // Check normal move
        for (Move move : getMoves()) {
            if (move.isPathClear(board, getX(), getY(), toX, toY, distance, getColor()) &&
                    board[toY][toX].empty()) {
                movement = MoveType.NORMAL;
                break;
            }
        }

        // if the move isn't a normal, it's could ba special :o
        movement = movement != MoveType.NORMAL ? specialMove(board, toX, toY, turn) : movement;


        if (movement != MoveType.IMPOSSIBLE) {

            // the movement might result into a promotion
            // such a lucky pawn
            if (promotable(board[toY][toX]))
                movement = MoveType.PROMOTION;

            // update the last turn the current pawn was played
            setLastPlayedTurn(turn);

            // if it's the pawns first movement, update movement flag
            if (!alreadyMoved()) setMoved(true);
        }

        return movement;
    }

    /**
     * Check if the move is a special move and return it
     * @param board The current game board
     * @param toX The toX coordinate
     * @param toY The toY coordinate
     * @param turn The turn (begin to 1 (white))
     * @return The type of move that is playing (possibly special)
     */
    private MoveType specialMove(Cell[][] board, int toX, int toY, int turn) {
        Diagonal topLeft = new Diagonal(Direction.DIAG_TOP_LEFT);
        Diagonal topRight = new Diagonal(Direction.DIAG_TOP_RIGHT);

        if (topLeft.isPathClear(board, getX(), getY(), toX, toY, getDistance(), getColor()) ||
            topRight.isPathClear(board, getX(), getY(), toX, toY, getDistance(), getColor())) {

            if (eating(board[toY][toX]))
                return MoveType.NORMAL;

            // check if the pawn is doing an `En passant`
            if (enPassant(board, toX, toY, turn))
                return MoveType.EN_PASSANT;
        }

        return MoveType.IMPOSSIBLE;
    }

    /**
     * Check if the target cell can be eat
     * @param targetCell The target Cell
     * @return True if the target cell can be eat, false otherwise
     */
    private boolean eating(Cell targetCell) {
        return !targetCell.empty() && targetCell.getPiece().getColor() != getColor();
    }

    /**
     * Check if the move is a "enPassant" move
     * @param board The current game board
     * @param toX The toX coordinate
     * @param toY The toY coordinate
     * @param turn The turn (begin to 1 (white))
     * @return True if the move is "enPassant", false otherwise
     */
    private boolean enPassant(Cell[][] board, int toX, int toY, int turn) {
        // check if the pawn is doing an `En passant`
        int eateeYPos = getY() - toY > 0 ? toY + 1 : toY - 1;
        if (!board[toY][toX].empty() || board[eateeYPos][toX].empty())
            return false;

        Piece eatee = board[eateeYPos][toX].getPiece();
        if (eatee.getType() == PieceType.PAWN && eatee.getColor() != getColor() && (turn - eatee.getLastPlayedTurn()) == 1) {
            return true;
        }

        return false;
    }

    /**
     * Check if the move is a "promotable"
     * @param targetCell The target Cell
     * @return True if the move is "promotable", false otherwise
     */
    private boolean promotable(Cell targetCell) {
        int lastRow = getColor() == PlayerColor.WHITE ? 7 : 0;
        return targetCell.getY() == lastRow && getY() == Math.abs(lastRow - 1);
    }

    @Override
    public String toString() {
        return "Pawn";
    }

}

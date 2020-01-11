package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Move;

public class Knight extends Piece {

    /**
     * Constructor
     * @param color The Piece color
     */
    public Knight(PlayerColor color) {
        super(
                PieceType.KNIGHT,
                color,
                new Move[0],
                3
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
        int yGap = getY() - toY;
        int xGap = getX() - toX;

        Cell toCell = board[toY][toX];
        if ((Math.abs(yGap) == 2 && Math.abs(xGap) == 1 || Math.abs(xGap) == 2 && Math.abs(yGap) == 1) &&
                (toCell.empty() || toCell.getPiece().getColor() != getColor())) {
            setLastPlayedTurn(turn);
            return MoveType.NORMAL;
        }

        return MoveType.IMPOSSIBLE;
    }

    @Override
    public String toString() {
        return "Knight";
    }

}

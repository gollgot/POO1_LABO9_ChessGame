package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Move;

public class Knight extends Piece {

    public Knight(PlayerColor color){
        super(
            PieceType.KNIGHT,
            color,
            new Move[0],
            3
        );
    }

    @Override
    public boolean isValidMove(Cell[][] board, int toX, int toY) {

        int yGap = getY() - toY;
        int xGap = getX() - toX;

        Cell toCell = board[toY][toX];
        return (Math.abs(yGap) == 2 && Math.abs(xGap) == 1 || Math.abs(xGap) == 2 && Math.abs(yGap) == 1) &&
                (toCell.empty() || toCell.getPiece().getColor() != getColor());
    }
}

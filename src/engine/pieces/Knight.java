package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Move;

public class Knight extends Piece {

    public Knight(PlayerColor color) {
        super(
                PieceType.KNIGHT,
                color,
                new Move[0],
                3
        );
    }

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
    public String textValue() {
        return "Knight";
    }
}

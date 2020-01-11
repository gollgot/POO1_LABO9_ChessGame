package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public class Diagonal implements Move {

    private Direction direction;

    public Diagonal(Direction direction) {
        if (direction != Direction.DIAG_TOP_LEFT && direction != Direction.DIAG_TOP_RIGHT &&
                direction != Direction.DIAG_BOT_LEFT && direction != Direction.DIAG_BOT_RIGHT)
            throw new RuntimeException("Vertical move can have only UP or DOWN direction");

        this.direction = direction;
    }

    @Override
    public boolean isDestinationOccupied(Cell[][] board, int toX, int toY, PlayerColor playerColor) {
        // Check if the cell we want to go is empty or eatable
        Cell toCell = board[toY][toX];
        return toCell.empty() || toCell.getPiece().getColor() != playerColor;
    }

    @Override
    public boolean isPathClear(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {
        int horizontalGap = direction == Direction.DIAG_TOP_LEFT || direction == Direction.DIAG_BOT_LEFT ? fromX - toX : toX - fromX;
        int verticalMultiplier = direction == Direction.DIAG_TOP_LEFT || direction == Direction.DIAG_TOP_RIGHT ? 1 : -1;
        int colorMultiplier = playerColor == PlayerColor.WHITE ? 1 : -1;
        int verticalGap = playerColor == PlayerColor.WHITE ? (toY - fromY) * verticalMultiplier : (fromY - toY) * verticalMultiplier;
        int gap = -1;

        if (horizontalGap == verticalGap) {
            gap = horizontalGap;
        }

        boolean isClickedCellValid = gap <= distance && gap >= 0;
        if (!isClickedCellValid) {
            return false;
        }
        // Check if no piece is on the diagonal way (dont check the last)
        else {
            for (int i = 1; i < gap; ++i) {
                int row = direction == Direction.DIAG_TOP_LEFT || direction == Direction.DIAG_TOP_RIGHT ? fromY + (i * colorMultiplier) : fromY - (i * colorMultiplier);
                int col = direction == Direction.DIAG_TOP_LEFT || direction == Direction.DIAG_BOT_LEFT ? fromX - i : fromX + i;
                // If the cell is not empty -> error there is a piece on the way
                if (!board[row][col].empty()) {
                    return false;
                }
            }

            return true;
        }
    }
}

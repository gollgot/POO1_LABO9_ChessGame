package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public class Vertical implements Move {

    private Direction direction;

    /**
     * Constructor
     *
     * @param direction The Direction for which we want to move
     * @throws RuntimeException if direction is something else than UP or DOWN
     */
    public Vertical(Direction direction) {
        if (direction != Direction.UP && direction != Direction.DOWN) {
            throw new RuntimeException("Vertical move can have only UP or DOWN direction");
        }
        this.direction = direction;
    }


    @Override
    public boolean isDestinationTakable(Cell[][] board, int toX, int toY, PlayerColor playerColor) {
        // Check if the cell we want to go is empty or eatable
        Cell toCell = board[toY][toX];
        return toCell.empty() || toCell.getPiece().getColor() != playerColor;
    }

    @Override
    public boolean isPathClear(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance,
                               PlayerColor playerColor) {
        // Multiplier to inverse the way because black and white is the opposite
        int colorMultiplier = playerColor == PlayerColor.WHITE ? 1 : -1;
        // The gap value depends on the direction choose (UP our DOWN)
        int gap = direction == Direction.UP ? (toY - fromY) * colorMultiplier : (fromY - toY) * colorMultiplier;

        // Clicked cell is valid
        boolean isClickedCellValid = fromX == toX && gap <= distance && gap >= 0;

        // Stop here if the clicked cell if wrong
        if (!isClickedCellValid) {
            return false;
        }
        // Check if no piece is on the vertical way (dont check the last)
        else {
            // Loop through all cell on the gap (except last cell, the one that can maybe be eatable)
            for (int i = 1; i < gap; ++i) {
                int row = direction == Direction.UP ? fromY + (i * colorMultiplier) : fromY - (i * colorMultiplier);
                int col = fromX;
                // If the cell is not empty -> error there is a piece on the way
                if (!board[row][col].empty()) {
                    return false;
                }
            }

            return true;
        }
    }

}

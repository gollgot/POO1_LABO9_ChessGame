package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public class Horizontal implements Move {

    private Direction direction;

    /**
     * Constructor
     * @param direction The Direction for which we want to move
     * @throws RuntimeException if direction is something else than LEFT or RIGHT
     */
    public Horizontal(Direction direction) {
        if (direction != Direction.LEFT && direction != Direction.RIGHT) {
            throw new RuntimeException("Horizontal move can have only LEFT or RIGHT direction");
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
    public boolean isPathClear(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {
        // The gap value depends on the direction choose (LEFT our RIGHT)
        int gap = direction == Direction.LEFT ? fromX - toX : toX - fromX;

        boolean isClickedCellValid = fromY == toY && gap <= distance && gap >= 0;
        if (!isClickedCellValid) {
            return false;
        }
        // Check if no piece is on the horizontal way (dont check the last)
        else {
            for (int i = 1; i < gap; ++i) {
                int row = fromY;
                int col = direction == Direction.LEFT ? fromX - i : fromX + i;
                // If the cell is not empty -> error there is a piece on the way
                if (!board[row][col].empty()) {
                    return false;
                }
            }
        }


        return true;
    }


}

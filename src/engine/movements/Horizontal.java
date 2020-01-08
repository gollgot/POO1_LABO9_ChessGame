package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public class Horizontal implements Move{

    private Direction direction;

    public Horizontal(Direction direction) {
        if(direction != Direction.LEFT && direction != Direction.RIGHT){
            throw new RuntimeException("Horizontal move can have only LEFT or RIGHT direction");
        }
        this.direction = direction;
    }

    @Override
    public boolean isValid(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {
        // The gap value depends on the direction choose (LEFT our RIGHT)
        int gap = direction == Direction.LEFT ? fromX - toX : toX - fromX;

        // Correct move
        return fromY == toY && gap <= distance && gap >= 0;
    }
}

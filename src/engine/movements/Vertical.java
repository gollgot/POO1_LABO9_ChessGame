package engine.movements;

import chess.PlayerColor;

public class Vertical implements Move{

    private Direction direction;

    public Vertical (Direction direction) {
        if(direction != Direction.UP && direction != Direction.DOWN){
            throw new RuntimeException("Vertical move can have only UP or DOWN direction");
        }
        this.direction = direction;
    }

    @Override
    public Direction isValid(int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {
        // Multiplier to inverse the way because black and white is the opposite
        int colorMultiplier = playerColor == PlayerColor.WHITE ? 1 : -1;
        // The gap value depends on the direction choose (UP our DOWN)
        int gap = direction == Direction.UP ? (toY - fromY) * colorMultiplier : (fromY - toY) * colorMultiplier;

        // Correct move
        if(fromX == toX && gap <= distance && gap >= 0){
            return direction;
        }
        // Incorrect move
        else{
            return Direction.INVALID;
        }



    }
}

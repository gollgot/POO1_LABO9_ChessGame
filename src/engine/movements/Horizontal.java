package engine.movements;

import chess.PlayerColor;

public class Horizontal implements Move{

    private Direction direction;

    public Horizontal(Direction direction) {
        if(direction != Direction.LEFT && direction != Direction.RIGHT){
            throw new RuntimeException("Horizontal move can have only LEFT or RIGHT direction");
        }
        this.direction = direction;
    }

    @Override
    public Direction isValid(int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {

        int gap = Math.abs(fromX - toX);

        // Correct move
        if(fromY == toY && gap <= distance && gap >= 0){
            return direction;
        }
        // Incorrect move
        else{
            return Direction.INVALID;
        }



    }
}

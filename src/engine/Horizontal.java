package engine;

import chess.PlayerColor;

class Horizontal implements Move{

    private Direction direction;

    Horizontal(Direction direction) {
        if(direction != Direction.LEFT && direction != Direction.RIGHT){
            throw new RuntimeException("Horizontal move can have only LEFT or RIGHT direction");
        }
        this.direction = direction;
    }

    @Override
    public Direction isValid(int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {

        // The gap value depends on the direction choose (UP our DOWN)
        int gap = direction == Direction.LEFT ? fromX - toX : toX - fromX;

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

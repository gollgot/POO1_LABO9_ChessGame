package engine;

import chess.PlayerColor;

class Diagonal implements Move {

    private Direction direction;

    Diagonal(Direction direction) {
        if(direction != Direction.DIAG_TOP_LEFT && direction != Direction.DIAG_TOP_RIGHT &&
            direction != Direction.DIAG_BOT_LEFT && direction != Direction.DIAG_BOT_RIGHT){
            throw new RuntimeException("Vertical move can have only UP or DOWN direction");
        }
        this.direction = direction;
    }

    @Override
    public Direction isValid(int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor) {
        int horizontalGap = direction == Direction.DIAG_TOP_LEFT || direction == Direction.DIAG_BOT_LEFT ? fromX - toX : toX - fromX;
        int verticalMultiplier = direction == Direction.DIAG_TOP_LEFT || direction == Direction.DIAG_TOP_RIGHT ? 1 : -1;
        int verticalGap = playerColor == PlayerColor.WHITE ? (toY - fromY) * verticalMultiplier : (fromY - toY) * verticalMultiplier;
        int gap = -1;

        if(horizontalGap == verticalGap){
            gap = horizontalGap;
        }

        // Correct move
        if(gap <= distance && gap >= 0){
            return direction;
        }
        // Incorrect move
        else{
            return Direction.INVALID;
        }



    }
}

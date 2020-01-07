package engine;

import chess.PieceType;
import chess.PlayerColor;

class King extends Piece {

    King(PieceType type, PlayerColor color, Move[] moves, int distance){
        super(type, color, moves, distance);
    }

    @Override
    boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        boolean isValidMove = false;
        Direction dirToMove;

        for(Move move : getMoves()){
            dirToMove = move.isValid(fromX, fromY, toX, toY, getDistance(), getColor());
            switch(dirToMove){
                // Move LEFT possibly OK
                case UP:
                case DOWN:
                case LEFT:
                case RIGHT:
                case DIAG_TOP_LEFT:
                case DIAG_TOP_RIGHT:
                case DIAG_BOT_LEFT:
                case DIAG_BOT_RIGHT:

                    // Check obstacle etc...
                    // ...
                    // All check ok -> move

                    if(!isAlreadyMoved()){
                        setAlreadyMoved(true);
                    }
                    isValidMove = true;
                    break;
                default:
                    break;
            }
        }

        return isValidMove;
    }
}

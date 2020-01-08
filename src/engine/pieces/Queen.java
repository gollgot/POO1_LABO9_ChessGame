package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.*;

public class Queen extends Piece {

    public Queen(PlayerColor color){
        super(
                PieceType.QUEEN,
                color,
                new Move[]{
                        new Vertical(Direction.UP),
                        new Vertical(Direction.DOWN),
                        new Horizontal(Direction.LEFT),
                        new Horizontal(Direction.RIGHT),
                        new Diagonal(Direction.DIAG_TOP_LEFT),
                        new Diagonal(Direction.DIAG_TOP_RIGHT),
                        new Diagonal(Direction.DIAG_BOT_LEFT),
                        new Diagonal(Direction.DIAG_BOT_RIGHT)
                },
                8
        );
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
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

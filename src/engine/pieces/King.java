package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.*;

public class King extends Piece {

    public King(PlayerColor color){
        super(
                PieceType.KING,
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
                1
        );
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        boolean isValidMove = false;

        for(Move move : getMoves()){
            if(move.isValid(fromX, fromY, toX, toY, getDistance(), getColor())){
                isValidMove = true;
                if(!isAlreadyMoved()){
                    setAlreadyMoved(true);
                }
                break;
            }
        }

        return isValidMove;
    }
}

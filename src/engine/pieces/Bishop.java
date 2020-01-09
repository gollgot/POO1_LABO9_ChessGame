package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Diagonal;
import engine.movements.Direction;
import engine.movements.Move;

public class Bishop extends Piece {

    public Bishop(PlayerColor color){
        super(
                PieceType.BISHOP,
                color,
                new Move[]{
                        new Diagonal(Direction.DIAG_TOP_LEFT),
                        new Diagonal(Direction.DIAG_TOP_RIGHT),
                        new Diagonal(Direction.DIAG_BOT_LEFT),
                        new Diagonal(Direction.DIAG_BOT_RIGHT)
                },
                8
        );
    }

    @Override
    public boolean isValidMove(Cell[][] board, int toX, int toY) {
        boolean isValidMove = false;

        // Check normal move
        for(Move move : getMoves()){
            if(move.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor()) && move.isLastCellEmptyOrEatable(board, toX, toY, getColor())){
                isValidMove = true;
                break;
            }
        }

        // Update already move only for the first move we did
        if(isValidMove && !isAlreadyMoved()){
            setAlreadyMoved(true);
        }

        return isValidMove;
    }
}

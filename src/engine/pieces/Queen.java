package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
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
    public boolean isValidMove(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        boolean isValidMove = false;

        // Check normal move
        for(Move move : getMoves()){
            if(move.isClickedCellAndWayValid(board, fromX, fromY, toX, toY, getDistance(), getColor()) && move.isLastCellEmptyOrEatable(board, toX, toY, getColor())){
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

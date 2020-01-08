package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
import engine.movements.Direction;
import engine.movements.Horizontal;
import engine.movements.Move;
import engine.movements.Vertical;

public class Rook extends Piece {

    public Rook(PlayerColor color){
        super(
                PieceType.ROOK,
                color,
                new Move[]{
                        new Vertical(Direction.UP),
                        new Vertical(Direction.DOWN),
                        new Horizontal(Direction.LEFT),
                        new Horizontal(Direction.RIGHT)
                },
                8
        );
    }

    @Override
    public boolean isValidMove(Cell[][] board, int fromX, int fromY, int toX, int toY) {
        boolean isValidMove = false;

        for(Move move : getMoves()){
            if(move.isValid(board, fromX, fromY, toX, toY, getDistance(), getColor())){
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

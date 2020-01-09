package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.Cell;
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
    public boolean isValidMove(Cell[][] board, int toX, int toY) {
        boolean isValidMove = false;

        // Check normal move
        for(Move move : getMoves()){
            if(move.isClickedCellAndWayValid(board, getX(), getY(), toX, toY, getDistance(), getColor()) && move.isLastCellEmptyOrEatable(board, toX, toY, getColor())){
                isValidMove = true;
                break;
            }
        }

        // Check castling
        if(isSmallCastling(board, getX(), getY(), toX, toY) || isBigCastling(board, getX(), getY(), toX, toY)){
            isValidMove = true;
        }

        // Update already move only for the first move we did
        if(isValidMove && !isAlreadyMoved()){
            setAlreadyMoved(true);
        }

        return isValidMove;
    }

    private boolean isSmallCastling(Cell[][] board, int fromX, int fromY, int toX, int toY){
        if(!isAlreadyMoved()){
            Cell rookCell = board[getY()][7];
            Move rightMove = new Horizontal(Direction.RIGHT);
            if(
                    !rookCell.empty() &&
                    rookCell.getPiece().getType() == PieceType.ROOK &&
                    !rookCell.getPiece().isAlreadyMoved() &&
                    toX == 6 && toY == getY() &&
                    rightMove.isClickedCellAndWayValid(board, fromX, fromY, rookCell.getX(), rookCell.getY(), 8, getColor())
            ){
                return true;
            }
        }

        return false;
    }

    private boolean isBigCastling(Cell[][] board, int fromX, int fromY, int toX, int toY){
        if(!isAlreadyMoved()) {
            Cell rookCell = board[getY()][0];
            Move leftMove = new Horizontal(Direction.LEFT);
            if(
                    !rookCell.empty() &&
                    rookCell.getPiece().getType() == PieceType.ROOK &&
                    !rookCell.getPiece().isAlreadyMoved() &&
                    toX == 2 && toY == getY() &&
                    leftMove.isClickedCellAndWayValid(board, fromX, fromY, rookCell.getX(), rookCell.getY(), 8, getColor())
            ){
                return true;
            }
        }

        return false;
    }


}

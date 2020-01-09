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
    public String textValue() {
        return "Reine";
    }
}

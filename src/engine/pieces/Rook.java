package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.Direction;
import engine.movements.Horizontal;
import engine.movements.Move;
import engine.movements.Vertical;

public class Rook extends Restricted {

    public Rook(PlayerColor color) {
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
    public String textValue() {
        return "Rook";
    }
}

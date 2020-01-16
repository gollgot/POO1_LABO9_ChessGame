package engine.pieces;

import chess.PieceType;
import chess.PlayerColor;
import engine.movements.Diagonal;
import engine.movements.Direction;
import engine.movements.Move;

public class Bishop extends Piece {

    /**
     * Constructor
     *
     * @param color The Piece color
     */
    public Bishop(PlayerColor color) {
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
    public String toString() {
        return "Bishop";
    }

}

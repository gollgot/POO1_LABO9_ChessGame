package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public interface Move {
    boolean isValid(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor);
}

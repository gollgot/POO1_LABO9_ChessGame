package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public interface Move {
    boolean isPathClear(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor);
    boolean isDestinationOccupied(Cell[][] board, int toX, int toY, PlayerColor playerColor);
}

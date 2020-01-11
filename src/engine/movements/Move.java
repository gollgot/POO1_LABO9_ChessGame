package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public interface Move {
    /**
     * Check if the path between the fromX fromY pos and toX toY pos is clear (last cell exclude)
     * @param board The current board game
     * @param fromX The fromX coordinate
     * @param fromY The fromY coordinate
     * @param toX The toX coordinate
     * @param toY The toY coordinate
     * @param distance The maximum distance that the Piece can move
     * @param playerColor The color of the current player
     * @return True if the path is clear, false otherwise
     */
    boolean isPathClear(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor);

    /**
     * Check if the destination can be taken
     * @param board The current board game
     * @param toX The toX coordinate
     * @param toY The toY coordinate
     * @param playerColor The color of the current player
     * @return True if the destination can be taken, false otherwise
     */
    boolean isDestinationTakable(Cell[][] board, int toX, int toY, PlayerColor playerColor);
}

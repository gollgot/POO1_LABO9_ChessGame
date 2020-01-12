package engine.movements;

import chess.PlayerColor;
import engine.Cell;

public interface Move {
    /**
     * Check if the path between the source coordinates target ones is clear
     * @note the last cell is excluded from the check. It's check w/ `isDestinationTakable`
     *
     * @param board Board
     * @param fromX Source X coordinate
     * @param fromY Source Y coordinate
     * @param toX Target X coordinate
     * @param toY Target Y coordinate
     * @param distance Maximum distance the Piece can move
     * @param playerColor Color of the piece trying to moving
     * @return True if the path is clear, otherwise false
     */
    boolean isPathClear(Cell[][] board, int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor);

    /**
     * Check if the destination can be taken
     * i.e. it's occupied by an opponent or it's empty
     *
     * @param board Board
     * @param toX Target X coordinate
     * @param toY Target Y coordinate
     * @param playerColor Color of the piece trying to moving
     * @return True if the destination can be taken, false otherwise
     */
    boolean isDestinationTakable(Cell[][] board, int toX, int toY, PlayerColor playerColor);
}

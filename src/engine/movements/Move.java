package engine.movements;

import chess.PlayerColor;

public interface Move {
    boolean isValid(int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor);
}

package engine;

import chess.PlayerColor;

interface Move {
    Direction isValid(int fromX, int fromY, int toX, int toY, int distance, PlayerColor playerColor);
}

package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.pieces.*;

public class ChessBoardController implements ChessController {
    private static final int dimension = 8;
    private ChessView view;
    private Cell[][] board;
    private int turn;
    private Piece whiteKing;
    private Piece blackKing;

    public ChessBoardController() {
        board = new Cell[8][8];
        for (int row = 0; row < dimension; ++row) {
            for (int col = 0; col < dimension; ++col) {
                board[row][col] = new Cell(col, row);
            }
        }
    }

    @Override
    public void start(ChessView view) {
        this.view = view;
        view.startView();
    }

    @Override
    public boolean move(int fromX, int fromY, int toX, int toY) {
        Cell fromCell = board[fromY][fromX];
        Cell toCell = board[toY][toX];

        if (fromCell.empty())
            return false;

        // Piece on from cell -> check if move valid
        Piece p = fromCell.getPiece();

        // Even if we do an invalid move, display the chess if there is one
        if (isKingCheck(getOpponentColor(p.getColor()))) {
            view.displayMessage("Echec");
        }

        // Odd turn is white player and even is black (turn begin to 1)
        //if(turn % 2 == 1 && p.getColor() != PlayerColor.WHITE || turn % 2 == 0 && p.getColor() != PlayerColor.BLACK)
        //return false;

        MoveType move = p.isValidMove(board, toX, toY, turn);

        // The move we want to do must be correct
        if (move == MoveType.IMPOSSIBLE)
            return false;

        // Specific board game update for castling movement type
        if (move == MoveType.KING_SIDE_CASTLE || move == MoveType.QUEEN_SIDE_CASTLE)
            return doCastle((King) p, move, toX, toY);

        // Simulate the move
        fromCell.removePiece();
        toCell.addPiece(p);

        // The move will cause a check -> restore the move and try another move
        if (isKingCheck(getOpponentColor(p.getColor()))) {
            toCell.removePiece();
            fromCell.addPiece(p);

            return false;
        }

        // The move saved the king or didn't cause a check -> the move can be applied to the view
        view.displayMessage("");
        view.removePiece(fromX, fromY);
        view.putPiece(p.getType(), p.getColor(), toX, toY);

        // Specific board game update for "En Passant" movetype
        if (move == MoveType.EN_PASSANT) {
            doEnPasant(fromY, toX, toY);
        }
        // Specific board game update for pawn promotion
        else if (move == MoveType.PROMOTION) {
            doPromotion(p);
        }

        ++turn;
        return true;
    }

    private boolean doCastle(King k, MoveType castleType, int toX, int toY) {
        Cell kingOgCell = board[k.getY()][k.getX()];
        // get the kings next cells
        Cell kingNextCell = castleType == MoveType.KING_SIDE_CASTLE ? board[k.getY()][k.getX() + 1] : board[k.getY()][k.getX() - 1];
        Cell kingTargetCell = board[toY][toX];

        // move the king to the next cell and see if it puts him in `check`
        kingNextCell.addPiece(k);
        kingOgCell.removePiece();
        if (isKingCheck(getOpponentColor(k.getColor()))) {
            // yes, then put the king back to his og cell
            kingNextCell.removePiece();
            kingOgCell.addPiece(k);

            k.setMoved(false);

            return false;
        }

        // all gucci, now move to king to the target cell and check if puts him in `check`
        kingTargetCell.addPiece(k);
        kingNextCell.removePiece();
        if (isKingCheck(getOpponentColor(k.getColor()))) {
            // yes, then put the king back to his og cell
            kingTargetCell.removePiece();
            kingOgCell.addPiece(k);

            k.setMoved(false);

            return false;
        }

        view.putPiece(k.getType(), k.getColor(), kingTargetCell.getX(), kingTargetCell.getY());
        view.removePiece(kingOgCell.getX(), kingOgCell.getY());

        Cell oldRookCell = castleType == MoveType.KING_SIDE_CASTLE ? board[k.getY()][k.getX() + 1] : board[k.getY()][k.getX() - 2];
        Cell newRookCell = castleType == MoveType.KING_SIDE_CASTLE ? board[k.getY()][k.getX() - 1] : board[k.getY()][k.getX() + 1];

        // Add new rook on the board
        newRookCell.addPiece(oldRookCell.getPiece());
        view.putPiece(oldRookCell.getPiece().getType(), oldRookCell.getPiece().getColor(), newRookCell.getX(), newRookCell.getY());
        // Remove old rook from the board
        oldRookCell.removePiece();
        view.removePiece(oldRookCell.getX(), oldRookCell.getY());

        ++turn;

        return true;
    }

    private void doEnPasant(int fromY, int toX, int toY) {
        int eateeYPos = fromY - toY > 0 ? toY + 1 : toY - 1;
        Cell eateeCell = board[eateeYPos][toX];

        eateeCell.removePiece();
        view.removePiece(eateeCell.getX(), eateeCell.getY());
    }

    private void doPromotion(Piece p) {
        Piece queen = new Queen(p.getColor());
        Piece knight = new Knight(p.getColor());
        Piece rook = new Rook(p.getColor());
        Piece bishop = new Bishop(p.getColor());

        Piece selectedPiece = view.askUser("Promotion", "Sélectionnez la pièce de promotion", queen, knight, rook, bishop);
        board[p.getY()][p.getX()].removePiece();
        board[p.getY()][p.getX()].addPiece(selectedPiece);
        view.removePiece(p.getX(), p.getY());
        view.putPiece(selectedPiece.getType(), selectedPiece.getColor(), selectedPiece.getX(), selectedPiece.getY());
    }

    private boolean isKingCheck(PlayerColor opponentColor) {
        // Check chess to king
        Piece king = opponentColor == PlayerColor.BLACK ? whiteKing : blackKing;
        for (int row = 0; row < dimension; ++row) {
            for (int col = 0; col < dimension; ++col) {
                Cell currentCell = board[row][col];

                if (currentCell.empty() || currentCell.getPiece().getColor() == king.getColor())
                    continue;

                if (currentCell.getPiece().isValidMove(board, king.getX(), king.getY(), turn + 1) != MoveType.IMPOSSIBLE)
                    return true;
            }
        }

        return false;
    }

    private PlayerColor getOpponentColor(PlayerColor color) {
        return color == PlayerColor.WHITE ? PlayerColor.BLACK : PlayerColor.WHITE;
    }

    @Override
    public void newGame() {
        // set turn number to 1
        turn = 1;
        kingIsChess = false;

        for (int row = 0; row < dimension; ++row) {
            for (int col = 0; col < dimension; ++col) {
                Cell currentCell = board[row][col];
                if (!currentCell.empty()) {
                    board[row][col].removePiece();
                }
            }
        }

        // Add all pawn
        for (int col = 0; col < dimension; ++col) {
            Piece pawnWhite = new Pawn(PlayerColor.WHITE);
            Piece pawnBlack = new Pawn(PlayerColor.BLACK);
            board[1][col].addPiece(pawnWhite);
            board[6][col].addPiece(pawnBlack);

            view.putPiece(pawnWhite.getType(), pawnWhite.getColor(), col, 1);
            view.putPiece(pawnBlack.getType(), pawnBlack.getColor(), col, 6);
        }

        // Rook
        Piece rookWhite1 = new Rook(PlayerColor.WHITE);
        Piece rookWhite2 = new Rook(PlayerColor.WHITE);
        Piece rookBlack1 = new Rook(PlayerColor.BLACK);
        Piece rookBlack2 = new Rook(PlayerColor.BLACK);
        // Knight
        Piece knightWhite1 = new Knight(PlayerColor.WHITE);
        Piece knightWhite2 = new Knight(PlayerColor.WHITE);
        Piece knightBlack1 = new Knight(PlayerColor.BLACK);
        Piece knightBlack2 = new Knight(PlayerColor.BLACK);
        // Bishop
        Piece bishopWhite1 = new Bishop(PlayerColor.WHITE);
        Piece bishopWhite2 = new Bishop(PlayerColor.WHITE);
        Piece bishopBlack1 = new Bishop(PlayerColor.BLACK);
        Piece bishopBlack2 = new Bishop(PlayerColor.BLACK);
        // Queen
        Piece queenWhite = new Queen(PlayerColor.WHITE);
        Piece queenBlack = new Queen(PlayerColor.BLACK);
        // King
        whiteKing = new King(PlayerColor.WHITE);
        blackKing = new King(PlayerColor.BLACK);

        // Add white pieces on the board
        board[0][0].addPiece(rookWhite1);
        board[0][1].addPiece(knightWhite1);
        board[0][2].addPiece(bishopWhite1);
        board[0][3].addPiece(queenWhite);
        board[0][4].addPiece(whiteKing);
        board[0][5].addPiece(bishopWhite2);
        board[0][6].addPiece(knightWhite2);
        board[0][7].addPiece(rookWhite2);

        // Add black pieces on the board
        board[7][0].addPiece(rookBlack1);
        board[7][1].addPiece(knightBlack1);
        board[7][2].addPiece(bishopBlack1);
        board[7][3].addPiece(queenBlack);
        board[7][4].addPiece(blackKing);
        board[7][5].addPiece(bishopBlack2);
        board[7][6].addPiece(knightBlack2);
        board[7][7].addPiece(rookBlack2);

        // Display white pieces
        view.putPiece(rookWhite1.getType(), rookWhite1.getColor(), 0, 0);
        view.putPiece(knightWhite1.getType(), knightWhite1.getColor(), 1, 0);
        view.putPiece(bishopWhite1.getType(), bishopWhite1.getColor(), 2, 0);
        view.putPiece(queenWhite.getType(), queenWhite.getColor(), 3, 0);
        view.putPiece(whiteKing.getType(), whiteKing.getColor(), 4, 0);
        view.putPiece(bishopWhite2.getType(), bishopWhite2.getColor(), 5, 0);
        view.putPiece(knightWhite2.getType(), knightWhite2.getColor(), 6, 0);
        view.putPiece(rookWhite2.getType(), rookWhite2.getColor(), 7, 0);

        // Display black pieces
        view.putPiece(rookBlack1.getType(), rookBlack1.getColor(), 0, 7);
        view.putPiece(knightBlack1.getType(), knightBlack2.getColor(), 1, 7);
        view.putPiece(bishopBlack1.getType(), bishopBlack2.getColor(), 2, 7);
        view.putPiece(queenBlack.getType(), queenBlack.getColor(), 3, 7);
        view.putPiece(blackKing.getType(), blackKing.getColor(), 4, 7);
        view.putPiece(bishopBlack2.getType(), bishopBlack2.getColor(), 5, 7);
        view.putPiece(knightBlack2.getType(), knightBlack2.getColor(), 6, 7);
        view.putPiece(rookBlack2.getType(), rookBlack2.getColor(), 7, 7);
    }

}

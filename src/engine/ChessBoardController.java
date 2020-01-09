package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PlayerColor;
import engine.movements.*;
import engine.pieces.*;

public class ChessBoardController implements ChessController {
    private ChessView view;
    private static final int dimension = 8;
    private Cell[][] board;
    private int turn;

    public ChessBoardController(){
        board = new Cell[8][8];
        for(int row = 0; row < dimension; ++row){
            for(int col = 0; col < dimension; ++col){
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

        // From cell empty -> no piece to move
        if(fromCell.empty()){
            System.out.println("No piece to move");
        }
        // Piece on from cell -> check if move valid
        else{
            Piece p = fromCell.getPiece();
            MoveType move = p.isValidMove(board, toX, toY, turn);
            if(move == MoveType.NORMAL){
                // Move
                fromCell.removePiece();
                view.removePiece(fromX, fromY);
                toCell.addPiece(p);
                view.putPiece(p.getType(), p.getColor(), toX, toY);

                ++turn;
                return true;
            } else if (move == MoveType.EN_PASSANT) {
                // Move
                fromCell.removePiece();
                view.removePiece(fromX, fromY);
                toCell.addPiece(p);
                view.putPiece(p.getType(), p.getColor(), toX, toY);

                int eateeYPos = fromY - toY > 0 ? toY + 1 : toY - 1;
                Cell eateeCell = board[eateeYPos][toX];

                eateeCell.removePiece();
                view.removePiece(eateeCell.getX(), eateeCell.getY());
            }
        }

        return false;
    }

    @Override
    public void newGame() {
        // set turn number to 1
        this.turn = 1;

        for(int row = 0; row < dimension; ++row){
            for(int col = 0; col < dimension; ++col){
                Cell currentCell = board[row][col];
                if(!currentCell.empty()){
                    board[row][col].removePiece();
                }
            }
        }

        // Add all pawn
        for(int col = 0; col < dimension; ++col){
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
        Piece kingWhite = new King(PlayerColor.WHITE);
        Piece kingBlack = new King(PlayerColor.BLACK);

        // Add white pieces on the board
        board[0][0].addPiece(rookWhite1);
        board[0][1].addPiece(knightWhite1);
        board[0][2].addPiece(bishopWhite1);
        board[0][3].addPiece(queenWhite);
        board[0][4].addPiece(kingWhite);
        board[0][5].addPiece(bishopWhite2);
        board[0][6].addPiece(knightWhite2);
        board[0][7].addPiece(rookWhite2);

        // Add black pieces on the board
        board[7][0].addPiece(rookBlack1);
        board[7][1].addPiece(knightBlack1);
        board[7][2].addPiece(bishopBlack1);
        board[7][3].addPiece(queenBlack);
        board[7][4].addPiece(kingBlack);
        board[7][5].addPiece(bishopBlack2);
        board[7][6].addPiece(knightBlack2);
        board[7][7].addPiece(rookBlack2);

        // Display white pieces
        view.putPiece(rookWhite1.getType(), rookWhite1.getColor(), 0, 0);
        view.putPiece(knightWhite1.getType(), knightWhite1.getColor(), 1, 0);
        view.putPiece(bishopWhite1.getType(), bishopWhite1.getColor(), 2, 0);
        view.putPiece(queenWhite.getType(), queenWhite.getColor(), 3, 0);
        view.putPiece(kingWhite.getType(), kingWhite.getColor(), 4, 0);
        view.putPiece(bishopWhite2.getType(), bishopWhite2.getColor(), 5, 0);
        view.putPiece(knightWhite2.getType(), knightWhite2.getColor(), 6, 0);
        view.putPiece(rookWhite2.getType(), rookWhite2.getColor(), 7, 0);

        // Display black pieces
        view.putPiece(rookBlack1.getType(), rookBlack1.getColor(), 0, 7);
        view.putPiece(knightBlack1.getType(), knightBlack2.getColor(), 1, 7);
        view.putPiece(bishopBlack1.getType(), bishopBlack2.getColor(), 2, 7);
        view.putPiece(queenBlack.getType(), queenBlack.getColor(), 3, 7);
        view.putPiece(kingBlack.getType(), kingBlack.getColor(), 4, 7);
        view.putPiece(bishopBlack2.getType(), bishopBlack2.getColor(), 5, 7);
        view.putPiece(knightBlack2.getType(), knightBlack2.getColor(), 6, 7);
        view.putPiece(rookBlack2.getType(), rookBlack2.getColor(), 7, 7);
    }

}

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
            if(p.isValidMove(fromX, fromY, toX, toY)){
                // Move
                fromCell.removePiece();
                view.removePiece(fromX, fromY);
                toCell.addPiece(p);
                view.putPiece(p.getType(), p.getColor(), toX, toY);
                return true;
            }
        }

        return false;
    }

    @Override
    public void newGame() {
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
        Piece rookWhite = new Rook(PlayerColor.WHITE);
        Piece rookBlack = new Rook(PlayerColor.BLACK);
        // Knight
        Piece knightWhite = new Rook(PlayerColor.WHITE);
        Piece knightBlack = new Rook(PlayerColor.BLACK);
        // Bishop
        Piece bishopWhite = new Bishop(PlayerColor.WHITE);
        Piece bishopBlack = new Bishop(PlayerColor.BLACK);
        // Queen
        Piece queenWhite = new Queen(PlayerColor.WHITE);
        Piece queenBlack = new Queen(PlayerColor.BLACK);
        // King
        Piece kingWhite = new King(PlayerColor.WHITE);
        Piece kingBlack = new King(PlayerColor.BLACK);

        // Add white pieces on the board
        board[0][0].addPiece(rookWhite);
        board[0][1].addPiece(knightWhite);
        board[0][2].addPiece(bishopWhite);
        board[0][3].addPiece(queenWhite);
        board[0][4].addPiece(kingWhite);
        board[0][5].addPiece(bishopWhite);
        board[0][6].addPiece(knightWhite);
        board[0][7].addPiece(rookWhite);

        // Add black pieces on the board
        board[7][0].addPiece(rookBlack);
        board[7][1].addPiece(knightBlack);
        board[7][2].addPiece(bishopBlack);
        board[7][3].addPiece(queenBlack);
        board[7][4].addPiece(kingBlack);
        board[7][5].addPiece(bishopBlack);
        board[7][6].addPiece(knightBlack);
        board[7][7].addPiece(rookBlack);

        // Display white pieces
        view.putPiece(rookWhite.getType(), rookWhite.getColor(), 0, 0);
        view.putPiece(knightWhite.getType(), knightWhite.getColor(), 1, 0);
        view.putPiece(bishopWhite.getType(), bishopWhite.getColor(), 2, 0);
        view.putPiece(queenWhite.getType(), queenWhite.getColor(), 3, 0);
        view.putPiece(kingWhite.getType(), kingWhite.getColor(), 4, 0);
        view.putPiece(bishopWhite.getType(), bishopWhite.getColor(), 5, 0);
        view.putPiece(knightWhite.getType(), knightWhite.getColor(), 6, 0);
        view.putPiece(rookWhite.getType(), rookWhite.getColor(), 7, 0);

        // Display black pieces
        view.putPiece(rookBlack.getType(), rookBlack.getColor(), 0, 7);
        view.putPiece(knightBlack.getType(), knightBlack.getColor(), 1, 7);
        view.putPiece(bishopBlack.getType(), bishopBlack.getColor(), 2, 7);
        view.putPiece(queenBlack.getType(), queenBlack.getColor(), 3, 7);
        view.putPiece(kingBlack.getType(), kingBlack.getColor(), 4, 7);
        view.putPiece(bishopBlack.getType(), bishopBlack.getColor(), 5, 7);
        view.putPiece(knightBlack.getType(), knightBlack.getColor(), 6, 7);
        view.putPiece(rookBlack.getType(), rookBlack.getColor(), 7, 7);
    }

}

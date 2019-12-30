package engine;

import chess.ChessController;
import chess.ChessView;
import chess.PieceType;
import chess.PlayerColor;

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
        System.out.println(String.format("(%d, %d) to (%d,%d)", fromX, fromY, toX, toY));
        Cell fromCell = board[fromX][fromY];

        if(fromCell.empty()){
            System.out.println("No piece to move");
        }else{
            Piece p = fromCell.getPiece();
            return p.isValidMove(fromX, fromY, toX, toY);
        }
        return false;
    }

    @Override
    public void newGame() {
        for(int row = 0; row < dimension; ++row){
            for(int col = 0; col < dimension; ++col){
                Cell currentCell = board[row][col];
                if(!currentCell.empty()){
                    currentCell.removePiece();
                }
            }
        }

        // Add all pawn
        Move[] pawnMoves = new Move[]{new Vertical(Direction.UP)};
        for(int col = 0; col < dimension; ++col){
            Piece pawnWhite = new Pawn(PieceType.PAWN, PlayerColor.WHITE, pawnMoves, 1);
            Piece pawnBlack = new Pawn(PieceType.PAWN, PlayerColor.BLACK, pawnMoves, 1);
            board[1][col].addPiece(pawnWhite);
            board[7][col].addPiece(pawnBlack);
            view.putPiece(pawnWhite.getType(), pawnWhite.getColor(), col, 1);
            view.putPiece(pawnBlack.getType(), pawnBlack.getColor(), col, 6);
        }


    }

}

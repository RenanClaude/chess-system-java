package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

  private Board board;

  public ChessMatch() {
    this.board = new Board(8, 8);
    initialSetup();
  }

  public ChessPiece[][] getPieces() {
    ChessPiece[][] matriz = new ChessPiece[this.board.getRows()][this.board.getColumns()];
    for (int indexL = 0; indexL < this.board.getRows(); indexL += 1) {
      for (int indexC = 0; indexC < this.board.getColumns(); indexC += 1) {
        matriz[indexL][indexC] = (ChessPiece) this.board.piece(indexL, indexC);
      }
    }
    return matriz;
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    this.board.placePiece(piece, new ChessPosition(row, column).toPosition());
  }

  public void initialSetup() {
    this.placeNewPiece('e', 1, new King(this.board, Color.WHITE));
    this.placeNewPiece('e', 8, new King(this.board, Color.BLACK));
  }
}

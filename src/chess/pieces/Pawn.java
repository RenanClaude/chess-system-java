package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

  public Pawn(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "P";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] matriz = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    Position pos = new Position(0, 0);

    if (this.getColor() == Color.WHITE) {
      pos.setValues(this.position.getRow() - 1, this.position.getColumn());
      if (this.getBoard().positionExistis(pos) && !this.getBoard().thereIsAPieace(pos)) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
      pos.setValues(this.position.getRow() - 2, this.position.getColumn());
      Position pos2 = new Position(this.position.getRow() - 1, this.position.getColumn());
      if (this.getBoard().positionExistis(pos)
          && !this.getBoard().thereIsAPieace(pos)
          && this.getBoard().positionExistis(pos2)
          && !this.getBoard().thereIsAPieace(pos2)
          && this.getMoveCount() == 0) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
      pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
      if (this.getBoard().positionExistis(pos) && this.isThereOpponentPiece(pos)) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
      pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
      if (this.getBoard().positionExistis(pos) && this.isThereOpponentPiece(pos)) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
    } else {
      pos.setValues(this.position.getRow() + 1, this.position.getColumn());
      if (this.getBoard().positionExistis(pos) && !this.getBoard().thereIsAPieace(pos)) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
      pos.setValues(this.position.getRow() + 2, this.position.getColumn());
      Position pos2 = new Position(this.position.getRow() + 1, this.position.getColumn());
      if (this.getBoard().positionExistis(pos)
          && !this.getBoard().thereIsAPieace(pos)
          && this.getBoard().positionExistis(pos2)
          && !this.getBoard().thereIsAPieace(pos2)
          && this.getMoveCount() == 0) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
      pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
      if (this.getBoard().positionExistis(pos) && this.isThereOpponentPiece(pos)) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }
      pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
      if (this.getBoard().positionExistis(pos) && this.isThereOpponentPiece(pos)) {
        matriz[pos.getRow()][pos.getColumn()] = true;
      }

    }
    return matriz;
  }
}

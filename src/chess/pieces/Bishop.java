package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Bishop extends ChessPiece {

  public Bishop(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "B";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] matriz = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    Position pos = new Position(0, 0);

    //NW
    pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setValues(pos.getRow() - 1, pos.getColumn() - 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //NE
    pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setValues(pos.getRow() - 1, pos.getColumn() + 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //SE
    pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setValues(pos.getRow() + 1, pos.getColumn() + 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //SW
    pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setValues(pos.getRow() + 1, pos.getColumn() - 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    return matriz;
  }
}

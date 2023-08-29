package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

  public Rook(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "R";
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] matriz = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    Position pos = new Position(0, 0);

    //Above
    pos.setValues(this.position.getRow() - 1, this.position.getColumn());
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setRow(pos.getRow() - 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //Left
    pos.setValues(this.position.getRow(), this.position.getColumn() - 1);
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setColumn(pos.getColumn() - 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //Right
    pos.setValues(this.position.getRow(), this.position.getColumn() + 1);
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setColumn(pos.getColumn() + 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //Below
    pos.setValues(this.position.getRow() + 1, this.position.getColumn());
    while (getBoard().positionExistis(pos) && !getBoard().thereIsAPieace(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
      pos.setRow(pos.getRow() + 1);
    }
    if (getBoard().positionExistis(pos) && isThereOpponentPiece(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    return matriz;
  }
}

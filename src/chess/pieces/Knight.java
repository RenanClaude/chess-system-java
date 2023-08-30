package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Knight extends ChessPiece {

  public Knight(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "N";
  }

  private boolean canMove(Position position) {
    ChessPiece piece = (ChessPiece) this.getBoard().piece(position);
    return piece == null || piece.getColor() != this.getColor();
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] matriz = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    Position pos = new Position(0, 0);

    pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 2);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() - 2, this.position.getColumn() - 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() - 2, this.position.getColumn() + 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 2);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 2);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() + 2, this.position.getColumn() + 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() + 2, this.position.getColumn() - 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 2);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }
    return matriz;
  }
}

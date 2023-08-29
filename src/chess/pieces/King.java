package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {

  public King(Board board, Color color) {
    super(board, color);
  }

  @Override
  public String toString() {
    return "K";
  }


  private boolean canMove(Position position) {
    ChessPiece piece = (ChessPiece) this.getBoard().piece(position);
    return piece == null || piece.getColor() != this.getColor();
  }

  @Override
  public boolean[][] possibleMoves() {
    boolean[][] matriz = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
    Position pos = new Position(0, 0);
    //Above
    pos.setValues(this.position.getRow() - 1, this.position.getColumn());
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //Below
    pos.setValues(this.position.getRow() + 1, this.position.getColumn());
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //Left
    pos.setValues(this.position.getRow(), this.position.getColumn() - 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //Right
    pos.setValues(this.position.getRow(), this.position.getColumn() + 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //NW
    pos.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //NE
    pos.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //SW
    pos.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    //SE
    pos.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
    if (getBoard().positionExistis(pos) && canMove(pos)) {
      matriz[pos.getRow()][pos.getColumn()] = true;
    }

    return matriz;
  }
}

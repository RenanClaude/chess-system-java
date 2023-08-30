package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;
import java.util.List;

public class King extends ChessPiece {

  private ChessMatch match;

  public King(Board board, Color color, ChessMatch match) {
    super(board, color);
    this.match = match;
  }

  @Override
  public String toString() {
    return "K";
  }

  private boolean canMove(Position position) {
    ChessPiece piece = (ChessPiece) this.getBoard().piece(position);
    return (piece == null || piece.getColor() != this.getColor())
        && !this.match.kingWillBeInCheck(position, this.getColor());
  }

  private boolean testRookCastling(Position position) {
    ChessPiece piece = (ChessPiece) this.getBoard().piece(position);
    return piece instanceof Rook
        && piece.getColor() == this.getColor()
        && piece.getMoveCount() == 0;
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

    //Castling
    if (this.getMoveCount() == 0 && !this.match.getCheck()) {
      //Castling on the king's side.
      Position posRook1 = new Position(this.position.getRow(), this.position.getColumn() + 3);
      if (this.testRookCastling(posRook1)
          && !this.match.testCheckKingSideCastling(this.getColor())) {
        Position position1 = new Position(this.position.getRow(), this.position.getColumn() + 2);
        Position position2 = new Position(this.position.getRow(), this.position.getColumn() + 1);
        if (this.getBoard().piece(position1) == null && this.getBoard().piece(position2) == null) {
          matriz[this.position.getRow()][this.position.getColumn() + 2] = true;
        }
      }
      //Castling on the queen's side.
      Position posRook2 = new Position(this.position.getRow(), this.position.getColumn() - 4);
      if (this.testRookCastling(posRook2)
          && !this.match.testCheckQueenSideCastling(this.getColor())) {
        Position position1 = new Position(this.position.getRow(), this.position.getColumn() - 1);
        Position position2 = new Position(this.position.getRow(), this.position.getColumn() - 2);
        Position position3 = new Position(this.position.getRow(), this.position.getColumn() - 3);
        if (this.getBoard().piece(position1) == null
            && this.getBoard().piece(position2) == null
            && this.getBoard().piece(position3) == null) {
          matriz[this.position.getRow()][this.position.getColumn() - 2] = true;
        }
      }
    }
    return matriz;
  }
}

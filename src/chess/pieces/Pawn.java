package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

  private ChessMatch match;

  public Pawn(Board board, Color color, ChessMatch match) {
    super(board, color);
    this.match = match;
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

      //En Passant White
      if (this.position.getRow() == 3) {
        Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
        if (this.getBoard().positionExistis(left)
            && this.isThereOpponentPiece(left)
            && this.getBoard().piece(left) == this.match.getEnPassantVulnerable()) {
          matriz[left.getRow() - 1][left.getColumn()] = true;
        }
        Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
        if (this.getBoard().positionExistis(right)
            && this.isThereOpponentPiece(right)
            && this.getBoard().piece(right) == this.match.getEnPassantVulnerable()) {
          matriz[right.getRow() - 1][right.getColumn()] = true;
        }
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

      //En Passant Black
      if (this.position.getRow() == 4) {
        Position left = new Position(this.position.getRow(), this.position.getColumn() - 1);
        if (this.getBoard().positionExistis(left)
            && this.isThereOpponentPiece(left)
            && this.getBoard().piece(left) == this.match.getEnPassantVulnerable()) {
          matriz[left.getRow() + 1][left.getColumn()] = true;
        }
        Position right = new Position(this.position.getRow(), this.position.getColumn() + 1);
        if (this.getBoard().positionExistis(right)
            && this.isThereOpponentPiece(right)
            && this.getBoard().piece(right) == this.match.getEnPassantVulnerable()) {
          matriz[right.getRow() + 1][right.getColumn()] = true;
        }
      }

    }
    return matriz;
  }
}

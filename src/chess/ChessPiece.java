package chess;

import boardgame.Board;
import boardgame.Piece;

public class ChessPiece extends Piece {

  protected Color color;
  protected Integer moveCount;

  public ChessPiece(Board board, Color color) {
    super(board);
    this.color = color;
  }

  public Color getColor() {
    return this.color;
  }
}

package chess;

import boardgame.Board;
import boardgame.Piece;

public abstract class ChessPiece extends Piece {

  private final Color color;
  protected Integer moveCount;

  public ChessPiece(Board board, Color color) {
    super(board);
    this.color = color;
  }

  public Color getColor() {
    return this.color;
  }
}

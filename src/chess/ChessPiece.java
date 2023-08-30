package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

  private final Color color;
  private int moveCount;

  public ChessPiece(Board board, Color color) {
    super(board);
    this.color = color;
  }

  public Color getColor() {
    return this.color;
  }


  public int getMoveCount() {
    return moveCount;
  }

  public void increaseMoveCount() {
    this.moveCount += 1;
  }

  public void decreaseMoveCount() {
    this.moveCount -= 1;
  }

  public ChessPosition getChessPosition() {
    return ChessPosition.fromPosition(position);
  }

  protected boolean isThereOpponentPiece(Position position) {
    ChessPiece piece = (ChessPiece) getBoard().piece(position);
    return piece != null && piece.getColor() != this.color;
  }
}

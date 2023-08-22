package chess;

import boardgame.Position;

public class ChessPosition {

  private int row;
  private char column;

  public ChessPosition(char column, int row) {
    if (row < 1 || row > 8 || column < 'a' || column > 'h') {
      throw new ChessException("Error instantiating the chess position: Valid values are a1 "
          + "through h8.");
    }
    this.row = row;
    this.column = column;
  }

  public int getRow() {
    return row;
  }

  public char getColumn() {
    return column;
  }

  protected Position toPosition() {
    return new Position(8 - this.row, this.column - 'a');
  }

  protected static ChessPosition fromPosition(Position position) {
    return new ChessPosition((char) (position.getColumn() + 1),8 - position.getRow());
  }

  @Override
  public String toString() {
    return "" + this.column + this.row;
  }
}

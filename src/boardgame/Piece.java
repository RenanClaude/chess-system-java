package boardgame;

public abstract class Piece {

  protected Position position;
  private Board board;

  public Piece(Board board) {
    this.board = board;
  }

  protected Board getBoard() {
    return board;
  }

  public abstract boolean[][] possibleMoves();

  public boolean possibleMove(Position position) {
    return possibleMoves()[position.getRow()][position.getColumn()];
  }

  public boolean isThereAnyPossibleMove() {
    boolean[][] boolBoard = possibleMoves();
    for (int indexL = 0; indexL < boolBoard.length; indexL += 1) {
      for (int indexC = 0; indexC < boolBoard.length; indexC += 1) {
        if (boolBoard[indexL][indexC]) {
          return true;
        }
      }
    }
    return false;
  }
}

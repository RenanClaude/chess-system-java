package boardgame;

public class Board {

  private int rows;
  private int columns;
  private Piece[][] pieces;

  public Board(int rows, int columns) {
    if (rows < 1 || columns < 1) {
      throw new BoardException("Error creating board: There must be at least 1 row and 1 column.");
    }
    this.rows = rows;
    this.columns = columns;
    pieces = new Piece[rows][columns];
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

  public Piece piece(int row, int column) {
    if (!positionExistis(row, column)) {
      throw new BoardException("This position does not exist on the board");
    }
    return this.pieces[row][column];
  }

  public Piece piece(Position position) {
    if (!positionExistis(position)) {
      throw new BoardException("This position does not exist on the board");
    }
    return this.pieces[position.getRow()][position.getColumn()];
  }

  public void placePiece(Piece piece, Position position) {
    if (thereIsAPieace(position)) {
      throw new BoardException("There is already a piece in that position " + position);
    }
    this.pieces[position.getRow()][position.getColumn()] = piece;
    piece.position = position;
  }

  public Piece removePiece(Position position) {
    if (!positionExistis(position)) {
      throw new BoardException("This position does not exist on the board");
    }
    if (this.piece(position) == null) {
      return null;
    }
    Piece aux = this.piece(position);
    aux.position = null;
    this.pieces[position.getRow()][position.getColumn()] = null;
    return aux;
  }

  private boolean positionExistis(int row, int column) {
    return row >= 0 && row < this.rows && column >= 0 && column < this.columns;
  }

  public boolean positionExistis(Position position) {
    return positionExistis(position.getRow(), position.getColumn());
  }

  public boolean thereIsAPieace(Position position) {
    if (!positionExistis(position)) {
      throw new BoardException("This position does not exist on the board");
    }
    return this.piece(position) != null;
  }
}

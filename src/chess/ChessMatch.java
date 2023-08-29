package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

  private int turn;
  private Color currentPlayer;
  private final Board board;
  private boolean check;
  private List<ChessPiece> piecesOnTheBoard = new ArrayList<>();
  private List<ChessPiece> capturedPieces = new ArrayList<>();

  public ChessMatch() {
    this.board = new Board(8, 8);
    this.turn = 1;
    this.currentPlayer = Color.WHITE;
    initialSetup();
  }

  public int getTurn() {
    return turn;
  }

  public Color getCurrentPlayer() {
    return currentPlayer;
  }

  public boolean getCheck() {
    return this.check;
  }

  public ChessPiece[][] getPieces() {
    ChessPiece[][] matriz = new ChessPiece[this.board.getRows()][this.board.getColumns()];
    for (int indexL = 0; indexL < this.board.getRows(); indexL += 1) {
      for (int indexC = 0; indexC < this.board.getColumns(); indexC += 1) {
        matriz[indexL][indexC] = (ChessPiece) this.board.piece(indexL, indexC);
      }
    }
    return matriz;
  }

  public boolean[][] possibleMoves(ChessPosition sourcePosition) {
    Position position = sourcePosition.toPosition();
    validateSourcePosition(position);
    return board.piece(position).possibleMoves();
  }

  public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
    Position source = sourcePosition.toPosition();
    Position target = targetPosition.toPosition();
    validateSourcePosition(source);
    validateTargetPosition(source, target);
    Piece capturedPiece = makeMove(source, target);

    if (testCheck(this.currentPlayer)) {
      undoMove(source, target, (ChessPiece) capturedPiece);
      throw new ChessException("You can't put yourself in check");
    }
    this.check = testCheck(opponent(this.currentPlayer));
    nextTurn();
    return (ChessPiece) capturedPiece;
  }

  private Piece makeMove(Position source, Position target) {
    Piece p = this.board.removePiece(source);
    Piece capturedPiece = this.board.removePiece(target);
    if (capturedPiece != null) {
      this.piecesOnTheBoard.remove(capturedPiece);
      this.capturedPieces.add((ChessPiece) capturedPiece);
    }
    this.board.placePiece(p, target);
    return capturedPiece;
  }

  private void undoMove(Position source, Position target, ChessPiece capturedPiece) {
    Piece p = this.board.removePiece(target);
    this.board.placePiece(p, source);
    if (capturedPiece != null) {
      this.board.placePiece(capturedPiece, target);
      this.capturedPieces.remove(capturedPiece);
      this.piecesOnTheBoard.add(capturedPiece);
    }
  }

  private void validateSourcePosition(Position srcPosition) {
    if (!this.board.thereIsAPieace(srcPosition)) {
      throw new ChessException("There is no piece on source position");
    }
    if (this.currentPlayer != ((ChessPiece) board.piece(srcPosition)).getColor()) {
      throw new ChessException("The piece chosen is not yours.");
    }
    if (!this.board.piece(srcPosition).isThereAnyPossibleMove()) {
      throw new ChessException("There are no possible moves for the chosen piece");
    }
  }

  private void validateTargetPosition(Position source, Position target) {
    if (!this.board.piece(source).possibleMove(target)) {
      throw new ChessException("The chosen piece cannot move to the target position.");
    }
  }

  private void nextTurn() {
    this.turn += 1;
    this.currentPlayer = this.currentPlayer == Color.WHITE ? Color.BLACK : Color.WHITE;
  }

  private Color opponent(Color color) {
    return color == Color.WHITE ? Color.BLACK : Color.WHITE;
  }

  private ChessPiece king(Color color) {
    List<ChessPiece> list = this
        .piecesOnTheBoard.stream().filter(x -> x.getColor() == color).toList();
    for (ChessPiece piece : list) {
      if (piece instanceof King) {
        return piece;
      }
    }
    throw new IllegalStateException("There is no " + color + " King on the board");
  }

  private boolean testCheck(Color color) {
    Position kingPosition = king(color).getChessPosition().toPosition();
    List<ChessPiece> opponentPieces =
        this.piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(color)).toList();
    for (ChessPiece piece : opponentPieces) {
      boolean[][] matriz = piece.possibleMoves();
      if (matriz[kingPosition.getRow()][kingPosition.getColumn()]) {
        return true;
      }
    }
    return false;
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
    this.piecesOnTheBoard.add(piece);
  }

  public void initialSetup() {
    placeNewPiece('c', 1, new Rook(board, Color.WHITE));
    placeNewPiece('c', 2, new Rook(board, Color.WHITE));
    placeNewPiece('d', 2, new Rook(board, Color.WHITE));
    placeNewPiece('e', 2, new Rook(board, Color.WHITE));
    placeNewPiece('e', 1, new Rook(board, Color.WHITE));
    placeNewPiece('d', 1, new King(board, Color.WHITE));

    placeNewPiece('c', 7, new Rook(board, Color.BLACK));
    placeNewPiece('c', 8, new Rook(board, Color.BLACK));
    placeNewPiece('d', 7, new Rook(board, Color.BLACK));
    placeNewPiece('e', 7, new Rook(board, Color.BLACK));
    placeNewPiece('e', 8, new Rook(board, Color.BLACK));
    placeNewPiece('d', 8, new King(board, Color.BLACK));
  }
}

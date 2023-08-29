package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;
import java.util.ArrayList;
import java.util.List;

public class ChessMatch {

  private int turn;
  private Color currentPlayer;
  private final Board board;
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

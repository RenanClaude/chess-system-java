package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.Bishop;
import chess.pieces.King;
import chess.pieces.Knight;
import chess.pieces.Pawn;
import chess.pieces.Queen;
import chess.pieces.Rook;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

  private int turn;
  private Color currentPlayer;
  private final Board board;
  private boolean check;
  private boolean checkMate;
  private ChessPiece enPassantVulnerable;
  private ChessPiece promoted;
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

  public boolean getCheckMate() {
    return this.checkMate;
  }

  public ChessPiece getEnPassantVulnerable() {
    return this.enPassantVulnerable;
  }

  public ChessPiece getPromoted() {
    return promoted;
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
      throw new ChessException("You can't keep yourself in check.");
    }
    ChessPiece movedPiece = (ChessPiece) this.board.piece(target);

    //Promotion
    this.promoted = null;
    if (movedPiece instanceof Pawn) {
      if ((movedPiece.getColor() == Color.WHITE && target.getRow() == 0)
          || (movedPiece.getColor() == Color.BLACK && target.getRow() == 7)) {
        this.promoted = (ChessPiece) this.board.piece(target);
        this.promoted = this.replacePromotedPiece("Q");
      }
    }

    this.check = testCheck(opponent(this.currentPlayer));

    if (this.testCheckMate(this.opponent(this.currentPlayer))) {
      this.checkMate = true;
    } else {
      nextTurn();
    }

    //En Passant
    if (movedPiece instanceof Pawn
        && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
      this.enPassantVulnerable = movedPiece;
    } else {
      this.enPassantVulnerable = null;
    }

    return (ChessPiece) capturedPiece;
  }

  public ChessPiece replacePromotedPiece(String type) {
    if (this.promoted == null) {
      throw new IllegalStateException("There is no piece to be promoted");
    }
    type = type.toUpperCase();
    if (!type.equals("B") && !type.equals("R") && !type.equals("N") && !type.equals("Q")) {
      throw new InvalidParameterException("Invalid type for promotion");
    }
    Position pos = this.promoted.getChessPosition().toPosition();
    Piece p = this.board.removePiece(pos);
    this.piecesOnTheBoard.remove(p);
    ChessPiece newPiece = this.newPiece(type, promoted.getColor());
    this.board.placePiece(newPiece, pos);
    this.piecesOnTheBoard.add(newPiece);

    return newPiece;
  }

  public ChessPiece newPiece(String type, Color color) {
    if (type.equals("B")) {
      return new Bishop(this.board, color);
    }
    if (type.equals("R")) {
      return new Rook(this.board, color);
    }
    if (type.equals("N")) {
      return new Knight(this.board, color);
    }
    return new Queen(this.board, color);
  }

  private Piece makeMove(Position source, Position target) {
    ChessPiece p = (ChessPiece) this.board.removePiece(source);
    p.increaseMoveCount();
    Piece capturedPiece = this.board.removePiece(target);
    this.board.placePiece(p, target);
    if (capturedPiece != null) {
      this.piecesOnTheBoard.remove(capturedPiece);
      this.capturedPieces.add((ChessPiece) capturedPiece);
    }
    //Kingside Castling - Rook
    if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
      Position posRook = new Position(source.getRow(), source.getColumn() + 3);
      Position targetRook1 = new Position(source.getRow(), source.getColumn() + 1);
      ChessPiece rook1 = (ChessPiece) this.board.removePiece(posRook);
      this.board.placePiece(rook1, targetRook1);
      rook1.increaseMoveCount();
    }
    //Queenside Castling - Rook
    if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
      Position posRook = new Position(source.getRow(), source.getColumn() - 4);
      Position targetRook1 = new Position(source.getRow(), source.getColumn() - 1);
      ChessPiece rook1 = (ChessPiece) this.board.removePiece(posRook);
      this.board.placePiece(rook1, targetRook1);
      rook1.increaseMoveCount();
    }

    // En Passant
    if (p instanceof Pawn) {
      if (source.getColumn() != target.getColumn() && capturedPiece == null) {
        Position pawnPosition;
        if (p.getColor() == Color.WHITE) {
          pawnPosition = new Position(target.getRow() + 1, target.getColumn());
        } else {
          pawnPosition = new Position(target.getRow() - 1, target.getColumn());
        }
        capturedPiece = this.board.removePiece(pawnPosition);
        this.capturedPieces.add((ChessPiece) capturedPiece);
        this.piecesOnTheBoard.remove(capturedPiece);
      }
    }

    return capturedPiece;
  }

  private void undoMove(Position source, Position target, ChessPiece capturedPiece) {
    ChessPiece p = (ChessPiece) this.board.removePiece(target);
    p.decreaseMoveCount();
    this.board.placePiece(p, source);
    if (capturedPiece != null) {
      this.board.placePiece(capturedPiece, target);
      this.capturedPieces.remove(capturedPiece);
      this.piecesOnTheBoard.add(capturedPiece);
    }
    //Kingside Castling - Rook
    if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
      Position posRook = new Position(source.getRow(), source.getColumn() + 3);
      Position targetRook1 = new Position(source.getRow(), source.getColumn() + 1);
      ChessPiece rook1 = (ChessPiece) this.board.removePiece(targetRook1);
      this.board.placePiece(rook1, posRook);
      rook1.decreaseMoveCount();
    }
    //Queenside Castling - Rook
    if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
      Position posRook = new Position(source.getRow(), source.getColumn() - 4);
      Position targetRook1 = new Position(source.getRow(), source.getColumn() - 1);
      ChessPiece rook1 = (ChessPiece) this.board.removePiece(targetRook1);
      this.board.placePiece(rook1, posRook);
      rook1.decreaseMoveCount();
    }

    // En Passant
    if (p instanceof Pawn) {
      if (source.getColumn() != target.getColumn() && capturedPiece == this.enPassantVulnerable) {
        ChessPiece pawn = (ChessPiece) this.board.removePiece(target);
        Position pawnPosition;
        if (p.getColor() == Color.WHITE) {
          pawnPosition = new Position(3, target.getColumn());
        } else {
          pawnPosition = new Position(4, target.getColumn());
        }
        this.board.placePiece(pawn, pawnPosition);
      }
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

  public boolean kingWillBeInCheck(Position position, Color color) {
    List<ChessPiece> opponentPieces =
        this.piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(color)).toList();
    for (ChessPiece piece : opponentPieces) {
      if (!(piece instanceof King)) {
        boolean[][] matriz = piece.possibleMoves();
        if (matriz[position.getRow()][position.getColumn()]) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean testCheckKingSideCastling(Color color) {
    Position kingPosition = king(color).getChessPosition().toPosition();
    List<ChessPiece> opponentPieces =
        this.piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(color)).toList();
    for (ChessPiece piece : opponentPieces) {
      if (!(piece instanceof King)) {
        boolean[][] matriz = piece.possibleMoves();
        if (matriz[kingPosition.getRow()][
            kingPosition.getColumn() + 1]) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean testCheckQueenSideCastling(Color color) {
    Position kingPosition = king(color).getChessPosition().toPosition();
    List<ChessPiece> opponentPieces =
        this.piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(color)).toList();
    for (ChessPiece piece : opponentPieces) {
      if (!(piece instanceof King)) {
        boolean[][] matriz = piece.possibleMoves();
        if (matriz[kingPosition.getRow()][
            kingPosition.getColumn() - 1]) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean testCheckMate(Color color) {
    if (!testCheck(color)) {
      return false;
    }
    List<ChessPiece> piecesList = this
        .piecesOnTheBoard.stream().filter(x -> x.getColor() == color).toList();
    for (ChessPiece piece : piecesList) {
      boolean[][] matriz = piece.possibleMoves();
      for (int indexL = 0; indexL < this.board.getRows(); indexL += 1) {
        for (int indexC = 0; indexC < this.board.getColumns(); indexC += 1) {
          if (matriz[indexL][indexC]) {
            Position source = piece.getChessPosition().toPosition();
            Position target = new Position(indexL, indexC);
            Piece capturedPiece = this.makeMove(source, target);
            boolean testCheck = this.testCheck(color);
            undoMove(source, target, (ChessPiece) capturedPiece);
            if (!testCheck) {
              return false;
            }
          }
        }
      }
    }
    return true;
  }

  private void placeNewPiece(char column, int row, ChessPiece piece) {
    this.board.placePiece(piece, new ChessPosition(column, row).toPosition());
    this.piecesOnTheBoard.add(piece);
  }

  public void initialSetup() {
    placeNewPiece('a', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('b', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('c', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('d', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('e', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('f', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('g', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('h', 2, new Pawn(this.board, Color.WHITE, this));
    placeNewPiece('a', 1, new Rook(this.board, Color.WHITE));
    placeNewPiece('b', 1, new Knight(this.board, Color.WHITE));
    placeNewPiece('c', 1, new Bishop(this.board, Color.WHITE));
    placeNewPiece('d', 1, new Queen(this.board, Color.WHITE));
    placeNewPiece('e', 1, new King(this.board, Color.WHITE, this));
    placeNewPiece('f', 1, new Bishop(this.board, Color.WHITE));
    placeNewPiece('g', 1, new Knight(this.board, Color.WHITE));
    placeNewPiece('h', 1, new Rook(this.board, Color.WHITE));

    placeNewPiece('a', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('b', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('c', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('d', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('e', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('f', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('g', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('h', 7, new Pawn(this.board, Color.BLACK, this));
    placeNewPiece('a', 8, new Rook(this.board, Color.BLACK));
    placeNewPiece('b', 8, new Knight(this.board, Color.BLACK));
    placeNewPiece('c', 8, new Bishop(this.board, Color.BLACK));
    placeNewPiece('d', 8, new Queen(this.board, Color.BLACK));
    placeNewPiece('e', 8, new King(this.board, Color.BLACK, this));
    placeNewPiece('f', 8, new Bishop(this.board, Color.BLACK));
    placeNewPiece('g', 8, new Knight(this.board, Color.BLACK));
    placeNewPiece('h', 8, new Rook(this.board, Color.BLACK));
  }
}

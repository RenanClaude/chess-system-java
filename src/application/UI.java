package application;

import chess.ChessPiece;

public class UI {

  public static void printBoard(ChessPiece[][] pieces) {
    for (int indexL = 0; indexL < pieces.length; indexL += 1) {
      System.out.print((8 - indexL) + " ");
      for (int indexC = 0; indexC < pieces.length; indexC += 1) {
        printPiece(pieces[indexL][indexC]);
      }
      System.out.println();
    }
    System.out.println("  a b c d e f g h");
  }

  public static void printPiece(ChessPiece piece) {
    if (piece == null) {
      System.out.print("-");
    } else {
      System.out.print(piece);
    }
    System.out.print(" ");
  }
}

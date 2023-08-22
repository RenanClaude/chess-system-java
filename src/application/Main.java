package application;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    ChessMatch match = new ChessMatch();

    while (true) {
      try {
        UI.clearScreen();
        UI.printBoard(match.getPieces());
        System.out.println();
        System.out.print("Source: ");
        ChessPosition source = UI.readChessPosition(sc);

        System.out.print("Target: ");
        ChessPosition target = UI.readChessPosition(sc);
        ChessPiece capturedPiece = match.performChessMove(source, target);
        System.out.println();
      } catch (ChessException error) {
        System.out.println(error.getMessage());
        sc.nextLine();
      } catch (InputMismatchException error) {
        System.out.println(error.getMessage());
        sc.nextLine();
      }
    }
  }
}
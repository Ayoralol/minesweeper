package minesweeper;

import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Game game = GameSetup.setupGame(scanner);
    game.start();
    scanner.close();
  }
}

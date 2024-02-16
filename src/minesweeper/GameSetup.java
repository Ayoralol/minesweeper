package minesweeper;

import java.util.Scanner;

public class GameSetup {

  public static Game setupGame(Scanner scanner) {
    int size = 0;
    while (size < 5 || size > 26) {
      System.out.println("Enter the size of the board (Min 5 - Max 26): ");
      size = scanner.nextInt();
      if (size < 5 || size > 26) {
        System.out.println(
          "Invalid size. Please enter a number between 5 and 26."
        );
      }
    }

    int difficulty = 0;
    while (difficulty < 1 || difficulty > 3) {
      System.out.println(
        "Enter the difficulty level (1 for EASY, 2 for MEDIUM, 3 for HARD): "
      );
      difficulty = scanner.nextInt();
      if (difficulty < 1 || difficulty > 3) {
        System.out.println(
          "Invalid difficulty level. Please enter a number between 1 and 3."
        );
      }
    }

    Board.Difficulty level;
    switch (difficulty) {
      case 1:
        level = Board.Difficulty.EASY;
        break;
      case 2:
        level = Board.Difficulty.MEDIUM;
        break;
      case 3:
        level = Board.Difficulty.HARD;
        break;
      default:
        System.out.println("Invalid difficulty level. Defaulting to EASY.");
        level = Board.Difficulty.EASY;
    }

    return new Game(size, level);
  }
}

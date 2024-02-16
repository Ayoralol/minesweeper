package minesweeper;

import java.util.Scanner;

public class InputHandler {

  private Scanner scanner;
  private int size;

  public InputHandler(int size) {
    this.scanner = new Scanner(System.in);
    this.size = size;
  }

  public int[] getUserInput() {
    while (true) {
      System.out.println(
        "Enter your move - \"C A 1\" to click a square or \"F A 1\" to flag a square:"
      );
      String[] parts = scanner.nextLine().toUpperCase().split(" ");

      if (
        parts.length != 3 ||
        !(parts[0].equals("C") || parts[0].equals("F")) ||
        parts[1].length() != 1 ||
        !Character.isLetter(parts[1].charAt(0)) ||
        !parts[2].matches("\\d+")
      ) {
        System.out.println(
          "Invalid input. Please enter a command (C or F), a column (A-J), and a row (1-10)."
        );
        continue;
      }

      int command = parts[0].startsWith("F") ? 1 : 2;
      int column = parts[1].charAt(0) - 'A' + 1;
      int row = Integer.parseInt(parts[2]);

      if (column < 1 || column > size || row < 1 || row > size) {
        System.out.println("Please input a valid coordinate.");
        continue;
      }

      System.out.println(
        "You entered: " + parts[0] + " " + parts[1] + " " + parts[2]
      );

      return new int[] { command, column, row };
    }
  }
}

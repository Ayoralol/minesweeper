package minesweeper;

import java.util.Random;

public class Board {

  private String[][] board;
  private boolean[][] opened;
  private boolean[][] flagged;
  private int SIZE;
  private boolean initialized = false;
  private boolean gameOver = false;
  private int squaresRevealed = 0;
  private int BOMB_COUNT;
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_WHITE = "\u001B[37m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RESET = "\u001B[0m";

  public enum Difficulty {
    EASY,
    MEDIUM,
    HARD,
  }

  public Board(int size, Difficulty difficulty) {
    this.SIZE = size;
    this.board = new String[SIZE][SIZE];
    this.opened = new boolean[SIZE][SIZE];
    this.flagged = new boolean[SIZE][SIZE];

    switch (difficulty) {
      case EASY:
        this.BOMB_COUNT = SIZE * SIZE / 10;
        break;
      case MEDIUM:
        this.BOMB_COUNT = SIZE * SIZE / 6;
        break;
      case HARD:
        this.BOMB_COUNT = SIZE * SIZE / 4;
        break;
    }

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        this.board[i][j] = "[-]";
      }
    }
  }

  public void initializeBombs(int firstMoveX, int firstMoveY) {
    int bombCount = BOMB_COUNT;
    Random rand = new Random();

    firstMoveX -= 1;
    firstMoveY -= 1;

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board[i][j] = " ";
      }
    }

    while (bombCount > 0) {
      int x = rand.nextInt(SIZE);
      int y = rand.nextInt(SIZE);

      if ((x == firstMoveX && y == firstMoveY) || "B".equals(board[x][y])) {
        continue;
      }

      board[x][y] = "B";
      bombCount--;
    }

    initialized = true;
  }

  public void calculateSurroundingBombs() {
    int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
    int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        if ("B".equals(board[i][j])) {
          continue;
        }

        int bombCount = 0;
        for (int k = 0; k < 8; k++) {
          int x = i + dx[k];
          int y = j + dy[k];

          if (
            x >= 0 && x < SIZE && y >= 0 && y < SIZE && "B".equals(board[x][y])
          ) {
            bombCount++;
          }
        }
        board[i][j] = bombCount > 0 ? String.valueOf(bombCount) : "0";
      }
    }
  }

  public boolean flagSquare(int x, int y) {
    if (!initialized) {
      System.out.println("Please click a square before you place flags");
      return false;
    }

    x -= 1;
    y -= 1;

    if (x < 0 || x >= SIZE || y < 0 || y >= SIZE || opened[x][y]) {
      return false;
    }

    if (flagged[x][y]) {
      flagged[x][y] = false;
    } else {
      flagged[x][y] = true;
    }
    return true;
  }

  public void openSquare(int x, int y) {
    x -= 1;
    y -= 1;

    if (
      x < 0 || x >= SIZE || y < 0 || y >= SIZE || opened[x][y] || flagged[x][y]
    ) {
      return;
    }

    if (!opened[x][y]) {
      opened[x][y] = true;
      squaresRevealed++;

      if ("B".equals(board[x][y])) {
        gameOver = true;
        return;
      }

      if ("0".equals(board[x][y])) {
        for (int dx = -1; dx <= 1; dx++) {
          for (int dy = -1; dy <= 1; dy++) {
            if (dx == 0 && dy == 0) continue;
            openSquare(x + dx + 1, y + dy + 1);
          }
        }
      }
    }
  }

  public void render() {
    for (int i = SIZE; i > 0; i--) {
      System.out.printf("%2d ", i);
      for (int j = 0; j < SIZE; j++) {
        if (gameOver) {
          System.out.print(
            "[" +
            ANSI_RED +
            (board[j][i - 1].equals("0") ? " " : board[j][i - 1]) +
            ANSI_RESET +
            "] "
          );
        } else if (opened[j][i - 1]) {
          System.out.print(
            "[" +
            ANSI_BLUE +
            (board[j][i - 1].equals("0") ? " " : board[j][i - 1]) +
            ANSI_RESET +
            "] "
          );
        } else if (flagged[j][i - 1]) {
          System.out.print("[" + ANSI_RED + "F" + ANSI_RESET + "] ");
        } else {
          System.out.print("[" + ANSI_GREEN + "\u25A1" + ANSI_RESET + "] ");
        }
      }
      System.out.println();
    }

    System.out.print("    ");
    for (char i = 'A'; i < 'A' + SIZE; i++) {
      System.out.print(i + "   ");
    }
    System.out.println();
  }

  public boolean hasInitialized() {
    return initialized;
  }

  public boolean isGameOver() {
    if (gameOver) {
      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
          opened[i][j] = true;
        }
      }
      render();
    }
    return gameOver;
  }

  public boolean hasWon() {
    boolean winner = squaresRevealed == SIZE * SIZE - BOMB_COUNT;
    if (winner) {
      for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
          opened[i][j] = true;
        }
      }
      render();
    }
    return winner;
  }
}

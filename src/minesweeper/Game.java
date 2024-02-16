package minesweeper;

public class Game {

  private Board board;
  private InputHandler inputHandler;

  public Game(int size, Board.Difficulty difficulty) {
    this.board = new Board(size, difficulty);
    this.inputHandler = new InputHandler(size);
  }

  public void start() {
    boolean firstRender = true;
    int[] move = new int[3];
    while (true) {
      if (firstRender || move[0] != 1 || board.hasInitialized()) {
        board.render();
        firstRender = false;
      }

      move = inputHandler.getUserInput();

      if (!board.hasInitialized()) {
        if (move[0] == 1) {
          System.out.println(
            "**** Please click a square before you place flags ****"
          );
          continue;
        }
        board.initializeBombs(move[1], move[2]);
        board.calculateSurroundingBombs();
      }

      if (move[0] == 1) {
        board.flagSquare(move[1], move[2]);
      } else {
        board.openSquare(move[1], move[2]);
      }

      if (board.isGameOver()) {
        System.out.println("That was a bomb! Game over!");
        break;
      }

      if (board.hasWon()) {
        System.out.println("Congratulations! You won!");
        break;
      }
    }
  }
}

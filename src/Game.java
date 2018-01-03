/**
 * class to represent the game.
 * runs the game and gets the winner.
 */
public class Game {
    private Board board;
    private char currPlayer;
    private int depth;

    /**
     * constructor.
     *
     * @param filename input file.
     * @param depth    depth of minimax algorithm for each turn.
     */
    public Game(String filename, int depth) {
        this.board = Board.fromFile(filename);
        this.currPlayer = Board.BLACK;
        this.depth = depth;
    }

    /**
     * runs the game till the end and get the winner.
     *
     * @return 'B' for black, 'W' for white, indicates the winner.
     */
    public char getWinner() {
        char winner;
        this.board.print();
        while ((winner = this.board.getWinner()) == Board.EMPTY) {
            boolean isMaximizingPlayer = this.currPlayer == Board.BLACK;

            // delete
            System.out.print("\nplayer ");
            System.out.print(currPlayer);
            System.out.println(" played:");

            Value move = MinimaxAlgorithm.minimax(this.board, this.depth, isMaximizingPlayer);
            if (move != null) {
                this.board = move.getState();
                board.print();
            } else {
                System.out.println("no move!");
            }

            // next player turn
            this.currPlayer = Board.getRival(this.currPlayer);

        }

        return winner;
    }
}

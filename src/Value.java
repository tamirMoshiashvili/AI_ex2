/**
 * class to hold the value of minimax.
 * contains some state (board) and heuristic-value.
 */
public class Value {
    private Board state;
    private int h;

    /**
     * constructor.
     *
     * @param state board.
     * @param h     heuristic value.
     */
    public Value(Board state, int h) {
        this.state = state;
        this.h = h;
    }

    /**
     * getter for the board.
     *
     * @return board.
     */
    public Board getState() {
        return state;
    }

    /**
     * getter.
     *
     * @return heuristic value.
     */
    public int getH() {
        return h;
    }
}

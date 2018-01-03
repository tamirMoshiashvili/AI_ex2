import java.util.List;

/**
 * the algorithm class.
 * implementation of minimax algorithm on board.
 */
public class MinimaxAlgorithm {

    /**
     * apply the minimax algorithm on the given board.
     *
     * @param node             board.
     * @param depth            current depth.
     * @param maximizingPlayer boolean to tell if it is the maximizing player.
     * @return value which contains a board after a move and heuristic value.
     */
    public static Value minimax(Board node, int depth, boolean maximizingPlayer) {
        char winner = node.getWinner();
        if (winner != Board.EMPTY) {   // game over
            int h = winner == Board.BLACK ? Integer.MAX_VALUE : winner == Board.WHITE ? Integer.MIN_VALUE : 0;
            return new Value(node, h);
        }
        if (depth == 0) {
            int h = node.getNumBlack() - node.getNumWhite();
            h += node.getNumTokensAtSides(Board.BLACK) - node.getNumTokensAtSides(Board.WHITE);
            return new Value(node, h);
        }

        Value bestValue;
        char player = maximizingPlayer ? Board.BLACK : Board.WHITE;
        List<Board> children = node.getPossibleStates(player);
        if (children.isEmpty()) {
            return null;
        }

        if (maximizingPlayer) {
            bestValue = new Value(node, Integer.MIN_VALUE);
            for (Board b : children) {
                Value v = minimax(b, depth - 1, false);
                if (v != null && v.getH() >= bestValue.getH()) {
                    bestValue = new Value(b, v.getH());
                }
            }
        } else {    // minimizing player
            bestValue = new Value(node, Integer.MAX_VALUE);
            for (Board b : children) {
                Value v = minimax(b, depth - 1, true);
                if (v != null && v.getH() <= bestValue.getH()) {
                    bestValue = new Value(b, v.getH());
                }
            }
        }
        return bestValue;
    }
}

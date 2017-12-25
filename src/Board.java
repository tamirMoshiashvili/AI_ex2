import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * board hsa a matrix, each cell holds a token.
 * each token can be 'B' for black, 'W' for white, 'E' for empty.
 */
public class Board {
    private static final int len = 5;
    static final char BLACK = 'B';
    static final char WHITE = 'W';
    static final char EMPTY = 'E';

    private char matrix[][];
    private int numWhite;
    private int numBlack;

    /**
     * constructor, from string.
     *
     * @param boardStr string that represents the board's state.
     */
    public Board(String boardStr) {
        // convert the string to matrix
        this.matrix = new char[len][len];
        this.numBlack = this.numWhite = 0;

        for (int i = 0; i < len; i++) {
            int i_len = i * len;
            for (int j = 0; j < len; j++) {
                // set the char
                int charIndex = i_len + j;
                char c = boardStr.charAt(charIndex);
                matrix[i][j] = c;

                // update counters
                if (c == BLACK) {
                    this.numBlack += 1;
                } else if (c == WHITE) {
                    this.numWhite += 1;
                }
            }
        }
    }

    /**
     * constructor.
     *
     * @param matrix   the matrix with all the tokens.
     * @param numWhite number of white tokens.
     * @param numBlack number of black tokens.
     */
    public Board(char matrix[][], int numWhite, int numBlack) {
        this.matrix = matrix;
        this.numBlack = numBlack;
        this.numWhite = numWhite;
    }

    /**
     * create a board-object out of a file.
     * the content of the file is the boards state.
     *
     * @param filename name of the file.
     * @return board if read the file successfully, null otherwise.
     */
    public static Board fromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            reader.close();
            return new Board(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println("Error: reading file");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: reading from file");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * getter.
     *
     * @return number of white tokens in the board.
     */
    public int getNumWhite() {
        return numWhite;
    }

    /**
     * getter.
     *
     * @return number of black tokens in the board.
     */
    public int getNumBlack() {
        return numBlack;
    }

    /**
     * get number of tokens as the given one at sides of the board.
     *
     * @param token token color.
     * @return number.
     */
    public int getNumTokensAtSides(char token) {
        int num = 0;
        int lenMinus1 = len - 1;

        // right and left
        for (int i = 0; i < len; ++i) {
            if (this.matrix[i][0] == token) {
                ++num;
            }
            if (this.matrix[i][lenMinus1] == token) {
                ++num;
            }
        }

        // up and down
        for (int j = 1; j < lenMinus1; ++j) {
            if (this.matrix[0][j] == token) {
                ++num;
            }
            if (this.matrix[lenMinus1][j] == token) {
                ++num;
            }
        }

        return num;
    }

    /**
     * copy the board as it is, with all its parameters.
     *
     * @return copy of the current board.
     */
    public Board copy() {
        char clonedMatrix[][] = new char[len][len];
        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < len; ++j) {
                clonedMatrix[i][j] = this.matrix[i][j];
            }
        }
        return new Board(clonedMatrix, this.numWhite, this.numBlack);
    }

    /**
     * make a move according to the given data and update the number of tokens from each color.
     *
     * @param point point to put the token on.
     * @param token token color.
     * @return number of changed tokens.
     */
    public int play(Point point, char token) {
        int numChangedTokens = this.makeMove(point, token);
        if (numChangedTokens == 0) {
            // invalid move
            return 0;
        }

        // update numbers
        if (token == BLACK) {
            this.numBlack += numChangedTokens + 1;
            this.numWhite -= numChangedTokens;
        } else {    // WHITE
            this.numBlack -= numChangedTokens;
            this.numWhite += numChangedTokens + 1;
        }
        return numChangedTokens;
    }

    /**
     * put the given token at the point (row, col) and update the board according to this move.
     *
     * @param point point to put the token on.
     * @param token token color.
     * @return number of changed tokens
     */
    private int makeMove(Point point, char token) {
        int numChangedTokens = 0;
        int row = point.getX(), col = point.getY();

        // check for valid move
        if (row < 0 || col < 0 || row >= len || col >= len) {
            System.out.println("Error: point out of bounds of the board");
            return numChangedTokens;
        } else if (this.matrix[row][col] != EMPTY) {
            System.out.println("Error: chosen place is not empty");
            return numChangedTokens;
        }

        // try all possible states
        for (int i = 0; i < 4; ++i) {
            this.rotateMatrix90DegreesClockwise();
            // rotate row and col
            int temp = row;
            row = len - col - 1;
            col = temp;
            // make changes
            numChangedTokens += rightHorizontalSqaures(row, col, token);
            numChangedTokens += rightDiagonalSquares(row, col, token);
        }
        if (numChangedTokens == 0) {
            // invalid squares
            return 0;
        }
        // valid move, place the token
        this.matrix[row][col] = token;
        return numChangedTokens;
    }

    /**
     * rotate the matrix 90 degrees clockwise.
     */
    private void rotateMatrix90DegreesClockwise() {
        int halfBoardSize = len / 2;
        char temp;
        for (int i = 0; i < halfBoardSize; ++i) {
            for (int j = i; j < len - 1 - i; ++j) {
                temp = this.matrix[i][j];
                // right to top
                this.matrix[i][j] = this.matrix[j][len - 1 - i];
                // bottom to right
                this.matrix[j][len - 1 - i] = this.matrix[len - 1 - i][len - 1 - j];
                // left to bottom
                this.matrix[len - 1 - i][len - 1 - j] = this.matrix[len - 1 - j][i];
                // temp to left
                this.matrix[len - 1 - j][i] = temp;
            }
        }
    }

    /**
     * apply the move, if possible, to the right horizontal row.
     *
     * @param row   row number.
     * @param col   column number.
     * @param token token color.
     * @return number of changed tokens.
     */
    private int rightHorizontalSqaures(int row, int col, char token) {
        int numChangedTokens = 0;
        // right -->
        for (int j = col + 1; j < len; ++j) {
            if (this.matrix[row][j] == EMPTY) {
                // this direction is not valid
                return 0;
            } else if (this.matrix[row][j] == token) {
                // found valid square
                numChangedTokens = j - col - 1;
                // change tokens
                for (++col; col < j; ++col) {
                    this.matrix[row][col] = token;
                }
                break;
            }
        }
        return numChangedTokens;
    }

    /**
     * apply the move, if possible, to the right-down diagonal squares.
     *
     * @param row   row number.
     * @param col   column number.
     * @param token token color.
     * @return number of changed tokens.
     */
    private int rightDiagonalSquares(int row, int col, char token) {
        // right and down
        int numChangedTokens = 0;
        for (int i = row + 1, j = col + 1; i < len && j < len; i++, j++) {
            if (this.matrix[i][j] == EMPTY) {
                // this direction is not valid
                return numChangedTokens;
            } else if (this.matrix[i][j] == token) {
                // found valid square
                numChangedTokens = j - col - 1;
                // change tokens
                for (++row, ++col; row < i && col < j; ++row, ++col) {
                    this.matrix[row][col] = token;
                }
                break;
            }
        }
        return numChangedTokens;
    }

    /**
     * prints the board to screen as matrix.
     */
    public void print() {
        for (int i = 0; i < len; i++) {
            System.out.print(this.matrix[i][0]);

            for (int j = 1; j < len; j++) {
                System.out.print('|');
                System.out.print(this.matrix[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * return the winner of the game as char.
     * win: 'B' for black, 'W' for white.
     * no-winner: 'E'.
     *
     * @return the winner as char.
     */
    public char getWinner() {
        if (this.numBlack + this.numWhite == len * len) {    // game over
            int diff = this.numBlack - this.numWhite;
            return diff > 0 ? BLACK : WHITE;
        } else {    // game is still on
            return EMPTY;
        }
    }

    /**
     * get all the possible states that can be generated by putting the given token in empty-cells,
     * so that move changed the board (changed rival-token's).
     *
     * @param token token color.
     * @return list of boards, each is optional state by player's move,
     * empty if no available move for the player with the given token.
     */
    public List<Board> getPossibleStates(char token) {
        List<Board> states = new ArrayList<Board>();
        Board board = this.copy();

        for (int i = 0; i < len; ++i) {
            for (int j = 0; j < len; ++j) {
                if (this.matrix[i][j] == EMPTY) {   // try putting the token
                    int numChangedTokens = board.play(new Point(i, j), token);
                    if (numChangedTokens != 0) { // valid move
                        states.add(board);
                        board = this.copy();
                    }
                }
            }
        }

        return states;
    }

    /**
     * get the token of the rival player with the given token.
     *
     * @param token token of player.
     * @return char.
     */
    public static char getRival(char token) {
        return token == BLACK ? WHITE : BLACK;
    }
}

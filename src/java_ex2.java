import java.io.*;

/**
 * main class to run the exercise.
 */
public class java_ex2 {
    public static void main(String args[]) {
        System.out.println("start");
        String filename = "input.txt";
        int depth = 3;

        Game game = new Game(filename, depth);
        char winner = game.getWinner();

        try {
            Writer writer = new FileWriter(new File("output.txt"));
            writer.write(winner);
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: problem with output file");
            e.printStackTrace();
        }
    }
}

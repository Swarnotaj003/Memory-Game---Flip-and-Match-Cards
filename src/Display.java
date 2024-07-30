import java.util.concurrent.TimeUnit;

public class Display {
    public static void printMatrixForFiveSeconds(int[][] matrix) {
        // Print the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void main (String[] args) {
        int[][] matrix = {
            {1,2,3},
            {4,5,6},
            {7,8,9}
        };
        printMatrixForFiveSeconds(matrix);
    }
}
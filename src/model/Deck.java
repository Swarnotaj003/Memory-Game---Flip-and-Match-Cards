package model;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Deck 
{
    private int numOfCards;
    private int numOfSymbols;
    private Card[][] matrix;

    public Deck (int numOfSymbols) {
        this.numOfSymbols = numOfSymbols;
        this.numOfCards = 4 * numOfSymbols * numOfSymbols;
        matrix = new Card[2*numOfSymbols][2*numOfSymbols];

        for (int i = 0; i < 2*numOfSymbols; i++) {
            for (int j = 0; j < 2*numOfSymbols; j++) {
                matrix[i][j] = new Card((char)('A' + (i*2*numOfSymbols + j)/4));
            }
        }
    }

    public int getNumberOfCards() {
        return this.numOfCards;
    }

    public int getMatrixDimension() {
        return 2 * this.numOfSymbols;
    }

    public Card getCard (int row, int col) {
        return this.matrix[row][col];
    }

    public boolean isEmpty() {
        return this.numOfCards == 0;
    }

    public boolean isCardAvailable (int row, int col) {
        return matrix[row][col] != null;
    }

    public Card withdrawCard (int row, int col) {
        numOfCards--;
        Card card = matrix[row][col];
        card.flip();
        matrix[row][col] = null;
        return card;
    }

    public void addCard (Card card, int row, int col) {
        numOfCards++;
        card.flip();
        matrix[row][col] = card;
    }

    public void shuffle() {
        Random random = new Random();
        int row, col;
        for (int i = 0; i < 2*numOfSymbols; i++) {
            for (int j = 0; j < 2*numOfSymbols; j++) {
                row = random.nextInt(2*numOfSymbols);
                col = random.nextInt(2*numOfSymbols);

                Card tempCard = matrix[i][j];
                matrix[i][j] = matrix[row][col];
                matrix[row][col] = tempCard;
            }
        }
    }

    public void showDeck() {
        for (int i = 0; i < 2*numOfSymbols; i++) {
            for (int j = 0; j < 2*numOfSymbols; j++) {
                char symbol = isCardAvailable(i, j) ? (matrix[i][j].isFaceUp() ? matrix[i][j].showCardFace() : '#') : '_';
                System.out.print(symbol + " ");
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
}
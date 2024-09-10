package view;

import java.util.concurrent.TimeUnit;
import model.GameModel;
import model.Deck;

public class GameView 
{
    private GameModel gameModel;

    public GameView (GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void showInstructions() {
        new Instructions();
    }

    public void showDeck() {
        Deck deck = this.gameModel.getDeck();
        for (int i = 0; i < deck.getMatrixDimension(); i++) {
            for (int j = 0; j < deck.getMatrixDimension(); j++) {
                char symbol = deck.isCardAvailable(i, j) ? 
                    (deck.getCard(i, j).isFaceUp() ? deck.getCard(i, j).showCardFace() : '#')
                    : '_';
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        // Clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void showResults() {
        System.out.println("*****************************************");
        System.out.printf("%22s\n", "RESULT");
        System.out.println("*****************************************");

        int n = this.gameModel.getDeck().getMatrixDimension();
        int numberOfPairs = n*n/2-1;
        int numberOfAttempts = gameModel.getAttempts();
        float accuracy = (float)numberOfPairs * 100 / numberOfAttempts;

        System.out.printf("No. of pairs matched : %5d\n", numberOfPairs);
        System.out.printf("No. of attempts      : %5d\n", numberOfAttempts);
        System.out.printf("Your accuracy        : %2.2f%%\n", accuracy);
        System.out.println("-----------------------------------------");
    }
}
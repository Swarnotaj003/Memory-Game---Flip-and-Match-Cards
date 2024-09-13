package view;

import java.util.concurrent.TimeUnit;
import model.GameModel;
import model.Deck;

public class GameView implements View
{
    private GameModel gameModel;

    public GameView (GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public void displayInstructions() {
        System.out.println("************************************************************************");
        System.out.printf("%45s","MEMORY MATCH GAME\n");
        System.out.println("************************************************************************");
        System.out.println("Test your memory and concentration");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("GAME OVERVIEW");
        System.out.println("- A deck of shuffled cards is presented, all facing down");
        System.out.println("- Set the gameplay by entering your preferred `game level`");
        System.out.println("- Enter the addresses of two cards to flip over");
        System.out.println("- If you find a match, you will keep the cards");
        System.out.println("- If you don't find a match, the cards will flipped back");
        System.out.println("- Matched or not don't worry, carry on remembering & flipping");
        System.out.println("- The play continues until the last pair remains to be matched");
        System.out.println("- Lesser the number of attempts, better the score");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("GAMEPLAY LEVELS");
        System.out.println("\t1 : Flip & match among 4x4 cards");
        System.out.println("\t2 : Flip & match among 6x6 cards");
        System.out.println("\t3 : Flip & match among 8x8 cards");
        System.out.println("------------------------------------------------------------------------");
        System.out.println();

        System.out.println("About to START the game...");
        flushTerminal(10);
    }

    @Override
    public void displayDeck() {
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
        flushTerminal(5);
    }

    @Override
    public void flushTerminal(int timeout) {
        try {
            TimeUnit.SECONDS.sleep(timeout);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        // Clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void promptForGameLevel(int low, int high) {
        System.out.printf("Enter the game level (%d-%d): ", low, high);        
    }

    @Override
    public void promptForCardSelection(int cardNumber) {
        if (cardNumber == 1)
            System.out.print("Choose your first card (row col): ");
        else if (cardNumber == 2)
            System.out.print("Choose your second card (row col): ");
    }

    @Override
    public void promptToContinue() {
        System.out.print("Do wish to continue (Y/N)? ");
    }

    @Override
    public void displayInvalidCardSelection() {
        System.out.println("## No card available at that position ##");
    }

    @Override
    public void displayMatchedMessage() {
        System.out.println("## It's a MATCH! You keep two more cards ##");
    }

    @Override
    public void displayNotMatchedMessage() {
        System.out.println("## It's NOT a MATCH! The cards are kept back ##");
    }

    @Override
    public void displayGaveUpMessage() {
        System.out.println("## GAME OVER! Better luck next time! ##");
        System.out.println();
    }

    @Override
    public void displayFinishedMessage() {
        System.out.println("## CONGRATS! All pairs are matched ##");
        System.out.println();
    }

    @Override
    public void displayResults() {
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
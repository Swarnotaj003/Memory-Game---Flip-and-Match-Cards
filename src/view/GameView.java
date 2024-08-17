package view;

import java.util.concurrent.TimeUnit;
import model.GameModel;
import model.Deck;
import model.Player;

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
        int maxScore = -1;
        Player winner = null;
        System.out.println("******************************************");
        System.out.printf("%25s\n", "RESULTS");
        System.out.println("******************************************");

        System.out.printf("%20s : %5s\n", "PLAYER", "SCORE");
        for (Player player : this.gameModel.getPlayers()) {
            System.out.printf("%20s : %3d\n", player.getName(), player.getScore());
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                winner = player;
            }
        }
        System.out.println("------------------------------------------");
        System.out.println("## The WINNER is " + winner.getName() + " ##");
        System.out.println("------------------------------------------");
    }
}
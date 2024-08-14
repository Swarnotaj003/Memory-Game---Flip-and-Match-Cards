package view;

import model.*;

public class GameView 
{
    GameModel gameModel;

    public GameView (GameModel gameModel) {
        this.gameModel = gameModel;
    }

    public void showInstructions() {
        new Instructions();
    }

    public void showDeck() {
        this.gameModel.getDeck().showDeck();
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
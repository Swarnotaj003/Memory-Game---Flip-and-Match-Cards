package controller;

import java.util.Scanner;
import model.GameModel;
import view.GameView;
import model.Deck;

public class GameController 
{
    private GameModel gameModel;
    private GameView gameView;
    private GameState gameState;
    private Scanner sc;

    public GameController () {
        this.sc = new Scanner(System.in);
        this.gameView = new GameView(null);
        
        displayInstructions();
        setGame();
        playGame();
        this.sc.close();
    }

    public void displayInstructions() {
        this.gameView.showInstructions();
    }

    public void setGame() {
        System.out.print("Enter the game level (1-3): ");
        int gameLevel = sc.nextInt();
        while (gameLevel < 1 || gameLevel > 3) {
            System.out.println("## Enter a valid game level ##");
            System.out.print("Enter the game level (1-3): ");
            gameLevel = sc.nextInt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();

        this.gameModel = new GameModel(new Deck(gameLevel+1));
        this.gameView = new GameView(this.gameModel);
    }

    public void playGame() {
        this.gameState = GameState.SHUFFLING;
        Deck deck = this.gameModel.getDeck();
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        boolean continuePlay = true, picked;
        char tryAgain = 'y';

        while (continuePlay) {
            switch (this.gameState) {
                case SHUFFLING:
                    System.out.println("Shuffling your deck...");
                    deck.shuffle();
                    this.gameView.showDeck();
                    this.gameState = GameState.SELECT_FIRST_CARD;
                    break;

                case SELECT_FIRST_CARD:
                    picked = false;
                    while (!picked) {
                        System.out.print("Choose your first card (row col): ");
                        row1 = sc.nextInt();
                        col1 = sc.nextInt();
                        if (validateInput(row1, col1) && deck.isCardAvailable(row1, col1)) {
                            deck.getCard(row1, col1).flip();
                            picked = true;
                            this.gameState = GameState.SELECT_SECOND_CARD;
                        }
                        else {
                            System.out.println("## No card available at that position ##");
                        }
                    }
                    break;

                case SELECT_SECOND_CARD:
                    picked = false;
                    while (!picked) {
                        System.out.print("Choose your second card (row col): ");
                        row2 = sc.nextInt();
                        col2 = sc.nextInt();
                        if (validateInput(row2, col2) && deck.isCardAvailable(row2, col2) && !(row1 == row2 && col1 == col2)) {
                            deck.getCard(row2, col2).flip();
                            picked = true;
                            this.gameState = GameState.SHOWING_DECK;
                        }
                        else {
                            System.out.println("## No card available at that position ##");
                        }
                    }
                    break;

                case SHOWING_DECK:
                    System.out.println(); 
                    this.gameView.showDeck();
                    System.out.println();
                    this.gameState = GameState.CHECK_FOR_MATCH;
                    break;

                case CHECK_FOR_MATCH:
                    if (deck.getCard(row1, col1).hasMatched(deck.getCard(row2, col2))) {
                        System.out.println("## It's a MATCH! You keep two more cards ##");
                        deck.withdrawCard(row1, col1);
                        deck.withdrawCard(row2, col2);
                    }   
                    else {
                        System.out.println("## It's NOT a MATCH! The cards are kept back ##");
                        deck.getCard(row1, col1).flip();
                        deck.getCard(row2, col2).flip();
                    }
                    System.out.println();
                    gameModel.incrementAttempts();
                    if (hasFinishedMatching()) {
                        this.gameState = GameState.FINISHED;
                    }
                    else {
                        System.out.print("Do wish to continue (Y/N)? ");
                        sc.nextLine();
                        tryAgain = sc.nextLine().charAt(0);
                        System.out.println();
                        if (tryAgain == 'N' || tryAgain == 'n') {
                            this.gameState = GameState.GAVE_UP;
                        }
                        else {
                            this.gameState = GameState.SELECT_FIRST_CARD;
                        }
                    }
                    break;

                case GAVE_UP:
                    System.out.println("## GAME OVER! Better luck next time! ##");
                    continuePlay = false;
                    break;

                case FINISHED:
                    System.out.println("## CONGRATS! All pairs are matched ##");
                    System.out.println();
                    displayResults();
                    continuePlay = false;
                    break;
            }
        }        
    }

    public boolean hasFinishedMatching() {
        return this.gameModel.getDeck().getNumberOfCards() == 2;
    }

    public boolean validateInput(int row, int col) {
        int n = this.gameModel.getDeck().getMatrixDimension();
        return (row >= 0 && row < n && col >= 0 && col < n);
    }

    public void displayResults() {
        this.gameView.showResults();
    }
}
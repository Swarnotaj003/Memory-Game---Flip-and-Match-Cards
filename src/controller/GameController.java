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
        displayResults();
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
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        Deck deck = this.gameModel.getDeck();

        System.out.println("Shuffling your deck...");
        deck.shuffle();
        this.gameView.showDeck();

        while (!gameOver()) {           
            boolean picked = false;
            char tryAgain = 'Y';
            
            while (!picked) {
                System.out.print("Choose your first card (row col): ");
                row1 = sc.nextInt();
                col1 = sc.nextInt();
                if (validateInput(row1, col1) && deck.isCardAvailable(row1, col1)) {
                    deck.getCard(row1, col1).flip();
                    picked = true;
                }
                else {
                    System.out.println("## No card available at that position ##");
                }
            }
            picked = false;
            while (!picked) {
                System.out.print("Choose your second card (row col): ");
                row2 = sc.nextInt();
                col2 = sc.nextInt();
                if (validateInput(row2, col2) && deck.isCardAvailable(row2, col2)) {
                    deck.getCard(row2, col2).flip();
                    picked = true;
                }
                else {
                    System.out.println("## No card available at that position ##");
                }
            }

            System.out.println(); 
            this.gameView.showDeck();
            System.out.println(); 

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
            System.out.print("Do wish to continue (Y/N)? ");
            sc.nextLine();
            tryAgain = sc.nextLine().charAt(0);
            System.out.println();
            if (tryAgain == 'N' || tryAgain == 'n') {
                this.gameState = GameState.GAVE_UP;
                System.out.println("## GAME OVER! Better luck next time! ##");
                break;
            }
        }   
        System.out.println();
        if (this.gameState != GameState.GAVE_UP) {
            this.gameState = GameState.FINSIHED;
            System.out.println("## CONGRATS! All pairs are matched ##");
            System.out.println();
        }
        this.sc.close();
    }

    public boolean gameOver() {
        return this.gameModel.getDeck().getNumberOfCards() == 2;
    }

    public boolean validateInput(int row, int col) {
        int n = this.gameModel.getDeck().getMatrixDimension();
        return (row >= 0 && row < n && col >= 0 && col < n);
    }

    public void displayResults() {
        if (this.gameState == GameState.FINSIHED)
            this.gameView.showResults();
    }
}
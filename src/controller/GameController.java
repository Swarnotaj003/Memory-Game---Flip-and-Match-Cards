package controller;

import java.util.Scanner;
import model.GameModel;
import view.GameView;
import model.Deck;
import model.Player;

public class GameController 
{
    private GameModel gameModel;
    private GameView gameView;
    private GameState gameState;
    private Scanner sc;

    public GameController () {
        this.sc = new Scanner(System.in);
        this.gameView = new GameView(null);
        this.gameState = GameState.INITIALIZING;
        
        switch (gameState) {
            case INITIALIZING:
                gameState = GameState.transition(gameState);
                displayInstructions();
            case INSTRUCTING:
                gameState = GameState.transition(gameState);
                setGame();
            case GAMEPLAY_SETTINGS:
                gameState = GameState.transition(gameState);
                playGame();
            case PLAYING:
                gameState = GameState.transition(gameState);
            case GAME_OVER:
                gameState = GameState.transition(gameState);
                displayResults();
            case SHOWING_RESULTS:
                sc.close();
        }
    }

    public void displayInstructions() {
        this.gameView.showInstructions();
    }

    public void setGame() {
        this.gameModel = new GameModel(setDeck(), setPlayers());
        this.gameView = new GameView(this.gameModel);
    }

    public Deck setDeck() {
        System.out.print("Enter the game level (1-3): ");
        int gameLevel = sc.nextInt();
        while (gameLevel < 1 || gameLevel > 3) {
            System.out.println("## Enter a valid game level ##");
            System.out.print("Enter the game level (1-3): ");
            gameLevel = sc.nextInt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return new Deck(gameLevel+1);
    }

    public Player[] setPlayers() {
        System.out.print("Enter the number of players (2-4): ");
        int numOfPlayers = sc.nextInt();
        while (numOfPlayers < 2 || numOfPlayers > 4) {
            System.out.println("## Enter the permissible no. of players ##");
            System.out.print("Enter the number of players (2-4): ");
            numOfPlayers = sc.nextInt();
        }
        sc.nextLine();

        System.out.println("## Enter the player details ##");
        Player[] players = new Player[numOfPlayers];
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("Name of player " + (i+1) + ": ");
            players[i] = new Player(sc.nextLine());
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return players;
    }

    public void playGame() {
        int turn = 0;
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        Deck deck = this.gameModel.getDeck();
        Player[] players = this.gameModel.getPlayers();

        System.out.println("Shuffling your deck...");
        deck.shuffle();
        this.gameView.showDeck();

        while (!gameOver()) {
            System.out.println("TURN OF " + players[turn].getName());
            System.out.println("CURRENT SCORE : " + players[turn].getScore());
            
            boolean picked = false;
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
                System.out.println("## It's a MATCH! " + players[turn].getName() + " keeps two more cards ##");
                System.out.println();
                deck.withdrawCard(row1, col1);
                deck.withdrawCard(row2, col2);
                players[turn].updateScore();
            }   
            else {
                System.out.println("## It's NOT a MATCH! The cards are kept back ##");
                System.out.println();
                deck.getCard(row1, col1).flip();
                deck.getCard(row2, col2).flip();
            }
            turn = (turn + 1) % players.length;
        }   
        System.out.println();
        System.out.println("## Game Over! All cards are withdrawn ##");
        System.out.println();
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
        this.gameView.showResults();
    }
}
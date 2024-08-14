package control;

import java.util.Scanner;
import model.*;
import view.*;

public class GameControl 
{
    GameModel gameModel;
    GameView gameView;
    Scanner sc;

    public GameControl () {
        this.sc = new Scanner(System.in);
        displayInstructions();
        setGame();
        playGame();
        displayResults();
        sc.close();
    }

    public void displayInstructions() {
        this.gameView.showInstructions();
    }

    public void setGame() {
        this.gameModel = new GameModel(setDeck(), setPlayers());
        this.gameView = new GameView(this.gameModel);
    }

    public Deck setDeck() {
        System.out.print("Enter the game level : ");
        int gameLevel = sc.nextInt();
        while (gameLevel < 1 || gameLevel > 3) {
            System.out.println("## Enter a valid game level (1-3) ##");
            System.out.print("Enter the game level : ");
            gameLevel = sc.nextInt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return new Deck(gameLevel+1);
    }

    public Player[] setPlayers() {
        System.out.print("Enter the number of players : ");
        int numOfPlayers = sc.nextInt();
        while (numOfPlayers < 2 || numOfPlayers > 4) {
            System.out.println("## Enter the permissible no. of players (2-4) ##");
            System.out.print("Enter the number of players : ");
            numOfPlayers = sc.nextInt();
        }

        System.out.println("## Enter the player details ##");
        Player[] players = this.gameModel.getPlayers();
        for (int i = 0; i < players.length; i++) {
            System.out.print("Name of player " + (i+1) + ": ");
            players[i] = new Player(sc.nextLine());
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        return new Player[numOfPlayers];
    }

    public void playGame() {
        int turn = 0;
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        Deck deck = this.gameModel.getDeck();
        Player[] players = this.gameModel.getPlayers();

        System.out.println("Shuffling your deck...");
        deck.shuffle();
        deck.showDeck();

        while (!gameOver()) {
            System.out.println("TURN OF " + players[turn].getName());
            System.out.println("CURRENT SCORE : " + players[turn].getScore());
            
            boolean picked = false;
            while (!picked) {
                System.out.print("Choose your first card : ");
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
                System.out.print("Choose your second card : ");
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
            deck.showDeck();
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
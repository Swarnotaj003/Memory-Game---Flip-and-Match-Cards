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

    public GameController() {
        this.sc = new Scanner(System.in);
        this.gameView = new GameView(null);
        
        this.gameView.displayInstructions();
        setGame();
        playGame();
        this.sc.close();
    }

    public void setGame() {
        this.gameView.promptForGameLevel(1, 3);
        int gameLevel = sc.nextInt();
        while (gameLevel < 1 || gameLevel > 3) {
            this.gameView.promptForGameLevel(1, 3);
            gameLevel = sc.nextInt();
        }
        this.gameView.flushTerminal(0);
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
                    deck.shuffle();
                    this.gameView.displayDeck();
                    this.gameState = GameState.SELECT_FIRST_CARD;
                    break;

                case SELECT_FIRST_CARD:
                    picked = false;
                    while (!picked) {
                        this.gameView.promptForCardSelection(1);
                        row1 = sc.nextInt();
                        col1 = sc.nextInt();
                        if (validateInput(row1, col1) && deck.isCardAvailable(row1, col1)) {
                            deck.getCard(row1, col1).flip();
                            picked = true;
                            this.gameState = GameState.SELECT_SECOND_CARD;
                        }
                        else {
                            this.gameView.displayInvalidCardSelection();
                        }
                    }
                    break;

                case SELECT_SECOND_CARD:
                    picked = false;
                    while (!picked) {
                        this.gameView.promptForCardSelection(2);
                        row2 = sc.nextInt();
                        col2 = sc.nextInt();
                        if (validateInput(row2, col2) && deck.isCardAvailable(row2, col2) && !(row1 == row2 && col1 == col2)) {
                            deck.getCard(row2, col2).flip();
                            picked = true;
                            this.gameState = GameState.SHOWING_DECK;
                        }
                        else {
                            this.gameView.displayInvalidCardSelection();
                        }
                    }
                    break;

                case SHOWING_DECK:
                    System.out.println(); 
                    this.gameView.displayDeck();
                    System.out.println();
                    this.gameState = GameState.CHECK_FOR_MATCH;
                    break;

                case CHECK_FOR_MATCH:
                    if (deck.getCard(row1, col1).hasMatched(deck.getCard(row2, col2))) {
                        this.gameView.displayMatchedMessage();
                        deck.withdrawCard(row1, col1);
                        deck.withdrawCard(row2, col2);
                    }   
                    else {
                        this.gameView.displayNotMatchedMessage();
                        deck.getCard(row1, col1).flip();
                        deck.getCard(row2, col2).flip();
                    }
                    System.out.println();
                    gameModel.incrementAttempts();
                    if (hasFinishedMatching()) {
                        this.gameState = GameState.FINISHED;
                    }
                    else {
                        this.gameView.promptToContinue();
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
                    this.gameView.displayGaveUpMessage();
                    continuePlay = false;
                    break;

                case FINISHED:
                    this.gameView.displayFinishedMessage();
                    this.gameView.displayResults();
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
}
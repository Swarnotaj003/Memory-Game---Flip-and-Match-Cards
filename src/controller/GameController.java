package controller;

import model.Deck;
import model.GameModel;
import view.GameView;
import view.GameViewCLI;

public class GameController 
{
    private static final int LOW = 1;
    private static final int HIGH = 3;
    private GameModel gameModel;
    private GameView gameView;
    private UserInput userinput;
    private GameState gameState;

    public GameController() {
        this.gameView = new GameViewCLI(null);
        this.userinput = new UserInputCLI();
        this.gameView.displayWelcome();
        int gameLevel = this.userinput.gameLevelInput(LOW, HIGH);
        this.gameModel = new GameModel(new Deck(gameLevel+1));
        this.gameView = new GameViewCLI(this.gameModel);
    }

    private boolean hasFinishedMatching() {
        return this.gameModel.getDeck().getNumberOfCards() == 2;
    }

    private boolean validateInput(int row, int col) {
        int n = this.gameModel.getDeck().getMatrixDimension();
        return (row >= 0 && row < n && col >= 0 && col < n);
    }

    public void playGame() {
        this.gameState = GameState.SHUFFLING;
        Deck deck = this.gameModel.getDeck();
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
        boolean continuePlay = true, picked;

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
                        int address[] = this.userinput.cardInput(true);
                        row1 = address[0];
                        col1 = address[1];
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
                        int address[] = this.userinput.cardInput(false);
                        row2 = address[0];
                        col2 = address[1];
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
                        if (this.userinput.wantToQuit()) 
                            this.gameState = GameState.GAVE_UP;
                        else 
                            this.gameState = GameState.SELECT_FIRST_CARD;
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
}
import java.util.Scanner;

public class Game 
{
    private int numOfPlayers;
    private int gameLevel;
    private Deck deck;
    private Player[] players;
    Scanner sc = new Scanner(System.in);
    
    public Game (int numOfPlayers, int gameLevel) {
        this.numOfPlayers = numOfPlayers;
        this.gameLevel = gameLevel;
        this.deck = new Deck(gameLevel + 1);
        this.players = new Player[numOfPlayers];
    }

    public boolean gameOver() {
        return deck.numOfCards == 2;
    }

    public void setPlayers() {
        System.out.println("## Enter the player details ##");
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("Name of player " + (i+1) + ": ");
            players[i] = new Player(sc.nextLine());
        }
        System.out.println();
    }

    public boolean validateInput(int row, int col) {
        return (row >= 0 && row <= 2*gameLevel+1 && col >= 0 && col <= 2*gameLevel+1);
    }

    public void showResults() {
        int maxScore = -1;
        Player winner = null;
        System.out.println("******************************************");
        System.out.println("RESULTS");
        System.out.println("******************************************");

        System.out.printf("%20s : %5s\n", "PLAYER", "SCORE");
        for (Player player : players) {
            System.out.printf("%20s : %3d\n", player.getName(), player.getScore());
            if (player.getScore() > maxScore) {
                maxScore = player.getScore();
                winner = player;
            }
        }
        System.out.println("------------------------------------------");
        System.out.println("## The WINNER is " + winner.getName() + " ##");
        System.out.println("------------------------------------------");
        sc.close();
    }

    public void play() {
        int turn = 0;
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
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
                    deck.matrix[row1][col1].flip();
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
                    deck.matrix[row2][col2].flip();
                    picked = true;
                }
                else {
                    System.out.println("## No card available at that position ##");
                }
            }

            System.out.println(); 
            deck.showDeck();
            System.out.println(); 


            if (deck.matrix[row1][col1].hasMatched(deck.matrix[row2][col2])) {
                System.out.println("## It's a MATCH! " + players[turn].getName() + " keeps two more cards ##");
                System.out.println();
                deck.drawCard(row1, col1);
                deck.drawCard(row2, col2);
                players[turn].updateScore();
            }   
            else {
                System.out.println("## It's NOT a MATCH! The cards are kept back ##");
                System.out.println();
                deck.matrix[row1][col1].flip();
                deck.matrix[row2][col2].flip();
            }
            turn = (turn + 1) % numOfPlayers;
        }   
        System.out.println();
        System.out.println("## Game Over! All cards are withdrawn ##");
        System.out.println();
    }
}
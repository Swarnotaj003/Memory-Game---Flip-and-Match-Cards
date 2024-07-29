import java.util.Scanner;
import java.util.ArrayList;

public class Game 
{
    private int numOfPlayers;
    @SuppressWarnings("unused")
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
        System.out.println("Enter the player details");
        for (int i = 0; i < numOfPlayers; i++) {
            System.out.print("Name of player " + (i+1) + ": ");
            players[i] = new Player(sc.nextLine());
        }
    }

    public void showResults() {
        int maxScore = -1;
        ArrayList<Player> winners = new ArrayList<>();
        System.out.println("\nRESULTS");
        for (Player player : players) {
            System.out.printf("%20s : %3d\n", player.getName(), player.getScore());
            if (player.getScore() >= maxScore) {
                maxScore = player.getScore();
                winners.add(player);
            }
        }
        if (winners.size() == 1)
            System.out.println("## The WINNER is " + winners.get(0).getName() + " ##");
        else {
            System.out.print("## The WINNERS are ");
            for (Player winner : winners)
                System.out.print(winner.getName() + " ");
            System.out.println("##");
        }
        sc.close();
    }

    public void play() {
        int turn = 0;
        int row1 = 0, col1 = 0, row2 = 0, col2 = 0;
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
                if (deck.isCardAvailable(row1, col1)) {
                    deck.matrix[row1][col1].flip();
                    picked = true;
                }
                else {
                    System.out.println("## No card available at that position");
                }
            }

            picked = false;
            while (!picked) {
                System.out.print("Choose your second card : ");
                row2 = sc.nextInt();
                col2 = sc.nextInt();
                if (deck.isCardAvailable(row2, col2)) {
                    deck.matrix[row2][col2].flip();
                    picked = true;
                }
                else {
                    System.out.println("## No card available at that position");
                }
            }

            System.out.println(); 
            deck.showDeck();
            System.out.println(); 


            if (deck.matrix[row1][col1].hasMatched(deck.matrix[row2][col2])) {
                System.out.println("## It's a MATCH! " + players[turn].getName() + " keeps two more cards ##");
                deck.drawCard(row1, col1);
                deck.drawCard(row2, col2);
                players[turn].updateScore();
            }   
            else {
                System.out.println("## It's NOT a MATCH! The cards are kept back");
                deck.matrix[row1][col1].flip();
                deck.matrix[row2][col2].flip();
            }
            turn = (turn + 1) % numOfPlayers;
        }   
        System.out.println();
        System.out.println("## Game Over! All cards are withdrawn ##");
    }
}
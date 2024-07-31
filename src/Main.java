import java.util.Scanner;

public class Main 
{
    public static void main (String[] args) 
    {
        Scanner sc = new Scanner(System.in);
        new Instructions();
        System.out.print("Press ENTER to continue...");
        sc.nextLine();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        // Modify to handle Input violations 
        System.out.print("Enter the game level : ");
        int gameLevel = sc.nextInt();
        System.out.print("Enter the number of players : ");
        int numOfPlayers = sc.nextInt();

        Game game = new Game(numOfPlayers, gameLevel);
        game.setPlayers();
        game.play();
        game.showResults();
        
        sc.close();
    }
}
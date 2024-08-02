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

        System.out.print("Enter the game level : ");
        int gameLevel = sc.nextInt();
        while (gameLevel < 1 || gameLevel > 3) {
            System.out.println("## Enter a valid game level (1-3) ##");
            System.out.print("Enter the game level : ");
            gameLevel = sc.nextInt();
        }

        System.out.print("Enter the number of players : ");
        int numOfPlayers = sc.nextInt();
        while (numOfPlayers < 2 || numOfPlayers > 4) {
            System.out.println("## Enter the permissible no. of players (2-4) ##");
            System.out.print("Enter the number of players : ");
            numOfPlayers = sc.nextInt();
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();

        Game game = new Game(numOfPlayers, gameLevel);
        game.setPlayers();
        game.play();
        game.showResults();
        
        sc.close();
    }
}
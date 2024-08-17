package view;

import java.util.concurrent.TimeUnit;

public class Instructions
{
    {
        System.out.println("************************************************************************");
        System.out.printf("%45s","MEMORY MATCH GAME\n");
        System.out.println("************************************************************************");
        System.out.println("Test your memory and concentration");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("GAME OVERVIEW");
        System.out.println("- A deck of shuffled cards is presented, all facing down");
        System.out.println("- Each player selects two cards to flip over, revealing their faces");
        System.out.println("- If the two cards match, the player keeps the cards and scores a point");
        System.out.println("- Else, they are turned back, and the next player tries");
        System.out.println("- The game continues till the last pair of cards reamins to be flipped");
        System.out.println("- The player matching the most pairs wins the game");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("GAMEPLAY LEVELS");
        System.out.println("\t1 : Flip & match among 4x4 cards");
        System.out.println("\t2 : Flip & match among 6x6 cards");
        System.out.println("\t3 : Flip & match among 8x8 cards");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("NUMBER OF PLAYERS");
        System.out.println("\tCan be played among 2 to 4 players");
        System.out.println("------------------------------------------------------------------------");
        System.out.println();

        System.out.print("About to START the game...");
        try {
            TimeUnit.SECONDS.sleep(10);
        }   catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
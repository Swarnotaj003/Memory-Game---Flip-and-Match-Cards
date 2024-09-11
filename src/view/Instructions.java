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
        System.out.println("- Set the gameplay by entering your preferred `game level`");
        System.out.println("- Enter the addresses of two cards to flip over");
        System.out.println("- If you find a match, you will keep the cards");
        System.out.println("- If you don't find a match, the cards will flipped back");
        System.out.println("- Matched or not don't worry, carry on remembering & flipping");
        System.out.println("- The play continues until the last pair remains to be matched");
        System.out.println("- Lesser the number of attempts, better the score");
        System.out.println("------------------------------------------------------------------------");

        System.out.println("GAMEPLAY LEVELS");
        System.out.println("\t1 : Flip & match among 4x4 cards");
        System.out.println("\t2 : Flip & match among 6x6 cards");
        System.out.println("\t3 : Flip & match among 8x8 cards");
        System.out.println("------------------------------------------------------------------------");
        System.out.println();

        System.out.println("About to START the game...");
        System.out.println();

        try {
            TimeUnit.SECONDS.sleep(10);
        }   catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // Clear the console
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
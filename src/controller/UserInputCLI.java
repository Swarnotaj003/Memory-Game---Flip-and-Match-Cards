package controller;

import java.util.Scanner;

public class UserInputCLI implements UserInput {
    private Scanner sc = new Scanner(System.in);

    @Override
    public int gameLevelInput(int low, int high) {
        System.out.printf("Enter the game level (%d-%d): ", low, high); 
        int level = sc.nextInt();
        while (level < low || level > high) {
            System.out.printf("## Enter a valid game level (%d-%d): ", low, high);
            level = sc.nextInt();
        }
        return level;
    }

    @Override
    public int[] cardInput(boolean firstCard) {
        int address[] = new int[2];
        if (firstCard) 
            System.out.print("Choose your first card (row col): ");
        else
            System.out.print("Choose your second card (row col): ");
        address[0] = sc.nextInt();
        address[1] = sc.nextInt();
        return address;
    }

    @Override
    public boolean wantToQuit() {
        System.out.print("Do you wish to continue? (Y/N) ");
        sc.nextLine();
        char ch = sc.nextLine().charAt(0);
        return (ch == 'n' || ch == 'N');
    }
}
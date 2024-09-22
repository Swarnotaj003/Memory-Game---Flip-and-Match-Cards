package controller;

public interface UserInput {
    public int gameLevelInput(int low, int high);
    public int[] cardInput(boolean firstCard);
    public boolean wantToQuit();
}
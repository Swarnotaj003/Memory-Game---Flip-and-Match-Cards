package view;

public interface GameView {
    public void displayWelcome();
    public void displayDeck();
    public void displayInvalidCardSelection();
    public void displayMatchedMessage();
    public void displayNotMatchedMessage();
    public void displayGaveUpMessage();
    public void displayFinishedMessage();
    public void displayResults();
}
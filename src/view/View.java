package view;

public interface View {
    public void displayInstructions();
    public void displayDeck();
    public void flushTerminal(int timeout);
    public void promptForGameLevel(int low, int high);
    public void promptForCardSelection(int cardNumber);
    public void displayInvalidCardSelection();
    public void displayMatchedMessage();
    public void displayNotMatchedMessage();
    public void displayGaveUpMessage();
    public void displayFinishedMessage();    
    public void promptToContinue();
    public void displayResults();
}
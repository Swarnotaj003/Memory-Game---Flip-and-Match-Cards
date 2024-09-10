package model;

public class GameModel 
{
    private Deck deck;
    private int attempts;

    public GameModel (Deck deck) {
        this.deck = deck;
        this.attempts = 0;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incrementAttempts() {
        attempts++;
    }
}
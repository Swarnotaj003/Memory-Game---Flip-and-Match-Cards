package model;

public class GameModel 
{
    private Deck deck;
    private Player[] players;

    public GameModel (Deck deck, Player[] players) {
        this.deck = deck; 
        this.players = players;
    }

    public Deck getDeck() {
        return this.deck;
    }

    public Player[] getPlayers() {
        return this.players;
    }
}
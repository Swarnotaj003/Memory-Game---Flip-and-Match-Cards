package model;

public class Card 
{
    private char face;
    private boolean faceUp;

    public Card(char symbol) {
        this.face = symbol;
        this.faceUp = false;
    }

    public char showCardFace() {
        return this.face;
    }

    public boolean isFaceUp() {
        return this.faceUp;
    }

    public void flip() {
        faceUp = !faceUp;
    }

    public boolean hasMatched (Card card) {
        return this.face == card.face;
    }
}
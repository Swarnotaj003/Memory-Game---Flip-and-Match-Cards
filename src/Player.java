public class Player 
{
    private String playerName;
    private int score;
    private static int count;

    public Player (String playerName) {
        count++;
        if (playerName == null || playerName == "")
            this.playerName = "Player-" + count;
        else
            this.playerName = playerName;
        this.score = 0;
    }

    public String getName() {
        return this.playerName;
    }

    public int getScore() {
        return this.score;
    }

    public void updateScore () {
        score++;
    }
}
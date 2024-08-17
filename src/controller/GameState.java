package controller;

public enum GameState {
    INITIALIZING,
    INSTRUCTING,
    GAMEPLAY_SETTINGS,
    PLAYING,
    GAME_OVER,
    SHOWING_RESULTS;

    public static GameState transition (GameState state) {
        switch (state) {
            case INITIALIZING:
                return INSTRUCTING;
            case INSTRUCTING:
                return GAMEPLAY_SETTINGS;
            case GAMEPLAY_SETTINGS:
                return PLAYING;
            case PLAYING:
                return GAME_OVER;
            case GAME_OVER:
                return SHOWING_RESULTS;
            case SHOWING_RESULTS:
                break;         
        }
        return state;
    }
}
package fr.fistin.fistinframework.game;

public interface Game
{
    void start();
    void stop();
    int maxPlayers();
    int minPlayers();
    int players();
    int timerBeforeGameStart();

    <G extends Game> G cast();
}

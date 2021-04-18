package fr.fistin.fistinframework.game;

public interface Game
{
    void start();
    void stop();
    int maxPlayers();
    int minPlayers();
    int players();
}

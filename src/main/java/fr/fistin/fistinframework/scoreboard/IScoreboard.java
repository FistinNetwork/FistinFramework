package fr.fistin.fistinframework.scoreboard;

public interface IScoreboard
{
    IScoreboardSign getScoreboardSign();
    void setScoreboardSign(IScoreboardSign scoreboardSign);
    void createScoreboard();
    void updateScoreboard();
    void destroy();
}

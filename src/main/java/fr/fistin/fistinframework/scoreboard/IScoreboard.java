package fr.fistin.fistinframework.scoreboard;

public interface IScoreboard
{
    IScoreboardSign getScoreboardSign();
    void setScoreboardSign(IScoreboardSign scoreboardSign);
    void updateScoreboard();

    default void createScoreboard()
    {
        this.getScoreboardSign().create();
    }
    default void destroy()
    {
        this.getScoreboardSign().destroy();
    }
}

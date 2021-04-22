package fr.fistin.fistinframework.scoreboard;

public abstract class DefaultScoreboard implements IScoreboard
{
    protected IScoreboardSign scoreboardSign;

    public DefaultScoreboard(IScoreboardSign scoreboardSign)
    {
        this.scoreboardSign = scoreboardSign;
    }

    @Override
    public IScoreboardSign getScoreboardSign()
    {
        return this.scoreboardSign;
    }

    @Override
    public void setScoreboardSign(IScoreboardSign scoreboardSign)
    {
        this.scoreboardSign = scoreboardSign;
    }
}

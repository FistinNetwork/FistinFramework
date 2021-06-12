package fr.fistin.fistinframework.scoreboard;

/**
 * Represent the default implementation of {@link IScoreboard}.
 *
 * @see IScoreboard
 * @see ScoreboardBuilder
 * @see IScoreboardSign
 */
public abstract class DefaultScoreboard implements IScoreboard
{
    protected IScoreboardSign scoreboardSign;

    /**
     * Construct a new {@link DefaultScoreboard}.
     * @param scoreboardSign the {@link IScoreboardSign} to wrap.
     */
    public DefaultScoreboard(IScoreboardSign scoreboardSign)
    {
        this.scoreboardSign = scoreboardSign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IScoreboardSign getScoreboardSign()
    {
        return this.scoreboardSign;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScoreboardSign(IScoreboardSign scoreboardSign)
    {
        this.scoreboardSign = scoreboardSign;
    }
}

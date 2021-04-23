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
     * Get the wrapped scoreboard sign.
     * @return the wrapper scoreboard sign.
     */
    @Override
    public IScoreboardSign getScoreboardSign()
    {
        return this.scoreboardSign;
    }

    /**
     * Define the {@link IScoreboardSign} of the wrapper.
     * @param scoreboardSign the new {@link IScoreboardSign}
     */
    @Override
    public void setScoreboardSign(IScoreboardSign scoreboardSign)
    {
        this.scoreboardSign = scoreboardSign;
    }
}

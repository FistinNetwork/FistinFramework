package fr.fistin.fistinframework.scoreboard;

/**
 * IScoreboardSign wrapper.
 * Represent a (high-level) Scoreboard in FistinFramework.
 *
 * @see IScoreboardSign
 * @see ScoreboardBuilder
 */
public interface IScoreboard
{
    /**
     * Get the wrapped scoreboard sign.
     * @return the wrapper scoreboard sign.
     */
    IScoreboardSign getScoreboardSign();

    /**
     * Define the {@link IScoreboardSign} of the wrapper.
     * @param scoreboardSign the new {@link IScoreboardSign}
     */
    void setScoreboardSign(IScoreboardSign scoreboardSign);

    /**
     * Update the scoreboard here!
     */
    void updateScoreboard();

    /**
     * Default actions on scoreboard creation.
     */
    default void createScoreboard()
    {
        this.getScoreboardSign().create();
        this.updateScoreboard();
    }

    /**
     * Destroy scoreboard.
     */
    default void destroy()
    {
        this.getScoreboardSign().destroy();
    }
}

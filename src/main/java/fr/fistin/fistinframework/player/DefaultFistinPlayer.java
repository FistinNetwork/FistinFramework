package fr.fistin.fistinframework.player;

import fr.fistin.fistinframework.IFistinFramework;
import fr.fistin.fistinframework.configuration.Language;
import fr.fistin.fistinframework.event.PlayerStateChangedEvent;
import fr.fistin.fistinframework.scoreboard.IScoreboard;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;

public abstract class DefaultFistinPlayer implements FistinPlayer
{
    protected Player player;
    protected PlayerState playerState;
    protected IScoreboard scoreboard;
    protected Preferences preferences;

    public DefaultFistinPlayer(Player player)
    {
        this.player = player;
        this.preferences = new Preferences("fr"); // TODO: retrieve preferences from DB.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Player getPlayer()
    {
        return this.player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayer(@NotNull Player player)
    {
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PlayerState playerState()
    {
        return this.playerState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPlayerState(@NotNull PlayerState playerState)
    {
        IFistinFramework.framework().fistinEventBus().handleEvent(() -> new PlayerStateChangedEvent(this.playerState, this.playerState = playerState, this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePlayerState(@NotNull String newState)
    {
        this.setPlayerState(this.gameManager().getPlayerState(newState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changePlayerState(int newState)
    {
        this.setPlayerState(this.gameManager().getPlayerState(newState));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull IScoreboard scoreboard()
    {
        return this.scoreboard;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScoreboard(@NotNull IScoreboard scoreboard)
    {
        this.scoreboard = scoreboard;
        this.scoreboard.createScoreboard();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Language getSelectedLanguage()
    {
        return IFistinFramework.framework().languageManager().getLanguage(IFistinFramework.framework(), Locale.forLanguageTag(this.preferences().getLocale()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName()
    {
        return this.player.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UUID getUniqueId()
    {
        return this.player.getUniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Preferences preferences()
    {
        return this.preferences;
    }
}

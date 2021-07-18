package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.player.FistinPlayer;

public interface Messages
{
    String fixColor(String toFix);

    String getPlayerJoinMessage(Language language, FistinPlayer fistinPlayer, Game game);
    String getStartingGameMessage(Language language);
    String getStoppingGameMessage(Language language);
    String getWinnerMessage(Language language, FistinPlayer fistinPlayer);
    String getKickMessageAtEnd(Language language);
    String getPlayerRequiredMessage(Language language);
    String getMissingPermissionMessage(Language language);
}

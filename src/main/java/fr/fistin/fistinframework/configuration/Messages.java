package fr.fistin.fistinframework.configuration;

import fr.fistin.fistinframework.command.FistinCommand;
import fr.fistin.fistinframework.game.Game;
import fr.fistin.fistinframework.player.FistinPlayer;

public interface Messages
{
    String fixColor(String toFix);

    String getPlayerJoin(Language language, FistinPlayer fistinPlayer, Game game);
    String getStartingGame(Language language);
    String getStoppingGame(Language language);
    String getWinner(Language language, FistinPlayer fistinPlayer);
    String getKickMessageAtEnd(Language language);
    String getPlayerSpecifiedNotFound(Language language);
    String getInvalidObjectName(Language language);
    String getCommandPlayerRequired(Language language);
    String getCommandMissingPermission(Language language);
    String getCommandBadUsage(Language language, FistinCommand command);
    String getCommandError(Language language, FistinCommand command);
}

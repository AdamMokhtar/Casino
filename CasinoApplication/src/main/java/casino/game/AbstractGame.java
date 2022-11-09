package casino.game;


import gamblingauthoritiy.BettingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import gamblingauthoritiy.IBetTokenAuthority;

/**
 * @author ciara
 */
public abstract class AbstractGame implements IGame{
    private IBetLoggingAuthority betLoggingAuthority;
    private IBetTokenAuthority betTokenAuthority;
    private IGameRule gameRule;

 // define only the constructor here

    /**
     * creates an object of type AbstractGame with an IBetLoggingAuthority and IBetTokenAuthority and IGameRule
     *
     * @should createAnAbstractGameWithPassedIBetLoggingAuthorityAndIBetTokenAuthorityAndIGameRule
     * @should throwIllegalArgumentExceptionWithNullIBetLoggingAuthorityOrIBetTokenAuthorityOrIGameRule
     *
     * @param betLoggingAuthority is the AbstractGame's IBetLoggingAuthority
     * @param betTokenAuthority is the AbstractGame's IBetTokenAuthority
     * @param gameRule is the AbstractGame's IGameRule
     * @throws IllegalArgumentException if IBetLoggingAuthority or IBetTokenAuthority or IGameRule is null
     */
    public AbstractGame(IBetLoggingAuthority betLoggingAuthority, IBetTokenAuthority betTokenAuthority,
                        IGameRule gameRule) throws IllegalArgumentException {
        if (betLoggingAuthority != null) {
            if (betTokenAuthority != null) {
                if (gameRule != null) {
                    this.betLoggingAuthority = betLoggingAuthority;
                    this.betTokenAuthority = betTokenAuthority;
                    this.gameRule = gameRule;
                } else {
                    throw new IllegalArgumentException("IGameRule cannot be null.");
                }
            } else {
                throw new IllegalArgumentException("IBetTokenAuthority cannot be null.");
            }
        } else {
            throw new IllegalArgumentException("IBetLoggingAuthority cannot be null.");
        }

    }

    // BELOW ARE GETTERS CREATED WITH INTELLIJ SO THEY DO NOT NEED TO BE TESTED


    /**
     *
     * @return this AbstractGame's GameRule
     */
    public IBetLoggingAuthority getBetLoggingAuthority() {
        return betLoggingAuthority;
    }

    /**
     *
     * @return this AbstractGame's BetTokenAuthority
     */
    public IBetTokenAuthority getBetTokenAuthority() {
        return betTokenAuthority;
    }

    /**
     *
     * @return this AbstractGame's GameRule
     */
    public IGameRule getGameRule() {
        return gameRule;
    }
}

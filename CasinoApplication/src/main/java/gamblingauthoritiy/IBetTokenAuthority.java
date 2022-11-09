package gamblingauthoritiy;

import casino.idfactory.BettingRoundID;

public interface IBetTokenAuthority {

    /**
     * this method creates a betting token based on the given
     * BettingRoundID, which was created by the casino.
     *
     * This method must be called when creating a new bettinground.
     *
     * @param bettingRoundID
     * @return BetToken to be used by the casino.
     */
    BetToken getBetToken(BettingRoundID bettingRoundID);


    /**
     * method returns a true random integer.
     *
     * This method must be called and used
     * when determining the winner of a bettinground
     *
     * @param betToken betToken of the betting round for which the
     *                 random number is needed.
     * @return true random integer
     */
    Integer getRandomInteger(BetToken betToken);
}
/**
 * this method logs when a PlayerCard has been handed out to a Gambler.
 * <p>
 * it's used for logging purposes by the betlogging authority.
 * <p>
 *          this method MUST be used when a gambling card is handed out by the cashier.
 * </p>
 */

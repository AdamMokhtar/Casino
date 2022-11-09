package casino.game;

import casino.idfactory.BettingRoundID;
import gamblingauthoritiy.BetToken;
import casino.bet.Bet;

import java.util.Set;
/**
 * a bettinground is responsible for keeping a collection of unique Bets.
 */
public interface IBettingRound {
    BettingRoundID getBettingRoundID();

    /**
     * add a bet to the current bettinground.
     *
     *
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @param bet
     * @return true if bet is made, otherwise folse
     * @throws IllegalArgumentException when Bet is null
     */
    boolean placeBet(Bet bet) throws IllegalArgumentException;

    /**
     *
     * @return set of all bets made in this betting round.
     */
    Set<Bet> getAllBetsMade();

    /**
     *
     * @return betToken from this betting round.
     */
    BetToken getBetToken();

    /**
     *
     * @return number of bets made in the betting round
     */
    int numberOFBetsMade();
}

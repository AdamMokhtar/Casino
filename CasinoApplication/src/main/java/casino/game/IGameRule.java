package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.cashier.InvalidAmountException;

import java.util.Set;

/**
 * interface for gamerules
 *
 * gamerules determine the amount of bets are maximum needed from BettingRounds
 *
 */
public interface IGameRule {

    /**
     * Determine the winner from a Set of Bets, using a given random win value;
     * @param randomWinValue
     * @param bets
     * @return Betresult, containing the result for the winning bet.
     * @throws NoBetsMadeException when no bets have been made yet.
     */
    BetResult determineWinner(Integer randomWinValue, Set<Bet> bets) throws NoBetsMadeException, InvalidAmountException;

    /**
     * return the maximum number of bets which are used in the calculation of the winner.
     *
     * @return
     */
    int getMaxBetsPerRound();

}

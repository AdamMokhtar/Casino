package gamblingauthoritiy;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.game.IBettingRound;
import casino.idfactory.BetID;
import casino.idfactory.BettingRoundID;
import casino.idfactory.GamingMachineID;
import casino.idfactory.GeneralID;

import java.util.Set;


public interface IBetLoggingAuthority {
    /**
     * this method logs when a PlayerCard has been handed out to a Gambler.
     * <p>
     * it's used for logging purposes by the betlogging authority.
     * <p>
     *          this method MUST be used when a gambling card is handed out by the cashier.
     * </p>
     */
    void logHandOutGamblingCard(GeneralID card);

    /**
     * this method logs the PlayerCard which is turned in by a Gambler.
     * All betID's on it are logged by the authority.
     * <p>
     * <p>
     * it's used for logging purposes by the betlogging authority.
     * <p>
     *          this method MUST be used when a gambling card is handed in at the cashier.
     * </p>
     */
    void logHandInGamblingCard(GeneralID card, Set<BetID> betsMade);



    /**
     * this method logs when a BettingRound starts. It should be called before any submitted bets are added to
     * a betting round.
     * <p>
     * it's used for logging purposes by the betlogging authority.
     * <p>
     *          this method MUST be used when a betting round is started.
     * </p>

     */
    void logStartBettingRound(IBettingRound startingBettingRound);

    /**
     * this method logs an accepted bet by a BettingRound. It should be called:
     * after startBettingRound is called,
     * before endBettingRound is called
     * <p>
     * <p>
     * it's used for logging purposes by the betlogging authority.
     * <p>
     *          this method MUST be used when an accepted bet is added to a betting round.
     * </p>

     */
    void logAddAcceptedBet(Bet acceptedBet, BettingRoundID bettingRoundID, GamingMachineID gamingMachineID);

    /**
     * this method logs when a BettingRound ends. It should be called after the winner of a betting round
     * has been decided. all information in the betting Round are logged by the authority.
     * <p>
     * it's used for logging purposes by the betlogging authority.
     * <p>
     *          this method MUST be used when a betting round has ended and the winner is determined.
     * </p>
     */
    void logEndBettingRound(IBettingRound endedBettingRound, BetResult result);
}

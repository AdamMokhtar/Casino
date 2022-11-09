package casino.game;

import casino.idfactory.BettingRoundID;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BettingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import casino.bet.Bet;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author ciara
 */
public class BettingRound implements IBettingRound {
    private BettingRoundID bettingRoundID;
    private BetToken betToken;
    private Set<Bet> allBetsMade;

    /**
     * Creates a BettingRound with a BetToken and BettingRoundID
     *
     * @should createABettingRoundWithBettingRoundIDAndBetTokenAndInitialiseBetsMade
     * @should throwIllegalArgumentExceptionWithNullBettingRoundIDOrBetToken
     *
     * @param betToken is the BettingRound's BetToken
     * @param bettingRoundID is the BettingRound's BettingRoundID
     */
    public BettingRound(BettingRoundID bettingRoundID, BetToken betToken) throws IllegalArgumentException {
        if (bettingRoundID != null) {
            if (betToken != null) {
                this.bettingRoundID = bettingRoundID;
                this.betToken = betToken;
                this.allBetsMade = new HashSet<>();
            } else {
                throw new IllegalArgumentException("BetToken cannot be null.");
            }
        } else {
            throw new IllegalArgumentException("BettingRoundID cannot be null.");
        }
    }

    /**
     * add a bet to the current bettinground.
     *  Note: also use the appropiate required methods from the gambling authority API
     *  Update based on class teams call: there is not any required methods from the gambling authority API for BettingRound to call
     *
     * @should throwIllegalArgumentExceptionWhenBetIsNull
     * @should addBetToListOfBets
     * @should notAddBetToListOfBetsIfItIsAlreadyInTheList
     *
     * @param bet is the bet to place
     * @return true if bet is made, will never return false as the bet is checked if it is valid before this class
     * @throws IllegalArgumentException when Bet is null
     */
    @Override
    public boolean placeBet(Bet bet) throws IllegalArgumentException {
        if (bet != null) {
            this.allBetsMade.add(bet);
        } else {
            throw new IllegalArgumentException("Bet to place cannot be null.");
        }
        return true;
    }

    /**
     * @should returnTheSizeOfTheBetsList
     *
     * @return number of bets made in the betting round
     */
    @Override
    public int numberOFBetsMade() {
        return this.allBetsMade.size();
    }

    /**
     * Make BettingRounds with same BettingRoundID logically equal
     * @should makeBettingRoundsWithSameBettingRoundIDLogicallyEqual
     *
     * @param o is  the object to compare
     * @return true if they are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BettingRound that = (BettingRound) o;
        return Objects.equals(bettingRoundID, that.bettingRoundID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bettingRoundID);
    }


    // BELOW ARE GETTERS CREATED WITH INTELLIJ SO THEY DO NOT NEED TO BE TESTED

    /**
     *
     * @return this BettingRound's bettingID
     */
    @Override
    public BettingRoundID getBettingRoundID() {
        return bettingRoundID;
    }

    /**
     *
     * @return betToken from this betting round.
     */
    @Override
    public BetToken getBetToken() {
        return betToken;
    }

    /**
     *
     * @return set of all bets made in this betting round.
     */
    @Override
    public Set<Bet> getAllBetsMade() {
        return allBetsMade;
    }
}

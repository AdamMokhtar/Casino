package casino.gamingmachine;

import casino.bet.BetResult;
import casino.cashier.BetNotExceptedException;
import casino.cashier.IGamblerCard;
import casino.cashier.InvalidAmountException;
import casino.game.NoBetsMadeException;
import casino.game.NoCurrentRoundException;
import casino.idfactory.GamingMachineID;

/**
 * Described a gaming machine. a machine has a gamingmachineID,
 * is always connected to a game and the cashier.
 * one card can be connected or disconnected to a machine.
 */
public interface IGamingMachine {
    /**
     *
     * try to place bet with given amount and connected card.
     * amount needs to be checked with the cashier
     * if accepted: generate a bet using the card and add it to the game.
     *
     * @param amountInCents amount in cents to gamble
     * @return true if bet is valid, excepted and added to betting round.
     * @throws NoPlayerCardException when no card is connected to this machine.
     */
    boolean placeBet(long amountInCents) throws NoPlayerCardException, CurrentBetMadeException, InvalidAmountException, BetNotExceptedException, NoCurrentRoundException, NoBetsMadeException;

    /**
     * Accept the BetResult from the winner. Clear all open bets on this machine.
     * When the winner has made his bet on this machine: let the Cashier update
     * the amount of the winner.
     *
     * @param winResult result of a betting round. can be null when there is no winner.
     */
    void acceptWinner(BetResult winResult) throws InvalidAmountException;

    /**
     * getter
     * @return gamingmachineID
     */
    GamingMachineID getGamingMachineID();

    /**
     * connect card to this gaming machine
     * @param card card to connect
     */
    public void connectCard(IGamblerCard card);


    /**
     * disconnects/removes card from this gaming machine.
     * @throws CurrentBetMadeException when open bets made with this card
     * are still present in the current betting round and
     */
    void disconnectCard() throws CurrentBetMadeException;


}

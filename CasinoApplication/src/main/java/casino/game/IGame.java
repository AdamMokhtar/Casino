package casino.game;

import casino.bet.Bet;
import casino.cashier.InvalidAmountException;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.IGamingMachine;

import java.util.Set;

/**
 * represents a game in a casino.
 * every game needs access to the gaming authority API for logging and for betting round
 * activities and needs to have a gamerule.
 *
 *
 *
 */
public interface IGame {

    /**
     * create and start a new BettingRound.
     * when called when a current bettinground is active: the current bettinground ends and a new
     * bettinground is created, which becomes the current bettinground.
     *
     * Note: also use the appropiate required methods from the gambling authority API
     *
     */
    void startBettingRound();

    /**
     * Accept a bet on the current betting round.
     * determine if the betting round is finished, if so: determine the winner,
     * notify the connected gaming machines and start a new betting round.
     *
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @param bet the bet to be made on the betting round
     * @param gamingMachine gamingmachine which places bet on this game.
     * @return true when bet is accepted by the game, otherwise false.
     * @throws NoCurrentRoundException when no BettingRound is currently active.
     */
    boolean acceptBet(Bet bet, IGamingMachine gamingMachine) throws NoCurrentRoundException, NoBetsMadeException, InvalidAmountException;


    /**
     * End the current bettinground and calculate the winner using the gamerules.
     * notifiy all connected game machines of the BetResult.
     * When no bets have been made yet, no winner can be determined. In this case, only log to the betlogging authority,
     * and end the current bettinground.
     *
     * Note: also use the appropiate required methods from the gambling authority API
     */
    void determineWinner() throws NoBetsMadeException, InvalidAmountException;

    /**
     * determine if the right number of bets are done (determined by gamerules) to be able to
     * calculate a winner.
     * for calculation a winner, a true random value needs to be received from the gambling authority API.
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @return true if all necessary bets are made in the betting round, otherwise false
     */
    boolean isBettingRoundFinished() throws NoCurrentRoundException;

    /**
     * to get the betting round created for the game
     * @return the current betting round
     */
    BettingRound getCurrentBettingRound();

    /**
     * to set the current betting round with another betting round
     * this is mainly created for testing to mock the calls to the bettingRound
     * @param currentBettingRound
     */
    void setCurrentBettingRound(BettingRound currentBettingRound);


    /**
     * all the gaming machines connected to the game will be returned
     * @return set of gaming machines added to the game
     */
    Set<GamingMachine> getConnectedMachines();

    /**
     * all the betting rounds added to the game will be returned
     * @return set of betting rounds added to the game
     */
    Set<BettingRound> getBettingRounds();

}
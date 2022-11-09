package casino.game;


import casino.bet.Bet;
import casino.bet.BetResult;
import casino.cashier.InvalidAmountException;
import casino.gamingmachine.GamingMachine;
import casino.gamingmachine.IGamingMachine;
import casino.idfactory.BettingRoundID;
import casino.idfactory.GeneralID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.BettingAuthority;
import gamblingauthoritiy.IBetLoggingAuthority;
import gamblingauthoritiy.IBetTokenAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * represents a game in a casino.
 * every game needs access to the gaming authority API for logging and for betting round
 * activities and needs to have a gamerule.
 */
public class DefaultGame extends AbstractGame {

    private Set<BettingRound> bettingRounds;
    private Set<GamingMachine> connectedMachines;
    private BettingRound currentBettingRound;

    /**
     * @author ciara
     * creates an object of type AbstractGame with an IBetLoggingAuthority and IBetTokenAuthority and IGameRule
     *
     * @param betLoggingAuthority is the DefaultGame's IBetLoggingAuthority
     * @param betTokenAuthority is the DefaultGame's IBetTokenAuthority
     * @param gameRule is the DefaultGame's IGameRule
     * @should createAnAbstractGameWithPassedIBetLoggingAuthorityAndIBetTokenAuthorityAndIGameRule
     * @should throwIllegalArgumentExceptionWithNullIBetLoggingAuthorityOrIBetTokenAuthorityOrIGameRule
     * @should initializeConnectedMachineAndBettingRoundsSet
     */
    public DefaultGame(IBetLoggingAuthority betLoggingAuthority, IBetTokenAuthority betTokenAuthority,
                       IGameRule gameRule) throws IllegalArgumentException {
        super(betLoggingAuthority, betTokenAuthority, gameRule);
        connectedMachines = new HashSet<>();
        bettingRounds = new HashSet<>();
    }

    /**
     * @author adam
     * create and start a new BettingRound.
     * when called when a current bettinground is active: the current bettinground ends and a new
     * bettinground is created, which becomes the current bettinground.
     *
     * @should createNewBettingRoundAndAssignItAsTheCurrentBettingRound
     * @should callLogStartBettingRoundToLogTheNewCreatedBettingRound
     * @should addTheNewlyCreatedBettingRoundToTheListOfBettingRounds
     * @should emptyTheSetOfConnectedMachines
     */
    @Override
    public void startBettingRound() {
        BettingRoundID bettingRoundID = (BettingRoundID) IDFactory.generateID("BETTINGROUNDID");
        BetToken betToken = getBetTokenAuthority().getBetToken(bettingRoundID);  //cant set when for this!!
        connectedMachines.clear();
        BettingRound bettingRound = new BettingRound(bettingRoundID,betToken);
        bettingRounds.add(bettingRound);
        this.currentBettingRound = bettingRound;
        getBetLoggingAuthority().logStartBettingRound(getCurrentBettingRound());
    }

    /**
     * @author adam
     * Accept a bet on the current betting round.
     * determine if the betting round is finished, if so: determine the winner,
     * notify the connected gaming machines and start a new betting round.
     *
     * @should throwIllegalArgumentExceptionWhenBetOrGamingMachineIsNull
     * @should throwNoCurrentRoundExceptionWhenTheCurrentBettingRoundIsNull
     * @should returnFalseIfTheGamingMachinePassedInTheParameterAlreadyExist
     * @should callPlaceBetOnTheCurrentBettingRoundByPassingTheBetAsParameter
     * @should triggerIsBettingRoundFinishedIfPlacingTheBetReturnedTrueAndCallGetMaxBetsPerRound
     * @should triggerDetermineWinnerIfIsBettingRoundFinishedReturnedTrueAndCallGetRandomInteger
     * @should callLogAddAcceptedBetOnBetLoggingAuthorityByPassingTheBetAndTheBettingRoundIDAndGamingMachineId
     * @should returnTrueIfTrueIsReturnedByPlaceBetOnTheCurrentBettingRound
     * @should returnFalseIfFalseIsReturnedByPlaceBetOnTheCurrentBettingRound
     * @should addTheGamingMachineToTheSetOFConnectedMachinesToHaveOnlyOneBetPerMachine
     *
     * @param bet the bet to be made on the betting round
     * @param gamingMachine gamingmachine which places bet on this game.
     * @return true if placing a bet on the betting round returns true, return false if the game machine passed already exist or placing the bet on the betting round returned false
     * @throws NoCurrentRoundException if the current betting round is null
     * @throws IllegalArgumentException if the parameter input in null
     * @throws NoBetsMadeException its needed to call determineWinner which has this throw
     * @throws InvalidAmountException its needed to call determineWinner function
     */
    @Override
    public boolean acceptBet(Bet bet, IGamingMachine gamingMachine) throws NoCurrentRoundException, IllegalArgumentException, NoBetsMadeException, InvalidAmountException {
        if(bet == null || gamingMachine == null)
        {
            throw new IllegalArgumentException("bet or gaming machine input is null");
        }
        if(currentBettingRound == null)
        {
            throw new NoCurrentRoundException("There's not a current betting round");
        }
        if(connectedMachines.contains(gamingMachine))
        {
            return false;
        }
        connectedMachines.add((GamingMachine) gamingMachine);
        if(currentBettingRound.placeBet(bet))
        {
            getBetLoggingAuthority().logAddAcceptedBet(bet,currentBettingRound.getBettingRoundID(),gamingMachine.getGamingMachineID());
            if(isBettingRoundFinished())
            {
                determineWinner();
            }
            return true;
        }
        return false;
    }

    /**
     * @author adam
     * End the current bettinground and calculate the winner using the gamerules.
     * notifiy all connected game machines of the BetResult.
     * When no bets have been made yet, no winner can be determined. In this case, only log to the betlogging authority,
     * and end the current bettinground.
     *
     * @should setTheCurrentBettingRoundAsNull
     * @should callGetRandomIntegerOnBetTokenAuthorityToGetTheRandomInteger
     * @should callDetermineWinnerOfTheRuleClassByPassingTheSetOfBetsAndTheRandomValueToReturnBetResult
     * @should callAcceptWinnerOnAllConnectedMachinesAndPassBetResult
     * @should notSetWinnerWhenNoBetsWerePlaced
     * @should callLogEndBettingRoundWithNullAsResultIfNoBetsWereMade
     * @should callLogEndBettingRoundOnBetLoggingAuthorityWithCurrentBettingRoundAndBetResultAsParameters
     * @throws NoBetsMadeException if no bets where placed on the current betting round
     * @throws InvalidAmountException it is needed to call determineWinner and acceptWinner
     */
    @Override
    public void determineWinner() throws NoBetsMadeException, InvalidAmountException {
        if(currentBettingRound.getAllBetsMade().size() != 0)
        {
            int random = getBetTokenAuthority().getRandomInteger(currentBettingRound.getBetToken());
            BetResult betResult = getGameRule().determineWinner(random,currentBettingRound.getAllBetsMade());
            for (GamingMachine gamingMachine: connectedMachines) {
                gamingMachine.acceptWinner(betResult);
            }
            getBetLoggingAuthority().logEndBettingRound(currentBettingRound,betResult);
        }
        else
        {
            getBetLoggingAuthority().logEndBettingRound(currentBettingRound,null);
            currentBettingRound = null;
        }
    }

    /**
     * @author adam
     * determine if the right number of bets are done (determined by gamerules) to be able to
     * calculate a winner.
     * for calculation a winner, a true random value needs to be received from the gambling authority API.
     *
     * @should callGetMaxBetsPerRoundOnGameRuleToGetTheMaximumNumberOfBets
     * @should callGetAllBetsMadeOnBettingRound
     * @should returnTrueWhenGetMaxBetsPerRoundIsPerceivedByComparingAllBetsMadeOnBettingRound
     * @should returnFalseWhenGetMaxBetsPerRoundIsNotPerceivedByComparingAllBetsMadeOnBettingRound
     * @should throwNoCurrentRoundExceptionWhenTheCurrentBettingRoundIsNull
     * note: call required methods from API its done in determineWinner
     * @return true if all necessary bets are made in the betting round, otherwise false
     * @throws NoCurrentRoundException if the current betting round is null
     */
    @Override
    public boolean isBettingRoundFinished() throws NoCurrentRoundException {
        if(getCurrentBettingRound() == null)
        {
            throw new NoCurrentRoundException("There is no current betting round");
        }
        int maxBetsPerRound = getGameRule().getMaxBetsPerRound();
        int countAllBetsMade = currentBettingRound.getAllBetsMade().size();
        if(maxBetsPerRound == countAllBetsMade)
        {
            return true;
        }
        return false;
    }

    // BELOW ARE GETTERS AND SETTERS CREATED WITH INTELLIJ SO THEY DO NOT NEED TO BE TESTED

    public BettingRound getCurrentBettingRound()
    {
        return currentBettingRound;
    }

    public void setCurrentBettingRound(BettingRound currentBettingRound) {
        this.currentBettingRound = currentBettingRound;
    }

    public Set<GamingMachine> getConnectedMachines() {
        return connectedMachines;
    }

    public Set<BettingRound> getBettingRounds() {
        return bettingRounds;
    }
}

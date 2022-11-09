package casino.game;


import casino.bet.Bet;
import casino.bet.BetResult;
import casino.cashier.Cashier;

import casino.gamingmachine.GamingMachine;
import casino.idfactory.BettingRoundID;
import casino.idfactory.GamingMachineID;
import gamblingauthoritiy.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;


import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
/*import static org.hamcrest.CoreMatchers.notNullValue;*/
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DefaultGameTest {
    AbstractGame sut;
    IBetLoggingAuthority betLoggingAuthority;
    IBetTokenAuthority betTokenAuthority;
    IGameRule gameRule;


    /**
     * @author ciara
     */
    @BeforeEach
    public void setUp() {

        betLoggingAuthority = mock(IBetLoggingAuthority.class);
        betTokenAuthority = new BetTokenAuthority(); //betTokenAuthority wont be mocked due to the accessibility limitation to BettingRoundID so it cant be passed in getBetToken
        gameRule = mock(GameRule.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);

    }

    /**
     * @author ciara
     * @verifies createAnAbstractGameWithPassedIBetLoggingAuthorityAndIBetTokenAuthorityAndIGameRule
     * @see DefaultGame#DefaultGame(IBetLoggingAuthority, IBetTokenAuthority, IGameRule)
     */
    @Test
    public void DefaultGame_shouldCreateAnAbstractGameWithPassedIBetLoggingAuthorityAndIBetTokenAuthorityAndIGameRule() {
        // Assert
        assertThat(sut.getBetLoggingAuthority()).isEqualTo(betLoggingAuthority);
        assertThat(sut.getBetTokenAuthority()).isEqualTo(betTokenAuthority);
        assertThat(sut.getGameRule()).isEqualTo(gameRule);
    }

    /**
     * @author ciara
     * @verifies throwIllegalArgumentExceptionWithNullIBetLoggingAuthorityOrIBetTokenAuthorityOrIGameRule
     * @see DefaultGame#DefaultGame(IBetLoggingAuthority, IBetTokenAuthority, IGameRule)
     */
    @Test
    public void DefaultGame_shouldThrowIllegalArgumentExceptionWithNullIBetLoggingAuthorityOrIBetTokenAuthorityOrIGameRule() {
        // Arrange, Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DefaultGame(betLoggingAuthority, betTokenAuthority, null));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DefaultGame(betLoggingAuthority, null, gameRule));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->
                new DefaultGame(null, betTokenAuthority, gameRule));
    }

    /**
     * @author adam
     * @verifies createNewBettingRoundAndAssignItAsTheCurrentBettingRound
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldCreateNewBettingRoundAndAssignItAsTheCurrentBettingRound() throws Exception {
        // act and assert
        BettingRound currentBettingRound = sut.getCurrentBettingRound();
        assertThatExceptionOfType(NoCurrentRoundException.class).isThrownBy(() -> sut.isBettingRoundFinished());
        sut.startBettingRound();
        BettingRound newCurrentBettingRound = sut.getCurrentBettingRound();
        assertDoesNotThrow(() -> sut.isBettingRoundFinished());
        assertThat(currentBettingRound).isNotEqualTo(newCurrentBettingRound);
    }

    /**
     * @author adam
     * @verifies callGetLoggingAuthorityOnBetLoggingAuthorityAndCallLogStartBettingRoundToLogTheNewCreatedBettingRound
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldCallLogStartBettingRoundToLogTheNewCreatedBettingRound() throws Exception {
        // act
        sut.startBettingRound();
        // verify
        verify(betLoggingAuthority).logStartBettingRound(sut.getCurrentBettingRound());
    }

    /**
     * @author adam
     * @verifies throwIllegalArgumentExceptionWhenBetOrGamingMachineIsNull
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldThrowIllegalArgumentExceptionWhenBetOrGamingMachineIsNull() throws Exception {
        // arrange
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        // act
        sut.startBettingRound();
        // assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.acceptBet(null, gamingMachine));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.acceptBet(bet, null));
    }

    /**
     * @author adam
     * @verifies throwNoCurrentRoundExceptionWhenTheCurrentBettingRoundIsNull
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldThrowNoCurrentRoundExceptionWhenTheCurrentBettingRoundIsNull() throws Exception {
        // arrange
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        // assert
        assertThatExceptionOfType(NoCurrentRoundException.class).isThrownBy(() -> sut.acceptBet(bet, gamingMachine));
    }

    /**
     * @author adam
     * @verifies callPlaceBetOnTheCurrentBettingRoundByPassingTheBetAsParameter
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldCallPlaceBetOnTheCurrentBettingRoundByPassingTheBetAsParameter() throws Exception {
        // arrange and act
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        sut.acceptBet(bet, gamingMachine);
        // verify
        verify(bettingRound).placeBet(bet);
    }

    /**
     * @author adam
     * @verifies triggerIsBettingRoundFinishedIfPlacingTheBetReturnedTrueAndCallGetMaxBetsPerRound
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldTriggerIsBettingRoundFinishedIfPlacingTheBetReturnedTrueAndCallGetMaxBetsPerRound() throws Exception {
        // arrange and act
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        when(bettingRound.placeBet(bet)).thenReturn(true);
        sut.acceptBet(bet, gamingMachine);
        // verify
        verify(gameRule).getMaxBetsPerRound();
    }

    /**
     * @author adam
     * @verifies triggerDetermineWinnerIfIsBettingRoundFinishedReturnedTrueAndSetCurrentRoundToNull
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldTriggerDetermineWinnerIfIsBettingRoundFinishedReturnedTrueAndCallGetRandomInteger() throws Exception {
        // arrange and act
        betTokenAuthority = mock(BetTokenAuthority.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        when(bettingRound.placeBet(bet)).thenReturn(true);
        //#region make IsBettingRoundFinished return true
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        Bet bet2 = mock(Bet.class);
        bets.add(bet2);
        when(gameRule.getMaxBetsPerRound()).thenReturn(2);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        //#endregion
        //#region make determineWinner pass
        BetToken betToken = mock(BetToken.class);
        when(bettingRound.getBetToken()).thenReturn(betToken);
        //#endregion
        sut.acceptBet(bet, gamingMachine);
        //verify
        verify(betTokenAuthority).getRandomInteger(betToken);
    }

    /**
     * @author adam
     * @verifies callLogAddAcceptedBetOnBetLoggingAuthorityByPassingTheBetAndTheBettingRoundIDAndGamingMachineId
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldCallLogAddAcceptedBetOnBetLoggingAuthorityByPassingTheBetAndTheBettingRoundIDAndGamingMachineId() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        BettingRoundID bettingRoundID = mock(BettingRoundID.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        GamingMachineID gamingMachineID = mock(GamingMachineID.class);
        sut.setCurrentBettingRound(bettingRound);
        when(bettingRound.placeBet(bet)).thenReturn(true);
        when(bettingRound.getBettingRoundID()).thenReturn(bettingRoundID);
        when(gamingMachine.getGamingMachineID()).thenReturn(gamingMachineID);
        sut.acceptBet(bet, gamingMachine);
        // verify
        verify(betLoggingAuthority).logAddAcceptedBet(bet,bettingRound.getBettingRoundID(),gamingMachine.getGamingMachineID());
    }

    /**
     * @author adam
     * @verifies returnTrueIfTrueIsReturnedByPlaceBetOnTheCurrentBettingRound
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldReturnTrueIfTrueIsReturnedByPlaceBetOnTheCurrentBettingRound() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        sut.setCurrentBettingRound(bettingRound);
        when(bettingRound.placeBet(bet)).thenReturn(true);
        // assert
        assertThat(sut.acceptBet(bet, gamingMachine)).isTrue();
    }

    /**
     * @author adam
     * @verifies returnFalseIfFalseIsReturnedByPlaceBetOnTheCurrentBettingRound
     * @see DefaultGame#acceptBet(casino.bet.Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldReturnFalseIfFalseIsReturnedByPlaceBetOnTheCurrentBettingRound() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        sut.setCurrentBettingRound(bettingRound);
        when(bettingRound.placeBet(bet)).thenReturn(false);
        // assert
        assertThat(sut.acceptBet(bet, gamingMachine)).isFalse();
    }

    /**
     * @author adam
     * @verifies setTheCurrentBettingRoundAsNull
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldSetTheCurrentBettingRoundAsNull() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        sut.determineWinner();
        // assert
        assertThat(sut.getCurrentBettingRound()).isNull();
    }

    /**
     * @author adam
     * @verifies callGetRandomIntegerOnBetTokenAuthorityToGetTheRandomInteger
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldCallGetRandomIntegerOnBetTokenAuthorityToGetTheRandomInteger() throws Exception {
        // arrange and act
        betTokenAuthority = mock(BetTokenAuthority.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        BetToken betToken = mock(BetToken.class);
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        when(bettingRound.getBetToken()).thenReturn(betToken);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        sut.determineWinner();
        // verify
        verify(betTokenAuthority).getRandomInteger(betToken);
    }

    /**
     * @author adam
     * @verifies callDetermineWinnerOfTheRuleClassByPassingTheSetOfBetsAndTheRandomValueToReturnBetResult
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldCallDetermineWinnerOfTheRuleClassByPassingTheSetOfBetsAndTheRandomValueToReturnBetResult() throws Exception {
        // arrange and act
        betTokenAuthority = mock(BetTokenAuthority.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        BetToken betToken = mock(BetToken.class);
        when(bettingRound.getBetToken()).thenReturn(betToken);
        when(betTokenAuthority.getRandomInteger(betToken)).thenReturn(5);
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        Bet bet2 = mock(Bet.class);
        bets.add(bet2);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        sut.determineWinner();
        // verify
        verify(gameRule).determineWinner(betTokenAuthority.getRandomInteger(betToken),bets);
    }

    /**
     * @author adam
     * @verifies callAcceptWinnerOnAllConnectedMachinesAndPassBetResult
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldCallAcceptWinnerOnAllConnectedMachinesAndPassBetResult() throws Exception {
        // arrange and act
        betTokenAuthority = mock(BetTokenAuthority.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        Bet bet2 = mock(Bet.class);
        bets.add(bet2);
        GamingMachine gamingMachine1 = mock(GamingMachine.class);
        GamingMachine gamingMachine2 = mock(GamingMachine.class);
        BetResult betResult = mock(BetResult.class);
        BetToken betToken = mock(BetToken.class);
        when(bettingRound.getBetToken()).thenReturn(betToken);
        when(betTokenAuthority.getRandomInteger(betToken)).thenReturn(1);
        sut.acceptBet(bet1,gamingMachine1);
        sut.acceptBet(bet2,gamingMachine2);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        when(gameRule.determineWinner(1,bets)).thenReturn(betResult);
        sut.determineWinner();
        // verify
        verify(gamingMachine1).acceptWinner(betResult);
        verify(gamingMachine2).acceptWinner(betResult);
    }

    /**
     * @author adam
     * @verifies notSetWinnerWhenNoBetsWerePlaced
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldNotSetWinnerWhenNoBetsWerePlaced() throws Exception {
        // arrange
        betTokenAuthority = mock(BetTokenAuthority.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);
        BetToken betToken = mock(BetToken.class);
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        Set<Bet> bets = new HashSet<>();
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        // act
        sut.determineWinner();
        // assert
        verify(betTokenAuthority, never()).getRandomInteger(betToken);
    }

    /**
     * @author adam
     * @verifies callLogEndBettingRoundOnBetLoggingAuthorityWithCurrentBettingRoundAndBetResultAsParameters
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldCallLogEndBettingRoundOnBetLoggingAuthorityWithCurrentBettingRoundAndBetResultAsParameters() throws Exception {
        // arrange and act
        betTokenAuthority = mock(BetTokenAuthority.class);
        sut = new DefaultGame(betLoggingAuthority, betTokenAuthority, gameRule);
        BetResult betResult = mock(BetResult.class);
        BettingRound bettingRound = mock(BettingRound.class);
        BetToken betToken = mock(BetToken.class);
        sut.setCurrentBettingRound(bettingRound);
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        when(bettingRound.getBetToken()).thenReturn(betToken);
        when(betTokenAuthority.getRandomInteger(betToken)).thenReturn(1);
        when(gameRule.determineWinner(1,bets)).thenReturn(betResult);
        sut.determineWinner();
        // verify
        verify(betLoggingAuthority).logEndBettingRound(bettingRound,betResult);
    }

    /**
     * @author adam
     * @verifies callGetMaxBetsPerRoundOnGameRuleToGetTheMaximumNumberOfBets
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldCallGetMaxBetsPerRoundOnGameRuleToGetTheMaximumNumberOfBets() throws Exception {
        // act
        sut.startBettingRound();
        sut.isBettingRoundFinished();
        // assert
        verify(gameRule).getMaxBetsPerRound();
    }

    /**
     * @author adam
     * @verifies callGetAllBetsMadeOnBettingRound
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test

    public void isBettingRoundFinished_shouldCallGetAllBetsMadeOnBettingRound() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        sut.isBettingRoundFinished();
        // verify
        verify(bettingRound).getAllBetsMade();
    }

    /**
     * @author adam
     * @verifies returnTrueWhenGetMaxBetsPerRoundIsPerceivedByComparingAllBetsMadeOnBettingRound
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnTrueWhenGetMaxBetsPerRoundIsPerceivedByComparingAllBetsMadeOnBettingRound() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        Bet bet2 = mock(Bet.class);
        bets.add(bet2);
        when(gameRule.getMaxBetsPerRound()).thenReturn(2);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        // assert
        assertThat(sut.isBettingRoundFinished()).isEqualTo(true);
    }

    /**
     * @author adam
     * @verifies returnFalseWhenGetMaxBetsPerRoundIsNotPerceivedByComparingAllBetsMadeOnBettingRound
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldReturnFalseWhenGetMaxBetsPerRoundIsNotPerceivedByComparingAllBetsMadeOnBettingRound() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        Set<Bet> bets = new HashSet<>();
        Bet bet1 = mock(Bet.class);
        bets.add(bet1);
        when(gameRule.getMaxBetsPerRound()).thenReturn(5);
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        // assert
        assertThat(sut.isBettingRoundFinished()).isEqualTo(false);
    }

    /**
     * @author adam
     * @verifies throwNoCurrentRoundExceptionWhenTheCurrentBettingRoundIsNull  note: call required methods from API its done in determineWinner
     * @see DefaultGame#isBettingRoundFinished()
     */
    @Test
    public void isBettingRoundFinished_shouldThrowNoCurrentRoundExceptionWhenTheCurrentBettingRoundIsNullNoteCallRequiredMethodsFromAPIItsDoneInDetermineWinner() throws Exception {
        // assert
        assertThatExceptionOfType(NoCurrentRoundException.class).isThrownBy(() -> sut.isBettingRoundFinished());
    }

    /**
     * @author adam
     * @verifies addTheNewlyCreatedBettingRoundToTheListOfBettingRounds
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldAddTheNewlyCreatedBettingRoundToTheListOfBettingRounds() throws Exception {
        // act
        sut.startBettingRound();
        // assert
        assertThat(sut.getBettingRounds().size()).isEqualTo(1);
    }

    /**
     * @author adam
     * @verifies emptyTheSetOfConnectedMachines
     * @see DefaultGame#startBettingRound()
     */
    @Test
    public void startBettingRound_shouldEmptyTheSetOfConnectedMachines() throws Exception {
        // arrange and act and assert
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        sut.startBettingRound();
        sut.acceptBet(bet,gamingMachine);
        sut.startBettingRound();
        assertThat(sut.getConnectedMachines().size()).isEqualTo(0);
    }

    /**
     * @author adam
     * @verifies addTheGamingMachineToTheSetOFConnectedMachinesToHaveOnlyOneBetPerMachine
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldAddTheGamingMachineToTheSetOFConnectedMachinesToHaveOnlyOneBetPerMachine() throws Exception {
        // arrange
        Bet bet = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        // act
        sut.startBettingRound();
        sut.acceptBet(bet,gamingMachine);
        // assert
        assertThat(sut.getConnectedMachines().size()).isEqualTo(1);
    }

    /**
     * @author adam
     * @verifies initializeConnectedMachineAndBettingRoundsSet
     * @see DefaultGame#DefaultGame(IBetLoggingAuthority, IBetTokenAuthority, IGameRule)
     */
    @Test
    public void DefaultGame_shouldInitializeConnectedMachineAndBettingRoundsSet() throws Exception {
        // assert
        assertThat(sut.getConnectedMachines()).isNotNull();
        assertThat(sut.getBettingRounds()).isNotNull();
    }

    /**
     * @author adam
     * @verifies returnFalseIfTheGamingMachinePassedInTheParameterAlreadyExist
     * @see DefaultGame#acceptBet(Bet, casino.gamingmachine.IGamingMachine)
     */
    @Test
    public void acceptBet_shouldReturnFalseIfTheGamingMachinePassedInTheParameterAlreadyExist() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        Bet bet1 = mock(Bet.class);
        Bet bet2 = mock(Bet.class);
        GamingMachine gamingMachine = mock(GamingMachine.class);
        sut.acceptBet(bet1,gamingMachine);
        when(bettingRound.placeBet(bet2)).thenReturn(true);
        // assert
        assertThat(sut.acceptBet(bet2,gamingMachine)).isFalse();
    }

    /**
     * @verifies callLogEndBettingRoundWithNullAsResultIfNoBetsWereMade
     * @see DefaultGame#determineWinner()
     */
    @Test
    public void determineWinner_shouldCallLogEndBettingRoundWithNullAsResultIfNoBetsWereMade() throws Exception {
        // arrange and act
        BettingRound bettingRound = mock(BettingRound.class);
        sut.setCurrentBettingRound(bettingRound);
        Set<Bet> bets = new HashSet<>();
        when(bettingRound.getAllBetsMade()).thenReturn(bets);
        sut.determineWinner();
        // assert
        verify(betLoggingAuthority).logEndBettingRound(bettingRound,null);
    }
}
// need to make changes after the teachers feedback, resulted in redoing default game test!
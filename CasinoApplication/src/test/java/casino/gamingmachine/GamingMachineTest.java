package casino.gamingmachine;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.*;
import casino.game.IGame;
import casino.game.NoBetsMadeException;
import casino.game.NoCurrentRoundException;
import casino.idfactory.BetID;
import casino.idfactory.GamingMachineID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;



import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * @author nagi
 */

public class GamingMachineTest {

    private static long VALID_MONEY_AMOUNT_iN_CENTS = 50l;
    GamingMachine sut;
    IGame game;
    Cashier cashier;

    @BeforeEach
    public void setUp(){

        game = mock(IGame.class);
        cashier= mock(Cashier.class);
        sut = new GamingMachine(game, cashier);

    }

    /**
     * @verifies throwIllegalArgumentExceptionIfOneOfTheParametersIsNull
     * @see GamingMachine#GamingMachine(casino.game.IGame, casino.cashier.Cashier)
     */
    @Test
    public void GamingMachine_shouldThrowIllegalArgumentExceptionIfOneOfTheParametersIsNull()  {

        //arrange//act//assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            GamingMachine sutA = new GamingMachine(null, null);
            GamingMachine sutB = new GamingMachine(game, null);
            GamingMachine sutC = new GamingMachine(null, cashier);
        });
    }

    /**
     * @verifies initialiseGameAndCashierWithPassedParameters
     * @see GamingMachine#GamingMachine(casino.game.IGame, casino.cashier.Cashier)
     */
    @Test
    public void GamingMachine_shouldInitialiseGameAndCashierWithPassedParameters()  {

        //arrange//act//assert
        assertThat(sut.getGame()).isEqualTo(game);
        assertThat(sut.getCashier()).isEqualTo(cashier);

    }

    /**
     * @verifies throwNoPlayerCardExceptionInCaseTheConnectedGamblerCardIsNull
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldThrowNoPlayerCardExceptionInCaseTheConnectedGamblerCardIsNull()  {

        //arrange//act//assert
        assertThatExceptionOfType(NoPlayerCardException.class).isThrownBy(() -> {
            sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS);
        });
    }

    /**
     * @verifies throwCurrentBetMadeExceptionInCaseTheOpenBetIsNotNull
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldThrowCurrentBetMadeExceptionInCaseTheOpenBetIsNotNull()  {
        //arrange
        Bet bet = mock(Bet.class);
        GamblerCard gamblerCard = mock(GamblerCard.class);
        // act
        sut.setOpenBet(bet);
        sut.setConnectedGamblerCard(gamblerCard);
        // assert
        assertThatExceptionOfType(CurrentBetMadeException.class).isThrownBy(() -> {
            sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS);
        });
    }

    /**
     * @verifies callGenerateNewBetIdOnGamblerCardToGetABetID
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldCallGenerateNewBetIdOnGamblerCardToGetABetID() throws NoPlayerCardException, CurrentBetMadeException, InvalidAmountException, BetNotExceptedException, NoCurrentRoundException, NoBetsMadeException {

        //arrange
        GamblerCard gamblerCard = mock(GamblerCard.class);
        // act
        sut.setConnectedGamblerCard(gamblerCard);
        sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS);
        //assert
        verify(gamblerCard).generateNewBetID();

    }

    /**
     * @verifies createNewBetWithTheBetIdOfTheConnectedGamblerCardAndCreateMoneyAmountFromPassedAmountOfCentsThenAssignItToOpenBet
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldCreateNewBetWithTheBetIdOfTheConnectedGamblerCardAndCreateMoneyAmountFromPassedAmountOfCentsThenAssignItToOpenBet() throws NoPlayerCardException, CurrentBetMadeException, InvalidAmountException, BetNotExceptedException, NoCurrentRoundException, NoBetsMadeException {

        //arrange
        BetID betID = mock(BetID.class);
        GamblerCard gamblerCard = mock(GamblerCard.class);
        //act
        sut.setConnectedGamblerCard(gamblerCard);
        when(gamblerCard.generateNewBetID()).thenReturn(betID);
        sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS);
        //assert
        assertThat(sut.getOpenBet().getBetID()).isEqualTo(betID);
        assertThat(sut.getOpenBet().getMoneyAmount().getAmountInCents()).isEqualTo(VALID_MONEY_AMOUNT_iN_CENTS);

    }

    /**
     * @verifies throwInvalidAmountExceptionInCaseTheAmountInCentsIsNegativeValueOrNull
     * @see GamingMachine#placeBet(long)
     */
    @ParameterizedTest
    @ValueSource(longs = {-1, -4, 0, -100, -5})
    public void placeBet_shouldThrowInvalidAmountExceptionInCaseTheAmountInCentsIsNegativeValueOrNull(long value)  {

        //arrange
        GamblerCard gamblerCard = mock(GamblerCard.class);
        // act
        sut.setConnectedGamblerCard(gamblerCard);
        // assert
        assertThatExceptionOfType(InvalidAmountException.class).isThrownBy(() -> {
            sut.placeBet(value);

        });
    }

    /**
     * @verifies returnFalseInCaseTheCheckIfBetIsValidOnCashierReturnFalse
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldReturnFalseInCaseTheCheckIfBetIsValidOnCashierReturnFalse() throws BetNotExceptedException, InvalidAmountException, CurrentBetMadeException, NoPlayerCardException, NoCurrentRoundException, NoBetsMadeException {
        //arrange
        IGamblerCard gamblerCard = mock(GamblerCard.class);
        BetID connectCardBetId = mock(BetID.class);
        // act
        sut.setConnectedGamblerCard(gamblerCard);
        MoneyAmount moneyAmount = new MoneyAmount(VALID_MONEY_AMOUNT_iN_CENTS);
        Bet openBet = new Bet(connectCardBetId,moneyAmount);
        when(cashier.checkIfBetIsValid(gamblerCard, openBet)).thenReturn(false);
        //assert
        assertThat(sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS)).isFalse();
    }

    /**
     * @verifies returnTrueOnlyIfTheCheckIfBetIsValidOnCashierAndTheAcceptBetOnGameReturnTrue
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldReturnTrueOnlyIfTheCheckIfBetIsValidOnCashierAndTheAcceptBetOnGameReturnTrue() throws NoCurrentRoundException, BetNotExceptedException, InvalidAmountException, CurrentBetMadeException, NoPlayerCardException, NoBetsMadeException {

        //arrange
        BetID connectCardBetId = mock(BetID.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        //act
        sut.setConnectedGamblerCard(gamblerCard);
        when(gamblerCard.generateNewBetID()).thenReturn(connectCardBetId);
        MoneyAmount moneyAmount = new MoneyAmount(VALID_MONEY_AMOUNT_iN_CENTS);
        Bet openBet = new Bet(connectCardBetId,moneyAmount);
        when(cashier.checkIfBetIsValid(gamblerCard, openBet)).thenReturn(true);
        when(game.acceptBet(openBet, sut)).thenReturn(true);
        //assert
        assertThat(sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS)).isTrue();
    }

    /**
     * @verifies returnFalseInCaseTheAcceptBetInGameReturnFalse
     * @see GamingMachine#placeBet(long)
     */
    @Test
    public void placeBet_shouldReturnFalseInCaseTheAcceptBetInGameReturnFalse() throws Exception  {
        //arrange
        BetID connectCardBetId = mock(BetID.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        //act
        sut.setConnectedGamblerCard(gamblerCard);
        when(gamblerCard.generateNewBetID()).thenReturn(connectCardBetId);
        MoneyAmount moneyAmount = new MoneyAmount(VALID_MONEY_AMOUNT_iN_CENTS);
        Bet openBet = new Bet(connectCardBetId,moneyAmount);
        when(cashier.checkIfBetIsValid(gamblerCard, openBet)).thenReturn(true);
        when(game.acceptBet(openBet, sut)).thenReturn(false);
        //assert
        assertThat(sut.placeBet(VALID_MONEY_AMOUNT_iN_CENTS)).isFalse();
    }

    /**
     * @verifies removeOpenBetInTheConnectedGameInTheMachine
     * @see GamingMachine#acceptWinner(casino.bet.BetResult)
     */
    @Test
    public void acceptWinner_shouldRemoveOpenBetInTheConnectedGameInTheMachine() throws InvalidAmountException {

        //arrange
        BetResult betResult = mock(BetResult.class);
        Bet openBet = mock(Bet.class);
        //act
        sut.setOpenBet(openBet);
        sut.acceptWinner(betResult);
        //assert
        assertThat(sut.getOpenBet()).isNull();
    }

    /**
     * @verifies callAddAmountFromTheCashierToMakeSureTheMoneyIsAddedToWinnerCard
     * @see GamingMachine#acceptWinner(casino.bet.BetResult)
     */
    @Test
    public void acceptWinner_shouldCallAddAmountFromTheCashierToMakeSureTheMoneyIsAddedToWinnerCard() throws InvalidAmountException {
        //arrange
        BetResult betResult = mock(BetResult.class);
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        // act
        sut.setConnectedGamblerCard(gamblerCard);
        when(betResult.getAmountWon()).thenReturn(moneyAmount);
        sut.acceptWinner(betResult);
        // assert
        verify(cashier).addAmount(sut.getConnectedGamblerCard(), moneyAmount);


    }

    /**
     * @verifies returnGamingMachineID
     * @see GamingMachine#getGamingMachineID()
     */
    @Test
    public void getGamingMachineID_shouldReturnGamingMachineID()  {

        //arrange
        GamingMachineID gamingMachineID = mock(GamingMachineID.class);
        //act
        sut.setGamingMachineID(gamingMachineID);
        //assert
        assertThat(sut.getGamingMachineID()).isEqualTo(gamingMachineID);

    }

    /**
     * @verifies connectGamblerCardToTheGamingMachine
     * @see GamingMachine#connectCard(casino.cashier.IGamblerCard)
     */
    @Test
    public void connectCard_shouldConnectGamblerCardToTheGamingMachine()  {
        //arrange
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        //act
        sut.connectCard(gamblerCard);
        //assert
        assertThat(sut.getConnectedGamblerCard()).isEqualTo(gamblerCard);

    }

    /**
     * @verifies throwIllegalArgumentExceptionInCaseThePassedGamblerCardIsNull
     * @see GamingMachine#connectCard(casino.cashier.IGamblerCard)
     */
    @Test
    public void connectCard_shouldThrowIllegalArgumentExceptionInCaseThePassedGamblerCardIsNull()  {
        //arrange//act//assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            sut.connectCard(null);
        });
    }

    /**
     * @verifies throwIllegalArgumentExceptionInCaseThePassedGamblerCardIsAlreadyConnected
     * @see GamingMachine#connectCard(casino.cashier.IGamblerCard)
     */
    @Test
    public void connectCard_shouldThrowIllegalArgumentExceptionInCaseThePassedGamblerCardIsAlreadyConnected()   {

        //arrange
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        // act
        sut.setConnectedGamblerCard(gamblerCard);
        // assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {
            sut.connectCard(gamblerCard);
        });
    }

    /**
     * @verifies setConnectedCardToToNullInCaseTheOpenBetIsNull
     * @see GamingMachine#disconnectCard()
     */
    @Test
    public void disconnectCard_shouldSetConnectedCardToToNullInCaseTheOpenBetIsNull() throws CurrentBetMadeException {
        //arrange
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        //act
        sut.setConnectedGamblerCard(gamblerCard);
        sut.disconnectCard();
        //assert
        assertThat(sut.getConnectedGamblerCard()).isNull();


    }

    /**
     * @verifies throwCurrentBetMadeExceptionInCaseTheOpenBetIsNotNull
     * @see GamingMachine#disconnectCard()
     */
    @Test
    public void disconnectCard_shouldThrowCurrentBetMadeExceptionInCaseTheOpenBetIsNotNull()  {
        //arrange
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        Bet openBet = mock(Bet.class);
        //act
        sut.setConnectedGamblerCard(gamblerCard);
        sut.setOpenBet(openBet);
        //assert
        assertThatExceptionOfType(CurrentBetMadeException.class).isThrownBy(() -> {
            sut.disconnectCard();
        });

    }
}
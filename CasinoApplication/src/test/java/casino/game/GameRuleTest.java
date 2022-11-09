package casino.game;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.bet.MoneyAmount;
import casino.cashier.InvalidAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.HashSet;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author nagi
 */
public class GameRuleTest {

    private static Integer VALID_RANDOM_WIN_NUMBER = 5;
    GameRule sut;
    Set<Bet> bets;

    @BeforeEach
    public void setUp(){
        sut = new GameRule();
        bets = new HashSet<>();
    }

    /**
     * @verifies throwNoBetsMadeExceptionInCaseThereIsNoBetsInTheBetsList
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldThrowNoBetsMadeExceptionInCaseThereIsNoBetsInTheBetsList()  {
      //arrange// act//assert
        assertThatExceptionOfType(NoBetsMadeException.class).isThrownBy(() -> {

            sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets);
        });
    }

    /**
     * @verifies throwIllegalArgumentExceptionIfRandomWinValueIsNotAWholeNumber
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, -4, -67, -100, -5})
    public void determineWinner_shouldThrowIllegalArgumentExceptionIfRandomWinValueIsNotAWholeNumber(Integer value)  {

        //arrange
        Bet bet = mock(Bet.class);
        // act
        bets.add(bet);
        // assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> {

            sut.determineWinner(value, bets);
        });
    }


    /**
     * @verifies createAndReturnABetResultWithTheWinningBetAndTheWinningMoneyAmountWhenTotalBetsIsEqualTheGetMaxBetsPerRound
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldCreateAndReturnABetResultWithTheWinningBetAndTotalMoneyAmountOfBetsAsTheWinningMoneyAmountWhenTotalBetsIsEqualTheGetMaxBetsPerRound() throws NoBetsMadeException, InvalidAmountException {

        //arrange
        Bet betA = mock(Bet.class);
        Bet betB = mock(Bet.class);
        Bet betC = mock(Bet.class);
        MoneyAmount moneyAmountA = mock(MoneyAmount.class);
        MoneyAmount moneyAmountB = mock(MoneyAmount.class);
        MoneyAmount moneyAmountC = mock(MoneyAmount.class);

        //act
        when(betA.getMoneyAmount()).thenReturn(moneyAmountA);
        when(betB.getMoneyAmount()).thenReturn(moneyAmountB);
        when(betC.getMoneyAmount()).thenReturn(moneyAmountC);

        when(moneyAmountA.getAmountInCents()).thenReturn(2L);
        when(moneyAmountB.getAmountInCents()).thenReturn(4L);
        when(moneyAmountC.getAmountInCents()).thenReturn(1L);

        bets.add(betA);
        bets.add(betB);
        bets.add(betC);
        //assert
        assertThat(sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets).getWinningBet()).isEqualTo(betB);
        assertThat(sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets).getAmountWon().getAmountInCents()).isEqualTo(7L);


    }

    /**
     * @verifies notCreateOrReturnABetResultTillTheTotalBetsIsEqualToTheGetMaxBetsPerRound
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldNotCreateOrReturnABetResultTillTheTotalBetsIsEqualToTheGetMaxBetsPerRound() throws NoBetsMadeException, InvalidAmountException {

        //arrange
        Bet betA = mock(Bet.class);
        Bet betB = mock(Bet.class);
        //act
        bets.add(betA);
        bets.add(betB);
        //assert
        assertFalse(sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets) instanceof BetResult);

    }

    /**
     * @verifies returnNullInCaseTheTotalBetsAreNotEqualToTheGetMaxBetsPerRound
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldReturnNullInCaseTheTotalBetsAreNotEqualToTheGetMaxBetsPerRound() throws NoBetsMadeException, InvalidAmountException {

        //arrange
        Bet betA = mock(Bet.class);
        Bet betB = mock(Bet.class);
        //act
        bets.add(betA);
        bets.add(betB);
        //assert
        assertThat(sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets)).isEqualTo(null);

    }

    /**
     * @verifies throwInvalidAmountExceptionInCaseTheWinningMoneyAmountIsNegativeValueOrNull
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldThrowInvalidAmountExceptionInCaseTheWinningMoneyAmountIs0()  {

        //arrange
        Bet betA = mock(Bet.class);
        Bet betB = mock(Bet.class);
        Bet betC = mock(Bet.class);
        MoneyAmount moneyAmountA = mock(MoneyAmount.class);
        MoneyAmount moneyAmountB = mock(MoneyAmount.class);
        MoneyAmount moneyAmountC = mock(MoneyAmount.class);

        //act
        when(betA.getMoneyAmount()).thenReturn(moneyAmountA);
        when(betB.getMoneyAmount()).thenReturn(moneyAmountB);
        when(betC.getMoneyAmount()).thenReturn(moneyAmountC);

        when(moneyAmountA.getAmountInCents()).thenReturn(0L);
        when(moneyAmountB.getAmountInCents()).thenReturn(0L);
        when(moneyAmountC.getAmountInCents()).thenReturn(0L);

        bets.add(betA);
        bets.add(betB);
        bets.add(betC);
        //assert
        assertThatExceptionOfType(InvalidAmountException.class).isThrownBy(() -> {

            sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets);
        });


    }
    /**
     * @verifies callAllTheMoneyAmountOfAllBetsInThePassedListOfBets
     * @see GameRule#determineWinner(Integer, java.util.Set)
     */
    @Test
    public void determineWinner_shouldCallAllTheMoneyAmountOfAllBetsInThePassedListOfBets() throws NoBetsMadeException, InvalidAmountException {

        //arrange
        Bet betA = mock(Bet.class);
        Bet betB = mock(Bet.class);
        Bet betC = mock(Bet.class);
        MoneyAmount moneyAmountA = mock(MoneyAmount.class);
        MoneyAmount moneyAmountB = mock(MoneyAmount.class);
        MoneyAmount moneyAmountC = mock(MoneyAmount.class);

        //act
        when(betA.getMoneyAmount()).thenReturn(moneyAmountA);
        when(betB.getMoneyAmount()).thenReturn(moneyAmountB);
        when(betC.getMoneyAmount()).thenReturn(moneyAmountC);

        when(moneyAmountA.getAmountInCents()).thenReturn(2L);
        when(moneyAmountB.getAmountInCents()).thenReturn(4L);
        when(moneyAmountC.getAmountInCents()).thenReturn(1L);
        bets.add(betA);
        bets.add(betB);
        bets.add(betC);
        sut.determineWinner(VALID_RANDOM_WIN_NUMBER, bets);
        //assert
        verify(betA, atLeastOnce()).getMoneyAmount();
        verify(betB, atLeastOnce()).getMoneyAmount();
        verify(betC, atLeastOnce()).getMoneyAmount();
    }
    /**
     * @verifies returnAWholeNumberBetween1To15
     * @see GameRule#getMaxBetsPerRound()
     */
    @Test
    public void getMaxBetsPerRound_returnValue3ForMaxBetsPerRound()  {

        //arrange//act//assert
        assertThat(sut.getMaxBetsPerRound()).isEqualTo(3);
    }
    /**
     * @verifies throwInvalidAmountExceptionInCaseTheWinningMoneyAmountIsNegativeValue
     * @see GameRule#determineWinner(Integer, Set)
     */
    @Test
    public void determineWinner_shouldThrowInvalidAmountExceptionInCaseTheWinningMoneyAmountIsNegativeValue()  {

        //arrange
        Bet betA = mock(Bet.class);
        Bet betB = mock(Bet.class);
        Bet betC = mock(Bet.class);
        MoneyAmount moneyAmountA = mock(MoneyAmount.class);
        MoneyAmount moneyAmountB = mock(MoneyAmount.class);
        MoneyAmount moneyAmountC = mock(MoneyAmount.class);

        //act
        when(betA.getMoneyAmount()).thenReturn(moneyAmountA);
        when(betB.getMoneyAmount()).thenReturn(moneyAmountB);
        when(betC.getMoneyAmount()).thenReturn(moneyAmountC);

        when(moneyAmountA.getAmountInCents()).thenReturn(-3L);
        when(moneyAmountB.getAmountInCents()).thenReturn(-5L);
        when(moneyAmountC.getAmountInCents()).thenReturn(-10L);

        bets.add(betA);
        bets.add(betB);
        bets.add(betC);
        //assert
        assertThatExceptionOfType(InvalidAmountException.class).isThrownBy(() -> {

            sut.determineWinner(VALID_RANDOM_WIN_NUMBER , bets);
        });
    }
}
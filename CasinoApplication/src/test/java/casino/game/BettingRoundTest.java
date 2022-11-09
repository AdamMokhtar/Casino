package casino.game;

import casino.bet.Bet;
import casino.cashier.Cashier;
import casino.idfactory.BettingRoundID;
import gamblingauthoritiy.BetToken;
import gamblingauthoritiy.IBetLoggingAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * @author ciara
 */
public class BettingRoundTest {
    private static BettingRoundID bettingRoundID;
    private static BetToken betToken;
    BettingRound sut;

    @BeforeEach
    public void setUp() {
        bettingRoundID = mock(BettingRoundID.class);
        betToken = mock(BetToken.class);
        sut = new BettingRound(bettingRoundID, betToken);
    }

    /**
     * @verifies createABettingRoundWithBettingRoundIDAndBetToken
     * @see BettingRound#BettingRound(casino.idfactory.BettingRoundID, gamblingauthoritiy.BetToken)
     */
    @Test
    public void BettingRound_shouldCreateABettingRoundWithBettingRoundIDAndBetTokenAndInitialiseBetsMade() {
        // Assert
        assertThat(sut.getBettingRoundID()).isEqualTo(bettingRoundID);
        assertThat(sut.getBetToken()).isEqualTo(betToken);
        assertThat(sut.getAllBetsMade()).isNotNull();
    }

    /**
     * @verifies throwIllegalArgumentExceptionWithNullBettingRoundIDOrBetToken
     * @see BettingRound#BettingRound(casino.idfactory.BettingRoundID, gamblingauthoritiy.BetToken)
     */
    @Test
    public void BettingRound_shouldThrowIllegalArgumentExceptionWithNullBettingRoundIDOrBetToken() {
        // Arrange, Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new BettingRound(null, betToken));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new BettingRound(bettingRoundID, null));
    }

    /**
     * @verifies throwIllegalArgumentExceptionWhenBetIsNull
     * @see BettingRound#placeBet(casino.bet.Bet)
     */
    @Test
    public void placeBet_shouldThrowIllegalArgumentExceptionWhenBetIsNull() {
        // Arrange, Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.placeBet(null));
    }

    /**
     * @verifies addBetToListOfBets
     * @see BettingRound#placeBet(casino.bet.Bet)
     */
    @Test
    public void placeBet_shouldAddBetToListOfBets() {
        // Arrange
        Bet bet = mock(Bet.class);

        // Act
        sut.placeBet(bet);

        // Assert
        assertThat(sut.getAllBetsMade()).contains(bet);
    }

    /**
     * @verifies notAddBetToListOfBetsIfItIsAlreadyInTheList
     * @see BettingRound#placeBet(Bet)
     *
     * this will always be true as we are using a HashSet
     */
    @Test
    public void placeBet_shouldNotAddBetToListOfBetsIfItIsAlreadyInTheList() throws Exception {
        // Arrange
        Bet bet = mock(Bet.class);

        // Act
        sut.placeBet(bet);
        sut.placeBet(bet);

        // Assert
        assertThat(sut.getAllBetsMade()).containsOnlyOnce(bet);
    }

    /**
     * @verifies returnTheSizeOfTheBetsList
     * @see BettingRound#numberOFBetsMade()
     */
    @Test
    public void numberOFBetsMade_shouldReturnTheSizeOfTheBetsList() {
        // Arrange
        Bet bet = mock(Bet.class);

        // Act
        sut.placeBet(bet);

        // Assert
        assertThat(sut.numberOFBetsMade()).isEqualTo(1);
    }

    /**
     * @verifies makeBettingRoundsWithSameBettingRoundIDLogicallyEqual
     * @see BettingRound#equals(Object)
     */
    @Test
    public void equals_shouldMakeBettingRoundsWithSameBettingRoundIDLogicallyEqual() {
        // Arrange
        BettingRound b = new BettingRound(bettingRoundID, betToken);

        // Assert
        org.assertj.core.api.Assertions.assertThat(sut).isEqualTo(b);
        assertThat(sut).hasSameHashCodeAs(b);
    }
}
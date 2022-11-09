package casino.cashier;

import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.BetID;
import casino.idfactory.CardID;
import gamblingauthoritiy.IBetLoggingAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.*;

/**
 * @author ciara
 */
public class CashierTest {
    private static IBetLoggingAuthority betLoggingAuthority;
    Cashier sut;

    @BeforeEach
    public void setUp() {
        betLoggingAuthority = mock(IBetLoggingAuthority.class);
        sut = new Cashier(betLoggingAuthority);
    }

    /**
     * @verifies createCashierAndAssignIBetLoggingAuthorityToLocalVariableAndInitialiseDistributedGamblerCards
     * @see Cashier#Cashier(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void Cashier_shouldCreateCashierAndAssignIBetLoggingAuthorityToLocalVariableAndInitialiseDistributedGamblerCards() {
        // Assert
        assertThat(sut.getBetLoggingAuthority()).isEqualTo(betLoggingAuthority);
        assertThat(sut.getDistributedGamblerCards()).isNotNull();
    }

    /**
     * @verifies throwIllegalArgumentExceptionIfIBetLoggingAuthorityIsNull
     * @see Cashier#Cashier(gamblingauthoritiy.IBetLoggingAuthority)
     */
    @Test
    public void Cashier_shouldThrowIllegalArgumentExceptionIfIBetLoggingAuthorityIsNull() {
        // Arrange, Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Cashier(null));
    }

    /**
     * @verifies createAndAssignIGamblerCardToAGamblerAndCallLogHandOutGamblingCardOnBetLoggingAuthority
     * @see Cashier#distributeGamblerCard()
     */
    @Test
    public void distributeGamblerCard_shouldCreateAndAssignIGamblerCardToAGamblerWithNoMoneyAndCallLogHandOutGamblingCardOnBetLoggingAuthority() {
        // Arrange & Act
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        CardID cardID = gamblerCard.getCardID();

        // Assert
        assertThat(sut.getDistributedGamblerCards().containsKey(gamblerCard)).isTrue();
        verify(betLoggingAuthority, times(1)).logHandOutGamblingCard(cardID);
        assertThat(sut.getDistributedGamblerCards().get(gamblerCard)).isEqualTo(0L);
    }

    /**
     * @verifies notAssignNullIGamblerCardToAGambler
     * @see Cashier#distributeGamblerCard()
     */
    @Test
    public void distributeGamblerCard_shouldNotAssignNullIGamblerCardToAGambler() {
        // Arrange & Act
        IGamblerCard gamblerCard = sut.distributeGamblerCard();

        // Assert
        assertThat(sut.getDistributedGamblerCards().containsKey(gamblerCard)).isTrue();
        assertThat(gamblerCard).isNotNull();
    }

    /**
     * @verifies callLogHandInGamblingCardOnBetLoggingAuthorityWithIGamblerCardsCardIDAndBetIDs
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldCallLogHandInGamblingCardOnBetLoggingAuthorityWithIGamblerCardsCardIDAndBetIDsAndRemoveBetIDsFromCard() {
        // Arrange
        CardID cardID = mock(CardID.class);
        BetID betID1 = mock(BetID.class);
        BetID betID2 = mock(BetID.class);
        Set<BetID> betIDs = new HashSet<>();
        betIDs.add(betID1);
        betIDs.add(betID2);
        IGamblerCard gamblerCard = mock(IGamblerCard.class);
        when(gamblerCard.getCardID()).thenReturn(cardID);
        when(gamblerCard.returnBetIDsAndClearCard()).thenReturn(betIDs);

        // Act
        sut.returnGamblerCard(gamblerCard);

        // Assert
        verify(betLoggingAuthority).logHandInGamblingCard(cardID, betIDs);
        verify(gamblerCard).returnBetIDsAndClearCard();
    }

    /**
     * @verifies throwIllegalArgumentExceptionIfIGamblerCardIsNullOrNotDistributed
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldThrowIllegalArgumentExceptionIfIGamblerCardIsNullOrNotDistributed() {
        // Arrange, Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.returnGamblerCard(null));
    }

    /**
     * @verifies removeIGamblerCardFromListOfDistributedGamblerCards
     * @see Cashier#returnGamblerCard(IGamblerCard)
     */
    @Test
    public void returnGamblerCard_shouldRemoveIGamblerCardFromListOfDistributedGamblerCards() {
        // Arrange
        IGamblerCard gamblerCard = sut.distributeGamblerCard();

        // Act
        sut.returnGamblerCard(gamblerCard);

        // Assert
        assertThat(sut.getDistributedGamblerCards().containsKey(gamblerCard)).isFalse();
    }

    /**
     * @verifies returnTrueIfIGamblerCardMoneyIsGreaterOrEqualToBetMoneyAmountAndIsInDistributedGamblerCards
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldReturnTrueIfIGamblerCardMoneyIsGreaterOrEqualToBetMoneyAmountAndIsInDistributedGamblerCards() throws InvalidAmountException, BetNotExceptedException {
        // Arrange
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        Bet bet = mock(Bet.class);
        MoneyAmount betMoneyAmount = mock(MoneyAmount.class);
        when(betMoneyAmount.getAmountInCents()).thenReturn(100L);
        when(bet.getMoneyAmount()).thenReturn(betMoneyAmount);
        MoneyAmount cardMoneyAmount = mock(MoneyAmount.class);
        when(cardMoneyAmount.getAmountInCents()).thenReturn(100L);
        sut.addAmount(gamblerCard, cardMoneyAmount);

        // Act & Assert
        assertThat(sut.checkIfBetIsValid(gamblerCard, bet)).isTrue();
        verify(bet, times(1)).getMoneyAmount();
        verify(betMoneyAmount, times(1)).getAmountInCents();
    }

    /**
     * @verifies throwBetNotExceptedExceptionIfIGamblerCardMoneyIsLessThanBetMoneyAmountOrIGamblerCardIsNotDistributed
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldThrowBetNotExceptedExceptionIfIGamblerCardMoneyIsLessThanBetMoneyAmountOrIGamblerCardIsNotDistributed() throws InvalidAmountException {
        // Arrange
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        IGamblerCard notDistributedCard = mock(GamblerCard.class);
        Bet bet = mock(Bet.class);
        MoneyAmount betMoneyAmount = mock(MoneyAmount.class);
        when(betMoneyAmount.getAmountInCents()).thenReturn(100L);
        when(bet.getMoneyAmount()).thenReturn(betMoneyAmount);
        MoneyAmount cardMoneyAmount = mock(MoneyAmount.class);
        when(cardMoneyAmount.getAmountInCents()).thenReturn(10L);
        sut.addAmount(gamblerCard, cardMoneyAmount);

        // Act & Assert
        assertThatExceptionOfType(BetNotExceptedException.class).isThrownBy(() -> sut.checkIfBetIsValid(gamblerCard, bet));
        assertThat(sut.getDistributedGamblerCards().containsKey(notDistributedCard)).isFalse();
        assertThatExceptionOfType(BetNotExceptedException.class).isThrownBy(() -> sut.checkIfBetIsValid(notDistributedCard, bet));
    }

    /**
     * @verifies subtractIGamblerCardAmountByBetMoneyAmountValueInCentsInDistributedGamblerCardsIfValid
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldSubtractIGamblerCardAmountByBetMoneyAmountValueInCentsInDistributedGamblerCardsIfValid() throws InvalidAmountException, BetNotExceptedException {
        // Arrange
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        Bet bet = mock(Bet.class);
        long betMoneyAmountInCents = 100L;
        MoneyAmount betMoneyAmount = mock(MoneyAmount.class);
        when(betMoneyAmount.getAmountInCents()).thenReturn(betMoneyAmountInCents);
        when(bet.getMoneyAmount()).thenReturn(betMoneyAmount);
        long cardMoneyAmountInCents = 1000L;
        MoneyAmount cardMoneyAmount = mock(MoneyAmount.class);
        when(cardMoneyAmount.getAmountInCents()).thenReturn(cardMoneyAmountInCents);
        sut.addAmount(gamblerCard, cardMoneyAmount);

        // Act &  Assert
        assertThat(sut.checkIfBetIsValid(gamblerCard, bet)).isTrue();
        assertThat(sut.getDistributedGamblerCards().get(gamblerCard)).isEqualTo(cardMoneyAmountInCents - betMoneyAmountInCents);
    }

    /**
     * @verifies throwsIllegalArgumentExceptionIfIGamblerCardOrBetIsNull
     * @see Cashier#checkIfBetIsValid(IGamblerCard, casino.bet.Bet)
     */
    @Test
    public void checkIfBetIsValid_shouldThrowsIllegalArgumentExceptionIfIGamblerCardOrBetIsNull() {
        // Arrange
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        Bet bet = mock(Bet.class);
        long betMoneyAmountInCents = 100L;
        MoneyAmount betMoneyAmount = mock(MoneyAmount.class);
        when(betMoneyAmount.getAmountInCents()).thenReturn(betMoneyAmountInCents);
        when(bet.getMoneyAmount()).thenReturn(betMoneyAmount);

        // Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.checkIfBetIsValid(null, bet));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.checkIfBetIsValid(gamblerCard, null));
    }

    /**
     * @verifies addAmountToIGamblerCardInDistributedGamblerCards
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    public void addAmount_shouldAddAmountToIGamblerCardInDistributedGamblerCards() throws InvalidAmountException {
        // Arrange
        IGamblerCard gamblerCard = sut.distributeGamblerCard();
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        long amountInCents = 100L;
        when(moneyAmount.getAmountInCents()).thenReturn(amountInCents);

        // Act
        sut.addAmount(gamblerCard, moneyAmount);

        // Assert
        assertThat(sut.getDistributedGamblerCards().get(gamblerCard)).isEqualTo(amountInCents);
        verify(moneyAmount).getAmountInCents();
    }

    /**
     * @verifies throwsIllegalArgumentExceptionIfIGamblerCardIsNullOrNotDistributed
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    public void addAmount_shouldThrowsIllegalArgumentExceptionIfIGamblerCardIsNullOrNotDistributed() {
        // Arrange
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        IGamblerCard gamblerCard = mock(GamblerCard.class);

        // Act, & Assert
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.addAmount(null, moneyAmount));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> sut.addAmount(gamblerCard, moneyAmount));
    }

    /**
     * @verifies throwInvalidAmountExceptionWhenMoneyAmountContainsNegativeValueOrIsNull
     * @see Cashier#addAmount(IGamblerCard, casino.bet.MoneyAmount)
     */
    @Test
    public void addAmount_shouldThrowInvalidAmountExceptionWhenMoneyAmountContainsNegativeValueOrIsNull() {
        // Arrange
        IGamblerCard distributedCard = sut.distributeGamblerCard();
        MoneyAmount moneyAmount = mock(MoneyAmount.class);
        when(moneyAmount.getAmountInCents()).thenReturn(-1L);

        // Act & Assert
        assertThatExceptionOfType(InvalidAmountException.class).isThrownBy(() -> sut.addAmount(distributedCard, moneyAmount));
        assertThatExceptionOfType(InvalidAmountException.class).isThrownBy(() -> sut.addAmount(distributedCard, null));
    }
}
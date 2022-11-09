package casino.cashier;


import casino.bet.Bet;
import casino.bet.MoneyAmount;
import casino.idfactory.CardID;
import gamblingauthoritiy.IBetLoggingAuthority;

import java.util.HashMap;
import java.util.Map;
/**
 * @author ciara
 */
public class Cashier implements ICashier {
    private Map<IGamblerCard, Long> distributedGamblerCards;
    private IBetLoggingAuthority betLoggingAuthority;

    /**
     * Creates a Cashier with a IBetLoggingAuthority
     *
     * @should createCashierAndAssignIBetLoggingAuthorityToLocalVariableAndInitialiseDistributedGamblerCards
     * @should throwIllegalArgumentExceptionIfIBetLoggingAuthorityIsNull
     *
     * @param bettingLoggingAuthority is the IBetLoggingAuthority the Cashier can communicate with
     * @throws IllegalArgumentException if the IBetLoggingAuthority is null
     */
    public Cashier(IBetLoggingAuthority bettingLoggingAuthority) throws IllegalArgumentException {
        if (bettingLoggingAuthority != null) {
            this.betLoggingAuthority = bettingLoggingAuthority;
            this.distributedGamblerCards = new HashMap<IGamblerCard, Long>();
        } else {
            throw new IllegalArgumentException("IBetLoggingAuthority cannot be null.");
        }
    }

    /**
     * Cards are distributed by a Cashier to a gambler
     * bankteller keeps track of which cards are handed out.
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @should createAndAssignIGamblerCardToAGamblerWithNoMoneyAndCallLogHandOutGamblingCardOnBetLoggingAuthority
     * @should notAssignNullIGamblerCardToAGambler
     *
     * @return the distributed IGamblerCard
     */
    @Override
    public IGamblerCard distributeGamblerCard() {
        IGamblerCard gamblerCard = new GamblerCard();
        this.distributedGamblerCards.put(gamblerCard, 0L);
        CardID cardID = gamblerCard.getCardID();
        this.betLoggingAuthority.logHandOutGamblingCard(cardID);
        return gamblerCard;
    }

    /**
     * When handing in the card at a Bank teller, all betID’s on it are logged.
     * The total amount of money credit is physically handed to the gambler,
     * and card is removed from the list of distributed gambler cards.
     * The stored betID’s on the card are also removed.
     * Note: also use the appropriate required methods from the gambling authority API
     *
     * @should callLogHandInGamblingCardOnBetLoggingAuthorityWithIGamblerCardsCardIDAndBetIDsAndRemoveBetIDsFromCard
     * @should removeIGamblerCardFromListOfDistributedGamblerCards
     * @should throwIllegalArgumentExceptionIfIGamblerCardIsNullOrNotDistributed
     *
     * @param card is the IGamblerCard to return
     * @throws IllegalArgumentException if the IGamblerCard to return is null
     */
    @Override
    public void returnGamblerCard(IGamblerCard card) throws IllegalArgumentException {
        if (card != null) {
            betLoggingAuthority.logHandInGamblingCard(card.getCardID(), card.returnBetIDsAndClearCard());
            distributedGamblerCards.remove(card);
        } else {
            throw new IllegalArgumentException("IGamblerCard to return cannot be null.");
        }
    }

    /**
     * check if Bet made with the playercard is possible. this is based on the amount related
     * to the card, and the amount made in the bet.
     *
     * if the bet is valid, the amount of the bet is subtracted from the amount belonging to
     * the card.
     *
     * @should returnTrueIfIGamblerCardMoneyIsGreaterOrEqualToBetMoneyAmountAndIsInDistributedGamblerCards
     * @should throwBetNotExceptedExceptionIfIGamblerCardMoneyIsLessThanBetMoneyAmountOrIGamblerCardIsNotDistributed
     * @should subtractIGamblerCardAmountByBetMoneyAmountValueInCentsInDistributedGamblerCardsIfValid
     * @should throwsIllegalArgumentExceptionIfIGamblerCardOrBetIsNull
     *
     * @param card is the IGamblerCard used to make a bet
     * @param betToCheck is the bet that the IGamblerCard wants to make
     * @return true if the IGamblerCard's corresponding MoneyAmount is greater than the betToCheck's MoneyAmount
     * @throws BetNotExceptedException if bet amount is invalid
     * @throws IllegalArgumentException if the bet or card is null
     */
    @Override
    public boolean checkIfBetIsValid(IGamblerCard card, Bet betToCheck) throws BetNotExceptedException, IllegalArgumentException {
        if (card != null) {
            if (betToCheck != null) {
                if (distributedGamblerCards.containsKey(card)) {
                    long betAmountInCents = betToCheck.getMoneyAmount().getAmountInCents();
                    if (betAmountInCents <= distributedGamblerCards.get(card)) {
                        Long newAmountInCents = distributedGamblerCards.get(card) - betAmountInCents;
                        distributedGamblerCards.put(card, newAmountInCents);
                        return true;
                    } else {
                        throw new BetNotExceptedException("Bet is not valid. Bet money amount is greater than the money amount on the card.");
                    }
                } else {
                    throw new BetNotExceptedException("It is not possible to make a bet with a card that is not yet distributed.");
                }
            } else {
                throw new IllegalArgumentException("Bet to check cannot be null.");
            }

        } else {
            throw new IllegalArgumentException("Card cannot be null.");
        }
    }

    /**
     * add amount to the players card.
     *
     * @should addAmountToIGamblerCardInDistributedGamblerCards
     * @should throwsIllegalArgumentExceptionIfIGamblerCardIsNullOrNotDistributed
     * @should throwInvalidAmountExceptionWhenMoneyAmountContainsNegativeValueOrIsNull
     *
     * @param card card to add amount to
     * @param amount amount to add to card
     * @throws InvalidAmountException when MoneyAmount contains a negative value or is null
     * @throws IllegalArgumentException if a card is not yet distributed or a card is null
     */
    @Override
    public void addAmount(IGamblerCard card, MoneyAmount amount) throws InvalidAmountException, IllegalArgumentException {
        if (card != null) {
            if (distributedGamblerCards.containsKey(card)) {
                if (amount != null) {
                    long amountToAdd = amount.getAmountInCents();
                    if (amountToAdd > 0) {
                        Long newAmountInCents = distributedGamblerCards.get(card) + amountToAdd;
                        distributedGamblerCards.put(card, newAmountInCents);
                    } else {
                        throw new InvalidAmountException("It is not possible to add a negative amount to a card.");
                    }
                } else {
                    throw new InvalidAmountException("It is not possible to add a null amount to a card.");
                }
            } else {
                throw new IllegalArgumentException("It is not possible to add an amount to a card that is not yet distributed.");
            }
        } else {
          throw new IllegalArgumentException("It is not possible to add an amount to a null card.");
        }
    }

    // BELOW ARE GETTERS CREATED WITH INTELLIJ SO THEY DO NOT NEED TO BE TESTED
    public IBetLoggingAuthority getBetLoggingAuthority() {
        return betLoggingAuthority;
    }

    public Map<IGamblerCard, Long> getDistributedGamblerCards() {
        return distributedGamblerCards;
    }
}

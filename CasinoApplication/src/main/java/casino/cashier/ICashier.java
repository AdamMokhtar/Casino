package casino.cashier;

import casino.bet.Bet;
import casino.bet.MoneyAmount;

public interface ICashier {
    /**
     * Cards are distributed by a Cashier to a gambler
     * bankteller keeps track of which cards are handed out.
     * Note: also use the appropiate required methods from the gambling authority API
     *
     * @return
     */
    IGamblerCard distributeGamblerCard();

    /**
     * When handing in the card at a Bank teller, all betID’s on it are logged.
     * The total amount of money credit is physically handed to the gambler,
     * and card is removed from the list of distributed gambler cards.
     * The stored betID’s on the card are also removed.
     * Note: also use the appropriate required methods from the gambling authority API
      * @param card
     */
    void returnGamblerCard(IGamblerCard card);

    /**
     * check if Bet made with the playercard is possible. this is based on the amount related
     * to the card, and the amount made in the bet.
     *
     * if the bet is valid, the amount of the bet is subtracted from the amount belonging to
     * the card.
     *
     * @param card
     * @param betToCheck
     * @return
     * @throws BetNotExceptedException if bet amount is invalid
     */
    boolean checkIfBetIsValid(IGamblerCard card, Bet betToCheck) throws BetNotExceptedException;

    /**
     * add amount to the players card.
     *
     * @param card card to add amount to
     * @param amount amount to add to card
     * @throws InvalidAmountException when MoneyAmount contains a negative value or is null
     */
    void addAmount(IGamblerCard card, MoneyAmount amount) throws InvalidAmountException;
}

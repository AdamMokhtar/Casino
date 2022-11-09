package casino.bet;

import casino.idfactory.BetID;

import java.util.Objects;

/**
 * immutable class.
 * keeps unique betID and moneyamount in the bet.
 */
public class Bet {
    private BetID betID;
    private MoneyAmount moneyAmount;

    public Bet(BetID betID, MoneyAmount moneyAmount) {
        this.betID = betID;
        this.moneyAmount = moneyAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bet bet = (Bet) o;
        return betID == bet.betID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(betID);
    }

    public MoneyAmount getMoneyAmount() {
        return moneyAmount;
    }

    public BetID getBetID() {
        return betID;
    }

}

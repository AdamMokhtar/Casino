package casino.bet;

import java.util.Currency;
import java.util.Objects;

/**
 * immutable object representing an amount of money.
 * For demo purposes: hardocded USD
 */
public class MoneyAmount {
    private long amountInCents;
    private Currency currency;

    public MoneyAmount(long amountInCents) {
        this.amountInCents = amountInCents;
        this.currency = Currency.getInstance("USD");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoneyAmount moneyAmount = (MoneyAmount) o;
        return amountInCents == moneyAmount.getAmountInCents() && currency == moneyAmount.getCurrency();
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountInCents, currency);
    }

    public Currency getCurrency() {
        return currency;
    }
    public long getAmountInCents() {
        return amountInCents;
    }

}

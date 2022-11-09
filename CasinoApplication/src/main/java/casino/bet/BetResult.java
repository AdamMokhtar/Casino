package casino.bet;

/**
 * Immutable class.
 * contains the Bet which won, together with the amount won.
 */
public class BetResult {
    private Bet winningBet;  // original bet which won.
    private MoneyAmount amountWon;  // amount won.

    public BetResult(Bet winningBet, MoneyAmount amountWon) {
        this.winningBet = winningBet;
        this.amountWon = amountWon;
    }

    public Bet getWinningBet() {
        return winningBet;
    }

    public MoneyAmount getAmountWon() {
        return amountWon;
    }
}


package casino;

import gamblingauthoritiy.BettingAuthority;
import casino.cashier.*;
import casino.bet.Bet;
import casino.game.IGame;

import java.util.HashMap;
import java.util.Map;

/**
 * class representing a casino.
 *
 * A casino consists of:
 * 1 Cashier
 * 1 Betting Authority API object
 * a Set of IGames in the casino
 *
 * no other methods are allowed to be added.
 *
 *
 * <p>
 * DOES NOT HAVE TO BE TESTED !!!
 */
public final class Casino  {
    private final BettingAuthority bettingAuthority;    // object which is forced to be used by the gambling authority.
    private final ICashier teller;                      // cashier of the casino
    private Map<String, IGame> games;                   // map of games in this casino.


    public Casino() {
        this.bettingAuthority = new BettingAuthority();
        this.teller = new Cashier(this.bettingAuthority.getLoggingAuthority());
        this.games = new HashMap<>();
    }

    /**
     * method to add a named game to the casino
     * <p>
     *
     * @param gameName
     * @param gameToAdd
     */
    public void addGame(String gameName, IGame gameToAdd) {
        if (gameName != null && gameToAdd != null) {
            this.games.put(gameName, gameToAdd);
        }
    }



}
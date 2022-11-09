package casino.gamingmachine;

import casino.cashier.Cashier;
import casino.game.IGame;
import gamblingauthoritiy.BettingAuthority;

public abstract  class AbstractGamingMachine implements IGamingMachine {


    /**
     * @should throwIllegalArgumentExceptionIfOneOfTheParametersIsNull
     * @should initialiseGameAndCashierWithPassedParameters
     *
     */
    public AbstractGamingMachine(IGame game, Cashier cashier) throws IllegalArgumentException{


    }

}

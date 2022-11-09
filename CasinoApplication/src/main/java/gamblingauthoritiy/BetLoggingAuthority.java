package gamblingauthoritiy;

import casino.bet.Bet;
import casino.bet.BetResult;
import casino.game.IBettingRound;
import casino.idfactory.BetID;
import casino.idfactory.BettingRoundID;
import casino.idfactory.GamingMachineID;
import casino.idfactory.GeneralID;

import java.util.Set;

/**
 * See interface for described behaviour
 *
 */
public class BetLoggingAuthority implements IBetLoggingAuthority {

 
    @Override
    public void logHandOutGamblingCard(GeneralID card){
        // timestamp and log
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION

    }

    @Override
    public void logHandInGamblingCard(GeneralID card, Set<BetID> betsMade){
        // timestamp and log
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION

    }



    @Override
    public void logStartBettingRound(IBettingRound startingBettingRound){
        // timestamp and log
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION

    }


    @Override
    public void logAddAcceptedBet(Bet acceptedBet, BettingRoundID bettingRoundID, GamingMachineID gamingMachineID){
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION

    }

    @Override
    public void logEndBettingRound(IBettingRound endedBettingRound, BetResult result) {
        // timestamp and log
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION

    }



}

package gamblingauthoritiy;


import casino.idfactory.BettingRoundID;

/**
 * See interface for described behaviour
 *
 */
public class BetTokenAuthority implements IBetTokenAuthority {



    public BetToken getBetToken(BettingRoundID bettingRoundID){
        // create token
        BetToken bettingBetToken = new BetToken(bettingRoundID);
        // timestamp and log
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION

        // return token
        return bettingBetToken;
    }


    public Integer getRandomInteger(BetToken betToken){
        // NOT RELEVANT TO KNOW THE IMPLEMENTATION
        return null;  // DUMMY IMPLEMENTATION
    }
}

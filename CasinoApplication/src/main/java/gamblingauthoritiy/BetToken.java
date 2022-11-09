package gamblingauthoritiy;


import casino.idfactory.BettingRoundID;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 *  immutable class consisting of a unique ID + creationTime combination,
 *  combined with the bettingroundID it relates to.
 *
 *  This class is used for logging purposes of the bettingauthority.
 */
public class BetToken implements Comparable {
    private BettingRoundID bettingRoundID;
    private UUID uniqueTokenID;
    private Instant creationTime;


    public BetToken(BettingRoundID bettingRoundID) {
        this.bettingRoundID = bettingRoundID;
        this.uniqueTokenID = UUID.randomUUID();
        this.creationTime = Instant.now();
    }

    public BettingRoundID getBettingRoundID() {
        return bettingRoundID;
    }

    public UUID getUniqueTokenID() {
        return uniqueTokenID;
    }

    public Instant getCreationTime() {
        return creationTime;
    }


    @Override
    public int compareTo(Object o) {
            // throw exceptions when necessary
            if(o == null){
                throw new NullPointerException();
            }
            if(! (o instanceof BetToken)){
                throw new ClassCastException();
            }
            // comparing logical attributes
            BetToken token = (BetToken)o;
            int returnvalue = this.uniqueTokenID.compareTo(token.uniqueTokenID);
            if(returnvalue == 0){
                returnvalue = this.creationTime.compareTo(token.creationTime);
            }
            return returnvalue;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetToken betToken = (BetToken) o;
        return uniqueTokenID.equals(betToken.uniqueTokenID) &&
                creationTime.equals(betToken.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueTokenID, creationTime);
    }
}

package casino.idfactory;

import casino.game.BettingRound;

/**
 * @author adam
 */
public class BettingRoundID extends GeneralID implements Comparable {

    /**
     * to check if two objects are equal by using the hashcode and if they are an instance of BettingRoundID
     *
     * @should return0IfTwoBettingRoundIDsAreEqualBasedOnHashCodeAndObjectType
     * @should return1IfTwoObjectsForBettingRoundIDAreNotEqualBasedOnHashCodeAndObjectType
     * @param o object used for testing
     * @return one if two objects are equal and zero if not
     */
    @Override
    public int compareTo(Object o) {
        if (o.equals(this)) {
            if (o instanceof BettingRoundID) {
                return 0;
            }
        }
        return 1;

    }
}

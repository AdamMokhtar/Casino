package casino.idfactory;


import casino.bet.Bet;

/**
 * @author ciara
 */
public class BetID extends GeneralID implements Comparable {
    /**
     * this method compares two objects to see if they are equal
     * two objects are equal if they have the same hashcode and
     * are both an instance of the BetID class
     *
     * @should return0IfTwoObjectsAreEqualBasedOnHashCodeAndObjectType
     * @should return1IfTwoObjectsAreNotEqualBasedOnHashCodeAndObjectType
     *
     * @param o is the object to test with this
     * @return 0 if the two objects are equal and zero if not
     */
    @Override
    public int compareTo(Object o) {
        if (o.equals(this)) {
            if (o instanceof BetID) {
                return 0;
            }
        }
        return 1;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

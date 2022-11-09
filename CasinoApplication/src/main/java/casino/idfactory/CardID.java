package casino.idfactory;

/**
 * @author nagi
 */
public class CardID extends GeneralID implements Comparable {
    /**
     * @should returnZeroInCaseTheTwoObjectsAreEqual
     * @should returnOneInCaseTheTwoObjectsAreNotEqual
     *
     * @param o object that will use to compare with this
     * @return 0 if this and the object are equal and 1 if they are not.
     */
    @Override
    public int compareTo(Object o) {
        if (o.equals(this)) {
            if (o instanceof CardID) {
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


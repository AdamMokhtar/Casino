package casino.idfactory;

/**
 * @author adam
 */
public class GamingMachineID extends GeneralID implements Comparable{


    /**
     * to check if two objects are equal by using the hashcode and if they are an instance of GamingMachineID
     *
     * @should return0IfTwoGamingMachineIDsAreEqualBasedOnHashCodeAndObjectType
     * @should return1IfTwoObjectsForGamingMachineIDAreNotEqualBasedOnHashCodeAndObjectType
     * @param o object used for testing
     * @return one if two objects are equal and zero if not
     */
    @Override
    public int compareTo(Object o) {
        if (o.equals(this)) {
            if (o instanceof GamingMachineID) {
                return 0;
            }
        }
        return 1;
    }
}

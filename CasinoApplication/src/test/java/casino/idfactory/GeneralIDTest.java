package casino.idfactory;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author adam
 */
public class GeneralIDTest {

    /**
     * @verifies assignTheNewRandomValueAndTheTimeStampToTheLocalVariables
     * @see GeneralID#GeneralID()
     */
    @Test
    public void GeneralID_shouldAssignTheNewRandomValueAndTheTimeStampToTheLocalVariables() throws Exception {
        // arrange and act
        GeneralID sut = new GeneralID() {};
        // assert
        assertThat(sut.getTimeStamp()).isNotNull();
        assertThat(sut.getUniqueID()).isNotNull();
    }

    /**
     * @verifies considerTheSameUniqueIdAndTimeStampAreLogicallyEqual
     * @see GeneralID#equals(Object)
     */
    @Test
    public void equals_shouldConsiderTheSameUniqueIdAndTimeStampAreLogicallyEqual() throws Exception {
        // arrange and act
        GeneralID generalID1 = new GeneralID() {};
        GeneralID generalID2 = generalID1;

        // assert
        assertThat(generalID1).isEqualTo(generalID2);
        assertThat(generalID1).hasSameHashCodeAs(generalID2);
    }
}
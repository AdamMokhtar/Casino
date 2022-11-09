package casino.idfactory;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author nagi
 */
public class IDFactoryTest {

    /**
     * @verifies returnBetIdObjectInCaseTheInsertParameterIsBetId
     * @see IDFactory#generateID(String)
     */
    @Test
    public void generateID_shouldReturnBetIdObjectInCaseTheInsertParameterIsBetId()  {

        //arrange//act//assert
        assertThat(IDFactory.generateID("BETID")).isInstanceOf(BetID.class);
    }

    /**
     * @verifies returnBettingroundIdObjectInCaseTheInsertParameterIsBettingroundId
     * @see IDFactory#generateID(String)
     */
    @Test

    public void generateID_shouldReturnBettingroundIdObjectInCaseTheInsertParameterIsBettingroundId()  {
        //arrange//act//assert
        assertThat(IDFactory.generateID("BETTINGROUNDID")).isInstanceOf(BettingRoundID.class);
    }

    /**
     * @verifies returnCardIdObjectInCaseTheInsertParameterIsCardId
     * @see IDFactory#generateID(String)
     */
    @Test
    public void generateID_shouldReturnCardIdObjectInCaseTheInsertParameterIsCardId()  {

        //arrange//act//assert
        assertThat(IDFactory.generateID("CARDID")).isInstanceOf(CardID.class);
    }

    /**
     * @verifies returnGamingMachineIdObjectInCaseTheInsertParameterIsGamingMachineId
     * @see IDFactory#generateID(String)
     */
    @Test
    public void generateID_shouldReturnGamingMachineIdObjectInCaseTheInsertParameterIsGamingMachineId()  {
        //arrange//act//assert
        assertThat(IDFactory.generateID("GAMINGMACHINEID")).isInstanceOf(GamingMachineID.class);
    }

    /**
     * @verifies returnNullInCaseTheInsertParameterIsNull
     * @see IDFactory#generateID(String)
     */
    @Test
    public void generateID_shouldReturnNullInCaseTheInsertParameterIsNull()  {
        assertThat(IDFactory.generateID(null)).isEqualTo(null);

    }

    /**
     * @verifies returnNullWhenUnknownInsertType
     * @see IDFactory#generateID(String)
     */

    @ParameterizedTest
    @ValueSource(strings = {"","CLASSID","PWESONID", "MANAGERID"})
    public void generateID_shouldReturnNullWhenUnknownInsertType(String value)  {
        assertThat(IDFactory.generateID(value)).isEqualTo(null);
    }
}
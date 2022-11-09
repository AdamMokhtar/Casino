package casino.idfactory;

import casino.bet.Bet;
//import com.sun.tools.javac.jvm.Gen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


public class IDsTest {
    /**
     * @author ciara
     * @verifies return0IfTwoObjectsAreEqual
     * @see BetID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturn0IfTwoObjectsAreEqualBasedOnHashCodeAndObjectType() {
        // Arrange
        BetID a = new BetID();
        BetID b = a;

        // Assert
        assertThat(a.compareTo(b)).isEqualTo(0);
        assertThat(a).hasSameHashCodeAs(b);
    }

    /**
     * @author ciara
     * @verifies return1IfTwoObjectsAreNotEqual
     * @see BetID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturn1IfTwoObjectsAreNotEqualBasedOnHashCodeAndObjectType() {
        // Arrange
        BetID a = new BetID();
        BetID b = new BetID();
        BettingRoundID c = new BettingRoundID();

        // Assert
        assertThat(a.compareTo(b)).isEqualTo(1);
        assertThat(a).doesNotHaveSameHashCodeAs(b);
        assertThat(a.compareTo(c)).isEqualTo(1);
        assertThat(a).doesNotHaveSameHashCodeAs(c);
    }

    /**
     * @author nagi
     * @verifies returnZeroInCaseTheTwoObjectsAreEqual
     * @see CardID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturnZeroInCaseTheTwoObjectsAreEqual() throws Exception {

        //arrange
        CardID cardID1 = new CardID();
        CardID cardID2 = cardID1;
        //act//assert
        assertThat(cardID1.compareTo(cardID2)).isEqualTo(0);
        assertThat(cardID1).hasSameHashCodeAs(cardID2);


    }

    /**
     * @author nagi
     * @verifies returnOneInCaseTheTwoObjectsAreNotEqual
     * @see CardID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturnOneInCaseTheTwoObjectsAreNotEqual() throws Exception {

        //arrange
        CardID cardIDA = new CardID();
        CardID cardIDB = new CardID();
        BetID betID = new BetID();
        //act//assert
        assertThat(cardIDA.compareTo(cardIDB)).isEqualTo(1);
        assertThat(cardIDA).doesNotHaveSameHashCodeAs(cardIDB);
        assertThat(cardIDA.compareTo(betID)).isEqualTo(1);
        assertThat(cardIDA).doesNotHaveSameHashCodeAs(betID);
    }
    /**
     * @author adam
     * @verifies return0IfTwoBettingRoundIDsAreEqualBasedOnHashCodeAndObjectType
     * @see BettingRoundID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturn0IfTwoBettingRoundIDsAreEqualBasedOnHashCodeAndObjectType() throws Exception {
        // Arrange
        BettingRoundID bettingRoundID1 = new BettingRoundID();
        BettingRoundID bettingRoundID2 = bettingRoundID1;

        // Assert
        assertThat(bettingRoundID1.compareTo(bettingRoundID2)).isEqualTo(0);
        assertThat(bettingRoundID1).hasSameHashCodeAs(bettingRoundID2);
    }

    /**
     * @author adam
     * @verifies return1IfTwoObjectsForBettingRoundIDAreNotEqualBasedOnHashCodeAndObjectType
     * @see BettingRoundID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturn1IfTwoObjectsForBettingRoundIDAreNotEqualBasedOnHashCodeAndObjectType() throws Exception {
        // Arrange
        BettingRoundID bettingRoundID1 = new BettingRoundID();
        BettingRoundID bettingRoundID2 = new BettingRoundID();
        GamingMachineID gamingMachineID = new GamingMachineID();

        // Assert
        assertThat(bettingRoundID1.compareTo(bettingRoundID2)).isEqualTo(1);
        assertThat(bettingRoundID1).doesNotHaveSameHashCodeAs(bettingRoundID2);
        assertThat(bettingRoundID1.compareTo(gamingMachineID)).isEqualTo(1);
        assertThat(bettingRoundID1).doesNotHaveSameHashCodeAs(gamingMachineID);
    }

    /**
     * @author adam
     * @verifies return0IfTwoGamingMachineIDsAreEqualBasedOnHashCodeAndObjectType
     * @see GamingMachineID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturn0IfTwoGamingMachineIDsAreEqualBasedOnHashCodeAndObjectType() throws Exception {
        // Arrange
        GamingMachineID gamingMachineID1 = new GamingMachineID();
        GamingMachineID gamingMachineID2 = gamingMachineID1;

        // Assert
        assertThat(gamingMachineID1.compareTo(gamingMachineID2)).isEqualTo(0);
        assertThat(gamingMachineID1).hasSameHashCodeAs(gamingMachineID2);
    }

    /**
     * @author adam
     * @verifies return1IfTwoObjectsForGamingMachineIDAreNotEqualBasedOnHashCodeAndObjectType
     * @see GamingMachineID#compareTo(Object)
     */
    @Test
    public void compareTo_shouldReturn1IfTwoObjectsForGamingMachineIDAreNotEqualBasedOnHashCodeAndObjectType() throws Exception {
        // Arrange
        GamingMachineID gamingMachineID1 = new GamingMachineID();
        GamingMachineID gamingMachineID2 = new GamingMachineID();
        BettingRoundID bettingRoundID = new BettingRoundID();

        // Assert
        assertThat(gamingMachineID1.compareTo(gamingMachineID2)).isEqualTo(1);
        assertThat(gamingMachineID1).doesNotHaveSameHashCodeAs(gamingMachineID2);
        assertThat(gamingMachineID1.compareTo(bettingRoundID)).isEqualTo(1);
        assertThat(gamingMachineID1).doesNotHaveSameHashCodeAs(bettingRoundID);
    }

}

package casino.cashier;

import casino.game.DefaultGame;
import casino.idfactory.BetID;
import casino.idfactory.BettingRoundID;
import casino.idfactory.CardID;
import casino.idfactory.IDFactory;
import gamblingauthoritiy.BettingAuthority;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author adam
 */
public class GamblerCardTest {
    private GamblerCard sut;

    @BeforeEach
    public void setUp() {
        sut = new GamblerCard();
    }

    /**
     * @verifies initialiseCardIDAttributeWithReturnedCardIDObject
     * @see GamblerCard#GamblerCard()
     */
    @Test
    public void GamblerCard_shouldInitialiseCardIDAttributeWithReturnedCardIDObject() throws Exception {
        // assert
        assertThat(sut.getCardID()).isNotNull();
    }

    /**
     * @verifies returnAllBetIDsAddedToTheSetOfBetIDs
     * @see GamblerCard#returnBetIDs()
     */
    @Test
    public void returnBetIDs_shouldReturnAllBetIDsAddedToTheSetOfBetIDs() throws Exception {
        // arrange
        Set<BetID> betIDs = new HashSet<>();
        // act
        BetID betID1 = sut.generateNewBetID();
        betIDs.add(betID1);
        BetID betID2 = sut.generateNewBetID();
        betIDs.add(betID2);
        // assert
        assertThat(sut.returnBetIDs()).isEqualTo(betIDs);
    }

    /**
     * @verifies returnAnEmptySetWhenNoBetIDsWereAddedToTheSetOfBetIDs
     * @see GamblerCard#returnBetIDs()
     */
    @Test
    public void returnBetIDs_shouldReturnAnEmptySetWhenNoBetIDsWereAddedToTheSetOfBetIDs() throws Exception {
        // assert
        assertThat(sut.returnBetIDs().size()).isEqualTo(0);
    }

    /**
     * @verifies returnAllBetIDsAddedToTheSetOfBetIDs
     * @see GamblerCard#returnBetIDsAndClearCard()
     */
    @Test
    public void returnBetIDsAndClearCard_shouldReturnAllBetIDsAddedToTheSetOfBetIDs() throws Exception {
        // arrange
        Set<BetID> betIDs = new HashSet<>();
        // act
        BetID betID1 = sut.generateNewBetID();
        betIDs.add(betID1);
        BetID betID2 = sut.generateNewBetID();
        betIDs.add(betID2);
        // assert
        assertThat(sut.returnBetIDsAndClearCard()).isEqualTo(betIDs);
    }

    /**
     * @verifies returnAnEmptySetWhenNoBetIDsWereAddedToTheSetOfBetIDs
     * @see GamblerCard#returnBetIDsAndClearCard()
     */
    @Test
    public void returnBetIDsAndClearCard_shouldReturnAnEmptySetWhenNoBetIDsWereAddedToTheSetOfBetIDs() throws Exception {
        // assert
        assertThat(sut.returnBetIDsAndClearCard().size()).isEqualTo(0);
    }

    /**
     * @verifies removeAllBetIDsFromTheSetOfBetIDs
     * @see GamblerCard#returnBetIDsAndClearCard()
     */
    @Test
    public void returnBetIDsAndClearCard_shouldRemoveAllBetIDsFromTheSetOfBetIDs() throws Exception {
        // act
        sut.generateNewBetID();
        sut.generateNewBetID();
        sut.returnBetIDsAndClearCard();
        // assert
        assertThat(sut.getNumberOfBetIDs()).isEqualTo(0);
    }

    /**
     * @verifies addTheNewGeneratedBetIDToTheSetOfBetIDs
     * @see GamblerCard#generateNewBetID()
     */
    @Test
    public void generateNewBetID_shouldAddTheNewGeneratedBetIDToTheSetOfBetIDs() throws Exception {
        // act
        sut.generateNewBetID();
        // assert
        assertThat(sut.getBetIDs().size()).isEqualTo(1);
    }

    /**
     * @verifies returnTheNewGeneratedBetID
     * @see GamblerCard#generateNewBetID()
     */
    @Test
    public void generateNewBetID_shouldReturnTheNewGeneratedBetID() throws Exception {
        // assert
        assertThat(sut.generateNewBetID()).isNotNull();
    }

    /**
     * @verifies returnTheAmountOfBetIDsInTheSetOfBetIDs
     * @see GamblerCard#getNumberOfBetIDs()
     */
    @Test
    public void getNumberOfBetIDs_shouldReturnTheAmountOfBetIDsInTheSetOfBetIDs() throws Exception {
        // act
        sut.generateNewBetID();
        sut.generateNewBetID();
        // assert
        assertThat(sut.getNumberOfBetIDs()).isEqualTo(2);
    }

    /**
     * @verifies returnZeroWhenNoBetIDsWereAddedToTheSetOfBetIDs
     * @see GamblerCard#getNumberOfBetIDs()
     */
    @Test
    public void getNumberOfBetIDs_shouldReturnZeroWhenNoBetIDsWereAddedToTheSetOfBetIDs() throws Exception {
        // assert
        assertThat(sut.getNumberOfBetIDs()).isEqualTo(0);
    }

    /**
     * @verifies returnTheCardIDThatIsAssignedToTheLocalVariableCardID
     * @see GamblerCard#getCardID()
     */
    @Test
    public void getCardID_shouldReturnTheCardIDThatIsAssignedToTheLocalVariableCardID() throws Exception {
        // assert
        assertThat(sut.getCardID()).isNotNull();
    }
}


// need to make changes after the teachers feedback, resulted in redoing GamblerCardTest test!
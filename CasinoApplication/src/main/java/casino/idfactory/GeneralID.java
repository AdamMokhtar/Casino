package casino.idfactory;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * CREATE TESTS FOR THIS CLASS, implement necessary code when needed (after creating tests first)
 *
 */
public abstract class GeneralID {
    private final UUID uniqueID;
    private final Instant timeStamp;

    /**
     * @should assignTheNewRandomValueAndTheTimeStampToTheLocalVariables
     */
    public GeneralID() {
        this.uniqueID = UUID.randomUUID();
        this.timeStamp = Instant.now();
    }

    /**
     * //needs review!!
     * @should considerTheSameUniqueIdAndTimeStampAreLogicallyEqual
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralID generalID = (GeneralID) o;
        return Objects.equals(uniqueID, generalID.uniqueID) && Objects.equals(timeStamp, generalID.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueID, timeStamp);
    }

// TODO: implement necessary code. Add WHY you need it.

    // BELOW ARE GETTERS CREATED WITH INTELLIJ SO THEY DO NOT NEED TO BE TESTED
    public UUID getUniqueID() {
        return uniqueID;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

}

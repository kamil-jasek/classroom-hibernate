package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("stationary")
public final class StationaryLesson extends Lesson {

    private String roomNumber;
    private String address;

    protected StationaryLesson() {}

    public StationaryLesson(ZonedDateTime date, Subject subject, String roomNumber, String address) {
        super(date, subject);
        this.roomNumber = roomNumber;
        this.address = address;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        StationaryLesson that = (StationaryLesson) o;
        return Objects.equals(roomNumber, that.roomNumber) &&
            Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roomNumber, address);
    }
}

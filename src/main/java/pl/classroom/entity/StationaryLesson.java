package pl.classroom.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@DiscriminatorValue("STATIONARY")
public final class StationaryLesson extends Lesson {

    @Column(length = 10)
    private String roomNumber;

    @Column(length = 20)
    private String address;

    protected StationaryLesson() {
    }

    public StationaryLesson(Subject subject, ZonedDateTime data, String roomNumber, String address) {
        super(subject, data);
        this.roomNumber = roomNumber;
        this.address = address;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StationaryLesson that = (StationaryLesson) o;
        return roomNumber.equals(that.roomNumber) &&
                address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roomNumber, address);
    }

    @Override
    public String toString() {
        return "StationaryLesson{" +
                "roomNumber='" + roomNumber + '\'' +
                ", address='" + address + '\'' +
                ", subject=" + subject +
                ", date=" + date +
                ", participants=" + participants +
                '}';
    }
}
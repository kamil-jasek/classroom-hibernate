package pl.classroom.entity;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@DiscriminatorValue("ONLINE")
public final class OnlineLesson extends Lesson {

    @Column(length = 80)
    private String url;

    @Column(length = 20)
    private String code;

    private OnlineLesson() {
    }

    public OnlineLesson(Subject subject, ZonedDateTime data, String url, String code) {
        super(subject, data);
        this.url = url;
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        OnlineLesson that = (OnlineLesson) o;
        return url.equals(that.url) &&
                code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url, code);
    }

    @Override
    public String toString() {
        return "OnlineLesson{" +
                "url='" + url + '\'' +
                ", code='" + code + '\'' +
                ", subject=" + subject +
                ", date=" + date +
                ", participants=" + participants +
                '}';
    }
}

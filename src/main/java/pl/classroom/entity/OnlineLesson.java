package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("online")
public final class OnlineLesson extends Lesson {

    private String url;
    private String code;

    protected OnlineLesson() {}

    public OnlineLesson(ZonedDateTime date, Subject subject, String url, String code) {
        super(date, subject);
        this.url = url;
        this.code = code;
    }

    public String getUrl() {
        return url;
    }

    public String getCode() {
        return code;
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
        OnlineLesson lesson = (OnlineLesson) o;
        return Objects.equals(url, lesson.url) &&
            Objects.equals(code, lesson.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), url, code);
    }
}

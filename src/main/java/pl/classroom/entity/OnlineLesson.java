package pl.classroom.entity;

import java.time.ZonedDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@DiscriminatorValue("ONLINE")
@SQLDelete(sql = "UPDATE lessons SET deleted = true, deleteTime = NOW() WHERE id = ?")
@Where(clause = "deleted <> true")
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

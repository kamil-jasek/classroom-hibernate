package pl.classroom.entity;

import java.time.ZonedDateTime;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Auditable {

    private ZonedDateTime createTime;
    private ZonedDateTime deleteTime;
    private String createdBy;
    private boolean deleted;

    @OnlyForHibernate
    protected Auditable() {}

    protected Auditable(ZonedDateTime createTime, String createdBy) {
        this.createTime = createTime;
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public ZonedDateTime getDeleteTime() {
        return deleteTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public boolean isDeleted() {
        return deleted;
    }
}

package com.example.User.entity;

import com.example.User.utils.LongToStringDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Base class for all entities
 *
 * @param <T> Generic Parameter
 * @author Muhammad Mansoor
 */

@MappedSuperclass
@EntityListeners(value = AuditingEntityListener.class)
public abstract class BaseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @CreatedDate
    @JsonSerialize(using = LongToStringDateSerializer.class)
    private long createdDate;

    @LastModifiedDate
    @JsonSerialize(using = LongToStringDateSerializer.class)
    private long lastModifiedDate;

    @CreatedBy
    private String createdBy;

    /**
     * @return the id
     */
    public T getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(final T id) {
        this.id = id;
    }

    /**
     * @return the lastModifiedDate
     */
    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * @param lastModifiedDate the lastModifiedDate to set
     */
    public void setLastModifiedDate(final long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * @return the createdDate
     */
    public long getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(final long createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the createdBy
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy the createdBy to set
     */
    public void setCreatedBy(final String createdBy) {
        this.createdBy = createdBy;
    }


    @Override
    public String toString() {
        return "BaseEntity [id=" + id + ", createdDate=" + createdDate + ", lastModifiedDate=" + lastModifiedDate
                + ", createdBy=" + createdBy + "]";
    }
}

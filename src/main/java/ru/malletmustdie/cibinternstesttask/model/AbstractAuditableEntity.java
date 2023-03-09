package ru.malletmustdie.cibinternstesttask.model;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * Общий каркас для всех сущностей.
 */
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity {

    @CreatedDate
    @Column(updatable = false)
    //@Type(type = "java.time.Instant")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Instant createdAt;


    @LastModifiedDate
    //@Type(type = "java.time.Instant")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Instant updatedAt;

    /**
     * Установка свойства дата создания с округлением до микросекунд для корректной записи в БД.
     *
     * @param createdAt
     *         Дата создания
     */
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt != null ? createdAt.truncatedTo(ChronoUnit.MICROS) : Instant.now();
    }

    /**
     * Установка свойства дата обновление с округлением до микросекунд для корректной записи в БД.
     *
     * @param updatedAt
     *         Дата обновления
     */
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt.truncatedTo(ChronoUnit.MICROS);
    }

}

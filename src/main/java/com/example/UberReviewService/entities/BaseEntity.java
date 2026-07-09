package com.example.UberReviewService.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    protected Long id;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP) // this annotation tells spring about the format of Date object to be stored i.e. DATE, TIME or TIMESTAMP
    @CreatedDate // this annotation tells spring to handle it for object creation
    protected Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate // this annotation tells spring to handle it for object modification
    protected Date updatedAt;
}

package com.asn.data.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@MappedSuperclass
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int id;
    @Column(name ="\"createAt\"")
    protected LocalDateTime createAt;
    @Column(name = "\"updateAt\"")
    protected LocalDateTime updateAt;

    @PrePersist
    public void onPersist() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now(); 
    }

    @PreUpdate
    public void onUpdate() {
        updateAt = LocalDateTime.now();
    }

}

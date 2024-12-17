package com.asn.data.entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode

@Entity
@Table(name = "role")



public class Role extends AbstractEntity {
    String nom;

    public Role() {
        createAt = LocalDateTime.now();
        updateAt = LocalDateTime.now();
    }
}
